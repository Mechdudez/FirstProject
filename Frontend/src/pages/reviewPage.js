import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import RestaurantClient from "../api/restaurantClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class ReviewPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['clearResults', 'firstRender', 'clearSessionStorage', 'onManualClearResults', 'onGetReview', 'onCreateReview', 'renderReview'], this);
        this.dataStore = new DataStore();

    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the restaurant/review list.
     */
    mount() {
        document.getElementById('review-restaurant-form').addEventListener('submit', this.onCreateReview);

        document.getElementById('clearReviewResultsButton').addEventListener('click', this.onManualClearResults);
        document.getElementById('reviewPageButton').addEventListener('click', this.clearSessionStorage);


        this.client = new RestaurantClient();


        this.firstRender();


        this.dataStore.addChangeListener(this.renderReview);

    }


    // Render Methods --------------------------------------------------------------------------------------------------


    async clearResults() {
        let randomResultArea = document.getElementById("randomRestaurant");
        let resultArea = document.getElementById("result-info");

        randomResultArea.innerHTML = "";
        resultArea.innerHTML = "";
    }

    // Event Handlers --------------------------------------------------------------------------------------------------
    // to refresh all dataStore lists (KK)
    onRefresh() {
        this.fetchRestaurants();
        this.fetchReviews();
    }

    async renderReview(review) {
        let resultArea = document.getElementById("result-info");

        let storeHtmlReview = "";

        if (review) {
            storeHtmlReview += `<ul>`;
            storeHtmlReview += `<p><h3 class="listName" style="color:red;">Past Review for:</h3></p>`;
            storeHtmlReview += `<p><h3 class="listName" style="color:red;">${sessionStorage.getItem("restaurantName")}</h3></p>`;
            storeHtmlReview += `<p><b>Title: </b>${review.title}</p>`;
            storeHtmlReview += `<p><b>Rating: </b>${review.rating}</p>`;
            storeHtmlReview += `<p><b>Price: </b>$${review.price}</p>`;
            storeHtmlReview += `<p><b>Description: </b>${review.description}</p>`;
            storeHtmlReview += `<hr></hr>`;
            storeHtmlReview += `<p></p>`;
            storeHtmlReview += `</ul>`;
            resultArea.innerHTML = storeHtmlReview;

        } else {
            resultArea.innerHTML = "No Review";
        }


    }

    async onGetReview(event) {
        event.preventDefault();

        let result = await this.client.getAllReviews(this.errorHandler);
        this.dataStore.set("review", result);
    }

    async onManualClearResults(event) {
        event.preventDefault();

        await this.clearResults();
    }

    async onCreateReview(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        // Set the loading flag for the submit/create button
        let submitReviewButton = document.getElementById('submitReviewButton');
        submitReviewButton.innerText = 'submitting...';
        submitReviewButton.disabled = true;

        let title = document.getElementById("review-restaurant-title").value;
        let rating = document.getElementById("review-restaurant-rating").value;
        let price = document.getElementById("review-restaurant-price").value;
        let description = document.getElementById("review-restaurant-description").value;

        // restaurantId is pulled from dataStore
        let restaurantId = sessionStorage.getItem("restaurantId");
        let restaurantName = sessionStorage.getItem("restaurantName");
        let userId = sessionStorage.getItem("userId");

        let createdReview = await this.client.createReview(restaurantId, restaurantName, userId, title,
            rating, price, description, this.errorHandler());

        if (createdReview) {
            this.showMessage(`Submitted review for ${createdReview.restaurantName}!`)
            let resultArea = document.getElementById("randomRestaurant");
            resultArea.innerHTML = createdReview;
        } else {
            this.errorHandler("Error submitting!  Try again...");
        }


        // reset the form
        document.getElementById("review-restaurant-form").reset();

        // Re-enable the form
        submitReviewButton.innerText = 'Review Submitted';

    }

    async clearSessionStorage() {
        sessionStorage.removeItem("restaurantId");
        sessionStorage.removeItem("restaurantName");
    }
}


/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const reviewPage = new ReviewPage();

    if (sessionStorage.getItem("userId") == null) {
        window.location.href = "login.html";
    }

    if (sessionStorage.getItem("restaurantId") == null) {
        window.location.href = "restaurant.html";
    }


    reviewPage.mount();

};

window.addEventListener('DOMContentLoaded', main);
import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import RestaurantClient from "../api/restaurantClient";
/**
 * Logic needed for the view playlist page of the website.
 */
class RestaurantPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['createReview', 'onCreateRestaurant', 'renderRestaurant', 'clearResults', 'onManualClearResults', 'onGetRandomRestaurantFiltered'], this);
        this.dataStore = new DataStore();

    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the restaurant/review list.
     */
    mount() {
        document.getElementById('create-restaurant-form').addEventListener('submit', this.onCreateRestaurant);
        document.getElementById('get-restaurant-filtered-form').addEventListener('submit', this.onGetRandomRestaurantFiltered);
        document.getElementById('reviewListedPageButton').addEventListener('click', this.createReview);
        document.getElementById('clearResultsButton').addEventListener('click', this.onManualClearResults);

        this.client = new RestaurantClient();

        sessionStorage.removeItem("restaurantId");
        sessionStorage.removeItem("restaurantName");

        this.dataStore.addChangeListener(this.renderRestaurant);

    }


    // Render Methods --------------------------------------------------------------------------------------------------

    async renderRestaurant(restaurant) {
        let resultArea = document.getElementById("result-info");

        let storeHtmlRestaurant = "";

        storeHtmlRestaurant += `<ul>`;
        storeHtmlRestaurant += `<p><h3 class="listName" style="color:red;">${restaurant.restaurantName}</h3></p>`;
        storeHtmlRestaurant += `<p><b>Category: </b>${restaurant.category}</p>`;
        storeHtmlRestaurant += `<p><b>Average Rating: </b>${restaurant.averageRating}</p>`;
        storeHtmlRestaurant += `<p><b>Average Price: </b>${restaurant.averagePrice}</p>`;
        storeHtmlRestaurant += `<p><b>Store Hours: </b>${restaurant.storeHours}</p>`;
        storeHtmlRestaurant += `<hr></hr>`;
        storeHtmlRestaurant += `<p></p>`;
        storeHtmlRestaurant += `</ul>`;
        resultArea.innerHTML = storeHtmlRestaurant;
    }

    async clearResults() {
        let randomResultArea = document.getElementById("randomRestaurant");
        let resultArea = document.getElementById("result-info");

        randomResultArea.innerHTML = "";
        resultArea.innerHTML = "";
    }

    // Event Handlers --------------------------------------------------------------------------------------------------
    async onManualClearResults(event) {
        event.preventDefault();

        await this.clearResults();
    }

    async createReview(){
        if (this.dataStore.get("restaurantId") != null) {
            sessionStorage.setItem("restaurantId", this.dataStore.get("restaurantId"));
            sessionStorage.setItem("restaurantName", this.dataStore.get("restaurantName"));
        }
    }


    async onGetRandomRestaurantFiltered(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        // Set the loading flag for the submit/create button

        let generateRestaurantButton = document.getElementById('generate-random-filtered');
        generateRestaurantButton.innerText = 'generating...';
        generateRestaurantButton.disabled = true;



        let price = document.getElementById('get-restaurant-filtered-price').value;
        let category = document.getElementById('get-restaurant-filtered-category').value;

        let resultArea = document.getElementById("randomRestaurant");


        if (price == "" && category == "none") {
            let randomRestaurant = await this.client.getRandomRestaurant(this.errorHandler);

            this.dataStore.set("restaurantId", randomRestaurant.restaurantId);


            if (randomRestaurant) {
                // populates form field with random restaurant name
                await this.renderRestaurant(randomRestaurant);
                sessionStorage.setItem("restaurantId", randomRestaurant.restaurantId);
                sessionStorage.setItem("restaurantName", randomRestaurant.restaurantName);
            } else {
                resultArea.innerHTML = "No restaurant available";
            }
        } else {
            let randomRestaurant = await this.client.getRandomRestaurantFiltered(price, category, this.errorHandler);
            if (randomRestaurant) {
                // populates form field with random restaurant name
                await this.renderRestaurant(randomRestaurant);
                sessionStorage.setItem("restaurantId", randomRestaurant.restaurantId);
                sessionStorage.setItem("restaurantName", randomRestaurant.restaurantName);
            } else {
                resultArea.innerHTML = "No restaurant available";
            }
        }
        // Re-enable
        generateRestaurantButton.innerText = 'Generate';
        generateRestaurantButton.disabled = false;
    }


    async onCreateRestaurant(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        // Set the loading flag for the submit/create button
        let createRestaurantButton = document.getElementById('createRestaurantButton');
        createRestaurantButton.innerText = 'creating...';
        createRestaurantButton.disabled = true;

        let name = document.getElementById("create-restaurant-name").value;
        let category = document.getElementById("create-restaurant-category").value;

        let mondayStart = document.getElementById("create-restaurant-monday-start").value;
        let mondayStartAMPM = document.getElementById("create-restaurant-monday-am-pm").value;
        let mondayEnd = document.getElementById("create-restaurant-monday-end").value;
        let mondayEndAMPM = document.getElementById("create-restaurant-monday-end-am-pm").value;

        let tuesdayStart = document.getElementById("create-restaurant-tuesday-start").value;
        let tuesdayStartAMPM = document.getElementById("create-restaurant-tuesday-am-pm").value;
        let tuesdayEnd = document.getElementById("create-restaurant-tuesday-end").value;
        let tuesdayEndAMPM = document.getElementById("create-restaurant-tuesday-end-am-pm").value;

        let wednesdayStart = document.getElementById("create-restaurant-wednesday-start").value;
        let wednesdayStartAMPM = document.getElementById("create-restaurant-wednesday-am-pm").value;
        let wednesdayEnd = document.getElementById("create-restaurant-wednesday-end").value;
        let wednesdayEndAMPM = document.getElementById("create-restaurant-wednesday-end-am-pm").value;

        let thursdayStart = document.getElementById("create-restaurant-thursday-start").value;
        let thursdayStartAMPM = document.getElementById("create-restaurant-thursday-am-pm").value;
        let thursdayEnd = document.getElementById("create-restaurant-thursday-end").value;
        let thursdayEndAMPM = document.getElementById("create-restaurant-thursday-end-am-pm").value;

        let fridayStart = document.getElementById("create-restaurant-friday-start").value;
        let fridayStartAMPM = document.getElementById("create-restaurant-friday-am-pm").value;
        let fridayEnd = document.getElementById("create-restaurant-friday-end").value;
        let fridayEndAMPM = document.getElementById("create-restaurant-friday-end-am-pm").value;

        let saturdayStart = document.getElementById("create-restaurant-saturday-start").value;
        let saturdayStartAMPM = document.getElementById("create-restaurant-saturday-am-pm").value;
        let saturdayEnd = document.getElementById("create-restaurant-saturday-end").value;
        let saturdayEndAMPM = document.getElementById("create-restaurant-saturday-end-am-pm").value;

        let sundayStart = document.getElementById("create-restaurant-sunday-start").value;
        let sundayStartAMPM = document.getElementById("create-restaurant-sunday-am-pm").value;
        let sundayEnd = document.getElementById("create-restaurant-sunday-end").value;
        let sundayEndAMPM = document.getElementById("create-restaurant-sunday-end-am-pm").value;

        //input all storeHours variables, calls createStoreHours from restaurantClient.js (KK)
        const Monday = "Monday "  + mondayStart + mondayStartAMPM + " to " + mondayEnd + mondayEndAMPM;
        const Tuesday = " Tuesday " + tuesdayStart + tuesdayStartAMPM + " to " + tuesdayEnd + tuesdayEndAMPM;
        const Wednesday = " Wednesday " + wednesdayStart + wednesdayStartAMPM + " to " + wednesdayEnd + wednesdayEndAMPM;
        const Thursday = " Thursday " + thursdayStart + thursdayStartAMPM + " to " + thursdayEnd + thursdayEndAMPM;
        const Friday = " Friday " + fridayStart + fridayStartAMPM + " to " + fridayEnd + fridayEndAMPM;
        const Saturday = " Saturday " + saturdayStart + saturdayStartAMPM + " to " + saturdayEnd + saturdayEndAMPM;
        const Sunday = " Sunday " + sundayStart + sundayStartAMPM + " to " + sundayEnd + sundayEndAMPM;

        // list of strings variable
        let storeHours = [Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday];

        //input all arguments and call createRestaurant() from restaurantClient
        const createdRestaurant = await this.client.createRestaurant(name, category, storeHours, this.errorHandler);

        if (createdRestaurant) {
            await this.showMessage(`Created ${createdRestaurant.restaurantName}!`);
            await this.renderRestaurant(createdRestaurant);
        } else {
            this.errorHandler("Error creating!  Try again...");
        }

        if (document.getElementById('create-restaurant-review-now').value == "true"){
            sessionStorage.setItem("restaurantId", createdRestaurant.restaurantId);
            sessionStorage.setItem("restaurantName", createdRestaurant.restaurantName);
            window.location.href = "review.html";
        }

        // reset the form
        document.getElementById("create-restaurant-form").reset();

        // Re-enable the form
        createRestaurantButton.innerText = 'Create';
        createRestaurantButton.disabled = false;
    }
}


/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const restaurantPage = new RestaurantPage();

    if (sessionStorage.getItem("userId") == null){
        window.location.href = "login.html";
    }
    restaurantPage.mount();

};

window.addEventListener('DOMContentLoaded', main);
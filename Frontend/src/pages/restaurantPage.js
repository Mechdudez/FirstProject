import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import RestaurantClient from "../api/restaurantClient";
/**
 * Logic needed for the view playlist page of the website.
 */
class RestaurantPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGetRandomRestaurant', 'onCreateRestaurant', 'renderRestaurant', 'onGetReview', 'onCreateReview'], this);
        this.dataStore = new DataStore();

    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the restaurant/review list.
     */
    mount() {
        document.getElementById('get-restaurant-form').addEventListener('click', this.onGetRandomRestaurant);
        document.getElementById('create-restaurant-form').addEventListener('submit', this.onCreateRestaurant);
        document.getElementById('review-restaurant-form').addEventListener('submit', this.onCreateReview);
        // document.getElementById('generateRandomRestaurant').addEventListener("click", this.onGetRandomRestaurant);


        this.client = new RestaurantClient();

        this.dataStore.addChangeListener(this.renderRestaurant);

    }

    async fetchRestaurants() {
        const allRestaurants = await this.client.getAllRestaurants(this.errorHandler)

        //TODO: verify path
        this.dataStore.set("restaurants/all", allRestaurants);
    }

    async fetchReviews() {
        const allReviews = await this.client.getAllReviews(this.errorHandler)

        //TODO: verify path
        this.dataStore.set("review/all", allReviews);
    }

    async checkRestaurants(inputRestaurantName) {
        const allRestaurants = await this.client.getAllRestaurants(this.errorHandler)
        let existingRestaurant = [];

        //TODO: attempt to find more efficient method, .contains?
        // if (allRestaurants.contains(restaurantName, inputRestaurantName))
        if (allRestaurants && allRestaurants.length > 0) {
            for (const restaurant of allRestaurants) {
                if (restaurant.restaurantName == inputRestaurantName) {
                    existingRestaurant.push(restaurant);
                }
            }
        }
        if (existingRestaurant.length > 0) {
            return existingRestaurant.at(0).restaurantId;
        } else {
            this.errorHandler("Restaurant does not exist!");
        }
    }


    // Render Methods --------------------------------------------------------------------------------------------------

    async renderRestaurant() {
        let resultArea = document.getElementById("result-info");

        // (KK) changed pathing
        const restaurants = this.dataStore.get("restaurants/all");

        let storeHtmlRestaurant = "";

        for (let restaurant of restaurants){

            if (restaurant) {

                storeHtmlRestaurant += `<ul>`;
                storeHtmlRestaurant += `<p>Restaurant Name: ${restaurant.restaurantName}</p>`;
                storeHtmlRestaurant += `<p>Category: ${restaurant.category}</p>`;
                storeHtmlRestaurant += `<p>Store Hours: ${restaurant.storeHours}</p>`;
                storeHtmlRestaurant += `<p></p>`;
                storeHtmlRestaurant += `</ul>`;
                resultArea.innerHTML = storeHtmlRestaurant;

            } else {
                resultArea.innerHTML = "No Restaurant";
            }
        }

    }

    // Event Handlers --------------------------------------------------------------------------------------------------
    // to refresh all dataStore lists (KK)
    onRefresh() {
        this.fetchRestaurants();
        this.fetchReviews();
    }

    async onGetReview(event) {
        event.preventDefault();

        let result = await this.client.getAllReviews(this.errorHandler);
        this.dataStore.set("review", result);
    }


    async onCreateReview(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        // Set the loading flag for the submit/create button
        let submitReviewButton = document.getElementById('submitReviewButton');
        submitReviewButton.innerText = 'submitting...';
        submitReviewButton.disabled = true;

        //TODO: figure out how to get restaurantId without front end user input

        // let restaurantId = document.getElementsByName("restaurantName").value;
        let restaurantName = document.getElementsByName("restaurantName").value;
        let userId = document.getElementsByName("userId").value;
        let title = document.getElementsByName("title").value;
        let rating = document.getElementsByName("rating").value;
        let price = document.getElementsByName("price").value;
        let description = document.getElementsByName("description").value;

        // restaurantId is pulled from dataStore
        let restaurantId = this.checkRestaurants(restaurantName);

        let createdReview = await this.client.createReview(restaurantId, restaurantName, userId, title,
            rating, price, description, this.errorHandler());
        this.dataStore.set("review", createdReview);

        if (createdReview) {
            this.showMessage(`Submitted review for ${createdReview.restaurantName}!`)
        } else {
            this.errorHandler("Error submitting!  Try again...");
        }

        // reset the form
        document.getElementById("review-restaurant-form").reset();

        // Re-enable the form
        submitReviewButton.innerText = 'Submit Review';
        submitReviewButton.disabled = false;
        this.onRefresh();



    }

    //TODO: finish implementation
    async onGetRandomRestaurant(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        // Set the loading flag for the submit/create button
        let generateRestaurantButton = document.getElementById('generateRandomRestaurant');
        generateRestaurantButton.innerText = 'generating...';
        generateRestaurantButton.disabled = true;

        let randomRestaurant = await this.client.getRandomRestaurant(this.errorHandler);

        // populates form field with random restaurant name
//        document.getElementById('randomRestaurant').value = randomRestaurant.restaurantName;
//        document.getElementById('randomRestaurant').value = randomRestaurant;
        let resultArea = document.getElementById("randomRestaurant");
        if (randomRestaurant) {
            resultArea.innerHTML = randomRestaurant.restaurantName;
        } else {
            resultArea.innerHTML = "No restaurant available";
        }



        // Re-enable
        generateRestaurantButton.innerText = 'Generate';
        generateRestaurantButton.disabled = false;
//        this.onRefresh();
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


        //temporary restaurantId variable, Math.Random() may not be unique
        //TODO: find better method, possibly uuidv4()

        //input all arguments and call createRestaurant() from restaurantClient
        const createdRestaurant = await this.client.createRestaurant(name, category, storeHours, this.errorHandler);
        this.dataStore.set("restaurants", createdRestaurant);

        if (createdRestaurant) {
            this.showMessage(`Created ${createdRestaurant.restaurantName}!`);
        } else {
            this.errorHandler("Error creating!  Try again...");
        }

        // reset the form
        document.getElementById("create-restaurant-form").reset();

        // Re-enable the form
        createRestaurantButton.innerText = 'Create';
        createRestaurantButton.disabled = false;
        this.onRefresh();
    }
}


/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const restaurantPage = new RestaurantPage();

    // var expanded = false;
    //
    // function showCheckboxes() {
    //
    // }
    restaurantPage.mount();

};

window.addEventListener('DOMContentLoaded', main);

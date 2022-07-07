import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import RestaurantClient from "../api/restaurantClient";
/**
 * Logic needed for the view playlist page of the website.
 */
class RestaurantPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGetRestaurant', 'onCreateRestaurant', 'renderRestaurant'], this);
        this.dataStore = new DataStore();

    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('get-restaurant-form').addEventListener('submit', this.onGet);
        document.getElementById('create-restaurant-form').addEventListener('submit', this.onCreate)
        document.getElementById('review-restaurant-form').addEventListener('submit', this.onCreate);

        this.client = new RestaurantClient();

        this.dataStore.addChangeListener(this.renderRestaurant);
        // this.onGetRestaurant();
    }


    // Render Methods --------------------------------------------------------------------------------------------------

    async renderRestaurant() {
        let resultArea = document.getElementById("result-info");

        const restaurants = this.dataStore.get("restaurant");

        let storeHtmlRestaurant = "";

        for (let restaurant of restaurants){

            if (restaurant) {

                storeHtmlRestaurant += `<ul>`;
                storeHtmlRestaurant += `<h2><li>${restaurant.name}</li></h2>`;
                storeHtmlRestaurant += `</ul>`;
                resultArea.innerHTML = storeHtmlRestaurant;
                // storeHtmlRestaurant += `<h3> By: ${restaurant.name</h3>`;


            } else {
                resultArea.innerHTML = "No Restaurant";
            }
        }

    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onGetRestaurant() {
        // Prevent the page from refreshing on form submit

        let result = await this.client.getAllRestaurants(this.errorHandler);
        this.dataStore.set("restaurant", result);

    }

    async onCreateRestaurant(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        // Set the loading flag for the submit/create button
        let createRestaurantButton = document.getElementById('createRestaurantButton');
        createRestaurantButton.innerText = 'creating...';
        createRestaurantButton.disabled = true;

        let userId = document.getElementById("create-restaurant-userId").value;
        let name = document.getElementById("create-restaurant-name").value;
        let category = document.getElementById("create-restaurant-category").value;

        let storeHoursMondayStart = document.getElementById("create-restaurant-monday-start").value;
        let storeHoursMondayStartAMPM = document.getElementById("create-restaurant-monday-am-pm").value;
        let storeHoursMondayEnd = document.getElementById("create-restaurant-monday-end").value;
        let storeHoursMondayEndAMPM = document.getElementById("create-restaurant-monday-end-am-pm").value;

        let storeHoursTuesdayStart = document.getElementById("create-restaurant-tuesday-start").value;
        let storeHoursTuesdayStartAMPM = document.getElementById("create-restaurant-tuesday-am-pm").value;
        let storeHoursTuesdayEnd = document.getElementById("create-restaurant-tuesday-end").value;
        let storeHoursTuesdayEndAMPM = document.getElementById("create-restaurant-tuesday-end-am-pm").value;

        let storeHoursWednesdayStart = document.getElementById("create-restaurant-wednesday-start").value;
        let storeHoursWednesdayStartAMPM = document.getElementById("create-restaurant-wednesday-am-pm").value;
        let storeHoursWednesdayEnd = document.getElementById("create-restaurant-wednesday-end").value;
        let storeHoursWednesdayEndAMPM = document.getElementById("create-restaurant-wednesday-end-am-pm").value;

        let storeHoursThursdayStart = document.getElementById("create-restaurant-thursday-start").value;
        let storeHoursThursdayStartAMPM = document.getElementById("create-restaurant-thursday-am-pm").value;
        let storeHoursThursdayEnd = document.getElementById("create-restaurant-thursday-end").value;
        let storeHoursThursdayEndAMPM = document.getElementById("create-restaurant-thursday-end-am-pm").value;

        let storeHoursFridayStart = document.getElementById("create-restaurant-friday-start").value;
        let storeHoursFridayStartAMPM = document.getElementById("create-restaurant-friday-am-pm").value;
        let storeHoursFridayEnd = document.getElementById("create-restaurant-friday-end").value;
        let storeHoursFridayEndAMPM = document.getElementById("create-restaurant-friday-end-am-pm").value;

        let storeHoursSaturdayStart = document.getElementById("create-restaurant-saturday-start").value;
        let storeHoursSaturdayStartAMPM = document.getElementById("create-restaurant-saturday-am-pm").value;
        let storeHoursSaturdayEnd = document.getElementById("create-restaurant-saturday-end").value;
        let storeHoursSaturdayEndAMPM = document.getElementById("create-restaurant-saturday-end-am-pm").value;

        let storeHoursSundayStart = document.getElementById("create-restaurant-sunday-start").value;
        let storeHoursSundayStartAMPM = document.getElementById("create-restaurant-sunday-am-pm").value;
        let storeHoursSundayEnd = document.getElementById("create-restaurant-sunday-end").value;
        let storeHoursSundayEndAMPM = document.getElementById("create-restaurant-sunday-end-am-pm").value;

        //input all storeHours variables, calls createStoreHours from restaurantClient.js (KK)
        let storeHours = this.client.createStoreHours(storeHoursMondayStart, storeHoursMondayStartAMPM, storeHoursMondayEnd, storeHoursMondayEndAMPM,
               storeHoursTuesdayStart, storeHoursTuesdayStartAMPM, storeHoursTuesdayEnd, storeHoursTuesdayEndAMPM,
               storeHoursWednesdayStart, storeHoursWednesdayStartAMPM, storeHoursWednesdayEnd, storeHoursWednesdayEndAMPM,
               storeHoursThursdayStart, storeHoursThursdayStartAMPM, storeHoursThursdayEnd, storeHoursThursdayEndAMPM,
               storeHoursFridayStart, storeHoursFridayStartAMPM, storeHoursFridayEnd, storeHoursFridayEndAMPM,
               storeHoursSaturdayStart, storeHoursSaturdayStartAMPM, storeHoursSaturdayEnd, storeHoursSaturdayEndAMPM,
               storeHoursSundayStart, storeHoursSundayStartAMPM, storeHoursSundayEnd, storeHoursSundayEndAMPM
        );


        //temporary restaurantId variable, Math.Random() may not be unique
        //TODO: find better method, possibly uuidv4()
        var restaurantId = Math.floor(Math.random() * 100);

        //input all arguments and call createRestaurant() from restaurantClient
        const createdRestaurant = await this.client.createRestaurant(restaurantId, userId, name, category, storeHours, this.errorHandler);
        this.dataStore.set("restaurant", createdRestaurant);

        if (createdRestaurant) {
            this.showMessage(`Created ${createdRestaurant.name}!`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
        this.onGetRestaurant();

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

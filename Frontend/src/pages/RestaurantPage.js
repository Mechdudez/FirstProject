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

        document.getElementById('get-by-restaurant-form').addEventListener('submit', this.onGet);
     //   document.getElementById('create-form').addEventListener('submit', this.onCreate);
        this.client = new RestaurantClient();

        this.dataStore.addChangeListener(this.renderRestaurant);
        this.onGetRestaurant();
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

    async onCreateRestaurant() {
        // Prevent the page from refreshing on form submit

        let userId = document.getElementById("create-comment-userId").value;
        let name = document.getElementById("create-comment-name").value;
        let category = document.getElementById("create-comment-category").value;
        let storeHours = document.getElementById("create-comment-storeHours").value;

        const createdRestaurant = await this.client.createRestaurant(userId, name, category, storeHours, this.errorHandler);
        this.dataStore.set("restaurant", createdRestaurant);

        if (createdRestaurant) {
            this.showMessage(`Created ${createdRestaurant.name}!`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
        this.onGetRestaurant();
    }
}


/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const restaurantPage = new RestaurantPage();

    var expanded = false;

    function showCheckboxes() {
        var checkboxes = document.getElementById("checkboxes");
        if (!expanded) {
            checkboxes.style.display = "block";
            expanded = true;
        } else {
            checkboxes.style.display = "none";
            expanded = false;
        }
    }
    restaurantPage.mount();

    };

window.addEventListener('DOMContentLoaded', main);

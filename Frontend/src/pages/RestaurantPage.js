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

        this.dataStore.addChangeListener(this.renderRestaurant)
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
                // storeHtmlRestaurant += `<h3> By: ${restaurant.name</h3>`;

                resultArea.innerHTML = storeHtmlRestaurant;
            } else {
                resultArea.innerHTML = "No Restaurant";
            }
        }

    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onGetRestaurant() {
        // Prevent the page from refreshing on form submit
        // event.preventDefault();

        // let id = document.getElementById("id-field").value;
        // this.dataStore.set("restaurant", null);

        let result = await this.client.getExample(this.errorHandler);
        this.dataStore.set("restaurant", result);

        //     if (result) {
        //         this.showMessage(`Got ${result.name}!`)
        //     } else {
        //         this.errorHandler("Error doing GET!  Try again...");
        //     }
    }

    async onCreateRestaurant(event) {
        // Prevent the page from refreshing on form submit
        //  event.preventDefault();
        //  this.dataStore.set("example", null);

        let restaurant = document.getElementById("create-comment-restaurant").value;

        const createdRestaurant = await this.client.createExample(restaurant, this.errorHandler);
        this.dataStore.set("example", createdRestaurant);

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
    restaurantPage.mount();
};

window.addEventListener('DOMContentLoaded', main);

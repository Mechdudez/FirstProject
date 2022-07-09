import BaseClass from "../util/baseClass";
import axios from 'axios'

// var _List = require("collections/list");

/**
 * Client to call the RestaurantService.
 */
export default class RestaurantClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'getRestaurant', 'createRestaurant', 'getAllRestaurants', 'createReview',
            'getAllReviews', 'getRandomRestaurant'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     * @param client The client that has been successfully loaded.
     */
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }

    async getRestaurant(errorCallback) {
        try {
            const response = await this.client.get(`/restaurant/find`);
            return response.data;
        } catch (error) {
            this.handleError("getRestaurant", error, errorCallback)
        }
    }

    async getRandomRestaurant(errorCallback) {
        try {
            //TODO: fix pathing (KK)
            const response = await this.client.get(`/restaurant`);
            return response.data;
        } catch (error) {
            this.handleError("getRandomRestaurant", error, errorCallback)
        }
    }

    async createReview(restaurantId, restaurantName, userId, title,
                       rating, price, description, errorCallback) {
        try {
            const response = await this.client.post(`review`, {
                //TODO: need to get restaurantId without user input (implemented now in restaurantPage.js)
                "restaurantId": restaurantId,
                "restaurantName": restaurantName,
                "userId": userId,
                "title": title,
                "rating": rating,
                "price": price,
                "description": description

            });
            return response.data;
        } catch (error) {
            this.handleError("createReview", error, errorCallback);
        }

    }

    async createRestaurant( name, category, storeHours, errorCallback) {
        try {
            const response = await this.client.post(`restaurant`, {
                //TODO: need to generate UUID serverside instead of user input (KK)
                //TODO: clarify function and application of userId (KK)
                "name": name,
                "category": category,
                "storeHours":storeHours

            });
            return response.data;
        } catch (error) {
            this.handleError("createRestaurant", error, errorCallback);
        }

    }
    async getAllRestaurants(errorCallback) {
        try {
            const response = await this.client.get(`/restaurant/all`);
            return response.data;
        } catch (error) {
            this.handleError("getAllRestaurants", error, errorCallback)
        }
    }
    async getAllReviews(errorCallback) {
        try {
            const response = await this.client.get(`/review/all`);
            return response.data;
        } catch (error) {
            this.handleError("getAllReviews", error, errorCallback)
        }
    }

    //TODO: method to take storeHours input and return formatted List of Strings (KK)


    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(method, error, errorCallback) {
        console.error(method + " failed - " + error);
        if (error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
        if (errorCallback) {
            errorCallback(method + " failed - " + error);
        }
    }
}
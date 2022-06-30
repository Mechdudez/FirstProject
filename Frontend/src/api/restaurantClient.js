import BaseClass from "../util/baseClass";
import axios from 'axios'

/**
 * Client to call the ExampleService.
 */
export default class RestaurantClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'getRestaurant', 'createRestaurant', 'getAllRestaurants'];
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

    async getRestaurant(id, errorCallback) {
        try {
            const response = await this.client.get(`/restaurant/${id}`);
            return response.data;
        } catch (error) {
            this.handleError("getRestaurant", error, errorCallback)
        }
    }

    async createRestaurant(restaurant, errorCallback) {
        try {
            const response = await this.client.post(`restaurant`, {
                "restaurant": restaurant,

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
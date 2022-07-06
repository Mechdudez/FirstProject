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

    async getRestaurant(errorCallback) {
        try {
            const response = await this.client.get(`/restaurant/find`);
            return response.data;
        } catch (error) {
            this.handleError("getRestaurant", error, errorCallback)
        }
    }

    async createRestaurant(restaurantId, userId, name, category, storeHours, errorCallback) {
        try {
            const response = await this.client.post(`restaurant`, {
                //TODO: need to generate UUID serverside instead of user input (KK)
                //TODO: clarify function and application of userId (KK)
                "restaurantId": restaurantId,
                "userId": userId,
                "name": name,
                "category": category,
                "storeHours": storeHours

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
    async getRestaurantRequest(){

    }

    //TODO: method to take storeHours input and return formatted List of Strings (KK)
    async createStoreHours(mondayStart, mondayStartAMPM, mondayEnd, mondayEndAMPM,
                                tuesdayStart, tuesdayStartAMPM, tuesdayEnd, tuesdayEndAMPM,
                                wednesdayStart, wednesdayStartAMPM, wednesdayEnd, wednesdayEndAMPM,
                                thursdayStart, thursdayStartAMPM, thursdayEnd, thursdayEndAMPM,
                                fridayStart, fridayStartAMPM, fridayEnd, fridayEndAMPM,
                                saturdayStart, saturdayStartAMPM, saturdayEnd, saturdayEndAMPM,
                                sundayStart, sundayStartAMPM, sundayEnd, sundayEndAMPM) {
        // implement method
        // formatted Strings for each day
        const Monday = "Monday " + mondayStart + mondayStartAMPM + " to " + mondayEnd + mondayEndAMPM;
        const Tuesday = "Tuesday " + tuesdayStart + tuesdayStartAMPM + " to " + tuesdayEnd + tuesdayEndAMPM;
        const Wednesday = "Wednesday " + wednesdayStart + wednesdayStartAMPM + " to " + wednesdayEnd + wednesdayEndAMPM;
        const Thursday = "Thursday " + thursdayStart + thursdayStartAMPM + " to " + thursdayEnd + thursdayEndAMPM;
        const Friday = "Friday " + fridayStart + fridayStartAMPM + " to " + fridayEnd + fridayEndAMPM;
        const Saturday = "Saturday " + saturdayStart + saturdayStartAMPM + " to " + saturdayEnd + saturdayEndAMPM;
        const Sunday = "Sunday " + sundayStart + sundayStartAMPM + " to " + sundayEnd + sundayEndAMPM;

        // list of strings variable
        var listHours = [Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday];
        return listHours;
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
import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
/**
 * Logic needed for the view playlist page of the website.
 */
class LoginPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['login'], this);
        this.dataStore = new DataStore();

    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the restaurant/review list.
     */
    mount() {
        document.getElementById('get-userId-form').addEventListener('submit', this.login);

    }

    async login(event) {
        event.preventDefault();

        let userId = document.getElementById("login-userId");
        sessionStorage.setItem("userId", userId.value);

        if (sessionStorage.getItem("userId") != null) {
            this.showMessage(`Welcome ${sessionStorage.getItem("userId")}!`)
            window.location.href = "restaurant.html";
        } else {
            this.errorHandler("Error logging in!  Try again...");
        }
    }

}
/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const loginPage = new LoginPage();

    loginPage.mount();

};

window.addEventListener('DOMContentLoaded', main);
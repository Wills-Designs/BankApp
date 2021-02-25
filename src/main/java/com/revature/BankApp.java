package com.revature;
import com.revature.utilities.AppState;

/**
 * @author William Soriano
 * initializes the app by creating a new appState in a while loop,
 *  runs until the boolean is set to false and ends the appState
 */

public class BankApp {

    private static AppState app = new AppState();

    public static void main(String[] args) {
        while (app.isAppRunning()) {
            app.getRouter().navigate("/home");
        }
    }

    public static AppState app() {
        return app;
    }
}

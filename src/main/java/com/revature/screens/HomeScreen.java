package com.revature.screens;

import java.text.DecimalFormat;
import static com.revature.BankApp.app;

/** the main screen displayed once the app is initialized,
 *  allows user to login, register, or end the application
 */
public class HomeScreen extends Screen{

    /** creates a new screen, and sets the route */
    public HomeScreen(){
        super("HomeScreen", "/home");
    }

    /** overrides the render method to change what is displayed to the console
     * takes user input to decide next route */
    @Override
    public void render() {
        System.out.println("\n\n\n\nWelcome to the Bank");
        System.out.println("Please select from the following options");
        System.out.println("[1] Login");
        System.out.println("[2] Register");
        System.out.println("[3] Exit Terminal");

        try {
            System.out.print("\n Enter Selection -> ");
            String userSelection = app().getConsole().readLine();

            switch (userSelection) {
                case "1":
                    System.out.println("\n\n\n\n\n\n[LOG] - Navigating to login screen");
                    app().getRouter().navigate("/login");
                    break;
                case "2":
                    System.out.println("\n\n\n\n\n\n[LOG] - Navigating to bank screen");
                    app().getRouter().navigate("/register");
                    break;
                case "3":
                    System.out.println("\n\n\n\n\n\n[LOG] - Exiting application");
                    app().setAppRunning(false);
                    break;
                default:
                    System.out.println("Invalid selection!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Shutting down application");
            app().setAppRunning(false);
        }
    }
}

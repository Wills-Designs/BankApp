package com.project.screens;
import com.project.BankApp;
import com.project.exceptions.InvalidRequestException;
import com.project.users.UserService;

import javax.security.sasl.AuthenticationException;

/** displays the login menu, where a users credentials are validated */
public class LoginScreen extends Screen {

    private UserService userService;

    /**
     * creates a new screen, and sets the route
     * @param userService
     */
    public LoginScreen(UserService userService) {
        super("LoginScreen", "/login");
        this.userService = userService;
    }

    /** overrides render to display login functions,
     * user is prompted to enter credentials and
     * the authenticate method is called to verify, creates session upon successful validation
     */
    @Override
    public void render() {
        String username;
        String password;

        try {
            System.out.println("+---------------------+");
            System.out.println("Please provide your login credentials");
            System.out.print("Username: ");
            username = BankApp.app().getConsole().readLine();
            System.out.print("Password: ");
            password = BankApp.app().getConsole().readLine();

            userService.authenticate(username, password);

            if (BankApp.app().isSessionValid()) {
                System.out.println("[LOG] - Login successful, navigating to dashboard...");
                BankApp.app().getRouter().navigate("/bankscreen");
            }
        } catch (InvalidRequestException | AuthenticationException e) {
            System.err.println("[INFO] - Invalid login credentials provided!");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[SEVERE] - An unexpected exception occurred");
            System.err.println("[FATAL] - Shutting down application");
            BankApp.app().setAppRunning(false);
        }
    }
}
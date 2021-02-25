package com.project.screens;
import com.project.users.AppUser;
import com.project.users.UserService;
import static com.project.BankApp.app;

/** registration screen for new users to create an account */
public class RegisterScreen extends Screen {

    private UserService userService;

    /**
     * creates a new screen, and sets the route, also creates a new userService
     * @param userService
     */
    public RegisterScreen(UserService userService) {
        super("RegisterScreen", "/register");
        this.userService = userService;
    }

    /** overrides render, sets up a prompt asking user to
     * input properties needed to generate a new user in the database
     * upon completion reroutes them to homescreen
     */
    @Override
    public void render() {
        String firstName;
        String lastName;
        String username;
        String password;

        try {
            System.out.println("+---------------------+");
            System.out.println("Sign up for a new account!");
            System.out.print("First name: ");
            firstName = app().getConsole().readLine();
            System.out.print("Last name: ");
            lastName = app().getConsole().readLine();
            System.out.print("Username: ");
            username = app().getConsole().readLine();
            System.out.print("Password: ");
            password = app().getConsole().readLine();

            AppUser newUser = new AppUser(firstName, lastName, username, password);
            userService.register(newUser);
            System.out.println("[LOG] - Generating new checking account, login to make first deposit.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
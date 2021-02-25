package com.revature.screens;
import com.revature.exceptions.InvalidRequestException;
import com.revature.users.AppUser;
import com.revature.users.UserService;
import com.revature.utilities.ConnectionFactory;
import javax.security.sasl.AuthenticationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static com.revature.BankApp.app;

/** main menu displayed after user is authenticated */
public class BankScreen extends Screen {

    private UserService userService;

    /**
     * creates a new screen, and sets the route
     * @param userService
     */
    public BankScreen(UserService userService) {
        super("BankScreen", "/bankscreen");
        this.userService = userService;
    }

    /** overrides previous render and brings up the users first name, and acc balance
     *  user is given the option to add or remove money using switch statement
     */
    @Override
    public void render() {

        AppUser user = app().getCurrentSession().getSessionUser();
        String balance = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "SELECT current_balance from accounts WHERE user_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, user.getId());

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                balance = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("\n\n\n\n\n\nWelcome to the Bank, " + user.getFirstName());
        System.out.println("Your Current balance is, $" + balance);
        System.out.println("\n\nPlease select from the following options");
        System.out.println("[1] Withdraw");
        System.out.println("[2] Deposit");
        System.out.println("[3] Log Off");

        try {
            System.out.print("\n Enter Selection -> ");
            String userSelection = app().getConsole().readLine();

            switch (userSelection) {
                case "1":
                    System.out.println("\n\n");
                    app().getRouter().navigate("/withdrawl");
                    break;
                case "2":
                    System.out.println("\n\n");
                    app().getRouter().navigate("/deposit");
                    break;
                case "3":
                    System.out.println("Exiting application");
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
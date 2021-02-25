package com.project.screens;
import com.project.BankApp;
import com.project.users.AppUser;
import com.project.users.UserService;
import com.project.utilities.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/** deposit screen, where the user can input amount to be added to account
 *  updates account as well
 */
public class DepositScreen extends Screen{

    /**
     * creates a new screen, and sets the route
     * @param userService
     */
    public DepositScreen(UserService userService) {
        super("DepositScreen", "/deposit");
    }

    /** overrides the render method to allow a user to input deposit amount
     * after validating the users choice the accounts table is updated with the new quantity
     */
    @Override
    public void render() {
        AppUser user = BankApp.app().getCurrentSession().getSessionUser();

        String balance = null;
        String depAmount;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "select current_balance from accounts where user_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, user.getId());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                balance = rs.getString(1);
            }

            System.out.println("\n\n\n\n\n\nPlease enter the amount to be Deposited : ");
            System.out.print("\n Enter Quantity ->$");
            depAmount = BankApp.app().getConsole().readLine();
            double number = Double.parseDouble(depAmount);
            double currBalance = Double.parseDouble(balance);

            System.out.println("\n\n\n\n\n\n\n\n\nAre you sure you want to deposit $" + number);
            System.out.println("[1] Yes");
            System.out.println("[2] Change amount");
            System.out.println("[3] Log Off\n\n\n");
            System.out.print("\n Enter Selection -> ");

            String userSelection = BankApp.app().getConsole().readLine();

            switch (userSelection) {
                case "1":
                    currBalance = currBalance + number;  // need to set currAmount as return value from parsing bufferreader

                    String sql2 = "update accounts set current_balance = ? where user_id = ?;";
                    PreparedStatement pstmt2 = conn.prepareStatement(sql2);
                    pstmt2.setDouble(1, currBalance);
                    pstmt2.setInt(2, user.getId());
                    pstmt2.executeUpdate();

                    System.out.print("\n $" + number + " was successfully deposited, returning to main menu\n\n");
                    BankApp.app().getRouter().navigate("/bankscreen");
                    break;
                case "2":
                    System.out.println("");
                    BankApp.app().getRouter().navigate("/deposit");
                    break;
                case "3":
                    System.out.println("Exiting application");
                    BankApp.app().setAppRunning(false);
                    break;
                default:
                    System.out.println("Invalid selection, returning to main menu!");
                    BankApp.app().getRouter().navigate("/bankscreen");
            }
        } catch (NumberFormatException e) {
            System.err.println("[INFO] - Input must be numbers only! Please re-enter your amount");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Shutting down application");
            BankApp.app().setAppRunning(false);
        }
    }
}

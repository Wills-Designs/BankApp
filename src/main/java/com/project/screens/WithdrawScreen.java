package com.project.screens;
import com.project.BankApp;
import com.project.users.AppUser;
import com.project.users.UserService;
import com.project.utilities.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/** withdraw screen where user inputs how much
 * they are removing from the account
 */
public class WithdrawScreen extends Screen {

    /**
     * creates a new screen, and sets the route
     * @param userService
     */
    public WithdrawScreen(UserService userService) {
        super("WithdrawlScreen", "/withdrawl");
    }

    /** overrides the render to display withdraw options,
     * after user inputs quantity it is checked to prevent overdrafts
     * then the record in accounts is updated
     */
    @Override
    public void render() {

        AppUser user = BankApp.app().getCurrentSession().getSessionUser();
        String balance = null;
        String withAmount;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "select current_balance from accounts where user_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, user.getId());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                balance = rs.getString(1);
            }

            System.out.println("\n\n\n\n\n\nPlease enter the amount to be withdrawn : ");
            System.out.print("\n Enter Quantity ->$");
            withAmount = BankApp.app().getConsole().readLine();
            double number = Double.parseDouble(withAmount);
            double currBalance = Double.parseDouble(balance);

            if (number > currBalance){
                System.out.println("Amount exceeds your current balance, of $" + currBalance + ", please enter a different amount");
                BankApp.app().getRouter().navigate("/withdrawl");
            }

            System.out.println("\n\n\n\n\n\n\n\n\nAre you sure you want to withdraw $" + number);

            System.out.println("[1] Yes");
            System.out.println("[2] Change amount");
            System.out.println("[3] Return to main menu");
            System.out.println("[3] Log Off\n\n\n");
            System.out.print("\n Enter Selection -> ");

            String userSelection = BankApp.app().getConsole().readLine();

            switch (userSelection) {
                case "1":
                    currBalance = currBalance - number;  // need to set currAmount as return value from parsing bufferreader

                    String sql2 = "update accounts set current_balance = ? where user_id = ?;";
                    PreparedStatement pstmt2 = conn.prepareStatement(sql2);
                    pstmt2.setDouble(1, currBalance);
                    pstmt2.setInt(2, user.getId());
                    pstmt2.executeUpdate();

                    System.out.print("\n $" + number + " successfully withdrawn, returning to main menu\n\n");
                    BankApp.app().getRouter().navigate("/bankscreen");
                    break;
                case "2":
                    System.out.println("");
                    BankApp.app().getRouter().navigate("/withdrawl");
                    break;
                case "3":
                    System.out.println("\n\n\n\n\n\n[LOG] - Navigating back to main menu");
                    BankApp.app().getRouter().navigate("/bankscreen");
                    break;
                case "4":
                    System.out.println("Exiting application");
                    BankApp.app().setAppRunning(false);
                    break;
                default:
                    System.out.println("Invalid selection!");
                    BankApp.app().getRouter().navigate("/bankscreen");
            }
        } catch (NumberFormatException e) {
            System.err.println("[INFO] - Input must be numbers only! Please re-enter your amount");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Shutting down application");
            BankApp.app().setAppRunning(false);
        }
    }
}

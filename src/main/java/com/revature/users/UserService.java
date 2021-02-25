package com.revature.users;
import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourcePersistenceException;
import com.revature.users.AppUser;
import com.revature.users.UserRole;
import com.revature.repos.UserRepository;
import com.revature.utilities.ConnectionFactory;
import com.revature.utilities.Session;

import java.sql.Connection;

import static com.revature.BankApp.app;

/** class that validates all the data a user inputs,
 *  ensures to trim white space and check that no values are null */
public class UserService {

    private UserRepository userRepo;

    /**
     * sets userRepo to itself
     * @param userRepo
     */
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * verifies the users credentials and checks for null entries
     * @param username
     * @param password
     */
    public void authenticate(String username, String password) {
        if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
            throw new InvalidRequestException("Invalid credentials provided (null or empty strings)!");
        }
        AppUser authUser = userRepo.findUserByUsernameAndPassword(username, password);
        if (authUser == null) {
            throw new AuthenticationException();
        }
        app().setCurrentSession(new Session(authUser, ConnectionFactory.getInstance().getConnection()));
    }

    /**
     * checks the input provided to ensure proper input information is passed in for the properties
     * also prevents users from setting the same username
     * @param newUser
     */
    public void register(AppUser newUser) {
        if (!isUserValid(newUser)){
            throw new InvalidRequestException("Invalid new user provided!");
        }
        if (userRepo.findUserByUsername(newUser.getUsername()) != null) {
            throw new ResourcePersistenceException("The provided username is already in use!");
        }
        newUser.setUserRole(UserRole.ACCOUNT_OWNER);
        userRepo.save(newUser);
    }

    /**
     * verifies none of the fields were set as null
     * @param user
     * @return
     */
    public boolean isUserValid(AppUser user) {
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        if (user.getPassword() == null || user.getPassword().trim().equals("")) return false;
        return true;
    }
}
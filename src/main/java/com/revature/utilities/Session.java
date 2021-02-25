package com.revature.utilities;

import com.revature.users.AppUser;
import com.revature.users.UserRole;
import java.sql.Connection;

/** class creates a session/connection that keeps the user logged in */
public class Session {

    private AppUser sessionUser;
    private Connection connection;

    /**
     * ensures no null values, does not establish session if either value is null
     * @param sessionUser
     * @param connection
     */
    public Session(AppUser sessionUser, Connection connection) {
        if (sessionUser == null || connection == null) {
            throw new ExceptionInInitializerError("Cannot establish user session, null values provided!");
        }

        this.sessionUser = sessionUser;
        this.connection = connection;

    }

    public AppUser getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(AppUser sessionUser) {
        this.sessionUser = sessionUser;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean isAdmin() {
        return (sessionUser.getUserRole().equals(UserRole.ADMIN));
    }

}
package com.project.repos;

import com.project.users.AppUser;
import com.project.users.UserRole;
import com.project.utilities.ConnectionFactory;
import com.project.utilities.LinkedList;

import java.sql.*;

import static com.project.BankApp.app;

/**
 * allows us to connect to the database and do various tasks
 */
public class UserRepository implements CrudRepository<AppUser>{

    private final String base = "SELECT * " +
            "FROM app_users au " +
            "JOIN user_roles ur " +
            "USING (role_id) ";

    /**
     * takes 2 strings in, username and passcode, and searches for it in database
     * @param username
     * @param password
     * @return
     */
    public AppUser findUserByUsernameAndPassword(String username, String password) {

        AppUser user = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = base + "WHERE username = ? AND password = ? ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            user = mapResultSet(rs).pop();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * takes a string of username in and looks for user with matching value
     * @param username
     * @return
     */
    public AppUser findUserByUsername(String username) {

        AppUser user = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = base + "WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            user = mapResultSet(rs).pop();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;

    }

    /**
     * connects to the database and saves a new user with the properties passed through when calling the method
     * @param newObj
     */
    @Override
    public void save(AppUser newObj) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "INSERT INTO app_users (username, password, first_name, last_name, role_id) " +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"user_id"});
            pstmt.setString(1, newObj.getUsername());
            pstmt.setString(2, newObj.getPassword());
            pstmt.setString(3, newObj.getFirstName());
            pstmt.setString(4, newObj.getLastName());
            pstmt.setInt(5, newObj.getUserRole().ordinal() + 1);

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted != 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                while (rs.next()) {
                    newObj.setId(rs.getInt("user_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * gives us a linked list of all the users in the table
     * @return
     */
    @Override
    public LinkedList<AppUser> findAll() {

        Connection conn = app().getCurrentSession().getConnection();
        LinkedList<AppUser> users = new LinkedList<>();

        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(base);
            users = mapResultSet(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public AppUser findById(int id) {
        System.err.println("Not implemented");
        return null;
    }

    @Override
    public boolean update(AppUser updatedObj) {
        System.err.println("Not implemented");
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        System.err.println("Not implemented");
        return false;
    }

    /**
     * sets values inside AppUSer
     * @param rs
     * @return
     * @throws SQLException
     */
    private LinkedList<AppUser> mapResultSet(ResultSet rs) throws SQLException {

        LinkedList<AppUser> users = new LinkedList<>();

        while(rs.next()) {
            AppUser user = new AppUser();
            user.setId(rs.getInt("user_id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setUserRole(UserRole.valueOf(rs.getString("role_name")));
            users.insert(user);
        }
        return users;
    }
}
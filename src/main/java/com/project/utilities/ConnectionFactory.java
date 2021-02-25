package com.project.utilities;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/** this class opens up a connection with the postgres database, by
 * reading our properties file
 * */
public class ConnectionFactory {

    private static ConnectionFactory connFactory = new ConnectionFactory();
    private Properties props = new Properties();

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * loads file into the file reader for setting up connection
     */
    private ConnectionFactory() {
        try {
            props.load(new FileReader("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionFactory getInstance() {
        return connFactory;
    }

    /** grabs our properties from the file, to establish a connection */
    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    props.getProperty("url"),
                    props.getProperty("admin-usr"),
                    props.getProperty("admin-pw")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
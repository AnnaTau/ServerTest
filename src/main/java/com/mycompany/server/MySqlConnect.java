/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 1
 */
public class MySqlConnect {
    private Connection conn;
    private String url = "jdbc:mysql://localhost:3306/mysql";
    private String driver = "com.mysql.jdbc.Driver";
    private String userName = "root"; 
    private String password = "root";

    public MySqlConnect() {
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            System.out.println("Connected to the database");
        } catch (InstantiationException ex) {
            Logger.getLogger(MySqlConnect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MySqlConnect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySqlConnect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MySqlConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void createIfNotExist (String db) {
        boolean executeQuery;
        try {
            executeQuery = conn.createStatement().execute("CREATE DATABASE IF NOT EXISTS "+db);
        } catch (SQLException e) {
            Logger.getLogger(MySqlConnect.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}

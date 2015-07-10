/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assignment4.products;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Harry89
 */
public class mycon {
    
      public  java.sql.Connection getConnection() {
        java.sql.Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.err.println("JDBC Driver Not Found: " + ex.getMessage());
        }

        try {
            String jdbc = "jdbc:mysql://ipro.lambton.on.ca/inventory";
            con = DriverManager.getConnection(jdbc, "products", "products");
        } catch (SQLException ex) {
            System.err.println("Failed to Connect: " + ex.getMessage());
        }
        return con;
    
}
}

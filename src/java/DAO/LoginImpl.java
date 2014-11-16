/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author hgindra
 */
public class LoginImpl {

    public boolean validCredentials(String user, String password) {

        String selectQuery = "SELECT * FROM LINKEDU.LOGIN ";
        selectQuery += "WHERE USERNAME = '" + user + "' and PASSWORD = '" + password + "'";

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");

        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int rowCount = 0;

        try {
            String myDB = "jdbc:derby://localhost:1527/LinkEDU";
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
            Statement stmt = DBConn.createStatement();

            ResultSet rs = stmt.executeQuery(selectQuery);

            if (rs.next()) {
                rs.close();
                DBConn.close();
                stmt.close();                
                return true;
            } else {
                rs.close();
                DBConn.close();
                stmt.close();
                return false;
            }
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        return false;
    }

    public char getAccountType(String username) {
        String selectQuery = " SELECT ACCTYPE FROM LINKEDU.USERINFO WHERE USERNAME='" + username + "'";
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");

        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        try {
            String myDB = "jdbc:derby://localhost:1527/LinkEDU";
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);
            if (rs.next()) {
                return (rs.getString("ACCTYPE").charAt(0));
            }
            rs.close();
            stmt.close();
            DBConn.close();
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        return 'N';
    }
}

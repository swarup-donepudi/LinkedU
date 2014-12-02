/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author hgindra
 */
public class LoginDAO extends AppDBInfoDAO{ 
    
    private Connection DBConn;
    
    public LoginDAO() throws SQLException{
        super();
    }
    
    public boolean validCredentials(String user, String password) {
        user=user.toLowerCase();
        String selectQuery = "SELECT * FROM LINKEDU.LOGIN ";
        selectQuery += "WHERE LOWER(USERNAME) = '" + user + "' and PASSWORD = '" + password + "'";

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        try {
            this.DBConn = DriverManager.getConnection(this.databaseURL,this.dbUserName,this.dbPassword);
            Statement stmt = this.DBConn.createStatement();

            ResultSet rs = stmt.executeQuery(selectQuery);

            if (rs.next()) {
                rs.close();
                this.DBConn.close();
                stmt.close();                
                return true;
            } else {
                rs.close();
                this.DBConn.close();
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
        username = username.toLowerCase();
        String selectQuery = " SELECT ACCTYPE FROM LINKEDU.USERINFO WHERE LOWER(USERNAME)='" + username + "'";
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");

        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        try {
            this.DBConn = DriverManager.getConnection(this.databaseURL,this.dbUserName,this.dbPassword);
            Statement stmt = this.DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);
            if (rs.next()) {
                return (rs.getString("ACCTYPE").charAt(0));
            }
            rs.close();
            stmt.close();
            this.DBConn.close();
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        return 'E';
    }
}

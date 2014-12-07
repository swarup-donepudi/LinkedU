/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import java.io.IOException;
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
    
    public boolean validCredentials(String user, String password) throws IOException {
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
            this.redirectToErrorPage();
        }
        return false;
    }

    public char getAccountType(String username) throws IOException {
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
            this.redirectToErrorPage();
        }
        return 'E';
    }
    
    public String verifyEmailID(String email) throws SQLException{
        email = email.toLowerCase();
        String username="";
        this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
        String selectStatement = "SELECT * FROM LINKEDU.USERINFO WHERE LOWER(EMAILID)='" + email + "'";
        Statement stmt = DBConn.createStatement();
        ResultSet rs = stmt.executeQuery(selectStatement);
        if (rs.next()) {
            username = rs.getString("USERNAME");
        }
        rs.close();
        this.DBConn.close();
        stmt.close();
        return username;
    }
    
    public int addVerificationDetails(String username, String verifyLink) throws IOException {
        int rowCount = 0;
        try {
            this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
            String insertString;
            Statement stmt = DBConn.createStatement();
                    insertString = "INSERT INTO LINKEDU.VERIFICATION_STRINGS VALUES ('"
                    + username
                    + "','" + verifyLink
                    + "')";
            rowCount = stmt.executeUpdate(insertString);
            System.out.println("insert string =" + insertString);
            this.DBConn.close();
        } catch (SQLException e) {
            this.redirectToErrorPage();
        }

        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully).
        // Else, insert failed.
        return rowCount;
    }
    
    public String verifyForgotPasswordLink(String verifyLink) throws SQLException{
        String username="";
        this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
        String selectStatement = "SELECT * FROM LINKEDU.VERIFICATION_STRINGS WHERE LINK_STRING='" + verifyLink + "'";
        Statement stmt = DBConn.createStatement();
        ResultSet rs = stmt.executeQuery(selectStatement);
        if (rs.next()) {
            username = rs.getString("USERNAME");
        }
        rs.close();
        this.DBConn.close();
        stmt.close();
        return username;        
    }
    
    public boolean deleteVerificationData(String username) throws SQLException{
        String user="";
        this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
        String selectStatement = "DELETE FROM LINKEDU.VERIFICATION_STRINGS WHERE USERNAME = '"+username+"'" ; 
        Statement stmt = DBConn.createStatement();
        int row = stmt.executeUpdate(selectStatement);
        this.DBConn.close();
        stmt.close();
        return false;         
    }
    
    public int changePasswordLogin(String username, String password) throws SQLException{
        String user="";
        this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
        String selectStatement = "UPDATE LINKEDU.LOGIN " 
                + " SET PASSWORD = '" + password
                +"' WHERE USERNAME = '" + username + "'"; 
        Statement stmt = DBConn.createStatement();
        int row = stmt.executeUpdate(selectStatement);        
        this.DBConn.close();
        stmt.close();
        return row;  
    }
}

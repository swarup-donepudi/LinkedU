/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.SignupBean;
import org.primefaces.model.DefaultStreamedContent;

/**
 *
 * @author harshit
 */
public class SignupDAO extends AppDBInfoDAO {

    private Connection DBConn;

    public SignupDAO() throws SQLException {
        super();
    }

    public boolean usernameAlreadyExists(String username) throws SQLException {
        username = username.toLowerCase();
        boolean usernameExists = false;
        this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
        String selectStatement = "SELECT * FROM LINKEDU.LOGIN WHERE LOWER(USERNAME)='" + username + "'";

        Statement stmt = DBConn.createStatement();
        ResultSet rs = stmt.executeQuery(selectStatement);

        if (rs.next()) {
            usernameExists = true;
        }
        rs.close();
        this.DBConn.close();
        stmt.close();
        return usernameExists;
    }
    
    
    public int insertVerificationDetails(String username, String verifyLink) throws IOException{
        int rowCount = 0;
        try {
            this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
            String insertString;            
            insertString = "INSERT INTO LINKEDU.VERIFICATION_STRINGS VALUES ('"
                    + username
                    + "','" + verifyLink
                    + "')";
            Statement stmt = DBConn.createStatement();
            rowCount = stmt.executeUpdate(insertString);
            this.DBConn.close();
        } catch (SQLException e) {
            this.redirectToErrorPage();
        }

        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully).
        // Else, insert failed.
        return rowCount;

    }
    
    
    
    public boolean emailAlreadyExits(String emailID) throws SQLException {
        emailID = emailID.toLowerCase();
        boolean emailExits=false;
        this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
        String selectStatement = "SELECT * FROM LINKEDU.USERINFO WHERE LOWER(EMAILID)='" + emailID + "'";
        
        Statement stmt = DBConn.createStatement();
        ResultSet rs = stmt.executeQuery(selectStatement);

        if (rs.next()) {
            emailExits = true;
        }
        rs.close();
        this.DBConn.close();
        stmt.close();
        return emailExits;
    }
    
    public boolean userAccStatus(String emailID) throws SQLException {
        emailID = emailID.toLowerCase();
        boolean accStatus=false;
        this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
        String selectStatement = "SELECT * FROM LINKEDU.USERINFO WHERE LOWER(EMAILID)='" + emailID + "'";
        
        Statement stmt = DBConn.createStatement();
        ResultSet rs = stmt.executeQuery(selectStatement);
        
        if (rs.next()) {
            if(rs.getString("ACC_STATUS").equals("I"))
                accStatus=true;
        }
        rs.close();
        this.DBConn.close();
        stmt.close();
        return accStatus;
    }
    
    public String checkLink(String randomString) throws SQLException{
        String username="";
        this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
        String selectStatement = "SELECT * FROM LINKEDU.VERIFICATION_STRINGS WHERE LINK_STRING ='" + randomString + "'";
        
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
    
    public boolean updateAccStatus(String username) throws SQLException{
        String user="";
        this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
        String selectStatement = "UPDATE LINKEDU.USERINFO " 
                + " SET ACC_STATUS = 'A'"
                +" WHERE USERNAME = '" + username + "'"; 
        Statement stmt = DBConn.createStatement();
        int row = stmt.executeUpdate(selectStatement);        
        this.DBConn.close();
        stmt.close();
        return false;         
    }
    
    
    

    public int createLoginAccount(SignupBean bean) {
        int rowCount = 0;
        try {
            this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
            String insertString;
            Statement stmt = DBConn.createStatement();
                    insertString = "INSERT INTO LINKEDU.LOGIN VALUES ('"
                    + bean.getUserName()
                    + "','" + bean.getPassword()
                    + "')";
            rowCount = stmt.executeUpdate(insertString);
            System.out.println("insert string =" + insertString);
            this.DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully).
        // Else, insert failed.
        return rowCount;

    }
    
    public int createUserAccount(SignupBean bean) {
        int rowCount = 0;
        try {
            this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
            String insertString;            
            insertString = "INSERT INTO LINKEDU.USERINFO VALUES ('"
                    + bean.getUserName()
                    + "','" + bean.getEmail().toLowerCase()
                    + "','" + bean.getAccountType()
                    + "','I')";
            Statement stmt = DBConn.createStatement();
            rowCount = stmt.executeUpdate(insertString);
            this.DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully).
        // Else, insert failed.
        return rowCount;

    }
    
    public ArrayList fetchALlImage() {
        ArrayList images = new ArrayList();
        try {
           this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
            String query = "Select * from LINKEDU.SPONSORED_UNIVERSITIES ";
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            byte[] file;
            DefaultStreamedContent allimages = null;
            while(rs.next()){
                file = rs.getBytes("IMAGES");
                allimages = new DefaultStreamedContent(new ByteArrayInputStream(file), "image/jpeg");
                images.add(allimages);
            }
            rs.close();
        } catch (SQLException ex) {           
        }
        return images;
    }

}

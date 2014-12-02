/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.SignupBean;

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
    
    
    

    public int createAccount(SignupBean bean) {
        int rowCount = 0;
        try {
            this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
            String insertString;
            Statement stmt = DBConn.createStatement();
            insertString = "INSERT INTO LINKEDU.USERINFO VALUES ('"
                    + bean.getUserName()
                    + "','" + bean.geteMail().toLowerCase()
                    + "','" + bean.getAccountType()
                    + "')";

            rowCount = stmt.executeUpdate(insertString);
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

}

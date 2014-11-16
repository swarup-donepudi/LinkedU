/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import model.SignupBean;

/**
 *
 * @author harshit
 */
public class SignupImpl {
    
    
    
    public int createProfile(SignupBean bean){
        
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
            String insertString;
            Statement stmt = DBConn.createStatement();
            insertString = "INSERT INTO LINKEDU.USERINFO VALUES ('"
                    + bean.getUserName()
                    + "','" + bean.geteMail()
                    + "','" + bean.getAccountType()
                    + "')";

            rowCount = stmt.executeUpdate(insertString);
            insertString = "INSERT INTO LINKEDU.LOGIN VALUES ('"
                    + bean.getUserName()
                    + "','" + bean.getPassword()
                    + "')";
            rowCount = stmt.executeUpdate(insertString);
            System.out.println("insert string =" + insertString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount;
        
    }
    
}

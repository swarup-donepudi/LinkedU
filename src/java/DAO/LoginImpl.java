/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author hgindra
 */
public class LoginImpl {
    
    public char loginQuery(String user, String password){
        char type = 0;
        String query = "SELECT * FROM LINKEDU.LOGIN ";
        query += "WHERE USERNAME = '" + user + "' and PASSWORD = '"+password+"'";
        type=checkUser(query);

        return type;
    }
    
    public char checkUser(String query){
        String type="";
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
           
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        ArrayList profile = new ArrayList();
        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/LinkEDU";
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
            Statement stmt = DBConn.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {

                
                type=typeQuery(rs.getString("USERNAME"));

                System.out.println("dbms complete");
            }
            rs.close();
            stmt.close();
              DBConn.close();
        } catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
       return type.charAt(0);
    }
    
    public String typeQuery(String name){
        String query=" SELECT ACCTYPE FROM LINKEDU.USERINFO WHERE USERNAME='"+name+"'" ;
        return getAccType(query);
        
    }
    
    public String getAccType(String query){
        String type="";
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
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                
                type=rs.getString("ACCTYPE");

                System.out.println("dbms complete");
            }
            rs.close();
            stmt.close();
              DBConn.close();
        } catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
       return type;
        
    }
    
}

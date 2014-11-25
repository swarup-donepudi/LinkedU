/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.MessageBean;

/**
 *
 * @author skdonep
 */
public class CommonDAO extends AppDBInfoDAO{
    private Connection DBConn;
    
    public CommonDAO(){
        super();
    }
    
    public String getEmailFromUserInfoTable(String username) {
        String emailFromUserInfo = "";
        String getEmailQuery = "SELECT EMAIL FROM LINKEDU.USERINFO WHERE USERNAME='" + username + "'";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(getEmailQuery);
            if (rs.next()) {
                emailFromUserInfo = rs.getString("EMAIL");
            }
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        return emailFromUserInfo;
    }
}

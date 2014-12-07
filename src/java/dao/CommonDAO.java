/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author skdonep
 */
public class CommonDAO extends AppDBInfoDAO {

    private Connection DBConn;

    public CommonDAO() {
        super();
    }

    public String getEmailFromUserInfoTable(String username) throws IOException {
        String emailFromUserInfo
                = username = username.toLowerCase();
        String getEmailQuery = "SELECT EMAILID FROM LINKEDU.USERINFO WHERE LOWER(USERNAME)='" + username + "'";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(getEmailQuery);
            if (rs.next()) {
                emailFromUserInfo = rs.getString("EMAILID");
            }
        } catch (SQLException e) {
            this.redirectToErrorPage();
        }
        return emailFromUserInfo;
    }

    public char getAccountStatusFromDB(String username) throws IOException {
        char accStatus = 'N';
        username = username.toLowerCase();
        String getEmailQuery = "SELECT ACC_STATUS FROM LINKEDU.USERINFO WHERE LOWER(USERNAME)='" + username + "'";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(getEmailQuery);
            if (rs.next()) {
                accStatus = rs.getString("ACC_STATUS").charAt(0);
            }
        } catch (SQLException e) {
            this.redirectToErrorPage();
        }
        return accStatus;
    }

    public char getAccountTypeFromDB(String username) throws IOException {
        char accType = 'N';
        username = username.toLowerCase();
        String getEmailQuery = "SELECT ACCTYPE FROM LINKEDU.USERINFO WHERE LOWER(USERNAME)='" + username + "'";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(getEmailQuery);
            if (rs.next()) {
                accType = rs.getString("ACCTYPE").charAt(0);
            }
        } catch (SQLException e) {
            this.redirectToErrorPage();
        }
        return accType;
    }
}

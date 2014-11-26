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

/**
 *
 * @author skdonep
 */
public class CommonDAO extends AppDBInfoDAO {

    private Connection DBConn;

    public CommonDAO() {
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

    public int addToWatchListInDB(String wlOwner, String wlEntry) throws SQLException {
        this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
        Statement stmt = DBConn.createStatement();
        String insertStatement = "INSERT INTO LINKEDU.WATCH_LIST VALUES('" + wlOwner + "','" + wlEntry + "')";
        int rowCount = stmt.executeUpdate(insertStatement);
        return rowCount;
    }

    public boolean userNotInWatchListInDB(String targetWL, String wlEntry) {
        boolean userNotInWatchList = true;
        String selectQuery = "SELECT * FROM LINKEDU.WATCH_LIST WHERE OWNER = '" + targetWL
                + "' AND WL_ENTRY='" + wlEntry + "'";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();

            ResultSet rs = stmt.executeQuery(selectQuery);

            if (rs.next()) {
                userNotInWatchList = false;
            }
            rs.close();
            this.DBConn.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        return userNotInWatchList;
    }

    public ArrayList<String> retrieveUserrWatchListFromDB(String wlOwner) {
        String selectQuery = "SELECT * FROM LINKEDU.WATCH_LIST WHERE OWNER = '" + wlOwner + "'";
        ArrayList<String> watchList = new ArrayList<>();
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);
            int i = 0;
            while (rs.next()) {
                watchList.add(rs.getString("WL_ENTRY"));
                i++;
            }
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        return watchList;
    }
}

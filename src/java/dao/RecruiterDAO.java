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
import java.util.ArrayList;
import model.RecruiterProfile;
import model.WatchListItem;

/**
 *
 * @author skdonep
 */
public class RecruiterDAO extends AppDBInfoDAO {

    private Connection DBConn;

    public RecruiterDAO() {
        super();
    }

    public boolean recruiterHasProfile(String username) throws SQLException {
        boolean recruiterHasProfile = false;
        username=username.toLowerCase();
        String selectQuery = "SELECT * FROM LINKEDU.RECRUITER_PROFILE WHERE LOWER(USERNAME) = '" + username + "'";

        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            try (Statement stmt = this.DBConn.createStatement()) {
                ResultSet rs = stmt.executeQuery(selectQuery);

                if (rs.next()) {
                    recruiterHasProfile = true;
                }
                rs.close();
                this.DBConn.close();
            }
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select in RecruiterHasProfile method");
        }
        return recruiterHasProfile;
    }

    public RecruiterProfile fetchRecruiterProfile(String username) {
        RecruiterProfile recruiterProfile = new RecruiterProfile();
        username = username.toLowerCase();
        String selectQuery = "SELECT * FROM LINKEDU.RECRUITER_PROFILE WHERE LOWER(USERNAME) = '" + username + "'";

        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();

            ResultSet rs = stmt.executeQuery(selectQuery);

            while (rs.next()) {
                recruiterProfile.setFname(rs.getString("FIRST_NAME"));
                recruiterProfile.setLname(rs.getString("LAST_NAME"));
                recruiterProfile.setGender(rs.getString("GENDER").charAt(0));
                recruiterProfile.setInstName(rs.getString("UNIVERSITY"));
                recruiterProfile.setInstURL(rs.getString("UNIV_URL"));
                recruiterProfile.setEmail(rs.getString("EMAIL"));
                recruiterProfile.setPrimaryPhNum(rs.getString("PRIMARY_PHONE"));
                recruiterProfile.setSecondaryPhNum(rs.getString("SECONDARY_PHONE"));
                recruiterProfile.setCountry(rs.getString("COUNTRY"));
                recruiterProfile.setState(rs.getString("STATE"));
                recruiterProfile.setCity(rs.getString("CITY"));
                recruiterProfile.setUsername(rs.getString("USERNAME"));
            }

            rs.close();
            this.DBConn.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        return recruiterProfile;
    }

    public void updateRecruiterProfile(RecruiterProfile recruiterProfile, String username) {
        String updateQuery = "UPDATE LINKEDU.RECRUITER_PROFILE SET FIRST_NAME = '"
                + recruiterProfile.getFname() + "', "
                + "LAST_NAME = '"
                + recruiterProfile.getLname() + "', "
                + "GENDER = '"
                + recruiterProfile.getGender() + "', "
                + "UNIVERSITY = '"
                + recruiterProfile.getInstName() + "', "
                + "UNIV_URL = '"
                + recruiterProfile.getInstURL() + "', "
                + "PRIMARY_PHONE = '"
                + recruiterProfile.getPrimaryPhNum() + "', "
                + "SECONDARY_PHONE = '"
                + recruiterProfile.getSecondaryPhNum() + "', "
                + "COUNTRY = '"
                + recruiterProfile.getCountry() + "', "
                + "STATE = '"
                + recruiterProfile.getState() + "', "
                + "CITY = '"
                + recruiterProfile.getCity() + "', "
                + "EMAIL = '"
                + recruiterProfile.getEmail()
                + "' WHERE USERNAME='" + username + "'";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            try (Statement stmt = this.DBConn.createStatement()) {
                stmt.executeUpdate(updateQuery);
                this.DBConn.close();
            }
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
    }

    public int createRecruiterProfile(RecruiterProfile recruiterProfile, String username) {
        int rowsInserted = 0;
        CommonDAO coomonDB = new CommonDAO();
        String emailFromUserInfo = coomonDB.getEmailFromUserInfoTable(username);
        String insertQuery = "INSERT INTO LINKEDU.RECRUITER_PROFILE(FIRST_NAME,"
                + "LAST_NAME,"
                + "GENDER,"
                + "UNIVERSITY,"
                + "UNIV_URL,"
                + "PRIMARY_PHONE,"
                + "SECONDARY_PHONE,"
                + "COUNTRY,"
                + "STATE,"
                + "CITY,"
                + "USERNAME,"
                + "EMAIL)"
                + "VALUES('"
                + recruiterProfile.getFname() + "','"
                + recruiterProfile.getLname() + "','"
                + recruiterProfile.getGender() + "','"
                + recruiterProfile.getInstName() + "','"
                + recruiterProfile.getInstURL() + "','"
                + recruiterProfile.getPrimaryPhNum() + "','"
                + recruiterProfile.getSecondaryPhNum() + "','"
                + recruiterProfile.getCountry() + "','"
                + recruiterProfile.getState() + "','"
                + recruiterProfile.getCity() + "','"
                + username + "','"
                + emailFromUserInfo + "')";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();
            rowsInserted = stmt.executeUpdate(insertQuery);
            this.DBConn.close();
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        return rowsInserted;
    }

    public boolean studentNotInWatchListInDB(String wlOwner, String wlItem) {
        boolean studentNotInWatchList = true;
        String selectQuery = "SELECT * FROM LINKEDU.STUDENT_WATCHLIST WHERE OWNER = '" + wlOwner
                + "' AND WL_ITEM='" + wlItem + "'";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();

            ResultSet rs = stmt.executeQuery(selectQuery);

            if (rs.next()) {
                studentNotInWatchList = false;
            }
            rs.close();
            this.DBConn.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        return studentNotInWatchList;
    }

    public int addStudentToWatchListInDB(String wlOwner, String wlEntry) throws SQLException {
        this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
        Statement stmt = DBConn.createStatement();
        String insertStatement = "INSERT INTO LINKEDU.WATCH_LIST VALUES('" + wlOwner + "','" + wlEntry + "')";
        int rowCount = stmt.executeUpdate(insertStatement);
        return rowCount;
    }

    public void retrieveRecruiterWatchListFromDB(String wlOwner, ArrayList<WatchListItem> recruiterWatchList) {
                String selectQuery = "SELECT * FROM LINKEDU.RECRUITER_WATCHLIST WHERE WL_OWNER = '" + wlOwner + "'";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();

            ResultSet rs = stmt.executeQuery(selectQuery);

            char wlItemType;
            while (rs.next()) {
                WatchListItem wli = new WatchListItem();
                wli.setWlItem(rs.getString("WL_ITEM"));
                    wli.setItemFname("ITEM_FNAME");
                    wli.setItemLname("ITEM_LNAME");
                    recruiterWatchList.add(wli);
            }
            rs.close();
            this.DBConn.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
    }
}

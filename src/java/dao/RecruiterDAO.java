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
import model.StudentProfile;
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

    public boolean recruiterHasProfile(String username) throws SQLException, IOException {
        boolean recruiterHasProfile = false;
        username = username.toLowerCase();
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
            this.redirectToErrorPage();
        }
        return recruiterHasProfile;
    }

    public RecruiterProfile fetchRecruiterProfile(String username) throws IOException {
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
            this.redirectToErrorPage();
        }
        return recruiterProfile;
    }

    public void updateRecruiterProfile(RecruiterProfile recruiterProfile, String username) throws IOException {
        String updateQuery = "UPDATE LINKEDU.RECRUITER_PROFILE SET FIRST_NAME = '"
                + recruiterProfile.getFname() + "', "
                + "LAST_NAME = '"
                + recruiterProfile.getLname() + "', "
                + "INST_NAME = '"
                + recruiterProfile.getInstName() + "', "
                + "DEPT_NAME = '"
                + recruiterProfile.getDeptName() + "', "
                + "FB_PAGE = '"
                + recruiterProfile.getInstFBPage() + "', "
                + "TWITTER_HANDLE = '"
                + recruiterProfile.getTwitterHandle() + "', "
                + "EMAIL = '"
                + recruiterProfile.getEmail()
                + recruiterProfile.getEmail() + "', "
                + "LINKEDU_REASON = '"
                + recruiterProfile.getReasonForLinkEDU()
                + "PROFILE_IMAGE = '"
                + recruiterProfile.getProfileImage()
                + "COUNTRY_DIALING_CODE = '"
                + recruiterProfile.getCountryDialingCode() + "', "
                + "PRIMARY_PH = '"
                + recruiterProfile.getPrimaryPhNum() + "', "
                + "SECONDARY_PH = '"
                + recruiterProfile.getSecondaryPhNum() + "', "
                + "CITY = '"
                + recruiterProfile.getCity()
                + "STATE = '"
                + recruiterProfile.getState() + "', "
                + "COUNTRY = '"
                + recruiterProfile.getCountry() + "', "
                + "' WHERE USERNAME='" + username + "'";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            try (Statement stmt = this.DBConn.createStatement()) {
                stmt.executeUpdate(updateQuery);
                this.DBConn.close();
            }
        } catch (SQLException e) {
            this.redirectToErrorPage();
        }
    }

    public int createRecruiterProfile(RecruiterProfile recruiterProfile, String username) throws IOException {
        int rowsInserted = 0;
        CommonDAO coomonDB = new CommonDAO();
        String emailFromUserInfo = coomonDB.getEmailFromUserInfoTable(username);
        String insertQuery = "INSERT INTO LINKEDU.RECRUITER_PROFILE(USERNAME,"
                + "FIRST_NAME,"
                + "LAST_NAME,"
                + "INST_NAME,"
                + "DEPT_NAME,"
                + "FB_PAGE,"
                + "TWITTER_HANDLE,"
                + "EMAIL,"
                + "LINKEDU_REASON,"
                //+ "PROFILE_IMAGE,"
                + "COUNTRY_DIALING_CODE,"
                + "PRIMARY_PH,"
                + "SECONDARY_PH,"
                + "CITY,"
                + "STATE,"
                + "COUNTRY)"
                + " VALUES('"
                + recruiterProfile.getUsername()+ "','"
                + recruiterProfile.getFname() + "','"
                + recruiterProfile.getLname() + "','"
                + recruiterProfile.getInstName() + "','"
                + recruiterProfile.getDeptName() + "','"
                + recruiterProfile.getInstFBPage() + "','"
                + recruiterProfile.getTwitterHandle() + "','"
                + recruiterProfile.getEmail() + "','"
                + recruiterProfile.getReasonForLinkEDU() + "','"
                //+ recruiterProfile.getProfileImage() + "','"
                + recruiterProfile.getCountryDialingCode() + "','"
                + recruiterProfile.getPrimaryPhNum() + "','"
                + recruiterProfile.getSecondaryPhNum() + "','"
                + recruiterProfile.getCity() + "','"
                + recruiterProfile.getState() + "','"
                + recruiterProfile.getCountry() + "')";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();
            rowsInserted = stmt.executeUpdate(insertQuery);
            this.DBConn.close();
        } catch (SQLException e) {
            this.redirectToErrorPage();
        }
        return rowsInserted;
    }

    public boolean studentNotInWatchListInDB(String wlOwner, String wlItem) throws IOException {
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
            this.redirectToErrorPage();
        }
        return studentNotInWatchList;
    }

    public int addStudentToWatchListInDB(String recruiter, StudentProfile ssc) throws SQLException {
        this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
        Statement stmt = DBConn.createStatement();
        String insertStatement = "INSERT INTO LINKEDU.RECRUITER_WATCHLIST VALUES('"
                + recruiter + "','"
                + ssc.getUsername()
                + "','" + ssc.getFname()
                + "','" + ssc.getLname() + "')";
        int rowCount = stmt.executeUpdate(insertStatement);
        return rowCount;
    }

    public void retrieveRecruiterWatchListFromDB(String wlOwner, ArrayList<WatchListItem> recruiterWatchList) throws IOException {
        String selectQuery = "SELECT * FROM LINKEDU.RECRUITER_WATCHLIST WHERE WL_OWNER = '" + wlOwner + "'";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();

            ResultSet rs = stmt.executeQuery(selectQuery);

            char wlItemType;
            while (rs.next()) {
                WatchListItem wli = new WatchListItem();
                wli.setWlItem(rs.getString("WL_ITEM"));
                wli.setItemFname(rs.getString("ITEM_FNAME"));
                wli.setItemLname(rs.getString("ITEM_LNAME"));
                recruiterWatchList.add(wli);
            }
            rs.close();
            this.DBConn.close();
            stmt.close();
        } catch (SQLException e) {
            this.redirectToErrorPage();
        }
    }

    public ArrayList getStudentListFromDB(String wlOwner) throws IOException {
        String selectQuery = "SELECT * FROM LINKEDU.RECRUITER_WATCHLIST WHERE WL_OWNER = '" + wlOwner + "'";
        ArrayList names = new ArrayList();
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);
            while (rs.next()) {
                String name = rs.getString("WL_ITEM");
                names.add(name);
            }
            rs.close();
            this.DBConn.close();
            stmt.close();
        } catch (SQLException e) {
            this.redirectToErrorPage();
        }
        return names;
    }
}

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
import model.RecruiterProfile;

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

        String selectQuery = "SELECT * FROM LINKEDU.RECRUITER_PROFILE WHERE USERNAME = '" + username + "'";

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

        String selectQuery = "SELECT * FROM LINKEDU.RECRUITER_PROFILE WHERE USERNAME = '" + username + "'";

        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();

            ResultSet rs = stmt.executeQuery(selectQuery);

            while (rs.next()) {
                recruiterProfile.setfName(rs.getString("FIRST_NAME"));
                recruiterProfile.setlName(rs.getString("LAST_NAME"));
                recruiterProfile.setGender(rs.getString("GENDER").charAt(0));
                recruiterProfile.setUnivName(rs.getString("UNIVERSITY"));
                recruiterProfile.setUnivURL(rs.getString("UNIV_URL"));
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
                + recruiterProfile.getfName() + "', "
                + "LAST_NAME = '"
                + recruiterProfile.getlName() + "', "
                + "GENDER = '"
                + recruiterProfile.getGender() + "', "
                + "UNIVERSITY = '"
                + recruiterProfile.getUnivName() + "', "
                + "UNIV_URL = '"
                + recruiterProfile.getUnivURL() + "', "
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
        int rowsInserted=0;
        CommonDAO coomonDB= new CommonDAO();
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
                + recruiterProfile.getfName() + "','"
                + recruiterProfile.getlName() + "','"
                + recruiterProfile.getGender() + "','"
                + recruiterProfile.getUnivName() + "','"
                + recruiterProfile.getUnivURL() + "','"
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
            rowsInserted=stmt.executeUpdate(insertQuery);
            this.DBConn.close();
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        return rowsInserted;
    }

    

    public ArrayList<String> retrieveRecruiterWatchList(String username) {
        String selectQuery = "SELECT * FROM LINKEDU.WATCH_LIST WHERE USERNAME = '" + username + "'";
        ArrayList<String> watchList = new ArrayList<String>();
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);
            int i = 0;
            while (rs.next()) {
                watchList.add(rs.getString("LIST_ITEM"));
                i++;
            }
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        return watchList;
    }

    public boolean studentNotInWatchList(String recruiterUsername, String studentUsername) {
        boolean studentNotInWatchList = true;

        String selectQuery = "SELECT * FROM LINKEDU.RECRUITERWATCHLIST WHERE RECRUITERUSERNAME = '" + recruiterUsername
                + "' AND STUDENTUSERNAME='" + studentUsername + "'";

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
}

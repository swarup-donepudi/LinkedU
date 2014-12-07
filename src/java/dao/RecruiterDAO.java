/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.RecruiterProfile;
import model.StudentProfile;
import model.WatchListItem;
import org.primefaces.model.DefaultStreamedContent;

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
            System.err.println("ERROR: Problems with SQL select in RecruiterHasProfile method");
        }
        return recruiterHasProfile;
    }

    public RecruiterProfile fetchRecruiterProfile(String username) throws SQLException {
        RecruiterProfile recruiterProfile = new RecruiterProfile();
        username = username.toLowerCase();
        String selectQuery = "SELECT * FROM LINKEDU.RECRUITER_PROFILE WHERE LOWER(USERNAME) = '" + username + "'";

        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();
            byte[] displayImg;
            DefaultStreamedContent displayImage = null;

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
                displayImg = rs.getBytes("UNIVERSITY_IMAGE");
                if (displayImg != null) {
                    displayImage = new DefaultStreamedContent(new ByteArrayInputStream(displayImg), "image/jpeg");
                }
            }
        recruiterProfile.setImageDisplay(displayImage);

        rs.close();
        this.DBConn.close();
        stmt.close();

    }
    catch (SQLException e

    
        ) {
            System.err.println("ERROR: Problems with SQL select");
        e.printStackTrace();
    }
    return recruiterProfile ;
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
                wli.setItemFname(rs.getString("ITEM_FNAME"));
                wli.setItemLname(rs.getString("ITEM_LNAME"));
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

    public ArrayList getStudentListFromDB(String wlOwner) {
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
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        return names;
    }

    public int uploadImageToDB(RecruiterProfile recPro) throws SQLException, IOException {
        this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);        
        InputStream f2 = recPro.getImageUpload().getInputstream();
        String query = "UPDATE LINKEDU.recruiter_profile SET university_image = ? WHERE username = ?";
        int rowCount;
        try (PreparedStatement ps = this.DBConn.prepareStatement(query)) {
            ps.setBinaryStream(1, f2);
            ps.setString(2, recPro.username);
            rowCount = ps.executeUpdate();
//        String selectSQL = "SELECT university_image FROM LINKEDU.recruiter_profile WHERE username = ?";
//        ps = this.DBConn.prepareStatement(selectSQL);  
//        ps.setString(1, username);
        }
        return rowCount;
    }
}

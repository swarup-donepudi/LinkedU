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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import model.StudentProfile;
import model.WatchListItem;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author skdonep
 */
public class StudentDAO extends AppDBInfoDAO {

    private Connection DBConn;

    public StudentDAO() {
        super();
    }

    public boolean studentHasProfile(String username) throws SQLException, IOException {
        boolean studentHasProfile = false;

        String selectQuery = "SELECT * FROM LINKEDU.STUDENT_PROFILE WHERE LOWER(USERNAME) = '" + username.toLowerCase() + "'";

        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();

            ResultSet rs = stmt.executeQuery(selectQuery);

            if (rs.next()) {
                studentHasProfile = true;
            }
            rs.close();
            this.DBConn.close();
            stmt.close();
        } catch (SQLException e) {
            this.redirectToErrorPage();
        }

        return studentHasProfile;
    }

    public StudentProfile fetchStudentProfile(String username) throws ParseException, IOException {
        StudentProfile studentProfile = new StudentProfile();

        String selectQuery = "SELECT * FROM LINKEDU.STUDENT_PROFILE WHERE LOWER(USERNAME) = '" + username.toLowerCase() + "'";

        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();
            byte[] profileImg;
            byte[] resume;
            ResultSet rs = stmt.executeQuery(selectQuery);

            while (rs.next()) {
                studentProfile.setFname(rs.getString("FIRST_NAME"));
                studentProfile.setLname(rs.getString("LAST_NAME"));
                studentProfile.setGender(rs.getString("GENDER").charAt(0));
                if (rs.getString("DOB") != null) {
                    studentProfile.setDob(new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH).parse(rs.getString("DOB")));
                } else {
                    studentProfile.setDob(null);
                }
                studentProfile.setHighestDegree(rs.getString("HIGHEST_DEGREE"));
                studentProfile.setGpa(rs.getDouble("GPA"));
                studentProfile.setIelts(rs.getDouble("IELTS"));
                studentProfile.setEmail(rs.getString("EMAILID"));
                studentProfile.setACT(rs.getInt("ACT"));
                studentProfile.setSAT(rs.getInt("SAT"));
                studentProfile.setTOEFL(rs.getInt("TOEFL"));
                studentProfile.setGRE(rs.getInt("GRE"));
                if (rs.getString("PREFERRED_UNIVS") != null) {
                    studentProfile.setPreferredInsts(this.convertStringToList(rs.getString("PREFERRED_UNIVS")));
                } else {
                    studentProfile.setPreferredInsts(null);
                }
                if (rs.getString("PREFERRED_PROGRAMS") != null) {
                    studentProfile.setPreferredPrograms(this.convertStringToList(rs.getString("PREFERRED_PROGRAMS")));
                } else {
                    studentProfile.setPreferredPrograms(null);
                }
                studentProfile.setPrimaryPhNum(rs.getString("PRIMARY_PHONE"));
                studentProfile.setCountryDialingCode(rs.getString("COUNTRY_DIALING_CODE"));
                studentProfile.setSecondaryPhNum(rs.getString("SECONDARY_PHONE"));
                studentProfile.setCountry(rs.getString("COUNTRY"));
                studentProfile.setState(rs.getString("STATE"));
                studentProfile.setCity(rs.getString("CITY"));
                studentProfile.setUsername(rs.getString("USERNAME"));
                if (rs.getString("Youtube_Link") != null) {
                    studentProfile.setYoutubeLink(rs.getString("Youtube_Link"));
                }
                profileImg = rs.getBytes("PROFILE_IMAGE");
                resume = rs.getBytes("RESUME");
                if (resume != null) {
                    studentProfile.setDownloadResume(this.binaryToDefaultStreamedContent(resume, "pdf/docx"));
                }
                if (profileImg != null) {
                    studentProfile.setImageDisplay(this.binaryToDefaultStreamedContent(profileImg, "image/jpeg"));
                }
            }
            rs.close();
            this.DBConn.close();
            stmt.close();

        } catch (SQLException e) {
            this.redirectToErrorPage();
        }
        return studentProfile;
    }

    public DefaultStreamedContent downloadResume(String username) throws IOException {
        byte[] resume;
        DefaultStreamedContent resumeDownload = null;
        String selectQuery = "SELECT * FROM LINKEDU.STUDENT_PROFILE WHERE USERNAME = '" + username + "'";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();

            ResultSet rs = stmt.executeQuery(selectQuery);
            while (rs.next()) {
                if (rs.getBytes("resume") != null) {
                    resume = rs.getBytes("RESUME");
                    resumeDownload = new DefaultStreamedContent(new ByteArrayInputStream(resume), "application/pdf", "downloaded_primefaces.pdf");
                }
                rs.close();
                this.DBConn.close();
                stmt.close();
            }
        } catch (SQLException e) {
            this.redirectToErrorPage();
        }
        return resumeDownload;

    }

    public void updateStudentProfile(StudentProfile studentProfile, String username) throws IOException {
        String updateQuery = "UPDATE LINKEDU.STUDENT_PROFILE SET FIRST_NAME = '"
                + studentProfile.getFname() + "', "
                + "LAST_NAME = '"
                + studentProfile.getLname() + "', "
                + "GENDER = '"
                + studentProfile.getGender() + "', "
                + "DOB = ";
        if (studentProfile.getDob() == null) {
            updateQuery += "null,HIGHEST_DEGREE = '";
        } else {
            updateQuery += "" + studentProfile.getDob() + ",HIGHEST_DEGREE = '";
        }
        updateQuery += studentProfile.getHighestDegree() + "', "
                + "GPA = "
                + studentProfile.getGpa() + ", "
                + "SAT = "
                + studentProfile.getSAT() + ", "
                + "ACT = "
                + studentProfile.getACT() + ", "
                + "TOEFL = "
                + studentProfile.getTOEFL() + ", "
                + "GRE = "
                + studentProfile.getGRE() + ", "
                + "IELTS = "
                + studentProfile.getIelts() + ", "
                + "CERTIFICATIONS = '"
                + studentProfile.getCeritifications() + "', "
                + "EMAILID = '"
                + studentProfile.getEmail() + "', "
                + "PREFERRED_PROGRAMS = ";
        if (studentProfile.getPreferredPrograms() == null) {
            updateQuery += "null,PREFERRED_UNIVS = ";
        } else {
            updateQuery += "'" + this.convertListtoString(studentProfile.getPreferredPrograms()) + "',PREFERRED_UNIVS = ";
        }
        if (studentProfile.getPreferredInsts() == null) {
            updateQuery += "null,";
        } else {
            updateQuery += "'" + this.convertListtoString(studentProfile.getPreferredInsts()) + "',";
        }
        updateQuery += "PRIMARY_PHONE = '"
                + studentProfile.getPrimaryPhNum() + "', "
                + "SECONDARY_PHONE = '"
                + studentProfile.getSecondaryPhNum() + "', "
                + "COUNTRY = '"
                + studentProfile.getCountry() + "', "
                + "STATE = '"
                + studentProfile.getState() + "', "
                + "CITY = '"
                + studentProfile.getCity() + "', "
                + "YOUTUBE_LINK = '"
                + studentProfile.getYoutubeLink() + "' "
                + "WHERE LOWER(USERNAME)='" + username.toLowerCase() + "'";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();
            stmt.execute(updateQuery);
            this.DBConn.close();
            stmt.close();
        } catch (SQLException e) {
            this.redirectToErrorPage();
        }
    }

    public void createStudentProfile(StudentProfile studentProfile, String username) throws IOException {
        CommonDAO coomonDB = new CommonDAO();
        UploadedFile profileImageUpload = studentProfile.getImageUpload();
        InputStream profileImage = null;
        if (profileImageUpload != null) {
            profileImage = studentProfile.getImageUpload().getInputstream();
        }
        UploadedFile resumeUpload = studentProfile.getResume();
        InputStream resume = null;
        if (resumeUpload != null) {
            resume = studentProfile.getResume().getInputstream();
        }
        String emailFromUserInfo = coomonDB.getEmailFromUserInfoTable(username);
        String insertQuery = "INSERT INTO LINKEDU.STUDENT_PROFILE(FIRST_NAME,"
                + "LAST_NAME,"
                + "GENDER,"
                + "DOB,"
                + "HIGHEST_DEGREE,"
                + "GPA,"
                + "SAT,"
                + "ACT,"
                + "TOEFL,"
                + "GRE,"
                + "IELTS,"
                + "CERTIFICATIONS,"
                + "EMAILID,"
                + "PREFERRED_PROGRAMS,"
                + "PREFERRED_UNIVS,"
                + "PRIMARY_PHONE,"
                + "SECONDARY_PHONE,"
                + "COUNTRY,"
                + "STATE,"
                + "CITY,"
                + "YOUTUBE_LINK,"
                + "USERNAME"
                + ") "
                + "VALUES('"
                + studentProfile.getFname() + "','"
                + studentProfile.getLname() + "','"
                + studentProfile.getGender() + "',";
        if (studentProfile.getDob() == null) {
            insertQuery += "null,'";
        } else {
            insertQuery += "'" + studentProfile.getDob() + "','";
        }
        insertQuery += studentProfile.getHighestDegree() + "',"
                + studentProfile.getGpa() + ","
                + studentProfile.getSAT() + ","
                + studentProfile.getACT() + ","
                + studentProfile.getTOEFL() + ","
                + studentProfile.getGRE() + ","
                + studentProfile.getIelts() + ",'"
                + studentProfile.getCeritifications() + "','"
                + emailFromUserInfo + "',";
        if (studentProfile.getPreferredPrograms() == null) {
            insertQuery += "null,";
        } else {
            insertQuery += "'" + this.convertListtoString(studentProfile.getPreferredPrograms()) + "',";
        }
        if (studentProfile.getPreferredInsts() == null) {
            insertQuery += "null,'";
        } else {
            insertQuery += "'" + this.convertListtoString(studentProfile.getPreferredInsts()) + "','";
        }
        insertQuery += studentProfile.getPrimaryPhNum() + "','"
                + studentProfile.getSecondaryPhNum() + "','"
                + studentProfile.getCountry() + "','"
                + studentProfile.getState() + "','"
                + studentProfile.getCity() + "','"
                + studentProfile.getYoutubeLink() + "','"
                + username + "')";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();
            stmt.execute(insertQuery);
            this.DBConn.close();
        } catch (SQLException e) {
            this.redirectToErrorPage();
        }
    }

    public String convertListtoString(List<String> list) {
        String convertedString = null;
        Iterator<String> iterator = list.iterator();
        if (iterator.hasNext()) {
            convertedString = iterator.next();
        }
        while (iterator.hasNext()) {
            convertedString += ";" + iterator.next();
        }
        return convertedString;
    }

    public List<String> convertStringToList(String delimitedString) {
        List<String> convertedList = new ArrayList<>();
        convertedList.addAll(Arrays.asList(delimitedString.split(";")));
        return convertedList;
    }

    public ArrayList<String> retrieveStudentWatchListRecruitersFromDB(String wlOwner) throws IOException {
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
            this.redirectToErrorPage();
        }
        return watchList;
    }

    public ArrayList<String> retrieveStudentWatchListUniversitiesFromDB(String wlOwner) throws IOException {
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
            this.redirectToErrorPage();
        }
        return watchList;
    }

    public boolean recruiterNotInWatchListInDB(String wlOwner, String wlItem) throws IOException {
        boolean userNotInWatchList = true;
        String selectQuery = "SELECT * FROM LINKEDU.STUDENT_WATCHLIST WHERE LOWER(WL_OWNER) = '" + wlOwner.toLowerCase()
                + "' AND LOWER(WL_ITEM)='" + wlItem.toLowerCase() + "'";
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
            this.redirectToErrorPage();
        }
        return userNotInWatchList;
    }

    public boolean institutionNotInWatchListInDB(String wlOwner, String wlItem) throws IOException {
        boolean universityNotInWatchList = true;
        String selectQuery = "SELECT * FROM LINKEDU.STUDENT_WATCHLIST WHERE LOWER(WL_OWNER) = '" + wlOwner.toLowerCase()
                + "' AND LOWER(WL_ITEM)='" + wlItem.toLowerCase() + "'";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();

            ResultSet rs = stmt.executeQuery(selectQuery);

            if (rs.next()) {
                universityNotInWatchList = false;
            }
            rs.close();
            this.DBConn.close();
            stmt.close();
        } catch (SQLException e) {
            this.redirectToErrorPage();
        }
        return universityNotInWatchList;
    }

    public int addUniversityToWatchListInDB(String wlOwner, String wlEntry, String wlLname) throws SQLException {
        this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
        Statement stmt = DBConn.createStatement();
        String insertStatement = "INSERT INTO LINKEDU.STUDENT_WATCHLIST VALUES('" + wlOwner + "','" + wlEntry + "',NULL,+'" + wlLname + "','U')";
        int rowCount = stmt.executeUpdate(insertStatement);
        return rowCount;
    }

    public int addRecruiterToWatchListInDB(String wlOwner, String wlItem, String itemFname, String itemLname) throws SQLException {
        this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
        Statement stmt = DBConn.createStatement();
        String insertStatement = "INSERT INTO LINKEDU.STUDENT_WATCHLIST VALUES('" + wlOwner + "','" + wlItem + "','" + itemFname + "','" + itemLname + "','R')";
        int rowCount = stmt.executeUpdate(insertStatement);
        return rowCount;
    }

    public void retrieveStudenteWatchListFromDB(String wlOwner,
            ArrayList<WatchListItem> studentWatchListInstitutions,
            ArrayList<WatchListItem> studentWatchListRecruiters) throws IOException {
        String selectQuery = "SELECT * FROM LINKEDU.STUDENT_WATCHLIST WHERE LOWER(WL_OWNER) = '" + wlOwner.toLowerCase() + "'";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();

            ResultSet rs = stmt.executeQuery(selectQuery);

            char wlItemType;
            while (rs.next()) {
                WatchListItem wli = new WatchListItem();
                wli.setWlItem(rs.getString("WL_ITEM"));
                if (rs.getString("ITEM_TYPE").charAt(0) == 'R') {
                    wli.setItemFname(rs.getString("ITEM_FNAME"));
                    wli.setItemLname(rs.getString("ITEM_LNAME"));
                    studentWatchListRecruiters.add(wli);
                } else {
                    wli.setItemFname(null);
                    wli.setItemLname(rs.getString("ITEM_LNAME"));
                    studentWatchListInstitutions.add(wli);
                }
            }
            rs.close();
            this.DBConn.close();
            stmt.close();
        } catch (SQLException e) {
            this.redirectToErrorPage();
        }
    }

    public int uploadResume(String username, StudentProfile stuPro) throws SQLException, IOException {
        int rowCount = 0;
        if (stuPro.getResume() != null) {
            try {
                this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
                InputStream resume = stuPro.getResume().getInputstream();
                String query = "UPDATE LINKEDU.student_profile SET resume = ? WHERE username = ?";
                PreparedStatement ps = this.DBConn.prepareStatement(query);
                ps.setBinaryStream(1, resume);
                ps.setString(2, username);
                rowCount = ps.executeUpdate();
                this.DBConn.close();
            } catch (SQLException e) {
                this.redirectToErrorPage();
            }
        }
        return rowCount;
    }

    public int uploadImg(String username, StudentProfile stuPro) throws SQLException, IOException {
        int rowCount = 0;
        if (stuPro.getImageUpload() != null) {
            try {
                this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
                InputStream profileImage = stuPro.getImageUpload().getInputstream();
                String query = "UPDATE LINKEDU.student_profile SET profile_image = ? WHERE username = ?";
                PreparedStatement ps = this.DBConn.prepareStatement(query);
                ps.setBinaryStream(1, profileImage);
                ps.setString(2, username);
                rowCount = ps.executeUpdate();
                this.DBConn.close();
            } catch (SQLException e) {
                this.redirectToErrorPage();
            }
        }
        return rowCount;
    }

}

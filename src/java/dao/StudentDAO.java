/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import model.StudentProfile;
import model.WatchListItem;

/**
 *
 * @author skdonep
 */
public class StudentDAO extends AppDBInfoDAO {

    private Connection DBConn;

    public StudentDAO() {
        super();
    }

    public boolean studentHasProfile(String username) throws SQLException {
        boolean studentHasProfile = false;

        String selectQuery = "SELECT * FROM LINKEDU.STUDENT_PROFILE WHERE USERNAME = '" + username + "'";

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
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }

        return studentHasProfile;
    }

    public StudentProfile fetchStudentProfile(String username) throws ParseException {
        StudentProfile studentProfile = new StudentProfile();

        String selectQuery = "SELECT * FROM LINKEDU.STUDENT_PROFILE WHERE USERNAME = '" + username + "'";

        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();

            ResultSet rs = stmt.executeQuery(selectQuery);

            while (rs.next()) {
                studentProfile.setfName(rs.getString("FIRST_NAME"));
                studentProfile.setlName(rs.getString("LAST_NAME"));
                studentProfile.setGender(rs.getString("GENDER").charAt(0));
                studentProfile.setDob(new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH).parse(rs.getString("DOB")));
                studentProfile.setHighestDegree(rs.getString("HIGHEST_DEGREE"));
                studentProfile.setGPA(rs.getString("GPA"));
                studentProfile.setEmail(rs.getString("EMAILID"));
                studentProfile.setPreferredPrograms(this.convertStringToList(rs.getString("PREFERRED_PROGRAMS")));
                studentProfile.setPreferredInsts(this.convertStringToList(rs.getString("PREFERRED_UNIVS")));
                studentProfile.setPrimaryPhNum(rs.getString("PRIMARY_PHONE"));
                studentProfile.setSecondaryPhNum(rs.getString("SECONDARY_PHONE"));
                studentProfile.setCity(rs.getString("COUNTRY"));
                studentProfile.setState(rs.getString("STATE"));
                studentProfile.setCity(rs.getString("CITY"));
                studentProfile.setUsername(rs.getString("USERNAME"));
            }

            rs.close();
            this.DBConn.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        return studentProfile;
    }

    public void updateStudentProfile(StudentProfile studentProfile, String username) {
        String updateQuery = "UPDATE STUDENT_PROFILE SET FIRST_NAME = '"
                + studentProfile.getfName() + "', "
                + "LAST_NAME = '"
                + studentProfile.getlName() + "', "
                + "GENDER = '"
                + studentProfile.getGender() + "', "
                + "DOB = '"
                + studentProfile.getDob() + "', "
                + "HIGHEST_DEGREE = '"
                + studentProfile.getHighestDegree() + "', "
                + "GPA = '"
                + studentProfile.getGPA() + "', "
                + "PREFERRED_PROGRAMS = '"
                + this.convertListtoString(studentProfile.getPreferredPrograms()) + "', "
                + "PREFERRED_UNIVS = '"
                + this.convertListtoString(studentProfile.getPreferredInsts()) + "', "
                + "PRIMARY_PHONE = '"
                + studentProfile.getPrimaryPhNum() + "', "
                + "SECONDARY_PHONE = '"
                + studentProfile.getSecondaryPhNum() + "', "
                + "COUNTRY = '"
                + studentProfile.getCountry() + "', "
                + "STATE = '"
                + studentProfile.getState() + "', "
                + "CITY = '"
                + studentProfile.getCity() + ");"
                + "EMAIL = '"
                + studentProfile.getEmail() + "', "
                + "' WHERE USERNAME='" + username + "'";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();
            stmt.execute(updateQuery);
            this.DBConn.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
    }

    public void createStudentProfile(StudentProfile studentProfile, String username) {
        CommonDAO coomonDB = new CommonDAO();
        String emailFromUserInfo = coomonDB.getEmailFromUserInfoTable(username);
        String insertQuery = "INSERT INTO LINKEDU.STUDENT_PROFILE(FIRST_NAME,"
                + "LAST_NAME,"
                + "GENDER,"
                + "DOB,"
                + "HIGHEST_DEGREE,"
                + "GPA,"
                + "PREFERRED_PROGRAMS,"
                + "PREFERRED_UNIVS,"
                + "PRIMARY_PHONE,"
                + "SECONDARY_PHONE,"
                + "COUNTRY,"
                + "STATE,"
                + "CITY,"
                + "USERNAME,"
                + "EMAIL)"
                + "VALUES('"
                + studentProfile.getfName() + "','"
                + studentProfile.getlName() + "','"
                + studentProfile.getGender() + "','"
                + studentProfile.getDob() + "','"
                + studentProfile.getHighestDegree() + "','"
                + studentProfile.getGPA() + "','"
                + this.convertListtoString(studentProfile.getPreferredPrograms()) + "','"
                + this.convertListtoString(studentProfile.getPreferredInsts()) + "','"
                + studentProfile.getPrimaryPhNum() + "','"
                + studentProfile.getSecondaryPhNum() + "','"
                + studentProfile.getCountry() + "','"
                + studentProfile.getState() + "','"
                + studentProfile.getCity() + "','"
                + username + "','"
                + emailFromUserInfo + "')";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();
            stmt.execute(insertQuery);
            this.DBConn.close();
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
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
        List<String> convertedList = new ArrayList<>();;
        convertedList.addAll(Arrays.asList(delimitedString.split(";")));
        return convertedList;
    }

    public ArrayList<String> retrieveStudentWatchListRecruitersFromDB(String wlOwner) {
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

    public ArrayList<String> retrieveStudentWatchListUniversitiesFromDB(String wlOwner) {
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

    public boolean recruiterNotInWatchListInDB(String wlOwner, String wlItem) {
        boolean userNotInWatchList = true;
        String selectQuery = "SELECT * FROM LINKEDU.WATCH_LIST WHERE LOWER(OWNER) = '" + wlOwner.toLowerCase()
                + "' AND LOWER(WL_ENTRY)='" + wlItem.toLowerCase() + "'";
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

    public boolean institutionNotInWatchListInDB(String wlOwner, String wlItem) {
        boolean universityNotInWatchList = true;
        String selectQuery = "SELECT * FROM LINKEDU.STUDENT_WATCHLIST WHERE WL_OWNER = '" + wlOwner
                + "' AND WL_ITEM='" + wlItem + "'";
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
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        return universityNotInWatchList;
    }

    public int addUniversityToWatchListInDB(String wlOwner, String wlEntry,String wlLname) throws SQLException {
        this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
        Statement stmt = DBConn.createStatement();
        String insertStatement = "INSERT INTO LINKEDU.STUDENT_WATCHLIST VALUES('" + wlOwner + "','" + wlEntry + "',NULL,+'"+wlLname+"','U')";
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
            ArrayList<WatchListItem> studentWatchListRecruiters) {
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
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
    }
}

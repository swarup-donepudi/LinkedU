/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.StudentProfile;
import model.RecruiterProfile;

/**
 *
 * @author skdonep
 */
public class ProfileDAO extends AppDBInfoDAO {

    private Connection DBConn;

    public ProfileDAO() throws SQLException {
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

    public StudentProfile fetchStudentProfile(String username) {
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
                studentProfile.setDob(rs.getString("DOB"));
                studentProfile.setHighestDegree(rs.getString("HIGHEST_DEGREE"));
                studentProfile.setGPA(rs.getString("GPA"));
                studentProfile.setPreferredProgram(rs.getString("PREFERRED_PROGRAM"));
                studentProfile.setPreferredUnivs(rs.getString("PREFERRED_UNIVS"));
                studentProfile.setPrimaryPhNum(rs.getString("PRIMARY_PHONE"));
                studentProfile.setSecondaryPhNum(rs.getString("SECONDARY_PHONE"));
                studentProfile.setCity(rs.getString("COUNTRY"));
                studentProfile.setState(rs.getString("STATE"));
                studentProfile.setCity(rs.getString("CITY"));
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

    public RecruiterProfile fetchRecruiterProfile(String username) {
        RecruiterProfile recruiterProfile = new RecruiterProfile();

        String selectQuery = "SELECT * FROM LINKEDU.STUDENT_PROFILE WHERE USERNAME = '" + username + "'";

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
                recruiterProfile.setPrimaryPhNum(rs.getString("PRIMARY_PHONE"));
                recruiterProfile.setSecondaryPhNum(rs.getString("SECONDARY_PHONE"));
                recruiterProfile.setCity(rs.getString("COUNTRY"));
                recruiterProfile.setState(rs.getString("STATE"));
                recruiterProfile.setCity(rs.getString("CITY"));
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
                + "PREFERRED_PROGRAM = '"
                + studentProfile.getPreferredProgram() + "', "
                + "PREFERRED_UNIVS = '"
                + studentProfile.getPreferredUnivs() + "', "
                + "PRIMARY_PHONE = '"
                + studentProfile.getPrimaryPhNum() + "', "
                + "SECONDARY_PHONE = '"
                + studentProfile.getSecondaryPhNum() + "', "
                + "COUNTRY = '"
                + studentProfile.getCountry() + "', "
                + "STATE = '"
                + studentProfile.getState() + "', "
                + "CITY = '"
                + studentProfile.getCity() + ");";
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

    public void updateRecruiterProfile(RecruiterProfile recruiterProfile, String username) {
        String updateQuery = "UPDATE STUDENT_PROFILE SET FIRST_NAME = '"
                + recruiterProfile.getfName() + "', "
                + "LAST_NAME = '"
                + recruiterProfile.getlName() + "', "
                + "GENDER = '"
                + recruiterProfile.getGender() + "', "
                + "UNIVERSITY = '"
                + recruiterProfile.getUnivName() + "', "
                + "UNIV_URL = '"
                + recruiterProfile.getUnivURL()+ "', "
                + "PRIMARY_PHONE = '"
                + recruiterProfile.getPrimaryPhNum() + "', "
                + "SECONDARY_PHONE = '"
                + recruiterProfile.getSecondaryPhNum() + "', "
                + "COUNTRY = '"
                + recruiterProfile.getCountry() + "', "
                + "STATE = '"
                + recruiterProfile.getState() + "', "
                + "CITY = '"
                + recruiterProfile.getCity() + ");";
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

    public void createStudentProfile(StudentProfile studentProfile,String username) {
        String insertQuery = "INSERT INTO STUDENT_PROFILE(FIRST_NAME,"
                + "LAST_NAME,"
                + "GENDER,"
                + "DOB,"
                + "HIGHEST_DEGREE"
                + "GPA"
                + "PREFERRED_PROGRAM,"
                + "PREFERRED_UNIVS,"
                + "PRIMARY_PHONE,"
                + "SECONDARY_PHONE,"
                + "COUNTRY,"
                + "STATE,"
                + "CITY,"
                + "USERNAME) "
                + "VALUES("
                + studentProfile.getfName() + "','"
                + studentProfile.getlName() + "','"
                + studentProfile.getGender() + "','"
                + studentProfile.getDob() + "','"
                + studentProfile.getHighestDegree() + "','"
                + studentProfile.getGPA() + "','"
                + studentProfile.getPreferredProgram() + "','"
                + studentProfile.getPreferredUnivs() + "','"
                + studentProfile.getPrimaryPhNum() + "','"
                + studentProfile.getSecondaryPhNum() + "','"
                + studentProfile.getCountry() + "','"
                + studentProfile.getState() + "','"
                + studentProfile.getCity() + "','"
                + username + "';";
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            try (Statement stmt = this.DBConn.createStatement()) {
                stmt.execute(insertQuery);
                this.DBConn.close();
            }
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
    }

    public void createRecruiterProfile(RecruiterProfile recruiterProfile,String username) {
        
        String insertQuery = "INSERT INTO STUDENT_PROFILE(FIRST_NAME,"
                + "LAST_NAME,"
                + "GENDER,"
                + "UNIVERSITY,"
                + "UNIV_URL"
                + "GPA"
                + "PREFERRED_PROGRAM,"
                + "PREFERRED_UNIVS,"
                + "PRIMARY_PHONE,"
                + "SECONDARY_PHONE,"
                + "COUNTRY,"
                + "STATE,"
                + "CITY,"
                + "USERNAME) "
                + "VALUES("
                + recruiterProfile.getfName() + "','"
                + recruiterProfile.getlName() + "','"
                + recruiterProfile.getGender() + "','"
                + recruiterProfile.getUnivName()+ "','"
                + recruiterProfile.getUnivURL() + "','"
                + recruiterProfile.getPrimaryPhNum() + "','"
                + recruiterProfile.getSecondaryPhNum() + "','"
                + recruiterProfile.getCountry() + "','"
                + recruiterProfile.getState() + "','"
                + recruiterProfile.getCity() + "','"
                + username + "';";  
        try {
            this.DBConn = this.openDBConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            try (Statement stmt = this.DBConn.createStatement()) {
                stmt.execute(insertQuery);
                this.DBConn.close();
            }
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }        
    }
}

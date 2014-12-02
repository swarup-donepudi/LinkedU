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
        CommonDAO coomonDB= new CommonDAO();
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
    
    public String convertListtoString(List<String> list){
        String convertedString=null;
        Iterator<String> iterator = list.iterator();
        if(iterator.hasNext())
            convertedString = iterator.next();
	while (iterator.hasNext()) {
		convertedString += ";"+iterator.next();
	}
        return convertedString; 
    }
    
    public List<String> convertStringToList(String delimitedString){
        List<String> convertedList=new ArrayList<>();;
        convertedList.addAll(Arrays.asList(delimitedString.split(";")));
        return convertedList; 
    }    
}

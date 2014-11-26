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
import java.util.ArrayList;
import model.StudentProfile;
import model.StudentSearchCriteria;
import model.UniversityProfile;
import model.UniversitySearchCriteria;

/**
 *
 * @author skdonep
 */
public class SearchDAO extends AppDBInfoDAO {

    private Connection DBConn;

    public Connection getDBConn() {
        return DBConn;
    }

    public void setDBConn(Connection DBConn) {
        this.DBConn = DBConn;
    }

    public void retrieveStudentSearchResults(StudentSearchCriteria ssc, ArrayList<StudentProfile> studentSearchResults) throws SQLException {
        this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
        Statement stmt = DBConn.createStatement();
        String selectQuery = "SELECT * FROM LINKEDU.STUDENT_PROFILE WHERE PREFERRED_UNIVS LIKE '%" + ssc.getPreferredUniv() + "%'";
        ResultSet rs = stmt.executeQuery(selectQuery);
        while (rs.next()) {
            StudentProfile studentProfile = new StudentProfile();
            studentProfile.setfName(rs.getString("FIRST_NAME"));
            studentProfile.setlName(rs.getString("LAST_NAME"));
            studentProfile.setGender(rs.getString("GENDER").charAt(0));
            studentProfile.setDob(rs.getString("DOB"));
            studentProfile.setHighestDegree(rs.getString("HIGHEST_DEGREE"));
            studentProfile.setGPA(rs.getString("GPA"));
            //studentProfile.setPreferredPrograms(rs.getString("PREFERRED_PROGRAM"));
            //studentProfile.setPreferredUnivs(rs.getString("PREFERRED_UNIVS"));
            studentProfile.setPrimaryPhNum(rs.getString("PRIMARY_PHONE"));
            studentProfile.setSecondaryPhNum(rs.getString("SECONDARY_PHONE"));
            studentProfile.setCity(rs.getString("COUNTRY"));
            studentProfile.setState(rs.getString("STATE"));
            studentProfile.setCity(rs.getString("CITY"));
            studentProfile.setUsername(rs.getString("USERNAME"));
            studentSearchResults.add(studentProfile);
        }
        rs.close();
        this.DBConn.close();
        stmt.close();
    }
    
    public void retrieveUniversitySearchResults(UniversitySearchCriteria usc, ArrayList<UniversityProfile> universitySearchResults) throws SQLException {
        this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
        Statement stmt = DBConn.createStatement();
        String selectQuery = "SELECT * FROM LINKEDU.UNIVERSITY WHERE UNIV_NAME LIKE '%" + usc.getUnivName() + "%'";
        ResultSet rs = stmt.executeQuery(selectQuery);
        while (rs.next()) {
        }
        rs.close();
        this.DBConn.close();
        stmt.close();
    }    
}

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
            studentProfile.setFname(rs.getString("FIRST_NAME"));
            studentProfile.setLname(rs.getString("LAST_NAME"));
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
    
    public String requestMoreInfoByStudent(String uniname) throws SQLException {
        this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
        Statement stmt = DBConn.createStatement();
        String searchStmt = "SELECT * FROM LINKEDU.UNIVERSITY_DETAILS WHERE UNIVERSITY_NAME = '" + uniname + "'";
        ResultSet rs = stmt.executeQuery(searchStmt);
        String result = "";
        while (rs.next()) {
            result = "More Details are <br/>";
            result += rs.getString("UNIVERSITY_NAME") + " <br/><br/><br/> ";
            if(rs.getString("ACAD_BACKGROUND")!=null)
            result += rs.getString("ACAD_BACKGROUND") + " <br/> ";
            if(rs.getString("DEMO_INFO")!=null)
            result += rs.getString("DEMO_INFO") + " <br/> ";
            if(rs.getString("APP_FEE")!=null)
            result += rs.getString("APP_FEE") + " <br/> ";
            if(rs.getString("TRANSCRIPTS")!=null)
            result += rs.getString("TRANSCRIPTS") + " <br/> ";
            if(rs.getString("SAT_ACT")!=null)
            result += rs.getString("SAT_ACT") + " <br/> ";
            if(rs.getString("TOEFL_PAPER")!=null)
            result += rs.getString("TOEFL_PAPER") + " <br/> ";
            if(rs.getString("TOEFL_COMP")!=null)
            result += rs.getString("TOEFL_COMP") + " <br/> ";
            if(rs.getString("TOEFL_INTERNET")!=null)
            result += rs.getString("TOEFL_INTERNET") + " <br/> ";
            if(rs.getString("FINANCE_REQ")!=null)
            result += rs.getString("FINANCE_REQ") + " <br/> ";
            if(rs.getString("PERSONAL_INFO")!=null)
            result += rs.getString("PERSONAL_INFO") + " <br/> ";
            if(rs.getString("RESUME")!=null)
            result += rs.getString("RESUME") + " <br/> ";
            if(rs.getString("LOR")!=null)
            result += rs.getString("LOR") + " <br/> ";
            if(rs.getString("COST")!=null)
            result += rs.getString("COST") + " <br/> ";
        }
        rs.close();
        this.DBConn.close();
        stmt.close();

        return result;
    }
    
    public ArrayList UniversitySearchResults(String query) throws SQLException {
        this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
        Statement stmt = DBConn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        ArrayList result = new ArrayList();
        String uniName, program, location, website;
        Double gpa = null;
        int toefl = 0;
        while (rs.next()) {
            uniName = rs.getString("University_Name");
            gpa = rs.getDouble("GPA_REQ");
            toefl = rs.getInt("TOELF_REQ");
            program = rs.getString("PROGRAM");
            location = rs.getString("location");
            website = rs.getString("website");
            UniversitySearchCriteria update;
            update = new UniversitySearchCriteria(uniName, gpa, toefl, program, location, website);
            result.add(update);
        }
        rs.close();
        this.DBConn.close();
        stmt.close();

        return result;
    }
}

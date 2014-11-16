/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.UserProfile;

/**
 *
 * @author skdonep
 */
public class ProfileDAO extends AppDBInfoDAO {

    private Connection DBConn;

    public ProfileDAO() throws SQLException {
        super();
    }

    public boolean userHasProfile(String username, char accountType) {
        String userTable = "";
        if (accountType == 'S') {
            userTable = "STUDENT_PROFILE";
        } else if (accountType == 'R') {
            userTable = "RECRUITER_PROFILE";
        }
        String selectQuery = "SELECT * FROM LINKEDU." + userTable;
        selectQuery += " WHERE USERNAME = '" + username + "'";

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        try {
            this.DBConn = DriverManager.getConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();

            ResultSet rs = stmt.executeQuery(selectQuery);

            if (rs.next()) {
                rs.close();
                this.DBConn.close();
                stmt.close();
                return true;
            } else {
                rs.close();
                this.DBConn.close();
                stmt.close();
                return false;
            }
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        return false;
    }

    public void fetchUserProfile(UserProfile profile, String username, char accountType) {
        String userTable = "";
        if (accountType == 'S') {
            userTable = "STUDENT_PROFILE";
        } else if (accountType == 'R') {
            userTable = "RECRUITER_PROFILE";
        }
        String selectQuery = "SELECT * FROM LINKEDU." + userTable;
        selectQuery += " WHERE USERNAME = '" + username + "'";

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        try {
            this.DBConn = DriverManager.getConnection(this.databaseURL, this.dbUserName, this.dbPassword);
            Statement stmt = this.DBConn.createStatement();

            ResultSet rs = stmt.executeQuery(selectQuery);
            if (accountType == 'S') {
                while (rs.next()) {
                    profile.setfName(rs.getString("FIRST_NAME"));
                    profile.setlName(rs.getString("LAST_NAME"));
                    profile.setGender(rs.getString("GENDER").charAt(0));
                    rs.getString("DOB");
                    rs.getString("HIGHEST_DEGREE");
                    rs.getString("GPA");
                    rs.getString("PREFERRED_PROGRAM");
                    rs.getString("PREFERRED_UNIVS");
                    rs.getString("PRIMARY_PHONE");
                    rs.getString("SECONDARY_PHONE");
                    rs.getString("COUNTRY");
                    rs.getString("STATE");
                    rs.getString("CITY");
                }
            } else if (accountType == 'R') {
                    profile.setfName(rs.getString("FIRST_NAME"));
                    profile.setlName(rs.getString("LAST_NAME"));
                    profile.setGender(rs.getString("GENDER").charAt(0));
                    rs.getString("UNIVERSITY");
                    rs.getString("UNIV_URL");
                    rs.getString("PRIMARY_PHONE");
                    rs.getString("SECONDARY_PHONE");
                    rs.getString("COUNTRY");
                    rs.getString("STATE");
                    rs.getString("CITY");
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

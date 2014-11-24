/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author skdonep
 */
public class RecruiterDAO extends AppDBInfoDAO {
    private Connection DBConn;
    public boolean studentNotInWatchList(String recruiterUsername,String studentUsername){
       boolean studentNotInWatchList = true;

        String selectQuery = "SELECT * FROM LINKEDU.RECRUITERWATCHLIST WHERE RECRUITERUSERNAME = '" + recruiterUsername
                                + "' AND STUDENTUSERNAME='"+studentUsername+"'";

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

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
public class SuggestionsDAO extends AppDBInfoDAO {

    private Connection DBConn;

    public SuggestionsDAO() {
        super();
    }

    public int fetchCountryDialingCodeFromDB(String countryName) throws SQLException {
        int countryCode = 0;
        countryName = countryName.toLowerCase();
        this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
        Statement stmt = DBConn.createStatement();
        String selectQuery = "SELECT * FROM LINKEDU.COUNTRY_PH_CODES WHERE LOWER(COUNTRY_NAME)= '" + countryName + "'";
        ResultSet rs = stmt.executeQuery(selectQuery);
        if (rs.next()) {
            countryCode = rs.getInt("CODE");
        }
        rs.close();
        this.DBConn.close();
        stmt.close();
        return countryCode;
    }
}

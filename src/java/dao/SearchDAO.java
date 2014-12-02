/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.json.Json;
import javax.json.stream.JsonParser;
import static javax.json.stream.JsonParser.Event.KEY_NAME;
import static javax.json.stream.JsonParser.Event.VALUE_STRING;
import model.StudentProfile;
import model.StudentSearchCriteria;
import model.InstitutionProfile;
import model.InstitutionSearchCriteria;

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

    public void retrieveStudentSearchResults(StudentSearchCriteria ssc, ArrayList<StudentProfile> studentSearchResults) throws SQLException, ParseException {
        this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
        Statement stmt = DBConn.createStatement();
        String selectQuery = "SELECT * FROM LINKEDU.STUDENT_PROFILE WHERE PREFERRED_UNIVS LIKE '%" + ssc.getPreferredInst() + "%'";
        ResultSet rs = stmt.executeQuery(selectQuery);
        while (rs.next()) {
            StudentProfile studentProfile = new StudentProfile();
            studentProfile.setFname(rs.getString("FIRST_NAME"));
            studentProfile.setLname(rs.getString("LAST_NAME"));
            studentProfile.setGender(rs.getString("GENDER").charAt(0));
            studentProfile.setDob(new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH).parse(rs.getString("DOB")));
            studentProfile.setHighestDegree(rs.getString("HIGHEST_DEGREE"));
            studentProfile.setGPA(rs.getString("GPA"));
            studentProfile.setPreferredPrograms(this.convertStringToList("Information Systems"));//Replace the hard coded value with PREFERRED_PROGRAMS Column name
            studentProfile.setPreferredInsts(this.convertStringToList(rs.getString("PREFERRED_UNIVS")));
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

    public void retrieveInstitutionSearchResults(InstitutionSearchCriteria usc, ArrayList<InstitutionProfile> institutionSearchResults) throws SQLException, UnsupportedEncodingException, IOException {
        String keyname = null;
        String instName = usc.getInstName();
        instName = instName.toLowerCase();
        //Web service call to the US Department of Education
        String output = null;
        try {
            String serviceURI = "https://inventory.data.gov/api/action/datastore_search_sql?sql=";
            String serviceQuery = "SELECT \"UNITID\",\"INSTNM\" from \"38625c3d-5388-4c16-a30f-d105432553a4\" "
                    + "WHERE LOWER(\"INSTNM\") LIKE '%" + instName + "%' OR LOWER(\"IALIAS\") LIKE '%" + instName + "%'";
            serviceURI += URLEncoder.encode(serviceQuery, "UTF-8");
            URL url = new URL(serviceURI);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                int responseCode = conn.getResponseCode();
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            output = br.readLine();

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //output = "{\"help\": \"Search a DataStore resource.\\n\\n    The datastore_search action allows you to search data in a resource.\\n    DataStore resources that belong to private CKAN resource can only be\\n    read by you if you have access to the CKAN resource and send the appropriate\\n    authorization.\\n\\n    :param resource_id: id or alias of the resource to be searched against\\n    :type resource_id: string\\n    :param filters: matching conditions to select, e.g {\\\"key1\\\": \\\"a\\\", \\\"key2\\\": \\\"b\\\"} (optional)\\n    :type filters: dictionary\\n    :param q: full text query (optional)\\n    :type q: string\\n    :param plain: treat as plain text query (optional, default: true)\\n    :type plain: bool\\n    :param language: language of the full text query (optional, default: english)\\n    :type language: string\\n    :param limit: maximum number of rows to return (optional, default: 100)\\n    :type limit: int\\n    :param offset: offset this number of rows (optional)\\n    :type offset: int\\n    :param fields: fields to return (optional, default: all fields in original order)\\n    :type fields: list or comma separated string\\n    :param sort: comma separated field names with ordering\\n                 e.g.: \\\"fieldname1, fieldname2 desc\\\"\\n    :type sort: string\\n\\n    Setting the ``plain`` flag to false enables the entire PostgreSQL `full text search query language`_.\\n\\n    A listing of all available resources can be found at the alias ``_table_metadata``.\\n\\n    .. _full text search query language: http://www.postgresql.org/docs/9.1/static/datatype-textsearch.html#DATATYPE-TSQUERY\\n\\n    If you need to download the full resource, read :ref:`dump`.\\n\\n    **Results:**\\n\\n    The result of this action is a dictionary with the following keys:\\n\\n    :rtype: A dictionary with the following keys\\n    :param fields: fields/columns and their extra metadata\\n    :type fields: list of dictionaries\\n    :param offset: query offset value\\n    :type offset: int\\n    :param limit: query limit value\\n    :type limit: int\\n    :param filters: query filters\\n    :type filters: list of dictionaries\\n    :param total: number of total matching records\\n    :type total: int\\n    :param records: list of matching results\\n    :type records: list of dictionaries\\n\\n    \", \"success\": true, \"result\": {\"resource_id\": \"6fa1786f-ef01-4471-b033-aea7a7169e6c\", \"fields\": [{\"type\": \"int4\", \"id\": \"_id\"}, {\"type\": \"numeric\", \"id\": \"CIPCODE\"}, {\"type\": \"text\", \"id\": \"NAME\"}], \"records\": [{\"CIPCODE\": \"1\", \"_id\": 1, \"NAME\": \"Agriculture, Agriculture Operations, and Related Sciences\"}, {\"CIPCODE\": \"1.00\", \"_id\": 2, \"NAME\": \"Agriculture, General\"}, {\"CIPCODE\": \"1.0000\", \"_id\": 3, \"NAME\": \"Agriculture, General\"}, {\"CIPCODE\": \"1.01\", \"_id\": 4, \"NAME\": \"Agricultural Business and Management\"}, {\"CIPCODE\": \"1.0101\", \"_id\": 5, \"NAME\": \"Agricultural Business and Management, General\"}], \"_links\": {\"start\": \"/api/action/datastore_search?limit=5&resource_id=6fa1786f-ef01-4471-b033-aea7a7169e6c\", \"next\": \"/api/action/datastore_search?offset=5&limit=5&resource_id=6fa1786f-ef01-4471-b033-aea7a7169e6c\"}, \"limit\": 5, \"total\": 1892}}";
        JsonParser jsonParser = Json.createParser(new StringReader(output));
        InstitutionProfile instProfile = null;
        while (jsonParser.hasNext()) {
            switch (jsonParser.next()) {
                case KEY_NAME:
                    keyname = jsonParser.getString();
                    System.out.println("Keyname :");
                    break;
                case VALUE_STRING:
                    if (keyname.equals("INSTNM")) {
                        instProfile = new InstitutionProfile();
                        String valueStr = jsonParser.getString();
                        instProfile.setInstName(valueStr);
                    }
                    if (keyname.equals("UNITID")) {
                        String unitID = jsonParser.getString();
                        instProfile.setDoeUID(unitID);
                        institutionSearchResults.add(instProfile);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public List<String> convertStringToList(String delimitedString) {
        List<String> convertedList = new ArrayList<>();;
        convertedList.addAll(Arrays.asList(delimitedString.split(";")));
        return convertedList;

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

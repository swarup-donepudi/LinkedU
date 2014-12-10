/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.SuggestionsDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.stream.JsonParser;
import static javax.json.stream.JsonParser.Event.*;
import javax.servlet.http.HttpSession;

/**
 *
 * @author skdonep
 */
@ManagedBean
@SessionScoped
public class SuggestionsController {

    @ManagedProperty(value = "#{recruiterController}")
    private RecruiterController recruiterController;
    @ManagedProperty(value = "#{studentController}")
    private StudentController studentController;

    /**
     * Creates a new instance of SuggestionsController
     */
    public SuggestionsController() {
    }

    public RecruiterController getRecruiterController() {
        return recruiterController;
    }

    public void setRecruiterController(RecruiterController recruiterController) {
        this.recruiterController = recruiterController;
    }

    public StudentController getStudentController() {
        return studentController;
    }

    public void setStudentController(StudentController studentController) {
        this.studentController = studentController;
    }

    public List<String> suggestInsts(String query) throws IOException {
        List<String> results = new ArrayList<>();
        query = query.toLowerCase();
        if (!query.equals("")) {
            String keyname = null;
            //Web service call to the US Department of Education
            String output = null;
            try {
                String serviceURI = "https://inventory.data.gov/api/action/datastore_search_sql?sql=";
                String serviceQuery = "SELECT \"INSTNM\" from \"38625c3d-5388-4c16-a30f-d105432553a4\" "
                        + "WHERE LOWER(\"INSTNM\") LIKE '" + query +"%' OR LOWER(\"IALIAS\") LIKE '%"+ query + "%'";
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
            while (jsonParser.hasNext()) {
                switch (jsonParser.next()) {
                    case KEY_NAME:
                        keyname = jsonParser.getString();
                        System.out.println("Keyname :");
                        break;
                    case VALUE_STRING:
                        String valueStr = jsonParser.getString();
                        if (keyname != null) {
                            if (keyname.equals("INSTNM")) {
                                results.add(valueStr);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        return results;
    }

    public List<String> suggestPrograms(String query) throws IOException {
        List<String> results = new ArrayList<>();
        query = query.toLowerCase();
        if (!query.equals("")) {
            String doe_progs_table = "6fa1786f-ef01-4471-b033-aea7a7169e6c";
            String keyname = null;
            //Web service call to the US Department of Education
            String output = null;
            try {
                String serviceURI = "https://inventory.data.gov/api/action/datastore_search_sql?sql=";
                String serviceQuery = "SELECT \"NAME\" from \"" + doe_progs_table + "\""
                        + " WHERE LOWER(\"NAME\") LIKE '" + query + "%' LIMIT 5";
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
            while (jsonParser.hasNext()) {
                switch (jsonParser.next()) {
                    case KEY_NAME:
                        keyname = jsonParser.getString();
                        System.out.println("Keyname :");
                        break;
                    case VALUE_STRING:
                        String valueStr = jsonParser.getString();
                        if (keyname != null) {
                            if (keyname.equals("NAME")) {
                                results.add(valueStr);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        return results;
    }

    public List<String> suggestCities(String query) throws IOException {
        List<String> results = new ArrayList<>();
        if (!query.equals("")) {
            //Web service call to GeoBytes Location Data API service
            String output = null;
            try {
                String serviceURI = "http://gd.geobytes.com/AutoCompleteCity?q=";
                serviceURI += URLEncoder.encode(query, "UTF-8");
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
            if (output != null) {
                if (!output.equals("")) {
                    output = output.substring(2, output.length() - 2);
                    if (output.contains("\",\"")) {
                        for (String retval : output.split("\",\"")) {
                            results.add(retval);
                        }
                    } else {
                        results.add(output);
                    }
                }
            }
        }
        return results;
    }

    public void autofillWithCityName() throws UnsupportedEncodingException, IOException, SQLException {
        String countryCode=null;
        String selectedItem = null;
        String countryName = null;
        String stateName = null;
        String cityName = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        char accType = session.getAttribute("LinkEDU_AccType").toString().charAt(0);

        if (accType == 'S') {
            selectedItem = this.studentController.getStudentProfile().getCity();
        } else {
            selectedItem = this.recruiterController.getRecruiterProfile().getCity();
        }
        
        String[] citySuggestionParts = selectedItem.split(",");
        cityName = citySuggestionParts[0];
        stateName = citySuggestionParts[1].substring(1);
        countryName = citySuggestionParts[2].substring(1);
        /*String keyname = null;
        String output = null;

        try {
            String serviceURI = "http://gd.geobytes.com/GetCityDetails?fqcn=";
            serviceURI += URLEncoder.encode(selectedItem, "UTF-8");
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
     
        JsonParser jsonParser = Json.createParser(new StringReader(output));

        while (jsonParser.hasNext()) {
            switch (jsonParser.next()) {
                case KEY_NAME:
                    keyname = jsonParser.getString();
                    break;
                case VALUE_STRING:
                    if (keyname != null) {
                        if (keyname.equals("geobytescountry")) {
                            countryName = jsonParser.getString();
                        }
                    }
                    if (keyname != null) {
                        if (keyname.equals("geobytesregion")) {
                            stateName = jsonParser.getString();
                        }
                    }
                    if (keyname != null) {
                        if (keyname.equals("geobytescity")) {
                            cityName = jsonParser.getString();
                        }
                    }
                    break;
                default:
                    break;
            }
        }*/
        SuggestionsDAO suggestionDB = new SuggestionsDAO();
        int countryCodeNum = suggestionDB.fetchCountryDialingCodeFromDB(countryName);
        countryCode = "+"+countryCodeNum;
        if (accType == 'S') {
            this.studentController.getStudentProfile().setCountry(countryName);
            this.studentController.getStudentProfile().setState(stateName);
            this.studentController.getStudentProfile().setCity(cityName);
            this.studentController.getStudentProfile().setCountryDialingCode(countryCode);
        } else {
            this.recruiterController.getRecruiterProfile().setCountry(countryName);
            this.recruiterController.getRecruiterProfile().setState(stateName);
            this.recruiterController.getRecruiterProfile().setCity(cityName);
            this.recruiterController.getRecruiterProfile().setCountryDialingCode(countryCode);
        }
    }
}
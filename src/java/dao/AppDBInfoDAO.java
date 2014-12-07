/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author skdonep
 */
public class AppDBInfoDAO {

    public String databaseURL;
    public String dbUserName;
    public String dbPassword;

    public AppDBInfoDAO() {
        //this.databaseURL = "jdbc:derby://gfish.it.ilstu.edu:1527/skdonep_Fall14_LinkedU";
//        this.databaseURL = "jdbc:derby://localhost:1527/skdonep_Fall14_LinkedU";
//        this.dbUserName = "linkedu_admin";
//        this.dbPassword = "student123";
        this.databaseURL = "jdbc:derby://localhost:1527/project";
        this.dbUserName = "itkstu";
        this.dbPassword = "student";
    }

    public String getDatabaseURL() {
        return databaseURL;
    }

    public void setDatabaseURL(String databaseURL) {
        this.databaseURL = databaseURL;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public Connection openDBConnection(String databaseURL, String dbUserName, String dbPassword) {
        Connection DBConn = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found " + e);
        }
        try {
            DBConn = DriverManager.getConnection(databaseURL, dbUserName, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return DBConn;
    }

    public void redirectToErrorPage() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("SQLError.xhtml");
    }
    
    public DefaultStreamedContent binaryToDefaultStreamedContent(byte[] binaryFile, String ext){
        DefaultStreamedContent fileToShow = new DefaultStreamedContent(new ByteArrayInputStream(binaryFile), ext);
        return fileToShow;
    }
    
    public InputStream uploadedFileToBinary(UploadedFile sample) throws IOException{        
        InputStream finalFile = sample.getInputstream();
        return finalFile;
    }
}

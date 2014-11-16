/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

/**
 *
 * @author skdonep
 */
public class AppDBInfo {
    public String databaseURL;
    public String dbUserName;
    public String dbPassword;
    
    public AppDBInfo(){
            this.databaseURL = "jdbc:derby://gfish.it.ilstu.edu:1527/skdonep_Fall14_LinkedU";
            this.dbUserName = "linkedu_admin";
            this.dbPassword = "student123";
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
}

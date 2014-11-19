/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author skdonep
 */
public class RecruiterProfile extends UserProfile {

    private String univName;
    private String univURL;
    private ArrayList<String> watchList;

    public ArrayList<String> getWatchList() {
        return watchList;
    }

    public void setWatchList(ArrayList<String> watchList) {
        this.watchList = watchList;
    }

    public String getUnivName() {
        return univName;
    }

    public void setUnivName(String university) {
        this.univName = university;
    }

    public String getUnivURL() {
        return univURL;
    }

    public void setUnivURL(String universityURL) {
        this.univURL = universityURL;
    }
}

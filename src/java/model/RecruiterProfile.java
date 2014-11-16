/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author skdonep
 */
public class RecruiterProfile extends UserProfile{
    private String univName;
    private String univURL;

    public String getUniversity() {
        return univName;
    }

    public void setUniversity(String university) {
        this.univName = university;
    }

    public String getUniversityURL() {
        return univURL;
    }

    public void setUniversityURL(String universityURL) {
        this.univURL = universityURL;
    }
}

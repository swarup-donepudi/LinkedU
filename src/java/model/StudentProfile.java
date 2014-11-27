/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author skdonep
 */
public class StudentProfile extends UserProfile{
    private Date dob;
    private String highestDegree;
    private String GPA;
    private List<String> preferredPrograms;
    private List<String> preferredUnivs;

    public StudentProfile(){
        this.preferredPrograms = new ArrayList<>();
        this.preferredUnivs = new ArrayList<>();
    }
    
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getHighestDegree() {
        return highestDegree;
    }

    public void setHighestDegree(String highestDegree) {
        this.highestDegree = highestDegree;
    }

    public String getGPA() {
        return GPA;
    }

    public void setGPA(String GPA) {
        this.GPA = GPA;
    }

    public List<String> getPreferredPrograms() {
        return preferredPrograms;
    }

    public void setPreferredPrograms(List<String> preferredProgram) {
        this.preferredPrograms = preferredPrograms;
    }

    public List<String> getPreferredUnivs() {
        return preferredUnivs;
    }

    public void setPreferredUnivs(List<String> preferredUnivs) {
        this.preferredUnivs = preferredUnivs;
    }
}

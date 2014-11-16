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
public class StudentProfile extends UserProfile{
    private String dob;
    private String highestDegree;
    private double GPA;
    private String preferredProgram;
    private String[] preferredUnivs;

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getHighestDegree() {
        return highestDegree;
    }

    public void setHighestDegree(String highestDegree) {
        this.highestDegree = highestDegree;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    public String getPreferredProgram() {
        return preferredProgram;
    }

    public void setPreferredProgram(String preferredProgram) {
        this.preferredProgram = preferredProgram;
    }

    public String[] getPreferredUnivs() {
        return preferredUnivs;
    }

    public void setPreferredUnivs(String[] preferredUnivs) {
        this.preferredUnivs = preferredUnivs;
    }
}

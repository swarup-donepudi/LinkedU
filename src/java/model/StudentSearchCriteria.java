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
public class StudentSearchCriteria {
    private String GPA;
    private String preferredPrograms;
    private String preferredUniv;

    public String getGPA() {
        return GPA;
    }

    public void setGPA(String GPA) {
        this.GPA = GPA;
    }

    public String getPreferredPrograms() {
        return preferredPrograms;
    }

    public void setPreferredPrograms(String preferredPrograms) {
        this.preferredPrograms = preferredPrograms;
    }

    public String getPreferredUniv() {
        return preferredUniv;
    }

    public void setPreferredUniv(String preferredUniv) {
        this.preferredUniv = preferredUniv;
    }
    
    
}

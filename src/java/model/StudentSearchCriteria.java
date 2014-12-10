/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.List;

/**
 *
 * @author skdonep
 */
public class StudentSearchCriteria {
    
    private String preferredPrograms;
    private String preferredInst;
    private String student1;
    private String student2;
    public List watchListNames;

    

    public String getPreferredPrograms() {
        return preferredPrograms;
    }
    
    

    public void setPreferredPrograms(String preferredPrograms) {
        this.preferredPrograms = preferredPrograms;
    }

    public String getPreferredInst() {
        return preferredInst;
    }

    public void setPreferredInst(String preferredInst) {
        this.preferredInst = preferredInst;
    }

    /**
     * @return the student1
     */
    public String getStudent1() {
        return student1;
    }

    /**
     * @param student1 the student1 to set
     */
    public void setStudent1(String student1) {
        this.student1 = student1;
    }

    /**
     * @return the student2
     */
    public String getStudent2() {
        return student2;
    }

    /**
     * @param student2 the student2 to set
     */
    public void setStudent2(String student2) {
        this.student2 = student2;
    }

    /**
     * @return the watchListNames
     */
    public List getWatchListNames() {
        return watchListNames;
    }

    /**
     * @param watchListNames the watchListNames to set
     */
    public void setWatchListNames(List watchListNames) {
        this.watchListNames = watchListNames;
    }
    
    
}

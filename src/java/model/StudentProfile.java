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
    private List<String> preferredInsts;
    private String SAT;
    private String ACT;
    private String TOEFL;
    private String GRE;
    private String IELTS;
    private String ceritifications;

    public StudentProfile(){
        this.preferredPrograms = new ArrayList<>();
        this.preferredInsts = new ArrayList<>();
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

    public void setPreferredPrograms(List<String> preferredPrograms) {
        this.preferredPrograms = preferredPrograms;
    }

    public List<String> getPreferredInsts() {
        return preferredInsts;
    }

    public void setPreferredInsts(List<String> preferredInsts) {
        this.preferredInsts = preferredInsts;
    }

    /**
     * @return the SAT
     */
    public String getSAT() {
        return SAT;
    }

    /**
     * @param SAT the SAT to set
     */
    public void setSAT(String SAT) {
        this.SAT = SAT;
    }

    /**
     * @return the ACT
     */
    public String getACT() {
        return ACT;
    }

    /**
     * @param ACT the ACT to set
     */
    public void setACT(String ACT) {
        this.ACT = ACT;
    }

    /**
     * @return the TOEFL
     */
    public String getTOEFL() {
        return TOEFL;
    }

    /**
     * @param TOEFL the TOEFL to set
     */
    public void setTOEFL(String TOEFL) {
        this.TOEFL = TOEFL;
    }

    /**
     * @return the GRE
     */
    public String getGRE() {
        return GRE;
    }

    /**
     * @param GRE the GRE to set
     */
    public void setGRE(String GRE) {
        this.GRE = GRE;
    }

    /**
     * @return the IELTS
     */
    public String getIELTS() {
        return IELTS;
    }

    /**
     * @param IELTS the IELTS to set
     */
    public void setIELTS(String IELTS) {
        this.IELTS = IELTS;
    }

    /**
     * @return the ceritifications
     */
    public String getCeritifications() {
        return ceritifications;
    }

    /**
     * @param ceritifications the ceritifications to set
     */
    public void setCeritifications(String ceritifications) {
        this.ceritifications = ceritifications;
    }
}

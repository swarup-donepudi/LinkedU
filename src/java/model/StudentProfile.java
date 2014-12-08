/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author skdonep
 */
public class StudentProfile extends UserProfile {

    private Date dob;
    private String highestDegree;
    private double GPA;
    private int SAT;
    private int TOEFL;
    private double IELTS;
    private List<String> preferredPrograms;
    private List<String> preferredInsts;

    private int ACT;

    private int GRE;

    private String ceritifications;
    private UploadedFile resume;
    private String youtubeLink;

    public StudentProfile() {
        this.preferredPrograms = new ArrayList<>();
        this.preferredInsts = new ArrayList<>();
    }

    public void setResume(UploadedFile resume) {
        this.resume = resume;
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
     * @return the ACT
     */
    public int getACT() {
        return ACT;
    }

    /**
     * @param ACT the ACT to set
     */
    public void setACT(int ACT) {
        this.ACT = ACT;
    }

    /**
     * @return the GRE
     */
    public int getGRE() {
        return GRE;
    }

    /**
     * @param GRE the GRE to set
     */
    public void setGRE(int GRE) {
        this.GRE = GRE;
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

    /**
     * @return the youtubeLink
     */
    public String getYoutubeLink() {
        return youtubeLink;
    }

    /**
     * @param youtubeLink the youtubeLink to set
     */
    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public String fetchYoutubeLink() {
        String a = getYoutubeLink();
        return a;

    }

    /**
     * @return the resume
     */
    public UploadedFile getResume() {
        return resume;
    }

    /**
     * @return the GPA
     */
    public double getGPA() {
        return GPA;
    }

    /**
     * @param GPA the GPA to set
     */
    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    /**
     * @return the SAT
     */
    public int getSAT() {
        return SAT;
    }

    /**
     * @param SAT the SAT to set
     */
    public void setSAT(int SAT) {
        this.SAT = SAT;
    }

    /**
     * @return the TOEFL
     */
    public int getTOEFL() {
        return TOEFL;
    }

    /**
     * @param TOEFL the TOEFL to set
     */
    public void setTOEFL(int TOEFL) {
        this.TOEFL = TOEFL;
    }

    /**
     * @return the IELTS
     */
    public double getIELTS() {
        return IELTS;
    }

    /**
     * @param IELTS the IELTS to set
     */
    public void setIELTS(double IELTS) {
        this.IELTS = IELTS;
    }

}

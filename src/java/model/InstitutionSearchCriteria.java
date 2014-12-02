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
public class InstitutionSearchCriteria {
    
    private String instName;
    private String state;
    private int toeflScore;
    private double gpa;
    private String location;
    private String program;
    private String website;
    private String searchCriteria = "";
    private String moreInfo;
    private String searchKeyword = "";
    private String gpaCriteria = "";
    
    public UniversitySearchCriteria(String uniname, double gpa, int toeflScore, String program, String location, String website){
        this.univName=uniname;
        this.gpa= gpa;
        this.toeflScore=toeflScore;
        this.program=program;
        this.location= location;
        this.website= website;
    }
    public UniversitySearchCriteria(){
        
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    /**
     * @return the toeflScore
     */
    public int getToeflScore() {
        return toeflScore;
    }

    /**
     * @param toeflScore the toeflScore to set
     */
    public void setToeflScore(int toeflScore) {
        this.toeflScore = toeflScore;
    }

    /**
     * @return the gpa
     */
    public double getGpa() {
        return gpa;
    }

    /**
     * @param gpa the gpa to set
     */
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the program
     */
    public String getProgram() {
        return program;
    }

    /**
     * @param program the program to set
     */
    public void setProgram(String program) {
        this.program = program;
    }

    /**
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website the website to set
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * @return the searchCriteria
     */
    public String getSearchCriteria() {
        return searchCriteria;
    }

    /**
     * @param searchCriteria the searchCriteria to set
     */
    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    /**
     * @return the moreInfo
     */
    public String getMoreInfo() {
        return moreInfo;
    }

    /**
     * @param moreInfo the moreInfo to set
     */
    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    /**
     * @return the searchKeyword
     */
    public String getSearchKeyword() {
        return searchKeyword;
    }

    /**
     * @param searchKeyword the searchKeyword to set
     */
    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword.toUpperCase();
    }

    /**
     * @return the gpaCriteria
     */
    public String getGpaCriteria() {
        return gpaCriteria;
    }

    /**
     * @param gpaCriteria the gpaCriteria to set
     */
    public void setGpaCriteria(String gpaCriteria) {
        this.gpaCriteria = gpaCriteria;
    }
}

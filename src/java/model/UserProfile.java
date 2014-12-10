/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.text.DecimalFormat;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author skdonep
 */
public class UserProfile {
    private String fname;
    private String lname;
    private char gender;
    private String countryDialingCode;
    private String primaryPhNum;
    private String secondaryPhNum;
    private String country;
    private String city;
    private String state;
    private String email;
    public String username;
    private DefaultStreamedContent downloadResume;
    private String password;
    private UploadedFile imageUpload;
    private DefaultStreamedContent imageDisplay;
    private double GPA;
    private int SAT;
    private int ACT;
    private int TOEFL;
    private int GRE;
    private double IELTS;    

    
    /**
     * @return the fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * @param fname the fname to set
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * @return the lname
     */
    public String getLname() {
        return lname;
    }

    /**
     * @param lname the lname to set
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * @return the gender
     */
    public char getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(char gender) {
        this.gender = gender;
    }

    /**
     * @return the countryDialingCode
     */
    public String getCountryDialingCode() {
        return countryDialingCode;
    }

    /**
     * @param countryDialingCode the countryDialingCode to set
     */
    public void setCountryDialingCode(String countryDialingCode) {
        this.countryDialingCode = countryDialingCode;
    }

    /**
     * @return the primaryPhNum
     */
    public String getPrimaryPhNum() {
        return primaryPhNum;
    }

    /**
     * @param primaryPhNum the primaryPhNum to set
     */
    public void setPrimaryPhNum(String primaryPhNum) {
        this.primaryPhNum = primaryPhNum;
    }

    /**
     * @return the secondaryPhNum
     */
    public String getSecondaryPhNum() {
        return secondaryPhNum;
    }

    /**
     * @param secondaryPhNum the secondaryPhNum to set
     */
    public void setSecondaryPhNum(String secondaryPhNum) {
        this.secondaryPhNum = secondaryPhNum;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;

    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    

    /**
     * @return the downloadResume
     */
    public DefaultStreamedContent getDownloadResume() {
        return downloadResume;
    }

    /**
     * @param downloadResume the downloadResume to set
     */
    public void setDownloadResume(DefaultStreamedContent downloadResume) {
        this.downloadResume = downloadResume;
    }

    /**
     * @return the imageUpload
     */
    public UploadedFile getImageUpload() {        
        return imageUpload;
    }

    /**
     * @param imageUpload the imageUpload to set
     */
    public void setImageUpload(UploadedFile imageUpload) {
        this.imageUpload = imageUpload;
    }

    /**
     * @return the imageDisplay
     */
    public DefaultStreamedContent getImageDisplay() {
        return imageDisplay;
    }

    /**
     * @param imageDisplay the imageDisplay to set
     */
    public void setImageDisplay(DefaultStreamedContent imageDisplay) {
        this.imageDisplay = imageDisplay;
    }

    /**
     * @return the gpa
     */
    public double getGpa() {
        return GPA;
    }

    /**
     * @param gpa the gpa to set
     */
    public void setGpa(double gpa) {
        DecimalFormat df = new DecimalFormat("#.##");        
        this.GPA = Double.parseDouble(df.format(gpa));
    }

    /**
     * @return the sat
     */
    public int getSAT() {
        return SAT;
    }

    /**
     * @param SAT the sat to set
     */
    public void setSAT(int SAT) {
        this.SAT = SAT;
    }

    /**
     * @return the act
     */
    public int getACT() {
        return ACT;
    }

    /**
     * @param ACT the act to set
     */
    public void setACT(int ACT) {
        this.ACT = ACT;
    }

    /**
     * @return the gre
     */
    public int getGRE() {
        return GRE;
    }

    /**
     * @param GRE the gre to set
     */
    public void setGRE(int GRE) {
        this.GRE = GRE;
    }

    /**
     * @return the ielts
     */
    public double getIelts() {
        return IELTS;
    }

    /**
     * @param ielts the ielts to set
     */
    public void setIelts(double ielts) {
        DecimalFormat df = new DecimalFormat("#.##");        
        this.IELTS = Double.parseDouble(df.format(ielts));       
    }

    /**
     * @return the toefl
     */
    public int getTOEFL() {
        return TOEFL;
    }

    /**
     * @param TOEFL the toefl to set
     */
    public void setTOEFL(int TOEFL) {
        this.TOEFL = TOEFL;
    }
}


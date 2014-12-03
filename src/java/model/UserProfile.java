/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.File;
import javax.mail.Part;

/**
 *
 * @author skdonep
 */
public class UserProfile {
    public byte[] profileImage;
    public String fName;
    public String lName;
    public char gender;
    public String countryDialingCode;
    public String primaryPhNum;
    public String secondaryPhNum;
    public String Country;
    public String City;
    public String State;
    public String email;
    public String username;
    public String password;
    private Part resume;

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountryDialingCode() {
        return countryDialingCode;
    }

    public void setCountryDialingCode(String countryDialingCode) {
        this.countryDialingCode = countryDialingCode;
    }

    public String getPrimaryPhNum() {
        return primaryPhNum;
    }

    public void setPrimaryPhNum(String primaryPhNum) {
        this.primaryPhNum = primaryPhNum;
    }

    public String getSecondaryPhNum() {
        return secondaryPhNum;
    }

    public void setSecondaryPhNum(String secondaryPhNum) {
        this.secondaryPhNum = secondaryPhNum;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the resume
     */
    public Part getResume() {
        return resume;
    }

    /**
     * @param resume the resume to set
     */
    public void setResume(Part resume) {
        this.resume = resume;
    }
    
}

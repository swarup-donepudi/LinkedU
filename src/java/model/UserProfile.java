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
public class UserProfile {
    String fName;
    String lName;
    char gender;
    int primaryPhCountryCode;
    int primaryPhNum;
    int secondaryPhCountryCode;
    int secondaryPhNum;
    String Country;
    String City;
    String State;

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

    public int getPrimaryPhCountryCode() {
        return primaryPhCountryCode;
    }

    public void setPrimaryPhCountryCode(int primaryPhCountryCode) {
        this.primaryPhCountryCode = primaryPhCountryCode;
    }

    public int getPrimaryPhNum() {
        return primaryPhNum;
    }

    public void setPrimaryPhNum(int primaryPhNum) {
        this.primaryPhNum = primaryPhNum;
    }

    public int getSecondaryPhCountryCode() {
        return secondaryPhCountryCode;
    }

    public void setSecondaryPhCountryCode(int secondaryPhCountryCode) {
        this.secondaryPhCountryCode = secondaryPhCountryCode;
    }

    public int getSecondaryPhNum() {
        return secondaryPhNum;
    }

    public void setSecondaryPhNum(int secondaryPhNum) {
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
    
    
}

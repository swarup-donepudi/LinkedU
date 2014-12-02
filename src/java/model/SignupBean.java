/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author skdonep
 */
@Named(value = "signupBean")
@SessionScoped

public class SignupBean implements Serializable {
    String userName;
    String password;
    String eMail;
    char accountType;
    private char acc_status;
    private String verifyString;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public char getAccountType() {
        return accountType;
    }

    public void setAccountType(char accountType) {
        this.accountType = accountType;
    }   

    /**
     * @return the acc_status
     */
    public char getAcc_status() {
        return acc_status;
    }

    /**
     * @param acc_status the acc_status to set
     */
    public void setAcc_status(char acc_status) {
        this.acc_status = acc_status;
    }

    /**
     * @return the verifyString
     */
    public String getVerifyString() {
        return verifyString;
    }

    /**
     * @param verifyString the verifyString to set
     */
    public void setVerifyString(String verifyString) {
        this.verifyString = verifyString;
    }
}

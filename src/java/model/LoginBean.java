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
public class LoginBean {
    private String userName;
    private String password;
    private char accountType;
    
    public LoginBean(){
        
    }    
    public LoginBean(String userName,String password,char accountType){
        this.userName=userName;
        this.password=password;
        this.accountType=accountType;
    }

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

    public char getAccountType() {
        return accountType;
    }

    public void setAccountType(char accountType) {
        this.accountType = accountType;
    }
    
}

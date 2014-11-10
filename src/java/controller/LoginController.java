/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import model.LoginBean;

/**
 *
 * @author skdonep
 */
@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {
    
    LoginBean loginBean;

    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
        loginBean = new LoginBean();
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
    
    public void validateCredentials(){
    
    }
    
    public void signUpValidation(){
        
    }
    
}

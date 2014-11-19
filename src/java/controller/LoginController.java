/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.LoginDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.LoginBean;

/**
 *
 * This class will be a controller for all login related functions
 *
 * @author hgindra
 */
@ManagedBean(name = "loginController")
@SessionScoped

public class LoginController {

    private String errorMessage;
    private boolean loggedIn;
    private LoginBean loginBean;

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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void validateCredentials() throws IOException, SQLException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        LoginDAO login = new LoginDAO();
        if (login.validCredentials(loginBean.getUserName(), loginBean.getPassword())) {
            if (login.getAccountType(loginBean.getUserName()) == 'S') {
                loginBean.setAccountType('S');
                externalContext.redirect("StudentHome.xhtml");
            } else {
                loginBean.setAccountType('R');
                externalContext.redirect("RecruiterHome.xhtml");
            }
            this.setLoggedIn(true);
        } else {
            this.errorMessage = "Invalid Username/Password";
            externalContext.redirect("LoginFailed.xhtml");
        }

    }

    public void signUpValidation() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("SignedUp.xhtml");

    }

    public void createStudentProfile() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("CreateProfileStudent.xhtml");
    }

}

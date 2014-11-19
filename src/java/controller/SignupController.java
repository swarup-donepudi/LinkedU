/*
 * To changee this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.SignupDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import mail.SendMail;
import model.SignupBean;

/**
 *
 * @author hgindra
 */
@ManagedBean(name = "signupController")
@SessionScoped
public class SignupController {

    SignupBean signupBean;
    String usernameMsg;
    @ManagedProperty(value = "#{loginController}")
    private LoginController loginController;
    private SendMail mailObj;

    /**
     * Creates a new instance of SignupController
     */
    public SignupController() {
        signupBean = new SignupBean();
        mailObj = new SendMail();
    }

    public SignupBean getSignupBean() {
        return signupBean;
    }

    public void setSignupBean(SignupBean signupBean) {
        this.signupBean = signupBean;
    }

    public String getUsernameMsg() {
        return usernameMsg;
    }

    public void setUsernameMsg(String usernameMsg) {
        this.usernameMsg = usernameMsg;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public String checkDuplicateUsername() throws SQLException {
        SignupDAO signupDB = new SignupDAO();
        if (signupDB.usernameAlreadyExists(this.signupBean.getUserName())) {
            usernameMsg = "Username Already Exists";
        } else {
            usernameMsg = "";
        }
        return usernameMsg;
    }

    public void signUpValidation() throws IOException, SQLException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        SignupDAO create = new SignupDAO();
        int count = create.createAccount(signupBean);
        triggerMail();        
        if (count == 1) {
            loginController.getLoginBean().setUserName(signupBean.getUserName());
            loginController.getLoginBean().setAccountType(signupBean.getAccountType());
            if (signupBean.getAccountType() == 'S') {
                externalContext.redirect("StudentHome.xhtml");
            } else {
                externalContext.redirect("RecruiterHome.xhtml");
            }
        } else {
            externalContext.redirect("Error.xhtml");
        }
    }
    
    public void triggerMail(){
        //mailObj.triggerMail(theModel.getUserID(), theModel.getFirstName(), theModel.getLastName(), theModel.getPassword(), theModel.getEmail());
        mailObj.triggerMail(signupBean.getUserName(), signupBean.geteMail());
    }
}

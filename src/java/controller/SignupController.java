/*
 * To change this license header, choose License Headers in Project Properties.
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

    /**
     * Creates a new instance of SignupController
     */
    public SignupController() {
        signupBean = new SignupBean();
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
    
    public void checkDuplicateUsername() throws SQLException{
       SignupDAO signupDB = new SignupDAO();
       if(signupDB.usernameAlreadyExists(this.signupBean.getUserName())){
           
       }
    }

    public void signUpValidation() throws IOException, SQLException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        SignupDAO create = new SignupDAO();
        int count = create.createAccount(signupBean);
        if(count == 1){
            loginController.getLoginBean().setUserName(signupBean.getUserName());
            loginController.getLoginBean().setAccountType(signupBean.getAccountType());
            if(signupBean.getAccountType()=='S'){
                externalContext.redirect("StudentHome.xhtml");
            }
            else{
                externalContext.redirect("RecruiterHome.xhtml");
            }
        }
        else{ 
            externalContext.redirect("Error.xhtml");
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import DAO.LoginImpl;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.LoginBean;

/**
 *
 * @author hgindra
 */
@ManagedBean(name = "loginController")
@SessionScoped

public class LoginController {
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
    
    public void validateCredentials() throws IOException{
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        LoginImpl login=new LoginImpl();
        char check=login.loginQuery(loginBean.getUserName(), loginBean.getPassword());
        if(check=='S')
            externalContext.redirect("StudentHome.xhtml");
        if(check=='R')
            externalContext.redirect("RecruiterHome.xhtml");
        else
            externalContext.redirect("Error.xhtml");
            
    }
    
    public void signUpValidation() throws IOException{
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();       
        externalContext.redirect("SignedUp.xhtml");  
       
        
    }
    
    public void createStudentProfile() throws IOException{
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("CreateProfileStudent.xhtml");  
    }
    
}

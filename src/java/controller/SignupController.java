/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import DAO.CreateImpl;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
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

    public void signUpValidation() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        CreateImpl create=new CreateImpl();
        int count=create.createProfile(signupBean);
//        if(count==1)
        externalContext.redirect("SignedUp.xhtml");  
//        else 
//            externalContext.redirect("Error.xhtml");
//        
//        externalContext.redirect("SignedUp.xhtml");
    }
}

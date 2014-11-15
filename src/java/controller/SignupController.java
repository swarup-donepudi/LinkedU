/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.SignupBean;

/**
 *
 * @author skdonep
 */
@Named(value = "signupController")
@SessionScoped
public class SignupController implements Serializable {
    SignupBean signupBean;
    /**
     * Creates a new instance of SignupController
     */
    public SignupController() {
    }

    public SignupBean getSignupBean() {
        return signupBean;
    }

    public void setSignupBean(SignupBean signupBean) {
        this.signupBean = signupBean;
    }

    public void signUpValidation() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("SignedUp.xhtml");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author skdonep
 */
@ManagedBean(name = "logoutController")
@SessionScoped
public class LogoutController implements Serializable {

    /**
     * Creates a new instance of LogoutController
     */
    public LogoutController() {
    }

    public String logoutUser() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        if (session.getAttribute("loggedIn").equals("true")) {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        }
        return "index?faces-redirect=true";
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author skdonep
 */
@ManagedBean(name = "navigationController")
@SessionScoped
public class NavigationController implements Serializable {

    /**
     * Creates a new instance of NavigationController
     */
    public NavigationController() {
    }

    public String redirectToUserHomepage() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        char accType = session.getAttribute("LinkEDU_AccType").toString().charAt(0);
        if (accType == 'S') {
            return ("StudentHome.xhtml");
        } else {
            return ("RecruiterHome.xhtml");
        }
    }

    public void redirectToSenderProfile(String username) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        char accType = session.getAttribute("LinkEDU_AccType").toString().charAt(0);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String redirectURL;
        if (accType == 'S') {
            redirectURL = "RecruiterProfileForStudent.xhtml?selectedUsername=" + username;
            externalContext.redirect(redirectURL);
        } else {
            redirectURL = "StudentProfileForRecruiter.xhtml?selectedUsername=" + username;
            externalContext.redirect(redirectURL);
        }
    }
}

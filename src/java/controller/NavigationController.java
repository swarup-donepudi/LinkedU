/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author skdonep
 */
@ManagedBean(name = "navigationController")
@SessionScoped
public class NavigationController implements Serializable {

    @ManagedProperty(value = "#{loginController}")
    private LoginController loginController;

    /**
     * Creates a new instance of NavigationController
     */
    public NavigationController() {
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public String redirectToUserHomepage() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        String linkEDUUsername = session.getAttribute("username").toString();
        if ((!linkEDUUsername.equals("")) && (!linkEDUUsername.equals(""))) {
            if (loginController.getLoginBean().getAccountType() == 'S') {
                return ("StudentHome.xhtml");
            } else {
                return ("RecruiterHome.xhtml");
            }
        } else {
            return ("index.xhtml");
        }
    }
}

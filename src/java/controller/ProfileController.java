/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProfileDAO;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import model.RecruiterProfile;
import model.StudentProfile;
import model.UserProfile;

/**
 *
 * @author skdonep
 */
@Named(value = "profileController")
@SessionScoped
public class ProfileController implements Serializable {

    @ManagedProperty(value = "#{loginController}")
    private LoginController loginController;

    private UserProfile profile;
    
    /**
     * Creates a new instance of EditProfileController
     */
    public ProfileController() {
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public void editProfile(char accountType) throws IOException, SQLException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        if (this.userHasProfile()) {
            this.fetchUserProfile();
        }
        if (loginController.getLoginBean().getAccountType() == 'S') {
            externalContext.redirect("EditProfileStudent.xhtml");
        } else {
            externalContext.redirect("EditProfileRecruiter.xhtml");
        }
    }

    public boolean userHasProfile() throws SQLException {
        ProfileDAO profileDao = new ProfileDAO();
        return profileDao.userHasProfile(loginController.getLoginBean().getAccountType());
    }

    public void fetchUserProfile() {
    }
}

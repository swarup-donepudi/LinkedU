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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.RecruiterProfile;
import model.StudentProfile;
import model.UserProfile;

/**
 *
 * @author skdonep
 */
@ManagedBean(name = "profileController")
@SessionScoped
public class ProfileController implements Serializable {

    private String profileUpdateMessage;

    @ManagedProperty(value = "#{loginController}")
    private LoginController loginController;
    
    private StudentProfile studentProfile;
    private RecruiterProfile recruiterProfile;

    /**
     * Creates a new instance of EditProfileController
     */
    public ProfileController() {
        studentProfile = new StudentProfile();
                recruiterProfile = new RecruiterProfile();
    }

    public StudentProfile getStudentProfile() {
        return studentProfile;
    }

    public void setStudentProfile(StudentProfile studentProfile) {
        this.studentProfile = studentProfile;
    }

    public RecruiterProfile getRecruiterProfile() {
        return recruiterProfile;
    }

    public void setRecruiterProfile(RecruiterProfile recruiterProfile) {
        this.recruiterProfile = recruiterProfile;
    }

    
    public String getProfileUpdateMessage() {
        return profileUpdateMessage;
    }

    public void setProfileUpdateMessage(String profileUpdateMessage) {
        this.profileUpdateMessage = profileUpdateMessage;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void editProfile(char accountType, String username) throws IOException, SQLException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        if (this.userHasProfile(accountType, username)) {
            this.fetchUserProfile(accountType, username);
        }
        if (accountType == 'S') {
            externalContext.redirect("EditStudentProfile.xhtml");
        } else {
            externalContext.redirect("EditRecruiterProfile.xhtml");
        }
    }

    public boolean userHasProfile(char accountType, String username) throws SQLException {
        ProfileDAO profileDao = new ProfileDAO();
        if (accountType == 'S') {
            return profileDao.studentHasProfile(username);
        } else if (accountType == 'R') {
            return profileDao.recruiterHasProfile(username);
        }
        return false;
    }

    public void fetchUserProfile(char accountType, String username) throws SQLException {
        ProfileDAO profileDao = new ProfileDAO();
        if (accountType == 'S') {
            this.studentProfile = profileDao.fetchStudentProfile(username);
        }
        if (accountType == 'R') {
            this.recruiterProfile= profileDao.fetchRecruiterProfile(username);
        }        
    }

    public void updateProfile(char accountType, String username) throws SQLException {
        ProfileDAO profileDao = new ProfileDAO();
        if (this.userHasProfile(accountType, username)) {
            if (accountType == 'S') {
                profileDao.updateStudentProfile(this.studentProfile, username);
            }
            if (accountType == 'R') {
                profileDao.updateRecruiterProfile(this.recruiterProfile, username);
            } 
        }
        else
        {
            if (accountType == 'S') {
                profileDao.createStudentProfile(this.studentProfile,username);
            }
            if (accountType == 'R') {
                profileDao.createRecruiterProfile(this.recruiterProfile,username);
            }             
        }
        this.profileUpdateMessage = "Profile has been updated successfully!!";
    }
}

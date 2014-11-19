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
import model.RecruiterProfile;
import model.StudentProfile;

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

    public String showStudentHisProfile() throws IOException, SQLException {
        ProfileDAO profileDao = new ProfileDAO();
        if (profileDao.studentHasProfile(loginController.loginBean.getUserName())) {
            this.studentProfile = profileDao.fetchStudentProfile(loginController.loginBean.getUserName());
        }
        return ("StudentProfile.xhtml");
    }

    public String showRecruiterHisProfile() throws IOException, SQLException {
        ProfileDAO profileDao = new ProfileDAO();
        if (profileDao.recruiterHasProfile(loginController.loginBean.getUserName())) {
            this.recruiterProfile = profileDao.fetchRecruiterProfile(loginController.loginBean.getUserName());
        }
        return ("RecruiterProfile.xhtml");
    }

    public void updateStudentProfile() throws SQLException {
        ProfileDAO profileDao = new ProfileDAO();
        if (profileDao.studentHasProfile(loginController.loginBean.getUserName())) {
            profileDao.updateStudentProfile(this.studentProfile, loginController.loginBean.getUserName());
        } else {
            profileDao.createStudentProfile(this.studentProfile, loginController.loginBean.getUserName());
        }
        this.profileUpdateMessage="Profile updated successfully.";
    }
    
    public void updateRecruiterProfile() throws SQLException {
        ProfileDAO profileDao = new ProfileDAO();
        if (profileDao.recruiterHasProfile(loginController.loginBean.getUserName())) {
            profileDao.updateRecruiterProfile(this.recruiterProfile, loginController.loginBean.getUserName());
        } else {
            profileDao.createRecruiterProfile(this.recruiterProfile, loginController.loginBean.getUserName());
        }
        this.profileUpdateMessage="Profile updated successfully.";
    }    
}

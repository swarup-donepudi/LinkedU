/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.LoginDAO;
import dao.RecruiterDAO;
import dao.StudentDAO;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.LoginBean;
import model.RecruiterProfile;
import model.StudentProfile;

/**
 *
 * This class will be a controller for all login related functions
 *
 * @author hgindra
 */
@ManagedBean(name = "loginController")
@ApplicationScoped
public class LoginController implements Serializable{

    @ManagedProperty(value = "#{recruiterController}")
    private RecruiterController recruiterController;
    
    @ManagedProperty(value = "#{studentController}")
    private StudentController studentController;
    
    private String errorMessage;
    private boolean loggedIn;
    private LoginBean loginBean;

    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
        loginBean = new LoginBean();
    }

    public RecruiterController getRecruiterController() {
        return recruiterController;
    }

    public void setRecruiterController(RecruiterController recruiterController) {
        this.recruiterController = recruiterController;
    }

    public StudentController getStudentController() {
        return studentController;
    }

    public void setStudentController(StudentController studentController) {
        this.studentController = studentController;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void validateCredentials() throws IOException, SQLException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        LoginDAO loginDB = new LoginDAO();
        if (loginDB.validCredentials(loginBean.getUserName(), loginBean.getPassword())) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            session.setAttribute("loggedIn", "true");
            this.setLoggedIn(true);
            if (loginDB.getAccountType(loginBean.getUserName()) == 'S') {
                loginBean.setAccountType('S');
                StudentProfile studentProfile;
                StudentDAO profileDB = new StudentDAO();
                String studentUsername = this.loginBean.getUserName();
                if (profileDB.studentHasProfile(studentUsername)) {
                    studentProfile = profileDB.fetchStudentProfile(studentUsername);
                } else {
                    studentProfile = new StudentProfile();
                }
                this.studentController.setStudentProfile(studentProfile);                
                externalContext.redirect("StudentHome.xhtml");
            } else {
                loginBean.setAccountType('R');
                RecruiterProfile recruiterProfile;
                RecruiterDAO profileDB = new RecruiterDAO();
                String recruiterUsername = this.loginBean.getUserName();
                if (profileDB.recruiterHasProfile(recruiterUsername)) {
                    recruiterProfile = profileDB.fetchRecruiterProfile(recruiterUsername);
                } else {
                    recruiterProfile = new RecruiterProfile();
                }
                this.recruiterController.setRecruiterProfile(recruiterProfile);
                externalContext.redirect("RecruiterHome.xhtml");
            }

        } else {
            this.errorMessage = "Invalid Username/Password";
            externalContext.redirect("LoginFailed.xhtml");
        }
    }
}

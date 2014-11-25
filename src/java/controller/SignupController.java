/*
 * To changee this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.RecruiterDAO;
import dao.SignupDAO;
import dao.StudentDAO;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.RecruiterProfile;
import model.SignupBean;
import model.StudentProfile;

/**
 *
 * @author hgindra
 */
@ManagedBean(name = "signupController")
@SessionScoped
public class SignupController implements Serializable{
    
    @ManagedProperty(value = "#{recruiterController}")
    private RecruiterController recruiterController;
    
    @ManagedProperty(value = "#{studentController}")
    private StudentController studentController;    

    SignupBean signupBean;
    String usernameMsg;

    /**
     * Creates a new instance of SignupController
     */
    public SignupController() {
        signupBean = new SignupBean();
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

    public SignupBean getSignupBean() {
        return signupBean;
    }

    public void setSignupBean(SignupBean signupBean) {
        this.signupBean = signupBean;
    }

    public String getUsernameMsg() {
        return usernameMsg;
    }

    public void setUsernameMsg(String usernameMsg) {
        this.usernameMsg = usernameMsg;
    }

    public String checkDuplicateUsername() throws SQLException {
        SignupDAO signupDB = new SignupDAO();
        if (signupDB.usernameAlreadyExists(this.signupBean.getUserName())) {
            usernameMsg = "Username Already Exists";
        } else {
            usernameMsg = "";
        }
        return usernameMsg;
    }

    public void signUpValidation() throws IOException, SQLException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        SignupDAO create = new SignupDAO();
        int count = create.createAccount(signupBean);
        this.triggerSignupMail();        
        if (count == 1) {
            if (signupBean.getAccountType() == 'S') {
                StudentProfile studentProfile;
                StudentDAO profileDB = new StudentDAO();
                String studentUsername = this.signupBean.getUserName();
                if (profileDB.studentHasProfile(studentUsername)) {
                    studentProfile = profileDB.fetchStudentProfile(studentUsername);
                } else {
                    studentProfile = new StudentProfile();
                }
                this.studentController.setStudentProfile(studentProfile);                
                externalContext.redirect("StudentHome.xhtml");
            } else {
                RecruiterProfile recruiterProfile;
                RecruiterDAO recruiterDB = new RecruiterDAO();
                String recruiterUsername = this.signupBean.getUserName();
                if (recruiterDB.recruiterHasProfile(recruiterUsername)) {
                    recruiterProfile = recruiterDB.fetchRecruiterProfile(recruiterUsername);
                } else {
                    recruiterProfile = new RecruiterProfile();
                }
                this.recruiterController.setRecruiterProfile(recruiterProfile);                
                externalContext.redirect("RecruiterHome.xhtml");
            }
        } else {
            externalContext.redirect("Error.xhtml");
        }
    }
    
    public void triggerSignupMail(){
        EmailController ec = new EmailController();
        ec.triggerMail(signupBean.getUserName(), signupBean.geteMail());
    }
}

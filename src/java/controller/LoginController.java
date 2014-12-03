/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CommonDAO;
import dao.LoginDAO;
import dao.RecruiterDAO;
import dao.StudentDAO;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Random;
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
public class LoginController implements Serializable {

    @ManagedProperty(value = "#{recruiterController}")
    private RecruiterController recruiterController;
    @ManagedProperty(value = "#{studentController}")
    private StudentController studentController;
    private String errorMessage;
    private boolean loggedIn;
    private LoginBean loginBean;
    private boolean rememberMe;

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

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
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
    
    public void sendForgotPasswordEmail() throws SQLException, IOException{
        setErrorMessage("");
        LoginDAO loginDB = new LoginDAO();
        String username = loginDB.verifyEmailID(loginBean.getUserName());
        if(!username.equals("")){
            String verifyString = this.generateRandonString();
            int count = loginDB.addVerificationDetails(username, verifyString);
            String link = "http://localhost:8080/LinkedU/faces/ChangePassword.xhtml?fgetLink="+verifyString;
            EmailController sendemail = new EmailController();
            sendemail.mail(loginBean.getUserName(), "Change password", this.mailBody(link));
            setErrorMessage("Email Sent");
        }
        else{
            setErrorMessage("Something went wrong. Please retry");
        }
        
    }

    public String mailBody(String link){
        String msg = "<img src=\"https://s3-us-west-1.amazonaws.com/swarup921/linkedULogo.png\"/><br /><br />Click the following link to reset your password.<br /><br />"
                + "Click&nbsp<bold><a href ="+link+">here</a><bold>&nbsp to reset you password.</a><br/> This Link will expire once you change your password"
                + " or if not changed will expire in 24 hours.<br/><br/> Thank you<br/>LinkEDU Team";
        return msg;
    }
    
    public String generateRandonString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 15;
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (new Random().nextFloat() * (rightLimit - leftLimit));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    public void validateCredentials() throws IOException, SQLException, ParseException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        LoginDAO loginDB = new LoginDAO();
        if (loginDB.validCredentials(loginBean.getUserName(), loginBean.getPassword())) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            session.setAttribute("loggedIn", "true");
            session.setAttribute("username", loginBean.getUserName());
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
                    studentProfile.setUsername(studentUsername);
                    CommonDAO commonDB = new CommonDAO();
                    String studentEmail = commonDB.getEmailFromUserInfoTable(studentUsername);
                    studentProfile.setEmail(studentEmail);
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

    public void checkSessionStatus() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        if (!session.getAttribute("loggedIn").toString().equals("true")) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect("index.xhtml");
        }
    }
    
    public void verifyLink() throws SQLException, IOException{
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        LoginDAO loginDB = new LoginDAO();
        String username = loginDB.verifyForgotPasswordLink(loginBean.getVerifyLink());
        if(!username.equals("")){
            loginBean.setUserName(username);
            loginDB.deleteVerificationData(username);
        }
        else{
            externalContext.redirect("InvalidVerificationLink.xhtml");
        }
    }
    
    public void changePassword() throws SQLException, IOException{
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        LoginDAO loginDB = new LoginDAO();
        int count = loginDB.changePasswordLogin(loginBean.getUserName(), loginBean.getPassword());
        if(count==1){
            externalContext.redirect("index.xhtml");
        }
        else{
            externalContext.redirect("PlsRetryAgain.xhtml");
        }
        
    }
}

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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
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
@SessionScoped
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

    public void sendForgotPasswordEmail() throws SQLException, IOException {
        setErrorMessage("");
        LoginDAO loginDB = new LoginDAO();
            String username = loginDB.verifyEmailID(loginBean.getUserName());
        if (!username.equals("")) {
            String verifyString = this.generateRandonString();
            int count = loginDB.addVerificationDetails(username, verifyString);
            String link = "http://gfish2.it.ilstu.edu/mananda_Fall14_LinkedU3/faces/ChangePassword.xhtml?fgetLink=" + verifyString;
            EmailController sendemail = new EmailController();
            sendemail.mail(loginBean.getUserName(), "Change password", this.mailBody(link));
            setErrorMessage("Email Sent");
        } else {
            setErrorMessage("Something went wrong. Please retry");
        }

    }

    public String mailBody(String link) {
        String msg = "<img src=\"https://s3-us-west-1.amazonaws.com/swarup921/linkedULogo.png\"/><br /><br />Click the following link to reset your password.<br /><br />"
                + "Click<bold><a href =" + link + ">here</a><bold> to reset you password.</a><br/> This Link will expire once you change your password"
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
        String username = loginBean.getUserName();
        String password = loginBean.getPassword();
        char accStatus = 'N';
        char accType = 'N';
        boolean validLogin = true;
        validLogin = this.validLoginCredentials(username, password);
        if (validLogin) {
            accStatus = this.checkAccountStatus(username);
            accType = this.checkAccountType(username);
            this.loadUserProfile(username, accType);
            this.setSessionVariables(username, accType);
        }
        this.redirectToNextPage(validLogin, accStatus, accType);
    }

    public boolean validLoginCredentials(String username, String password) throws SQLException, IOException {
        LoginDAO loginDB = new LoginDAO();
        return (loginDB.validCredentials(username, password));
    }

    private char checkAccountStatus(String username) throws IOException {
        CommonDAO commonDB = new CommonDAO();
        return (commonDB.getAccountStatusFromDB(username));
    }

    private char checkAccountType(String username) throws IOException {
        CommonDAO commonDB = new CommonDAO();
        return (commonDB.getAccountTypeFromDB(username));
    }

    private void setSessionVariables(String username, char AccType) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        session.setAttribute("loggedIn", "true");
        session.setAttribute("LinkEDU_AccType", AccType);
        session.setAttribute("LinkEDU_Username", loginBean.getUserName());
    }

    private void loadUserProfile(String username, char accType) throws SQLException, ParseException, IOException {
        if (accType == 'S') {
            StudentProfile studentProfile;
            StudentDAO studentDB = new StudentDAO();
            String studentUsername = this.loginBean.getUserName();
            if (studentDB.studentHasProfile(studentUsername)) {
                studentProfile = studentDB.fetchStudentProfile(studentUsername);
            } else {
                studentProfile = new StudentProfile();
                studentProfile.setUsername(studentUsername);
                CommonDAO commonDB = new CommonDAO();
                String studentEmail = commonDB.getEmailFromUserInfoTable(studentUsername);
                studentProfile.setEmail(studentEmail);
            }
            this.studentController.setStudentProfile(studentProfile);
        }
        if (accType == 'R') {
            RecruiterProfile recruiterProfile;
            RecruiterDAO recruiterDB = new RecruiterDAO();
            if (recruiterDB.recruiterHasProfile(username)) {
                recruiterProfile = recruiterDB.fetchRecruiterProfile(username);
            } else {
                recruiterProfile = new RecruiterProfile();
                recruiterProfile.setUsername(username);
                CommonDAO commonDB = new CommonDAO();
                String recruiterEmail = commonDB.getEmailFromUserInfoTable(username);
                recruiterProfile.setEmail(recruiterEmail);
            }
            this.recruiterController.setRecruiterProfile(recruiterProfile);
        }
    }

    private void redirectToNextPage(boolean validLogin, char accStatus, char accType) throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        if (validLogin) {
            if (accStatus == 'A') {
                if (accType == 'S') {
                    externalContext.redirect("StudentHome.xhtml");
                } else if (accType == 'R') {
                    externalContext.redirect("RecruiterHome.xhtml");
                }
            } else if (accStatus == 'I') {
                externalContext.redirect("InactiveAccount.xhtml");
            }
        } else {
            externalContext.redirect("LoginFailed.xhtml");
        }
    }

    public void checkSessionStatus() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        if (session.getAttribute("loggedIn") != null) {
            if (!session.getAttribute("loggedIn").toString().equals("true")) {
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                externalContext.redirect("index.xhtml");
            }
        } else {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect("index.xhtml");
        }
    }

    public void verifyLink() throws SQLException, IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        LoginDAO loginDB = new LoginDAO();
        String username = loginDB.verifyForgotPasswordLink(loginBean.getVerifyLink());
        if (!username.equals("")) {
            loginBean.setUserName(username);
            loginDB.deleteVerificationData(username);
        } else {
            externalContext.redirect("InvalidVerificationLink.xhtml");
        }
    }

    public void changePassword() throws SQLException, IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        LoginDAO loginDB = new LoginDAO();
        int count = loginDB.changePasswordLogin(loginBean.getUserName(), loginBean.getPassword());
        if (count == 1) {
            externalContext.redirect("index.xhtml");
        }

    }
}

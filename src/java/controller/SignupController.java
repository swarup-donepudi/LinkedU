/*
 * To changee this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.SignupDAO;
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
import model.SignupBean;

/**
 *
 * @author hgindra
 */
@ManagedBean(name = "signupController")
@SessionScoped
public class SignupController implements Serializable {

    @ManagedProperty(value = "#{recruiterController}")
    private RecruiterController recruiterController;
    @ManagedProperty(value = "#{studentController}")
    private StudentController studentController;
    private SignupBean signupBean;
    private String usernameMsg;
    private boolean duplicateUsername;
    private String emailMsg;

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

    public String getEmailMsg() {
        return emailMsg;
    }

    /**
     * @param emailMsg the emailMsg to set
     */
    public void setEmailMsg(String emailMsg) {
        this.emailMsg = emailMsg;
    }

    public boolean isDuplicateUsername() {
        return duplicateUsername;
    }

    public void setDuplicateUsername(boolean duplicateUsername) {
        this.duplicateUsername = duplicateUsername;
    }

    public String getUsernameMsg() throws SQLException {
        FacesContext externalContext = FacesContext.getCurrentInstance();
        if (externalContext.isPostback()) {
            this.checkDuplicateUsername();
            if (this.signupBean.getUserName() != null) {
                if (this.signupBean.getUserName().equals("")) {
                    this.usernameMsg = "";
                }
            }
        }
        return usernameMsg;
    }

    public void setUsernameMsg(String usernameMsg) {
        this.usernameMsg = usernameMsg;
    }

    public void checkDuplicateUsername() throws SQLException {
        FacesContext externalContext = FacesContext.getCurrentInstance();
        if (externalContext.isPostback()) {
            SignupDAO signupDB = new SignupDAO();
            if (signupDB.usernameAlreadyExists(this.signupBean.getUserName())) {
                this.usernameMsg = "Username Already Exists";
                this.duplicateUsername = true;
            } else {
                this.duplicateUsername = false;
                this.usernameMsg = "";
            }
        }
    }

    public void checkDuplicateEmail() throws SQLException {
        SignupDAO signupDB = new SignupDAO();
        if (signupDB.emailAlreadyExits(this.signupBean.getEmail())) {
            this.setEmailMsg("Email Already Exists");
        } else {
            this.setEmailMsg("");
        }
    }

    public void signupUser() throws IOException, SQLException, ParseException {
        this.createUserInfo(this.signupBean);
        this.createLogin(this.signupBean);
        this.createVerificationString(this.signupBean);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("SignupSuccessful.xhtml");
    }

    public void createLogin(SignupBean user) throws SQLException {
        SignupDAO create = new SignupDAO();
        int count = create.createLoginAccount(user);
    }

    public void createUserInfo(SignupBean user) throws SQLException {
        SignupDAO create = new SignupDAO();
        int count = create.createUserAccount(user);
    }

    public void createVerificationString(SignupBean user) throws SQLException, IOException {
        SignupDAO verify = new SignupDAO();
        String randomString = this.generateRandonString();
        String link = "http://localhost:8080/LinkedU/faces/ConfirmEmail.xhtml?verifylink=" + randomString;
        EmailController mailing = new EmailController();
        mailing.mail(user.getEmail(), "Verify your Email Address", this.mailBody(link));
        verify.insertVerificationDetails(user.getUserName(), randomString);
    }

    public void resendVerificationLink() throws SQLException, IOException {
        this.createVerificationString(signupBean);
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

    public String mailBody(String link) {
        String msg = "<img src=\"https://s3-us-west-1.amazonaws.com/swarup921/linkedULogo.png\"/><br /><br />Thank you for signing up on LinkEDU<br/>."
                + "Click <b><a href =" + link + ">here</a></b> to confirm email address.<br /><br/>"
                + "<br/> Thank you<br/>LinkEDU Team";
        return msg;
    }

    public void verifyLink() throws ClassNotFoundException, IOException, SQLException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        SignupDAO check = new SignupDAO();
        String username = check.checkLink(signupBean.getVerifyString());
        if (!username.equals("")) {
            check.updateAccStatus(username);
            check.deleteVerificationData(username);
        } else {
            externalContext.redirect("InvalidVerificationLink.xhtml");
        }
    }
}

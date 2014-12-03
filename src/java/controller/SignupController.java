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
    private boolean dusplicateUsername;
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

    public boolean isDusplicateUsername() {
        return dusplicateUsername;
    }

    public void setDusplicateUsername(boolean dusplicateUsername) {
        this.dusplicateUsername = dusplicateUsername;
    }

    public String getUsernameMsg() throws SQLException {
        this.checkDuplicateUsername();
        if (this.signupBean.getUserName() != null) {
            if (this.signupBean.getUserName().equals("")) {
                this.usernameMsg = "";
            }
        }
        return usernameMsg;
    }

    public void setUsernameMsg(String usernameMsg) {
        this.usernameMsg = usernameMsg;
    }

    public void checkDuplicateUsername() throws SQLException {
        SignupDAO signupDB = new SignupDAO();
        if (signupDB.usernameAlreadyExists(this.signupBean.getUserName())) {
            usernameMsg = "Username Already Exists";
            this.dusplicateUsername = true;
        } else {
            this.dusplicateUsername = false;
            usernameMsg = "";
            this.usernameMsg = "Username Already Exists";
        }
    }
    
    public void checkDuplicateEmail() throws SQLException {
        SignupDAO signupDB = new SignupDAO();
        if (signupDB.emailAlreadyExits(this.signupBean.geteMail())) {
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
        externalContext.redirect("abc.xhtml");
    }
    
    public void createLogin(SignupBean user) throws SQLException{
        SignupDAO create = new SignupDAO();
        int count = create.createLoginAccount(user);
    }
    
    public void createUserInfo(SignupBean user) throws SQLException{
        SignupDAO create = new SignupDAO();
        int count = create.createUserAccount(user);
    }
    
    public void createVerificationString(SignupBean user) throws SQLException, IOException{
        SignupDAO verify = new SignupDAO();
        String randomString = generateRandonString();
        String link = "http://localhost:8080/LinkedU/faces/ConfirmEmail.xhtml?verifylink="+randomString;
        if(verify.userAccStatus(user.geteMail())){        
        EmailController mailing = new EmailController();
        mailing.mail(user.geteMail(), "Verify your Email Address", mailBody(link));
        verify.insertVerificationDetails(user.getUserName(), randomString);
    }
        else{
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect("abc.xhtml");
        }
    }
    
    public void resendVerificationLink() throws SQLException, IOException{
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
    
    public String mailBody(String link){
        String msg = "This is the verification link. Pls click on the following link to reset your password<br/>."
                + "<a href ="+link+">Click Here to activate.</a><br/> This Link will expire once you change your password"
                + " or if not changed will expire in 24 hours.<br/><br/> Thank you<br/>Linkedu Team";
        return msg;
    }
    
    public void verifyLink() throws ClassNotFoundException, IOException, SQLException{
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        SignupDAO check = new SignupDAO();
        String username = check.checkLink(signupBean.getVerifyString());
        if(!username.equals("")){
            check.updateAccStatus(username);
            check.deleteVerificationData(username);
        }
        else{
            externalContext.redirect("InvalidVerificationLink.xhtml");
        }
    }
}


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.MessagesDAO;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.MessageBean;

/**
 *
 * @author skdonep
 */
@ManagedBean
@ApplicationScoped
public class MessageController {

    @ManagedProperty(value = "#{loginController}")
    private LoginController loginController;

    @ManagedProperty(value = "#{recruiterController}")
    private RecruiterController recruiterController;

    @ManagedProperty(value = "#{studentController}")
    private StudentController studentController;

    private MessageBean messageBean;

    private String msgSendStatus;

    private int unreadMsgsCount;

    private ArrayList<MessageBean> userInbox;

    private String toolTipInbox;

    /**
     * Creates a new instance of MessageController
     */
    public MessageController() {
        messageBean = new MessageBean();
        this.userInbox = new ArrayList<MessageBean>();
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
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

    public MessageBean getMessageBean() {
        return messageBean;
    }

    public void setMessageBean(MessageBean messageBean) {
        this.messageBean = messageBean;
    }

    public String getMsgSendStatus() {
        return msgSendStatus;
    }

    public void setMsgSendStatus(String msgSendStatus) {
        this.msgSendStatus = msgSendStatus;
    }

    public int getUnreadMsgsCount() throws ParseException, IOException {
        String username = loginController.getLoginBean().getUserName();
        this.unreadMsgsCount = this.fetchUnreadMsgsCount(username);
        this.toolTipInbox = "You have " + this.unreadMsgsCount + " unread messages";
        return unreadMsgsCount;
    }

    public void setUnreadMsgsCount(int unreadMsgsCount) {
        this.unreadMsgsCount = unreadMsgsCount;
    }

    public ArrayList<MessageBean> getUserInbox() {
        return userInbox;
    }

    public void setUserInbox(ArrayList<MessageBean> userInbox) {
        this.userInbox = userInbox;
    }

    public String getToolTipInbox() {
        return toolTipInbox;
    }

    public void setToolTipInbox(String toolTipInbox) {
        this.toolTipInbox = toolTipInbox;
    }

    public void insertMessage() throws IOException {
        String fromAddres = null;
        String toAddress = null;
        char status = 'N';
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        char accType = session.getAttribute("LinkEDU_AccType").toString().charAt(0);
        if (accType == 'S') {
            fromAddres = this.studentController.getStudentProfile().getUsername();
            toAddress = this.studentController.getSelectedRecruiter().getUsername();
        } else {
            fromAddres = this.recruiterController.getRecruiterProfile().getUsername();
            toAddress = this.recruiterController.getSelectedStudent().getUsername();
        }

        this.messageBean.setFromAddress(fromAddres);
        this.messageBean.setToAddress(toAddress);
        this.messageBean.setStatus(status);

        MessagesDAO messagesDB = new MessagesDAO();

        int messagesInserted = messagesDB.insertMessageIntoDB(this.messageBean);
        if (messagesInserted == 1) {
            this.msgSendStatus = "Message Sent Successfully";
        } else {
            this.msgSendStatus = "Error sending message. Apologies for inconvinience";
        }
    }

    public int fetchUnreadMsgsCount(String username) throws IOException {
        int unreadCount = 0;
        MessagesDAO messagesDB = new MessagesDAO();
        unreadCount = messagesDB.fetchUnreadMsgsCountFromDB(username);
        return unreadCount;
    }

    public void fetchInboxItems() throws ParseException, IOException {
        String username = loginController.getLoginBean().getUserName();
        MessagesDAO messagesDB = new MessagesDAO();
        this.userInbox = messagesDB.fetchInoxItemsFromDB(username);
    }
}

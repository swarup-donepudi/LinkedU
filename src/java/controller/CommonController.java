/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CommonDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author skdonep
 */
@ManagedBean
@SessionScoped
public class CommonController {
    
    @ManagedProperty(value = "#{loginController}")
    private LoginController loginController;

    @ManagedProperty(value = "#{recruiterController}")
    private RecruiterController recruiterController;
    
    @ManagedProperty(value = "#{studentController}")
    private StudentController studentController;
    
    private ArrayList<String> watchList;    
    private String watchListUpdateMsg;
    private boolean selectedUserNotInWatchList;   
    private String watchListViewProfile;
    
    /**
     * Creates a new instance of CommonController
     */
    public CommonController() {
        this.watchList=new ArrayList<>();
    }

    public String getWatchListViewProfile() {
        if(this.loginController.getLoginBean().getAccountType()=='S'){
            watchListViewProfile = "RecruiterProfileForStudent";
        }
        else{
            watchListViewProfile = "StudentProfileForRecruiter";
        }
        return watchListViewProfile;
    }

    public void setWatchListViewProfile(String watchListViewProfile) {
        this.watchListViewProfile = watchListViewProfile;
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

    public String getWatchListUpdateMsg() {
        return watchListUpdateMsg;
    }

    public void setWatchListUpdateMsg(String watchListUpdateMsg) {
        this.watchListUpdateMsg = watchListUpdateMsg;
    }

    public void setStudentController(StudentController studentController) {
        this.studentController = studentController;
    }

    public boolean isSelectedUserNotInWatchList() {
        String wlOwner = this.loginController.getLoginBean().getUserName();
        String wlEntry; 
        if(this.loginController.getLoginBean().getAccountType()=='S'){
            wlEntry = this.studentController.getSelectedRecruiter().getUsername();
        }
        else{
            wlEntry = this.recruiterController.getSelectedStudent().getUsername(); 
        }
        this.checkUserAlreadyInWatchList(wlOwner,wlEntry);
        this.watchListUpdateMsg="";
        return selectedUserNotInWatchList;
    }

    public void setSelectedUserNotInWatchList(boolean selectedUserNotInWatchList) {
        this.selectedUserNotInWatchList = selectedUserNotInWatchList;
    }

    public ArrayList<String> getWatchList() {
        return watchList;
    }

    public void setWatchList(ArrayList<String> watchList) {
        this.watchList = watchList;
    }

    public void addToWatchList() throws SQLException {
        CommonDAO commonDB = new CommonDAO();
        String wlOwner;
        String wlEntry;
        String wlEntryType;
        if(this.loginController.getLoginBean().getAccountType()=='S'){
            wlOwner = this.loginController.getLoginBean().getUserName();
            wlEntry = this.studentController.getSelectedRecruiter().getUsername();
            wlEntryType="recruiter";
        }
        else{
            wlOwner = this.loginController.getLoginBean().getUserName();
            wlEntry = this.recruiterController.getSelectedStudent().getUsername(); 
            wlEntryType="student";
        }
        int insertCount = commonDB.addToWatchListInDB(wlOwner, wlEntry);
        
        if(insertCount == 1){           
            this.watchListUpdateMsg="This "+wlEntryType+" has been added to your Watch List";
        }
        else{
            this.watchListUpdateMsg="Error adding this "+wlEntryType+" to your Watch List. Apologies for inconvinience";
        }
    }
    
    public void checkUserAlreadyInWatchList(String wlOwner,String wlEntry){  
        CommonDAO commonDB = new CommonDAO();
        this.selectedUserNotInWatchList = commonDB.userNotInWatchListInDB(wlOwner,wlEntry);
    }    

    public String showUserWatchList() throws SQLException, IOException {
        String wlOwner = this.loginController.getLoginBean().getUserName();
        CommonDAO commonDAO = new CommonDAO();
        this.watchList=commonDAO.retrieveUserrWatchListFromDB(wlOwner);
        return ("UserWatchList.xhtml");
    }    
    
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
}

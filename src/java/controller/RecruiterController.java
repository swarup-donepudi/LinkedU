/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.RecruiterDAO;
import dao.SearchDAO;
import dao.StudentDAO;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.RecruiterProfile;
import model.StudentProfile;
import model.StudentSearchCriteria;
import model.WatchListItem;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author skdonep
 */
@ManagedBean(name = "recruiterController")
@ApplicationScoped
public class RecruiterController implements Serializable {

    private StudentSearchCriteria studentSearchCriteria;
    private ArrayList<StudentProfile> studentSearchResults;
    private RecruiterProfile recruiterProfile;
    private StudentProfile selectedStudent;
    private String profileUpdateMessage;
    private boolean selectedStudentNotInWatchList;
    private String watchListUpdateMsg;
    private ArrayList<WatchListItem> recruiterWatchList;
   
    /**
     * Creates a new instance of RecruiterController
     */
    public RecruiterController() {
        this.recruiterProfile = new RecruiterProfile();
        this.selectedStudent = new StudentProfile();
        this.studentSearchCriteria = new StudentSearchCriteria();
        this.studentSearchResults = new ArrayList<>();
        this.recruiterWatchList = new ArrayList<>();
    }

  

    public ArrayList<WatchListItem> getRecruiterWatchList() {
        return recruiterWatchList;
    }

    public void setRecruiterWatchList(ArrayList<WatchListItem> recruiterWatchList) {
        this.recruiterWatchList = recruiterWatchList;
    }

    public boolean isSelectedStudentNotInWatchList() {
        return selectedStudentNotInWatchList;
    }

    public void setSelectedStudentNotInWatchList(boolean selectedStudentNotInWatchList) {
        this.selectedStudentNotInWatchList = selectedStudentNotInWatchList;
    }

    public String getWatchListUpdateMsg() {
        return watchListUpdateMsg;
    }

    public void setWatchListUpdateMsg(String watchListUpdateMsg) {
        this.watchListUpdateMsg = watchListUpdateMsg;
    }

    public StudentSearchCriteria getStudentSearchCriteria() {
        return studentSearchCriteria;
    }

    public void setStudentSearchCriteria(StudentSearchCriteria studentSearchCriteria) {
        this.studentSearchCriteria = studentSearchCriteria;
    }

    public ArrayList<StudentProfile> getStudentSearchResults() {
        return studentSearchResults;
    }

    public void setStudentSearchResults(ArrayList<StudentProfile> studentSearchResults) {
        this.studentSearchResults = studentSearchResults;
    }

    public RecruiterProfile getRecruiterProfile() {
        return recruiterProfile;
    }

    public void setRecruiterProfile(RecruiterProfile recruiterProfile) {
        this.recruiterProfile = recruiterProfile;
    }

    public StudentProfile getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(StudentProfile selectedStudent) {
        this.selectedStudent = selectedStudent;
    }

    public String getProfileUpdateMessage() {
        return profileUpdateMessage;
    }

    public void setProfileUpdateMessage(String profileUpdateMessage) {
        this.profileUpdateMessage = profileUpdateMessage;
    }

    public void loadRecruiterProfile() throws IOException, SQLException {
        FacesContext externalContext = FacesContext.getCurrentInstance();
        //if (externalContext.isPostback()) {
            RecruiterDAO profileDao = new RecruiterDAO();
            if (profileDao.recruiterHasProfile(this.recruiterProfile.username)) {
                this.recruiterProfile = profileDao.fetchRecruiterProfile(this.recruiterProfile.username);
            }
       // }
    }

    public void showStudentProfileToRecruiter() throws SQLException, ParseException {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        String selectedStudentUsername = params.get("selectedUsername");
        StudentDAO studentDB = new StudentDAO();
        this.selectedStudent = studentDB.fetchStudentProfile(selectedStudentUsername);
    }

    public String updateRecruiterProfile() throws SQLException, IOException {
        RecruiterDAO recruiterDao = new RecruiterDAO();
        if (recruiterDao.recruiterHasProfile(this.recruiterProfile.getUsername())) {
            recruiterDao.updateRecruiterProfile(this.recruiterProfile, this.recruiterProfile.getUsername());
        } else {
            recruiterDao.createRecruiterProfile(this.recruiterProfile, this.recruiterProfile.getUsername());
        }
        this.recruiterProfile.setUsername(this.recruiterProfile.getUsername());
        this.profileUpdateMessage = "Profile updated successfully.";
        return ("RecruiterProfile.xhtml");
    }

    public String searchStudents() throws SQLException, IOException, ParseException {
        this.studentSearchResults.clear();
        if (this.studentSearchCriteria.getPreferredInst() != null) {
            SearchDAO db = new SearchDAO();
            this.studentSearchResults = db.retrieveStudentSearchResults(studentSearchCriteria, studentSearchResults);
        }
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        //externalContext.redirect("SearchStudents.xhtml");
        return null;
    }

    public void compareStudents() throws SQLException, IOException, ParseException {
        this.studentSearchResults.clear();
        SearchDAO db = new SearchDAO();
        this.studentSearchResults = db.retrieveStudentResultsForComparison(studentSearchCriteria, studentSearchResults);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("CompareStudents.xhtml");        
    }

    public boolean isSelectedUniversityNotInWatchList() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        String wlOwner = session.getAttribute("username").toString();
        String wlItem = this.selectedStudent.getUsername();
        RecruiterDAO recruiterDB = new RecruiterDAO();
        this.selectedStudentNotInWatchList = recruiterDB.studentNotInWatchListInDB(wlOwner, wlItem);
        return this.selectedStudentNotInWatchList;
    }

    public void addStudentToWatchList() throws SQLException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        String wlOwner = session.getAttribute("username").toString();        
        RecruiterDAO recruiterDB = new RecruiterDAO();
        int insertCount = recruiterDB.addStudentToWatchListInDB(wlOwner, this.selectedStudent);

        if (insertCount == 1) {
            this.watchListUpdateMsg = "This Student has been added to your Watch List";
        } else {
            this.watchListUpdateMsg = "Error adding this student to your Watch List. Apologies for inconvinience";
        }
    }

    public void loadRecruiterWatchList() throws SQLException, IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        String wlOwner = session.getAttribute("username").toString();        
        RecruiterDAO recruiterDB = new RecruiterDAO();
        recruiterDB.retrieveRecruiterWatchListFromDB(wlOwner, this.recruiterWatchList);
    }
    
    public void uploadImage() throws SQLException, IOException{
        RecruiterDAO upload = new RecruiterDAO();
        int uploadStatus = upload.uploadImageToDB(recruiterProfile);
        if(uploadStatus==0){
            recruiterProfile.setUploadStatus("Upload not successfull. Please retry");
        }
        else{
            recruiterProfile.setUploadStatus("Upload successfull. Congratulations");
        }
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("RecruiterProfile.xhtml");
    }

    
}

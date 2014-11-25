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
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import model.RecruiterProfile;
import model.StudentProfile;
import model.StudentSearchCriteria;

/**
 *
 * @author skdonep
 */
@ManagedBean(name = "recruiterController")
@ApplicationScoped
public class RecruiterController implements Serializable {
    
    private StudentSearchCriteria studentSearchCriteria;
    private ArrayList<StudentProfile> studentSearchResults;
    private String watchListUpdateMsg;
    private RecruiterProfile recruiterProfile;
    private StudentProfile selectedStudent;
    private String profileUpdateMessage;
    private boolean selectedStudentNotInWatchList;

    /**
     * Creates a new instance of RecruiterController
     */
    public RecruiterController() {        
        this.selectedStudent = new StudentProfile();
        this.studentSearchCriteria = new StudentSearchCriteria();
        this.studentSearchResults = new ArrayList<>();
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

    public String getWatchListUpdateMsg() {
        return watchListUpdateMsg;
    }

    public void setWatchListUpdateMsg(String watchListUpdateMsg) {
        this.watchListUpdateMsg = watchListUpdateMsg;
    }

    public boolean isSelectedStudentNotInWatchList() {
        return selectedStudentNotInWatchList;
    }

    public void setSelectedStudentNotInWatchList(boolean selectedStudentNotInWatchList) {
        this.selectedStudentNotInWatchList = selectedStudentNotInWatchList;
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
    
    public String showRecruiterHisProfile() throws IOException, SQLException {
        RecruiterDAO profileDao = new RecruiterDAO();
        if (profileDao.recruiterHasProfile(this.recruiterProfile.username)) {
            this.recruiterProfile = profileDao.fetchRecruiterProfile(this.recruiterProfile.username);
        }
        return ("RecruiterProfile.xhtml");
    }    

    public String showRecruiterWatchList() throws SQLException, IOException {
        RecruiterDAO profileDao = new RecruiterDAO();
        this.recruiterProfile.setWatchList(profileDao.retrieveRecruiterWatchList(this.recruiterProfile.username));
        return ("RecruiterWatchList.xhtml");
    }

    public void shoWStudentProfileToRecruiter() throws SQLException {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        String selectedStudentUsername = params.get("selectedUsername");
        RecruiterDAO db = new RecruiterDAO();
        String recruiterUsername = this.recruiterProfile.username;
        this.selectedStudentNotInWatchList = db.studentNotInWatchList(recruiterUsername, selectedStudentUsername);
        StudentDAO profileDB = new StudentDAO();
        this.selectedStudent = profileDB.fetchStudentProfile(selectedStudentUsername);
    }

    public String updateRecruiterProfile() throws SQLException {
        RecruiterDAO recruiterDao = new RecruiterDAO();
        if (recruiterDao.recruiterHasProfile(this.recruiterProfile.username)) {
            recruiterDao.updateRecruiterProfile(this.recruiterProfile, this.recruiterProfile.username);
        } else {
            recruiterDao.createRecruiterProfile(this.recruiterProfile, this.recruiterProfile.username);
        }
        this.recruiterProfile.setUsername(this.recruiterProfile.username);
        this.profileUpdateMessage = "Profile updated successfully.";
        return ("RecruiterProfile.xhtml");
    }

    public void searchStudents() throws SQLException, IOException {
        this.studentSearchResults.clear();
        SearchDAO db = new SearchDAO();
        db.retrieveSearchResults(studentSearchCriteria, studentSearchResults);
        //ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        //return ("StudentsSearchResults.xhtml");
    }

    public void fetchSelectedStudentProfile() throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        String selectedStudentUsername = params.get("selectedUsername");

        for (StudentProfile studentProfile : studentSearchResults) {
            if ((studentProfile.getUsername().equals(selectedStudentUsername))) {
                this.setSelectedStudent(studentProfile);
            }
        }
    }

    public void addStudentToWatchList(String studentUsername) throws SQLException {
        SearchDAO db = new SearchDAO();
        String recruiterUsername = this.recruiterProfile.username;
        int rowCount = db.addStudentToRecruiterWatchList(recruiterUsername, studentUsername);
        if (rowCount == 1) {
            this.setWatchListUpdateMsg("Student added to your Watch List");
        } else {
            this.setWatchListUpdateMsg("Error occured while adding student to your Watch List");
        }
    }
}

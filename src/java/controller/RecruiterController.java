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
    private RecruiterProfile recruiterProfile;
    private StudentProfile selectedStudent;
    private String profileUpdateMessage;

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

    public void showStudentProfileToRecruiter() throws SQLException, ParseException {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        String selectedStudentUsername = params.get("selectedUsername");
        StudentDAO studentDB = new StudentDAO();
        this.selectedStudent = studentDB.fetchStudentProfile(selectedStudentUsername);
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

    public void searchStudents() throws SQLException, IOException, ParseException {
        this.studentSearchResults.clear();
        SearchDAO db = new SearchDAO();
        db.retrieveStudentSearchResults(studentSearchCriteria, studentSearchResults);
    }
}

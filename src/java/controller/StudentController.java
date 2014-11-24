/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProfileDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.RecruiterProfile;
import model.StudentProfile;
import model.UniversitySearchCriteria;

/**
 *
 * @author skdonep
 */
@ManagedBean(name = "studentController")
@ApplicationScoped
public class StudentController {
    private StudentProfile studentProfile;
    private RecruiterProfile selectedRecruiter;
    private String profileUpdateMessage;    
    private UniversitySearchCriteria universitySearchCriteria;
    /**
     * Creates a new instance of StudentController
     */
    public StudentController() {
        this.selectedRecruiter = new RecruiterProfile();
        this.universitySearchCriteria = new UniversitySearchCriteria();
    }

    public StudentProfile getStudentProfile() {
        return studentProfile;
    }

    public void setStudentProfile(StudentProfile studentProfile) {
        this.studentProfile = studentProfile;
    }

    public RecruiterProfile getSelectedRecruiter() {
        return selectedRecruiter;
    }

    public void setSelectedRecruiter(RecruiterProfile selectedRecruiter) {
        this.selectedRecruiter = selectedRecruiter;
    }

    public String getProfileUpdateMessage() {
        return profileUpdateMessage;
    }

    public void setProfileUpdateMessage(String profileUpdateMessage) {
        this.profileUpdateMessage = profileUpdateMessage;
    }

    public UniversitySearchCriteria getUniversitySearchCriteria() {
        return universitySearchCriteria;
    }

    public void setUniversitySearchCriteria(UniversitySearchCriteria universitySearchCriteria) {
        this.universitySearchCriteria = universitySearchCriteria;
    }

    public void updateStudentProfile() throws SQLException {
        ProfileDAO profileDao = new ProfileDAO();
        if (profileDao.studentHasProfile(this.studentProfile.username)) {
            profileDao.updateStudentProfile(this.studentProfile, this.studentProfile.username);
        } else {
            profileDao.createStudentProfile(this.studentProfile, this.studentProfile.username);
        }
        this.profileUpdateMessage="Profile updated successfully.";
    }   
    public void showUniversitiesSearchForm() throws IOException{
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();       
        externalContext.redirect("SearchUniversities.xhtml");  
    }     
}

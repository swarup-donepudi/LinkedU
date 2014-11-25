/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.StudentDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.RecruiterProfile;
import model.StudentProfile;
import model.UniversitySearchCriteria;
import org.primefaces.event.SelectEvent;

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
        StudentDAO profileDao = new StudentDAO();
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
    
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.RecruiterDAO;
import dao.SearchDAO;
import dao.StudentDAO;
import dao.InstitutionDAO;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.RecruiterProfile;
import model.StudentProfile;
import model.InstitutionProfile;
import model.InstitutionSearchCriteria;

/**
 *
 * @author skdonep
 */
@ManagedBean(name = "studentController")
@ApplicationScoped
public class StudentController {

    private StudentProfile studentProfile;
    private RecruiterProfile selectedRecruiter;
    private InstitutionProfile selectedInstitution;
    private String profileUpdateMessage;
    private InstitutionSearchCriteria institutionSearchCriteria;
    private ArrayList<InstitutionProfile> institutionSearchResults;

    /**
     * Creates a new instance of StudentController
     */
    public StudentController() {
        this.selectedRecruiter = new RecruiterProfile();
        this.institutionSearchCriteria = new InstitutionSearchCriteria();
        this.institutionSearchResults = new ArrayList<>();
    }

    public ArrayList<InstitutionProfile> getInstitutionSearchResults() {
        return institutionSearchResults;
    }

    public void setInstitutionSearchResults(ArrayList<InstitutionProfile> institutionSearchResults) {
        this.institutionSearchResults = institutionSearchResults;
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

    public InstitutionProfile getSelectedInstitution() {
        return selectedInstitution;
    }

    public void setSelectedInstitution(InstitutionProfile selectedInstitution) {
        this.selectedInstitution = selectedInstitution;
    }

    public String getProfileUpdateMessage() {
        return profileUpdateMessage;
    }

    public void setProfileUpdateMessage(String profileUpdateMessage) {
        this.profileUpdateMessage = profileUpdateMessage;
    }

    public InstitutionSearchCriteria getInstitutionSearchCriteria() {
        return institutionSearchCriteria;
    }

    public void setInstitutionSearchCriteria(InstitutionSearchCriteria institutionSearchCriteria) {
        this.institutionSearchCriteria = institutionSearchCriteria;
    }

    public void updateStudentProfile() throws SQLException {
        StudentDAO profileDao = new StudentDAO();
        if (profileDao.studentHasProfile(this.studentProfile.username)) {
            profileDao.updateStudentProfile(this.studentProfile, this.studentProfile.username);
        } else {
            profileDao.createStudentProfile(this.studentProfile, this.studentProfile.username);
        }
        this.profileUpdateMessage = "Profile updated successfully.";
    }

    public void showInstitutionsSearchForm() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("SearchInstitutions.xhtml");
    }

    public void searchInstitutions() throws SQLException, IOException {
        this.institutionSearchResults = new ArrayList<>();
        if ((this.institutionSearchCriteria.getInstName() != null)) {
            if (!this.institutionSearchCriteria.getInstName().equals("")) {
                SearchDAO db = new SearchDAO();
                db.retrieveInstitutionSearchResults(institutionSearchCriteria, institutionSearchResults);
                this.institutionSearchCriteria = new InstitutionSearchCriteria();
            }
        }
    }

    public void showInstitutionProfileToStudent() throws UnsupportedEncodingException, IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        int selectedInstID = Integer.parseInt(params.get("selectedInstitution"));
        InstitutionDAO institutionDB = new InstitutionDAO();
        this.selectedInstitution = institutionDB.fetchInstitutionProfileFromDB(selectedInstID);
    }

    public void showRecruiterProfileToStudent() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        String selectedRecruiterUsername = params.get("selectedUsername");
        RecruiterDAO recruiterDB = new RecruiterDAO();
        this.selectedRecruiter = recruiterDB.fetchRecruiterProfile(selectedRecruiterUsername);
    }
}

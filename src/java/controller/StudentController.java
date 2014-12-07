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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import model.RecruiterProfile;
import model.StudentProfile;
import model.InstitutionProfile;
import model.InstitutionSearchCriteria;
import model.WatchListItem;
import org.primefaces.model.DefaultStreamedContent;

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
    private boolean selectedInstitutionNotInWatchList;
    private boolean selectedRecruiterNotInWatchList;
    private String watchListUpdateMsg;
    private ArrayList<WatchListItem> studentWatchListInstitutions;
    private ArrayList<WatchListItem> studentWatchListRecruiters;

    /**
     * Creates a new instance of StudentController
     */
    public StudentController() {
        this.studentProfile = new StudentProfile();
        this.selectedRecruiter = new RecruiterProfile();
        this.institutionSearchCriteria = new InstitutionSearchCriteria();
        this.institutionSearchResults = new ArrayList<>();
        this.studentWatchListInstitutions = new ArrayList<>();
        this.studentWatchListRecruiters = new ArrayList<>();
    }

    public ArrayList<WatchListItem> getStudentWatchListInstitutions() {
        return studentWatchListInstitutions;
    }

    public void setStudentWatchListInstitutions(ArrayList<WatchListItem> studentWatchListInstitutions) {
        this.studentWatchListInstitutions = studentWatchListInstitutions;
    }

    public ArrayList<WatchListItem> getStudentWatchListRecruiters() {
        return studentWatchListRecruiters;
    }

    public void setStudentWatchListRecruiters(ArrayList<WatchListItem> studentWatchListRecruiters) {
        this.studentWatchListRecruiters = studentWatchListRecruiters;
    }

    public String getWatchListUpdateMsg() {
        return watchListUpdateMsg;
    }

    public void setWatchListUpdateMsg(String watchListUpdateMsg) {
        this.watchListUpdateMsg = watchListUpdateMsg;
    }

    public void setSelectedInstitutionNotInWatchList(boolean selectedInstitutionNotInWatchList) {
        this.selectedInstitutionNotInWatchList = selectedInstitutionNotInWatchList;
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

    public void loadStudentProfile() throws IOException, SQLException, ParseException {
        FacesContext externalContext = FacesContext.getCurrentInstance();
      //  if (externalContext.isPostback()) {
            StudentDAO studentDao = new StudentDAO();
            if (studentDao.studentHasProfile(this.studentProfile.username)) {
                this.studentProfile = studentDao.fetchStudentProfile(this.studentProfile.username);
            }
      //  }
    }

    public void updateStudentProfile() throws SQLException, IOException {
        StudentDAO profileDao = new StudentDAO();
        if (profileDao.studentHasProfile(this.studentProfile.getUsername())) {
            profileDao.updateStudentProfile(this.studentProfile, this.studentProfile.getUsername());
        } else {
            profileDao.createStudentProfile(this.studentProfile, this.studentProfile.getUsername());
        }
        this.profileUpdateMessage = "Profile updated successfully.";
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("SearchInstitutions.xhtml");
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

    public void showRecruiterProfileToStudent() throws SQLException {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (!fc.isPostback()) {
            Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
            String selectedRecruiterUsername = params.get("selectedUsername");
            RecruiterDAO recruiterDB = new RecruiterDAO();
            this.selectedRecruiter = recruiterDB.fetchRecruiterProfile(selectedRecruiterUsername);
        }
    }

    public boolean isSelectedInstitutionNotInWatchList() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        String wlOwner = session.getAttribute("username").toString();
        String wlItem = this.selectedInstitution.getInstName();
        StudentDAO studentDB = new StudentDAO();
        this.selectedInstitutionNotInWatchList = studentDB.institutionNotInWatchListInDB(wlOwner, wlItem);
        return this.selectedInstitutionNotInWatchList;
    }

    public boolean isSelectedRecruiterNotInWatchList() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        String wlOwner = session.getAttribute("username").toString();
        String wlItem = this.selectedRecruiter.getUsername();
        StudentDAO studentDB = new StudentDAO();
        this.setSelectedRecruiterNotInWatchList(studentDB.recruiterNotInWatchListInDB(wlOwner, wlItem));
        return this.selectedRecruiterNotInWatchList;
    }

    public void addInstitutionToWatchList() throws SQLException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        String wlOwner = session.getAttribute("username").toString();
        String wlItem = this.selectedInstitution.getDoeUID();
        String wlLname = this.selectedInstitution.getInstName();
        StudentDAO studentDB = new StudentDAO();
        int insertCount = studentDB.addUniversityToWatchListInDB(wlOwner, wlItem, wlLname);
        if (insertCount == 1) {
            this.watchListUpdateMsg = "This institution has been added to your Watch List";
        } else {
            this.watchListUpdateMsg = "Error adding this institution to your Watch List. Apologies for inconvinience";
        }
    }

    public void addRecruiterToWatchList() throws SQLException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        String wlOwner = session.getAttribute("username").toString();
        String wlItem = this.selectedRecruiter.getUsername();
        String itemFName = this.selectedRecruiter.getFname();
        String itemLname = this.selectedRecruiter.getLname();

        StudentDAO studentDB = new StudentDAO();
        int insertCount = studentDB.addRecruiterToWatchListInDB(wlOwner, wlItem, itemFName, itemLname);

        if (insertCount == 1) {
            this.watchListUpdateMsg = "This recruiter has been added to your Watch List";
        } else {
            this.watchListUpdateMsg = "Error adding this recruiter to your Watch List. Apologies for inconvinience";
        }
    }

    public void loadStudentWatchList() throws SQLException, IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        String wlOwner = session.getAttribute("username").toString();
        StudentDAO studentDB = new StudentDAO();
        studentDB.retrieveStudenteWatchListFromDB(wlOwner, studentWatchListInstitutions, studentWatchListRecruiters);
    }

    public void uploadResumeImg() throws IOException, SQLException, MessagingException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        StudentDAO upload = new StudentDAO();
        int uploadCheck = 0;
        if (studentProfile.getResume() != null) {
            uploadCheck = upload.uploadResume(studentProfile.username, studentProfile);
        }
        if (studentProfile.getImageUpload() != null) {
            uploadCheck += upload.uploadImg(studentProfile.username, studentProfile);
        }
        if (uploadCheck == 0) {

            externalContext.redirect("StudentHome.xhtml");
        } else {
            externalContext.redirect("StudentProfile.xhtml");
        }

    }

    public DefaultStreamedContent downloadResume() {
        StudentDAO download = new StudentDAO();
        return download.downloadResume(studentProfile.username);
    }

    /**
     * @param selectedRecruiterNotInWatchList the
     * selectedRecruiterNotInWatchList to set
     */
    public void setSelectedRecruiterNotInWatchList(boolean selectedRecruiterNotInWatchList) {
        this.selectedRecruiterNotInWatchList = selectedRecruiterNotInWatchList;
    }
}

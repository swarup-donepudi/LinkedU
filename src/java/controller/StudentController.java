/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.SearchDAO;
import dao.StudentDAO;
import dao.UniversityDAO;

import java.io.File;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Map;

import java.util.List;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import model.RecruiterProfile;
import model.StudentProfile;
import model.UniversityProfile;
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

    private UniversityProfile selectedUniversity;
    private String profileUpdateMessage;    
    private UniversitySearchCriteria universitySearchCriteria;
    private ArrayList<UniversityProfile> universitySearchResults;
    private LoginController loginController;
    private Part document;
    private List<UniversitySearchCriteria> search;

    /**
     * Creates a new instance of StudentController
     */
    public StudentController() {
        this.selectedRecruiter = new RecruiterProfile();
        this.universitySearchCriteria = new UniversitySearchCriteria();
        this.universitySearchResults = new ArrayList<>();
        this.studentProfile = new StudentProfile();
    }

    public ArrayList<UniversityProfile> getUniversitySearchResults() {
        return universitySearchResults;
    }

    public void setUniversitySearchResults(ArrayList<UniversityProfile> universitySearchResults) {
        this.universitySearchResults = universitySearchResults;
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

    public UniversityProfile getSelectedUniversity() {
        return selectedUniversity;
    }

    public void setSelectedUniversity(UniversityProfile selectedUniversity) {
        this.selectedUniversity = selectedUniversity;
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
        if (profileDao.studentHasProfile(this.studentProfile.getUsername())) {
            profileDao.updateStudentProfile(this.studentProfile, this.studentProfile.getUsername());
        } else {
            profileDao.createStudentProfile(this.studentProfile, this.studentProfile.getUsername());
        }
        this.profileUpdateMessage = "Profile updated successfully.";
    }

    public void showUniversitiesSearchForm() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("SearchUniversities.xhtml");
    }

    public void searchUniversities() throws SQLException, IOException {
        this.getUniversitySearchResults().clear();
        SearchDAO find = new SearchDAO();
        this.setSearch(find.UniversitySearchResults(query()));
    }

    public String moreInfo(String uniname) throws SQLException {
        SearchDAO more = new SearchDAO();
        universitySearchCriteria.setMoreInfo(more.requestMoreInfoByStudent(uniname));
        return "RequestedMoreInfoByStudents.xhtml";
    }

    public void setSearch(List<UniversitySearchCriteria> search) {
        this.search = search;
    }

    
    public void showUniversityProfileToStudent(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        String selectedUniversityUsername = params.get("selectedUniversity");
        UniversityDAO universityDB = new UniversityDAO();
        this.selectedUniversity = universityDB.fetchUniversityProfile(selectedUniversityUsername);
    }


    public List<UniversitySearchCriteria> getSearch() {
        return search;
    }

    public String query() {
        String query = "select * from linkedu.university_search_criteria";
        if (universitySearchCriteria.getGpaCriteria().equals("A")) {
            query += " where gpa_req >= 3.2 and gpa_req <= 4.0";
        }
        if (universitySearchCriteria.getGpaCriteria().equals("B")) {
            query += " where gpa_req >= 2.5 and gpa_req < 3.2";
        }
        if (universitySearchCriteria.getGpaCriteria().equals("C")) {
            query += " where gpa_req < 2.5";
        }

        if (universitySearchCriteria.getSearchCriteria().equals("uniname")) {
            query += " where university_name like '%" + universitySearchCriteria.getSearchKeyword() + "%'";
        }
        if (universitySearchCriteria.getSearchCriteria().equals("location")) {
            query += " where location like '%" + universitySearchCriteria.getSearchKeyword() + "%'";
        }
        if (universitySearchCriteria.getSearchCriteria().equals("program")) {
            query += " where program like '%" + universitySearchCriteria.getSearchKeyword() + "%'";
        }
        return query;
    }

    public Part getDocument() {
        return document;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    /**
     * @param loginController the loginController to set
     */
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public String uploadFile() throws IOException, SQLException {
        studentProfile.setUploadStatusMsg("");
        String fileName = getLoginController().getLoginBean().getUserName() + "-" + getFileName(getDocument());
        String basePath = "H:" + File.separator + "temp" + File.separator + "documents" + File.separator;
        File outputFilePath = new File(basePath + fileName);

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = getDocument().getInputStream();
            outputStream = new FileOutputStream(outputFilePath);
            int read = 0;
            final byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            studentProfile.setUploadStatusMsg("File upload successfull");

        } catch (IOException e) {
            e.printStackTrace();
            studentProfile.setUploadStatusMsg("File upload failed. Retry");
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        StudentDAO updatePath = new StudentDAO();
        updatePath.updatePath(getLoginController().getLoginBean().getUserName(), basePath + fileName);

        return null;    // return to same page
    }

    private String getFileName(Part doc) {
        final String partHeader = doc.getHeader("content-disposition");
        System.out.println("***** partHeader: " + partHeader);
        for (String content : doc.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim()
                        .replace("\"", "");
            }
        }
        return null;
    }

    public void deleteFile() {

        try {

            StudentDAO path = new StudentDAO();
            File file = new File(path.getFileLocation(getLoginController().getLoginBean().getUserName()));

            if (file.delete()) {
                System.out.println(file.getName() + " is deleted!");
                studentProfile.setUploadStatusMsg("File Deleted");

            } else {
                System.out.println("Delete operation is failed.");
            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    /**
     * @param document the document to set
     */
    public void setDocument(Part document) {
        this.document = document;
    }


}

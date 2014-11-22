/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import dao.SearchDAO;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.StudentProfile;
import model.StudentSearchCriteria;
import model.UniversitySearchCriteria;

/**
 *
 * @author skdonep
 */
@ManagedBean(name = "searchController")
@SessionScoped
public class SearchController implements Serializable {
    
    
    private StudentSearchCriteria studentSearchCriteria;
    private UniversitySearchCriteria universitySearchCriteria;
    private ArrayList<StudentProfile>  studentSearchResults;
    private StudentProfile selectedProfile;
    private String watchListUpdateMsg;
    
    /**
     * Creates a new instance of SearchController
     */
    public SearchController() {
        studentSearchCriteria = new StudentSearchCriteria();
        universitySearchCriteria = new UniversitySearchCriteria();
        studentSearchResults = new ArrayList<StudentProfile>();
    }



    public String getWatchListUpdateMsg() {
        return watchListUpdateMsg;
    }

    public void setWatchListUpdateMsg(String watchListUpdateMsg) {
        this.watchListUpdateMsg = watchListUpdateMsg;
    }


    public StudentProfile getSelectedProfile() {
        return selectedProfile;
    }

    public void setSelectedProfile(StudentProfile selectedProfile) {
        this.selectedProfile = selectedProfile;
    }

    public StudentSearchCriteria getStudentSearchCriteria() {
        return studentSearchCriteria;
    }

    public void setStudentSearchCriteria(StudentSearchCriteria studentSearchCriteria) {
        this.studentSearchCriteria = studentSearchCriteria;
    }

    public UniversitySearchCriteria getUniversitySearchCriteria() {
        return universitySearchCriteria;
    }

    public void setUniversitySearchCriteria(UniversitySearchCriteria universitySearchCriteria) {
        this.universitySearchCriteria = universitySearchCriteria;
    }

    public ArrayList<StudentProfile> getStudentSearchResults() {
        return studentSearchResults;
    }

    public void setStudentSearchResults(ArrayList<StudentProfile> studentSearchResults) {
        this.studentSearchResults = studentSearchResults;
    }

    public void showStudentSearchFormToRecruiter() throws IOException{
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();       
        externalContext.redirect("SearchStudents.xhtml");  
    }
    
    public void showUniversitiesSearchFormToStudent() throws IOException{
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();       
        externalContext.redirect("SearchUniversities.xhtml");  
    }    
    
    public String searchStudents() throws SQLException, IOException {
        studentSearchResults.clear();
        SearchDAO db = new SearchDAO();
        db.retrieveSearchResults(studentSearchCriteria, studentSearchResults);  
        //ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        return("StudentsSearchResults.xhtml");        
    }

    public void fetchStudentProfile(String studentUsername) throws IOException {
        for (StudentProfile studentProfile : studentSearchResults){
            if ((studentProfile.getUsername().equals(studentUsername))){
                this.setSelectedProfile(studentProfile);
            }
        }
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("StudentProfileForRecruiter.xhtml");
    }    
    
    public void addStudentToWatchList(String studentUsername) throws SQLException{
        SearchDAO db = new SearchDAO();
        
        int rowCount = db.addStudentToRecruiterWatchList(studentUsername,studentUsername);
        if(rowCount==1)
            this.setWatchListUpdateMsg("Student added to your Watch List");
        else
            this.setWatchListUpdateMsg("Error occured while adding student to your Watch List");
    }

    public void searchUniversities(){
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.StudentSearchCriteria;
import model.StudentSearchResult;
import model.UniversitySearchCriteria;
import model.UniversitySearchResult;

/**
 *
 * @author skdonep
 */
@Named(value = "searchController")
@SessionScoped
public class SearchController implements Serializable {
    private StudentSearchCriteria studentSearchCriteria;
    private UniversitySearchCriteria universitySearchCriteria;
    private StudentSearchResult[]  studentSearchResults;
    private UniversitySearchResult[]  universitySearchResults;
    
    /**
     * Creates a new instance of SearchController
     */
    public SearchController() {
        studentSearchCriteria = new StudentSearchCriteria();
        universitySearchCriteria = new UniversitySearchCriteria();
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

    public StudentSearchResult[] getStudentSearchResults() {
        return studentSearchResults;
    }

    public void setStudentSearchResults(StudentSearchResult[] studentSearchResults) {
        this.studentSearchResults = studentSearchResults;
    }

    public UniversitySearchResult[] getUniversitySearchResults() {
        return universitySearchResults;
    }

    public void setUniversitySearchResults(UniversitySearchResult[] universitySearchResults) {
        this.universitySearchResults = universitySearchResults;
    }
    
    public void showStudentSearchFormToRecruiter() throws IOException{
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();       
        externalContext.redirect("SearchStudents.xhtml");  
    }
    
    public void showUniversitiesSearchFormToStudent() throws IOException{
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();       
        externalContext.redirect("SearchUniversities.xhtml");  
    }    
    
    public void searchStudents(){
        
    }

    public void searchUniversities(){
        
    }
}

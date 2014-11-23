/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import dao.ProfileDAO;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;
import javax.faces.context.FacesContext;
import model.StudentProfile;

/**
 *
 * @author skdonep
 */
@Named(value = "recruiterController")
@SessionScoped
public class RecruiterController implements Serializable {
    private StudentProfile studentProfile;

    /**
     * Creates a new instance of RecruiterController
     */
    public RecruiterController() {
        this.studentProfile = new StudentProfile();
    }   

    public StudentProfile getStudentProfile() {
        return studentProfile;
    }

    public void setStudentProfile(StudentProfile studentProfile) {
        this.studentProfile = studentProfile;
    }
    
    public void showRecruiterStudentProfile() throws SQLException{
        FacesContext fc =FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        String selectedStudentUsername  = params.get("selectedUsername");        
        ProfileDAO db = new ProfileDAO();
        this.studentProfile = db.fetchStudentProfile(selectedStudentUsername);
    }

}

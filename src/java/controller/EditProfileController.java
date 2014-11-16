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
import model.RecruiterProfile;
import model.StudentProfile;
import model.UserProfile;

/**
 *
 * @author skdonep
 */
@Named(value = "editProfileController")
@SessionScoped
public class EditProfileController implements Serializable {
    UserProfile profile;
    /**
     * Creates a new instance of EditProfileController
     */
    public EditProfileController() {
    }
    
    public void editProfile(char accountType) throws IOException{
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        if(accountType == 'S'){
            profile = new StudentProfile();
            externalContext.redirect("EditProfileStudent.xhtml");            
        }
        else{
            profile = new RecruiterProfile();
            externalContext.redirect("EditProfileRecruiter.xhtml");
        }
            
        
        
        
    }
    
}

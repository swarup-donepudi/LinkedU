/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
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
    
    public void editProfile(char accountType){
        if(accountType == 'S')
            profile = new StudentProfile();
        else
            profile = new RecruiterProfile();
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

=======
>>>>>>> origin/master
/**
 *
 * @author skdonep
 */
public class StudentProfile extends UserProfile{
    private Date dob;
    private String highestDegree;
    private String GPA;
    private String preferredProgram;
    private String preferredUnivs;
    private String uploadStatusMsg;

    public StudentProfile(){
        this.preferredPrograms = new ArrayList<>();
        this.preferredUnivs = new ArrayList<>();
    }
    
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getHighestDegree() {
        return highestDegree;
    }

    public void setHighestDegree(String highestDegree) {
        this.highestDegree = highestDegree;
    }

    public String getGPA() {
        return GPA;
    }

    public void setGPA(String GPA) {
        this.GPA = GPA;
    }

    public String getPreferredProgram() {
        return preferredProgram;
    }

    public void setPreferredProgram(String preferredProgram) {
        this.preferredProgram = preferredProgram;
    }

    public String getPreferredUnivs() {
        return preferredUnivs;
    }

    public void setPreferredUnivs(String preferredUnivs) {
        this.preferredUnivs = preferredUnivs;
    }

    /**
     * @return the uploadStatusMsg
     */
    public String getUploadStatusMsg() {
        return uploadStatusMsg;
    }

    /**
     * @param uploadStatusMsg the uploadStatusMsg to set
     */
    public void setUploadStatusMsg(String uploadStatusMsg) {
        this.uploadStatusMsg = uploadStatusMsg;
    }
}

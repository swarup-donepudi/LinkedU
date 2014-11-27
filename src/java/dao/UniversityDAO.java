/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import model.UniversityProfile;

/**
 *
 * @author skdonep
 */
public class UniversityDAO extends AppDBInfoDAO{
    private Connection DBConn;
    
    public UniversityDAO(){
        super();
    }
    
    public UniversityProfile fetchUniversityProfile(String univName){
        UniversityProfile univProfile = new UniversityProfile();
        return univProfile;
    }
}

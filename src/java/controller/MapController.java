/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.map.DefaultMapModel;  
import org.primefaces.model.map.LatLng;  
import org.primefaces.model.map.MapModel;  
import org.primefaces.model.map.Marker; 

/**
 *
 * @author skdonep
 */
@ManagedBean
@SessionScoped
public class MapController implements Serializable{
    
    @ManagedProperty(value="#{studentController}")
    private StudentController studentController;
    
    private MapModel instMap;
    private LatLng instCoordinates;
    
    private String instMapCentre;
    /**
     * Creates a new instance of MapController
     */
    public MapController() {
        this.instMap = new DefaultMapModel();     
    }

    public String getInstMapCentre() {
        float latitude = this.studentController.getSelectedInstitution().getLatitude();
        float longitude = this.studentController.getSelectedInstitution().getLongitude();
        this.instMapCentre = Float.toString(latitude)+", "+Float.toString(longitude);
        return instMapCentre;
    }

    public void setInstMapCentre(String instMapCentre) {
        this.instMapCentre = instMapCentre;
    }

    public MapModel getInstMap() {
        String instName = this.studentController.getSelectedInstitution().getInstName();
        String instState = this.studentController.getSelectedInstitution().getStateCode();
        String markerLabel = instName+", "+instState;
        
        this.instMap.addOverlay(new Marker(this.instCoordinates, markerLabel));
        
        return this.instMap;
        }

    public void setInstMap(MapModel instMap) {
        this.instMap = instMap;
    }

    public StudentController getStudentController() {
        return studentController;
    }

    public void setStudentController(StudentController studentController) {
        this.studentController = studentController;
    }

    public LatLng getInstCoordinates() {
        float latitude = this.studentController.getSelectedInstitution().getLatitude();
        float longitude = this.studentController.getSelectedInstitution().getLongitude();
        this.instCoordinates = new LatLng(latitude, longitude);        
        return instCoordinates;
    }

    public void setInstCoordinates(LatLng instCoordinates) {
        float latitude = this.studentController.getSelectedInstitution().getLatitude();
        float longitude = this.studentController.getSelectedInstitution().getLongitude();
        this.instCoordinates = new LatLng(latitude, longitude);
    }
    
}

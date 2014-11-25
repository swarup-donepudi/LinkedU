/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author skdonep
 */
@ManagedBean
@SessionScoped
public class SuggestionsController {

    /**
     * Creates a new instance of SuggestionsController
     */
    public SuggestionsController() {
    }
    
    public List<String> suggestCountries(String query) {
            List<String> results = new ArrayList<>();
            for(int i = 0; i < 10; i++) {
                results.add(query);
            }         
            return results;
        }    
    }

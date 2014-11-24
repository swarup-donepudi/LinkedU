/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author skdonep
 */
@ManagedBean
@SessionScoped
public class TemplateController {
    private String pageTemplate;

    /**
     * Creates a new instance of TemplateController
     */
    public TemplateController() {
        this.pageTemplate="templates\\AppThemeRecruiter.xhtml";
    }

    public String getPageTemplate() {
        return pageTemplate;
    }

    public void setPageTemplate(String pageTemplate) {
        this.pageTemplate = pageTemplate;
    }
    
    public void selectPageTemplate(){
        
    }
}

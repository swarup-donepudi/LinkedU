/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

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
        this.pageTemplate = "templates\\AppTheme.xhtml";
    }

    public String getPageTemplate() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        char accType = session.getAttribute("LinkEDU_AccType").toString().charAt(0);
        if (accType == 'S') {
            this.pageTemplate = "templates\\AppThemeStudent.xhtml";
        } else {
            this.pageTemplate = "templates\\AppThemeRecruiter.xhtml";
        }
        return pageTemplate;
    }

    public void setPageTemplate(String pageTemplate) {
        this.pageTemplate = pageTemplate;
    }

    public void selectPageTemplate() {

    }
}

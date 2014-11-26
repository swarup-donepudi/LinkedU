/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author skdonep
 */
@ManagedBean
@SessionScoped
public class TemplateController {
    private String pageTemplate;
    
@ManagedProperty(value = "#{loginController}")
    private LoginController loginController;    

    /**
     * Creates a new instance of TemplateController
     */
    public TemplateController() {
        this.pageTemplate="templates\\AppTheme.xhtml";
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public String getPageTemplate() {
        char accType=this.loginController.getLoginBean().getAccountType();
        if(accType=='S')
            this.pageTemplate="templates\\AppThemeStudent.xhtml";
        else
            this.pageTemplate="templates\\AppThemeRecruiter.xhtml";
        return pageTemplate;
    }

    public void setPageTemplate(String pageTemplate) {
        this.pageTemplate = pageTemplate;
    }
    
    public void selectPageTemplate(){
        
    }
}

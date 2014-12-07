/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author skdonep
 */
public class RecruiterProfile extends UserProfile {

    private String instName;
    private String instURL;
    private String instFBPage;
    private String twitterHandle;
    private String reasonForLinkEDU;
    
    
    private String uploadStatus;

    
    public RecruiterProfile(){
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getInstURL() {
        return instURL;
    }

    public void setInstURL(String instURL) {
        this.instURL = instURL;
    }

    public String getInstFBPage() {
        return instFBPage;
    }

    public void setInstFBPage(String instFBPage) {
        this.instFBPage = instFBPage;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public String getReasonForLinkEDU() {
        return reasonForLinkEDU;
    }

    public void setReasonForLinkEDU(String reasonForLinkEDU) {
        this.reasonForLinkEDU = reasonForLinkEDU;
    }
    /**
     * @return the uploadStatus
     */
    public String getUploadStatus() {
        return uploadStatus;
    }

    /**
     * @param uploadStatus the uploadStatus to set
     */
    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    
}

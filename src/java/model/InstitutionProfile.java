/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author skdonep
 */
public class InstitutionProfile {

    private String doeUID; //US Department of Education Institution ID Column : UNITID
    private String instAddr; //US DoE Street address or post office box Column : ADDR
    private String instName; //US DoE Column : INSTNM
    private String City; //US DoE Coulmn : CITY
    private String stateCode; //US DoE Column : STABBR
    private String zipCode; // US DoE Coulmn : ZIP
    private String infoDeskPh; // US DoE Coulmn : GENTELE
    private String instURL; // US DoE Column : WEBADDR
    private String adminURL; // Admission Dept URL. US DoE Coulmn : ADMINURL 
    private String finAidURL; // Financial Aid URL. US DoE Coulmn : FAIDURL
    private String appURL; // Applications URL. US DoE Coulmn : APPLURL
    private String highestOffering;//US DoE Coulmn : HLOFFER
    private String offersUG;//US DoE Coulmn : UGOFFER
    private String offersGrad;//US DoE Coulmn : GROFFER
    private String hasHospital;//US DoE Coulmn : HOSPITAL
    private String offersMedicalDegree;//US DoE Coulmn : MEDICAL
    private String locale;//US DoE Coulmn : LOCALE
    private String openToGenPublic;//US DoE Coulmn : OPENPUBL
    private String governingSystem; //US DoE Coulmn : F1SYSNAM
    private String faxNum; //US DoE Coulmn : F1SYSNAM
    private String countyName; //US DoE Coulmn : COUNTYNM
    private float longitude;//US DoE Coulmn : LONGITUD
    private float latitude; //US DoE Coulmn : LATITUDE
    private ArrayList<String> recruiters;
    private boolean hasRecruiters;
    private boolean noRecruiters;

    public InstitutionProfile() {
        recruiters = new ArrayList<>();
    }

    public boolean isNoRecruiters() {
        return noRecruiters;
    }

    public void setNoRecruiters(boolean noRecruiters) {
        this.noRecruiters = noRecruiters;
    }

    public boolean isHasRecruiters() {
        return hasRecruiters;
    }

    public void setHasRecruiters(boolean hasRecruiters) {
        this.hasRecruiters = hasRecruiters;
    }

    public ArrayList<String> getRecruiters() {
        return recruiters;
    }

    public void setRecruiters(ArrayList<String> recruiters) {
        this.recruiters = recruiters;
    }

    public String getDoeUID() {
        return doeUID;
    }

    public void setDoeUID(String doeUID) {
        this.doeUID = doeUID;
    }

    public String getInstAddr() {
        return instAddr;
    }

    public void setInstAddr(String instAddr) {
        this.instAddr = instAddr;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getInfoDeskPh() {
        return infoDeskPh;
    }

    public void setInfoDeskPh(String infoDeskPh) {
        this.infoDeskPh = infoDeskPh;
    }

    public String getInstURL() {
        return instURL;
    }

    public void setInstURL(String instURL) {
        this.instURL = instURL;
    }

    public String getAdminURL() {
        return adminURL;
    }

    public void setAdminURL(String adminURL) {
        this.adminURL = adminURL;
    }

    public String getFinAidURL() {
        return finAidURL;
    }

    public void setFinAidURL(String finAidURL) {
        this.finAidURL = finAidURL;
    }

    public String getAppURL() {
        return appURL;
    }

    public void setAppURL(String appURL) {
        this.appURL = appURL;
    }

    public String getHighestOffering() {
        return highestOffering;
    }

    public void setHighestOffering(String highestOffering) {
        this.highestOffering = highestOffering;
    }

    public String getOffersUG() {
        return offersUG;
    }

    public void setOffersUG(String offersUG) {
        this.offersUG = offersUG;
    }

    public String getOffersGrad() {
        return offersGrad;
    }

    public void setOffersGrad(String offersGrad) {
        this.offersGrad = offersGrad;
    }

    public String getHasHospital() {
        return hasHospital;
    }

    public void setHasHospital(String hasHospital) {
        this.hasHospital = hasHospital;
    }

    public String getOffersMedicalDegree() {
        return offersMedicalDegree;
    }

    public void setOffersMedicalDegree(String offersMedicalDegree) {
        this.offersMedicalDegree = offersMedicalDegree;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getOpenToGenPublic() {
        return openToGenPublic;
    }

    public void setOpenToGenPublic(String openToGenPublic) {
        this.openToGenPublic = openToGenPublic;
    }

    public String getGoverningSystem() {
        return governingSystem;
    }

    public void setGoverningSystem(String governingSystem) {
        this.governingSystem = governingSystem;
    }

    public String getFaxNum() {
        return faxNum;
    }

    public void setFaxNum(String faxNum) {
        this.faxNum = faxNum;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}

package in.co.modelclass;

public class Report_ModelClass_Existing {

    String CUSTOMER_NAME,CUSTOMER_ID,MOBILE_NUMBER,VEHICLECHASSISNUMBER,BARCODE,TAGID,TID,DATEACTIVATION,TIMEACTIVATION;

    public Report_ModelClass_Existing(String CUSTOMER_NAME, String CUSTOMER_ID, String MOBILE_NUMBER, String VEHICLECHASSISNUMBER, String BARCODE, String TAGID, String TID, String DATEACTIVATION, String TIMEACTIVATION) {
        this.CUSTOMER_NAME = CUSTOMER_NAME;
        this.CUSTOMER_ID = CUSTOMER_ID;
        this.MOBILE_NUMBER = MOBILE_NUMBER;
        this.VEHICLECHASSISNUMBER = VEHICLECHASSISNUMBER;
        this.BARCODE = BARCODE;
        this.TAGID = TAGID;
        this.TID = TID;
        this.DATEACTIVATION = DATEACTIVATION;
        this.TIMEACTIVATION = TIMEACTIVATION;
    }

    public String getCUSTOMER_NAME() {
        return CUSTOMER_NAME;
    }

    public void setCUSTOMER_NAME(String CUSTOMER_NAME) {
        this.CUSTOMER_NAME = CUSTOMER_NAME;
    }

    public String getCUSTOMER_ID() {
        return CUSTOMER_ID;
    }

    public void setCUSTOMER_ID(String CUSTOMER_ID) {
        this.CUSTOMER_ID = CUSTOMER_ID;
    }

    public String getMOBILE_NUMBER() {
        return MOBILE_NUMBER;
    }

    public void setMOBILE_NUMBER(String MOBILE_NUMBER) {
        this.MOBILE_NUMBER = MOBILE_NUMBER;
    }

    public String getVEHICLECHASSISNUMBER() {
        return VEHICLECHASSISNUMBER;
    }

    public void setVEHICLECHASSISNUMBER(String VEHICLECHASSISNUMBER) {
        this.VEHICLECHASSISNUMBER = VEHICLECHASSISNUMBER;
    }

    public String getBARCODE() {
        return BARCODE;
    }

    public void setBARCODE(String BARCODE) {
        this.BARCODE = BARCODE;
    }

    public String getTAGID() {
        return TAGID;
    }

    public void setTAGID(String TAGID) {
        this.TAGID = TAGID;
    }

    public String getTID() {
        return TID;
    }

    public void setTID(String TID) {
        this.TID = TID;
    }

    public String getDATEACTIVATION() {
        return DATEACTIVATION;
    }

    public void setDATEACTIVATION(String DATEACTIVATION) {
        this.DATEACTIVATION = DATEACTIVATION;
    }

    public String getTIMEACTIVATION() {
        return TIMEACTIVATION;
    }

    public void setTIMEACTIVATION(String TIMEACTIVATION) {
        this.TIMEACTIVATION = TIMEACTIVATION;
    }
}

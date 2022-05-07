package in.co.modelclass;

public class Report_Modelclass_New {

    String CUSTOMER_NAMe,CUSTOMER_ID,CRN_NUMBER,PAN_CARD_NUMBER,MOBILE_NUMBER,BAR_CODE,TAG_ID,TID,PAYMENT_AMOUNT,PAYMENT_ID,DATE_OF_ACTIVATION,TIME_OF_ACTIVATION;

    public Report_Modelclass_New(String CUSTOMER_NAMe, String CUSTOMER_ID, String CRN_NUMBER,
                                 String PAN_CARD_NUMBER, String MOBILE_NUMBER, String BAR_CODE,
                                 String TAG_ID, String TID, String PAYMENT_AMOUNT, String PAYMENT_ID,
                                 String DATE_OF_ACTIVATION, String TIME_OF_ACTIVATION) {

        this.CUSTOMER_NAMe = CUSTOMER_NAMe;
        this.CUSTOMER_ID = CUSTOMER_ID;
        this.CRN_NUMBER = CRN_NUMBER;
        this.PAN_CARD_NUMBER = PAN_CARD_NUMBER;
        this.MOBILE_NUMBER = MOBILE_NUMBER;
        this.BAR_CODE = BAR_CODE;
        this.TAG_ID = TAG_ID;
        this.TID = TID;
        this.PAYMENT_AMOUNT = PAYMENT_AMOUNT;
        this.PAYMENT_ID = PAYMENT_ID;
        this.DATE_OF_ACTIVATION = DATE_OF_ACTIVATION;
        this.TIME_OF_ACTIVATION = TIME_OF_ACTIVATION;
    }

    public String getCUSTOMER_NAMe() {
        return CUSTOMER_NAMe;
    }

    public void setCUSTOMER_NAMe(String CUSTOMER_NAMe) {
        this.CUSTOMER_NAMe = CUSTOMER_NAMe;
    }

    public String getCUSTOMER_ID() {
        return CUSTOMER_ID;
    }

    public void setCUSTOMER_ID(String CUSTOMER_ID) {
        this.CUSTOMER_ID = CUSTOMER_ID;
    }

    public String getCRN_NUMBER() {
        return CRN_NUMBER;
    }

    public void setCRN_NUMBER(String CRN_NUMBER) {
        this.CRN_NUMBER = CRN_NUMBER;
    }

    public String getPAN_CARD_NUMBER() {
        return PAN_CARD_NUMBER;
    }

    public void setPAN_CARD_NUMBER(String PAN_CARD_NUMBER) {
        this.PAN_CARD_NUMBER = PAN_CARD_NUMBER;
    }

    public String getMOBILE_NUMBER() {
        return MOBILE_NUMBER;
    }

    public void setMOBILE_NUMBER(String MOBILE_NUMBER) {
        this.MOBILE_NUMBER = MOBILE_NUMBER;
    }

    public String getBAR_CODE() {
        return BAR_CODE;
    }

    public void setBAR_CODE(String BAR_CODE) {
        this.BAR_CODE = BAR_CODE;
    }

    public String getTAG_ID() {
        return TAG_ID;
    }

    public void setTAG_ID(String TAG_ID) {
        this.TAG_ID = TAG_ID;
    }

    public String getTID() {
        return TID;
    }

    public void setTID(String TID) {
        this.TID = TID;
    }

    public String getPAYMENT_AMOUNT() {
        return PAYMENT_AMOUNT;
    }

    public void setPAYMENT_AMOUNT(String PAYMENT_AMOUNT) {
        this.PAYMENT_AMOUNT = PAYMENT_AMOUNT;
    }

    public String getPAYMENT_ID() {
        return PAYMENT_ID;
    }

    public void setPAYMENT_ID(String PAYMENT_ID) {
        this.PAYMENT_ID = PAYMENT_ID;
    }

    public String getDATE_OF_ACTIVATION() {
        return DATE_OF_ACTIVATION;
    }

    public void setDATE_OF_ACTIVATION(String DATE_OF_ACTIVATION) {
        this.DATE_OF_ACTIVATION = DATE_OF_ACTIVATION;
    }

    public String getTIME_OF_ACTIVATION() {
        return TIME_OF_ACTIVATION;
    }

    public void setTIME_OF_ACTIVATION(String TIME_OF_ACTIVATION) {
        this.TIME_OF_ACTIVATION = TIME_OF_ACTIVATION;
    }
}

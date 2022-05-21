package in.co.url;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import in.co.hitchpayments.SplashScreen;

public class SessionManager {

    SharedPreferences sharedprefernce;
    SharedPreferences.Editor editor;

    Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "sharedcheckLogin";
    private static final String IS_LOGIN="islogin";
    private static final String IS_USERID="isuserid";
    private static final String IS_RegistrationNumber="RegistrationNumber";
    private static final String IS_UserName="UserName";
    private static final String IS_PrimaryContact="PrimaryContact";
    private static final String IS_SecondaryContact="SecondaryContact";
    private static final String IS_AadharNumber="AadharNumber";
    private static final String IS_PANCardNumber="PANCardNumber";
    private static final String IS_DrivingLicence="DrivingLicence";
    private static final String IS_KYCStatus="KYCStatus";
    private static final String IS_BankName="BankName";
    private static final String IS_IFSCCode="IFSCCode";
    private static final String IS_AccountNumber="AccountNumber";
    private static final String IS_BankVerificationStatus="BankVerificationStatus";
    private static final String IS_NomineeName="NomineeName";
    private static final String IS_RelationshipWith="RelationshipWith";
    private static final String IS_ContactNumber="ContactNumber";
    private static final String IS_PROFILE="profile";

    public SessionManager(Context context) {

        this.context = context;
        sharedprefernce = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedprefernce.edit();
    }


    public Boolean isLogin(){
        return sharedprefernce.getBoolean(IS_LOGIN, false);

    }
    public void setLogin(){

        editor.putBoolean(IS_LOGIN, true);
        editor.commit();

    }

    public void setUserId(String userId){

        editor.putString(IS_USERID,userId);
        editor.commit();

    }
    public String getUserId(){

        return sharedprefernce.getString(IS_USERID,"DEFAULT");
    }

    public void setProfile(String profile){

        editor.putString(IS_PROFILE,profile);
        editor.commit();

    }
    public String getProfile(){

        return sharedprefernce.getString(IS_PROFILE,"DEFAULT");
    }

    public void setRegistrationNumber(String registrationNumber){

        editor.putString(IS_RegistrationNumber,registrationNumber);
        editor.commit();

    }
    public String getRegistrationNumber(){

        return sharedprefernce.getString(IS_RegistrationNumber,"DEFAULT");
    }

    public void setUserName(String userName){

        editor.putString(IS_UserName,userName);
        editor.commit();

    }
    public String getUserName(){

        return sharedprefernce.getString(IS_UserName,"DEFAULT");
    }

    public void setPrimaryContact(String primaryContact){

        editor.putString(IS_PrimaryContact,primaryContact);
        editor.commit();

    }
    public String getPrimaryContact(){

        return sharedprefernce.getString(IS_PrimaryContact,"DEFAULT");
    }

    public void setSecondaryContact(String secondaryContact){

        editor.putString(IS_SecondaryContact,secondaryContact);
        editor.commit();

    }
    public String getSecondaryContact(){

        return sharedprefernce.getString(IS_SecondaryContact,"DEFAULT");
    }



    public void setAadharNumber(String aadharNumber){

        editor.putString(IS_AadharNumber,aadharNumber);
        editor.commit();

    }
    public String getAadharNumber(){

        return sharedprefernce.getString(IS_AadharNumber,"DEFAULT");
    }

    public void setPANCardNumber(String pANCardNumber){

        editor.putString(IS_PANCardNumber,pANCardNumber);
        editor.commit();

    }
    public String getPANCardNumber(){

        return sharedprefernce.getString(IS_PANCardNumber,"DEFAULT");
    }

    public void setDrivingLicence(String drivingLicence){

        editor.putString(IS_DrivingLicence,drivingLicence);
        editor.commit();

    }
    public String getDrivingLicence(){

        return sharedprefernce.getString(IS_DrivingLicence,"DEFAULT");
    }

    public void setKYCStatus(String kYCStatus){

        editor.putString(IS_KYCStatus,kYCStatus);
        editor.commit();

    }
    public String getKYCStatus(){

        return sharedprefernce.getString(IS_KYCStatus,"DEFAULT");
    }

    public void setBankName(String bankName){

        editor.putString(IS_BankName,bankName);
        editor.commit();

    }
    public String getBankName(){

        return sharedprefernce.getString(IS_BankName,"DEFAULT");
    }

    public void setIFSCCode(String iFSCCode){

        editor.putString(IS_IFSCCode,iFSCCode);
        editor.commit();

    }
    public String getIFSCCode(){

        return sharedprefernce.getString(IS_IFSCCode,"DEFAULT");
    }

    public void setAccountNumber(String accountNumber){

        editor.putString(IS_AccountNumber,accountNumber);
        editor.commit();

    }
    public String getAccountNumber(){

        return sharedprefernce.getString(IS_AccountNumber,"DEFAULT");
    }

    public void setBankVerificationStatus(String bankVerificationStatus){

        editor.putString(IS_BankVerificationStatus,bankVerificationStatus);
        editor.commit();

    }
    public String getBankVerificationStatus(){

        return sharedprefernce.getString(IS_BankVerificationStatus,"DEFAULT");
    }

    public void setNomineeName(String nomineeName){

        editor.putString(IS_NomineeName,nomineeName);
        editor.commit();

    }
    public String getNomineeName(){

        return sharedprefernce.getString(IS_NomineeName,"DEFAULT");
    }

    public void setRelationshipWith(String relationshipWith){

        editor.putString(IS_RelationshipWith,relationshipWith);
        editor.commit();

    }
    public String getRelationshipWith(){

        return sharedprefernce.getString(IS_RelationshipWith,"DEFAULT");
    }

    public void setContactNumber(String contactNumber){

        editor.putString(IS_ContactNumber,contactNumber);
        editor.commit();

    }
    public String getContactNumber(){

        return sharedprefernce.getString(IS_ContactNumber,"DEFAULT");
    }


    public static String getIsLogin() {
        return IS_LOGIN;
    }


    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, SplashScreen.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }

}

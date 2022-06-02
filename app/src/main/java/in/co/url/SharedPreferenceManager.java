package in.co.url;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import in.co.hitchpayments.SplashScreen;

public class SharedPreferenceManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Context context;

    int PRIVATE_MODE=0;

    private static final String PREF_NAME = "sharedcheckLogin";
    private static final String IS_LOGIN = "islogin";
    private static final String IS_ORGREQID = "isorgreqid";
    private static final String IS_MOBILENO = "ismobileno";
    private static final String IS_statusArray = "statusArray";
    private static final String IS_TOKENNO = "tokennox";
    private static final String IS_CRN = "iscrn";
    private static final String IS_CUSTOMERSUBTYPE = "CUSTOMERSUBTYPE";
    private static final String IS_CITYNAME = "CITYNAME";
    private static final String IS_STATENAME = "STATENAME";
    private static final String IS_CITYID = "CITYID";
    private static final String IS_STATEID = "STATEID";
    private static final String IS_COUNTRYNAME = "COUNTRYNAME";
    private static final String IS_REGIONID = "REGIONID";
    private static final String IS_REGIONNAME = "REGION_NAME";
    private static final String IS_CUSTOMERID = "CUSTOMERID";
    private static final String IS_AGENTTYPE = "AGENTTYPE";

    public SharedPreferenceManager(Context context){

        this.context =  context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void setAGENTTYPE(String AGENTTYPE){

        editor.putString(IS_AGENTTYPE,AGENTTYPE);
        editor.commit();

    }

    public String getAGENTTYPE(){

        return sharedPreferences.getString(IS_AGENTTYPE,"DEFAULT");
    }

    public void setCUSTOMERID(String CUSTOMERID){

        editor.putString(IS_CUSTOMERID,CUSTOMERID);
        editor.commit();

    }

    public String getCUSTOMERID(){

        return sharedPreferences.getString(IS_CUSTOMERID,"DEFAULT");
    }

    public void setCOUNTRYNAME(String COUNTRYNAME){

        editor.putString(IS_COUNTRYNAME,COUNTRYNAME);
        editor.commit();

    }

    public String getCOUNTRYNAME(){

        return sharedPreferences.getString(IS_COUNTRYNAME,"DEFAULT");
    }

    public void setREGIONID(String REGIONID){

        editor.putString(IS_REGIONID,REGIONID);
        editor.commit();

    }

    public String getREGIONID(){

        return sharedPreferences.getString(IS_REGIONID,"DEFAULT");
    }

    public void setREGIONNAME(String REGIONNAME){

        editor.putString(IS_REGIONNAME,REGIONNAME);
        editor.commit();

    }

    public String getREGIONNAME(){

        return sharedPreferences.getString(IS_REGIONNAME,"DEFAULT");
    }

    public void setCITYID(String CITYNAME){

        editor.putString(IS_CITYID,CITYNAME);
        editor.commit();

    }

    public String getCITYID(){

        return sharedPreferences.getString(IS_CITYID,"DEFAULT");
    }

    public void setSTATEID(String STATENAME){

        editor.putString(IS_STATEID,STATENAME);
        editor.commit();

    }

    public String getSTATEID(){

        return sharedPreferences.getString(IS_STATEID,"DEFAULT");
    }

    public void setCITYNAME(String CITYNAME){

        editor.putString(IS_CITYNAME,CITYNAME);
        editor.commit();

    }

    public String getCITYNAME(){

        return sharedPreferences.getString(IS_CITYNAME,"DEFAULT");
    }

    public void setSTATENAME(String STATENAME){

        editor.putString(IS_STATENAME,STATENAME);
        editor.commit();

    }

    public String getSTATENAME(){

        return sharedPreferences.getString(IS_STATENAME,"DEFAULT");
    }

    public void setOrgreqId(String id){

        editor.putString(IS_ORGREQID,id);
        editor.commit();

    }

    public String getOrgreqId(){

        return sharedPreferences.getString(IS_ORGREQID,"DEFAULT");
    }

    public void setCUSTOMERSUBTYPE(String CUSTOMERSUBTYPE){

        editor.putString(IS_CUSTOMERSUBTYPE,CUSTOMERSUBTYPE);
        editor.commit();

    }

    public String getCUSTOMERSUBTYPE(){

        return sharedPreferences.getString(IS_CUSTOMERSUBTYPE,"DEFAULT");
    }

    public void setTOKENNO(String tokenNo){

        editor.putString(IS_TOKENNO,tokenNo);
        editor.commit();

    }

    public String getTOKENNO(){

        return sharedPreferences.getString(IS_TOKENNO,"DEFAULT");
    }

    public void setCRN(String crn){

        editor.putString(IS_CRN,crn);
        editor.commit();

    }

    public String getCRN(){

        return sharedPreferences.getString(IS_CRN,"DEFAULT");
    }

    public void setStatusArray(String statusArray){

        editor.putString(IS_statusArray,statusArray);
        editor.commit();

    }

    public String getStatusArray(){

        return sharedPreferences.getString(IS_statusArray,"DEFAULT");
    }

    public void setMobileNo(String mobileNo){

        editor.putString(IS_MOBILENO,mobileNo);
        editor.commit();

    }

    public String getMobileNo(){

        return sharedPreferences.getString(IS_MOBILENO,"DEFAULT");
    }

    public Boolean isLogin(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);

    }
    public void setLogin(){

        editor.putBoolean(IS_LOGIN, true);
        editor.commit();

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

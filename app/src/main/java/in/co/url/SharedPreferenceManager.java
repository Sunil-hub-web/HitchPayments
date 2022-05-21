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

    public SharedPreferenceManager(Context context){

        this.context =  context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void setOrgreqId(String id ){

        editor.putString(IS_ORGREQID,id);
        editor.commit();

    }

    public String getOrgreqId(){

        return sharedPreferences.getString(IS_ORGREQID,"DEFAULT");
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

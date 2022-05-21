package in.co.hitchpayments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import in.aabhasjindal.otptextview.OtpTextView;
import in.co.fragment.CustomerDeatilsFragment;
import in.co.url.AppUrl;
import in.co.url.MyJsonArrayRequest;
import in.co.url.SessionManager;
import in.co.url.SharedPreferenceManager;

public class LoginPage extends AppCompatActivity {

    Button btn_signin;
    TextView text_signUp,otpviewtext;
    EditText edit_EnterOTP,edit_UserId;
    TextInputLayout editEnterOTP,editMobileNo;
    String ORGREQID,currentTime;
    SharedPreferenceManager sharedPreferenceManager;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        btn_signin = findViewById(R.id.btn_signin);
        edit_EnterOTP = findViewById(R.id.edit_EnterOTP);
        edit_UserId = findViewById(R.id.edit_UserId);
        editEnterOTP = findViewById(R.id.editEnterOTP);
        editMobileNo = findViewById(R.id.editMobileNo);

        editMobileNo.setVisibility(View.VISIBLE);
        editEnterOTP.setVisibility(View.GONE);
        btn_signin.setText("Send OTP");

        sessionManager = new SessionManager(LoginPage.this);

        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        sharedPreferenceManager = new SharedPreferenceManager(LoginPage.this);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btn_signin.getText().toString().trim().equals("Send OTP")){

                    if(edit_UserId.getText().toString().trim().equals("")){

                        Toast.makeText(LoginPage.this, "Fill The User Id", Toast.LENGTH_SHORT).show();
                    }else{

                        String userid = edit_UserId.getText().toString().trim();
                        sessionManager.setUserId(userid);
                        salesLogin(userid);


                    }

                }else{

                    if(edit_EnterOTP.getText().toString().trim().equals("")){

                        Toast.makeText(LoginPage.this, "Fill OTP", Toast.LENGTH_SHORT).show();
                    }else{

                        String userid = sessionManager.getUserId();
                        String otp = edit_EnterOTP.getText().toString().trim();

                        Log.d("gdhjvhj",userid +" "+ otp);

                        verifyOtp(userid,otp);
                    }

                }
            }
        });

    }

    public void salesLogin(String userId){

        ProgressDialog progressDialog = new ProgressDialog(LoginPage.this);
        progressDialog.setMessage("Verify User Id Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.salesagentlogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    String error = jsonObject.getString("error");
                    String messages = jsonObject.getString("messages");

                    if(status.equals("200")){

                        JSONObject jsonObject_messages = new JSONObject(messages);

                        String responsecode = jsonObject_messages.getString("responsecode");
                        String status1 = jsonObject_messages.getString("status");

                        if(responsecode.equals("00")){

                            //Toast.makeText(LoginPage.this, status1, Toast.LENGTH_LONG).show();
                            Toast.makeText(LoginPage.this, "Otp Send Your Email Id", Toast.LENGTH_LONG).show();

                            editEnterOTP.setVisibility(View.VISIBLE);
                            editMobileNo.setVisibility(View.GONE);
                            btn_signin.setText("Verify Otp");

                        }else{

                            Toast.makeText(LoginPage.this, status1, Toast.LENGTH_LONG).show();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                    Toast.makeText(LoginPage.this.getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_SHORT).show();

                } else {

                    Log.d("responceVolley", "" + error);

                    Toast.makeText(LoginPage.this, "" + error, Toast.LENGTH_SHORT).show();
                }

            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("usrid",userId);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(LoginPage.this);
        requestQueue.add(stringRequest);
    }

    public void verifyOtp(String userId,String Otp){

        ProgressDialog progressDialog = new ProgressDialog(LoginPage.this);
        progressDialog.setMessage("Verify OTP Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.salesagentverifyotp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    Log.d("hjvusfjnbjhf",response.toString());

                    String status = jsonObject.getString("status");
                    String error = jsonObject.getString("error");
                    String messages = jsonObject.getString("messages");

                    if(status.equals("200")){

                        JSONObject jsonObject_messages = new JSONObject(messages);

                        String responsecode = jsonObject_messages.getString("responsecode");
                        String status1 = jsonObject_messages.getString("status");

                        if(responsecode.equals("01")){

                            Toast.makeText(LoginPage.this, status1, Toast.LENGTH_SHORT).show();

                        }else{

                            JSONObject jsonObject_status = new JSONObject(status1);

                            //Toast.makeText(LoginPage.this, status1, Toast.LENGTH_SHORT).show();

                            String salesagentId = jsonObject_status.getString("salesagentId");
                            String bankdetailsid = jsonObject_status.getString("bankdetailsid");
                            String nomineedetailsid = jsonObject_status.getString("nomineedetailsid");
                            String kycdetailsid = jsonObject_status.getString("kycdetailsid");
                            String allowedIdCreation = jsonObject_status.getString("allowedIdCreation");
                            String salesAgentRegdNum = jsonObject_status.getString("salesAgentRegdNum");
                            String Fname = jsonObject_status.getString("Fname");
                            String Lname = jsonObject_status.getString("Lname");
                            String ContactPrimary = jsonObject_status.getString("ContactPrimary");
                            String ContactSecondary = jsonObject_status.getString("ContactSecondary");
                            String ProfileImage = jsonObject_status.getString("ProfileImage");
                            String tollcity = jsonObject_status.getString("toll&city");
                            String status2 = jsonObject_status.getString("status");
                            String bankName = jsonObject_status.getString("bankName");
                            String accountNumber = jsonObject_status.getString("accountNumber");
                            String IFSCCode = jsonObject_status.getString("IFSCCode");
                            String bankkycStatus = jsonObject_status.getString("bankkycStatus");
                            String firstName = jsonObject_status.getString("firstName");
                            String lastName = jsonObject_status.getString("lastName");
                            String relationWith = jsonObject_status.getString("relationWith");
                            String contactNumber = jsonObject_status.getString("contactNumber");
                            String idProof = jsonObject_status.getString("idProof");
                            String aadharNumber = jsonObject_status.getString("aadharNumber");
                            String panCardNumber = jsonObject_status.getString("panCardNumber");
                            String drivingLicenceNumber = jsonObject_status.getString("drivingLicenceNumber");
                            String aadharProof = jsonObject_status.getString("aadharProof");
                            String panCardProof = jsonObject_status.getString("panCardProof");
                            String drivingLicenceProof = jsonObject_status.getString("drivingLicenceProof");
                            String kycStatus = jsonObject_status.getString("kycStatus");


                            sessionManager.setRegistrationNumber(salesAgentRegdNum);
                            sessionManager.setUserName(Fname +" "+ Lname);
                            sessionManager.setPrimaryContact(ContactPrimary);
                            sessionManager.setSecondaryContact(ContactSecondary);
                            sessionManager.setAadharNumber(aadharNumber);
                            sessionManager.setPANCardNumber(panCardNumber);
                            sessionManager.setDrivingLicence(drivingLicenceNumber);
                            sessionManager.setKYCStatus(kycStatus);
                            sessionManager.setBankName(bankName);
                            sessionManager.setIFSCCode(IFSCCode);
                            sessionManager.setAccountNumber(accountNumber);
                            sessionManager.setBankVerificationStatus(bankkycStatus);
                            sessionManager.setNomineeName(firstName +" "+ lastName);
                            sessionManager.setRelationshipWith(relationWith);
                            sessionManager.setContactNumber(contactNumber);
                            sessionManager.setProfile(ProfileImage);

                            Intent intent = new Intent(LoginPage.this,DashBoard.class);
                            startActivity(intent);

                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                    Toast.makeText(LoginPage.this.getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_SHORT).show();

                } else {

                    Log.d("responceVolley", "" + error);

                    Toast.makeText(LoginPage.this, "" + error, Toast.LENGTH_SHORT).show();
                }

            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("usrid",userId);
                params.put("usrotp",Otp);

                Log.d("hgujhd",userId +" "+ Otp);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(LoginPage.this);
        requestQueue.add(stringRequest);
    }

}
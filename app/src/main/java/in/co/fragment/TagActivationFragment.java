package in.co.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
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

import in.co.hitchpayments.R;
import in.co.url.AppUrl;
import in.co.url.MyJsonArrayRequest;
import in.co.url.SharedPreferenceManager;

public class TagActivationFragment extends Fragment {

    RadioGroup radioGroup;
    RadioButton text_NewCustomer, text_ExistingCustomer, genderradioButton;
    LinearLayout lin_SearchStatus, lin_Sendotp, lin_searchDetails;
    EditText edit_MobileNo, edit_MobileNo1,edit_OTP;
    Button btn_SearchStatues, btn_Sendotp;
    TextView name_text,text_CustomerName,text_ContactNumber,text_EmailId,text_CustomerId;
    CardView cardView12;
    TextInputLayout editMobileNo1, editOTP;
    String currentTime = "",ORGREQID;
    SharedPreferenceManager sharedPreferenceManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tagactivation, container, false);

        name_text = view.findViewById(R.id.name_text);
        radioGroup = view.findViewById(R.id.radioGroup);
        text_NewCustomer = view.findViewById(R.id.text_NewCustomer);
        text_ExistingCustomer = view.findViewById(R.id.text_ExistingCustomer);
        lin_SearchStatus = view.findViewById(R.id.lin_SearchStatus);
        lin_Sendotp = view.findViewById(R.id.lin_Sendotp);
        lin_searchDetails = view.findViewById(R.id.lin_searchDetails);
        edit_MobileNo = view.findViewById(R.id.edit_MobileNo);
        edit_MobileNo1 = view.findViewById(R.id.edit_MobileNo1);
        btn_SearchStatues = view.findViewById(R.id.btn_SearchStatues);
        btn_Sendotp = view.findViewById(R.id.btn_Sendotp);
        cardView12 = view.findViewById(R.id.cardView12);
        editMobileNo1 = view.findViewById(R.id.editMobileNo1);
        editOTP = view.findViewById(R.id.editOTP);
        text_CustomerName = view.findViewById(R.id.text_CustomerName);
        text_ContactNumber = view.findViewById(R.id.text_ContactNumber);
        text_EmailId = view.findViewById(R.id.text_EmailId);
        edit_OTP = view.findViewById(R.id.edit_OTP);
        text_CustomerId = view.findViewById(R.id.text_CustomerId);

        editOTP.setVisibility(View.GONE);

        name_text.setText("Tag Activation");

        if(currentTime.equals("")){

            currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        }

        sharedPreferenceManager = new SharedPreferenceManager(getContext());


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                genderradioButton = (RadioButton) group.findViewById(checkedId);

                if (selectedId == -1) {

                    Toast.makeText(getActivity(), "Nothing selected", Toast.LENGTH_SHORT).show();
                } else {

                    String str_radioButton = genderradioButton.getText().toString();

                    if (str_radioButton.equals("New Customer")) {

                        lin_Sendotp.setVisibility(View.VISIBLE);
                        lin_SearchStatus.setVisibility(View.GONE);
                        lin_searchDetails.setVisibility(View.GONE);
                        cardView12.setVisibility(View.GONE);

                    } else if (str_radioButton.equals("Existing Customer")) {

                        lin_SearchStatus.setVisibility(View.VISIBLE);
                        lin_Sendotp.setVisibility(View.GONE);
                        lin_searchDetails.setVisibility(View.GONE);
                        cardView12.setVisibility(View.GONE);

                    }

                }
            }
        });

        btn_Sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btn_Sendotp.getText().toString().trim().equals("Send Otp")) {

                    if(edit_MobileNo1.getText().toString().trim().equals("")){

                        Toast.makeText(getActivity(), "Fill The Mobile No", Toast.LENGTH_LONG).show();

                    }else if (edit_MobileNo1.getText().toString().trim().length() != 10) {

                        edit_MobileNo1.setError("Enter 10 digit Mobile No");

                    }else{


                        String mobile = edit_MobileNo1.getText().toString().trim();
                        long mobile_no = Long.valueOf(mobile);

                        GenerateOTP("230201", "504",currentTime,mobile_no);

                    }


                } else {


                    if(edit_OTP.getText().toString().trim().equals("")){

                        Toast.makeText(getActivity(), "Fill The Details", Toast.LENGTH_LONG).show();

                    }else if (edit_OTP.getText().toString().trim().length() != 6) {

                        edit_OTP.setError("Enter 6 digit Otp");

                    }else{


                        String otp = edit_OTP.getText().toString().trim();

                        String mobile = sharedPreferenceManager.getMobileNo();
                        long mobile_no = Long.valueOf(mobile);

                        String orgreqID = sharedPreferenceManager.getOrgreqId();

                        OTPVerify(orgreqID,otp, "504",mobile_no);

                    }


                }
            }
        });

        btn_SearchStatues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_MobileNo.getText().toString().trim().equals("")) {

                    edit_MobileNo.setError("Enter Mobile No");

                } else if (edit_MobileNo.getText().toString().trim().length() != 10) {

                    edit_MobileNo.setError("Enter 10 digit Mobile No");

                } else {

                    String mobile = edit_MobileNo.getText().toString().trim();
                    long mobile_no = Long.valueOf(mobile);

                    try {

                        VerifyCustomer("230201", "504", mobile_no);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });


        return view;
    }

    public void VerifyCustomer(String agentId, String cpid, long mobileNo) throws JSONException {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Customer Verify Please Wait...");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("AGENTID", agentId);
            jsonObject.put("CPID", cpid);
            jsonObject.put("MOBILENUMBER", mobileNo);

        } catch (Exception e) {

            e.printStackTrace();

        }

        MyJsonArrayRequest jsonObjectRequest = new MyJsonArrayRequest(Request.Method.POST, AppUrl.verifayCustomer, jsonObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                progressDialog.dismiss();

                Log.d("sunilresponse", response.toString());

                try {

                    String responsearray = response.toString();

                    JSONArray jsonArray = new JSONArray(responsearray);

                    JSONObject jsonObject_response = jsonArray.getJSONObject(0);

                    String responseCode = jsonObject_response.getString("RESPONSECODE");
                    String status = jsonObject_response.getString("STATUS");

                    if(responseCode.equalsIgnoreCase("00")){

                        lin_searchDetails.setVisibility(View.VISIBLE);
                        cardView12.setVisibility(View.VISIBLE);

                        String CUSTOMERID = jsonObject_response.getString("CUSTOMERID");
                        String MOBILENUMBER = jsonObject_response.getString("MOBILENUMBER");
                        String CUSTOMERNAME = jsonObject_response.getString("CUSTOMERNAME");
                        String AGENTTYPE = jsonObject_response.getString("AGENTTYPE");
                        String EMAILID = jsonObject_response.getString("EMAILID");

                        text_CustomerId.setText(CUSTOMERID);
                        text_CustomerName.setText(CUSTOMERNAME);
                        text_ContactNumber.setText(MOBILENUMBER);
                        text_EmailId.setText(EMAILID);

                    }else if(responseCode.equalsIgnoreCase("01")){

                        Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();

                    }





                } catch (JSONException e) {
                    e.printStackTrace();

                    Log.d("errorresponse", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                    Toast.makeText(getContext().getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_SHORT).show();

                } else {

                    Log.d("responceVolley", "" + error);

                    Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Content-Type", "application/json");
                params.put("Accept", "application/json");

                return params;
            }
        };



        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }

    public void GenerateOTP(String agentId, String cpid, String reqid, long mobileNo) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("otp Send Please Wait...");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("AGENTID", agentId);
            jsonObject.put("CPID", cpid);
            jsonObject.put("MOBILENUMBER", mobileNo);
            jsonObject.put("REQID", reqid);

        } catch (Exception e) {

            e.printStackTrace();

        }

        MyJsonArrayRequest jsonObjectRequest = new MyJsonArrayRequest(Request.Method.POST, AppUrl.generateOTP, jsonObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                progressDialog.dismiss();

                Log.d("sunilresponse", response.toString());

                try {


                    String responsearray = response.toString();

                    JSONArray jsonArray = new JSONArray(responsearray);

                    JSONObject jsonObject_response = jsonArray.getJSONObject(0);

                    String responseCode = jsonObject_response.getString("RESPONSECODE");
                    String status = jsonObject_response.getString("STATUS");

                    if(responseCode.equalsIgnoreCase("00")){

                        Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();

                        String RESDTSTAMP = jsonObject_response.getString("RESDTSTAMP");
                        String REQID = jsonObject_response.getString("REQID");
                        ORGREQID = jsonObject_response.getString("ORGREQID");
                        String mobile = String.valueOf(mobileNo);

                        sharedPreferenceManager.setOrgreqId(ORGREQID);
                        sharedPreferenceManager.setMobileNo(mobile);

                        editOTP.setVisibility(View.VISIBLE);
                        editMobileNo1.setVisibility(View.GONE);
                        btn_Sendotp.setText("Verify Otp");

                    }else{

                        Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    Log.d("errorresponse", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                    Toast.makeText(getContext().getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_SHORT).show();

                } else {

                    Log.d("responceVolley", "" + error);

                    Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Content-Type", "application/json");
                params.put("Accept", "application/json");

                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }

    public void OTPVerify(String orgreqid, String otp, String reqid, long mobileNo) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("otp Send Please Wait...");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("REQID", reqid);
            jsonObject.put("MOBILENUMBER", mobileNo);
            jsonObject.put("OTP", otp);
            jsonObject.put("ORGREQID", orgreqid);

        } catch (Exception e) {

            e.printStackTrace();

        }

        MyJsonArrayRequest jsonObjectRequest = new MyJsonArrayRequest(Request.Method.POST, AppUrl.oTPVerify, jsonObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                progressDialog.dismiss();

                Log.d("sunilresponse", response.toString());

                try {


                    String responsearray = response.toString();

                    JSONArray jsonArray = new JSONArray(responsearray);

                    JSONObject jsonObject_response = jsonArray.getJSONObject(0);

                    String responseCode = jsonObject_response.getString("RESPONSECODE");
                    String status = jsonObject_response.getString("STATUS");

                    if(responseCode.equalsIgnoreCase("00")){

                        Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();

                        editOTP.setVisibility(View.VISIBLE);
                        editMobileNo1.setVisibility(View.GONE);
                        btn_Sendotp.setText("Verify Otp");

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        CustomerDeatilsFragment customerDeatilsFragment = new CustomerDeatilsFragment();
                        ft.replace(R.id.nav_host_fragment, customerDeatilsFragment);
                        ft.addToBackStack(null);
                        ft.commit();

                        currentTime = "";

                    }else{

                        Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    Log.d("errorresponse", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                    Toast.makeText(getContext().getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_SHORT).show();

                } else {

                    Log.d("responceVolley", "" + error);

                    Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Content-Type", "application/json");
                params.put("Accept", "application/json");

                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }

}

package in.co.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import in.co.hitchpayments.R;
import in.co.url.AppUrl;
import in.co.url.MyJsonArrayRequest;
import in.co.url.SessionManager;
import in.co.url.SharedPreferenceManager;

public class CustomerDeatilsFragment extends Fragment {

    String[] addressProofName = {"-AddressProofName-", "Driving License", "Passport", "voter id"};
    DatePickerDialog.OnDateSetListener setListener;
    int year, month, day, hour, minute;
    HashMap<String, String> addressProofName1 = new HashMap<String, String>();
    Spinner AddressProofName, spinner_Gender;
    String value, date, time;
    TextInputLayout editDateOfBirth;
    MaterialButton btn_VerifayCustomer;
    String currentTime = "", ORGREQID, mobileNO;
    SharedPreferenceManager sharedPreferenceManager;
    EditText edit_FirstName, edit_LastName, edit_PANCardNumber, edit_DateOfBirth, edit_Pincode, edit_AddressProofNumber;
    String str_FirstName, str_LastName, str_PANCardNumber, str_DateOfBirth, str_Gender, str_Pincode,
            str_AddressProofNumber, statusArray, data,agentId;

    String[] genderName = {"-Gender-", "Male", "Female"};

    HashMap<String, String> genderName1 = new HashMap<String, String>();

    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customerdetails, container, false);

        AddressProofName = view.findViewById(R.id.AddressProofName);
        btn_VerifayCustomer = view.findViewById(R.id.btn_VerifayCustomer);
        edit_FirstName = view.findViewById(R.id.edit_FirstName);
        edit_LastName = view.findViewById(R.id.edit_LastName);
        edit_PANCardNumber = view.findViewById(R.id.edit_PANCardNumber);
        edit_DateOfBirth = view.findViewById(R.id.edit_DateOfBirth);
        edit_Pincode = view.findViewById(R.id.edit_Pincode);
        edit_AddressProofNumber = view.findViewById(R.id.edit_AddressProofNumber);
        editDateOfBirth = view.findViewById(R.id.editDateOfBirth);
        spinner_Gender = view.findViewById(R.id.spinner_Gender);

        addressProofName1.put("voter id", "1");
        addressProofName1.put("Driving License", "2");
        addressProofName1.put("Passport", "3");

        sharedPreferenceManager = new SharedPreferenceManager(getContext());
        sessionManager = new SessionManager(getContext());

        ArrayAdapter proofName = new ArrayAdapter(getActivity(), R.layout.spinneritem, addressProofName);
        proofName.setDropDownViewResource(R.layout.spinnerdropdownitem);
        AddressProofName.setAdapter(proofName);
        AddressProofName.setSelection(-1, true);

        genderName1.put("Male", "1");
        genderName1.put("Female", "2");


        ArrayAdapter genName = new ArrayAdapter(getActivity(), R.layout.spinneritem, genderName);
        proofName.setDropDownViewResource(R.layout.spinnerdropdownitem);
        spinner_Gender.setAdapter(genName);
        spinner_Gender.setSelection(-1, true);

        statusArray = sharedPreferenceManager.getStatusArray();
        agentId = sessionManager.getSalesAgentId();

        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        AddressProofName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                data = AddressProofName.getSelectedItem().toString();
                //Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();

                if (data.equals("voter id")) {

                    value = addressProofName1.get("voter id");
                    Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();

                } else if (data.equals("Driving License")) {

                    value = addressProofName1.get("Driving License");
                    Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();

                } else if (data.equals("Passport")) {

                    value = addressProofName1.get("Passport");
                    Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_Gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String data = AddressProofName.getSelectedItem().toString();
                //Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();

                if (data.equals("Male")) {

                    str_Gender = genderName1.get("Male");
                    Toast.makeText(getActivity(), str_Gender, Toast.LENGTH_SHORT).show();

                } else if (data.equals("Female")) {

                    str_Gender = genderName1.get("Female");
                    Toast.makeText(getActivity(), str_Gender, Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_VerifayCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_FirstName.getText().toString().trim().equals("")) {

                    Toast.makeText(getActivity(), "Fill The Details", Toast.LENGTH_SHORT).show();

                } else if (edit_LastName.getText().toString().trim().equals("")) {

                    Toast.makeText(getActivity(), "Fill The Details", Toast.LENGTH_SHORT).show();

                } else if (edit_PANCardNumber.getText().toString().trim().equals("")) {

                    Toast.makeText(getActivity(), "Fill The Details", Toast.LENGTH_SHORT).show();

                } else if (edit_DateOfBirth.getText().toString().trim().equals("")) {

                    Toast.makeText(getActivity(), "Fill The Details", Toast.LENGTH_SHORT).show();

                } else if (edit_Pincode.getText().toString().trim().equals("")) {

                    Toast.makeText(getActivity(), "Fill The Details", Toast.LENGTH_SHORT).show();

                } else if (edit_AddressProofNumber.getText().toString().trim().equals("")) {

                    Toast.makeText(getActivity(), "Fill The Details", Toast.LENGTH_SHORT).show();

                } else {

                    str_FirstName = edit_FirstName.getText().toString().trim();
                    str_LastName = edit_LastName.getText().toString().trim();
                    str_PANCardNumber = edit_PANCardNumber.getText().toString().trim();
                    str_DateOfBirth = edit_DateOfBirth.getText().toString().trim();
                    str_Pincode = edit_Pincode.getText().toString().trim();
                    str_AddressProofNumber = edit_AddressProofNumber.getText().toString().trim();
                    String tagId = sharedPreferenceManager.getStatusArray();

                    AddCustomeDetails(str_PANCardNumber, tagId, str_FirstName, str_LastName, str_DateOfBirth, data, value);

                }

            }
        });

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        date = new SimpleDateFormat("dd/mm/yyyy", Locale.getDefault()).format(new Date());
        time = new SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(new Date());

        editDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        month = month + 1;
                        String date = year + "/" + month + "/" + day;
                        //String date = year+"-"+month+"-"+day;
                        edit_DateOfBirth.setText(date);
                    }
                }, year, month, day);

                //display previous selected date
                datePickerDialog.updateDate(year, month, day);

                //disiable past date
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                datePickerDialog.show();
            }
        });

        return view;
    }

    public void VerifyNSDLCustomer(String REQID, String MOBILENUMBER, String ORGREQID, String FIRSTNAME,
                                   String LASTNAME, String PANNUMBER, String CUSTOMERTYPE, String DOB) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Verify NSDL Customer");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("AGENTID", "230201");
            jsonObject.put("CPID", "504");
            jsonObject.put("REQID", REQID);
            jsonObject.put("MOBILENUMBER", MOBILENUMBER);
            jsonObject.put("ORGREQID", ORGREQID);
            jsonObject.put("FIRSTNAME", FIRSTNAME);
            jsonObject.put("LASTNAME", LASTNAME);
            jsonObject.put("PANNUMBER", PANNUMBER);
            jsonObject.put("CUSTOMERTYPE", CUSTOMERTYPE);
            jsonObject.put("DOB", DOB);

        } catch (Exception e) {

            e.printStackTrace();

        }

        MyJsonArrayRequest myJsonArrayRequest = new MyJsonArrayRequest(Request.Method.POST, AppUrl.VerifyNSDLCustomer, jsonObject, new Response.Listener<JSONArray>() {
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


                    if (responseCode.equalsIgnoreCase("00")) {

                        String TOKENNO = jsonObject_response.getString("TOKENNO");
                        String CUSTOMERSUBTYPE = jsonObject_response.getString("CUSTOMERSUBTYPE");
                        String REQID = jsonObject_response.getString("REQID");
                        String ORGREQID = jsonObject_response.getString("ORGREQID");
                        String CRN = jsonObject_response.getString("CRN");

                        sharedPreferenceManager.setTOKENNO(TOKENNO);
                        sharedPreferenceManager.setCRN(CRN);
                        sharedPreferenceManager.setOrgreqId(ORGREQID);
                        sharedPreferenceManager.setCUSTOMERSUBTYPE(CUSTOMERSUBTYPE);

                        Updatetokenandcnr(TOKENNO, statusArray, CRN);

                        Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
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
        myJsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(myJsonArrayRequest);
    }

    public void AddCustomeDetails(String pancardnumber, String tagactivationid, String firstname, String lastname,
                                  String dob, String addressprofftype, String addressproofnumber) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Add Coustomer Details Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.addcontactnumber, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    String error = jsonObject.getString("error");
                    String messages = jsonObject.getString("messages");

                    JSONObject jsonObject_messages = new JSONObject(messages);

                    String responsecode = jsonObject_messages.getString("responsecode");
                    String statusArray = jsonObject_messages.getString("status");

                    if (responsecode.equals("00")) {
                        Toast.makeText(getActivity(), "Coustomer Details Add", Toast.LENGTH_SHORT).show();

                        ORGREQID = sharedPreferenceManager.getOrgreqId();
                        mobileNO = sharedPreferenceManager.getMobileNo();

                        VerifyNSDLCustomer(currentTime, mobileNO, ORGREQID, str_FirstName, str_LastName, str_PANCardNumber, "1", str_DateOfBirth);

                    } else {
                        Toast.makeText(getActivity(), statusArray, Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(getContext().getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_SHORT).show();

                } else {

                    Log.d("responceVolley", "" + error);

                    Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("pancardnumber", pancardnumber);
                params.put("tagactivationid", tagactivationid);
                params.put("firstname", firstname);
                params.put("lastname", lastname);
                params.put("dob", dob);
                params.put("addressprofftype", addressprofftype);
                params.put("addressproofnumber", addressproofnumber);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    public void Updatetokenandcnr(String tokennumber, String tagactivationid, String crnnumber) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Update Token And Cnr Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.addcontactnumber, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    String error = jsonObject.getString("error");
                    String messages = jsonObject.getString("messages");

                    JSONObject jsonObject_messages = new JSONObject(messages);

                    String responsecode = jsonObject_messages.getString("responsecode");
                    String statusArray = jsonObject_messages.getString("status");

                    if (responsecode.equals("00")) {
                        Toast.makeText(getActivity(), statusArray, Toast.LENGTH_SHORT).show();

                        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                        String pincode = edit_Pincode.getText().toString().trim();

                        sharedPreferenceManager.setStatusArray(statusArray);

                        GetStateCity(currentTime,pincode);

                    } else {
                        Toast.makeText(getActivity(), statusArray, Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(getContext().getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_SHORT).show();

                } else {

                    Log.d("responceVolley", "" + error);

                    Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tokennumber", tokennumber);
                params.put("tagactivationid", tagactivationid);
                params.put("crnnumber", crnnumber);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void GetStateCity(String REQID, String Pincode) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Get State And City");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("AGENTID", "230201");
            jsonObject.put("CPID", "504");
            jsonObject.put("REQID", REQID);
            jsonObject.put("PINCODE", Pincode);

        } catch (Exception e) {

            e.printStackTrace();

        }

        MyJsonArrayRequest myJsonArrayRequest = new MyJsonArrayRequest(Request.Method.POST, AppUrl.getStateCity, jsonObject, new Response.Listener<JSONArray>() {
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

                    if (responseCode.equalsIgnoreCase("00")) {

                        String CITYNAME = jsonObject_response.getString("CITYNAME");
                        String STATENAME = jsonObject_response.getString("STATENAME");
                        String CITYID = jsonObject_response.getString("CITYID");
                        String STATEID = jsonObject_response.getString("STATEID");
                        String COUNTRYNAME = jsonObject_response.getString("COUNTRYNAME");
                        String REGIONID = jsonObject_response.getString("REGIONID");
                        String REGIONNAME = jsonObject_response.getString("REGIONNAME");

                        sharedPreferenceManager.setCITYNAME(CITYNAME);
                        sharedPreferenceManager.setSTATENAME(STATENAME);
                        sharedPreferenceManager.setCITYID(CITYID);
                        sharedPreferenceManager.setSTATEID(STATEID);
                        sharedPreferenceManager.setCOUNTRYNAME(COUNTRYNAME);
                        sharedPreferenceManager.setREGIONID(REGIONID);
                        sharedPreferenceManager.setREGIONNAME(REGIONNAME);

                        String tokenNo = sharedPreferenceManager.getTOKENNO();
                        String subtype = sharedPreferenceManager.getCUSTOMERSUBTYPE();
                        String orgreqid = sharedPreferenceManager.getOrgreqId();
                        String CrnNumber = sharedPreferenceManager.getCRN();
                        String mobileNO = sharedPreferenceManager.getMobileNo();

                        str_FirstName = edit_FirstName.getText().toString().trim();
                        str_LastName = edit_LastName.getText().toString().trim();
                        str_PANCardNumber = edit_PANCardNumber.getText().toString().trim();
                        str_DateOfBirth = edit_DateOfBirth.getText().toString().trim();
                        str_Pincode = edit_Pincode.getText().toString().trim();
                        str_AddressProofNumber = edit_AddressProofNumber.getText().toString().trim();
                        String tagId = sharedPreferenceManager.getStatusArray();

                        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                        WalletCreation(currentTime,tokenNo,orgreqid,"1",subtype,CrnNumber,mobileNO,
                                str_PANCardNumber,str_FirstName,str_LastName,str_DateOfBirth,str_Gender,"India","India",
                                "India",str_Pincode,REGIONID,STATEID,CITYID,REGIONNAME,STATENAME,CITYNAME,data,str_AddressProofNumber
                                );

                    }else{

                        Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
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
        myJsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(30000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(myJsonArrayRequest);

    }

    public void WalletCreation(String REQID,String TOKEN,String ORGREQID,String CUSTOMERTYPE,String CUSTOMERSUBTYPE,
                               String CRNNUMBER,String MOBILENUMBER,String PANNUMBER,String FIRSTNAME,String LASTNAME,String DOB,
                               String GENDER,String ADDRESS1,String ADDRESS2,String ADDRESS3,String PINCODE,String REGIONID,String STATEID,
                               String CITYID,String REGIONNAME,String STATENAME,String CITYNAME,String ADDRESSPROOFTYPE,String ADDRESSPROOFNUMBER){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Wallet Creation Please wait...");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("AGENTID", "230201");
            jsonObject.put("CPID", "504");
            jsonObject.put("REQID", REQID);
            jsonObject.put("TOKEN", TOKEN);
            jsonObject.put("ORGREQID", ORGREQID);
            jsonObject.put("CUSTOMERTYPE", CUSTOMERTYPE);
            jsonObject.put("CUSTOMERSUBTYPE", CUSTOMERSUBTYPE);
            jsonObject.put("CRNNUMBER", CRNNUMBER);
            jsonObject.put("MOBILENUMBER", MOBILENUMBER);
            jsonObject.put("PANNUMBER", PANNUMBER);
            jsonObject.put("FIRSTNAME", FIRSTNAME);
            jsonObject.put("DOB", DOB);
            jsonObject.put("GENDER", GENDER);
            jsonObject.put("ADDRESS1", ADDRESS1);
            jsonObject.put("ADDRESS2", ADDRESS2);
            jsonObject.put("ADDRESS3", ADDRESS3);
            jsonObject.put("PINCODE", PINCODE);
            jsonObject.put("REGIONID", REGIONID);
            jsonObject.put("STATEID", STATEID);
            jsonObject.put("CITYID", CITYID);
            jsonObject.put("REGIONNAME", REGIONNAME);
            jsonObject.put("STATENAME", STATENAME);
            jsonObject.put("CITYNAME", CITYNAME);
            jsonObject.put("ADDRESSPROOFTYPE", ADDRESSPROOFTYPE);
            jsonObject.put("ADDRESSPROOFNUMBER", ADDRESSPROOFNUMBER);

        } catch (Exception e) {

            e.printStackTrace();

        }

        MyJsonArrayRequest myJsonArrayRequest = new MyJsonArrayRequest(Request.Method.POST, AppUrl.walletCreation, jsonObject, new Response.Listener<JSONArray>() {
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

                        String CUSTOMERID = jsonObject_response.getString("CUSTOMERID");
                        String ORGREQID = jsonObject_response.getString("ORGREQID");

                        sharedPreferenceManager.setOrgreqId(ORGREQID);
                        sharedPreferenceManager.setCUSTOMERID(CUSTOMERID);

                        String mobileNo = sharedPreferenceManager.getMobileNo();

                        long lon_MobileNO = Long.valueOf(mobileNo);

                        VerifyCustomer("230201","504",lon_MobileNO);

                    }else{

                        Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
                    }

                }catch(JSONException e){

                    e.printStackTrace();
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
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Content-Type", "application/json");
                params.put("Accept", "application/json");

                return params;
            }
        };

        myJsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(30000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(myJsonArrayRequest);
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

                        String CUSTOMERID = jsonObject_response.getString("CUSTOMERID");
                        String MOBILENUMBER = jsonObject_response.getString("MOBILENUMBER");
                        String CUSTOMERNAME = jsonObject_response.getString("CUSTOMERNAME");
                        String AGENTTYPE = jsonObject_response.getString("AGENTTYPE");
                        String EMAILID = jsonObject_response.getString("EMAILID");

                        String tid = sharedPreferenceManager.getStatusArray();
                        sharedPreferenceManager.setAGENTTYPE(AGENTTYPE);
                        AgentTypeCustomerType(AGENTTYPE,tid,CUSTOMERID);


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

    public void AgentTypeCustomerType(String agenttype, String tagactivationid, String customerid) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Update Token And Cnr Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.addcontactnumber, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    String error = jsonObject.getString("error");
                    String messages = jsonObject.getString("messages");

                    JSONObject jsonObject_messages = new JSONObject(messages);

                    String responsecode = jsonObject_messages.getString("responsecode");
                    String statusArray = jsonObject_messages.getString("status");

                    if (responsecode.equals("00")) {
                        Toast.makeText(getActivity(), statusArray, Toast.LENGTH_SHORT).show();

                        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        AddVehicleFragment addVehicleFragment = new AddVehicleFragment();
                        ft.replace(R.id.nav_host_fragment, addVehicleFragment);
                        ft.addToBackStack(null);
                        ft.commit();


                    } else {
                        Toast.makeText(getActivity(), statusArray, Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(getContext().getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_SHORT).show();

                } else {

                    Log.d("responceVolley", "" + error);

                    Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("agenttype", agenttype);
                params.put("tagactivationid", tagactivationid);
                params.put("customerid", customerid);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}

package in.co.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import in.co.adapter.ClassTagSpinerAdapter;
import in.co.hitchpayments.R;
import in.co.modelclass.ClassoFtag_ModelClass;
import in.co.url.AppUrl;
import in.co.url.MyJsonArrayRequest;
import in.co.url.SessionManager;
import in.co.url.SharedPreferenceManager;
import in.co.url.VolleyMultipartRequest;

public class TagActivationFragment extends Fragment {

    RadioGroup radioGroup;
    RadioButton text_NewCustomer, text_ExistingCustomer, genderradioButton;
    LinearLayout lin_SearchStatus, lin_Sendotp, lin_searchDetails;
    EditText edit_MobileNo, edit_MobileNo1,edit_OTP;
    Button btn_SearchStatues, btn_Sendotp;
    TextView name_text,text_CustomerName,text_ContactNumber,text_EmailId,text_CustomerId,text;
    CardView cardView12;
    TextInputLayout editMobileNo1, editOTP;
    String currentTime = "",ORGREQID,agentId;
    SharedPreferenceManager sharedPreferenceManager;
    SessionManager sessionManager;
    Spinner spinner_SelectProduct, spinner_VehicleClass, spinner_VehicleType, spinner_VehicleDataType;
    AutoCompleteTextView autotext_SelectBarcode;

    String[] vehicleClass = {"-Select Vehicle Class-", "VC4", "VC20"};
    HashMap<String, String> vehicleClass1 = new HashMap<String, String>();

    String[] VehicleDataType = {"-Select Vehicle Data Type-", "Vehicle Number", "Chassis Numner"};
    HashMap<String, String> VehicleDataType1 = new HashMap<String, String>();

    String[] vc4VehicleType = {"-Select Vehicle Type-", "Commercial", "Non-Commercial"};
    HashMap<String, String> vc4VehicleType1 = new HashMap<String, String>();

    String[] vc20VehicleType = {"-Select Vehicle Type-", "Commercial"};
    HashMap<String, String> vc20VehicleType1 = new HashMap<String, String>();

    String clssBar, str_classbar, vechtype, vctype, str_vechtype, str_vctype;

    ArrayList<ClassoFtag_ModelClass> barCode = new ArrayList<>();
    ArrayList<ClassoFtag_ModelClass> product = new ArrayList<>();
    ArrayList<String> str_barCode = new ArrayList<>();

    Button btn_UploadImage,btn_MakePayment;

    EditText edit_CustomerId,edit_CustomerName,edit_ContactNumber,edit_EmailId,edit_FastagClass,
            edit_VehicleNumber;

    String str_CustomerId,str_CustomerName,str_ContactNumber,str_EmailId,str_FastagClass,str_VehicleNumber,
            str_SelectBarcode,str_SelectProduct,parameters;

    ActivityResultLauncher<Intent> resultLauncher;

    Bitmap bitmap;

    ImageView edit_ShowImagePath;


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
        spinner_VehicleDataType = view.findViewById(R.id.spinner_VehicleDataType);
        spinner_VehicleClass = view.findViewById(R.id.spinner_VehicleClass);
        spinner_VehicleType = view.findViewById(R.id.spinner_VehicleType);
       // lin_searchDetails = view.findViewById(R.id.lin_searchDetails);
        edit_MobileNo = view.findViewById(R.id.edit_MobileNo);
        edit_MobileNo1 = view.findViewById(R.id.edit_MobileNo1);
        btn_SearchStatues = view.findViewById(R.id.btn_SearchStatues);
        btn_Sendotp = view.findViewById(R.id.btn_Sendotp);
        cardView12 = view.findViewById(R.id.cardView12);
        editMobileNo1 = view.findViewById(R.id.editMobileNo1);
        editOTP = view.findViewById(R.id.editOTP);
        edit_OTP = view.findViewById(R.id.edit_OTP);
        spinner_VehicleType = view.findViewById(R.id.spinner_VehicleType);
        spinner_SelectProduct = view.findViewById(R.id.spinner_SelectProduct);
        autotext_SelectBarcode = view.findViewById(R.id.autotext_SelectBarcode);
        text = view.findViewById(R.id.text);
        btn_UploadImage = view.findViewById(R.id.btn_UploadImage);
        btn_MakePayment = view.findViewById(R.id.btn_MakePayment);
        edit_ShowImagePath = view.findViewById(R.id.edit_ShowImagePath);
        edit_CustomerId = view.findViewById(R.id.edit_CustomerId);
        edit_CustomerName = view.findViewById(R.id.edit_CustomerName);
        edit_ContactNumber = view.findViewById(R.id.edit_ContactNumber);
        //edit_EmailId = view.findViewById(R.id.edit_EmailId);
        edit_FastagClass = view.findViewById(R.id.edit_FastagClass);
        edit_VehicleNumber = view.findViewById(R.id.edit_VehicleNumber);
        //edit_ShowImagePath = view.findViewById(R.id.edit_ShowImagePath);


        text.setVisibility(View.GONE);

        editOTP.setVisibility(View.GONE);

        name_text.setText("Tag Activation");


        sharedPreferenceManager = new SharedPreferenceManager(getContext());
        sessionManager = new SessionManager(getContext());

        agentId = sessionManager.getSalesAgentId();

        //GetBarcode(agentId);
        Getproduct(agentId);

        vehicleClass1.put("VC4", "4");
        vehicleClass1.put("VC20", "20");

        VehicleDataType1.put("Vehicle Number", "1");
        VehicleDataType1.put("Chassis Numner", "2");

        vc4VehicleType1.put("Commercial", "1");
        vc4VehicleType1.put("Non-Commercial", "2");

        vc4VehicleType1.put("Commercial", "1");

        ArrayAdapter ClassBarcodeAdapter = new ArrayAdapter(getActivity(), R.layout.spinneritem, vehicleClass);
        ClassBarcodeAdapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
        spinner_VehicleClass.setAdapter(ClassBarcodeAdapter);
        spinner_VehicleClass.setSelection(-1, true);

        ArrayAdapter VehicleTypeAdapter = new ArrayAdapter(getActivity(), R.layout.spinneritem, VehicleDataType);
        VehicleTypeAdapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
        spinner_VehicleDataType.setAdapter(VehicleTypeAdapter);
        spinner_VehicleDataType.setSelection(-1, true);

        ArrayAdapter Typeadapter = new ArrayAdapter(getActivity(), R.layout.spinneritem, vc4VehicleType);
        Typeadapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
        spinner_VehicleType.setAdapter(Typeadapter);
        spinner_VehicleType.setSelection(-1, true);

        spinner_VehicleClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                clssBar = spinner_VehicleClass.getSelectedItem().toString();

                if (clssBar.equalsIgnoreCase("VC4")) {

                    ArrayAdapter Typeadapter = new ArrayAdapter(getActivity(), R.layout.spinneritem, vc4VehicleType);
                    Typeadapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
                    spinner_VehicleType.setAdapter(Typeadapter);
                    spinner_VehicleType.setSelection(-1, true);

                    str_classbar = vehicleClass1.get("VC4");
                    Toast.makeText(getActivity(), str_classbar, Toast.LENGTH_SHORT).show();


                } else if (clssBar.equalsIgnoreCase("VC20")) {

                    ArrayAdapter Typeadapter = new ArrayAdapter(getActivity(), R.layout.spinneritem, vc20VehicleType);
                    Typeadapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
                    spinner_VehicleType.setAdapter(Typeadapter);
                    spinner_VehicleType.setSelection(-1, true);

                    str_classbar = vehicleClass1.get("VC20");
                    Toast.makeText(getActivity(), str_classbar, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spinner_VehicleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                vechtype = spinner_VehicleType.getSelectedItem().toString();

                if (vechtype.equalsIgnoreCase("Commercial")) {

                    str_vechtype = vc4VehicleType1.get("Commercial");
                    Toast.makeText(getActivity(), str_vechtype, Toast.LENGTH_SHORT).show();

                } else if (vechtype.equalsIgnoreCase("Non-Commercial")) {

                    str_vechtype = vc4VehicleType1.get("Non-Commercial");
                    Toast.makeText(getActivity(), str_vechtype, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_VehicleDataType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                vctype = spinner_VehicleDataType.getSelectedItem().toString();

                if (vctype.equalsIgnoreCase("Vehicle Number")) {

                    str_vctype = VehicleDataType1.get("Vehicle Number");
                    Toast.makeText(getActivity(), str_vctype, Toast.LENGTH_SHORT).show();

                } else if (vctype.equalsIgnoreCase("Chassis Numner")) {

                    str_vctype = VehicleDataType1.get("Chassis Numner");
                    Toast.makeText(getActivity(), str_vctype, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                genderradioButton = (RadioButton) group.findViewById(checkedId);

                if (selectedId == -1) {

                    Toast.makeText(getActivity(), "Nothing selected", Toast.LENGTH_LONG).show();
                } else {

                    String str_radioButton = genderradioButton.getText().toString();

                    if (str_radioButton.equals("New Customer")) {

                        lin_Sendotp.setVisibility(View.VISIBLE);
                        lin_SearchStatus.setVisibility(View.GONE);
                       // lin_searchDetails.setVisibility(View.GONE);
                        cardView12.setVisibility(View.GONE);

                    } else if (str_radioButton.equals("Existing Customer")) {

                        lin_SearchStatus.setVisibility(View.VISIBLE);
                        lin_Sendotp.setVisibility(View.GONE);
                        //lin_searchDetails.setVisibility(View.GONE);
                        cardView12.setVisibility(View.GONE);

                    }

                }
            }
        });

        btn_Sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* FragmentTransaction ft = getFragmentManager().beginTransaction();
                CustomerDeatilsFragment customerDeatilsFragment = new CustomerDeatilsFragment();
                ft.replace(R.id.nav_host_fragment, customerDeatilsFragment);
                ft.addToBackStack(null);
                ft.commit();*/

                if (btn_Sendotp.getText().toString().trim().equals("Send Otp")) {

                    if(edit_MobileNo1.getText().toString().trim().equals("")){

                        Toast.makeText(getActivity(), "Fill The Mobile No", Toast.LENGTH_LONG).show();

                    }else if (edit_MobileNo1.getText().toString().trim().length() != 10) {

                        edit_MobileNo1.setError("Enter 10 digit Mobile No");

                    }else{


                        String mobile = edit_MobileNo1.getText().toString().trim();
                        long mobile_no = Long.valueOf(mobile);

                        int min = 10;
                        int max = 10000;
                        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
                        String currentTime = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault()).format(new Date());
                        String uniqueId = random_int + currentTime;

                        GenerateOTP("230201", "504",uniqueId,mobile_no);

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

                        int min = 10;
                        int max = 10000;
                        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
                        String currentTime = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault()).format(new Date());
                        String uniqueId = random_int + currentTime;

                        OTPVerify(orgreqID,otp, uniqueId,mobile_no);

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

        btn_MakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(edit_CustomerId.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), " Fill The CustomerId Details", Toast.LENGTH_LONG).show();

                }else if(edit_CustomerName.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Fill The CustomerName Details", Toast.LENGTH_LONG).show();

                }else if(edit_ContactNumber.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Fill The ContactNumber Details", Toast.LENGTH_LONG).show();

                }else if(edit_VehicleNumber.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Fill The VehicleNumber Details", Toast.LENGTH_LONG).show();

                }else{

                    int min = 10;
                    int max = 10000;
                    int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
                    String currentTime = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault()).format(new Date());
                    String uniqueId = random_int + currentTime;

                    str_CustomerId = edit_CustomerId.getText().toString().trim();
                    str_CustomerName = edit_CustomerName.getText().toString().trim();
                    str_ContactNumber = edit_ContactNumber.getText().toString().trim();
                    //str_EmailId = edit_EmailId.getText().toString().trim();
                    str_FastagClass = edit_FastagClass.getText().toString().trim();
                    str_VehicleNumber = edit_VehicleNumber.getText().toString().trim();
                    str_SelectBarcode = autotext_SelectBarcode.getText().toString().trim();
                    str_SelectProduct = spinner_SelectProduct.getSelectedItem().toString();

                    String tagid = sharedPreferenceManager.getStatusArray();
                    String customerid = sharedPreferenceManager.getCUSTOMERID();

                    //AgentTypeCustomerType(tagid,str_vctype,bitmap,str_vctype,str_VehicleNumber,str_SelectBarcode,uniqueId,str_classbar,customerid);

                }
            }
        });

        btn_UploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFileChooser();
            }
        });

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                Intent intent = result.getData();

                if (intent != null) {

                    try {

                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), intent.getData());
                        edit_ShowImagePath.setImageBitmap(bitmap);

                    } catch (Exception e) {

                        e.printStackTrace();

                        Toast.makeText(getContext(), "Not Found", Toast.LENGTH_LONG).show();
                    }
                }else{

                    Toast.makeText(getContext(), "Data Not Found", Toast.LENGTH_LONG).show();
                }
            }
        });

        autotext_SelectBarcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

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

                       // lin_searchDetails.setVisibility(View.VISIBLE);
                        cardView12.setVisibility(View.VISIBLE);

                        String CUSTOMERID = jsonObject_response.getString("CUSTOMERID");
                        String MOBILENUMBER = jsonObject_response.getString("MOBILENUMBER");
                        String CUSTOMERNAME = jsonObject_response.getString("CUSTOMERNAME");
                        String AGENTTYPE = jsonObject_response.getString("AGENTTYPE");
                        String EMAILID = jsonObject_response.getString("EMAILID");

                        edit_CustomerId.setText(CUSTOMERID);
                        edit_CustomerName.setText(CUSTOMERNAME);
                        edit_ContactNumber.setText(MOBILENUMBER);
                        //edit_EmailId.setText(EMAILID);

                        sharedPreferenceManager.setCUSTOMERID(CUSTOMERID);
                        sharedPreferenceManager.setMobileNo(MOBILENUMBER);
                        sharedPreferenceManager.setCUSTOMERNAME(CUSTOMERNAME);

                    }else if(responseCode.equalsIgnoreCase("01")){

                        Toast.makeText(getActivity(), status, Toast.LENGTH_LONG).show();

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

                    Toast.makeText(getContext().getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_LONG).show();

                } else {

                    Log.d("responceVolley", "" + error);

                    Toast.makeText(getActivity(), "" + error, Toast.LENGTH_LONG).show();
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

                        Toast.makeText(getActivity(), status, Toast.LENGTH_LONG).show();

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

                        Toast.makeText(getActivity(), status, Toast.LENGTH_LONG).show();

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

                    Toast.makeText(getContext().getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_LONG).show();

                } else {

                    Log.d("responceVolley", "" + error);

                    Toast.makeText(getActivity(), "" + error, Toast.LENGTH_LONG).show();
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

            jsonObject.put("AGENTID","230201");
            jsonObject.put("CPID", "504");
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

                        ORGREQID = jsonObject_response.getString("ORGREQID");

                        sharedPreferenceManager.setOrgreqId(ORGREQID);

                        Toast.makeText(getActivity(), status, Toast.LENGTH_LONG).show();

                        editOTP.setVisibility(View.VISIBLE);
                        editMobileNo1.setVisibility(View.GONE);
                        btn_Sendotp.setText("Verify Otp");
                        String mobile = String.valueOf(mobileNo);

                        AddContactNumber(agentId,mobile);

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        CustomerDeatilsFragment customerDeatilsFragment = new CustomerDeatilsFragment();
                        ft.replace(R.id.nav_host_fragment, customerDeatilsFragment);
                        ft.addToBackStack(null);
                        ft.commit();

                        currentTime = "";

                    }else{

                        Toast.makeText(getActivity(), status, Toast.LENGTH_LONG).show();
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

                    Toast.makeText(getContext().getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_LONG).show();

                } else {

                    Log.d("responceVolley", "" + error);

                    Toast.makeText(getActivity(), "" + error, Toast.LENGTH_LONG).show();
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

    public void GetBarcode(String salesAgentId){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Get Barcode Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.getbarcode, new Response.Listener<String>() {
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

                    if(responsecode.equals("00")){

                        JSONArray jsonArray_status = new JSONArray(statusArray);

                        for (int i=0;i<jsonArray_status.length();i++){

                            JSONObject jsonObject_status = jsonArray_status.getJSONObject(i);

                            String barcode = jsonObject_status.getString("barcode");

                            if(barcode.equals("")){

                            }else{

                                ClassoFtag_ModelClass ftag_modelClass = new ClassoFtag_ModelClass(barcode);
                                barCode.add(ftag_modelClass);

                                str_barCode.add(barcode);
                            }

                        }

                       /* ClassTagSpinerAdapter classTagSpinerAdapter = new ClassTagSpinerAdapter(getActivity(),R.layout.spinneritem,barCode);
                        classTagSpinerAdapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
                        autotext_SelectBarcode.setAdapter(classTagSpinerAdapter);*/

                        ArrayAdapter<String> autoTextBarCode = new ArrayAdapter<String>(getActivity(), R.layout.spinneritem, str_barCode);
                        autotext_SelectBarcode.setAdapter(autoTextBarCode);
                        autotext_SelectBarcode.setThreshold(1);//will start working from first character
                        autotext_SelectBarcode.setTextColor(ContextCompat.getColor(getActivity(), R.color.danger));
                    }

                }catch(Exception e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                    Toast.makeText(getContext().getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_LONG).show();

                } else {

                    Log.d("responceVolley", "" + error);

                    Toast.makeText(getActivity(), "" + error, Toast.LENGTH_LONG).show();
                }

            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("userid",salesAgentId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void Getproduct(String salesAgentId){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Get Barcode Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.getproduct, new Response.Listener<String>() {
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

                    if(responsecode.equals("00")){

                        JSONArray jsonArray_status = new JSONArray(statusArray);

                        for (int i=0;i<jsonArray_status.length();i++){

                            JSONObject jsonObject_status = jsonArray_status.getJSONObject(i);

                            String prodctCode = jsonObject_status.getString("prodctCode");

                            if(prodctCode.equals("")){

                            }else{

                                ClassoFtag_ModelClass ftag_modelClass = new ClassoFtag_ModelClass(prodctCode);
                                product.add(ftag_modelClass);
                            }

                        }

                        ClassTagSpinerAdapter classTagSpinerAdapter = new ClassTagSpinerAdapter(getActivity(),R.layout.spinneritem,product);
                        classTagSpinerAdapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
                        spinner_SelectProduct.setAdapter(classTagSpinerAdapter);
                        spinner_SelectProduct.setSelection(-1,true);
                    }

                }catch(Exception e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                    Toast.makeText(getContext().getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_LONG).show();

                } else {

                    Log.d("responceVolley", "" + error);

                    Toast.makeText(getActivity(), "" + error, Toast.LENGTH_LONG).show();
                }

            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("userid",salesAgentId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void AddContactNumber(String salesAgentId,String mobileNo){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Add Mobile No Please Wait...");
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

                    if(responsecode.equals("00")){
                        Toast.makeText(getActivity(), "Mobile No Add", Toast.LENGTH_LONG).show();

                        sharedPreferenceManager.setStatusArray(statusArray);

                        ORGREQID = sharedPreferenceManager.getOrgreqId();
                        UpdateORGREQID(ORGREQID,statusArray);

                    }else{
                        Toast.makeText(getActivity(), statusArray, Toast.LENGTH_LONG).show();
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

                    Toast.makeText(getContext().getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_LONG).show();

                } else {

                    Log.d("responceVolley", "" + error);

                    Toast.makeText(getActivity(), "" + error, Toast.LENGTH_LONG).show();
                }

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("userid",salesAgentId);
                params.put("contactnumber",mobileNo);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void UpdateORGREQID(String orgreqid,String tagactivationid){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Add Mobile No Please Wait...");
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

                    if(responsecode.equals("00")){
                        Toast.makeText(getActivity(), statusArray, Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getActivity(), statusArray, Toast.LENGTH_LONG).show();
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

                    Toast.makeText(getContext().getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_LONG).show();

                } else {

                    Log.d("responceVolley", "" + error);

                    Toast.makeText(getActivity(), "" + error, Toast.LENGTH_LONG).show();
                }

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("orgreqid",orgreqid);
                params.put("tagactivationid",tagactivationid);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void showFileChooser() {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        resultLauncher.launch(Intent.createChooser(intent, "title"));

    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void AgentTypeCustomerType(String tagactivationid, String vehicletype,Bitmap rcbook,String vehiclenumbertype,String vehiclechasisnumber,
                                      String barcodeid,String transactionid,String classofBarcode,String productid) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Data Upload Please wait...");
        progressDialog.show();


        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, AppUrl.addcontactnumber, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(new String(response.data));

                    String status = jsonObject.getString("status");
                    String error = jsonObject.getString("error");
                    String messages = jsonObject.getString("messages");

                    JSONObject jsonObject_messages = new JSONObject(messages);

                    String responsecode = jsonObject_messages.getString("responsecode");
                    String statusArray = jsonObject_messages.getString("status");

                    if (responsecode.equals("00")) {

                        Toast.makeText(getActivity(), statusArray, Toast.LENGTH_LONG).show();

                        String imgdata = jsonObject_messages.getString("imgdata");

                        String mobile = sharedPreferenceManager.getMobileNo();
                        String CustomerID = sharedPreferenceManager.getCUSTOMERID();
                        String tagid = sharedPreferenceManager.getStatusArray();
                        String customerid = sharedPreferenceManager.getCUSTOMERID();
                        String agentType = sharedPreferenceManager.getAGENTTYPE();

                        str_CustomerId = edit_CustomerId.getText().toString().trim();
                        str_CustomerName = edit_CustomerName.getText().toString().trim();
                        str_ContactNumber = edit_ContactNumber.getText().toString().trim();

                       // sharedPreferenceManager.setStatusArray(tagid);



                        int min = 10;
                        int max = 10000;
                        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
                        String currentTime = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault()).format(new Date());
                        String uniqueId = random_int + currentTime;

                        JSONArray jsonarray =  new JSONArray();
                        JSONObject params = new JSONObject();

                        try {

                            params.put("VEHICLECLASS",str_classbar);
                            params.put("VEHICLENO",str_VehicleNumber);
                            params.put("COMMERCIALTYPE",str_vechtype);
                            params.put("PARAM1",str_vctype);
                            params.put("PARAM3","2");
                            params.put("MINIAMOUNT","150.00");
                            params.put("DEPOSITAMOUNT","50.00");
                            params.put("CARDCOST","0.00");
                            params.put("TOTAL","200.00");
                            params.put("SERIALNO",str_SelectBarcode);
                            params.put("CBSCOST","200.00");
                            params.put("AGENTTYPE",agentType);
                            params.put("CASATYPE","2");
                            params.put("RCBookUpload",imgdata);

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Log.d("sunil error",e.toString());
                        }

                        jsonarray.put(params);
                        Log.d("Ranjeeet",jsonarray.toString());
                        //Toast.makeText(getActivity(), jsonarray.toString(), Toast.LENGTH_LONG).show();

                        addVehicle(uniqueId,str_ContactNumber,str_CustomerId,jsonarray);


                    } else {

                        Toast.makeText(getActivity(), statusArray, Toast.LENGTH_LONG).show();
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

                    Toast.makeText(getContext().getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_LONG).show();

                } else {

                    Log.d("responceVolley", "" + error);

                    Toast.makeText(getActivity(), "" + error, Toast.LENGTH_LONG).show();
                }


            }
        }) {
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                params.put("tagactivationid", tagactivationid);
                params.put("vehicletype", vehicletype);
                params.put("vehiclenumbertype", vehiclenumbertype);
                params.put("vehiclechasisnumber", vehiclechasisnumber);
                params.put("barcodeid", barcodeid);
                params.put("transactionid", transactionid);
                params.put("classofBarcode", classofBarcode);
                params.put("productid", productid);

                return params;

            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                long imagename = System.currentTimeMillis();
                params.put("rcbook", new DataPart(imagename + ".png", getFileDataFromDrawable(rcbook)));

                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(volleyMultipartRequest);

    }
    public void addVehicle(String TRANSACTIONID, String MOBILENO, String CUSTOMERID, JSONArray jsonArray) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Add Vehicle Please Wait...");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("TRANSACTIONID", TRANSACTIONID);
            jsonObject.put("CPID", "504");
            jsonObject.put("AGENTID", "230201");
            jsonObject.put("MOBILENO", MOBILENO);
            jsonObject.put("CUSTOMERID", CUSTOMERID);
            jsonObject.put("ITEM", jsonArray);

        } catch (Exception e) {

            e.printStackTrace();

        }

        MyJsonArrayRequest myJsonArrayRequest = new MyJsonArrayRequest(Request.Method.POST, AppUrl.addVehicle, jsonObject, new Response.Listener<JSONArray>() {
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
                    String RECEIPT = jsonObject_response.getString("RECEIPT");
                    String TAG = jsonObject_response.getString("TAG");

                    if (responseCode.equalsIgnoreCase("00")) {

                       // Toast.makeText(getActivity(), status, Toast.LENGTH_LONG).show();
                        JSONArray jsonArray_TAG = new JSONArray(TAG);

                        JSONObject jsonObject_TAG = jsonArray_TAG.getJSONObject(0);

                        String OPERATION = jsonObject_TAG.getString("OPERATION");
                        String SERIALNO = jsonObject_TAG.getString("SERIALNO");
                        String CHASSISNUMBER = jsonObject_TAG.getString("CHASSISNUMBER");
                        String ERRORCODE = jsonObject_TAG.getString("ERRORCODE");
                        String VEHICLENO = jsonObject_TAG.getString("VEHICLENO");
                        String VEHICLETYPE = jsonObject_TAG.getString("VEHICLETYPE");

                        String RESULT = jsonObject_TAG.getString("RESULT");

                        if(RESULT.equals("230201")){


                            Toast.makeText(getActivity(), status, Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(), RESULT, Toast.LENGTH_LONG).show();

                            int min = 10;
                            int max = 10000;
                            int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
                            String currentTime = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault()).format(new Date());
                            String uniqueId = random_int + currentTime;

                            String statues = sharedPreferenceManager.getStatusArray();
                            String agentid = sessionManager.getSalesAgentId();
                            str_SelectBarcode = autotext_SelectBarcode.getText().toString().trim();

                            Updatetokenandcnr(statues,str_SelectBarcode,agentid,"200.00",uniqueId);

                        }else{

                            Toast.makeText(getActivity(), RESULT, Toast.LENGTH_LONG).show();
                        }

                    } else {

                        Toast.makeText(getActivity(), status, Toast.LENGTH_LONG).show();
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

                    Toast.makeText(getContext().getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_LONG).show();

                } else {

                    Log.d("responceVolley", "" + error);

                    Toast.makeText(getActivity(), "" + error, Toast.LENGTH_LONG).show();
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

        myJsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(myJsonArrayRequest);

    }

    public void Updatetokenandcnr(String tagactivationid, String barcodeid, String userid,String fastagprice,String transactionid) {

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

                        Toast.makeText(getActivity(), statusArray, Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getActivity(), statusArray, Toast.LENGTH_LONG).show();
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

                    Toast.makeText(getContext().getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_LONG).show();

                } else {

                    Log.d("responceVolley", "" + error);

                    Toast.makeText(getActivity(), "" + error, Toast.LENGTH_LONG).show();
                }

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("tagactivationid", tagactivationid);
                params.put("barcodeid", barcodeid);
                params.put("userid", userid);
                params.put("fastagprice", fastagprice);
                params.put("transactionid", transactionid);

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}

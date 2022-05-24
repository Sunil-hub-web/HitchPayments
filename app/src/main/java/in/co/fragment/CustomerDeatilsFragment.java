package in.co.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import in.co.hitchpayments.R;
import in.co.url.AppUrl;
import in.co.url.MyJsonArrayRequest;
import in.co.url.SharedPreferenceManager;

public class CustomerDeatilsFragment extends Fragment {

   String[] addressProofName = {"-AddressProofName-", "Driving License","Passport","voter id"};

    HashMap<String, String> addressProofName1 = new HashMap<String, String>();
    Spinner AddressProofName;
    String value;
    MaterialButton btn_VerifayCustomer;
    String currentTime = "",ORGREQID,mobileNO;
    SharedPreferenceManager sharedPreferenceManager;
    EditText edit_FirstName,edit_LastName,edit_PANCardNumber,edit_DateOfBirth,edit_Gender,edit_Pincode,edit_AddressProofNumber;
    String str_FirstName,str_LastName,str_PANCardNumber,str_DateOfBirth,str_Gender,str_Pincode,str_AddressProofNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customerdetails,container,false);

        AddressProofName = view.findViewById(R.id.AddressProofName);
        btn_VerifayCustomer = view.findViewById(R.id.btn_VerifayCustomer);
        edit_FirstName = view.findViewById(R.id.edit_FirstName);
        edit_LastName = view.findViewById(R.id.edit_LastName);
        edit_PANCardNumber = view.findViewById(R.id.edit_PANCardNumber);
        edit_DateOfBirth = view.findViewById(R.id.edit_DateOfBirth);
        edit_Gender = view.findViewById(R.id.edit_Gender);
        edit_Pincode = view.findViewById(R.id.edit_Pincode);
        edit_AddressProofNumber = view.findViewById(R.id.edit_AddressProofNumber);

        addressProofName1.put("voter id","1");
        addressProofName1.put("Driving License","2");
        addressProofName1.put("Passport","3");

        sharedPreferenceManager = new SharedPreferenceManager(getContext());

        ArrayAdapter proofName = new ArrayAdapter(getActivity(), R.layout.spinneritem,addressProofName);
        proofName.setDropDownViewResource(R.layout.spinnerdropdownitem);
        AddressProofName.setAdapter(proofName);
        AddressProofName.setSelection(-1, true);

        if(currentTime.equals("")){

            currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        }

        AddressProofName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String data = AddressProofName.getSelectedItem().toString();
                //Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();

                   if(data.equals("voter id")){

                    value = addressProofName1.get("voter id");
                    Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();

                }else if(data.equals("Driving License")){

                    value = addressProofName1.get("Driving License");
                    Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();

                }else if(data.equals("Passport")){

                    value = addressProofName1.get("Passport");
                    Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_VerifayCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edit_FirstName.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Fill The Details", Toast.LENGTH_SHORT).show();

                }else if(edit_LastName.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Fill The Details", Toast.LENGTH_SHORT).show();

                }else if(edit_PANCardNumber.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Fill The Details", Toast.LENGTH_SHORT).show();

                }else if(edit_DateOfBirth.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Fill The Details", Toast.LENGTH_SHORT).show();

                }else if(edit_Gender.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Fill The Details", Toast.LENGTH_SHORT).show();

                }else if(edit_Pincode.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Fill The Details", Toast.LENGTH_SHORT).show();

                }else if(edit_AddressProofNumber.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Fill The Details", Toast.LENGTH_SHORT).show();

                }else{

                    str_FirstName = edit_FirstName.getText().toString().trim();
                    str_LastName = edit_LastName.getText().toString().trim();
                    str_PANCardNumber = edit_PANCardNumber.getText().toString().trim();
                    str_DateOfBirth = edit_DateOfBirth.getText().toString().trim();
                    str_Gender = edit_Gender.getText().toString().trim();
                    str_Pincode = edit_Pincode.getText().toString().trim();
                    str_AddressProofNumber = edit_AddressProofNumber.getText().toString().trim();

                    ORGREQID = sharedPreferenceManager.getOrgreqId();
                    mobileNO = sharedPreferenceManager.getMobileNo();

                    VerifyNSDLCustomer(currentTime,mobileNO,ORGREQID,str_FirstName,str_LastName,str_PANCardNumber,"1",str_DateOfBirth);
                }

            }
        });


        return view;
    }

    public void VerifyNSDLCustomer(String REQID,String MOBILENUMBER,String ORGREQID,String FIRSTNAME,
                                   String LASTNAME,String PANNUMBER,String CUSTOMERTYPE,String DOB){

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
        myJsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(myJsonArrayRequest);
    }
}

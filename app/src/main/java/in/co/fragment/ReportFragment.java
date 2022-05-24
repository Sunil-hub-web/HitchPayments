package in.co.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.co.adapter.ExistingReportAdapter;
import in.co.adapter.NewReportAdapter;
import in.co.hitchpayments.R;
import in.co.modelclass.Report_ModelClass_Existing;
import in.co.modelclass.Report_Modelclass_New;
import in.co.url.AppUrl;
import in.co.url.SessionManager;

public class ReportFragment extends Fragment {

    RadioGroup radioGroup;
    RadioButton text_NewCustomer,text_ExistingCustomer,genderradioButton;
    RecyclerView recyclerReport;
    LinearLayoutManager linearLayoutManager;
    TextView name_text;
    ArrayList<Report_ModelClass_Existing> existingReport;
    ArrayList<Report_Modelclass_New> newReport;
    ExistingReportAdapter existingReportAdapter;
    NewReportAdapter newReportAdapter;
    SessionManager sessionManager;
    String agentId;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report,container,false);

        //recyclerReport = view.findViewById(R.id.recyclerReport);
        radioGroup = view.findViewById(R.id.radioGroup);
        text_NewCustomer = view.findViewById(R.id.text_NewCustomer);
        text_ExistingCustomer = view.findViewById(R.id.text_ExistingCustomer);
        name_text = view.findViewById(R.id.name_text);
        recyclerReport = view.findViewById(R.id.recyclerReport);

        name_text.setText("Report");

        sessionManager = new SessionManager(getContext());

        agentId = sessionManager.getSalesAgentId();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                genderradioButton = (RadioButton)group.findViewById(checkedId);

                if(selectedId==-1){

                    Toast.makeText(getActivity(),"Nothing selected", Toast.LENGTH_SHORT).show();
                }
                else{

                    String str_radioButton = genderradioButton.getText().toString();

                    if(str_radioButton.equals("New Customer")){

                        NewCustomerReport(agentId);

                    }else if(str_radioButton.equals("Existing Customer")){

                        ExistingCustomerReport(agentId);

                    }

                }
            }
        });

        return view;
    }

    public void ExistingCustomerReport(String salesAgentId){


        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Retrive Report Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.existingcustomerreport, new Response.Listener<String>() {
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

                    if(responsecode.equals("00")) {

                        existingReport = new ArrayList<>();

                        JSONArray jsonArray_status = new JSONArray(statusArray);

                        if (jsonArray_status.length() != 0) {

                            for (int i = 0; i < jsonArray_status.length(); i++) {

                                JSONObject jsonObject_status = jsonArray_status.getJSONObject(i);

                                String existinguserid = jsonObject_status.getString("existinguserid");
                                String productid = jsonObject_status.getString("productid");
                                String classofBarcode = jsonObject_status.getString("classofBarcode");
                                String vehicleType = jsonObject_status.getString("vehicleType");
                                String customername = jsonObject_status.getString("customername");
                                String mobileNumber = jsonObject_status.getString("mobileNumber");
                                String rcbook = jsonObject_status.getString("rcbook");
                                String vehicleNumbertype = jsonObject_status.getString("vehicleNumbertype");
                                String vehiclechasisnumber = jsonObject_status.getString("vehiclechasisnumber");
                                String salesagentId = jsonObject_status.getString("salesagentId");
                                String salesagentType = jsonObject_status.getString("salesagentType");
                                String customerid = jsonObject_status.getString("customerid");
                                String agenttype = jsonObject_status.getString("agenttype");
                                String barcodeid = jsonObject_status.getString("barcodeid");
                                String resultTag = jsonObject_status.getString("resultTag");
                                String statusTag = jsonObject_status.getString("statusTag");
                                String transactionstatus = jsonObject_status.getString("transactionstatus");
                                String transactionid = jsonObject_status.getString("transactionid");
                                String txnstatus = jsonObject_status.getString("txnstatus");
                                String datetime = jsonObject_status.getString("datetime");

                                String[] date_time = datetime.split(" ");

                                Log.d("datatimeclass", date_time.toString());

                                String date = date_time[0];
                                String time = date_time[1];

                                Report_ModelClass_Existing reportModelClassExisting = new Report_ModelClass_Existing(
                                        customername, customerid, mobileNumber, vehiclechasisnumber, barcodeid, "", "", date, time
                                );

                                existingReport.add(reportModelClassExisting);
                            }
                            recyclerReport.setVisibility(View.VISIBLE);

                            linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                            existingReportAdapter = new ExistingReportAdapter(getActivity(),existingReport);
                            recyclerReport.setLayoutManager(linearLayoutManager);
                            recyclerReport.setHasFixedSize(true);
                            recyclerReport.setAdapter(existingReportAdapter);

                        }else{

                            Toast.makeText(getActivity(), "No Data Avilable", Toast.LENGTH_SHORT).show();

                            recyclerReport.setVisibility(View.GONE);
                        }
                    }else{

                        Toast.makeText(getActivity(), "Invalide User Id", Toast.LENGTH_SHORT).show();
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

    public void NewCustomerReport(String salesAgentId){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Retrive Report Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.newcustomerreport, new Response.Listener<String>() {
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

                    if(responsecode.equals("00")) {

                        existingReport = new ArrayList<>();

                        JSONArray jsonArray_status = new JSONArray(statusArray);

                        if (jsonArray_status.length() != 0) {

                            for (int i = 0; i < jsonArray_status.length(); i++) {

                                JSONObject jsonObject_status = jsonArray_status.getJSONObject(i);

                                String existinguserid = jsonObject_status.getString("existinguserid");
                                String productid = jsonObject_status.getString("productid");
                                String classofBarcode = jsonObject_status.getString("classofBarcode");
                                String vehicleType = jsonObject_status.getString("vehicleType");
                                String customername = jsonObject_status.getString("customername");
                                String mobileNumber = jsonObject_status.getString("mobileNumber");
                                String rcbook = jsonObject_status.getString("rcbook");
                                String vehicleNumbertype = jsonObject_status.getString("vehicleNumbertype");
                                String vehiclechasisnumber = jsonObject_status.getString("vehiclechasisnumber");
                                String salesagentId = jsonObject_status.getString("salesagentId");
                                String salesagentType = jsonObject_status.getString("salesagentType");
                                String customerid = jsonObject_status.getString("customerid");
                                String agenttype = jsonObject_status.getString("agenttype");
                                String barcodeid = jsonObject_status.getString("barcodeid");
                                String resultTag = jsonObject_status.getString("resultTag");
                                String statusTag = jsonObject_status.getString("statusTag");
                                String transactionstatus = jsonObject_status.getString("transactionstatus");
                                String transactionid = jsonObject_status.getString("transactionid");
                                String txnstatus = jsonObject_status.getString("txnstatus");
                                String datetime = jsonObject_status.getString("datetime");

                                String[] date_time = datetime.split(" ");

                                Log.d("datatimeclass", date_time.toString());

                                String date = date_time[0];
                                String time = date_time[1];

                                Report_ModelClass_Existing reportModelClassExisting = new Report_ModelClass_Existing(
                                        customername, customerid, mobileNumber, vehiclechasisnumber, barcodeid, "", "", date, time
                                );

                                existingReport.add(reportModelClassExisting);
                            }

                            recyclerReport.setVisibility(View.VISIBLE);

                            linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                            newReportAdapter = new NewReportAdapter(getActivity(),existingReport);
                            recyclerReport.setLayoutManager(linearLayoutManager);
                            recyclerReport.setHasFixedSize(true);
                            recyclerReport.setAdapter(newReportAdapter);

                        }else{

                            recyclerReport.setVisibility(View.GONE);

                            Toast.makeText(getActivity(), "No Data Avilable", Toast.LENGTH_SHORT).show();


                        }
                    }else{

                        Toast.makeText(getActivity(), "Invalide User Id", Toast.LENGTH_SHORT).show();
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

}

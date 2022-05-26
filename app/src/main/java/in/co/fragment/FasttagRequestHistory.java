package in.co.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.co.adapter.ClassTagSpinerAdapter;
import in.co.adapter.FastagHistoryAdapter;
import in.co.hitchpayments.R;
import in.co.modelclass.ClassoFtag_ModelClass;
import in.co.modelclass.FastagRequestHistory_Model;
import in.co.url.AppUrl;
import in.co.url.SessionManager;

public class FasttagRequestHistory extends Fragment {

    Spinner spinner_FastagClass;
    EditText edit_NumberOfFastag;
    RecyclerView requestFastTagRecycler;
    ArrayList<ClassoFtag_ModelClass> classFtag;
    String str_classFtag,agentId;
    SessionManager sessionManager;
    FastagHistoryAdapter fastagHistoryAdapter;
    ArrayList<FastagRequestHistory_Model> fastagHistory = new ArrayList<>();;
    LinearLayoutManager linearLayoutManager;
    MaterialButton btn_SendRequest;
    TextView name_text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_requestfastag,container,false);

        spinner_FastagClass = view.findViewById(R.id.spinner_FastagClass);
        edit_NumberOfFastag = view.findViewById(R.id.edit_NumberOfFastag);
        requestFastTagRecycler = view.findViewById(R.id.requestFastTagRecycler);
        btn_SendRequest = view.findViewById(R.id.btn_SendRequest);
        name_text = view.findViewById(R.id.name_text);

        sessionManager = new SessionManager(getActivity());
        agentId = sessionManager.getSalesAgentId();

        name_text.setText("Request FastTag");

        //Toast.makeText(getActivity(), agentId, Toast.LENGTH_LONG).show();

        spinner_FastagClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {

                    ClassoFtag_ModelClass cate_data = (ClassoFtag_ModelClass) parent.getSelectedItem();

                    str_classFtag = cate_data.getClassoFtag();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(getContext(), "Select", Toast.LENGTH_LONG).show();

            }
        });

        GetFastagClass(agentId);
        FastagRequestHistory(agentId);

        btn_SendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edit_NumberOfFastag.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Fill The Details", Toast.LENGTH_LONG).show();
                }else{

                    String noofFastTag = edit_NumberOfFastag.getText().toString().trim();

                    RequestFastag(agentId,noofFastTag,str_classFtag);
                }
            }
        });

        return view;
    }

    public void GetFastagClass(String salesAgentId){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Get Fastag Class Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.getfastagclass, new Response.Listener<String>() {
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

                        classFtag = new ArrayList<>();

                        JSONArray jsonArray_status = new JSONArray(statusArray);

                        for (int i=0;i<jsonArray_status.length();i++){

                            JSONObject jsonObject_status = jsonArray_status.getJSONObject(i);

                            String classoftag = jsonObject_status.getString("classoftag");

                            if(classoftag.equals("")){

                            }else{

                                ClassoFtag_ModelClass ftag_modelClass = new ClassoFtag_ModelClass(classoftag);
                                classFtag.add(ftag_modelClass);
                            }

                        }

                        ClassTagSpinerAdapter classTagSpinerAdapter = new ClassTagSpinerAdapter(getActivity(),R.layout.spinneritem,classFtag);
                        classTagSpinerAdapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
                        spinner_FastagClass.setAdapter(classTagSpinerAdapter);
                        spinner_FastagClass.setSelection(-1,true);

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

    public void FastagRequestHistory(String salesAgentId){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fastag Request History Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.fastagrequesthistory, new Response.Listener<String>() {
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

                        if(jsonArray_status.length() != 0){

                            for (int i=0;i<jsonArray_status.length();i++){

                                JSONObject jsonObject_Status = jsonArray_status.getJSONObject(i);

                                String fastagrequestid = jsonObject_Status.getString("fastagrequestid");
                                String numberoffastag = jsonObject_Status.getString("numberoffastag");
                                String classoftag = jsonObject_Status.getString("classoftag");
                                String requestedbytype = jsonObject_Status.getString("requestedbytype");
                                String requestedbyid = jsonObject_Status.getString("requestedbyid");
                                String status1 = jsonObject_Status.getString("status");
                                String datetime = jsonObject_Status.getString("datetime");

                                String[] date_time = datetime.split(" ");

                                String date = date_time[0];

                                FastagRequestHistory_Model fastagRequestHistory_model = new FastagRequestHistory_Model(
                                        fastagrequestid,numberoffastag,classoftag,requestedbytype,requestedbyid,status1,date
                                );

                                fastagHistory.add(fastagRequestHistory_model);

                                Log.d("fgbgasdga",fastagHistory.toString());
                            }

                            linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                            fastagHistoryAdapter = new FastagHistoryAdapter(getActivity(),fastagHistory);
                            requestFastTagRecycler.setLayoutManager(linearLayoutManager);
                            requestFastTagRecycler.setHasFixedSize(true);
                            requestFastTagRecycler.setAdapter(fastagHistoryAdapter);

                        }else{

                            Toast.makeText(getActivity(), "No Data Avilable", Toast.LENGTH_LONG).show();
                        }

                    }else{

                        Toast.makeText(getActivity(), "User Id is Invalide", Toast.LENGTH_LONG).show();
                    }

                }catch (JSONException e) {
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
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void RequestFastag(String salesAgentId,String nooftag,String classoftag){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Request Fastag Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.requestfastag, new Response.Listener<String>() {
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
                    String status1 = jsonObject_messages.getString("status");

                    if(responsecode.equals("00")){

                        Toast.makeText(getActivity(), status1, Toast.LENGTH_LONG).show();
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
                params.put("nooftag",nooftag);
                params.put("classoftag",classoftag);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}

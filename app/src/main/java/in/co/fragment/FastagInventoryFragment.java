package in.co.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.co.adapter.FastagInventoryAdapter;
import in.co.hitchpayments.R;
import in.co.modelclass.FastagInventory_ModelClass;
import in.co.url.AppUrl;
import in.co.url.SessionManager;

public class FastagInventoryFragment extends Fragment {

    RecyclerView recyclerFastagInventory;
    TextView name_text;
    LinearLayoutManager linearLayoutManager;
    FastagInventoryAdapter fastagInventoryAdapter;
    ArrayList<FastagInventory_ModelClass> fastagInventory;

    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fastaginventoory,container,false);

        name_text = view.findViewById(R.id.name_text);
        recyclerFastagInventory = view.findViewById(R.id.recyclerFastagInventory);

        name_text.setText("Fastag Inventory");

        sessionManager = new SessionManager(getContext());
        String agentId = sessionManager.getSalesAgentId();
        ViewInventory(agentId);

        return view;
    }

    public void ViewInventory(String salesAgentId){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Retrive Inventory Please Wait....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.viewinventory, new Response.Listener<String>() {
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

                    if(responsecode.equalsIgnoreCase("00")){

                        fastagInventory = new ArrayList<>();

                        JSONArray jsonArray_status = new JSONArray(statusArray);

                        if(jsonArray_status != null){

                            for (int i=0;i<jsonArray_status.length();i++){

                                JSONObject jsonObject_status = jsonArray_status.getJSONObject(i);

                                String fasttagid = jsonObject_status.getString("fasttagid");
                                String inventoryid = jsonObject_status.getString("inventoryid");
                                String allotedto = jsonObject_status.getString("allotedto");
                                String allotedtotype = jsonObject_status.getString("allotedtotype");
                                String status1 = jsonObject_status.getString("status");
                                String barcode = jsonObject_status.getString("barcode");
                                String tagid = jsonObject_status.getString("tagid");
                                String classoftag = jsonObject_status.getString("classoftag");

                                FastagInventory_ModelClass fastagInventory_modelClass = new FastagInventory_ModelClass(
                                        barcode,tagid,classoftag,status1
                                );

                                fastagInventory.add(fastagInventory_modelClass);

                            }

                            linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                            fastagInventoryAdapter = new FastagInventoryAdapter(fastagInventory,getActivity());
                            recyclerFastagInventory.setLayoutManager(linearLayoutManager);
                            recyclerFastagInventory.setHasFixedSize(true);
                            recyclerFastagInventory.setAdapter(fastagInventoryAdapter);

                        }else{

                            Toast.makeText(getActivity(), "No data In Inventory", Toast.LENGTH_LONG).show();
                        }
                    }else{

                        Toast.makeText(getActivity(), "Invalide User Id", Toast.LENGTH_LONG).show();
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

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}

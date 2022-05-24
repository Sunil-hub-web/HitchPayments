package in.co.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.co.hitchpayments.R;
import in.co.modelclass.Report_ModelClass_Existing;
import in.co.url.AppUrl;
import in.co.url.MyJsonArrayRequest;
import in.co.url.SessionManager;

public class ExistingReportAdapter extends RecyclerView.Adapter<ExistingReportAdapter.ViewHolder> {

    Context context;
    ArrayList<Report_ModelClass_Existing> existingReport;

    SessionManager sessionManager ;

    public ExistingReportAdapter(FragmentActivity activity, ArrayList<Report_ModelClass_Existing> existingReport) {

        this.context = activity;
        this.existingReport = existingReport;
    }

    @NonNull
    @Override
    public ExistingReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reportdetails_existcustomer,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExistingReportAdapter.ViewHolder holder, int position) {

        sessionManager = new SessionManager(context);

        Report_ModelClass_Existing report_exiting = existingReport.get(position);

        holder.text_CUSTOMERNAME.setText(report_exiting.getCUSTOMER_NAME());
        holder.text_CUSTOMERID.setText(report_exiting.getCUSTOMER_ID());
        holder.text_MOBILENUMBER.setText(report_exiting.getMOBILE_NUMBER());
        holder.text_CHASSISNUMBER.setText(report_exiting.getVEHICLECHASSISNUMBER());
        holder.text_BARCODE.setText(report_exiting.getBARCODE());
        holder.text_DATEACTIVATION.setText(report_exiting.getDATEACTIVATION());
        holder.text_TIMEACTIVATION.setText(report_exiting.getTIMEACTIVATION());

        holder.text_ViewBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("otp Send Please Wait...");
                progressDialog.show();

                JSONObject jsonObject = new JSONObject();

                try {

                    jsonObject.put("CPID", "504");
                    jsonObject.put("AGENTID", "230201");
                    jsonObject.put("MOBILENUMBER", report_exiting.getMOBILE_NUMBER());

                } catch (Exception e) {

                    e.printStackTrace();

                }

                MyJsonArrayRequest jsonArrayRequest = new MyJsonArrayRequest(Request.Method.POST, AppUrl.getBalance, jsonObject, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        progressDialog.dismiss();

                        try {

                            String responsearray = response.toString();

                            JSONArray jsonArray = new JSONArray(responsearray);

                            Log.d("hvsuv",jsonArray.toString());

                            JSONObject jsonObject_response = jsonArray.getJSONObject(0);

                            Log.d("hvsuvfggg",jsonObject_response.toString());

                            String responseCode = jsonObject_response.getString("RESPONSECODE");
                            String status = jsonObject_response.getString("STATUS");

                            if(responseCode.equals("00")){

                                JSONObject jsonObject_response1 = jsonArray.getJSONObject(1);

                                Log.d("hvsuvfg",jsonObject_response1.toString());

                                String BALANCEDETAILS = jsonObject_response1.getString("BALANCEDETAILS");

                                JSONArray jsonArray1 = new JSONArray(BALANCEDETAILS);

                                JSONObject jsonObject_response2 = jsonArray1.getJSONObject(0);

                                String MASTERBALANCE = jsonObject_response2.getString("MASTERBALANCE");
                                String TOTALSDBALANCE = jsonObject_response2.getString("TOTALSDBALANCE");
                                String NOOFTAGS = jsonObject_response2.getString("NOOFTAGS");
                                String MOBILENUMBER = jsonObject_response2.getString("MOBILENUMBER");
                                String TOTALWALLETBALANCE = jsonObject_response2.getString("TOTALWALLETBALANCE");

                                sessionManager.setTotalBalance(TOTALWALLETBALANCE);

                                holder.text_Balance.setText("Rs. " + TOTALWALLETBALANCE);
                                holder.lin_ViewBalance.setVisibility(View.VISIBLE);
                                holder.text_ViewBalance.setVisibility(View.GONE);

                            }else{

                                Toast.makeText(context, "no payment", Toast.LENGTH_SHORT).show();
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

                            Toast.makeText(context.getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_SHORT).show();

                        } else {

                            Log.d("responceVolley", "" + error);

                            Toast.makeText(context, "" + error, Toast.LENGTH_SHORT).show();
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
                jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(jsonArrayRequest);


            }
        });

    }

    @Override
    public int getItemCount() {
        return existingReport.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_CUSTOMERNAME,text_CUSTOMERID,text_MOBILENUMBER,text_CHASSISNUMBER,text_BARCODE,
                text_DATEACTIVATION,text_TIMEACTIVATION,text_Balance,text_ViewBalance;

        LinearLayout lin_ViewBalance;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_CUSTOMERNAME = itemView.findViewById(R.id.text_CUSTOMERNAME);
            text_CUSTOMERID = itemView.findViewById(R.id.text_CUSTOMERID);
            text_MOBILENUMBER = itemView.findViewById(R.id.text_MOBILENUMBER);
            text_CHASSISNUMBER = itemView.findViewById(R.id.text_CHASSISNUMBER);
            text_BARCODE = itemView.findViewById(R.id.text_BARCODE);
            text_DATEACTIVATION = itemView.findViewById(R.id.text_DATEACTIVATION);
            text_TIMEACTIVATION = itemView.findViewById(R.id.text_TIMEACTIVATION);
            text_Balance = itemView.findViewById(R.id.text_Balance);
            text_ViewBalance = itemView.findViewById(R.id.text_ViewBalance);
            lin_ViewBalance = itemView.findViewById(R.id.lin_ViewBalance);

        }
    }

    public void getBalance(String mobileNo){


    }
}

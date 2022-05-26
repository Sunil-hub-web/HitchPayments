package in.co.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cashfree.pg.CFPaymentService;
import com.cashfree.pg.api.CFPaymentGatewayService;
import com.cashfree.pg.core.api.CFSession;

import com.cashfree.pg.core.api.callback.CFCheckoutResponseCallback;
import com.cashfree.pg.core.api.exception.CFException;
import com.cashfree.pg.core.api.utils.CFErrorResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import static com.cashfree.pg.CFPaymentService.PARAM_APP_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.cashfree.pg.CFPaymentService.PARAM_NOTIFY_URL;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_CURRENCY;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_NOTE;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import in.co.adapter.WalletAdapter;
import in.co.hitchpayments.R;
import in.co.modelclass.Wallet_ModelClass;
import in.co.url.AppUrl;
import in.co.url.SessionManager;

public class WalletFragment extends Fragment {

    TextView name_text;
    RecyclerView viewWalletRecycler;
    LinearLayoutManager linearLayoutManager;
    WalletAdapter walletAdapter;
    ArrayList<Wallet_ModelClass> wallet = new ArrayList<>();
    SessionManager sessionManager;
    MaterialButton btn_MakePayment;
    EditText edit_Amount;

    public static final String TAG = "WalletFragment";

    String orderID = "ORDER_ID";
    String token = "TOKEN",amount,currentTime;
    CFSession.Environment cfEnvironment = CFSession.Environment.PRODUCTION;
    CFPaymentService cfPaymentService;
    ActivityResultLauncher<Intent> resultLauncher;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        // btn_viewDetails = view.findViewById(R.id.btn_viewDetails);

        name_text = view.findViewById(R.id.name_text);
        viewWalletRecycler = view.findViewById(R.id.viewWalletRecycler);
        btn_MakePayment = view.findViewById(R.id.btn_MakePayment);
        edit_Amount = view.findViewById(R.id.edit_Amount);

        name_text.setText("Wallet");

        sessionManager = new SessionManager(getContext());
        String adentId = sessionManager.getSalesAgentId();
        GetWalletBalance(adentId);

       /* btn_viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewWalletDetails viewWalletDetails = new ViewWalletDetails();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, viewWalletDetails);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });*/


        btn_MakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    if(edit_Amount.getText().toString().trim().equals("")){

                        Toast.makeText(getActivity(), "Fill The Amount", Toast.LENGTH_SHORT).show();
                    }else{

                        currentTime = new SimpleDateFormat("HHmmss", Locale.getDefault()).format(new Date());
                        String agentId = sessionManager.getSalesAgentId();
                        amount = edit_Amount.getText().toString().trim();
                        int int_amount = Integer.valueOf(amount);

                        initiatepayment(currentTime,int_amount);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                Intent intent = result.getData();

                Log.d("hsgfhjdhs",intent.toString());

                if (intent != null) {

                    try {
                        //Prints all extras. Replace with app logic.
                            if (intent != null) {
                                Bundle bundle = intent.getExtras();
                                if (bundle != null) {

                                    ShowResponse(transformBundleToString(bundle));

                                    String str_amount = String.valueOf(amount);
                                    String agentId = sessionManager.getSalesAgentId();
                                    AddwalletBalance(agentId,str_amount,currentTime,"2","SUCCESS");

                                }
                            }

                    } catch (Exception e) {

                        e.printStackTrace();

                        Toast.makeText(getContext(), "Not Found", Toast.LENGTH_SHORT).show();
                    }
                }else{

                    Toast.makeText(getContext(), "Data Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    public void GetWalletBalance(String salesAgentId) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Wallet Balance Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.getwalletbalance, new Response.Listener<String>() {
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

                        JSONArray jsonArray_status = new JSONArray(statusArray);

                        for (int i = 0; i < jsonArray_status.length(); i++) {

                            JSONObject jsonObject_status = jsonArray_status.getJSONObject(i);

                            String walletid = jsonObject_status.getString("walletid");
                            String payerid = jsonObject_status.getString("payerid");
                            String payertype = jsonObject_status.getString("payertype");
                            String amount = jsonObject_status.getString("amount");
                            String transactionid = jsonObject_status.getString("transactionid");
                            String transactiontype = jsonObject_status.getString("transactiontype");
                            String transactionstatus = jsonObject_status.getString("transactionstatus");
                            String txn = jsonObject_status.getString("txn");
                            String datetime = jsonObject_status.getString("datetime");

                            Wallet_ModelClass wallet_modelClass = new Wallet_ModelClass(
                                    walletid, payerid, payertype, amount, transactionid, transactiontype, transactionstatus, txn, datetime
                            );

                            wallet.add(wallet_modelClass);
                        }

                        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        linearLayoutManager.setReverseLayout(true);
                        linearLayoutManager.setStackFromEnd(true);
                        walletAdapter = new WalletAdapter(getActivity(), wallet);
                        viewWalletRecycler.setLayoutManager(linearLayoutManager);
                        viewWalletRecycler.setHasFixedSize(true);
                        viewWalletRecycler.setAdapter(walletAdapter);
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
                params.put("userid", salesAgentId);
                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }


    public void ShowResponse(String response) {

        new MaterialAlertDialogBuilder(getContext())
                .setMessage(response)
                .setTitle("Payment Response")
                .setPositiveButton("OK", (dialog, which) -> {

                    String agentId = sessionManager.getSalesAgentId();

                    //AddwalletBalance(agentId,str_amount,orderid,"2","SUCCESS");

                    dialog.dismiss();
                }).show();
    }

    public String transformBundleToString(Bundle bundle) {
        String response = "";
        for (String key : bundle.keySet()) {
            if (bundle.getString(key) != null) {
                Log.d(TAG, key + " : " + bundle.getString(key));

                response = response.concat(String.format("%s : %s\n", key, bundle.getString(key)));

            }
        }
        return response;
    }

    private void initiatepayment(String orderid,int amount) throws JSONException {

        String App_url = "https://api.cashfree.com/api/v2/cftoken/order";

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("orderId",orderid);
        jsonObject.put("orderAmount",amount);
        jsonObject.put("orderCurrency","INR");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, App_url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String status = response.getString("status");
                    String message = response.getString("message");
                    String cftoken = response.getString("cftoken");

                    String appid = "19350339ee25ac9bdf7c97d3f6305391";
                    String secretkey = "e6cbc3a48d1ddaa503572f42f1a29a427b159c27";
                    String str_amount = String.valueOf(amount);

                    if(message.equals("Token generated")){

                        Map<String, String> params = new HashMap<>();
                        params.put(PARAM_APP_ID, appid);
                        params.put(PARAM_ORDER_ID, orderid);
                        params.put(PARAM_ORDER_AMOUNT, str_amount);
                        params.put(PARAM_ORDER_NOTE, "Make Payment");
                        params.put(PARAM_CUSTOMER_NAME, "Rajeev");
                        params.put(PARAM_CUSTOMER_PHONE, "7853074379");
                        params.put(PARAM_CUSTOMER_EMAIL, "sunil95@Gmail.com");
                        params.put(PARAM_ORDER_CURRENCY, "INR");
                        params.put(PARAM_NOTIFY_URL, "https://example.com/path/to/notify/url/");

                        for (Map.Entry entry : params.entrySet()) {

                            Log.d("CFSKDSample", entry.getKey() + " " + entry.getValue());
                        }

                        cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
                        cfPaymentService.setOrientation(0);
                        cfPaymentService.doPayment(getActivity(), params, cftoken, "PROD", "#F8A31A", "#FFFFFF", false);

                        String str_amount1 = String.valueOf(amount);
                        String agentId = sessionManager.getSalesAgentId();
                        AddwalletBalance(agentId,str_amount1,currentTime,"2","SUCCESS");

                    }else{

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                    Toast.makeText(getContext().getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_SHORT).show();

                } else {

                    Log.d("successresponceVolley", "" + error.networkResponse.statusCode);
                    Log.d("successresponceVolley", "" + error.networkResponse);
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        try {
                            String jError = new String(networkResponse.data);
                            JSONObject jsonError = new JSONObject(jError);

                            String data = jsonError.getString("message");
                            Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();

                            //AddwalletBalance(agentId,str_amount,orderid,"1","Success");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("successresponceVolley", "" + e);
                        }

                    }
                }
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String,String> header = new HashMap<>();

                header.put("Content-Type","application/json");
                header.put("x-client-id","19350339ee25ac9bdf7c97d3f6305391");
                header.put("x-client-secret","e6cbc3a48d1ddaa503572f42f1a29a427b159c27");

                return header;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);


    }

    public void AddwalletBalance(String payerid, String amount, String transactionid,String transactionstatus,String txnstatus) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Wallet Update Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.addwalletbalance, new Response.Listener<String>() {
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

                params.put("payerid", payerid);
                params.put("amount", amount);
                params.put("transactionid", transactionid);
                params.put("transactionstatus", transactionstatus);
                params.put("txnstatus", txnstatus);

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}

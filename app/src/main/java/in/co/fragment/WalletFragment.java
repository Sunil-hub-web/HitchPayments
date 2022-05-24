package in.co.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.cashfree.pg.CFPaymentService;
import com.cashfree.pg.api.CFPaymentGatewayService;
import com.cashfree.pg.core.api.CFCorePaymentGatewayService;
import com.cashfree.pg.core.api.CFSession;
import com.cashfree.pg.core.api.CFTheme;
import com.cashfree.pg.core.api.callback.CFCheckoutResponseCallback;
import com.cashfree.pg.core.api.emicard.CFEMICard;
import com.cashfree.pg.core.api.emicard.CFEMICardPayment;
import com.cashfree.pg.core.api.exception.CFException;
import com.cashfree.pg.core.api.netbanking.CFNetBanking;
import com.cashfree.pg.core.api.netbanking.CFNetBankingPayment;
import com.cashfree.pg.core.api.upi.CFUPI;
import com.cashfree.pg.core.api.upi.CFUPIPayment;
import com.cashfree.pg.core.api.utils.CFErrorResponse;
import com.cashfree.pg.core.api.wallet.CFWallet;
import com.cashfree.pg.core.api.wallet.CFWalletPayment;
import com.cashfree.pg.ui.api.CFDropCheckoutPayment;
import com.cashfree.pg.ui.api.CFPaymentComponent;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.co.adapter.NewReportAdapter;
import in.co.adapter.WalletAdapter;
import in.co.hitchpayments.R;
import in.co.modelclass.Wallet_ModelClass;
import in.co.url.AppUrl;
import in.co.url.SessionManager;

public class WalletFragment extends Fragment implements CFCheckoutResponseCallback {

    TextView name_text;
    RecyclerView viewWalletRecycler;
    LinearLayoutManager linearLayoutManager;
    WalletAdapter walletAdapter;
    ArrayList<Wallet_ModelClass> wallet = new ArrayList<>();
    SessionManager sessionManager;
    MaterialButton btn_MakePayment;

    public static final String TAG = "WalletFragment";

    String orderID = "ORDER_ID";
    String token = "TOKEN";
    CFSession.Environment cfEnvironment = CFSession.Environment.PRODUCTION;


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

        try {
            CFPaymentGatewayService.getInstance().setCheckoutCallback(this);
        } catch (CFException e) {
            e.printStackTrace();
        }

        btn_MakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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

                    if(responsecode.equals("00")){

                        JSONArray jsonArray_status = new JSONArray(statusArray);

                        for(int i=0;i<jsonArray_status.length();i++){

                            JSONObject jsonObject_status = jsonArray_status.getJSONObject(i);

                            String walletid  = jsonObject_status.getString("walletid");
                            String payerid  = jsonObject_status.getString("payerid");
                            String payertype  = jsonObject_status.getString("payertype");
                            String amount  = jsonObject_status.getString("amount");
                            String transactionid  = jsonObject_status.getString("transactionid");
                            String transactiontype  = jsonObject_status.getString("transactiontype");
                            String transactionstatus  = jsonObject_status.getString("transactionstatus");
                            String txn  = jsonObject_status.getString("txn");
                            String datetime  = jsonObject_status.getString("datetime");

                            Wallet_ModelClass wallet_modelClass = new Wallet_ModelClass(
                                    walletid,payerid,payertype,amount,transactionid,transactiontype,transactionstatus,txn,datetime
                            );

                            wallet.add(wallet_modelClass);
                        }

                        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                        walletAdapter = new WalletAdapter(getActivity(),wallet);
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


    @Override
    public void  onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Same request code for all payment APIs.
        Log.d(TAG, "ReqCode : " + CFPaymentService.REQ_CODE);
        Log.d(TAG, "API Response : ");
        //Prints all extras. Replace with app logic.
        if (data != null) {
            Bundle  bundle = data.getExtras();
            if (bundle != null)
                for (String  key  :  bundle.keySet()) {
                    if (bundle.getString(key) != null) {
                        Log.d(TAG, key + " : " + bundle.getString(key));
                    }
                }
        }
    }

    @Override
    public void onPaymentVerify(String s) {

    }

    @Override
    public void onPaymentFailure(CFErrorResponse cfErrorResponse, String s) {

    }
}

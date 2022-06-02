package in.co.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import in.co.hitchpayments.LoginPage;
import in.co.hitchpayments.R;
import in.co.url.AppUrl;
import in.co.url.MyJsonArrayRequest;

public class NPCITagFragment extends Fragment {

    TextView name_text,text_TAGID,text_VehicleNumber,text_VehicleClass,text_TagStatus,text_IssueDate,text_ExcCode,
            text_BankId,text_CommercialVehicle,text_CCH,text_ActivationDate,text_TId;

    EditText edit_VehicleNo;
    Button btn_SearchStatues;
    String currentTime;
    CardView cardView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_npcitag,container,false);

        name_text = view.findViewById(R.id.name_text);
        edit_VehicleNo = view.findViewById(R.id.edit_VehicleNo);
        btn_SearchStatues = view.findViewById(R.id.btn_SearchStatues);
        text_TAGID = view.findViewById(R.id.text_TAGID);
        text_VehicleNumber = view.findViewById(R.id.text_VehicleNumber);
        text_VehicleClass = view.findViewById(R.id.text_VehicleClass);
        text_TagStatus = view.findViewById(R.id.text_TagStatus);
        text_IssueDate = view.findViewById(R.id.text_IssueDate);
        text_ExcCode = view.findViewById(R.id.text_ExcCode);
        text_BankId = view.findViewById(R.id.text_BankId);
        text_CommercialVehicle = view.findViewById(R.id.text_CommercialVehicle);
        text_CCH = view.findViewById(R.id.text_CCH);
        text_ActivationDate = view.findViewById(R.id.text_ActivationDate);
        text_TId = view.findViewById(R.id.text_TId);
        cardView = view.findViewById(R.id.cardView);
        name_text.setText("NPCI Tag");

        String currentTime = new SimpleDateFormat("HHmmss", Locale.getDefault()).format(new Date());

        btn_SearchStatues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edit_VehicleNo.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Fill the details", Toast.LENGTH_LONG).show();

                }else{

                    String VehicleNo = edit_VehicleNo.getText().toString().trim();

                    NPCITagstatus(VehicleNo,currentTime);

                }
            }
        });

        return view;

    }

    public void NPCITagstatus(String vehicleNo,String reqid){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Data Please Wait...");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("AGENTID", "230201");
            jsonObject.put("CPID", "504");
            jsonObject.put("INPUTTYPE", "REGNO");
            jsonObject.put("REQID", reqid);
            jsonObject.put("INPUT", vehicleNo);

        } catch (Exception e) {

            e.printStackTrace();

        }

        MyJsonArrayRequest jsonArrayRequest = new MyJsonArrayRequest(Request.Method.POST, AppUrl.nPCITagstatus, jsonObject, new Response.Listener<JSONArray>() {
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

                    if(responseCode.equals("00")){

                        cardView.setVisibility(View.VISIBLE);

                        Toast.makeText(getActivity(), status, Toast.LENGTH_LONG).show();

                        String ERRORCODE = jsonObject_response.getString("ERRORCODE");
                        String ERRORDESC = jsonObject_response.getString("ERRORDESC");
                        String REQID = jsonObject_response.getString("REQID");
                        String NPCIVehicleDetails = jsonObject_response.getString("NPCIVehicleDetails");

                        JSONArray jsonArray_NPCIVehicle = new JSONArray(NPCIVehicleDetails);

                        JSONObject jsonObject_NPCIVehicle = jsonArray_NPCIVehicle.getJSONObject(0);

                        String TAGSTATUS = jsonObject_NPCIVehicle.getString("TAGSTATUS");
                        String RESDTSTAMP = jsonObject_NPCIVehicle.getString("RESDTSTAMP");
                        String ISSUEDATE = jsonObject_NPCIVehicle.getString("ISSUEDATE");
                        String EXCCODE = jsonObject_NPCIVehicle.getString("EXCCODE");
                        String TAGID = jsonObject_NPCIVehicle.getString("TAGID");
                        String VEHICLECLASS = jsonObject_NPCIVehicle.getString("VEHICLECLASS");
                        String COMVEHICLE = jsonObject_NPCIVehicle.getString("COMVEHICLE");
                        String TID = jsonObject_NPCIVehicle.getString("TID");
                        String REGNUMBER = jsonObject_NPCIVehicle.getString("REGNUMBER");
                        String BANKID = jsonObject_NPCIVehicle.getString("BANKID");


                        text_TAGID.setText(TAGID);
                        text_VehicleNumber.setText(REGNUMBER);
                        text_VehicleClass.setText(VEHICLECLASS);
                        text_TagStatus.setText(TAGSTATUS);
                        text_IssueDate.setText(ISSUEDATE);
                        text_ExcCode.setText(EXCCODE);
                        text_BankId.setText(BANKID);
                        text_CommercialVehicle.setText(COMVEHICLE);
                        text_CCH.setText(VEHICLECLASS);
                        text_ActivationDate.setText(ISSUEDATE);
                        text_TId.setText(TID);


                    }else{

                        Toast.makeText(getActivity(), status, Toast.LENGTH_LONG).show();
                    }

                }catch(Exception e){

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
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }
}

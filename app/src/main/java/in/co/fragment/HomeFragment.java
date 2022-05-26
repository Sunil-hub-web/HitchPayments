package in.co.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.co.adapter.ImageSliderAdapter;
import in.co.adapter.TransactionAdapter;
import in.co.hitchpayments.DashBoard;
import in.co.hitchpayments.R;
import in.co.modelclass.Image_ModelClass;
import in.co.modelclass.Transaction_ModelClass;
import in.co.url.AppUrl;
import in.co.url.SessionManager;

public class HomeFragment extends Fragment {

    TextView name_text,text_LastDayActivation,text_TodayActivation;
    Button btn_TagActivation,btn_Report,btn_FastagInventory,btn_NPCITAG;
    ViewPager2 viewpager2ImageSlider;
    ArrayList<Image_ModelClass> imageSlider = new ArrayList<>();
    ImageSliderAdapter imageSliderAdapter;
    Handler sliderhandler = new Handler();
    RecyclerView allTransactionRecycler;
    TransactionAdapter transactionAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Transaction_ModelClass> transaction = new ArrayList<>();
    SessionManager sessionManager;
    ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);

        name_text = view.findViewById(R.id.name_text);
        viewpager2ImageSlider = view.findViewById(R.id.viewpager2ImageSlider);
        text_LastDayActivation = view.findViewById(R.id.text_LastDayActivation);
        text_TodayActivation = view.findViewById(R.id.text_TodayActivation);
        allTransactionRecycler = view.findViewById(R.id.allTransactionRecycler);

        name_text.setText("Home");

        sessionManager = new SessionManager(getActivity());
        String agentId = sessionManager.getSalesAgentId();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Activation Report Please Wait...");
        progressDialog.show();

        TodayActivationReport(agentId);
        YesterdayActivationReport(agentId);




        imageSlider.add(new Image_ModelClass(R.drawable.epay));
        imageSlider.add(new Image_ModelClass(R.drawable.epay2));
        imageSlider.add(new Image_ModelClass(R.drawable.epay));
        imageSlider.add(new Image_ModelClass(R.drawable.epay2));

        imageSliderAdapter = new ImageSliderAdapter(getContext(),imageSlider,viewpager2ImageSlider);
        viewpager2ImageSlider.setAdapter(imageSliderAdapter);

        viewpager2ImageSlider.setClipToPadding(false);
        viewpager2ImageSlider.setClipChildren(false);
        viewpager2ImageSlider.setOffscreenPageLimit(3);
        viewpager2ImageSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull  View page, float position) {

                float r = 1 - Math.abs(position);
                page.setScaleY(1.0f +  r * 0.90f);

            }
        });

        viewpager2ImageSlider.setPageTransformer(compositePageTransformer);

        Runnable sliderRunable = new Runnable() {
            @Override
            public void run() {

                viewpager2ImageSlider.setCurrentItem(viewpager2ImageSlider.getCurrentItem() + 1);
            }
        };

        viewpager2ImageSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                //super.onPageSelected(position);

               /* if (currentPossition >= arraysize)
                    currentPossition = 0;
                selectedIndicatorPosition (currentPossition++);*/

                sliderhandler.removeCallbacks(sliderRunable);
                sliderhandler.postDelayed(sliderRunable,2000);

            }
        });

        transaction.add(new Transaction_ModelClass("25.4.2022 6:15","1234567891","1722700","500","Success"));
        transaction.add(new Transaction_ModelClass("25.4.2022 6:15","7008324941","1909005","700","Success"));
        transaction.add(new Transaction_ModelClass("25.4.2022 6:15","9777022225","1909005","1000","Success"));
        transaction.add(new Transaction_ModelClass("25.4.2022 6:15","1234567894","1722755","1200","Success"));
        transaction.add(new Transaction_ModelClass("25.4.2022 6:15","1234567895","1722708","1500","Success"));

        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        transactionAdapter = new TransactionAdapter(transaction,getActivity());
        allTransactionRecycler.setLayoutManager(linearLayoutManager);
        allTransactionRecycler.setHasFixedSize(true);
        allTransactionRecycler.setAdapter(transactionAdapter);

        return view;
    }

    public void TodayActivationReport(String salesAgentId){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.todayactivationreport, new Response.Listener<String>() {
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

                    if(responsecode.equals("00")){

                        String count = jsonObject_messages.getString("count");
                        text_TodayActivation.setText(count);

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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void YesterdayActivationReport(String salesAgentId){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.yesterdayactivationreport, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    String error = jsonObject.getString("error");
                    String messages = jsonObject.getString("messages");

                    JSONObject jsonObject_messages = new JSONObject(messages);
                    String responsecode = jsonObject_messages.getString("responsecode");

                    if(responsecode.equals("00")){

                        String count = jsonObject_messages.getString("count");
                        text_LastDayActivation.setText(count);

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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}

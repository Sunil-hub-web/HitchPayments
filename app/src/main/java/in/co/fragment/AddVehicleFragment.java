package in.co.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
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

public class AddVehicleFragment extends Fragment {

    Spinner spinner_SelectProduct, spinner_VehicleClass, spinner_VehicleType, spinner_VehicleDataType;
    AutoCompleteTextView autotext_SelectBarcode;

    String[] vehicleClass = {"-Select Vehicle Class-", "VC4", "VC20"};
    HashMap<String, String> vehicleClass1 = new HashMap<String, String>();

    String[] VehicleDataType = {"-Select Vehicle Data Type-", "Vehicle Number", "Chassis Numner"};
    HashMap<String, String> VehicleDataType1 = new HashMap<String, String>();

    String[] vc4VehicleType = {"-Select Vehicle Type-", "Commercial Type", "Non-Commercial Type"};
    HashMap<String, String> vc4VehicleType1 = new HashMap<String, String>();

    String[] vc20VehicleType = {"-Select Vehicle Type-", "Commercial"};
    HashMap<String, String> vc20VehicleType1 = new HashMap<String, String>();

    String clssBar, agentId, str_classbar, vechtype, vctype, str_vechtype, str_vctype;

    ArrayList<ClassoFtag_ModelClass> barCode = new ArrayList<>();
    ArrayList<String> str_barCode = new ArrayList<>();
    ArrayList<ClassoFtag_ModelClass> product = new ArrayList<>();

    SessionManager sessionManager;
    SharedPreferenceManager sharedPreferenceManager;

    Button btn_UploadImage,btn_MakePayment;

    public static final int IMAGE_CODE = 1;
    Uri imageUri, selectedImageUri;
    Bitmap bitmap;
    File f;
    String ImageDecode;
    ImageView edit_ShowImagePath;
    EditText edit_CustomerId,edit_CustomerName,edit_ContactNumber,edit_EmailId,edit_FastagClass,
            edit_VehicleNumber;
    String str_CustomerId,str_CustomerName,str_ContactNumber,str_EmailId,str_FastagClass,str_VehicleNumber,
            str_SelectBarcode,str_SelectProduct,parameters,profile_photo,transctionId,productId,productId1,productPrice,productid;

    ActivityResultLauncher<Intent> resultLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_addvehicle, container, false);

        spinner_VehicleType = view.findViewById(R.id.spinner_VehicleType);
        autotext_SelectBarcode = view.findViewById(R.id.autotext_SelectBarcode);
        spinner_SelectProduct = view.findViewById(R.id.spinner_SelectProduct);
        spinner_VehicleDataType = view.findViewById(R.id.spinner_VehicleDataType);
        spinner_VehicleClass = view.findViewById(R.id.spinner_VehicleClass);
        spinner_VehicleType = view.findViewById(R.id.spinner_VehicleType);
        btn_MakePayment = view.findViewById(R.id.btn_MakePayment);
        btn_UploadImage = view.findViewById(R.id.btn_UploadImage);
        edit_ShowImagePath = view.findViewById(R.id.edit_ShowImagePath);
        edit_CustomerId = view.findViewById(R.id.edit_CustomerId);
        edit_CustomerName = view.findViewById(R.id.edit_CustomerName);
        edit_ContactNumber = view.findViewById(R.id.edit_ContactNumber);
        //edit_EmailId = view.findViewById(R.id.edit_EmailId);
        edit_FastagClass = view.findViewById(R.id.edit_FastagClass);
        edit_VehicleNumber = view.findViewById(R.id.edit_VehicleNumber);
        //edit_ShowImagePath = view.findViewById(R.id.edit_ShowImagePath);

        sessionManager = new SessionManager(getContext());
        sharedPreferenceManager = new SharedPreferenceManager(getContext());

        vehicleClass1.put("VC4", "4");
        vehicleClass1.put("VC20", "20");

        VehicleDataType1.put("Vehicle Number", "1");
        VehicleDataType1.put("Chassis Numner", "2");

        vc4VehicleType1.put("Commercial Type", "1");
        vc4VehicleType1.put("Non-Commercial Type", "2");

        vc20VehicleType1.put("Commercial", "1");

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

                if (vechtype.equalsIgnoreCase("Commercial Type")) {

                    str_vechtype = vc4VehicleType1.get("Commercial Type");
                    Toast.makeText(getActivity(), str_vechtype, Toast.LENGTH_SHORT).show();

                } else if (vechtype.equalsIgnoreCase("Non-Commercial Type")) {

                    str_vechtype = vc4VehicleType1.get("Non-Commercial Type");
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

        spinner_SelectProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                productId = spinner_SelectProduct.getSelectedItem().toString();

                ClassoFtag_ModelClass mystate = (ClassoFtag_ModelClass) parent.getSelectedItem();

                productId1 = mystate.getClassoFtag();
                productPrice = mystate.getFastagprice();
                productid = mystate.getProductid();

                Log.d("cate_Name", productId1 + "  " + productPrice);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_MakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edit_CustomerId.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), " Fill The CustomerId Details", Toast.LENGTH_SHORT).show();

                }else if(edit_CustomerName.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Fill The CustomerName Details", Toast.LENGTH_SHORT).show();

                }else if(edit_ContactNumber.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Fill The ContactNumber Details", Toast.LENGTH_SHORT).show();

                }else if(edit_VehicleNumber.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Fill The VehicleNumber Details", Toast.LENGTH_SHORT).show();

                }else{

                    int min = 10;
                    int max = 10000;
                    int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
                    String currentTime = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault()).format(new Date());
                    String uniqueId = random_int + currentTime;

                    transctionId = uniqueId;



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

                    Log.d("sunilagenttype",tagid+" "+vechtype+" "+vctype+" "+str_VehicleNumber+" "+str_SelectBarcode+" "+transctionId+" "+clssBar+" "+customerid);

                    AgentTypeCustomerType(tagid,vechtype,str_vctype,str_VehicleNumber,str_SelectBarcode,transctionId,clssBar,productid);

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

                      /*  bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), intent.getData());
                        edit_ShowImagePath.setImageBitmap(bitmap);*/

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), intent.getData());
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap = Bitmap.createScaledBitmap(bitmap, 500, 750, true);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 80, baos); //bm is the bitmap object
                        byte[] img = baos.toByteArray();
                        profile_photo = Base64.encodeToString(img, Base64.DEFAULT);
                        edit_ShowImagePath.setImageBitmap(bitmap);



                    } catch (Exception e) {

                        e.printStackTrace();

                        Toast.makeText(getContext(), "Not Found", Toast.LENGTH_SHORT).show();
                    }
                }else{

                    Toast.makeText(getContext(), "Data Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        });


        agentId = sessionManager.getSalesAgentId();

        //GetBarcode(agentId);
        Getproduct(agentId);

        try {

            String mobileNo = sharedPreferenceManager.getMobileNo();
            long lon_MobileNO = Long.valueOf(mobileNo);
            VerifyCustomer("230201","504",lon_MobileNO);

        } catch (JSONException e) {
            e.printStackTrace();
        }

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

    public void GetBarcode(String salesAgentId) {

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

                    if (responsecode.equals("00")) {

                        JSONArray jsonArray_status = new JSONArray(statusArray);

                        for (int i = 0; i < jsonArray_status.length(); i++) {

                            JSONObject jsonObject_status = jsonArray_status.getJSONObject(i);

                            String barcode = jsonObject_status.getString("barcode");

                            if (barcode.equals("")) {

                            } else {

                                /*ClassoFtag_ModelClass ftag_modelClass = new ClassoFtag_ModelClass(barcode);
                                barCode.add(ftag_modelClass);

                                str_barCode.add(barcode);*/
                            }

                        }

                        /*ClassTagSpinerAdapter classTagSpinerAdapter = new ClassTagSpinerAdapter(getActivity(), R.layout.spinneritem, barCode);
                        classTagSpinerAdapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
                        autotext_SelectBarcode.setAdapter(classTagSpinerAdapter);
                        autotext_SelectBarcode.setThreshold(1);*/

                        ArrayAdapter<String> autoTextBarCode = new ArrayAdapter<String>(getActivity(), R.layout.spinneritem, str_barCode);
                        autotext_SelectBarcode.setAdapter(autoTextBarCode);
                        autotext_SelectBarcode.setThreshold(1);//will start working from first character
                        autotext_SelectBarcode.setTextColor(ContextCompat.getColor(getActivity(), R.color.danger));
                    }

                } catch (Exception e) {

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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void Getproduct(String salesAgentId) {

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

                    if (responsecode.equals("00")) {

                        JSONArray jsonArray_status = new JSONArray(statusArray);

                        for (int i = 0; i < jsonArray_status.length(); i++) {

                            JSONObject jsonObject_status = jsonArray_status.getJSONObject(i);

                            String prodctCode = jsonObject_status.getString("prodctCode");
                            String productid = jsonObject_status.getString("productid");
                            String userId = jsonObject_status.getString("userId");
                            String fastagClass = jsonObject_status.getString("fastagClass");
                            String fastagprice = jsonObject_status.getString("fastagprice");
                            String initialPayment = jsonObject_status.getString("initialPayment");
                            //String prodctCode = jsonObject_status.getString("prodctCode");

                            if (prodctCode.equals("")) {

                            } else {

                                ClassoFtag_ModelClass ftag_modelClass = new ClassoFtag_ModelClass(prodctCode,fastagprice,productid);
                                product.add(ftag_modelClass);
                            }

                        }

                        ClassTagSpinerAdapter classTagSpinerAdapter = new ClassTagSpinerAdapter(getActivity(), R.layout.spinneritem, product);
                        classTagSpinerAdapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
                        spinner_SelectProduct.setAdapter(classTagSpinerAdapter);
                        spinner_SelectProduct.setSelection(-1, true);
                    }

                } catch (Exception e) {

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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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

    public void AgentTypeCustomerType(String tagactivationid, String vehicletype,String vehiclenumbertype,String vehiclechasisnumber,
                                      String barcodeid,String transactionid,String classofBarcode,String productid) {

        Log.d("sunilaProductdetails_below_method",tagactivationid+" "+vehicletype+" "+vehiclenumbertype+" "+vehiclechasisnumber+" "+barcodeid+" "+transactionid+" "+classofBarcode+" "+productid);


        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Data Upload Please wait...");
        progressDialog.show();


        StringRequest volleyMultipartRequest = new StringRequest(Request.Method.POST, AppUrl.addcontactnumber, new Response.Listener<String>() {
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

                       // String imgdata = jsonObject_messages.getString("imgdata");

                        String mobile = sharedPreferenceManager.getMobileNo();
                        String CustomerID = sharedPreferenceManager.getCUSTOMERID();
                        String tagid = sharedPreferenceManager.getStatusArray();
                        String customerid = sharedPreferenceManager.getCUSTOMERID();
                        String agentType = sharedPreferenceManager.getAGENTTYPE();

                        str_CustomerId = edit_CustomerId.getText().toString().trim();
                        str_CustomerName = edit_CustomerName.getText().toString().trim();
                        str_ContactNumber = edit_ContactNumber.getText().toString().trim();

                        //sharedPreferenceManager.setStatusArray(tagid);

                        String imgdata = "data:image/png;base64,"+profile_photo;


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
                        Log.d("suniladdvech",transctionId+" "+str_ContactNumber+" "+str_CustomerId);
                        //Toast.makeText(getActivity(), jsonarray.toString(), Toast.LENGTH_LONG).show();

                        addVehicle(transctionId,str_ContactNumber,str_CustomerId,jsonarray);


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
                params.put("rcbook", "");
                params.put("vehiclenumbertype", vehiclenumbertype);
                params.put("vehiclechasisnumber", vehiclechasisnumber);
                params.put("barcodeid", barcodeid);
                params.put("transactionid", transactionid);
                params.put("classofBarcode", classofBarcode);
                params.put("productid", productid);

                Log.d("sunilaProductdetails",tagactivationid+" "+vehicletype+" "+vehiclenumbertype+" "+vehiclechasisnumber+" "+barcodeid+" "+transactionid+" "+classofBarcode+" "+productid);

                return params;

            }

           /* @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                long imagename = System.currentTimeMillis();
                params.put("rcbook", new DataPart(imagename + ".png", getFileDataFromDrawable(rcbook)));

                return params;
            }*/
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

            Log.d("Ranjeeetarray",jsonArray.toString());
            Log.d("suniladd",TRANSACTIONID+" "+MOBILENO+" "+CUSTOMERID);

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

                        int min = 10;
                        int max = 10000;
                        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
                        String currentTime = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault()).format(new Date());
                        String uniqueId = random_int + currentTime;

                        if(RESULT.equals("230201")){


                            Toast.makeText(getActivity(), status, Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(), RESULT, Toast.LENGTH_LONG).show();

                            String statues = sharedPreferenceManager.getStatusArray();
                            String agentid = sessionManager.getSalesAgentId();
                            str_SelectBarcode = autotext_SelectBarcode.getText().toString().trim();

                            String tagid = sharedPreferenceManager.getStatusArray();

                            getStatues("0","Success",RESULT,status,tagid);

                            Log.d("sunilstatuies",RESULT+" "+status+" "+tagid);

                            //Updatetokenandcnr(statues,str_SelectBarcode,agentid,"200.00","503");

                        }else{

                            Toast.makeText(getActivity(), RESULT, Toast.LENGTH_LONG).show();
                            String tagid = sharedPreferenceManager.getStatusArray();

                            getStatues("0","Failed",RESULT,status,tagid);
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

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        HomeFragment homeFragment = new HomeFragment();
                        ft.replace(R.id.nav_host_fragment, homeFragment);
                        ft.addToBackStack(null);
                        ft.commit();

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

                Log.d("sunilupdatetoken",tagactivationid+" "+barcodeid+" "+userid+" "+fastagprice+" "+transactionid);


                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
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
                        //cardView12.setVisibility(View.VISIBLE);

                        String CUSTOMERID = jsonObject_response.getString("CUSTOMERID");
                        String MOBILENUMBER = jsonObject_response.getString("MOBILENUMBER");
                        String CUSTOMERNAME = jsonObject_response.getString("CUSTOMERNAME");
                        String AGENTTYPE = jsonObject_response.getString("AGENTTYPE");
                        String EMAILID = jsonObject_response.getString("EMAILID");

                        sharedPreferenceManager.setAGENTTYPE(AGENTTYPE);
                        sharedPreferenceManager.setMobileNo(MOBILENUMBER);
                        sharedPreferenceManager.setCUSTOMERNAME(CUSTOMERNAME);
                        sharedPreferenceManager.setCUSTOMERID(CUSTOMERID);

                        edit_CustomerId.setText(CUSTOMERID);
                        edit_CustomerName.setText(CUSTOMERNAME);
                        edit_ContactNumber.setText(MOBILENUMBER);
                       // edit_EmailId.setText(EMAILID);

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

    public void getStatues(String transactionstatus,String txnstatus,String responsecode,String responsestatus,String tagactivationid){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Get Barcode Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.addcontactnumber, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    String error = jsonObject.getString("error");
                    String messages = jsonObject.getString("messages");

                    if(status.equals("200")){

                        Toast.makeText(getActivity(), "data updated", Toast.LENGTH_SHORT).show();

                        String statues = sharedPreferenceManager.getStatusArray();
                        String agentid = sessionManager.getSalesAgentId();
                        str_SelectBarcode = autotext_SelectBarcode.getText().toString().trim();

                        Log.d("suniltoken",statues+" "+str_SelectBarcode+" "+agentid+" "+"200.00"+" "+transctionId);

                        Updatetokenandcnr(statues,str_SelectBarcode,agentid,productPrice,transctionId);

                    }else {

                        Toast.makeText(getActivity(), "data updated", Toast.LENGTH_SHORT).show();
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

                params.put("transactionstatus",transactionstatus);
                params.put("txnstatus",txnstatus);
                params.put("responsecode",responsecode);
                params.put("responsestatus",responsestatus);
                params.put("tagactivationid",tagactivationid);

                Log.d("sunilstatus",transactionstatus+" "+txnstatus+" "+responsecode+" "+responsestatus+" "+tagactivationid);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }



}

package in.co.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;

import in.co.hitchpayments.R;

public class TagActivationFragment extends Fragment {

    RadioGroup radioGroup;
    RadioButton text_NewCustomer,text_ExistingCustomer,genderradioButton;
    LinearLayout lin_SearchStatus,lin_Sendotp,lin_searchDetails;
    EditText edit_MobileNo,edit_MobileNo1;
    Button btn_SearchStatues,btn_Sendotp;
    TextView name_text;
    CardView cardView12;
    TextInputLayout editMobileNo1,editOTP;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tagactivation,container,false);

        name_text = view.findViewById(R.id.name_text);
        radioGroup = view.findViewById(R.id.radioGroup);
        text_NewCustomer = view.findViewById(R.id.text_NewCustomer);
        text_ExistingCustomer = view.findViewById(R.id.text_ExistingCustomer);
        lin_SearchStatus = view.findViewById(R.id.lin_SearchStatus);
        lin_Sendotp = view.findViewById(R.id.lin_Sendotp);
        lin_searchDetails = view.findViewById(R.id.lin_searchDetails);
        edit_MobileNo = view.findViewById(R.id.edit_MobileNo);
        edit_MobileNo1 = view.findViewById(R.id.edit_MobileNo1);
        btn_SearchStatues = view.findViewById(R.id.btn_SearchStatues);
        btn_Sendotp = view.findViewById(R.id.btn_Sendotp);
        cardView12 = view.findViewById(R.id.cardView12);
        editMobileNo1 = view.findViewById(R.id.editMobileNo1);
        editOTP = view.findViewById(R.id.editOTP);

        editOTP.setVisibility(View.GONE);

        name_text.setText("Tag Activation");


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

                        lin_Sendotp.setVisibility(View.VISIBLE);
                        lin_SearchStatus.setVisibility(View.GONE);
                        lin_searchDetails.setVisibility(View.GONE);
                        cardView12.setVisibility(View.GONE);

                    }else if(str_radioButton.equals("Existing Customer")){

                        lin_SearchStatus.setVisibility(View.VISIBLE);
                        lin_Sendotp.setVisibility(View.GONE);
                        lin_searchDetails.setVisibility(View.GONE);
                        cardView12.setVisibility(View.GONE);

                    }

                }
            }
        });

        btn_Sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btn_Sendotp.getText().toString().trim().equals("Send Otp")){

                    editOTP.setVisibility(View.VISIBLE);
                    editMobileNo1.setVisibility(View.GONE);
                    btn_Sendotp.setText("Verifay Otp");

                }else{

                    editOTP.setVisibility(View.VISIBLE);
                    editMobileNo1.setVisibility(View.GONE);
                    btn_Sendotp.setText("Verifay Otp");

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    CustomerDeatilsFragment customerDeatilsFragment = new CustomerDeatilsFragment();
                    ft.replace(R.id.nav_host_fragment, customerDeatilsFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });

        btn_SearchStatues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lin_searchDetails.setVisibility(View.VISIBLE);
                cardView12.setVisibility(View.VISIBLE);
            }
        });


        return view;
    }
}

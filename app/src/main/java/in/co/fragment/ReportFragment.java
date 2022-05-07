package in.co.fragment;

import android.os.Bundle;
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

import in.co.hitchpayments.R;

public class ReportFragment extends Fragment {

    RadioGroup radioGroup;
    RadioButton text_NewCustomer,text_ExistingCustomer,genderradioButton;
    RecyclerView recyclerReport;
    LinearLayoutManager linearLayoutManager;
    TextView name_text;

    CardView lin_ExistingCustomer,lin_NewCustomer;


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
        lin_ExistingCustomer = view.findViewById(R.id.lin_ExistingCustomer);
        lin_NewCustomer = view.findViewById(R.id.lin_NewCustomer);
        name_text = view.findViewById(R.id.name_text);

        name_text.setText("Report");

        lin_NewCustomer.setVisibility(View.GONE);
        lin_ExistingCustomer.setVisibility(View.GONE);

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

                        lin_NewCustomer.setVisibility(View.VISIBLE);
                        lin_ExistingCustomer.setVisibility(View.GONE);


                    }else if(str_radioButton.equals("Existing Customer")){

                        lin_NewCustomer.setVisibility(View.GONE);
                        lin_ExistingCustomer.setVisibility(View.VISIBLE);


                    }

                }
            }
        });

        return view;
    }
}

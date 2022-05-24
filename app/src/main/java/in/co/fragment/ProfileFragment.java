package in.co.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import in.co.hitchpayments.R;
import in.co.url.SessionManager;

public class ProfileFragment extends Fragment {

    TextView name_text;
    EditText text_RegistrationNumber,text_UserName,text_PrimaryContact,text_SecondaryContact,text_AadharNumber,
            text_PANCardNumber,text_DrivingLicence,text_KYCStatus,text_BankName,text_IFSCCode,text_AccountNumber,
            text_BankVerificationStatus,text_FullName,text_RelationshipNominee,text_ContactNumber;

    SessionManager sessionManager;

    CircleImageView profile_image;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        name_text = view.findViewById(R.id.name_text);
        name_text.setText("Profile");

        sessionManager = new SessionManager(getContext());

        text_RegistrationNumber = view.findViewById(R.id.text_RegistrationNumber);
        text_UserName = view.findViewById(R.id.text_UserName);
        text_PrimaryContact = view.findViewById(R.id.text_PrimaryContact);
        text_SecondaryContact = view.findViewById(R.id.text_SecondaryContact);
        text_AadharNumber = view.findViewById(R.id.text_AadharNumber);
        text_PANCardNumber = view.findViewById(R.id.text_PANCardNumber);
        text_DrivingLicence = view.findViewById(R.id.text_DrivingLicence);
        text_KYCStatus = view.findViewById(R.id.text_KYCStatus);
        text_BankName = view.findViewById(R.id.text_BankName);
        text_IFSCCode = view.findViewById(R.id.text_IFSCCode);
        text_AccountNumber = view.findViewById(R.id.text_AccountNumber);
        text_BankVerificationStatus = view.findViewById(R.id.text_BankVerificationStatus);
        text_FullName = view.findViewById(R.id.text_FullName);
        text_RelationshipNominee = view.findViewById(R.id.text_RelationshipNominee);
        text_ContactNumber = view.findViewById(R.id.text_ContactNumber);
        profile_image = view.findViewById(R.id.profile_image);
        text_RegistrationNumber.setText(sessionManager.getRegistrationNumber());
        text_UserName.setText(sessionManager.getUserName());
        text_PrimaryContact.setText(sessionManager.getPrimaryContact());
        text_SecondaryContact.setText(sessionManager.getSecondaryContact());
        text_AadharNumber.setText(sessionManager.getAadharNumber());
        text_PANCardNumber.setText(sessionManager.getPANCardNumber());
        text_DrivingLicence.setText(sessionManager.getDrivingLicence());
        text_BankName.setText(sessionManager.getBankName());
        text_IFSCCode.setText(sessionManager.getIFSCCode());
        text_AccountNumber.setText(sessionManager.getAccountNumber());
        text_FullName.setText(sessionManager.getNomineeName());
        text_RelationshipNominee.setText(sessionManager.getRelationshipWith());
        text_ContactNumber.setText(sessionManager.getContactNumber());

        if(sessionManager.getKYCStatus().equals("0")){

            text_KYCStatus.setText("Pending ");

        }else{

            text_KYCStatus.setText("Verified");
        }

        if(sessionManager.getBankVerificationStatus().equals("0")){

            text_BankVerificationStatus.setText("Pending ");

        }else{

            text_BankVerificationStatus.setText("Verified");
        }

        String url = "https://kottakotabusinesses.com/public/adminasset/img/salesagent/profileimage/"+sessionManager.getProfile();

        Picasso.get().load(url).into(profile_image);
        return view;
    }
}

package in.co.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import in.co.hitchpayments.R;

public class CustomerDeatilsFragment extends Fragment {

    String[] addressProofName = {"-AddressProofName-", "Driving License", "Adhaar(UID)", "Passport","voter id"};
    Spinner AddressProofName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customerdetails,container,false);

        AddressProofName = view.findViewById(R.id.AddressProofName);

        ArrayAdapter proofName = new ArrayAdapter(getActivity(), R.layout.spinneritem, addressProofName);
        proofName.setDropDownViewResource(R.layout.spinnerdropdownitem);
        AddressProofName.setAdapter(proofName);
        AddressProofName.setSelection(-1, true);

        return view;
    }
}

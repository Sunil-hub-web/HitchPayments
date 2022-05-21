package in.co.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import in.co.hitchpayments.R;

public class WalletFragment extends Fragment {

    TextView name_text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wallet,container,false);

       // btn_viewDetails = view.findViewById(R.id.btn_viewDetails);

        name_text = view.findViewById(R.id.name_text);

        name_text.setText("Wallet");

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

        return view;
    }
}

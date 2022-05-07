package in.co.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import in.co.hitchpayments.R;

public class NPCITagFragment extends Fragment {

    TextView name_text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_npcitag,container,false);

        name_text = view.findViewById(R.id.name_text);
        name_text.setText("NPCI Tag");
        return view;
    }
}

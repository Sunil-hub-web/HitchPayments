package in.co.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.co.adapter.FastagInventoryAdapter;
import in.co.hitchpayments.R;
import in.co.modelclass.FastagInventory_ModelClass;

public class FastagInventoryFragment extends Fragment {

    RecyclerView recyclerFastagInventory;
    TextView name_text;
    LinearLayoutManager linearLayoutManager;
    FastagInventoryAdapter fastagInventoryAdapter;
    ArrayList<FastagInventory_ModelClass> fastagInventory = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fastaginventoory,container,false);

        name_text = view.findViewById(R.id.name_text);
        recyclerFastagInventory = view.findViewById(R.id.recyclerFastagInventory);

        name_text.setText("Fastag Inventory");

        fastagInventory.add(new FastagInventory_ModelClass("1722700","E28011052000714D08D","4","Allocated"));
        fastagInventory.add(new FastagInventory_ModelClass("1722700","E28011052000714D08D","4","Allocated"));
        fastagInventory.add(new FastagInventory_ModelClass("1722700","E28011052000714D08D","4","Allocated"));

        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        fastagInventoryAdapter = new FastagInventoryAdapter(fastagInventory,getActivity());
        recyclerFastagInventory.setLayoutManager(linearLayoutManager);
        recyclerFastagInventory.setHasFixedSize(true);
        recyclerFastagInventory.setAdapter(fastagInventoryAdapter);



        return view;
    }
}

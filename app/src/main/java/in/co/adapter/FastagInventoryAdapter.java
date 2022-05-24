package in.co.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.co.hitchpayments.R;
import in.co.modelclass.FastagInventory_ModelClass;

public class FastagInventoryAdapter extends RecyclerView.Adapter<FastagInventoryAdapter.ViewHolder> {

    Context context;
    ArrayList<FastagInventory_ModelClass> fastagInventory;

    public FastagInventoryAdapter(ArrayList<FastagInventory_ModelClass> fastagInventory, FragmentActivity activity) {

        this.context = activity;
        this.fastagInventory = fastagInventory;
    }

    @NonNull
    @Override
    public FastagInventoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fastaginventoory,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FastagInventoryAdapter.ViewHolder holder, int position) {

        FastagInventory_ModelClass fastag = fastagInventory.get(position);

        holder.text_CLASSOFTAG.setText(fastag.getCLASS_OF_TAG());
        holder.text_BARCODE.setText(fastag.getBAR_CODE());
        holder.text_TAGID.setText(fastag.getTAG_ID());

        String status = fastag.getSTATUS();

        if(status.equals("1")){

            holder.text_STATUS.setText("Allocated");

        }else if(status.equals("0")){

            holder.text_STATUS.setText("Active");
        }

    }

    @Override
    public int getItemCount() {
        return fastagInventory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_BARCODE,text_TAGID,text_CLASSOFTAG,text_STATUS;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_STATUS = itemView.findViewById(R.id.text_STATUS);
            text_BARCODE = itemView.findViewById(R.id.text_BARCODE);
            text_TAGID = itemView.findViewById(R.id.text_TAGID);
            text_CLASSOFTAG = itemView.findViewById(R.id.text_CLASSOFTAG);
        }
    }
}

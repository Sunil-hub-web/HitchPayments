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
import in.co.modelclass.FastagRequestHistory_Model;

public class FastagHistoryAdapter extends RecyclerView.Adapter<FastagHistoryAdapter.ViewHolder> {

    Context context;
    ArrayList<FastagRequestHistory_Model> requestHistory;

    public FastagHistoryAdapter(FragmentActivity activity, ArrayList<FastagRequestHistory_Model> fastagHistory) {

        this.requestHistory = fastagHistory;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fasttagrequest_history,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FastagRequestHistory_Model fasttagRequest = requestHistory.get(position);

        String status = fasttagRequest.getStatus();

        holder.text_CLASSOFTAG.setText(fasttagRequest.getClassoftag());
        holder.text_NUMBEROFTAG.setText(fasttagRequest.getNumberOffasTag());
        holder.text_REQUESTEDON.setText(fasttagRequest.getDatetime());

        if(status.equals("0")){
            holder.text_STATUS.setText("Pending");
        }else if(status.equals("1")){
            holder.text_STATUS.setText("Accepted");
        }else if(status.equals("2")){
            holder.text_STATUS.setText("Decline");
        }
    }

    @Override
    public int getItemCount() {
        return requestHistory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_CLASSOFTAG,text_NUMBEROFTAG,text_STATUS,text_REQUESTEDON;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_REQUESTEDON = itemView.findViewById(R.id.text_REQUESTEDON);
            text_CLASSOFTAG = itemView.findViewById(R.id.text_CLASSOFTAG);
            text_NUMBEROFTAG = itemView.findViewById(R.id.text_NUMBEROFTAG);
            text_STATUS = itemView.findViewById(R.id.text_STATUS);
        }
    }
}

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
import in.co.modelclass.Transaction_ModelClass;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    Context context;
    ArrayList<Transaction_ModelClass> transaction;

    public TransactionAdapter(ArrayList<Transaction_ModelClass> transaction, FragmentActivity activity) {

        this.context = activity;
        this.transaction = transaction;
    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transctiondetails,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {

        Transaction_ModelClass trans = transaction.get(position);
        holder.text_MobileNo.setText(trans.getMobileno());
        holder.text_DateTime.setText(trans.getDatetime());
        holder.text_PaymentId.setText(trans.getPaymentid());
        holder.text_Price.setText("Rs :" +trans.getPrice());
        holder.text_Statues.setText(trans.getPaymentStatues());
    }

    @Override
    public int getItemCount() {
        return transaction.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_MobileNo,text_DateTime,text_PaymentId,text_Price,text_Statues;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_Statues = itemView.findViewById(R.id.text_Statues);
            text_MobileNo = itemView.findViewById(R.id.text_MobileNo);
            text_DateTime = itemView.findViewById(R.id.text_DateTime);
            text_PaymentId = itemView.findViewById(R.id.text_PaymentId);
            text_Price = itemView.findViewById(R.id.text_Price);
        }
    }
}

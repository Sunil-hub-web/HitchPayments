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
import in.co.modelclass.Wallet_ModelClass;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {

    Context context;
    ArrayList<Wallet_ModelClass> wallet;
    public WalletAdapter(FragmentActivity activity, ArrayList<Wallet_ModelClass> wallet) {

        this.context = activity;
        this.wallet = wallet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Wallet_ModelClass wallet_modelClass = wallet.get(position);

        String wallettype = wallet_modelClass.getTransactiontype();

        holder.text_Amount.setText(wallet_modelClass.getAmount());
        holder.text_DateTime.setText(wallet_modelClass.getDatetime());
        holder.text_TransactionId.setText(wallet_modelClass.getTransactionid());

        if(wallettype.equals("1")){

            holder.text_PaymentType.setText("Credit");

        }else if(wallettype.equals("2")){

            holder.text_PaymentType.setText("Debited");
        }

    }

    @Override
    public int getItemCount() {
        return wallet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_DateTime,text_TransactionId,text_PaymentType,text_Amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_Amount = itemView.findViewById(R.id.text_Amount);
            text_DateTime = itemView.findViewById(R.id.text_DateTime);
            text_TransactionId = itemView.findViewById(R.id.text_TransactionId);
            text_PaymentType = itemView.findViewById(R.id.text_PaymentType);
        }
    }
}

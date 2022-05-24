package in.co.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;

import in.co.hitchpayments.R;
import in.co.modelclass.ClassoFtag_ModelClass;

public class ClassTagSpinerAdapter extends ArrayAdapter<ClassoFtag_ModelClass> {

    private ArrayList<ClassoFtag_ModelClass> myarrayList;

    public ClassTagSpinerAdapter(Context context, int textViewResourceId, ArrayList<ClassoFtag_ModelClass> modelArrayList) {
        super(context, textViewResourceId, modelArrayList);
        this.myarrayList = modelArrayList;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, parent);
    }

    @Nullable
    @Override
    public ClassoFtag_ModelClass getItem(int position) {
        return myarrayList.get(position);
    }

    @Override
    public int getCount() {
        int count = myarrayList.size();
        //return count > 0 ? count - 1 : count;
        return count;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, parent);
    }

    private View getCustomView(int position, ViewGroup parent) {

        ClassoFtag_ModelClass model = getItem(position);

        View spinnerRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinneritem, parent, false);

        TextView label = spinnerRow.findViewById(R.id.spinner_text);
        label.setText(String.format("%s", model != null ? model.getClassoFtag() : ""));


        return spinnerRow;
    }

}

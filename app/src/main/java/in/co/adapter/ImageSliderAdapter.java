package in.co.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import java.util.ArrayList;

import in.co.hitchpayments.R;
import in.co.modelclass.Image_ModelClass;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ViewHolder> {

    Context context;
    ArrayList<Image_ModelClass> imageslider;
    ViewPager2 viewPager2;

    public ImageSliderAdapter(Context context, ArrayList<Image_ModelClass> image_slider, ViewPager2 viewpager2ImageSlider) {

        this.context = context;
        this.imageslider = image_slider;
        this.viewPager2 = viewpager2ImageSlider;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bannerxml,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {

        Image_ModelClass slideImage = imageslider.get(position);

        holder.img_showImage.setImageResource(slideImage.getImage());

        if(position == imageslider.size() - 2){

            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return imageslider.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_showImage;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            img_showImage = itemView.findViewById(R.id.img_showImage);
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            imageslider.addAll(imageslider);
            notifyDataSetChanged();
        }
    };
}

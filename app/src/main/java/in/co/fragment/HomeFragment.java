package in.co.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

import in.co.adapter.ImageSliderAdapter;
import in.co.adapter.TransactionAdapter;
import in.co.hitchpayments.R;
import in.co.modelclass.Image_ModelClass;
import in.co.modelclass.Transaction_ModelClass;

public class HomeFragment extends Fragment {

    TextView name_text;
    Button btn_TagActivation,btn_Report,btn_FastagInventory,btn_NPCITAG;
    ViewPager2 viewpager2ImageSlider;
    ArrayList<Image_ModelClass> imageSlider = new ArrayList<>();
    ImageSliderAdapter imageSliderAdapter;
    Handler sliderhandler = new Handler();
    RecyclerView allTransactionRecycler;
    TransactionAdapter transactionAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Transaction_ModelClass> transaction = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);

        name_text = view.findViewById(R.id.name_text);
        viewpager2ImageSlider = view.findViewById(R.id.viewpager2ImageSlider);
        allTransactionRecycler = view.findViewById(R.id.allTransactionRecycler);

        name_text.setText("Home");

        imageSlider.add(new Image_ModelClass(R.drawable.epay));
        imageSlider.add(new Image_ModelClass(R.drawable.epay2));
        imageSlider.add(new Image_ModelClass(R.drawable.epay));
        imageSlider.add(new Image_ModelClass(R.drawable.epay2));

        imageSliderAdapter = new ImageSliderAdapter(getContext(),imageSlider,viewpager2ImageSlider);
        viewpager2ImageSlider.setAdapter(imageSliderAdapter);

        viewpager2ImageSlider.setClipToPadding(false);
        viewpager2ImageSlider.setClipChildren(false);
        viewpager2ImageSlider.setOffscreenPageLimit(3);
        viewpager2ImageSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull  View page, float position) {

                float r = 1 - Math.abs(position);
                page.setScaleY(1.0f +  r * 0.90f);

            }
        });

        viewpager2ImageSlider.setPageTransformer(compositePageTransformer);

        Runnable sliderRunable = new Runnable() {
            @Override
            public void run() {

                viewpager2ImageSlider.setCurrentItem(viewpager2ImageSlider.getCurrentItem() + 1);
            }
        };

        viewpager2ImageSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                //super.onPageSelected(position);

               /* if (currentPossition >= arraysize)
                    currentPossition = 0;
                selectedIndicatorPosition (currentPossition++);*/

                sliderhandler.removeCallbacks(sliderRunable);
                sliderhandler.postDelayed(sliderRunable,2000);

            }
        });

        transaction.add(new Transaction_ModelClass("25.4.2022 6:15","1234567891","1722700","500","Success"));
        transaction.add(new Transaction_ModelClass("25.4.2022 6:15","7008324941","1909005","700","Success"));
        transaction.add(new Transaction_ModelClass("25.4.2022 6:15","9777022225","1909005","1000","Success"));
        transaction.add(new Transaction_ModelClass("25.4.2022 6:15","1234567894","1722755","1200","Success"));
        transaction.add(new Transaction_ModelClass("25.4.2022 6:15","1234567895","1722708","1500","Success"));

        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        transactionAdapter = new TransactionAdapter(transaction,getActivity());
        allTransactionRecycler.setLayoutManager(linearLayoutManager);
        allTransactionRecycler.setHasFixedSize(true);
        allTransactionRecycler.setAdapter(transactionAdapter);

        return view;
    }
}

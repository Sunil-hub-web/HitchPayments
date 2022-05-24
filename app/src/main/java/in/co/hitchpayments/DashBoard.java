package in.co.hitchpayments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;


import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import in.co.fragment.FastagInventoryFragment;
import in.co.fragment.FasttagRequestHistory;
import in.co.fragment.HomeFragment;
import in.co.fragment.NPCITagFragment;
import in.co.fragment.ProfileFragment;
import in.co.fragment.ReportFragment;
import in.co.fragment.TagActivationFragment;
import in.co.fragment.WalletFragment;
import in.co.url.AppUrl;
import in.co.url.SessionManager;

public class DashBoard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private ChipNavigationBar bottomNavigation;
    DrawerLayout myDrawer;
    NavigationView navigationView;
    SessionManager sessionManager;
    CircleImageView nav_profile_image;
    TextView nav_NPCITAG,nav_TagActivation,nav_FastagInventory,nav_Report,nav_Logout,nav_Wallet,
            nav_RequestFastag,nav_userid,nav_userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        navigationView = findViewById(R.id.navigationview);
        myDrawer = findViewById(R.id.myDrawer);

        sessionManager = new SessionManager(DashBoard.this);


        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        nav_Logout = header.findViewById(R.id.nav_Logout);
        nav_NPCITAG = header.findViewById(R.id.nav_NPCITAG);
        nav_TagActivation = header.findViewById(R.id.nav_TagActivation);
        //nav_ActivatePendingFastag = header.findViewById(R.id.nav_ActivatePendingFastag);
        nav_FastagInventory = header.findViewById(R.id.nav_FastagInventory);
        nav_Report = header.findViewById(R.id.nav_Report);
        nav_Wallet = header.findViewById(R.id.nav_Wallet);
        nav_RequestFastag = header.findViewById(R.id.nav_RequestFastag);
        nav_profile_image = header.findViewById(R.id.nav_profile_image);
        nav_userid = header.findViewById(R.id.nav_userid);
        nav_userName = header.findViewById(R.id.nav_userName);

        String url = "https://kottakotabusinesses.com/public/adminasset/img/salesagent/profileimage/"+sessionManager.getProfile();

        Picasso.get().load(url).into(nav_profile_image);

        nav_userid.setText(sessionManager.getRegistrationNumber());
        nav_userName.setText(sessionManager.getUserName());



        nav_NPCITAG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDrawer.closeDrawer(GravityCompat.START);

                bottomNavigation.setItemSelected(R.id.home, false);

                NPCITagFragment npciTagFragment = new NPCITagFragment();
                FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, npciTagFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        nav_FastagInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDrawer.closeDrawer(GravityCompat.START);

                bottomNavigation.setItemSelected(R.id.home, false);

                FastagInventoryFragment fastagInventoryFragment = new FastagInventoryFragment();
                FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fastagInventoryFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        nav_TagActivation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDrawer.closeDrawer(GravityCompat.START);

                bottomNavigation.setItemSelected(R.id.home, false);

                TagActivationFragment tagActivationFragment = new TagActivationFragment();
                FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, tagActivationFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        nav_Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDrawer.closeDrawer(GravityCompat.START);

                bottomNavigation.setItemSelected(R.id.home, false);

                ReportFragment reportFragment = new ReportFragment();
                FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, reportFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        nav_Wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDrawer.closeDrawer(GravityCompat.START);

                bottomNavigation.setItemSelected(R.id.home, false);

                WalletFragment walletFragment = new WalletFragment();
                FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, walletFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        nav_RequestFastag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDrawer.closeDrawer(GravityCompat.START);

                bottomNavigation.setItemSelected(R.id.home, false);

                FasttagRequestHistory fasttagRequestHistory = new FasttagRequestHistory();
                FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fasttagRequestHistory);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        nav_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sessionManager.logoutUser();
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment(), "HomeFragment").addToBackStack(null).commit();
        bottomNavigation.setItemSelected(R.id.home, true);
        bottomNavigation.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

               Fragment fragment = null;

                switch (i) {

                    case R.id.home:

                        Log.d("possitionid",String.valueOf(i));

                        fragment = new HomeFragment();

                        break;

                    case R.id.profile:

                        Log.d("possitionid",String.valueOf(i));

                        fragment = new ProfileFragment();


                        break;

                }

                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        myDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void Clickmenu(View view){

        // open drawer
        openDrawer(myDrawer);
    }

    private static void openDrawer(DrawerLayout drawerLayout){

        // opendrawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }


}


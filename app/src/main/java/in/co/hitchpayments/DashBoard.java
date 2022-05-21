package in.co.hitchpayments;

import androidx.annotation.NonNull;
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

import com.google.android.material.navigation.NavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import in.co.fragment.FastagInventoryFragment;
import in.co.fragment.HomeFragment;
import in.co.fragment.NPCITagFragment;
import in.co.fragment.ProfileFragment;
import in.co.fragment.ReportFragment;
import in.co.fragment.TagActivationFragment;
import in.co.fragment.WalletFragment;

public class DashBoard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private ChipNavigationBar bottomNavigation;
    DrawerLayout myDrawer;
    NavigationView navigationView;
    TextView nav_NPCITAG,nav_TagActivation,nav_FastagInventory,nav_Report,nav_Logout,nav_Wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        navigationView = findViewById(R.id.navigationview);
        myDrawer = findViewById(R.id.myDrawer);

        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        nav_Logout = header.findViewById(R.id.nav_Logout);
        nav_NPCITAG = header.findViewById(R.id.nav_NPCITAG);
        nav_TagActivation = header.findViewById(R.id.nav_TagActivation);
        //nav_ActivatePendingFastag = header.findViewById(R.id.nav_ActivatePendingFastag);
        nav_FastagInventory = header.findViewById(R.id.nav_FastagInventory);
        nav_Report = header.findViewById(R.id.nav_Report);
        nav_Wallet = header.findViewById(R.id.nav_Wallet);

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


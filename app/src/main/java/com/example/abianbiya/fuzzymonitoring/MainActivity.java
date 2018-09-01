package com.example.abianbiya.fuzzymonitoring;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Checkable;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, CheckFragment.OnFragmentInteractionListener, HistoryFragment.OnFragmentInteractionListener{

    Toolbar toolbar;
    TextView tvTitle;
    private String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View viewActionBar = getLayoutInflater().inflate(R.layout.toolbar_title, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);

        tvTitle = (TextView) viewActionBar.findViewById(R.id.text_title);
        initToolbar("Monitoring Kolam Lele");

        getSupportActionBar().setCustomView(viewActionBar, params);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //---toolbar

        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);



        bottomNavigationView.inflateMenu(R.menu.menu_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.home:
                                selectedFragment = new HomeFragment();
                                initToolbar("Home");
                                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                                break;
                            case R.id.kondisi:
                                selectedFragment = new CheckFragment();
                                initToolbar("Cek Kondisi");
                                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                                break;
                            case R.id.history:
                                selectedFragment = new HistoryFragment();
                                initToolbar("History");
                                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });



        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, new HomeFragment());
        transaction.commit();

        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);

    }


    public void initToolbar(String vTitle){

        tvTitle.setText(vTitle);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

//    @Override
//    public void onFragmentInteraction(Uri uri) {
//
//    }
}

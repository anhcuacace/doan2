package com.example.doan2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.doan2.BackgroundService;
import com.example.doan2.fragment.FragmentHientai;
import com.example.doan2.MainViewpagerAdapter;
import com.example.doan2.fragment.GasFragment;
import com.example.doan2.fragment.temperatureFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.tunanh.firewarning.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int FRAGMENT_GAS=0;
    public static final int FRAGMENT_TEMPERATURE=1;
//    public static final int FRAGMENT_GAS=0;
    public static int mCurrentFragment=FRAGMENT_GAS;
//    private TabLayout tabLayout;
//    private ViewPager viewPager;
    private TextView tv_toolbar;
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i=new Intent(this, BackgroundService.class);

        startService(i);
//        Intent intent = new Intent(this, BackgroundService.class);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(intent);
//        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        tv_toolbar=findViewById(R.id.toolbar_name);
        setSupportActionBar(toolbar);

        mDrawerLayout =findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.nav_drawer_open,R.string.nav_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new GasFragment());
        navigationView.getMenu().findItem(R.id.nav_gas).setChecked(true);
//        tabLayout = findViewById(R.id.myTabLayout);
//        viewPager = findViewById(R.id.myViewPager);
//
//        MainViewpagerAdapter mainViewPagerAdapter = new MainViewpagerAdapter(getSupportFragmentManager());
//        Bundle bundle1 = new Bundle();
//        bundle1.putString("key", "khigatram1");
//        Bundle bundle2 = new Bundle();
//        bundle2.putString("key", "khigatram2");
//        Fragment phong1 = new FragmentHientai();
//        phong1.setArguments(bundle1);
//        Fragment phong2 = new FragmentHientai();
//        phong2.setArguments(bundle2);
//        mainViewPagerAdapter.addFragment(phong1,"Phòng 1");
//        mainViewPagerAdapter.addFragment(phong2,"Phòng 2");
//
//        viewPager.setAdapter(mainViewPagerAdapter);
//        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.nav_gas){
            if(mCurrentFragment!=FRAGMENT_GAS){
                replaceFragment(new GasFragment());
                mCurrentFragment=FRAGMENT_GAS;
                tv_toolbar.setText(getText(R.string.toxic_gas));
            }

        }else if (id==R.id.nav_temperature){
            if(mCurrentFragment!=FRAGMENT_TEMPERATURE){
                replaceFragment(new temperatureFragment());
                mCurrentFragment=FRAGMENT_TEMPERATURE;
                tv_toolbar.setText(getText(R.string.nav_temperature));
            }
        }else if(id==R.id.nav_logout){

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
    //    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//        super.onPointerCaptureChanged(hasCapture);
//    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();

    }
}
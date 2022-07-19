package com.example.doan2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.doan2.BackgroundService;
import com.example.doan2.FragmentHientai;
import com.example.doan2.MainViewpagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.tunanh.firewarning.R;

public class MainActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
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
        tabLayout = findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.myViewPager);

        MainViewpagerAdapter mainViewPagerAdapter = new MainViewpagerAdapter(getSupportFragmentManager());
        Bundle bundle1 = new Bundle();
        bundle1.putString("key", "khigatram1");
        Bundle bundle2 = new Bundle();
        bundle2.putString("key", "khigatram2");
        Fragment phong1 = new FragmentHientai();
        phong1.setArguments(bundle1);
        Fragment phong2 = new FragmentHientai();
        phong2.setArguments(bundle2);
        mainViewPagerAdapter.addFragment(phong1,"Phòng 1");
        mainViewPagerAdapter.addFragment(phong2,"Phòng 2");


        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


}
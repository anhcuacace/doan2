package com.example.doan2.fragment;

import android.app.FragmentManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doan2.MainViewpagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.tunanh.firewarning.R;


public class GasFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_gas, container, false);
        tabLayout = v.findViewById(R.id.myTabLayout);
        viewPager = v.findViewById(R.id.myViewPager);

        MainViewpagerAdapter mainViewPagerAdapter = new MainViewpagerAdapter(getActivity().getSupportFragmentManager());
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
        // Inflate the layout for this fragment
        return v;
    }


}
package com.example.doan2.fragment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//import androidx.room.parser.Section;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doan2.MainViewpagerAdapter;
import com.github.anastr.speedviewlib.SpeedView;
import com.github.anastr.speedviewlib.components.Section;
import com.github.anastr.speedviewlib.components.Style;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tunanh.firewarning.R;

import org.json.JSONObject;


public class temperatureFragment extends Fragment {

//    private TabLayout tabLayout;
//    private ViewPager viewPager;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View v=inflater.inflate(R.layout.fragment_temperature, container, false);
        // Inflate the layout for this fragment
//        tabLayout = v.findViewById(R.id.myTabLayout);
//        viewPager = v.findViewById(R.id.myViewPager);
//        MainViewpagerAdapter mainViewPagerAdapter = new MainViewpagerAdapter(getActivity().getSupportFragmentManager());
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


//        viewPager.setAdapter(mainViewPagerAdapter);
//        tabLayout.setupWithViewPager(viewPager);
//        return v;
//    }
SpeedView speedometer;
    Handler handler;
    Runnable runnable;
    int time = 5000;
    String field;
//    FirebaseService firebaseService = new FirebaseService();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hientai,container,false);


        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        speedometer = (SpeedView) view.findViewById(R.id.speedView);
        speedometer.setWithTremble(false);
        speedometer.setMinMaxSpeed(-10,180);

        speedometer.getSections().get(0).setStartEndOffset(0,.15f);
        speedometer.getSections().get(1).setStartEndOffset(.15f, .3f);
        speedometer.getSections().get(2).setStartEndOffset(.3f, 1f);
        speedometer.getSections().get(0).setColor(R.color.purple_700);
        speedometer.getSections().get(1).setColor(Color.GREEN);
        speedometer.getSections().get(2).setColor(Color.RED);
//        speedometer.getSections().get(4).setStartEndOffset(.5f, 1f);
//        speedometer.getSections().get(0).setStartEndOffset(0, .15f);
//        speedometer.clearSections();
//        speedometer.addSections(new Section(0f, .1f, Color.LTGRAY)
//                , new Section(.1f, .4f, Color.YELLOW)
//                , new Section(.4f, .75f, Color.BLUE)
//                , new Section(.75f, .9f, Color.RED));
        speedometer.setTickNumber(20);
        speedometer.setUnit("C");
        getdata();

    }
    private void getdata() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference= database.getReference("kiemtra/temperature");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long temp= snapshot.getValue(Long.class);
                try {

                    speedometer.speedTo(temp,time);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ahihi",error.getMessage());
            }
        });
    }
}
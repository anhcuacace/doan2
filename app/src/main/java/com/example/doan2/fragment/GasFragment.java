package com.example.doan2.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//import androidx.room.parser.Section;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.anastr.speedviewlib.SpeedView;
import com.google.android.material.slider.RangeSlider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tunanh.firewarning.R;


public class GasFragment extends Fragment {
//    private TabLayout tabLayout;
//    private ViewPager viewPager;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View v=inflater.inflate(R.layout.fragment_gas, container, false);
//
////        tabLayout = v.findViewById(R.id.myTabLayout);
////        viewPager = v.findViewById(R.id.myViewPager);
////
////        MainViewpagerAdapter mainViewPagerAdapter = new MainViewpagerAdapter(getActivity().getSupportFragmentManager());
////        Bundle bundle1 = new Bundle();
////        bundle1.putString("key", "khigatram1");
////        Bundle bundle2 = new Bundle();
////        bundle2.putString("key", "khigatram2");
////        Fragment phong1 = new FragmentHientai();
////        phong1.setArguments(bundle1);
////        Fragment phong2 = new FragmentHientai();
////        phong2.setArguments(bundle2);
////        mainViewPagerAdapter.addFragment(phong1,"Phòng 1");
////        mainViewPagerAdapter.addFragment(phong2,"Phòng 2");
////
////
////        viewPager.setAdapter(mainViewPagerAdapter);
////        tabLayout.setupWithViewPager(viewPager);
//        // Inflate the layout for this fragment
//        return v;
//    }

    SpeedView speedometer;
//    Handler handler;
//    Runnable runnable;
    int time = 5000;
//    String field;
//    FirebaseService firebaseService = new FirebaseService();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        try {
//            field = getArguments().getString("key");
//        }catch (Exception e){
//            Log.d("ahihi",e.getMessage());
//        }
        View view = inflater.inflate(R.layout.fragment_hientai,container,false);

//
//        handler = new Handler();
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                new GetDataTask().execute();
//                handler.postDelayed(runnable,time);
//            }
//        };
//        handler.postDelayed(runnable, 0);
        return view;
    }

//    class GetDataTask extends AsyncTask<Void, Void, Integer> {
//        private Exception exception;
//
//        @Override
//        protected Integer doInBackground(Void... voids) {
//            try {
//                JSONObject data = firebaseService.getData();
//                int gas = data.getInt(field);
//
//                return gas;
//            } catch (Exception e) {
//                this.exception = e;
//                return 0;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(Integer data) {
//            speedometer.speedTo((float) data ,time);
//        }
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        speedometer = (SpeedView) view.findViewById(R.id.speedView);
        speedometer.setWithTremble(false);
        speedometer.setTickNumber(11);
//        speedometer.clearSections();

        speedometer.getSections().get(0).setStartEndOffset(0, .2f);
        speedometer.getSections().get(1).setStartEndOffset(.2f, .5f);
        speedometer.getSections().get(2).setStartEndOffset(.5f, 1f);
        speedometer.setOnSpeedChangeListener((gauge, aBoolean, aBoolean2) ->
                {
                    if(speedometer.getSpeed()<20){
                        speedometer.setUnit(getString(R.string.safe));
                    }else if (speedometer.getSpeed()>50){
                        speedometer.setUnit(getString(R.string.hazardous));
                    }else {
                        speedometer.setUnit(getString(R.string.warning));
                    }
                    return null;
                });

//        speedometer.setUnit("%");
        getdata();

    }

    private void getdata() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference= database.getReference("kiemtra/gas");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long gas= snapshot.getValue(Long.class);
                try {
                    speedometer.speedTo(gas,time);
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
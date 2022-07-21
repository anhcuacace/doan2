package com.example.doan2.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doan2.MainViewpagerAdapter;
import com.github.anastr.speedviewlib.SpeedView;
import com.google.android.material.tabs.TabLayout;
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
    FirebaseService firebaseService = new FirebaseService();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            field = firebaseService.getData().getString("temperature");
        }catch (Exception e){
            Log.d("ahihi",e.getMessage());
        }
        View view = inflater.inflate(R.layout.fragment_temperature,container,false);
        speedometer = (SpeedView) view.findViewById(R.id.speedView);
        speedometer.setWithTremble(false);
        speedometer.getSections().get(0).setStartEndOffset(0, .2f);
        speedometer.getSections().get(1).setStartEndOffset(.2f, .5f);
        speedometer.getSections().get(2).setStartEndOffset(.5f, 1f);
        speedometer.setUnit("%");

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                new GetDataTask().execute();
                handler.postDelayed(runnable,time);
            }
        };
        handler.postDelayed(runnable, 0);
        return view;
    }

    class GetDataTask extends AsyncTask<Void, Void, Integer> {
        private Exception exception;

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                JSONObject data = firebaseService.getData();
                int gas = data.getInt(field);

                return gas;
            } catch (Exception e) {
                this.exception = e;
                return 0;
            }
        }

        @Override
        protected void onPostExecute(Integer data) {
            speedometer.speedTo((float) data ,time);
        }
    }
}
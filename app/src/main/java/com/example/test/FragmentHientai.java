package com.example.test;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.anastr.speedviewlib.SpeedView;
import com.tunanh.firewarning.R;

import org.json.JSONObject;

public class FragmentHientai extends Fragment {
    SpeedView speedometer;
    Handler handler;
    Runnable runnable;
    int time = 5000;
    String field;
    FirebaseService firebaseService = new FirebaseService();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        field = getArguments().getString("key");

        View view = inflater.inflate(R.layout.fragment_hientai,container,false);
        speedometer = view.findViewById(R.id.speedView);
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



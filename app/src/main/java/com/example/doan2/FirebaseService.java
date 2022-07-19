package com.example.doan2;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FirebaseService {
    OkHttpClient client;

    FirebaseService() {
        client = new OkHttpClient();
    }

    JSONObject getData() {
        Request request = new Request.Builder()
                .url("https://doanmonhoc1-5cbdc-default-rtdb.firebaseio.com/Sensor.json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            JSONObject data = new JSONObject(response.body().string());
            return data;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }
}

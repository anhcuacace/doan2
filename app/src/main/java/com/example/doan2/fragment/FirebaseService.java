package com.example.doan2.fragment;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import io.grpc.internal.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FirebaseService {
    OkHttpClient client;

    public FirebaseService() {
        client = new OkHttpClient();
    }

    public JSONObject getData() throws ParseException {
//        Request request = new Request.Builder()
//                .url("https://doan2-6803f-default-rtdb.firebaseio.com")
//                .build();
////        FileInputStream serviceAccount =
////                new FileInputStream("path/to/serviceAccountKey.json");
////
////        FirebaseOptions options = new FirebaseOptions.Builder()
////                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
////                .setDatabaseUrl("https://doan2-6803f-default-rtdb.firebaseio.com")
////                .build();
////
////        FirebaseApp.initializeApp(options);
//        try (Response response = client.newCall(request).execute()) {
//            JSONObject data = new JSONObject(response.body().string());
//            return data;
//        } catch (IOException | JSONException e) {
//            e.printStackTrace();
//        }
//        return new JSONObject();
        try {
            String s = getjsonStrfromURL("https://doan2-6803f-default-rtdb.firebaseio.com/kiemtra.json");
            JSONParser parser= new JSONParser();
            JSONObject object=(JSONObject) parser.parse(s);
            return object;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String getjsonStrfromURL(String string){
        String jsontx="";
        try {
            URL url= new URL(string);
            InputStream is =url.openStream();
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line=bufferedReader.readLine())!=null){
                jsontx+=line+"\n";

            }
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsontx;
    }
}

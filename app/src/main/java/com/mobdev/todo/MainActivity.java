package com.mobdev.todo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "577b3bd2eec54e5a84a1ae825e746783";

    protected static EditText userField;
    private Button mainBtn;
    private TextView viewMap;
    protected static TextView resultInfo;

    public static String city;
    public static double lat;
    public static double lon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userField = findViewById(R.id.user_field);
        mainBtn = findViewById(R.id.main_btn);
        viewMap = findViewById(R.id.view_map);
        resultInfo = findViewById(R.id.result_info);

        viewMap.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
        });

        mainBtn.setOnClickListener(view -> {
            if (userField.getText().toString().trim().equals(""))
                Toast.makeText(MainActivity.this, R.string.no_user_input, Toast.LENGTH_LONG).show();
            else {
                try {
                    String urlGetCoordinates = "http://api.openweathermap.org/geo/1.0/direct?q="
                            + userField.getText().toString() + "&limit=1&appid=" + API_KEY;

                    AsyncTask<String, String, String> request = new GetCitiesCoordinates().execute(urlGetCoordinates);
                    if(request.getStatus() == AsyncTask.Status.FINISHED){
                        Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private static class GetCitiesCoordinates extends HttpRequest {

        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity.resultInfo.setText("Загрузка...");
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject object = new JSONArray(result).getJSONObject(0);
                lat = object.getDouble("lat");
                lon = object.getDouble("lon");
                city = object.getJSONObject("local_names").getString("ru");
                MainActivity.resultInfo.setText("");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
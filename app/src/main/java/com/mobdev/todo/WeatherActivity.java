package com.mobdev.todo;

import android.annotation.SuppressLint;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherActivity extends AppCompatActivity {

    private LinearLayout blockWeather;
    private TextView nowTemp;
    private TextView cityName;
    private TextView dropInfo;
    private TextView windInfo;
    private ImageView nowWeatherIcon;

    private TextView tempOfFirstDay;
    private TextView windInfo1;
    private TextView dropInfo1;
    private ImageView firstDayWeatherIcon;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_forecast);

        blockWeather = findViewById(R.id.first_block_weather);
        nowTemp = findViewById(R.id.now_temp);
        cityName = findViewById(R.id.city_name);
        dropInfo = findViewById(R.id.drop_info);
        windInfo = findViewById(R.id.wind_info);
        nowWeatherIcon = findViewById(R.id.now_weather_icon);

        tempOfFirstDay = findViewById(R.id.temp_of_first_day);
        dropInfo1 = findViewById(R.id.drop_info1);
        windInfo1 = findViewById(R.id.wind_info1);
        firstDayWeatherIcon = findViewById(R.id.first_day_weather_icon);

        String city = MainActivity.userField.getText().toString();
        String key = "577b3bd2eec54e5a84a1ae825e746783";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city
                + "&units=metric&appid=" + key + "&lang=ru";

        double lat = 53.198627;
        double lon = 50.113987;
        String urlGetCoordinates = "http://api.openweathermap.org/geo/1.0/direct?q=" + city   //получить координаты object[0]?.getDouble("lat"&"lon")
                + "&limit=1&appid=" + key;                                                    //и использовать их в следующем запросе

        String forecastUrl = "https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon="
                + lon + "&exclude=minutely,hourly,alerts&units=metric&appid=" + key + "&lang=ru";
        new GetURLData().execute(url);
    }

    @SuppressLint("StaticFieldLeak")
    private class GetURLData extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity.resultInfo.setText("Загрузка...");
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            URL url = null;
            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");

                    return buffer.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("SetTextI18n")
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject object = new JSONObject(result);
                double temp = object.getJSONObject("main").getDouble("temp");
                double humidity = object.getJSONObject("main").getInt("humidity");
                double wind = object.getJSONObject("wind").getDouble("speed");
                String city = object.getString("name");

                nowTemp.setText((int) Math.round(temp) + "°С");
                dropInfo.setText((int) humidity + " %");
                windInfo.setText((int) Math.round(wind) + " м/с");
                cityName.setText(city);

                String nowIcon = object.getJSONArray("weather").getJSONObject(0).getString("icon");
                String iconUrl = "https://openweathermap.org/img/wn/" + nowIcon + "@2x.png";
                Picasso.get()
                        .load(iconUrl)
                        .error(R.drawable.ic_baseline_error_24)
                        .into(nowWeatherIcon);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

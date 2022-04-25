package com.mobdev.todo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

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

    private TextView tempOfSecondDay;
    private TextView windInfo2;
    private TextView dropInfo2;
    private ImageView secondDayWeatherIcon;

    private TextView tempOfThirdDay;
    private TextView windInfo3;
    private TextView dropInfo3;
    private ImageView thirdDayWeatherIcon;

    private TextView tempOfFourthDay;
    private TextView windInfo4;
    private TextView dropInfo4;
    private ImageView fourthDayWeatherIcon;


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

        tempOfSecondDay = findViewById(R.id.temp_of_second_day);
        dropInfo2 = findViewById(R.id.drop_info2);
        windInfo2 = findViewById(R.id.wind_info2);
        secondDayWeatherIcon = findViewById(R.id.second_day_weather_icon);

        tempOfThirdDay = findViewById(R.id.temp_of_third_day);
        dropInfo3 = findViewById(R.id.drop_info3);
        windInfo3 = findViewById(R.id.wind_info3);
        thirdDayWeatherIcon = findViewById(R.id.third_day_weather_icon);

        tempOfFourthDay = findViewById(R.id.temp_of_fourth_day);
        dropInfo4 = findViewById(R.id.drop_info4);
        windInfo4 = findViewById(R.id.wind_info4);
        fourthDayWeatherIcon = findViewById(R.id.fourth_day_weather_icon);

        cityName.setText(MainActivity.city);

//        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + MainActivity.city
//                + "&units=metric&appid=" + MainActivity.API_KEY + "&lang=ru";

//        double lat = 53.198627;  //Samara
//        double lon = 50.113987;

        String forecastUrl = "https://api.openweathermap.org/data/2.5/onecall?lat=" + MainActivity.lat + "&lon="
                + MainActivity.lon + "&exclude=minutely,hourly,alerts&units=metric&appid=" + MainActivity.API_KEY + "&lang=ru";
        new GetURLData().execute(forecastUrl);
    }

    @SuppressLint("StaticFieldLeak")
    private class GetURLData extends HttpRequest {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        @SuppressLint("SetTextI18n")
        protected void onPostExecute(String result) {
            try {
                JSONObject object = new JSONObject(result);
//                double temp = object.getJSONObject("main").getDouble("temp");
//                double humidity = object.getJSONObject("main").getInt("humidity");
//                double wind = object.getJSONObject("wind").getDouble("speed");
//                String city = object.getString("name");

                double temp = object.getJSONObject("current").getDouble("temp");
                double humidity = object.getJSONObject("current").getInt("humidity");
                double wind = object.getJSONObject("current").getDouble("wind_speed");

                nowTemp.setText((int) Math.round(temp) + "°С");
                dropInfo.setText((int) humidity + " %");
                windInfo.setText((int) Math.round(wind) + " м/с");

                String nowIcon = object.getJSONObject("current").getJSONArray("weather")
                        .getJSONObject(0).getString("icon");
                String iconUrl = "https://openweathermap.org/img/wn/" + nowIcon + "@2x.png";
                Picasso.get()
                        .load(iconUrl)
                        .error(R.drawable.ic_baseline_error_24)
                        .into(nowWeatherIcon);

                // 1 блок
                double temp1 = object.getJSONArray("daily").getJSONObject(1)
                        .getJSONObject("temp").getDouble("day");
                double humidity1 = object.getJSONArray("daily").getJSONObject(1)
                        .getInt("humidity");
                double wind1 = object.getJSONArray("daily").getJSONObject(1)
                        .getDouble("wind_speed");

                tempOfFirstDay.setText((int) Math.round(temp1) + "°С");
                dropInfo1.setText((int) humidity1 + " %");
                windInfo1.setText((int) Math.round(wind1) + " м/с");

                String first_icon = object.getJSONArray("daily").getJSONObject(1).getJSONArray("weather")
                        .getJSONObject(0).getString("icon");
                String iconUrl1 = "https://openweathermap.org/img/wn/" + first_icon + "@2x.png";
                Picasso.get()
                        .load(iconUrl1)
                        .error(R.drawable.ic_baseline_error_24)
                        .into(firstDayWeatherIcon);

                // 2 блок
                double temp2 = object.getJSONArray("daily").getJSONObject(2)
                        .getJSONObject("temp").getDouble("day");
                double humidity2 = object.getJSONArray("daily").getJSONObject(2)
                        .getInt("humidity");
                double wind2 = object.getJSONArray("daily").getJSONObject(2)
                        .getDouble("wind_speed");

                tempOfSecondDay.setText((int) Math.round(temp2) + "°С");
                dropInfo2.setText((int) humidity2 + " %");
                windInfo2.setText((int) Math.round(wind2) + " м/с");

                String second_icon = object.getJSONArray("daily").getJSONObject(2).getJSONArray("weather")
                        .getJSONObject(0).getString("icon");
                String iconUrl2 = "https://openweathermap.org/img/wn/" + second_icon + "@2x.png";
                Picasso.get()
                        .load(iconUrl2)
                        .error(R.drawable.ic_baseline_error_24)
                        .into(secondDayWeatherIcon);

                // 3 блок
                double temp3 = object.getJSONArray("daily").getJSONObject(3)
                        .getJSONObject("temp").getDouble("day");
                double humidity3 = object.getJSONArray("daily").getJSONObject(3)
                        .getInt("humidity");
                double wind3 = object.getJSONArray("daily").getJSONObject(3)
                        .getDouble("wind_speed");

                tempOfThirdDay.setText((int) Math.round(temp3) + "°С");
                dropInfo3.setText((int) humidity3 + " %");
                windInfo3.setText((int) Math.round(wind3) + " м/с");

                String third_icon = object.getJSONArray("daily").getJSONObject(3).getJSONArray("weather")
                        .getJSONObject(0).getString("icon");
                String iconUrl3 = "https://openweathermap.org/img/wn/" + third_icon + "@2x.png";
                Picasso.get()
                        .load(iconUrl3)
                        .error(R.drawable.ic_baseline_error_24)
                        .into(thirdDayWeatherIcon);

                // 4 блок
                double temp4 = object.getJSONArray("daily").getJSONObject(4)
                        .getJSONObject("temp").getDouble("day");
                double humidity4 = object.getJSONArray("daily").getJSONObject(4)
                        .getInt("humidity");
                double wind4 = object.getJSONArray("daily").getJSONObject(4)
                        .getDouble("wind_speed");

                tempOfFourthDay.setText((int) Math.round(temp4) + "°С");
                dropInfo4.setText((int) humidity4 + " %");
                windInfo4.setText((int) Math.round(wind4) + " м/с");

                String fourth_icon = object.getJSONArray("daily").getJSONObject(4).getJSONArray("weather")
                        .getJSONObject(0).getString("icon");
                String iconUrl4 = "https://openweathermap.org/img/wn/" + fourth_icon + "@2x.png";
                Picasso.get()
                        .load(iconUrl4)
                        .error(R.drawable.ic_baseline_error_24)
                        .into(fourthDayWeatherIcon);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

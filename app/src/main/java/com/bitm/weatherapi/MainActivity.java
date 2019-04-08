package com.bitm.weatherapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.bitm.weatherapi.Weather.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView tempTV;
    private double lat=23.7566001;
    private double lng=90.3894496;
    private String units= "metric";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempTV = findViewById(R.id.tempTVID);
        getWeatherInfo();
    }

    private void getWeatherInfo() {

        Weather_service weather_service = RetrofitClass.getRetrofitInstance().create(Weather_service.class);
        String url = String.format("weather?lat=%f&lon=%f&units=%s&appid=%s",lat,lng,units,
                getResources().getString(R.string.weather_api_key));
        Call<WeatherResponse> weatherResponseCall = weather_service.getWeatherData(url);

        weatherResponseCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {

                if (response.code()==200){

                    WeatherResponse weatherResponse = response.body();

                    tempTV.setText(String.valueOf(weatherResponse.getMain().getTempMin()));
                }

            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}

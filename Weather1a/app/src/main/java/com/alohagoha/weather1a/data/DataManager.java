package com.alohagoha.weather1a.data;

import android.util.Log;
import android.widget.TextView;

import com.alohagoha.weather1a.retrofit.OpenWeather;
import com.alohagoha.weather1a.retrofit.WeatherRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataManager implements  IDataManager{
    private static final String BASE_URL = "http://api.openweathermap.org/";
    private static final String TAG = "RETROFIT";
    OpenWeather openWeather;
    public void initRetrofit() {
        Retrofit retrofit;
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        openWeather = retrofit.create(OpenWeather.class);

    }

    public float toC(float t) {
        return t-273.15f;
    }

    public void requestRetrofit(final TextView temp, String city, String keyApi) {
        openWeather.loadWeather(city,keyApi)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        if(response.body()!=null) {
                            Log.d(TAG,Float.toString(response.body().getMain().getTemp()));
                            temp.setText(Float.toString(toC(response.body().getMain().getTemp())));
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                            Log.d(TAG,"oshibka");
                    }
                });
    }
}

package com.seb.ahmed.weather_app.MainActivity.Service;

import com.seb.ahmed.weather_app.MainActivity.Models.WeatherResponse;
import com.seb.ahmed.weather_app.common.Const;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPIs {

    @GET("forecast.json ")
    Call<WeatherResponse> GetCurrentWeather(@Query("key") String key, @Query("q") String city, @Query("days") String days);
}

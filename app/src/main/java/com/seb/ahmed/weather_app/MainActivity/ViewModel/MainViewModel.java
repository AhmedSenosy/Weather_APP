package com.seb.ahmed.weather_app.MainActivity.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.seb.ahmed.weather_app.Abstract.AbstractViewModel;
import com.seb.ahmed.weather_app.DB.WeatherRepo;
import com.seb.ahmed.weather_app.MainActivity.Adapter.WeatherAdapter;
import com.seb.ahmed.weather_app.MainActivity.Models.Current;
import com.seb.ahmed.weather_app.MainActivity.Models.Day;
import com.seb.ahmed.weather_app.MainActivity.Models.WeatherResponse;
import com.seb.ahmed.weather_app.MainActivity.Service.WeatherAPIs;
import com.seb.ahmed.weather_app.Rest.RestApiFactory;
import com.seb.ahmed.weather_app.common.ApplicationWrapper;
import com.seb.ahmed.weather_app.common.Const;
import com.seb.ahmed.weather_app.common.PreferencesManager;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AbstractViewModel{

    MutableLiveData< WeatherResponse >weatherResponse;
    private WeatherAPIs weatherAPIs;
    WeatherRepo repo;
    List<Day> storedDays;


    public MainViewModel(Context context) {
        weatherAPIs = RestApiFactory.getRetrofitInstance().create(WeatherAPIs.class);
        this.context = context;
        repo = new WeatherRepo(context);
        weatherResponse = new MutableLiveData<>();
    }


    @Override
    public void initializeViews() {

    }

    @Override
    public void fetchData() {
        weatherAPIs.GetCurrentWeather(Const.API_KEY,
                PreferencesManager.getInstance(context).getLastSelectedCity(),"5").enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                weatherResponse.postValue(response.body());
//                repo.DeleteAll();
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onSuccessResponse(Object object) {
//        weatherResponse = (WeatherResponse)object;
//        weatherResponse.setCurrent(UpdateCurrentObject(weatherResponse.getCurrent()));
//        repo.DeleteAll();
//        for(int i =0 ; i<weatherResponse.getForecast().getForecastDay().size() ; i++)
//        {
//            repo.insert(weatherResponse.getForecast().getForecastDay().get(i).getDay());
//        }
//        storedDays = repo.getDayList();
//        refreshView(weatherResponse);
    }

    @Override
    public void onFailureResponse(Throwable t) {

    }

    private Current UpdateCurrentObject(Current current)
    {
        current.setMaxTemp(String.format("%s°", weatherResponse.getValue().getForecast().getForecastDay().get(0).getDay().getMaxtempC()));
        current.setMinTemp(String.format("%s°", weatherResponse.getValue().getForecast().getForecastDay().get(0).getDay().getMintempC()));
        return current;
    }

    public MutableLiveData<WeatherResponse >getWeatherResponse() {
        return weatherResponse;
    }

}

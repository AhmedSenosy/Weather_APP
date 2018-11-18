package com.seb.ahmed.weather_app.MainActivity.ViewModel;

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

public class MainViewModel extends AbstractViewModel{

    WeatherResponse weatherResponse;
    private WeatherAPIs weatherAPIs;
    WeatherRepo repo;
    List<Day> storedDays;


    public MainViewModel(Context context) {
        weatherAPIs = RestApiFactory.getRetrofitInstance().create(WeatherAPIs.class);
        this.context = context;
        repo = new WeatherRepo(context);
    }


    @Override
    public void initializeViews() {

    }

    @Override
    public void fetchData() {
        Disposable disposable = weatherAPIs.GetCurrentWeather(
                Const.API_KEY,
                PreferencesManager.getInstance(context).getLastSelectedCity(),
                "5")
                .subscribeOn(Schedulers.io())
                .observeOn(ApplicationWrapper.getInstance().subscribeScheduler())
                .subscribe(this::onSuccessResponse,this::onFailureResponse);
        compositeDisposable.add(disposable);
    }

    @Override
    public void onSuccessResponse(Object object) {
        weatherResponse = (WeatherResponse)object;
        weatherResponse.setCurrent(UpdateCurrentObject(weatherResponse.getCurrent()));
        repo.DeleteAll();
        for(int i =0 ; i<weatherResponse.getForecast().getForecastDay().size() ; i++)
        {
            repo.insert(weatherResponse.getForecast().getForecastDay().get(i).getDay());
        }
        storedDays = repo.getDayList();
        refreshView(weatherResponse);
    }

    @Override
    public void onFailureResponse(Throwable t) {

    }

    private Current UpdateCurrentObject(Current current)
    {
        current.setMaxTemp(String.format("%s°", weatherResponse.getForecast().getForecastDay().get(0).getDay().getMaxtempC()));
        current.setMinTemp(String.format("%s°", weatherResponse.getForecast().getForecastDay().get(0).getDay().getMintempC()));
        return current;
    }

    public WeatherResponse getWeatherResponse() {
        return weatherResponse;
    }

}

package com.seb.ahmed.weather_app.MainActivity.Views;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.seb.ahmed.weather_app.Abstract.AbstractActivity;
import com.seb.ahmed.weather_app.MainActivity.Adapter.WeatherAdapter;
import com.seb.ahmed.weather_app.MainActivity.Models.WeatherResponse;
import com.seb.ahmed.weather_app.MainActivity.ViewModel.MainViewModel;
import com.seb.ahmed.weather_app.R;
import com.seb.ahmed.weather_app.databinding.MainDataBinding;

import java.util.Observable;
import java.util.Observer;


public class MainActivity extends AbstractActivity implements Observer {

    MainDataBinding mainDataBinding;
    MainViewModel mainViewModel;
    WeatherAdapter adapter;

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitializeView();
        SetObservable(mainViewModel);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void InitializeView() {
        mainViewModel = new MainViewModel(this);
        mainDataBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mainViewModel.fetchData();
        adapter=new WeatherAdapter(null);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

    }

    private void SetObservable(Observable observable) {
        observable.addObserver(this);
        mainViewModel.getWeatherResponse().observe(this, new android.arch.lifecycle.Observer<WeatherResponse>() {
            @Override
            public void onChanged(@Nullable WeatherResponse weatherResponse) {
                adapter.setForecast(weatherResponse.getForecast());
                mainDataBinding.header.setCurrent(mainViewModel.getWeatherResponse().getValue().getCurrent());
                recyclerView.setAdapter(adapter);
            }
        });
        mainDataBinding.setData(mainViewModel);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof WeatherResponse)
        {
            WeatherResponse response = (WeatherResponse)arg ;
            recyclerView.setAdapter(adapter);
            adapter.setForecast(response.getForecast());
        }
    }
}

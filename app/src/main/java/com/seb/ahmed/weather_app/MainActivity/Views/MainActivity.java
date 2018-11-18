package com.seb.ahmed.weather_app.MainActivity.Views;

import android.databinding.DataBindingUtil;
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
        mainViewModel.fetchData();
    }

    private void InitializeView() {
        mainViewModel = new MainViewModel(this);
        mainDataBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    private void SetObservable(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof WeatherResponse)
        {
            WeatherResponse response = (WeatherResponse)arg ;
            adapter=new WeatherAdapter(response.getForecast());
            recyclerView.setAdapter(adapter);
            adapter.setForecast(response.getForecast());
        }
        mainDataBinding.setData(mainViewModel);
    }
}

package com.seb.ahmed.weather_app.SplashModule.View;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.seb.ahmed.weather_app.R;
import com.seb.ahmed.weather_app.common.Navigator;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HideStatusBar();
        setContentView(R.layout.activity_splash);
        RunSplash();
    }

    private void HideStatusBar() {
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void RunSplash()  {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Navigator.NavigateToMain(SplashActivity.this);
            }
        }, 3000);
    }
}

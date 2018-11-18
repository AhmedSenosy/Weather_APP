package com.seb.ahmed.weather_app.common;

import android.app.Activity;
import android.content.Intent;

import com.seb.ahmed.weather_app.MainActivity.Views.MainActivity;

public class Navigator {

    public static void NavigateToMain(Activity from)
    {
        Intent main = new Intent(from, MainActivity.class);
        from.startActivity(main);
    }
}

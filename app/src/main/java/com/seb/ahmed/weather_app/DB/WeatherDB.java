package com.seb.ahmed.weather_app.DB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.seb.ahmed.weather_app.MainActivity.Models.Day;

import java.util.List;


@Database(entities = Day.class,version = 1)
public abstract class WeatherDB extends RoomDatabase {

    private static volatile WeatherDB INSTANCE;

    public WeatherDB() {

    }

    public abstract DayDao GetdayDao();

    public static WeatherDB getINSTANCE(final Context con) {
        if (INSTANCE==null)
        {
            synchronized (WeatherDB.class)
            {
                if (INSTANCE ==null)
                {
                    INSTANCE = Room.databaseBuilder(con.getApplicationContext(),
                            WeatherDB.class, "day_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

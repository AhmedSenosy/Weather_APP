package com.seb.ahmed.weather_app.DB;

import android.content.Context;
import android.os.AsyncTask;

import com.seb.ahmed.weather_app.MainActivity.Models.Day;

import java.util.List;

public class WeatherRepo {
    DayDao dao;
    List<Day> dayList;

    public WeatherRepo(Context context) {
        WeatherDB db = WeatherDB.getINSTANCE(context);
        dao = db.GetdayDao();
//        dayList = dao.getAllDays();
    }

    public List<Day> getDayList() {
        dayList = dao.getAllDays();
        return dayList;
    }

    public void insert (Day...day) {
        new insertAsyncTask(dayList,dao).execute(day);
    }

    public void setDayList(List<Day> dayList) {
        this.dayList = dayList;
    }

    public void DeleteAll()
    {
        dao.deleteAll();
    }

    private class insertAsyncTask extends AsyncTask<Day,Void,Void>
    {
        DayDao mAsyncDao;
        public insertAsyncTask(List<Day> days,DayDao dao) {
            mAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(Day... days) {
            dao.InsertALL(days);
            return null;
        }
    }
}

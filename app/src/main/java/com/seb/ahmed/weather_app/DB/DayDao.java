package com.seb.ahmed.weather_app.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.seb.ahmed.weather_app.MainActivity.Models.Day;

import java.util.List;


@Dao
public interface DayDao {

    @Query("SELECT * FROM day")
    List<Day> getAllDays();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertALL(Day...days);

    @Query("DELETE FROM day")
    void deleteAll();

}

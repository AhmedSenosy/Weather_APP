package com.seb.ahmed.weather_app.MainActivity.Models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.seb.ahmed.weather_app.BR;


public class Current extends BaseObservable implements Parcelable {
    public static final Parcelable.Creator<Current> CREATOR = new Parcelable.Creator<Current>() {
        @Override
        public Current createFromParcel(Parcel source) {
            return new Current(source);
        }

        @Override
        public Current[] newArray(int size) {
            return new Current[size];
        }
    };
    @SerializedName("condition")
    private Condition condition;
    @SerializedName("temp_c")
    private double tempC;

    private String maxTemp;
    private String minTemp;

    public Current() {
    }

    protected Current(Parcel in) {
        this.condition = in.readParcelable(Condition.class.getClassLoader());
        this.tempC = in.readDouble();
    }
    @Bindable
    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public double getTempC() {
        return tempC;
    }

    public void setTempC(double tempC) {
        this.tempC = tempC;
    }
    @Bindable
    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
        notifyPropertyChanged(BR.maxTemp);
    }
    @Bindable
    public String getMinTemp() {
        return minTemp;

    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
        notifyPropertyChanged(BR.minTemp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.condition, flags);
        dest.writeDouble(this.tempC);
    }
}

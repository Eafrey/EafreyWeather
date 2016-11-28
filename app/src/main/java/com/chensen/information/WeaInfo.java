package com.chensen.information;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eafrey on 2016/11/25.
 */
public class WeaInfo implements Serializable{
    public BasicInfo basic;
    public String status;
    public AQIData aqi;
    public AlarmInfo alarms;
    public NowWeathInfo now;
    public List<DailyForecastInfo> daily_forecast;
    public List<HourlyForecastInfo> hourly_forecast;
    public Suggestion suggestion;


    public WeaInfo() {
        basic = new BasicInfo();
        aqi = new AQIData();
        alarms = new AlarmInfo();
        now = new NowWeathInfo();
        daily_forecast = new ArrayList<>();
        hourly_forecast = new ArrayList<>();
        suggestion = new Suggestion();

        for(int i=0; i<7; i++) {
            daily_forecast.add(new DailyForecastInfo());
        }

        for(int i=0; i<8; i++) {
            hourly_forecast.add(new HourlyForecastInfo());
        }
    }

    public AlarmInfo getAlarms() {
        return alarms;
    }

    public void setAlarms(AlarmInfo alarms) {
        this.alarms = alarms;
    }

    public AQIData getAqi() {
        return aqi;
    }

    public void setAqi(AQIData aqi) {
        this.aqi = aqi;
    }

    public BasicInfo getBasic() {
        return basic;
    }

    public void setBasic(BasicInfo basic) {
        this.basic = basic;
    }

    public List<DailyForecastInfo> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(List<DailyForecastInfo> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }

    public List<HourlyForecastInfo> getHourly_forecast() {
        return hourly_forecast;
    }

    public void setHourly_forecast(List<HourlyForecastInfo> hourly_forecast) {
        this.hourly_forecast = hourly_forecast;
    }

    public NowWeathInfo getNow() {
        return now;
    }

    public void setNow(NowWeathInfo now) {
        this.now = now;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Suggestion getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }
}

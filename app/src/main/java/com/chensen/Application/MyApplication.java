package com.chensen.Application;

import android.app.Application;

import com.baidu.apistore.sdk.ApiStoreSDK;
import com.chensen.information.AQIData;
import com.chensen.information.AlarmInfo;
import com.chensen.information.BasicInfo;
import com.chensen.information.Suggestion;
import com.chensen.information.DailyForecastInfo;
import com.chensen.information.HourlyForecastInfo;
import com.chensen.information.NowWeathInfo;

/**
 * Created by 陈森 on 2016/5/31.
 */
public class MyApplication extends Application {

    //status状态暂时还没加入
    private BasicInfo basicInfo = new BasicInfo();
    private AQIData aqiData = new AQIData();
    private AlarmInfo alarmInfo = new AlarmInfo();
    private NowWeathInfo nowWeathInfo = new NowWeathInfo();
    private DailyForecastInfo[] forecast7Day = new DailyForecastInfo[7];
    private HourlyForecastInfo[] forecastHour = new HourlyForecastInfo[8];
    private Suggestion brfSuggestion = new Suggestion();
    private Suggestion detailedSuggestion = new Suggestion();

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public AQIData getAqiData() {
        return aqiData;
    }

    public void setAqiData(AQIData aqiData) {
        this.aqiData = aqiData;
    }

    public AlarmInfo getAlarmInfo() {
        return alarmInfo;
    }

    public void setAlarmInfo(AlarmInfo alarmInfo) {
        this.alarmInfo = alarmInfo;
    }

    public NowWeathInfo getNowWeathInfo() {
        return nowWeathInfo;
    }

    public void setNowWeathInfo(NowWeathInfo nowWeathInfo) {
        this.nowWeathInfo = nowWeathInfo;
    }

    public DailyForecastInfo[] getForecast7Day() {
        return forecast7Day;
    }

    public void setForecast7Day(DailyForecastInfo[] forecast7Day) {
        this.forecast7Day = forecast7Day;
    }

    public HourlyForecastInfo[] getForecastHour() {
        return forecastHour;
    }

    public void setForecastHour(HourlyForecastInfo[] forecastHour) {
        this.forecastHour = forecastHour;
    }

    public Suggestion getBrfSuggestion() {
        return brfSuggestion;
    }

    public void setBrfSuggestion(Suggestion brfSuggestion) {
        this.brfSuggestion = brfSuggestion;
    }

    public Suggestion getDetailedSuggestion() {
        return detailedSuggestion;
    }

    public void setDetailedSuggestion(Suggestion detailedSuggestion) {
        this.detailedSuggestion = detailedSuggestion;
    }

    @Override
    public void onCreate() {
        //初始化部分
        ApiStoreSDK.init(this, "55d00e1b496a6ea15e3fe4edaf42b392");
        super.onCreate();
    }
}

package com.chensen.information;

/**
 * Created by chensen on 2016/6/16.
 */
public class SumInformation {

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

}

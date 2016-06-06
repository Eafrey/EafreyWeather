package com.chensen.Application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.chensen.eafreyweather.MainActivity;
import com.chensen.information.AQIData;
import com.chensen.information.AlarmInfo;
import com.chensen.information.BasicInfo;
import com.chensen.information.Suggestion;
import com.chensen.information.DailyForecastInfo;
import com.chensen.information.HourlyForecastInfo;
import com.chensen.information.NowWeathInfo;
import com.chensen.util.JSON2Java;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

        initData();
        //updateWeaData();

        super.onCreate();
    }

    private void updateWeaData() {
        SharedPreferences preferences = getSharedPreferences("weather_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Method[] methods1 = getBasicInfo().getClass().getDeclaredMethods();
        for(Method method : methods1) {
            if(method.getName().startsWith("get")) {
                try {
                    String s = (String) method.invoke(getBasicInfo());
                    String key = method.getName().substring(3, method.getName().length()).toLowerCase();

                    editor.putString(key, s);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        Method[] methods2 = getAqiData().getClass().getDeclaredMethods();
        for(Method method : methods2) {
            if(method.getName().startsWith("get")) {
                try {
                    String s = (String) method.invoke(getAqiData());
                    String key = method.getName().substring(3, method.getName().length()).toLowerCase();

                    editor.putString(key, s);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        Method[] methods3 = getAlarmInfo().getClass().getDeclaredMethods();
        for(Method method : methods3) {
            if(method.getName().startsWith("get")) {
                try {
                    String s = (String) method.invoke(getAlarmInfo());
                    String key = method.getName().substring(3, method.getName().length()).toLowerCase();

                    editor.putString(key, s);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        Method[] methods4 = getNowWeathInfo().getClass().getDeclaredMethods();
        for(Method method : methods4) {
            if(method.getName().startsWith("get")) {
                try {
                    String s = (String) method.invoke(getNowWeathInfo());
                    String key = method.getName().substring(3, method.getName().length()).toLowerCase();

                    editor.putString(key, s);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        DailyForecastInfo[] forecast7Day = getForecast7Day();
        for(int i=0; i<forecast7Day.length && forecast7Day[i] != null; i++) {
            Method[] methods = forecast7Day[i].getClass().getDeclaredMethods();
            for(Method method : methods) {
                if(method.getName().startsWith("get")) {
                    try {
                        String s = (String) method.invoke(getForecast7Day()[i]);
                        String key = method.getName().substring(3, method.getName().length()).toLowerCase() + i;

                        editor.putString(key, s);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        HourlyForecastInfo[] forecastHour = getForecastHour();
        for(int i=0; i<forecastHour.length && forecastHour[i] != null; i++) {
            Method[] methods = forecastHour[i].getClass().getDeclaredMethods();
            for(Method method : methods) {
                if(method.getName().startsWith("get")) {
                    try {
                        String s = (String) method.invoke(getForecastHour()[i]);
                        String key = method.getName().substring(3, method.getName().length()).toLowerCase() + i;

                        editor.putString(key, s);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        Method[] methods5 = getBrfSuggestion().getClass().getDeclaredMethods();
        for(Method method : methods5) {
            if(method.getName().startsWith("get")) {
                try {
                    String s = (String) method.invoke(getBrfSuggestion());
                    String key = method.getName().substring(3, method.getName().length()).toLowerCase() + "brf";

                    editor.putString(key, s);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        Method[] methods6 = getDetailedSuggestion().getClass().getDeclaredMethods();
        for(Method method : methods6) {
            if(method.getName().startsWith("get")) {
                try {
                    String s = (String) method.invoke(getDetailedSuggestion());
                    String key = method.getName().substring(3, method.getName().length()).toLowerCase() + "det";

                    editor.putString(key, s);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        editor.commit();
    }

    private void initData() {
        Parameters para = new Parameters();
        para.put("city", "xian");
        ApiStoreSDK.execute("http://apis.baidu.com/heweather/weather/free",
                ApiStoreSDK.GET,
                para,
                new ApiCallBack() {

                    @Override
                    public void onSuccess(int status, String responseString) {
                        Log.i("loadData", "onSuccess");

                        JSON2Java.JSON2Java(MainActivity.app, responseString);
                    }

                    @Override
                    public void onComplete() {
                        Log.i("loadData", "onComplete");
                    }

                    @Override
                    public void onError(int status, String responseString, Exception e) {
                        Log.i("loadData", "onError, status: " + status);
                        Log.i("loadData", "errMsg: " + (e == null ? "" : e.getMessage()));
                    }

                });

    }
}

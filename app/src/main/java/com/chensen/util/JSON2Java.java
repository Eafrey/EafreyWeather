package com.chensen.util;

import com.chensen.Application.MyApplication;
import com.chensen.information.AQIData;
import com.chensen.information.AlarmInfo;
import com.chensen.information.BasicInfo;
import com.chensen.information.Suggestion;
import com.chensen.information.DailyForecastInfo;
import com.chensen.information.HourlyForecastInfo;
import com.chensen.information.NowWeathInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by 陈森 on 2016/5/31.
 */
public class JSON2Java {

    public static void JSON2Java(MyApplication app,String JSONString) {
        //System.out.println(JSON);

        //获取中括号数组内的JSONObject
        JSONObject jo = JSONObject.fromObject(JSONString);

        JSONArray jArr = jo.getJSONArray("HeWeather data service 3.0");
        JSONObject weaJO = jArr.getJSONObject(0);

        //解析基本信息到basicInfo
        parseBasicInformation(app, weaJO);

        parseAQIData(app, weaJO);

        parseAlarmInfo(app, weaJO);

        parseNowWeathInfo(app, weaJO);
        
        parseForecast7Day(app, weaJO);
        
        parseForecastHour(app, weaJO);

        parseSuggestion(app, weaJO);
    }

    private static void parseSuggestion(MyApplication app, JSONObject weaJO) {
        JSONObject sugJO = weaJO.getJSONObject("suggestion");

        Suggestion detailed = new Suggestion();
        Suggestion brf = new Suggestion();
        Method[] methods = brf.getClass().getDeclaredMethods();

        for(Method method : methods) {
            if(method.getName().startsWith("set")) {
                try {
                    String metNam = method.getName();
                    String key = metNam.substring(3, metNam.length()).toLowerCase();

                    if(sugJO.containsKey(key)) {
                        method.invoke(brf, sugJO.getJSONObject(key).getString("brf"));
                        method.invoke(detailed, sugJO.getJSONObject(key).getString("txt"));
                    }
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        app.setBrfSuggestion(brf);
        app.setDetailedSuggestion(detailed);
    }

    private static void parseForecastHour(MyApplication app, JSONObject weaJO) {
        JSONArray foreacastArr = weaJO.getJSONArray("hourly_forecast");
        HourlyForecastInfo[] forecastHour = new HourlyForecastInfo[8];

        for(int i=0; i<foreacastArr.size(); i++) {
            JSONObject hourlyForecastJO = foreacastArr.getJSONObject(i);

            HourlyForecastInfo hourlyForecastInfo = new HourlyForecastInfo();
            Method[] methods = hourlyForecastInfo.getClass().getDeclaredMethods();

            for(Method method : methods) {
                if(method.getName().startsWith("set")) {
                    try {
                        String metNam = method.getName();
                        String key = metNam.substring(3, metNam.length()).toLowerCase();

                        if(hourlyForecastJO.containsKey(key) && !key.equals("wind")) {
                            method.invoke(hourlyForecastInfo, hourlyForecastJO.getString(key));
                        }
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

            JSONObject wind = hourlyForecastJO.getJSONObject("wind");
            hourlyForecastInfo.setDeg(wind.getString("deg"));
            hourlyForecastInfo.setDir(wind.getString("dir"));
            hourlyForecastInfo.setSc(wind.getString("sc"));
            hourlyForecastInfo.setSpd(wind.getString("spd"));


            forecastHour[i] = hourlyForecastInfo;

        }

        app.setForecastHour(forecastHour);
    }


    private static void parseForecast7Day(MyApplication app, JSONObject weaJO) {
        JSONArray foreacastArr = weaJO.getJSONArray("daily_forecast");
        DailyForecastInfo[] forecast7Day = new DailyForecastInfo[7];

        for(int i=0; i<foreacastArr.size(); i++) {
            JSONObject dailyForecastJO = foreacastArr.getJSONObject(i);

            DailyForecastInfo dailyForecastInfo = new DailyForecastInfo();
            Method[] methods = dailyForecastInfo.getClass().getDeclaredMethods();

            for(Method method : methods) {
                if(method.getName().startsWith("set")) {
                    try {
                        String metNam = method.getName();
                        String key = metNam.substring(3, metNam.length()).toLowerCase();

                        if(dailyForecastJO.containsKey(key) && !key.equals("astro")
                                && !key.equals("cond") && !key.equals("wind") && !key.equals("tmp")) {
                            method.invoke(dailyForecastInfo, dailyForecastJO.getString(key));
                        }
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            JSONObject cond = dailyForecastJO.getJSONObject("cond");
            dailyForecastInfo.setCode_d(cond.getString("code_d"));
            dailyForecastInfo.setTxt_d(cond.getString("txt_d"));
            dailyForecastInfo.setCode_n(cond.getString("code_n"));
            dailyForecastInfo.setTxt_n(cond.getString("txt_n"));

            JSONObject wind = dailyForecastJO.getJSONObject("wind");
            dailyForecastInfo.setDeg(wind.getString("deg"));
            dailyForecastInfo.setDir(wind.getString("dir"));
            dailyForecastInfo.setSc(wind.getString("sc"));
            dailyForecastInfo.setSpd(wind.getString("spd"));

            JSONObject astro = dailyForecastJO.getJSONObject("astro");
            dailyForecastInfo.setSr(astro.getString("sr"));
            dailyForecastInfo.setSs(astro.getString("ss"));

            JSONObject temp = dailyForecastJO.getJSONObject("tmp");
            dailyForecastInfo.setMax(temp.getString("max"));
            dailyForecastInfo.setMin(temp.getString("min"));


            forecast7Day[i] = dailyForecastInfo;

        }

        app.setForecast7Day(forecast7Day);
    }

    private static void parseNowWeathInfo(MyApplication app, JSONObject weaJO) {
        JSONObject nowJO = weaJO.getJSONObject("now");

        NowWeathInfo nowInfo = new NowWeathInfo();
        Method[] methods = nowInfo.getClass().getDeclaredMethods();

        for(Method method : methods) {
            if(method.getName().startsWith("set")) {
                try {
                    String metNam = method.getName();
                    String key = metNam.substring(3, metNam.length()).toLowerCase();

                    if(nowJO.containsKey(key) && !key.equals("cond") && !key.equals("wind")) {
                        method.invoke(nowInfo, nowJO.getString(key));
                    }
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        JSONObject cond = nowJO.getJSONObject("cond");
        nowInfo.setCode(cond.getString("code"));
        nowInfo.setTxt(cond.getString("txt"));

        JSONObject wind = nowJO.getJSONObject("wind");
        nowInfo.setDeg(wind.getString("deg"));
        nowInfo.setDir(wind.getString("dir"));
        nowInfo.setSc(wind.getString("sc"));
        nowInfo.setSpd(wind.getString("spd"));

        app.setNowWeathInfo(nowInfo);
    }

    //预警还没有测试，因为不是每天都有预警信息
    private static void parseAlarmInfo(MyApplication app, JSONObject weaJO) {
        if(weaJO.containsKey("alarms")) {
            JSONArray arr = weaJO.getJSONArray("alarms");
            JSONObject alarmJO = arr.getJSONObject(0);

            AlarmInfo alarmInfo = new AlarmInfo();
            Method[] methods = alarmInfo.getClass().getDeclaredMethods();

            for (Method method : methods) {
                if (method.getName().startsWith("set")) {
                    try {
                        String metNam = method.getName();
                        String key = metNam.substring(3, metNam.length()).toLowerCase();

                        if(alarmJO.containsKey(key)) {
                            method.invoke(alarmInfo, alarmJO.getString(key));
                        }
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

            app.setAlarmInfo(alarmInfo);
        }
    }

    private static void parseAQIData(MyApplication app, JSONObject weaJO) {
        JSONObject cityAQIJO = weaJO.getJSONObject("aqi").getJSONObject("city");

        AQIData aqiData = new AQIData();
        Method[] methods = aqiData.getClass().getDeclaredMethods();

        for(Method method : methods) {
            if(method.getName().startsWith("set")) {
                try {
                    String metNam = method.getName();
                    String key = metNam.substring(3, metNam.length()).toLowerCase();

                    if(cityAQIJO.containsKey(key)) {
                        method.invoke(aqiData, cityAQIJO.getString(key));
                    }
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        app.setAqiData(aqiData);
    }

    private static void parseBasicInformation(MyApplication app, JSONObject weaJO) {
        JSONObject basJO = weaJO.getJSONObject("basic");

        BasicInfo basInfo = new BasicInfo();
        Method[] methods = basInfo.getClass().getDeclaredMethods();

        for(Method method : methods) {
            if(method.getName().startsWith("set")) {
                try {
                    String metNam = method.getName();
                    String key = metNam.substring(3, metNam.length()).toLowerCase();

                    //System.out.println(key);
                    //method.setAccessible(true);

                    //注意比较字符串应该用equals方法
                    //equals比较String的值，==比较在内存中的地址
                    if(!key.equals("loc") && !key.equals("utc") && basJO.containsKey(key)) {
                        method.invoke(basInfo, basJO.getString(key));
                    }
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        basInfo.setLoc(basJO.getJSONObject("update").getString("loc"));
        basInfo.setUtc(basJO.getJSONObject("update").getString("utc"));

        app.setBasicInfo(basInfo);
    }
}


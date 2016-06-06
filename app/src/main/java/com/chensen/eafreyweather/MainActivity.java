package com.chensen.eafreyweather;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.*;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.chensen.Application.MyApplication;
import com.chensen.information.DailyForecastInfo;
import com.chensen.information.HourlyForecastInfo;
import com.chensen.information.Suggestion;
import com.chensen.util.JSON2Java;
import com.chensen.widget.MyGridView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static MyApplication app;

    private TextView mTextView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private MyGridView mSugGridView;
    private String[] sugs;
    private int[] icons;
    List<Map<String, Object>> mDataLists;
    private final static int SUG_NUM = 7;

    private android.support.v7.widget.Toolbar mToolbar;

    private SharedPreferences weatherPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    private void updateWeaData() {
        SharedPreferences preferences = getSharedPreferences("weather_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Method[] methods1 = app.getBasicInfo().getClass().getDeclaredMethods();
        for(Method method : methods1) {
            if(method.getName().startsWith("get")) {
                try {
                    String s = (String) method.invoke(app.getBasicInfo());
                    String key = method.getName().substring(3, method.getName().length()).toLowerCase();

                    editor.putString(key, s);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        Method[] methods2 = app.getAqiData().getClass().getDeclaredMethods();
        for(Method method : methods2) {
            if(method.getName().startsWith("get")) {
                try {
                    String s = (String) method.invoke(app.getAqiData());
                    String key = method.getName().substring(3, method.getName().length()).toLowerCase();

                    editor.putString(key, s);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        Method[] methods3 = app.getAlarmInfo().getClass().getDeclaredMethods();
        for(Method method : methods3) {
            if(method.getName().startsWith("get")) {
                try {
                    String s = (String) method.invoke(app.getAlarmInfo());
                    String key = method.getName().substring(3, method.getName().length()).toLowerCase();

                    editor.putString(key, s);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        Method[] methods4 = app.getNowWeathInfo().getClass().getDeclaredMethods();
        for(Method method : methods4) {
            if(method.getName().startsWith("get")) {
                try {
                    String s = (String) method.invoke(app.getNowWeathInfo());
                    String key = method.getName().substring(3, method.getName().length()).toLowerCase();

                    editor.putString(key, s);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        DailyForecastInfo[] forecast7Day = app.getForecast7Day();
        for(int i=0; i<forecast7Day.length && forecast7Day[i] != null; i++) {
            Method[] methods = forecast7Day[i].getClass().getDeclaredMethods();
            for(Method method : methods) {
                if(method.getName().startsWith("get")) {
                    try {
                        String s = (String) method.invoke(app.getForecast7Day()[i]);
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
        HourlyForecastInfo[] forecastHour = app.getForecastHour();
        for(int i=0; i<forecastHour.length && forecastHour[i] != null; i++) {
            Method[] methods = forecastHour[i].getClass().getDeclaredMethods();
            for(Method method : methods) {
                if(method.getName().startsWith("get")) {
                    try {
                        String s = (String) method.invoke(app.getForecastHour()[i]);
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

        Method[] methods5 = app.getBrfSuggestion().getClass().getDeclaredMethods();
        for(Method method : methods5) {
            if(method.getName().startsWith("get")) {
                try {
                    String s = (String) method.invoke(app.getBrfSuggestion());
                    String key = method.getName().substring(3, method.getName().length()).toLowerCase() + "brf";

                    editor.putString(key, s);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        Method[] methods6 = app.getDetailedSuggestion().getClass().getDeclaredMethods();
        for(Method method : methods6) {
            if(method.getName().startsWith("get")) {
                try {
                    String s = (String) method.invoke(app.getDetailedSuggestion());
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


    private void initUI() {
        if(app == null) {
            app = (MyApplication) getApplication();
        }

        mTextView = (TextView) findViewById(R.id.mTextView);

        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSugGridView = (MyGridView) findViewById(R.id.sug_grid_view);
        weatherPref = getSharedPreferences("weather_info", Context.MODE_PRIVATE);

        initToolbar();

        initSwipeRefreshLayout();

        initSugGridView();

    }

    private void initToolbar() {
        mToolbar.setLogo(R.drawable.icon);
        String city = app.getBasicInfo().getCity();
        mToolbar.setTitle(city);
        setSupportActionBar(mToolbar);
    }

    private void initSugGridView() {

        sugs = new String[7];

        sugs[0] =  "舒适度:" + weatherPref.getString("comfbrf", null);
        sugs[1] = "洗车指数:" + weatherPref.getString("cwbrf", null);
        sugs[2] = "穿衣指数:"+ weatherPref.getString("drsgbrf", null);
        sugs[3] = "感冒指数:"+ weatherPref.getString("flubrf", null);
        sugs[4] = "运动指数:"+ weatherPref.getString("sportbrf", null);
        sugs[5] = "旅游指数:"+ weatherPref.getString("travbrf", null);
        sugs[6] = "紫外线指数:"+ weatherPref.getString("uvbrf", null);

        icons = new int[]{R.drawable.ic_sunny, R.drawable.ic_sunny, R.drawable.ic_sunny, R.drawable.ic_sunny
                , R.drawable.ic_sunny, R.drawable.ic_sunny, R.drawable.ic_sunny};

        mDataLists = new ArrayList<Map<String, Object>>();
        for(int i=0;i<SUG_NUM;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icons[i]);
            map.put("text", sugs[i]);
            mDataLists.add(map);
        }

        String [] from ={"image", "text"};
        int [] to = {R.id.icon_sug_index, R.id.sug_txt};
        mSugGridView.setAdapter(new SimpleAdapter(this, mDataLists, R.layout.sug_item, from, to));

    }

    private void updateSugInfo() {
        /*if(app == null) {
            app = (MyApplication) getApplication();
        }*/

        Suggestion brfSug = app.getBrfSuggestion();


        sugs[0] =  "舒适度:" + brfSug.getComf();
        sugs[1] = "洗车指数:" + brfSug.getCw();
        sugs[2] = "穿衣指数:" + brfSug.getDrsg();
        sugs[3] = "感冒指数:" + brfSug.getFlu();
        sugs[4] = "运动指数:" + brfSug.getSport();
        sugs[5] = "旅游指数:" + brfSug.getTrav();
        sugs[6] = "紫外线指数:" + brfSug.getUv();


        for(int i=0;i<SUG_NUM;i++){
            mDataLists.get(i).put("text", sugs[i]);
        }
    }

    private void  initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //if(app == null) {
                  //  app = (MyApplication) getApplication();
                //}
                initData();

                updateSugInfo();

                SimpleAdapter sa = (SimpleAdapter) mSugGridView.getAdapter();
                sa.notifyDataSetChanged();
                //mSugGridView.invalidateViews();上一句或者这一句都可以更新
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
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

                        JSON2Java.JSON2Java(app, responseString);

                        String s = new String(testToView(app));
                        mTextView.setText(s);
                        updateWeaData();
                        //mTextView.setText(responseString);
                    }

                    private StringBuffer testToView(MyApplication app) {
                        StringBuffer sb = new StringBuffer();

                        Method[] methods1 = app.getBasicInfo().getClass().getDeclaredMethods();
                        for(Method method : methods1) {
                            if(method.getName().startsWith("get")) {
                                try {
                                    String metNam = method.getName();
                                    String s = (String) method.invoke(app.getBasicInfo());

                                    sb.append(method.getName().substring(3, method.getName().length()).toLowerCase());
                                    sb.append(":");
                                    sb.append(s);
                                    sb.append("\n");
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
                        sb.append('\n');

                        Method[] methods2 = app.getAqiData().getClass().getDeclaredMethods();
                        for(Method method : methods2) {
                            if(method.getName().startsWith("get")) {
                                try {
                                    String metNam = method.getName();
                                    String s = (String) method.invoke(app.getAqiData());
                                    sb.append(method.getName().substring(3, method.getName().length()).toLowerCase());
                                    sb.append(":");
                                    sb.append(s);
                                    sb.append("\n");
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
                        sb.append('\n');

                        Method[] methods3 = app.getAlarmInfo().getClass().getDeclaredMethods();
                        for(Method method : methods3) {
                            if(method.getName().startsWith("get")) {
                                try {
                                    String metNam = method.getName();
                                    String s = (String) method.invoke(app.getAlarmInfo());
                                    sb.append(method.getName().substring(3, method.getName().length()).toLowerCase());
                                    sb.append(":");
                                    sb.append(s);
                                    sb.append("\n");
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
                        sb.append('\n');

                        Method[] methods4 = app.getNowWeathInfo().getClass().getDeclaredMethods();
                        for(Method method : methods4) {
                            if(method.getName().startsWith("get")) {
                                try {
                                    String metNam = method.getName();
                                    String s = (String) method.invoke(app.getNowWeathInfo());
                                    sb.append(method.getName().substring(3, method.getName().length()).toLowerCase());
                                    sb.append(":");
                                    sb.append(s);
                                    sb.append("\n");
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
                        sb.append('\n');

                        DailyForecastInfo[] forecast7Day = app.getForecast7Day();
                        for(int i=0; i<forecast7Day.length && forecast7Day[i] != null; i++) {
                            Method[] methods = forecast7Day[i].getClass().getDeclaredMethods();
                            for(Method method : methods) {
                                if(method.getName().startsWith("get")) {
                                    try {
                                        String metNam = method.getName();
                                        String s = (String) method.invoke(forecast7Day[i]);
                                        sb.append(method.getName().substring(3, method.getName().length()).toLowerCase());
                                        sb.append(":");
                                        sb.append(s);
                                        sb.append("\n");
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
                            sb.append('\n');
                        }
                        sb.append('\n');

                        HourlyForecastInfo[] forecastHour = app.getForecastHour();
                        for(int i=0; i<forecastHour.length && forecastHour[i] != null; i++) {
                            Method[] methods = forecastHour[i].getClass().getDeclaredMethods();
                            for(Method method : methods) {
                                if(method.getName().startsWith("get")) {
                                    try {
                                        String metNam = method.getName();
                                        String s = (String) method.invoke(forecastHour[i]);
                                        sb.append(method.getName().substring(3, method.getName().length()).toLowerCase());
                                        sb.append(":");
                                        sb.append(s);
                                        sb.append("\n");
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
                            sb.append('\n');
                        }
                        sb.append('\n');

                        Method[] methods5 = app.getBrfSuggestion().getClass().getDeclaredMethods();
                        for(Method method : methods5) {
                            if(method.getName().startsWith("get")) {
                                try {
                                    String metNam = method.getName();
                                    String s1 = (String) method.invoke(app.getBrfSuggestion());
                                    String s2 = (String) method.invoke(app.getDetailedSuggestion());

                                    sb.append(method.getName().substring(3, method.getName().length()).toLowerCase());
                                    sb.append(":");
                                    sb.append(s1);
                                    sb.append("\n");
                                    sb.append(s2);
                                    sb.append("\n");
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
                        sb.append('\n');

                        return sb;
                    }

                    @Override
                    public void onComplete() {
                        Log.i("loadData", "onComplete");
                    }

                    @Override
                    public void onError(int status, String responseString, Exception e) {
                        Log.i("loadData", "onError, status: " + status);
                        Log.i("loadData", "errMsg: " + (e == null ? "" : e.getMessage()));
                        mTextView.setText("连接不到网络...");
                    }
                });


    }

}

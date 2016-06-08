package com.chensen.eafreyweather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
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

    //仅作测试用的一个文本
    private TextView mTextView;

    //官方用于实现下滑刷新的layout
    private SwipeRefreshLayout mSwipeRefreshLayout;

    //GridView，里面包括各种建议
    private MyGridView mSugGridView;
    private String[] sugs;
    private int[] icons;
    List<Map<String, Object>> mDataLists;
    private final static int SUG_NUM = 7;

    private android.support.v7.widget.Toolbar mToolbar;

    //用于存储天气信息
    private SharedPreferences weatherPref;

    //现在的温度，天气的图标表示，和温度范围
    private TextView mTmpNow;
    private ImageView mNowWeaIcon;
    private TextView mTmpRange;

    //toolbar上的城市名字
    private TextView mToolbarCityText;

    //总的linearlayout
    private LinearLayout mSumLinearLayout;

    //选择城市的按钮
    private ImageButton mCtiyPick;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    /**
     * 更新天气信息到SharedPreference
     */
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
                    String key = method.getName().substring(3, method.getName().length()).toLowerCase() + "now";

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
                        String key = method.getName().substring(3, method.getName().length()).toLowerCase() + "day" + i;

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
                        String key = method.getName().substring(3, method.getName().length()).toLowerCase() + "hour" + i;

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


        mTmpNow = (TextView) findViewById(R.id.id_tmp_now);
        mTmpRange = (TextView) findViewById(R.id.id_now_tmp_range);
        mNowWeaIcon = (ImageView) findViewById(R.id.id_icon_now_wea);

        mToolbarCityText = (TextView) findViewById(R.id.id_toolbar_city);

        mSumLinearLayout = (LinearLayout) findViewById(R.id.sum_layout);

        mCtiyPick = (ImageButton) findViewById(R.id.icon_city_pick);




        initToolbar();

        initSwipeRefreshLayout();

        updateSugGridView();
        String [] from ={"image", "text"};
        int [] to = {R.id.icon_sug_index, R.id.sug_txt};
        mSugGridView.setAdapter(new SimpleAdapter(this, mDataLists, R.layout.sug_item, from, to));

        updateNowWeather();

        initCityPick();

    }

    private void initCityPick() {
        mCtiyPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CityPickActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }


    private void updateNowWeather() {
        //获取并显示当前温度
        String tmp = weatherPref.getString("tmpnow", "--") + "℃";
        mTmpNow.setText(tmp);

        //显示或更新当天天气情况的图标,并且更新背景图片
        //后续应加入根据时间是白天或者晚上加载相应的图标和背景
        //后续素材的寻找，当前天气素材不够完整
        String weaTxt = weatherPref.getString("txtnow", "--");
        switch (weaTxt) {
            case "多云":
                mNowWeaIcon.setImageResource(R.drawable.c_duoyun);
                mSumLinearLayout.setBackgroundResource(R.drawable.bg_duoyun);
                break;
            case "阴":
                mNowWeaIcon.setImageResource(R.drawable.c_yin);
                mSumLinearLayout.setBackgroundResource(R.drawable.bg_wumai);
                break;
            case "晴":
                mNowWeaIcon.setImageResource(R.drawable.c_qing);
                mSumLinearLayout.setBackgroundResource(R.drawable.bg_qing);
                break;
            case "阵雨":
                mNowWeaIcon.setImageResource(R.drawable.c_zhenyu);
                mSumLinearLayout.setBackgroundResource(R.drawable.bg_yu);
                break;
            case "小雨":
                mNowWeaIcon.setImageResource(R.drawable.c_xiaoyu);
                mSumLinearLayout.setBackgroundResource(R.drawable.bg_yu);
                break;
            default:
                break;
        }

        //获取并显示当天天气情况和当天温度范围
        String dayTxt = weatherPref.getString("txt_dday0", "--");
        String nigTxt = weatherPref.getString("txt_nday0", "--");

        String tmpRange = weatherPref.getString("minday0", "--") + "~" + weatherPref.getString("maxday0", "--") + "℃";

        String txt = new String();
        if(dayTxt.equals(nigTxt)) {
            txt = dayTxt + " " + tmpRange;
        } else {
            txt = dayTxt + "转" + nigTxt + " " + tmpRange;
        }
        mTmpRange.setText(txt);

        //更新背景图片

    }

    private void initToolbar() {
        String city = weatherPref.getString("city", "city");
        mToolbarCityText.setText(city);

        //mToolbar.setNavigationIcon(R.drawable.icon);
        //setSupportActionBar(mToolbar);
    }

    private void updateSugGridView() {

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


    }



    private void  initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //if(app == null) {
                  //  app = (MyApplication) getApplication();
                //}
                //刷新数据，并写入SharedPreference
                initData();

                //更新view的显示
                updateView();

                SimpleAdapter sa = (SimpleAdapter) mSugGridView.getAdapter();
                //更新GridView里的内容，mSugGridView.invalidateViews()也可以更新
                sa.notifyDataSetChanged();

                //设置下滑刷新提示小圆圈 消失
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void updateView() {


        updateSugGridView();
        updateNowWeather();
    }

    /**
     * 从api中获取天气数据，将JSON转换到相应信息类里，而且写入SharedPreference
     */
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

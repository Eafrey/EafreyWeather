package com.chensen.eafreyweather;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.chensen.information.AQIData;
import com.chensen.information.DailyForecastInfo;
import com.chensen.information.WeaInfo;
import com.chensen.widget.MyGridView;
import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chensen.util.Hanzi2Pinyin.getPinYin;

/**
 * Created by 陈森 on 2016/8/30.
 */
public class MainPagerFragment extends Fragment {
    private static final String WEATHER_INFO = new String("weather_info_key");
    //仅作测试用的一个文本
    private TextView mTextView;

    public String city;

    //GridView，里面包括各种建议
    private MyGridView mSugGridView;
    private String[] sugs = new String[7];
    private int[] icons = new int[]{R.drawable.ic_sunny, R.drawable.ic_sunny, R.drawable.ic_sunny, R.drawable.ic_sunny
            , R.drawable.ic_sunny, R.drawable.ic_sunny, R.drawable.ic_sunny};
    ;
    List<Map<String, Object>> mDataLists = new ArrayList<>();;
    private final static int SUG_NUM = 7;

    //存储天气信息的总类
    public WeaInfo mSumInfo;

    //用于存储天气信息
    private SharedPreferences weatherPref;

    //现在的温度，天气的图标表示，和温度范围
    private TextView mTmpNow;
    private ImageView mNowWeaIcon;
    private TextView mTmpRange;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    //Fragnment的view
    private View view;

    //总的linearlayout
    private LinearLayout mSumLinearLayout;

    protected Context mContext;

    //aqi数据
    private TextView aqiBrfInfo;

    public  final String TAG = "WeatherFragment";

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        if(savedInstanceState != null) {
            mSumInfo = (WeaInfo) savedInstanceState.getSerializable("weather_info");
        } else {
            mSumInfo = new WeaInfo();
            refreshData();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onResume");

        view = inflater.inflate(R.layout.swipe_refesh_layout, container, false);

        findView();

        refreshData();

        initUI();

        refreshView();


        return view;
    }

    public MainPagerFragment() {
        super();
    }

    public static MainPagerFragment newInstance(String str) {
        MainPagerFragment mpf = new MainPagerFragment();
        mpf.city = str;

        Bundle bundle = new Bundle();
        //bundle.putSerializable(WEATHER_INFO, mSumInfo);
        mpf.setArguments(bundle);

        //mpf.setArguments(bundle);


        return  mpf;
    }


    private void  initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshAll();

                //设置下滑刷新提示小圆圈 消失
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void initUI() {

        refreshView();

        String [] from ={"image", "text"};
        int [] to = {R.id.icon_sug_index, R.id.sug_txt};

        mSugGridView.setAdapter(new SimpleAdapter(mContext, mDataLists, R.layout.item_sug, from, to));

        initSwipeRefreshLayout();
    }

    private void findView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.m_swipe_refresh_layout);

        mTextView = (TextView) view.findViewById(R.id.mTextView);

        mSugGridView = (MyGridView) view.findViewById(R.id.sug_grid_view);



        mTmpNow = (TextView) view.findViewById(R.id.id_tmp_now);
        mTmpRange = (TextView) view.findViewById(R.id.id_now_tmp_range);
        mNowWeaIcon = (ImageView) view.findViewById(R.id.id_icon_now_wea);

        //mToolbarCityText = (TextView) getActivity().findViewById(R.id.id_toolbar_city);

        mSumLinearLayout = (LinearLayout) getActivity().findViewById(R.id.sum_layout);

        aqiBrfInfo = (TextView) view.findViewById(R.id.aqi_info);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mSumInfo != null)
            outState.putSerializable("weather_info", (Serializable) mSumInfo);
    }

//    /**
//     * 更新天气信息到SharedPreference
//     * private void updateStoreWeaData() {
//     SharedPreferences preferences = mainActivity.weatherPref;
//     SharedPreferences.Editor editor = preferences.edit();
//
//     Method[] methods1 = info.getBasicInfo().getClass().getDeclaredMethods();
//     for(Method method : methods1) {
//     if(method.getName().startsWith("get")) {
//     try {
//     String s = (String) method.invoke(info.getBasicInfo());
//     String key = method.getName().substring(3, method.getName().length()).toLowerCase();
//
//     editor.putString(key, s);
//     } catch (InvocationTargetException e) {
//     e.printStackTrace();
//     } catch (IllegalAccessException e) {
//     e.printStackTrace();
//     }
//     }
//     }
//
//     Method[] methods2 = info.getAqiData().getClass().getDeclaredMethods();
//     for(Method method : methods2) {
//     if(method.getName().startsWith("get")) {
//     try {
//     String s = (String) method.invoke(info.getAqiData());
//     String key = method.getName().substring(3, method.getName().length()).toLowerCase();
//
//     editor.putString(key, s);
//     } catch (InvocationTargetException e) {
//     e.printStackTrace();
//     } catch (IllegalAccessException e) {
//     e.printStackTrace();
//     }
//     }
//     }
//
//     Method[] methods3 = info.getAlarmInfo().getClass().getDeclaredMethods();
//     for(Method method : methods3) {
//     if(method.getName().startsWith("get")) {
//     try {
//     String s = (String) method.invoke(info.getAlarmInfo());
//     String key = method.getName().substring(3, method.getName().length()).toLowerCase();
//
//     editor.putString(key, s);
//     } catch (InvocationTargetException e) {
//     e.printStackTrace();
//     } catch (IllegalAccessException e) {
//     e.printStackTrace();
//     }
//     }
//     }
//
//     Method[] methods4 = info.getNowWeathInfo().getClass().getDeclaredMethods();
//     for(Method method : methods4) {
//     if(method.getName().startsWith("get")) {
//     try {
//     String s = (String) method.invoke(info.getNowWeathInfo());
//     String key = method.getName().substring(3, method.getName().length()).toLowerCase() + "now";
//
//     editor.putString(key, s);
//     } catch (InvocationTargetException e) {
//     e.printStackTrace();
//     } catch (IllegalAccessException e) {
//     e.printStackTrace();
//     }
//     }
//     }
//
//     DailyForecastInfo[] forecast7Day = info.getForecast7Day();
//     for(int i=0; i<forecast7Day.length && forecast7Day[i] != null; i++) {
//     Method[] methods = forecast7Day[i].getClass().getDeclaredMethods();
//     for(Method method : methods) {
//     if(method.getName().startsWith("get")) {
//     try {
//     String s = (String) method.invoke(info.getForecast7Day()[i]);
//     String key = method.getName().substring(3, method.getName().length()).toLowerCase() + "day" + i;
//
//     editor.putString(key, s);
//     } catch (InvocationTargetException e) {
//     e.printStackTrace();
//     } catch (IllegalAccessException e) {
//     e.printStackTrace();
//     }
//     }
//     }
//     }
//     HourlyForecastInfo[] forecastHour = info.getForecastHour();
//     for(int i=0; i<forecastHour.length && forecastHour[i] != null; i++) {
//     Method[] methods = forecastHour[i].getClass().getDeclaredMethods();
//     for(Method method : methods) {
//     if(method.getName().startsWith("get")) {
//     try {
//     String s = (String) method.invoke(info.getForecastHour()[i]);
//     String key = method.getName().substring(3, method.getName().length()).toLowerCase() + "hour" + i;
//
//     editor.putString(key, s);
//     } catch (InvocationTargetException e) {
//     e.printStackTrace();
//     } catch (IllegalAccessException e) {
//     e.printStackTrace();
//     }
//     }
//     }
//     }
//
//     Method[] methods5 = info.getBrfSuggestion().getClass().getDeclaredMethods();
//     for(Method method : methods5) {
//     if(method.getName().startsWith("get")) {
//     try {
//     String s = (String) method.invoke(info.getBrfSuggestion());
//     String key = method.getName().substring(3, method.getName().length()).toLowerCase() + "brf";
//
//     editor.putString(key, s);
//     } catch (InvocationTargetException e) {
//     e.printStackTrace();
//     } catch (IllegalAccessException e) {
//     e.printStackTrace();
//     }
//     }
//     }
//
//     Method[] methods6 = info.getDetailedSuggestion().getClass().getDeclaredMethods();
//     for(Method method : methods6) {
//     if(method.getName().startsWith("get")) {
//     try {
//     String s = (String) method.invoke(info.getDetailedSuggestion());
//     String key = method.getName().substring(3, method.getName().length()).toLowerCase() + "det";
//
//     editor.putString(key, s);
//     } catch (InvocationTargetException e) {
//     e.printStackTrace();
//     } catch (IllegalAccessException e) {
//     e.printStackTrace();
//     }
//     }
//     }
//
//     editor.commit();
//     }
//     */

    private void refreshAqiBrfData() {
        String s;
        if(mSumInfo != null) {
            AQIData aqiData = mSumInfo.getAqi();
            //Log.d("aqiInfo", aqiData.getCity().getAqi() + "   " + aqiData.getCity().getQlty());
            s = aqiData.getCity().getAqi() + ":" +  aqiData.getCity().getQlty();
        } else {
            s = "qlty:aqi";
        }
        aqiBrfInfo.setText(s);
    }

    private void refreshNowWeather() {
        String weaTxt = new String();
        String dayTxt = "";
        String nigTxt = "";
        String tmp = new String();
        String tmpRange = new String();
        if(mSumInfo != null) {
            tmp = mSumInfo.getNow().getTmp() + "℃";
            weaTxt = mSumInfo.now.cond+ "";

            DailyForecastInfo m = mSumInfo.getDaily_forecast().get(0);
            dayTxt = m.getCond().getTxt_d();
            nigTxt = m.getCond().getTxt_n();
            tmpRange = m.getTmp().getMin() + "~" + m.getTmp().getMax() + "℃";
        } else {

            weaTxt = "--";
            dayTxt = "--";
            nigTxt = "--";
            tmp = "--℃";
            tmpRange = "--~--℃";
        }

        mTmpNow.setText(tmp);

        //显示或更新当天天气情况的图标,并且更新背景图片
        //后续应加入根据时间是白天或者晚上加载相应的图标和背景
        //后续素材的寻找，当前天气素材不够完整
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

        String txt = new String();
        if(dayTxt.equals(nigTxt)) {
            txt = dayTxt + " " + tmpRange;
        } else {
            txt = dayTxt + "转" + nigTxt + " " + tmpRange;
        }
        mTmpRange.setText(txt);

    }

    private void refreshSugGridView() {
        if(mSumInfo != null) {

            Log.d("sugComf", mSumInfo.getSuggestion().getComf().getBrf());

            sugs[0] = this.getActivity().getResources().getString(R.string.index_comf) + ":" + mSumInfo.getSuggestion().getComf().getBrf();
            sugs[1] = this.getActivity().getResources().getString(R.string.index_car_wash) + ":" + mSumInfo.getSuggestion().getCw().getBrf();
            sugs[2] = this.getActivity().getResources().getString(R.string.index_dress) + ":" + mSumInfo.getSuggestion().getDrsg().getBrf();
            sugs[3] = this.getActivity().getResources().getString(R.string.index_flu) + ":" + mSumInfo.getSuggestion().getFlu().getBrf();
            sugs[4] = this.getActivity().getResources().getString(R.string.index_sport) + ":" + mSumInfo.getSuggestion().getSport().getBrf();
            sugs[5] = this.getActivity().getResources().getString(R.string.index_trav) + ":" + mSumInfo.getSuggestion().getTrav().getBrf();
            sugs[6] = this.getActivity().getResources().getString(R.string.index_uv) + ":" + mSumInfo.getSuggestion().getUv().getBrf();
        } else {
            for(int i=0; i<sugs.length; i++) {
                sugs[i] = "请刷新";
            }
        }

        mDataLists.clear();

        for(int i=0;i<SUG_NUM;i++){
            Map<String, Object> map = new HashMap<>();
            map.put("image", icons[i]);
            map.put("text", sugs[i]);
            mDataLists.add(map);
        }


    }

    public void refreshAll() {
        //刷新数据，并写入SharedPreference
        refreshData();

        //更新view的显示
        //initUI();
        refreshView();


        SimpleAdapter sa = (SimpleAdapter) mSugGridView.getAdapter();
        //更新GridView里的内容，mSugGridView.invalidateViews()也可以更新
        sa.notifyDataSetChanged();
    }

    private void refreshView() {
        refreshAqiBrfData();

        refreshNowWeather();

        refreshSugGridView();
    }

    /**
     * 从api中获取天气数据，将JSON转换到相应信息类里，而且写入SharedPreference
     */
    private void refreshData() {

        //从汉字转为拼音，例如"西安市"转换为"xian"
        if(city.equals("请选择城市")) {
            return;
        }
        String brfCityName = city.substring(0, city.length()-1);
        String cityPinyin = getPinYin(brfCityName);

        Log.d("CityName", cityPinyin);

        Parameters para = new Parameters();

        para.put("city", cityPinyin);
        ApiStoreSDK.execute("http://apis.baidu.com/heweather/weather/free",
                ApiStoreSDK.GET,
                para,
                new ApiCallBack() {

                    @Override
                    public void onSuccess(int status, String responseString) {
                        Log.i("loadData", "onSuccess");

                        if(mSumInfo == null) {
                            mSumInfo = new WeaInfo();
                        }

                        int begin = responseString.indexOf('[');
                        int end = responseString.lastIndexOf(']');

                        String myJSON = responseString.substring(begin+1, end);

                        Log.d("myJSON", myJSON);

                        Gson gson = new Gson();
                        mSumInfo = gson.fromJson(myJSON, WeaInfo.class);

                        //JSON2Java.JSON2Java(mSumInfo, responseString);

                        //String s = new String(testToView(mSumInfo));
                        //mTextView.setText(s);
                        //updateStoreWeaData();
                        //mTextView.setText(responseString)//原始返回的JSON数据;
                    }

//                    private StringBuffer testToView(SumInformation app) {
//                        StringBuffer sb = new StringBuffer();
//
//                        Method[] methods1 = app.getBasicInfo().getClass().getDeclaredMethods();
//                        for(Method method : methods1) {
//                            if(method.getName().startsWith("get")) {
//                                try {
//                                    String metNam = method.getName();
//                                    String s = (String) method.invoke(app.getBasicInfo());
//
//                                    sb.append(method.getName().substring(3, method.getName().length()).toLowerCase());
//                                    sb.append(":");
//                                    sb.append(s);
//                                    sb.append("\n");
//                                } catch (IllegalAccessException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                } catch (IllegalArgumentException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                } catch (InvocationTargetException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                        sb.append('\n');
//
//                        Method[] methods2 = app.getAqiData().getClass().getDeclaredMethods();
//                        for(Method method : methods2) {
//                            if(method.getName().startsWith("get")) {
//                                try {
//                                    String metNam = method.getName();
//                                    String s = (String) method.invoke(app.getAqiData());
//                                    sb.append(method.getName().substring(3, method.getName().length()).toLowerCase());
//                                    sb.append(":");
//                                    sb.append(s);
//                                    sb.append("\n");
//                                } catch (IllegalAccessException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                } catch (IllegalArgumentException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                } catch (InvocationTargetException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                        sb.append('\n');
//
//                        Method[] methods3 = app.getAlarmInfo().getClass().getDeclaredMethods();
//                        for(Method method : methods3) {
//                            if(method.getName().startsWith("get")) {
//                                try {
//                                    String metNam = method.getName();
//                                    String s = (String) method.invoke(app.getAlarmInfo());
//                                    sb.append(method.getName().substring(3, method.getName().length()).toLowerCase());
//                                    sb.append(":");
//                                    sb.append(s);
//                                    sb.append("\n");
//                                } catch (IllegalAccessException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                } catch (IllegalArgumentException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                } catch (InvocationTargetException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                        sb.append('\n');
//
//                        Method[] methods4 = app.getNowWeathInfo().getClass().getDeclaredMethods();
//                        for(Method method : methods4) {
//                            if(method.getName().startsWith("get")) {
//                                try {
//                                    String metNam = method.getName();
//                                    String s = (String) method.invoke(app.getNowWeathInfo());
//                                    sb.append(method.getName().substring(3, method.getName().length()).toLowerCase());
//                                    sb.append(":");
//                                    sb.append(s);
//                                    sb.append("\n");
//                                } catch (IllegalAccessException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                } catch (IllegalArgumentException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                } catch (InvocationTargetException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                        sb.append('\n');
//
//                        DailyForecastInfo[] forecast7Day = app.getForecast7Day();
//                        for(int i=0; i<forecast7Day.length && forecast7Day[i] != null; i++) {
//                            Method[] methods = forecast7Day[i].getClass().getDeclaredMethods();
//                            for(Method method : methods) {
//                                if(method.getName().startsWith("get")) {
//                                    try {
//                                        String metNam = method.getName();
//                                        String s = (String) method.invoke(forecast7Day[i]);
//                                        sb.append(method.getName().substring(3, method.getName().length()).toLowerCase());
//                                        sb.append(":");
//                                        sb.append(s);
//                                        sb.append("\n");
//                                    } catch (IllegalAccessException e) {
//                                        // TODO Auto-generated catch block
//                                        e.printStackTrace();
//                                    } catch (IllegalArgumentException e) {
//                                        // TODO Auto-generated catch block
//                                        e.printStackTrace();
//                                    } catch (InvocationTargetException e) {
//                                        // TODO Auto-generated catch block
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                            sb.append('\n');
//                        }
//                        sb.append('\n');
//
//                        HourlyForecastInfo[] forecastHour = app.getForecastHour();
//                        for(int i=0; i<forecastHour.length && forecastHour[i] != null; i++) {
//                            Method[] methods = forecastHour[i].getClass().getDeclaredMethods();
//                            for(Method method : methods) {
//                                if(method.getName().startsWith("get")) {
//                                    try {
//                                        String metNam = method.getName();
//                                        String s = (String) method.invoke(forecastHour[i]);
//                                        sb.append(method.getName().substring(3, method.getName().length()).toLowerCase());
//                                        sb.append(":");
//                                        sb.append(s);
//                                        sb.append("\n");
//                                    } catch (IllegalAccessException e) {
//                                        // TODO Auto-generated catch block
//                                        e.printStackTrace();
//                                    } catch (IllegalArgumentException e) {
//                                        // TODO Auto-generated catch block
//                                        e.printStackTrace();
//                                    } catch (InvocationTargetException e) {
//                                        // TODO Auto-generated catch block
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                            sb.append('\n');
//                        }
//                        sb.append('\n');
//
//                        Method[] methods5 = app.getBrfSuggestion().getClass().getDeclaredMethods();
//                        for(Method method : methods5) {
//                            if(method.getName().startsWith("get")) {
//                                try {
//                                    String metNam = method.getName();
//                                    String s1 = (String) method.invoke(app.getBrfSuggestion());
//                                    String s2 = (String) method.invoke(app.getDetailedSuggestion());
//
//                                    sb.append(method.getName().substring(3, method.getName().length()).toLowerCase());
//                                    sb.append(":");
//                                    sb.append(s1);
//                                    sb.append("\n");
//                                    sb.append(s2);
//                                    sb.append("\n");
//                                } catch (IllegalAccessException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                } catch (IllegalArgumentException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                } catch (InvocationTargetException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                        sb.append('\n');
//
//                        return sb;
//                  }

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

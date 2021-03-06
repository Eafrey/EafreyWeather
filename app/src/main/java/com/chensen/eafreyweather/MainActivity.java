package com.chensen.eafreyweather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.chensen.information.WeaInfo;
import com.chensen.widget.MyGridView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class MainActivity extends FragmentActivity {
    //仅作测试用的一个文本
    private TextView mTextView;

    //官方用于实现下滑刷新的layout
    private SwipeRefreshLayout mSwipeRefreshLayout;

    //GridView，里面包括各种建议
    private MyGridView mSugGridView;
    private String[] sugs = new String[7];
    private int[] icons;
    List<Map<String, Object>> mDataLists;
    private final static int SUG_NUM = 7;

    private android.support.v7.widget.Toolbar mToolbar;

    //存储天气信息的总类
    public WeaInfo info = new WeaInfo();

    //用于存储天气信息
    public static SharedPreferences weatherPref;

    //现在的温度，天气的图标表示，和温度范围
    private TextView mTmpNow;
    private ImageView mNowWeaIcon;
    private TextView mTmpRange;

    //toolbar上的城市名字
    public static TextView mToolbarCityText;

    //总的linearlayout
    private LinearLayout mSumLinearLayout;

    public static int mSelCityNum = 0;

    //选择城市的按钮和设置按钮
    private ImageButton mCtiyPick;
    private ImageButton mSetting;

    //aqi数据
    private TextView aqiBrfInfo;

    private final int CITY_PICK_REQUEST = 0;;
    
    public static ViewPager mViewPager;


    public static List<String> mSelectedCities = new ArrayList<>();

    //当前选择城市的Fragment的集合
    public static List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置状态栏和导航栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        findView();



        initViewPager();

        //initSwipeRefreshLayout();
        //refreshData();

        initChangeActivity();
    }

    private void findView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mCtiyPick = (ImageButton) findViewById(R.id.icon_city_pick);
        mSetting = (ImageButton) findViewById(R.id.ic_toolbar_setting);
        mToolbarCityText = (TextView) findViewById(R.id.id_toolbar_city);
    }


    private void initViewPager() {
        weatherPref = getSharedPreferences("weather_info", Context.MODE_PRIVATE);

        String selectedCities = weatherPref.getString("selected_cities", null);
        if(selectedCities != null) {
            String[] mCityArray = selectedCities.split(" ");
            for(String city : mCityArray) {
                //city确保不被包含，且不等于缺省值
                if(!city.equals("缺省值") && !mSelectedCities.contains(city)) {
                    mSelectedCities.add(city);
                }
            }
        }

        if(mSelectedCities.isEmpty()) {
            fragmentList.add(MainPagerFragment.newInstance("请选择城市"));
        } else {
            for(int i=0; i<mSelectedCities.size(); i++) {
                fragmentList.add(MainPagerFragment.newInstance(mSelectedCities.get(i)));
            }

        }

        mViewPager.setOffscreenPageLimit(0);

        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager(), fragmentList));
        mViewPager.setCurrentItem(0);
        //mViewPager.setOnPageChangeListener(new MyPageChangedListener());
        mViewPager.addOnPageChangeListener(new MyPageChangedListener());
    }

    class MyPageChangedListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //用于第一次进应用时在toolbar上显示城市名称
            mToolbarCityText.setText(((MainPagerFragment)fragmentList.get(position)).city);
        }

        @Override
        public void onPageSelected(int position) {
            //((MainPagerFragment)fragmentList.get(position)).refreshAll();
            //用于完成滑动后，toolbar上城市的名称也随之改变
            mToolbarCityText.setText(((MainPagerFragment)fragmentList.get(position)).city);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            final String SCROLL_TAG = new String("ViewPageScroll");

            if(state == 0) {
                Log.e(SCROLL_TAG, "OnPageScrollState=0,do nothing");
            } else if(state == 1) {
                Log.e(SCROLL_TAG, "OnPageScrollState=1,scrolling");
            } else if(state == 2) {
                Log.e(SCROLL_TAG, "OnPageScrollState=0,scrolled");
            }
        }
    }

    class MyAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;

        public MyAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.fragmentList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    /*private void findView() {
        weatherPref = getSharedPreferences("weather_info", Context.MODE_PRIVATE);

        mTextView = (TextView) findViewById(R.id.mTextView);

        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        //mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.m_swipe_refresh_layout);
        mSugGridView = (MyGridView) findViewById(R.id.sug_grid_view);



        mTmpNow = (TextView) findViewById(R.id.id_tmp_now);
        mTmpRange = (TextView) findViewById(R.id.id_now_tmp_range);
        mNowWeaIcon = (ImageView) findViewById(R.id.id_icon_now_wea);

        mToolbarCityText = (TextView) findViewById(R.id.id_toolbar_city);

        mSumLinearLayout = (LinearLayout) findViewById(R.id.sum_layout);

        mCtiyPick = (ImageButton) findViewById(R.id.icon_city_pick);
        mSetting = (ImageButton) findViewById(R.id.ic_toolbar_setting);

        aqiBrfInfo = (TextView) findViewById(R.id.aqi_info);
        
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
    }*/

    /**
     * 更新天气信息到SharedPreference
     */
    /*private void updateStoreWeaData() {
        SharedPreferences preferences = getSharedPreferences("weather_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Method[] methods1 = info.getBasicInfo().getClass().getDeclaredMethods();
        for(Method method : methods1) {
            if(method.getName().startsWith("get")) {
                try {
                    String s = (String) method.invoke(info.getBasicInfo());
                    String key = method.getName().substring(3, method.getName().length()).toLowerCase();

                    editor.putString(key, s);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        Method[] methods2 = info.getAqiData().getClass().getDeclaredMethods();
        for(Method method : methods2) {
            if(method.getName().startsWith("get")) {
                try {
                    String s = (String) method.invoke(info.getAqiData());
                    String key = method.getName().substring(3, method.getName().length()).toLowerCase();

                    editor.putString(key, s);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        Method[] methods3 = info.getAlarmInfo().getClass().getDeclaredMethods();
        for(Method method : methods3) {
            if(method.getName().startsWith("get")) {
                try {
                    String s = (String) method.invoke(info.getAlarmInfo());
                    String key = method.getName().substring(3, method.getName().length()).toLowerCase();

                    editor.putString(key, s);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        Method[] methods4 = info.getNowWeathInfo().getClass().getDeclaredMethods();
        for(Method method : methods4) {
            if(method.getName().startsWith("get")) {
                try {
                    String s = (String) method.invoke(info.getNowWeathInfo());
                    String key = method.getName().substring(3, method.getName().length()).toLowerCase() + "now";

                    editor.putString(key, s);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        DailyForecastInfo[] forecast7Day = info.getForecast7Day();
        for(int i=0; i<forecast7Day.length && forecast7Day[i] != null; i++) {
            Method[] methods = forecast7Day[i].getClass().getDeclaredMethods();
            for(Method method : methods) {
                if(method.getName().startsWith("get")) {
                    try {
                        String s = (String) method.invoke(info.getForecast7Day()[i]);
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
        HourlyForecastInfo[] forecastHour = info.getForecastHour();
        for(int i=0; i<forecastHour.length && forecastHour[i] != null; i++) {
            Method[] methods = forecastHour[i].getClass().getDeclaredMethods();
            for(Method method : methods) {
                if(method.getName().startsWith("get")) {
                    try {
                        String s = (String) method.invoke(info.getForecastHour()[i]);
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

        Method[] methods5 = info.getBrfSuggestion().getClass().getDeclaredMethods();
        for(Method method : methods5) {
            if(method.getName().startsWith("get")) {
                try {
                    String s = (String) method.invoke(info.getBrfSuggestion());
                    String key = method.getName().substring(3, method.getName().length()).toLowerCase() + "brf";

                    editor.putString(key, s);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        Method[] methods6 = info.getDetailedSuggestion().getClass().getDeclaredMethods();
        for(Method method : methods6) {
            if(method.getName().startsWith("get")) {
                try {
                    String s = (String) method.invoke(info.getDetailedSuggestion());
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
    }*/


    private void initChangeActivity() {
        //initSwipeRefreshLayout();

        //refreshView();

        //String [] from ={"image", "text"};
        //int [] to = {R.id.icon_sug_index, R.id.sug_txt};
        //mSugGridView.setAdapter(new SimpleAdapter(this, mDataLists, R.layout.item_sug, from, to));

        initCityManage();
        initSetting();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CITY_PICK_REQUEST && resultCode == RESULT_OK) {
            String city = data.getStringExtra("city");

            mSelCityNum = data.getIntExtra("cityNum", 0);

            fragmentList.add(MainPagerFragment.newInstance(city));

            FragmentPagerAdapter ma = (FragmentPagerAdapter) mViewPager.getAdapter();
            ma.notifyDataSetChanged();
            mViewPager.setCurrentItem(fragmentList.size() - 1);

            //设置成当前城市

            //refreshAll();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*private void refreshAqiBrfData() {
        String s = weatherPref.getString("qlty","qlty") + ":" + weatherPref.getString("aqi", "aqi");
        aqiBrfInfo.setText(s);
    }*/

    private void initSetting() {
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initCityManage() {
        mCtiyPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CityManageAcitivity.class);
                startActivity(intent);
            }
        });
    }


    /*private void refreshNowWeather() {
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

    private void refreshToolbar() {
        String city = weatherPref.getString("city", "city");
        mToolbarCityText.setText(city);
    }

    private void refreshSugGridView() {


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


    }*/



    /*private void  initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshAll();


                //设置下滑刷新提示小圆圈 消失
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }*/

    /*private void refreshAll() {
        //刷新数据，并写入SharedPreference
        refreshData();

        //更新view的显示
        refreshView();

        SimpleAdapter sa = (SimpleAdapter) mSugGridView.getAdapter();
        //更新GridView里的内容，mSugGridView.invalidateViews()也可以更新
        sa.notifyDataSetChanged();
    }*/

    /*private void refreshView() {
        refreshToolbar();

        refreshAqiBrfData();

        refreshNowWeather();

        refreshSugGridView();
    }*/

    /**
     * 从api中获取天气数据，将JSON转换到相应信息类里，而且写入SharedPreference
     */
//    private void refreshData() {
//        Parameters para = new Parameters();
//
//        para.put("city", "xian");
//        ApiStoreSDK.execute("http://apis.baidu.com/heweather/weather/free",
//                ApiStoreSDK.GET,
//                para,
//                new ApiCallBack() {
//
//                    @Override
//                    public void onSuccess(int status, String responseString) {
//                        Log.i("loadData", "onSuccess");
//
//                        JSON2Java.JSON2Java(info, responseString);
//
//                        String s = new String(testToView(info));
//                        mTextView.setText(s);
//                        //updateStoreWeaData();
//                        //mTextView.setText(responseString);
//                    }
//
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
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.i("loadData", "onComplete");
//                    }
//
//                    @Override
//                    public void onError(int status, String responseString, Exception e) {
//                        Log.i("loadData", "onError, status: " + status);
//                        Log.i("loadData", "errMsg: " + (e == null ? "" : e.getMessage()));
//                        mTextView.setText("连接不到网络...");
//                    }
//                });
//
//
//    }



}

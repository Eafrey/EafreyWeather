package com.chensen.eafreyweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.chensen.Application.MyApplication;
import com.chensen.information.DailyForecastInfo;
import com.chensen.information.HourlyForecastInfo;
import com.chensen.util.JSON2Java;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    private MyApplication app;

    private TextView mTextView;
    private Button test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    private void initUI() {
        mTextView = (TextView) findViewById(R.id.mTextView);
        test = (Button) findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mTextView.setText("");

                app = (MyApplication) getApplication();
                initData();
            }

        });
    }

    private void initData() {
        Parameters para = new Parameters();

        para.put("city", "beijing");
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
                        mTextView.setText("连接不到网络");
                    }
                });


    }

}

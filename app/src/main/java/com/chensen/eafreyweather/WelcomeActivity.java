package com.chensen.eafreyweather;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.chensen.information.SumInformation;
import com.chensen.util.JSON2Java;


public class WelcomeActivity extends BaseActivity {
    //SumInformation info = new SumInformation();
    private static final int GOTO_MAIN_ACTIVITY = 0;
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case GOTO_MAIN_ACTIVITY:
                    Intent intent = new Intent();
                    intent.setClass(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;

                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page_activity);


        /*Parameters para = new Parameters();

        para.put("city", "xian");
        ApiStoreSDK.execute("http://apis.baidu.com/heweather/weather/free",
                ApiStoreSDK.GET,
                para,
                new ApiCallBack() {

                    @Override
                    public void onSuccess(int status, String responseString) {
                        Log.i("loadData", "onSuccess");

                        JSON2Java.JSON2Java(info, responseString);
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
                });*/

        //mHandler.sendEmptyMessage(GOTO_MAIN_ACTIVITY);
        mHandler.sendEmptyMessageDelayed(GOTO_MAIN_ACTIVITY, 1200);
        //mHandler.sendEmptyMessageAtTime(GOTO_MAIN_ACTIVITY, 5000);//3秒跳转
    }
}

package com.chensen.Application;

import android.app.Application;

import com.baidu.apistore.sdk.ApiStoreSDK;

/**
 * Created by 陈森 on 2016/5/31.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        //初始化部分
        ApiStoreSDK.init(this, "55d00e1b496a6ea15e3fe4edaf42b392");

        super.onCreate();
    }
}
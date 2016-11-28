package com.chensen.information;

import com.chensen.information.aqi.AQICity;

import java.io.Serializable;

/**
 * Created by 陈森 on 2016/5/31.
 */
public class AQIData implements Serializable {;
    public AQICity city;

    public AQIData() {
        city = new AQICity();
    }

    public AQICity getCity() {
        return city;
    }

    public void setCity(AQICity city) {
        this.city = city;
    }
}



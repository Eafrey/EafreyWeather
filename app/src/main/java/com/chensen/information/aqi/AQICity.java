package com.chensen.information.aqi;

import java.io.Serializable;

/**
 * Created by Eafrey on 2016/11/25.
 */
public class  AQICity implements Serializable{
    //空气质量指数
    public String aqi;
    //PM2.5 1小时平均值(ug/m³)
    public String pm25;
    //PM10 1小时平均值(ug/m³)
    public String pm10;
    //二氧化硫1小时平均值(ug/m³)
    public String so2;
    //二氧化氮1小时平均值(ug/m³)
    public String no2;
    //一氧化碳1小时平均值(ug/m³)
    public String co;
    //臭氧1小时平均值(ug/m³)
    public String o3;
    //空气质量类别
    public String qlty;

    public AQICity() {
        aqi = new String();
        pm25 = new String();
        pm10 = new String();
        so2 = new String();
        no2 = new String();
        co = new String();
        o3 = new String();
        qlty = new String();
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getQlty() {
        return qlty;
    }

    public void setQlty(String qlty) {
        this.qlty = qlty;
    }

    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2;
    }
}
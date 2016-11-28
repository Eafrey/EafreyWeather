package com.chensen.information;

import java.io.Serializable;

/**
 * Created by chensen on 2016/5/31.
 */
public class HourlyForecastInfo implements Serializable {
    public String date;
    //湿度（%）
    public String hum;
    //降水概率
    public String pop;
    //气压
    public String pres;
    //温度
    public String tmp;

    public HourlyForecastWind wind;

    public HourlyForecastInfo() {
        date = new String();
        hum = new String();
        pop = new String();
        pres = new String();
        tmp = new String();
        wind = new HourlyForecastWind();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }
}

class HourlyForecastWind  implements Serializable{
    //风向（角度）
    public String deg;
    //风向（方向）
    public String dir;
    //风力等级
    public String sc;
    //风速kmph
    public String spd;

    public HourlyForecastWind() {
        deg = new String();
        dir = new String();
        sc = new String();
        spd = new String();
    }

    public String getDeg() {
        return deg;
    }

    public void setDeg(String deg) {
        this.deg = deg;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getSpd() {
        return spd;
    }

    public void setSpd(String spd) {
        this.spd = spd;
    }
}
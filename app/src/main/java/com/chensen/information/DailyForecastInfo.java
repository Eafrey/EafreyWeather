package com.chensen.information;

import com.chensen.information.dailyforeacst.DailyForecastCond;
import com.chensen.information.dailyforeacst.DailyForecastTmp;

import java.io.Serializable;

/**
 * Created by chensen on 2016/5/31.
 */
public class DailyForecastInfo implements Serializable {
    public String date;

    public DailyForecastAstro astro;

    public DailyForecastCond cond;

    //湿度（%）
    public String hum;
    //降雨量（mm）
    public String pcpn;
    //气压
    public String pres;

    public DailyForecastTmp tmp;

    //能见度（km）
    public String vis;
    //降水概率
    public String pop;

    public DailyForecastWind wind;


    public DailyForecastInfo() {
        date = new String();
        hum = new String();
        pcpn = new String();
        pres = new String();
        vis = new String();
        pop = new String();
        astro = new DailyForecastAstro();
        cond = new DailyForecastCond();
        wind = new DailyForecastWind();
        tmp = new DailyForecastTmp();
    }

    public DailyForecastAstro getAstro() {
        return astro;
    }

    public void setAstro(DailyForecastAstro astro) {
        this.astro = astro;
    }

    public DailyForecastCond getCond() {
        return cond;
    }

    public void setCond(DailyForecastCond cond) {
        this.cond = cond;
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

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
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

    public DailyForecastTmp getTmp() {
        return tmp;
    }

    public void setTmp(DailyForecastTmp tmp) {
        this.tmp = tmp;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public DailyForecastWind getWind() {
        return wind;
    }

    public void setWind(DailyForecastWind wind) {
        this.wind = wind;
    }
}

class DailyForecastWind  implements Serializable{
    //风向（角度）
    public String deg;
    //风向（方向）
    public String dir;
    //风力等级
    public String sc;
    //风速kmph
    public String spd;

    public DailyForecastWind() {
        deg  = new String();
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



class DailyForecastAstro  implements Serializable{
    //日出时间
    public String sr;
    //日落时间
    public String ss;

    public DailyForecastAstro() {
        sr  = new String();
        ss = new String();
    }

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }
}

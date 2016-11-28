package com.chensen.information;

import java.io.Serializable;

/**
 * Created by chensen on 2016/5/31.
 */
public class NowWeathInfo implements Serializable {

    public NowWeaCond cond;

    //体感温度
    public String fl;
    //湿度（%）
    public String hum;
    //降雨量（mm）
    public String pcpn;

    //气压
    public String pres;
    //当前温度
    public String tmp;
    //能见度（km）
    public String vis;

    public NowWeaWind wind;

    public NowWeathInfo() {
        fl = new String();
        hum = new String();
        pcpn = new String();
        pres = new String();
        tmp = new String();
        vis = new String();
        cond = new NowWeaCond();
        wind = new NowWeaWind();
    }

    public NowWeaCond getCond() {
        return cond;
    }

    public void setCond(NowWeaCond cond) {
        this.cond = cond;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
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

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public NowWeaWind getWind() {
        return wind;
    }

    public void setWind(NowWeaWind wind) {
        this.wind = wind;
    }
}

class NowWeaCond  implements Serializable{
    //天气代码
    public String code;
    //天气描述
    public String txt;

    public NowWeaCond() {
        code = new String();
        txt = new String();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}

class NowWeaWind  implements Serializable{
    //风向（角度）
    public String deg;
    //风向（方向）
    public String dir;
    //风力等级
    public String sc;
    //风速kmph
    public String spd;

    public NowWeaWind() {
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

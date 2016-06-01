package com.chensen.information;

/**
 * Created by chensen on 2016/5/31.
 */
public class DailyForecastInfo {
    private String date;
    //日出时间
    private String sr;
    //日落时间
    private String ss;

    //白天天气代码与描述
    private String code_d;
    private String code_n;
    //夜晚天气代码与描述
    private String txt_d;
    private String txt_n;

    //湿度（%）
    private String hum;
    //降雨量（mm）
    private String pcpn;
    //气压
    private String pres;
    //最高温度和最低温度
    private String max;
    private String min;
    //能见度（km）
    private String vis;
    //降水概率
    private String pop;

    //风向（角度）
    private String deg;
    //风向（方向）
    private String dir;
    //风力等级
    private String sc;
    //风速kmph
    private String spd;

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    public String getCode_d() {
        return code_d;
    }

    public void setCode_d(String code_d) {
        this.code_d = code_d;
    }

    public String getCode_n() {
        return code_n;
    }

    public void setCode_n(String code_n) {
        this.code_n = code_n;
    }

    public String getTxt_d() {
        return txt_d;
    }

    public void setTxt_d(String txt_d) {
        this.txt_d = txt_d;
    }

    public String getTxt_n() {
        return txt_n;
    }

    public void setTxt_n(String txt_n) {
        this.txt_n = txt_n;
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

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
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

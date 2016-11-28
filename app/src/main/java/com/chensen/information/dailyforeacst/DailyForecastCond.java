package com.chensen.information.dailyforeacst;

import java.io.Serializable;

/**
 * Created by Eafrey on 2016/11/28.
 */
public class DailyForecastCond implements Serializable {
    //白天天气代码与描述
    public String code_d;
    public String code_n;
    //夜晚天气代码与描述
    public String txt_d;
    public String txt_n;

    public DailyForecastCond() {
        code_d = new String();
        code_n = new String();
        txt_d = new String();
        txt_n = new String();
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
}
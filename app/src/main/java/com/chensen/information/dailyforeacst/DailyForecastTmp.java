package com.chensen.information.dailyforeacst;

import java.io.Serializable;

/**
 * Created by Eafrey on 2016/11/28.
 */
public class DailyForecastTmp implements Serializable {
    //最高温度和最低温度
    public String max;
    public String min;

    public DailyForecastTmp() {
        max = new String();
        min = new String();
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
}
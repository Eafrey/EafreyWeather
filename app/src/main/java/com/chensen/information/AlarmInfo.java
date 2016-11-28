package com.chensen.information;

import java.io.Serializable;

/**
 * Created by 陈森 on 2016/5/31.
 */
public class AlarmInfo implements Serializable{
    public String level;
    //预警状态
    public String stat;
    public String title;
    //预警描述
    public String txt;
    public String type;

    public AlarmInfo() {
        level = new String();
        stat = new String();
        title = new String();
        txt = new String();
        type = new String();
    }

    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public String getStat() {
        return stat;
    }
    public void setStat(String stat) {
        this.stat = stat;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTxt() {
        return txt;
    }
    public void setTxt(String txt) {
        this.txt = txt;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

}

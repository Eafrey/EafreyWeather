package com.chensen.information;

/**
 * Created by 陈森 on 2016/5/31.
 */
public class AlarmInfo {
    private String level;
    //预警状态
    private String stat;
    private String title;
    //预警描述
    private String txt;
    private String type;
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

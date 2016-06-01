package com.chensen.information;

/**
 * Created by 陈森 on 2016/5/31.
 */
public class BasicInfo {
    private String city;
    //国家
    private String cnty;
    //城市ID
    private String id;
    //纬度
    private String lat;
    //经度
    private String lon;
    //数据更新的当地时间
    private String loc;
    //数据更新的utc时间
    private String utc;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCnty() {
        return cnty;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getUtc() {
        return utc;
    }

    public void setUtc(String utc) {
        this.utc = utc;
    }
}


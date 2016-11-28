package com.chensen.information;

import java.io.Serializable;

/**
 * Created by 陈森 on 2016/5/31.
 */
public class BasicInfo implements Serializable {
    public String city;
    //国家
    public String cnty;
    //城市ID
    public String id;
    //纬度
    public String lat;
    //经度
    public String lon;

    public BasicUpdateTime update;

    public BasicInfo() {
        city = new String();
        cnty = new String();
        id = new String();
        lat = new String();
        lon = new String();
        update = new BasicUpdateTime();
    }

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

}

class BasicUpdateTime  implements Serializable{
    //数据更新的当地时间
    public String loc;
    //数据更新的utc时间
    public String utc;

    public BasicUpdateTime() {
        loc = new String();
        utc = new String();
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


package com.chensen.information;

import com.chensen.information.suggestion.*;
import java.io.Serializable;

/**
 * Created by chensen on 2016/5/31.
 */
public class Suggestion implements Serializable {
    //舒适指数
    public SugComf comf;
    //洗车指数
    public SugCw cw;
    //穿衣指数
    public SugDrsg drsg;
    //感冒指数
    public SugFlu flu;
    //运动指数
    public SugSport sport;
    //旅游指数
    public SugTrav trav;
    //紫外线指数
    public SugUv uv;

    public Suggestion() {
        comf = new SugComf();
        cw = new SugCw();
        drsg = new SugDrsg();
        flu = new SugFlu();
        sport = new SugSport();
        trav = new SugTrav();
        uv = new SugUv();
    }

    public SugComf getComf() {
        return comf;
    }

    public void setComf(SugComf comf) {
        this.comf = comf;
    }

    public SugCw getCw() {
        return cw;
    }

    public void setCw(SugCw cw) {
        this.cw = cw;
    }

    public SugDrsg getDrsg() {
        return drsg;
    }

    public void setDrsg(SugDrsg drsg) {
        this.drsg = drsg;
    }

    public SugFlu getFlu() {
        return flu;
    }

    public void setFlu(SugFlu flu) {
        this.flu = flu;
    }

    public SugSport getSport() {
        return sport;
    }

    public void setSport(SugSport sport) {
        this.sport = sport;
    }

    public SugTrav getTrav() {
        return trav;
    }

    public void setTrav(SugTrav trav) {
        this.trav = trav;
    }

    public SugUv getUv() {
        return uv;
    }

    public void setUv(SugUv uv) {
        this.uv = uv;
    }
}
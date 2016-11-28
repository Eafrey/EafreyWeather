package com.chensen.information.suggestion;

import java.io.Serializable;

/**
 * Created by Eafrey on 2016/11/28.
 */
public class SugTrav  implements Serializable {
    String brf;
    String txt;

    public SugTrav() {
        brf = new String();
        txt = new String();
    }

    public String getBrf() {
        return brf;
    }

    public void setBrf(String brf) {
        this.brf = brf;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
package com.chensen.eafreyweather;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by 陈森 on 2016/8/26.
 */
public class SettingFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}

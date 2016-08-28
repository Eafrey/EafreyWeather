package com.chensen.eafreyweather;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Created by chensen on 2016/6/8.
 */
public class SettingActivity extends BaseActivity {
        Toolbar mToolbar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings_more);
            //ButterKnife.inject(this);
            initToolbar();
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingFragment()).commit();
        }


        /**
         * 初始化Toolbar
         */
        private void initToolbar() {
            mToolbar = (Toolbar) findViewById(R.id.toolbar_setting_more);
            mToolbar.setTitle("设置");
            mToolbar.setTitleTextColor(Color.WHITE);
            setSupportActionBar(mToolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeAsUpIndicator(R.drawable.activity_back);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        /**
         * 选项菜单
         */
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
                    return true;
            }
            return false;
        }
}

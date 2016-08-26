package com.chensen.eafreyweather;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import me.yokeyword.indexablelistview.IndexEntity;
import me.yokeyword.indexablelistview.IndexHeaderEntity;
import me.yokeyword.indexablelistview.IndexableAdapter;
import me.yokeyword.indexablelistview.IndexableStickyListView;

/**
 * Created by 陈森 on 2016/6/8.
 */
public class CityPickActivity extends BaseActivity {
    public static final String TAG = CityPickActivity.class.getSimpleName();

    private static final String EXTRA_LARGE = "extra_large";

    private IndexableStickyListView mIndexableStickyListView;
    private SearchView mSearchView;
    private Toolbar mToolBar;

    private CityAdapter mAdapter;
    private List<CityEntity> mCities = new ArrayList<>();

    private SharedPreferences weatherPref;


    //当前已选城市数量，名称
    private int cityNum;
    private static ArrayList<String> mSelectedCities = new ArrayList<>();

    private String[] mHotCities = new String[]{"北京市", "上海市", "深圳市", "西安市", "成都市"};

    public static Intent getCallingIntent(Context context, boolean isLarge) {
        Intent intent = new Intent(context, CityPickActivity.class);
        intent.putExtra(EXTRA_LARGE, isLarge);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_pick);

        initData();
        initView();
    }

    private void initData() {
        // 初始化数据
        initCities();

        // 128倍大数据
        if (getIntent().getBooleanExtra(EXTRA_LARGE, false)) {
            initCities();
            initCities();
            initCities();
            initCities();
            initCities();
            initCities();
            initCities();
        }

        //当前城市数据获取
        weatherPref = getSharedPreferences("weather_info", Context.MODE_PRIVATE);
        String selectedCities = weatherPref.getString("selected_cities", null);
        if(selectedCities != null) {
            String[] mCityArray = selectedCities.split(" ");
            for(String city : mCityArray) {
                mSelectedCities.add(city);
            }
        }
    }

     protected void initView() {
        mIndexableStickyListView = (IndexableStickyListView) findViewById(R.id.indexListView);
        mSearchView = (SearchView) findViewById(R.id.searchview);
        //mToolBar = (Toolbar) findViewById(R.id.toolbar_city_pick);

        mAdapter = new CityAdapter(this);
        mIndexableStickyListView.setAdapter(mAdapter);

        //initActionBar();

        bindData();

        setQuery();

        setItemClickListener();

    }

    private void setQuery() {
        // 搜索
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 委托处理搜索
                mIndexableStickyListView.searchTextChange(newText);
                return true;
            }
        });
    }

    private void initActionBar() {
        //actionbar标题和返回图表的颜色问题
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("选择城市");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void bindData() {
        // 当前城市列表
        //mSelectedCities.add("西安市");
        IndexHeaderEntity<CityEntity> citySelectedHeader = new IndexHeaderEntity<>();
        ArrayList<CityEntity> citySelectedEntityList = new ArrayList<>();
        if(!mSelectedCities.isEmpty()) {
            for(String city : mSelectedCities) {
                CityEntity nowEntity = new CityEntity();
                nowEntity.setName(city);
                citySelectedEntityList.add(nowEntity);
            }
        } else {
            CityEntity nowEntity = new CityEntity();
            nowEntity.setName("请选择城市");
            citySelectedEntityList.add(nowEntity);
        }
        citySelectedHeader.setHeaderTitle("当前城市");
        citySelectedHeader.setIndex("城");
        citySelectedHeader.setHeaderList(citySelectedEntityList);

        // 添加定位城市Header
        ArrayList<CityEntity> gpsIndexEntityList = new ArrayList<>();
        final CityEntity gpsEntity = new CityEntity();
        gpsEntity.setName("定位中...");
        gpsIndexEntityList.add(gpsEntity);
        IndexHeaderEntity<CityEntity> gpsHeader = new IndexHeaderEntity<>("定", "GPS自动定位", gpsIndexEntityList);

        // 添加热门城市Header
        IndexHeaderEntity<CityEntity> hotHeader = new IndexHeaderEntity<>();
        ArrayList<CityEntity> hotIndexEntityList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CityEntity hotEntity = new CityEntity();
            hotEntity.setName(mHotCities[i]);
            hotIndexEntityList.add(hotEntity);
        }

        hotHeader.setHeaderTitle("热门城市");
        hotHeader.setIndex("热");
        hotHeader.setHeaderList(hotIndexEntityList);

        // 绑定数据
        mIndexableStickyListView.bindDatas(mCities, citySelectedHeader, gpsHeader, hotHeader);

        // 模拟定位
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gpsEntity.setName("杭州市");
                mAdapter.notifyDataSetChanged();
            }
        }, 3000);
    }

    private void setItemClickListener() {
        mIndexableStickyListView.setOnItemContentClickListener(new IndexableStickyListView.OnItemContentClickListener() {
            @Override
            public void onItemClick(View v, IndexEntity indexEntity) {
                final CityEntity cityEntity = (CityEntity) indexEntity;
                String city = cityEntity.getName();
                //Toast.makeText(CityPickActivity.this, "选择了" + cityEntity.getName(), Toast.LENGTH_SHORT).show();
                if(!city.equals("请选择城市")) {
                    if(mSelectedCities.isEmpty() || !mSelectedCities.contains(city)) {
                        new AlertDialog.Builder(CityPickActivity.this).setTitle("选择城市").setMessage("添加" + city + "?").
                                setPositiveButton("添加", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent();
                                        intent.putExtra("city", cityEntity.getName());
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
                        mSelectedCities.add(city);

                        //写入SharedPreference
                        SharedPreferences.Editor editor = weatherPref.edit();
                        String s = weatherPref.getString("selected_cities", null) + " " + city;
                        editor.putString("selected_cities", s);
                        editor.commit();
                    }
                }
            }
        });

        mIndexableStickyListView.setOnItemTitleClickListener(new IndexableStickyListView.OnItemTitleClickListener() {
            @Override
            public void onItemClick(View v, String title) {
                //对城市栏目小标题的点击响应
                //Toast.makeText(CityPickActivity.this, "点击了" + title, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initCities() {
        List<String> cityStrings = Arrays.asList(getResources().getStringArray(R.array.city_array));
        for (String item : cityStrings) {
            CityEntity cityEntity = new CityEntity();
            cityEntity.setName(item);
            mCities.add(cityEntity);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}

class CityAdapter extends IndexableAdapter<CityEntity> {
    private Context mContext;

    public CityAdapter(Context context) {
        mContext = context;
    }

    @Override
    protected TextView onCreateTitleViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tv_title_city, parent, false);
        return (TextView) view.findViewById(R.id.tv_title);
    }

    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_city, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, CityEntity cityEntity) {
        CityViewHolder cityViewHolder = (CityViewHolder) holder;
        cityViewHolder.tvCity.setText(cityEntity.getName());
    }


    class CityViewHolder extends IndexableAdapter.ViewHolder {
        TextView tvCity;

        public CityViewHolder(View view) {
            super(view);
            tvCity = (TextView) view.findViewById(R.id.tv_name);
        }
    }
}

class CityEntity extends IndexEntity {
}

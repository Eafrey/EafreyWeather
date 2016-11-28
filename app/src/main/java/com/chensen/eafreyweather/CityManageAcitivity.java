package com.chensen.eafreyweather;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.chensen.eafreyweather.MainActivity.MyPageChangedListener;

import java.util.List;


public class CityManageAcitivity extends AppCompatActivity {
    //view
    private Toolbar mToolbar;
    private ListView mListView;
    private FloatingActionButton mFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manage_acitivity);

        findView();

        initView();
    }




    private void initView() {
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("编辑城市");
        setSupportActionBar(mToolbar);

        mListView.setAdapter(new MyAdapter(this, MainActivity.mSelectedCities));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int pos = i;
                new AlertDialog.Builder(CityManageAcitivity.this).setMessage("删除该城市？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int clickI) {
                        //移除相应的城市;
                        //Log.d("pos", ""+pos);
                        MainActivity.mSelectedCities.remove(pos);
                        /*MainActivity.fragmentList.get(pos).onPause();
                        MainActivity.fragmentList.get(pos).onStop();
                        MainActivity.fragmentList.get(pos).onDestroy();
                        MainActivity.fragmentList.get(pos).onDestroyView();
                        MainActivity.fragmentList.get(pos).onDetach();*/
                        MainActivity.fragmentList.remove(pos);

                        FragmentPagerAdapter fpa = (FragmentPagerAdapter) MainActivity.mViewPager.getAdapter();
                        fpa.notifyDataSetChanged();

                        //即时改变ListView的显示
                        BaseAdapter ba = (BaseAdapter) mListView.getAdapter();
                        ba.notifyDataSetChanged();

                        //更新SharedPreference
                        SharedPreferences.Editor editor = MainActivity.weatherPref.edit();
                        String s = new String();
                        for(int c=0; c<MainActivity.mSelectedCities.size(); c++) {
                            if(c == 0) {
                                s += MainActivity.mSelectedCities.get(c);
                            } else {
                                s += " " + MainActivity.mSelectedCities.get(c);
                            }
                        }
                        editor.putString("selected_cities", s);
                        editor.commit();

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create().show();

            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CityManageAcitivity.this, CityPickActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_city_manage);
        mListView = (ListView) findViewById(R.id.list_city_manage);
        mFab = (FloatingActionButton) findViewById(R.id.fab_city_manage);
    }


}

//ListView的自定义适配器
class MyAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<String> mDatas;
    private Context mContext;

    public MyAdapter(Context context,List<String> mDatas) {
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return MainActivity.mSelectedCities.size();
    }

    @Override
    public Object getItem(int i) {
        return MainActivity.mSelectedCities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if(view == null) {
            view = mInflater.inflate(R.layout.item_city_manage_list, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.city_manage_list_item_city_text);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.text.setText(mDatas.get(i));

        return view;
    }

    class ViewHolder {
        TextView text;
    }
}
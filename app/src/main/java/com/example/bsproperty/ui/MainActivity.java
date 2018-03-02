package com.example.bsproperty.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.fragment.Fragment02;
import com.example.bsproperty.fragment.Fragment03;
import com.example.bsproperty.fragment.Fragment04;
import com.example.bsproperty.fragment.HomeFragment;
import com.example.bsproperty.utils.SpUtils;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.tb_bottom)
    TabLayout tbBottom;

    @BindView(R.id.dl_layout)
    DrawerLayout dlLayout;
    @BindView(R.id.nv_view)
    NavigationView nvView;

    private TextView tv_01, tv_02, tv_name;

    private long backTime;
    private HomeFragment homeFragment;
    private Fragment02 fragment02;
    private Fragment03 fragment03;
    private Fragment04 fragment04;
    private ArrayList<Fragment> fragments;
    private MyFragmentPagerAdapter adapter;
    private String[] tabs = new String[]{
            "1", "2", "3"
    };

    @Override
    protected void initView(Bundle savedInstanceState) {

        homeFragment = new HomeFragment();
        fragment02 = new Fragment02();
        fragment03 = new Fragment03();
        fragments = new ArrayList<>();
        fragments.add(homeFragment);
        fragments.add(fragment02);
        fragments.add(fragment03);

        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        vpContent.setAdapter(adapter);

        tbBottom.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < fragments.size(); i++) {
            if (i == 0) {
                tbBottom.addTab(tbBottom.newTab().setText(tabs[i]), true);
            } else {
                tbBottom.addTab(tbBottom.newTab().setText(tabs[i]), false);
            }
        }
        tbBottom.setupWithViewPager(vpContent);

        initNav();
    }

    private void initNav() {
        tv_01 = (TextView) nvView.getHeaderView(0).findViewById(R.id.tv_01);
        tv_02 = (TextView) nvView.getHeaderView(0).findViewById(R.id.tv_02);
        tv_name = (TextView) nvView.getHeaderView(0).findViewById(R.id.tv_name);

        tv_01.setOnClickListener(new MyClickListener());
        tv_02.setOnClickListener(new MyClickListener());
    }

    private class MyClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            dlLayout.closeDrawers();
        }
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void loadData() {
        MyApplication.getInstance().setUserBean(SpUtils.getUserBean(this));
    }

    @Override
    public void onBackPressed() {
        if (dlLayout.isDrawerOpen(nvView)) {
            dlLayout.closeDrawers();
            return;
        }
        if (System.currentTimeMillis() - backTime < 2000) {
            super.onBackPressed();
        } else {
            showToast(this, "再按一次，退出程序");
            backTime = System.currentTimeMillis();
        }
        backTime = System.currentTimeMillis();
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }
}

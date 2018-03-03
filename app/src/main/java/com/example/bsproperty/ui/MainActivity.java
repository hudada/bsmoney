package com.example.bsproperty.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.fragment.Fragment02;
import com.example.bsproperty.fragment.Fragment01;
import com.example.bsproperty.fragment.Fragment04;
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

    private TextView tv_01, tv_02,tv_03,tv_04, tv_name;

    private long backTime;
    private HomeActivity homeFragment;
    private Fragment02 fragment02;
    private Fragment01 fragment01;
    private Fragment04 fragment04;
    private ArrayList<Fragment> fragments;
    private MyFragmentPagerAdapter adapter;
    private String[] tabs = new String[]{
            "账单", "图表", "发现"
    };

    @Override
    protected void initView(Bundle savedInstanceState) {
        MyApplication.getInstance().setUserBean(SpUtils.getUserBean(this));

        homeFragment = new HomeActivity();
        fragment02 = new Fragment02();
        fragment01 = new Fragment01();
        fragment04=new Fragment04();
        fragments = new ArrayList<>();
        fragments.add(fragment01);
        fragments.add(fragment02);
        fragments.add(fragment04);


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
        tv_03= (TextView) nvView.getHeaderView(0).findViewById(R.id.tv_03);
        tv_04= (TextView) nvView.getHeaderView(0).findViewById(R.id.tv_04);
        tv_name = (TextView) nvView.getHeaderView(0).findViewById(R.id.tv_name);

        if (MyApplication.getInstance().getUserBean() != null){
            tv_01.setVisibility(View.GONE);
            tv_name.setText(MyApplication.getInstance().getUserBean().getNumber());
        }else{
            tv_02.setVisibility(View.GONE);
        }

        tv_01.setOnClickListener(new MyClickListener());
        tv_02.setOnClickListener(new MyClickListener());
        tv_03.setOnClickListener(new MyClickListener());
        tv_04.setOnClickListener(new MyClickListener());
    }

    private class MyClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (v==tv_01){
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }else if(v==tv_02){
                startActivity(new Intent(MainActivity.this,EditPassActivity.class));
            }else if(v==tv_03){
                //TODO 换肤
            }else if (v == tv_04){
                if(SpUtils.cleanUserBean(MainActivity.this)){
                    System.exit(0);
                }

            }
            dlLayout.closeDrawers();
        }
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void loadData() {

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

    public void openLeft(){
        dlLayout.openDrawer(nvView);
    }
}

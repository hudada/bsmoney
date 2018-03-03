package com.example.bsproperty;

import android.app.Application;

import com.example.bsproperty.bean.UserBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by yezi on 2018/1/27.
 */

public class MyApplication extends Application {

    private static MyApplication instance;
    private static String[] outtypes={"餐饮","生活","学习","服装","其他"};
    private static String[] intypes={"工资","奖金","红包","其他"};
    private UserBean userBean;

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("hdd"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();

        OkHttpUtils.initClient(okHttpClient);


    }

    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();

        }
        return instance;
    }

    public static String[] getOuttypes() {
        return outtypes;
    }

    public static String[] getIntypes() {
        return intypes;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
}

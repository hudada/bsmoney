package com.example.bsproperty.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bsproperty.R;
import com.example.bsproperty.bean.UserBean;
import com.example.bsproperty.bean.UserInfoBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {


    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.et_repass)
    EditText etRepass;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTitle.setText("用户注册");
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.btn_back, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_register:
                String number = etNumber.getText().toString().trim();
                String pass = etPass.getText().toString().trim();
                String repass = etRepass.getText().toString().trim();

                if (TextUtils.isEmpty(number)) {
                    showToast(RegisterActivity.this, "账号不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    showToast(RegisterActivity.this, "密码不能为空！");
                    return;
                }
                if (!pass.equals(repass)) {
                    showToast(RegisterActivity.this, "两次密码输入不一致！");
                    return;
                }

                OkHttpTools.sendPost(RegisterActivity.this,ApiManager.REGISTER)
                        .addParams("number",number)
                        .addParams("pwd",pass)
                        .build()
                        .execute(new BaseCallBack<UserInfoBean>(RegisterActivity.this, UserInfoBean.class) {
                            @Override
                            public void onResponse(UserInfoBean userInfoBean) {
                                showToast(RegisterActivity.this,"注册成功");
                                finish();
                            }
                        });
                break;
        }
    }
}

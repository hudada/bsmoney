package com.example.bsproperty.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.bean.BaseResponse;
import com.example.bsproperty.bean.UserBean;
import com.example.bsproperty.bean.UserInfoBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditPassActivity extends BaseActivity {


    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.et_newpass)
    EditText etNewpass;
    @BindView(R.id.et_newpass2)
    EditText etNewpass2;
    private UserBean userBean;

    @Override
    protected void initView(Bundle savedInstanceState) {
        //TODO 获取用户名
//        userBean = MyApplication.getInstance().getUserBean();
        tvTitle.setText("修改密码");
        btnRight.setText("保存");
        if (userBean != null) {
//            tvNumber.setText(userBean.getNumber());
        }

    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_edit_pass;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.btn_back, R.id.btn_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                this.finish();
                break;
            case R.id.btn_right:

                String oldPass = etPass.getText().toString().trim();
                String newPass = etNewpass.getText().toString().trim();
                String newPass2 = etNewpass2.getText().toString().trim();

                if (TextUtils.isEmpty(oldPass)) {
                    showToast(this, "密码不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(newPass)) {
                    showToast(this, "新密码不能为空！");
                    return;
                }
                if (!newPass.equals(newPass2)) {
                    showToast(this, "两次密码输入不一致！");
                    return;
                }

                OkHttpTools.sendPost(EditPassActivity.this,ApiManager.MODIFYPWD)
                        .addParams("oldPass",oldPass)
                        .addParams("newPass",newPass)
                        .addParams("number",MyApplication.getInstance().getUserBean().getNumber())
                        .build()
                        .execute(new BaseCallBack<BaseResponse>(EditPassActivity.this,BaseResponse.class) {
                            @Override
                            public void onResponse(BaseResponse baseResponse) {
                                showToast(EditPassActivity.this, "修改成功");
                                finish();
                            }
                        });
                break;
        }
    }
}
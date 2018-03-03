package com.example.bsproperty.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.adapter.BaseAdapter;
import com.example.bsproperty.bean.CommentBean;
import com.example.bsproperty.bean.ReCommentBean;
import com.example.bsproperty.bean.ReCommentInfoBean;
import com.example.bsproperty.bean.ReCommentObjBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentInfoActivity extends BaseActivity {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.et_re)
    EditText etRe;
    @BindView(R.id.btn_re)
    Button btnRe;

    private CommentBean commentBean;
    private ArrayList<ReCommentBean> mData = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        rvList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this,R.layout.item_recomment,mData);
        rvList.setAdapter(adapter);
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_comment_info;
    }

    @Override
    protected void loadData() {
        mData.clear();
        commentBean = (CommentBean) getIntent().getSerializableExtra("data");
        tvTitle.setText("详情");

        tvName.setText(commentBean.getName());
        tvContent.setText(commentBean.getContent());
        Glide.with(this).load(ApiManager.IMG+commentBean.getImg()).into(ivImg);

        OkHttpTools.sendGet(this,ApiManager.RECOMMENT_LIST+commentBean.getId())
                .build()
                .execute(new BaseCallBack<ReCommentInfoBean>(this,ReCommentInfoBean.class) {
                    @Override
                    public void onResponse(ReCommentInfoBean reCommentInfoBean) {
                        if (reCommentInfoBean.getData() != null&& reCommentInfoBean.getData().size()>0){
                            mData.addAll(reCommentInfoBean.getData());
                            adapter.notifyDataSetChanged(mData);
                        }
                    }
                });
    }


    @OnClick({R.id.btn_back, R.id.btn_re})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_re:
                String re = etRe.getText().toString().trim();
                if (TextUtils.isEmpty(re)){
                    showToast(CommentInfoActivity.this,"请输入回复内容");
                    return;
                }
                OkHttpTools.sendPost(CommentInfoActivity.this,ApiManager.RECOMMENT_ADD)
                        .addParams("name", MyApplication.getInstance().getUserBean().getNumber())
                        .addParams("uid",MyApplication.getInstance().getUserBean().getId())
                        .addParams("time",System.currentTimeMillis()+"")
                        .addParams("content",re)
                        .addParams("pid",commentBean.getId()+"")
                        .build()
                        .execute(new BaseCallBack<ReCommentObjBean>(CommentInfoActivity.this,ReCommentObjBean.class) {
                            @Override
                            public void onResponse(ReCommentObjBean reCommentBean) {
                                showToast(CommentInfoActivity.this,"回复成功");
                                mData.add(reCommentBean.getData());
                                etRe.setText("");
                                adapter.notifyDataSetChanged(mData);
                            }
                        });
                break;
        }
    }

    private class MyAdapter extends BaseAdapter<ReCommentBean>{

        public MyAdapter(Context context, int layoutId, ArrayList<ReCommentBean> data) {
            super(context, layoutId, data);
        }

        @Override
        public void initItemView(BaseViewHolder holder, ReCommentBean reCommentBean, int position) {
            holder.setText(R.id.tv_01,reCommentBean.getName()+" 的回复");
            holder.setText(R.id.tv_02,format.format(new Date(reCommentBean.getTime())));
            holder.setText(R.id.tv_03,reCommentBean.getContent());
        }
    }
}

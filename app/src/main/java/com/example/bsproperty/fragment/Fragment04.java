package com.example.bsproperty.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.adapter.BaseAdapter;
import com.example.bsproperty.bean.CommentBean;
import com.example.bsproperty.bean.CommentInfoBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;
import com.example.bsproperty.ui.CommentInfoActivity;
import com.example.bsproperty.ui.LoginActivity;
import com.example.bsproperty.ui.SendCommentActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by yezi on 2018/1/27.
 */

public class Fragment04 extends BaseFragment {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.rv_list01)
    RecyclerView rvList01;
    @BindView(R.id.sl_list01)
    SwipeRefreshLayout slList01;

    private ArrayList<CommentBean> mData = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void loadData() {
        tvTitle.setText("发现");
        btnBack.setVisibility(View.INVISIBLE);
        btnRight.setText("发一条");
        btnRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        rvList01.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MyAdapter(mContext, R.layout.item_fr04_list, mData);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Object item, int position) {
                if (MyApplication.getInstance().getUserBean() == null){
                    startActivity(new Intent(mContext, LoginActivity.class));
                }else{
                    Intent intent = new Intent(mContext, CommentInfoActivity.class);
                    intent.putExtra("data",mData.get(position));
                    startActivity(intent);
                }
            }
        });
        slList01.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebData();
                slList01.setRefreshing(false);
            }
        });
        rvList01.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadWebData();
    }

    private void loadWebData() {
        mData.clear();
        OkHttpTools.sendGet(mContext, ApiManager.COMMENT_LIST)
                .build()
                .execute(new BaseCallBack<CommentInfoBean>(mContext, CommentInfoBean.class) {
                    @Override
                    public void onResponse(CommentInfoBean commentInfoBean) {
                        if (commentInfoBean.getData() != null && commentInfoBean.getData().size() > 0) {
                            mData.addAll(commentInfoBean.getData());
                            adapter.notifyDataSetChanged(mData);
                        }
                    }
                });
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_04;
    }

    @OnClick({R.id.btn_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_right:
                if (MyApplication.getInstance().getUserBean() == null){
                    startActivity(new Intent(mContext, LoginActivity.class));
                }else{
                    startActivity(new Intent(mContext, SendCommentActivity.class));
                }

                break;
        }
    }


    private class MyAdapter extends BaseAdapter<CommentBean> {

        public MyAdapter(Context context, int layoutId, ArrayList<CommentBean> data) {
            super(context, layoutId, data);
        }

        @Override
        public void initItemView(BaseViewHolder holder, CommentBean commentBean, int position) {
            holder.setText(R.id.tv_01, commentBean.getName() + "发布");
            holder.setText(R.id.tv_03, commentBean.getContent());
            holder.setText(R.id.tv_05, format.format(new Date(commentBean.getTime())));
            ImageView imageView = (ImageView) holder.getView(R.id.iv_img);
            if (TextUtils.isEmpty(commentBean.getImg())){
                imageView.setVisibility(View.GONE);
            }else {
                imageView.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(ApiManager.IMG + commentBean.getImg())
                        .into(imageView);
            }

        }
    }
}

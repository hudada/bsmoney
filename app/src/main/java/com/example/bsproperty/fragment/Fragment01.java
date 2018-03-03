package com.example.bsproperty.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.adapter.BaseAdapter;
import com.example.bsproperty.ui.HomeActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by yezi on 2018/1/27.
 */

public class Fragment01 extends BaseFragment {

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.btn_right)
    Button btn_right;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.sl_list)
    SwipeRefreshLayout slList;
    private Fragment01.MyAdapter adapter;
    private String[] outtypes;
    private String[] intypes;
    private ArrayList mData=null;


    @Override
    protected void loadData() {
        outtypes = MyApplication.getInstance().getOuttypes();
        intypes = MyApplication.getInstance().getIntypes();
        btn_right.setText("记一笔");

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_01;
    }


    @OnClick({R.id.tv_date, R.id.btn_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_date:
                final Calendar c2 = Calendar.getInstance();
                int year2 = c2.get(Calendar.YEAR);
                int monthOfYear2 = c2.get(Calendar.MONTH);
                int dayOfMonth2 = c2.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog2 = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv_date.setText((monthOfYear + 1) + "月" + dayOfMonth + "日");
                        getData();
                        slList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                loadData();
                                slList.setRefreshing(false);
                            }
                        });
                        rvList.setLayoutManager(new LinearLayoutManager(mContext));
                        // TODO mData为筛选后数据
                        adapter = new Fragment01.MyAdapter(mContext, R.layout.item_fr03_list, mData);
                        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View v, Object item, final int position) {
                                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder.setTitle("删除明细")
                                        .setMessage("是否删除当前明细？")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //TODO 删除当前明细
                                            }
                                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                builder.show();
                            }
                        });
                        rvList.setAdapter(adapter);
                    }
                }, year2, monthOfYear2, dayOfMonth2);
                datePickerDialog2.show();
                break;
            case R.id.btn_right:
                startActivityForResult(new Intent(mContext, HomeActivity.class), 521);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 521:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            switch (requestCode) {
                case 521:
                    break;
            }
        }

    }

    public void getData() {
        mData = new ArrayList<>();

    }

    // TODO 自己写记一笔的对象
    private class MyAdapter extends BaseAdapter<Object> {

        public MyAdapter(Context context, int layoutId, ArrayList<Object> data) {
            super(context, layoutId, data);
        }

        @Override
        public void initItemView(BaseViewHolder holder, Object type, int position) {
        }
    }
}

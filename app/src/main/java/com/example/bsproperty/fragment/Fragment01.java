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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.adapter.BaseAdapter;
import com.example.bsproperty.bean.BaseResponse;
import com.example.bsproperty.bean.SaveBean;
import com.example.bsproperty.bean.SaveInfoBean;
import com.example.bsproperty.bean.SaveListBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;
import com.example.bsproperty.ui.HomeActivity;
import com.example.bsproperty.ui.LoginActivity;
import com.example.bsproperty.ui.MainActivity;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    @BindView(R.id.btn_back)
    Button btn_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.sl_list)
    SwipeRefreshLayout slList;
    @BindView(R.id.tv_year)
    TextView tv_year;

    @BindView(R.id.tv_in)
    TextView tvIn;
    @BindView(R.id.tv_out)
    TextView tvOut;
    @BindView(R.id.tv_all)
    TextView tvAll;

    private Fragment01.MyAdapter adapter;
    private String[] outtypes;
    private String[] intypes;
    private ArrayList<SaveBean> mData=new ArrayList<>();

    private String mStart,mEnd;


    @Override
    protected void loadData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        loadWebData();
    }

    private void loadWebData() {

        mData.clear();
        if (TextUtils.isEmpty(mStart)){
            final Calendar c2 = Calendar.getInstance();
            int yearS = c2.get(Calendar.YEAR);
            int monthOfYearS = c2.get(Calendar.MONTH);
            int dayOfMonthS = c2.get(Calendar.DAY_OF_MONTH);

            int monthS = monthOfYearS + 1;
            mStart = yearS + "-" + monthS + "-" + dayOfMonthS;

            c2.add(Calendar.DATE,1);

            int yearE = c2.get(Calendar.YEAR);
            int monthOfYearE = c2.get(Calendar.MONTH);
            int dayOfMonthE = c2.get(Calendar.DAY_OF_MONTH);

            int monthE = monthOfYearE + 1;
            mEnd = yearE + "-" + monthE + "-" + dayOfMonthE;
        }

        String[] startStr = mStart.split("-");

        tv_date.setText(startStr[1]+"月"+startStr[2]+"日 ▼");

        long start = 0;
        long end = 0;
        try {
            start = format.parse(mStart).getTime();
            end = format.parse(mEnd).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (MyApplication.getInstance().getUserBean() == null){
            return;
        }
        OkHttpTools.sendGet(mContext, ApiManager.MONEY_LIST+start+"/"+end+"/"+MyApplication.getInstance().getUserBean().getId())
                .build()
                .execute(new BaseCallBack<SaveListBean>(mContext,SaveListBean.class) {
                    @Override
                    public void onResponse(SaveListBean saveInfoBean) {
                        if (saveInfoBean.getData()!= null && saveInfoBean.getData().getData().size() > 0) {
                            tvIn.setText(saveInfoBean.getData().getTotIn()+"");
                            tvOut.setText(saveInfoBean.getData().getTotOut()+"");
                            tvAll.setText(saveInfoBean.getData().getTotTwo()+"");
                            mData.addAll(saveInfoBean.getData().getData());
                            adapter.notifyDataSetChanged(mData);
                        }
                    }
                });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        outtypes = MyApplication.getInstance().getOuttypes();
        intypes = MyApplication.getInstance().getIntypes();
        btn_right.setText("记一笔");
        tv_title.setText("记    账");
        btn_back.setText("我  的");


        slList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebData();
                slList.setRefreshing(false);
            }
        });
        rvList.setLayoutManager(new LinearLayoutManager(mContext));

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
                                OkHttpTools.sendPost(mContext, ApiManager.DEL_MONEY)
                                        .addParams("id",mData.get(position).getId()+"")
                                        .build()
                                        .execute(new BaseCallBack<BaseResponse>(mContext, BaseResponse.class) {
                                            @Override
                                            public void onResponse(BaseResponse baseResponse) {
                                                mData.remove(position);
                                                adapter.notifyItemRemoved(position);
                                            }
                                        });
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

    @Override
    public int getRootViewId() {
        return R.layout.fragment_01;
    }


    @OnClick({R.id.tv_date, R.id.btn_right,R.id.btn_back})
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
                        int month = monthOfYear + 1;
                        tv_date.setText(month + "月" + dayOfMonth + "日");
                        tv_year.setText(year+"年");
                        mStart = year + "-" + month + "-" + dayOfMonth;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year,monthOfYear,dayOfMonth);
                        calendar.add(Calendar.DATE,1);
                        int yearE = calendar.get(Calendar.YEAR);
                        int monthOfYearE = calendar.get(Calendar.MONTH);
                        int dayOfMonthE = calendar.get(Calendar.DAY_OF_MONTH);

                        int monthE = monthOfYearE + 1;
                        mEnd = yearE + "-" + monthE + "-" + dayOfMonthE;
                        loadData();
                    }
                }, year2, monthOfYear2, dayOfMonth2);
                datePickerDialog2.show();
                break;
            case R.id.btn_right:
                if (MyApplication.getInstance().getUserBean() == null){
                    startActivityForResult(new Intent(mContext, LoginActivity.class), 521);
                }else{
                    startActivityForResult(new Intent(mContext, HomeActivity.class), 521);
                }
                break;
            case R.id.btn_back:
                ((MainActivity)getActivity()).openLeft();
                break;
        }
    }

    private class MyAdapter extends BaseAdapter<SaveBean> {

        public MyAdapter(Context context, int layoutId, ArrayList<SaveBean> data) {
            super(context, layoutId, data);
        }

        @Override
        public void initItemView(BaseViewHolder holder, SaveBean saveBean, int position) {
            holder.setText(R.id.tv_01,saveBean.getName());
            TextView money = (TextView) holder.getView(R.id.tv_02);
            if (saveBean.getFlag() == 0){
                money.setText("-"+saveBean.getMoney()+"元");
                money.setTextColor(getResources().getColor(R.color.green));
            }else{
                money.setText("+"+saveBean.getMoney()+"元");
                money.setTextColor(getResources().getColor(R.color.redd));
            }
            holder.setText(R.id.tv_03,"消费地点："+saveBean.getAddress());
            holder.setText(R.id.tv_05,"消费时间："+format.format(new Date(saveBean.getTime())));
        }
    }
}

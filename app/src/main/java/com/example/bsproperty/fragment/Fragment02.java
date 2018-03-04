package com.example.bsproperty.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.bean.StatisticsBean;
import com.example.bsproperty.bean.StatisticsInfoBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yezi on 2018/1/27.
 */

public class Fragment02 extends BaseFragment {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @BindView(R.id.pc_chart)
    PieChart mChart;
    @BindView(R.id.tv_date01)
    TextView tvDate01;
    @BindView(R.id.tv_date02)
    TextView tvDate02;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_back)
    Button btn_back;
    @BindView(R.id.btn_add)
    Button btn_add;
    @BindView(R.id.rb_01)
    RadioButton rb_01;
    @BindView(R.id.rb_02)
    RadioButton rb_02;
    @BindView(R.id.rb_03)
    RadioButton rb_03;

    private int type=0;

    long mStart = 0;
    long mEnd = 0;

    protected ArrayList<StatisticsBean> mParties = new ArrayList<>();

    @Override
    protected void loadData() {
        rb_01.setChecked(true);
        type=1;
        tv_title.setText("收支统计");
        btn_back.setText("");

        if (mStart ==0 && mEnd == 0){
            final Calendar c2 = Calendar.getInstance();

            tvDate01.setText(format.format(c2.getTime()));

            c2.add(Calendar.MONTH,1);

            tvDate02.setText(format.format(c2.getTime()));

            try {
                mStart=format.parse(tvDate01.getText().toString()).getTime();
                mEnd=format.parse(tvDate02.getText().toString()).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }




    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            setData();
        }
    }

    @OnClick({R.id.tv_date01, R.id.tv_date02, R.id.btn_add,R.id.rb_01,R.id.rb_02,R.id.rb_03})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_date01:
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int monthOfYear = c.get(Calendar.MONTH);
                int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (monthOfYear + 1 < 10 && dayOfMonth < 10) {
                            tvDate01.setText(year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth);
                        } else if (monthOfYear + 1 >= 10 && dayOfMonth < 10) {
                            tvDate01.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
                        } else if (monthOfYear + 1 < 10 && dayOfMonth >= 10) {
                            tvDate01.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
                        } else {
                            tvDate01.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                        try {
                            mStart=format.parse(tvDate01.getText().toString()).getTime();
                            mEnd=format.parse(tvDate02.getText().toString()).getTime();
                            setData();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, year, monthOfYear, dayOfMonth);
                datePickerDialog.show();
                break;
            case R.id.tv_date02:
                final Calendar c2 = Calendar.getInstance();
                int year2 = c2.get(Calendar.YEAR);
                int monthOfYear2 = c2.get(Calendar.MONTH);
                int dayOfMonth2 = c2.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog2 = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (monthOfYear + 1 < 10 && dayOfMonth < 10) {
                            tvDate02.setText(year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth);
                        } else if (monthOfYear + 1 >= 10 && dayOfMonth < 10) {
                            tvDate02.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
                        } else if (monthOfYear + 1 < 10 && dayOfMonth >= 10) {
                            tvDate02.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
                        } else {
                            tvDate02.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                        try {
                            mStart=format.parse(tvDate01.getText().toString()).getTime();
                            mEnd=format.parse(tvDate02.getText().toString()).getTime();
                            setData();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, year2, monthOfYear2, dayOfMonth2);
                datePickerDialog2.show();
                break;
            case R.id.btn_add:
                try {
                    mStart=format.parse(tvDate01.getText().toString()).getTime();
                    mEnd=format.parse(tvDate02.getText().toString()).getTime();
                    setData();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                break;
            case R.id.rb_01:
                type=1;
                setData();
                break;
            case R.id.rb_02:
                type=0;
                setData();
                break;
            case R.id.rb_03:
                type=-1;
                setData();
                break;
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(false);
        mChart.setHighlightPerTapEnabled(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e == null)
                    return;
                Log.i("VAL SELECTED",
                        "Value: " + e.getY() + ", index: " + h.getX()
                                + ", DataSet index: " + h.getDataSetIndex());
            }

            @Override
            public void onNothingSelected() {
                Log.i("PieChart", "nothing selected");
            }
        });


        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setWordWrapEnabled(true);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setXOffset(0f);

        mChart.setEntryLabelColor(Color.BLACK);
        mChart.setEntryLabelTextSize(12f);
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_02;
    }

    private void setData() {
        if (MyApplication.getInstance().getUserBean() == null){
            return;
        }
        OkHttpTools.sendGet(mContext, ApiManager.STATISTICS_TYPE+mStart+"/"+mEnd+"/"+type+"/"+ MyApplication.getInstance().getUserBean().getId())
                .build()
                .execute(new BaseCallBack<StatisticsInfoBean>(mContext,StatisticsInfoBean.class) {
                    @Override
                    public void onResponse(StatisticsInfoBean statisticsInfoBean) {
                        if (statisticsInfoBean.getData()!= null && statisticsInfoBean.getData().size() > 0) {
                            mParties = (ArrayList<StatisticsBean>) statisticsInfoBean.getData();
                        }else{
                            mParties.clear();
                        }
                        invalidate();
                    }
                });
    }

    private void invalidate() {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        for (StatisticsBean a : mParties) {
            entries.add(new PieEntry(Float.parseFloat(a.getMoney()),
                    a.getName() + " " + a.getMoney()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }
}

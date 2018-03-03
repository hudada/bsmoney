package com.example.bsproperty.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.ui.BaseActivity;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yezi on 2018/1/27.
 */

public class HomeActivity extends BaseActivity {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @BindView(R.id.tv_value)
    EditText tvValue;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_address)
    EditText tvAddress;
    @BindView(R.id.btn_right)
    Button btn_right;
    @BindView(R.id.rb_01)
    RadioButton rb_01;
    @BindView(R.id.rb_02)
    RadioButton rb_02;
    @BindView(R.id.rb_011)
    RadioButton rb_011;
    @BindView(R.id.rb_012)
    RadioButton rb_012;
    @BindView(R.id.rb_013)
    RadioButton rb_013;
    @BindView(R.id.rb_014)
    RadioButton rb_014;
    @BindView(R.id.rb_015)
    RadioButton rb_015;
    @BindView(R.id.rb_021)
    RadioButton rb_021;
    @BindView(R.id.rb_022)
    RadioButton rb_022;
    @BindView(R.id.rb_023)
    RadioButton rb_023;
    @BindView(R.id.rb_024)
    RadioButton rb_024;
    @BindView(R.id.rl_group02)
    RelativeLayout rl_group02;
    @BindView(R.id.rl_group03)
    RelativeLayout rl_group03;
    boolean isZhichu=true;
    String type="";


    @Override
    protected void loadData() {
        rb_01.setChecked(true);
        isZhichu=true;
        rb_011.setChecked(true);
        type="餐饮";
        rl_group02.setVisibility(View.VISIBLE);
        rl_group03.setVisibility(View.GONE);
        String[] time = format.format(new Date()).split(" ");
        tvDate.setText(time[0]);
        tvTime.setText(time[1]);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_add;
    }


    @OnClick({R.id.tv_time,R.id.btn_right,R.id.btn_back,R.id.tv_date,R.id.rb_01,R.id.rb_02,R.id.rb_011,R.id.rb_012,R.id.rb_013,R.id.rb_014,R.id.rb_015})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case  R.id.tv_date:
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int monthOfYear = c.get(Calendar.MONTH);
                int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (monthOfYear + 1 < 10 && dayOfMonth < 10) {
                            tvDate.setText(year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth);
                        } else if (monthOfYear + 1 >= 10 && dayOfMonth < 10) {
                            tvDate.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
                        } else if (monthOfYear + 1 < 10 && dayOfMonth >= 10) {
                            tvDate.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
                        } else {
                            tvDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                    }
                }, year, monthOfYear, dayOfMonth);
                datePickerDialog.show();
                break;
            case R.id.tv_time:
                final Calendar c2 = Calendar.getInstance();
                final int hour = c2.get(Calendar.HOUR_OF_DAY);
                int minute = c2.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay < 10 && minute < 10) {
                            tvTime.setText("0" + hourOfDay + ":0" + minute + ":00");
                        } else if (hourOfDay >= 10 && minute < 10) {
                            tvTime.setText(hourOfDay + ":0" + minute + ":00");
                        } else if (hourOfDay < 10 && minute >= 10) {
                            tvTime.setText("0" + hourOfDay + ":" + minute + ":00");
                        } else {
                            tvTime.setText(hourOfDay + ":" + minute + ":00");
                        }
                    }
                }, hour, minute, true);
                timePickerDialog.show();
                break;
            case R.id.btn_right:
                if (TextUtils.isEmpty(tvValue.getText())) {
                    Toast.makeText(this, "请输入正确金额！", Toast.LENGTH_SHORT).show();
                    return;
                }

                DecimalFormat fd=new DecimalFormat("00.00");
                String time = tvDate.getText().toString() + " " + tvTime.getText().toString();
                Date d = new Date();
                try {
                    d = format.parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // TODO d.getTime() 就是转换好的时间戳
                Toast.makeText(this, "时间戳："+d.getTime(), Toast.LENGTH_SHORT).show();
                // TODO 添加一笔
                break;
            case R.id.btn_back:
                setResult(RESULT_OK);
                this.finish();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 521:
                    break;
                case 109:
                    break;
            }
        }
    }
}

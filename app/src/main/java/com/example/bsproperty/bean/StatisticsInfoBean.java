package com.example.bsproperty.bean;

import java.util.List;

/**
 * Created by wdxc1 on 2018/3/3.
 */

public class StatisticsInfoBean extends BaseResponse {

    private List<StatisticsBean> data;

    public List<StatisticsBean> getData() {
        return data;
    }

    public void setData(List<StatisticsBean> data) {
        this.data = data;
    }
}

package com.example.bsproperty.bean;

import java.util.List;

/**
 * Created by wdxc1 on 2018/3/3.
 */

public class SaveInfoBean {
    private List<SaveBean> data;
    private Double totOut;
    private Double totIn;
    private Double totTwo;

    public List<SaveBean> getData() {
        return data;
    }

    public void setData(List<SaveBean> data) {
        this.data = data;
    }

    public Double getTotOut() {
        return totOut;
    }

    public void setTotOut(Double totOut) {
        this.totOut = totOut;
    }

    public Double getTotIn() {
        return totIn;
    }

    public void setTotIn(Double totIn) {
        this.totIn = totIn;
    }

    public Double getTotTwo() {
        return totTwo;
    }

    public void setTotTwo(Double totTwo) {
        this.totTwo = totTwo;
    }
}

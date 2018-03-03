package com.example.bsproperty.bean;

import java.util.List;

/**
 * Created by wdxc1 on 2018/3/4.
 */

public class ReCommentInfoBean extends BaseResponse {
    private List<ReCommentBean> data;

    public List<ReCommentBean> getData() {
        return data;
    }

    public void setData(List<ReCommentBean> data) {
        this.data = data;
    }
}

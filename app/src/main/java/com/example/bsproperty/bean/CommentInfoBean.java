package com.example.bsproperty.bean;

import java.util.List;

/**
 * Created by wdxc1 on 2018/3/4.
 */

public class CommentInfoBean extends BaseResponse {
    private List<CommentBean> data;

    public List<CommentBean> getData() {
        return data;
    }

    public void setData(List<CommentBean> data) {
        this.data = data;
    }
}

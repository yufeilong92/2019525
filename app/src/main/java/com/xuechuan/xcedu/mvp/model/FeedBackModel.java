package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 提交反馈
 * @author: L-BackPacker
 * @date: 2018/5/22 10:10
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface FeedBackModel {
    public void submitFeedBack(Context context, String suggest, String phone, RequestResulteView view);
}

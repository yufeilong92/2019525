package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 我的错题
 * @author: L-BackPacker
 * @date: 2018/5/3 10:31
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface ErrorTextModel {
    public void requestErrorNumber(Context context, String  courseid, String tagtype, RequestResulteView view);
    public void reqeustErrOrCoNumber(Context context, String courseid, RequestResulteView view);
}

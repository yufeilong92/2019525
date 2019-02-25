package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 根据 tagid 获取下面所有题号
 * @author: L-BackPacker
 * @date: 2018/5/5 19:01
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface SpecailDetailModel {
    public void requestSpecailDetail(Context context, String courseid, String  tagid, RequestResulteView view);
}

package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.google.android.exoplayer.C;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/2 12:59
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface LoginModel {
    public void getLoginContent(Context context, String code, RequestResulteView view);

    public void getPawLoginContent(Context context,String username,String paw,RequestResulteView view);
}

package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 搜索
 * @author: L-BackPacker
 * @date: 2018/5/8 17:39
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface SearchModel {
    public void reqeustHostContent(Context context, String num, RequestResulteView view);
    public void reqeustResultContent(Context context,int page, String key,String usetype, RequestResulteView view);

}

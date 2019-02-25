package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 错题集合
 * @author: L-BackPacker
 * @date: 2018/5/7 18:50
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface ErrOrColListModel {
    public void reqeustOneEvalueContent(Context context,String courseid, String tagid, String tagtype, RequestResulteView view);

}

package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 网课下载视频提交
 * @author: L-BackPacker
 * @date: 2018/5/21 9:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface NetDownIngModel {
    public void submitBookDownProgress(Context context, String videoOid, String clssId, RequestResulteView view);
}

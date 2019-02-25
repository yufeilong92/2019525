package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 获取网课评价
 * @author: L-BackPacker
 * @date: 2018/5/21 14:42
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface NetVideoEvalueInfomModel {
    public void requestVideoEvalueContent(Context context, String videoid,String commentid, int page, RequestResulteView view);

    public void submitEvalueVideo(Context context, String videoId, String con, String commentid, RequestResulteView view);
}

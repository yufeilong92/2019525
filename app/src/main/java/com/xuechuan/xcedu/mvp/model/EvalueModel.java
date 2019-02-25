package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/3 15:51
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface  EvalueModel  {
    public void SubmitContent(Context context, String targetid, String comment, String commentid,String usetype, RequestResulteView view);
    public void reqeustOneEvalueContent(Context context, int page,String questionid, RequestResulteView view);
    public void reqeustEvalueContentNew(Context context, int page,String targettype,String targetid, RequestResulteView view);

}

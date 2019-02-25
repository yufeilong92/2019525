package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/6/1 10:28
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface InfomDetailContract {
    interface Model {
        public void requestGetDetail(Context context, String atricleid, RequestResulteView view);

        public void requestEvalueDetail(Context context,int page, String targettype, String targetid,RequestResulteView view);
    }

    interface View {
        public void GetDetailSuccess(String con);

        public void GetDetailError(String con);

        public void EvalueMoreDetail(String con);

        public void EvalueMoreDetailErr(String con);

        public void EvalueDetail(String con);

        public void EvalueDetailErr(String con);

    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void requestGetDetail(Context context, String atricleid);

        public void requestEvalueDetail(Context context,int page, String targettype, String targetid);

        public void requestEvalueMoreDetail(Context context,int page, String targettype, String targetid);
    }
}

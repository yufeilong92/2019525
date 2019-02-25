package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.03 下午 3:11
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public interface GmSubmitComContract {
    interface Model {
        public void submiteCommont(Context context, String tagetid, String comment, String commenttid, String usertype, RequestResulteView resulteView);
    }

    interface View {
        public void SubmitSuccess(String success);

        public void SubmiteError(String msg);
    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void submiteCommont(Context context, String tagetid, String comment, String commenttid, String usertype);
    }
}

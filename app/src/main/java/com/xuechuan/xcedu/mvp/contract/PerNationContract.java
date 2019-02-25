package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: 我的通知
 * @author: L-BackPacker
 * @date: 2018/5/25 16:55
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface PerNationContract {
    interface Model {
        public void requestNotification(Context context, RequestResulteView view);
    }

    interface View {
        void NotionSuccess(String con);

        void NotionError(String con);
    }

    interface Presenter {
        public void initModelView(Model model, View view);
        public void requestNotification(Context context);

    }
}

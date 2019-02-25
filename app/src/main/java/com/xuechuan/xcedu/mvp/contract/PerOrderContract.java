package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: 个人订单
 * @author: L-BackPacker
 * @date: 2018/5/25 16:46
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface PerOrderContract {
    interface Model {
        public void requestOrder(Context context,int page, String orderstat, RequestResulteView view);

        public void submitDelOrd(Context context, String ordernunm, String usetype, RequestResulteView view);
    }

    interface View {
        void OrderSuccess(String con);

        void OrderError(String con);
        void OrderSuccessMore(String con);

        void OrderErrorMore(String con);

        void submitSuccess(String con);

        void submitError(String con);
    }

    interface Presenter {
        public void BasePresenter(Model model, View view);

        public void requestOrder(Context context, int page,String orderstat);
        public void requestOrderMore(Context context, int page,String orderstat);

        public void submitDelOrd(Context context, String ordernunm, String usetype);
    }
}

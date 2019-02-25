package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/8/30 11:00
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface WaitAddressOrderContract {
    interface Model {
        public void requestWaitAddressOrder(Context context, RequestResulteView view);

        public void submitConfimAddress(Context context, int addressid, RequestResulteView view);


    }

    interface View {
        public void OrderInfomSuccess(String success);

        public void OrderInfomError(String msg);

        public void ConfimAddressSuccess(String success);

        public void ConfimAddressError(String msg);
    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void requestWaitAddressOrder(Context context);

        public void submitConfimAddress(Context context, int addressid);

    }


}

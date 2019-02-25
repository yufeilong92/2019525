package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.WaitAddressOrderContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 获取待地址确认赠品列表
 * @author: L-BackPacker
 * @date: 2018/8/30 11:00
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class WaitAddressOrderPresenter implements WaitAddressOrderContract.Presenter {
    WaitAddressOrderContract.Model model;
    WaitAddressOrderContract.View view;

    @Override
    public void initModelView(WaitAddressOrderContract.Model model, WaitAddressOrderContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestWaitAddressOrder(Context context) {
        model.requestWaitAddressOrder(context, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.OrderInfomSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.OrderInfomError(msg);
            }
        });
    }

    @Override
    public void submitConfimAddress(Context context, int addressid) {
        model.submitConfimAddress(context, addressid, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.ConfimAddressSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.ConfimAddressError(msg);
            }
        });
    }

}

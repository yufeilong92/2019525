package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.OrderDetailContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 根据订单编号获取订单详情
 * @author: L-BackPacker
 * @date: 2018/8/27 11:17
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class OrderDetailPresenter implements OrderDetailContract.Presenter {
    OrderDetailContract.Model model;
    OrderDetailContract.View view;

    @Override
    public void initModelView(OrderDetailContract.Model model, OrderDetailContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestOrderDetail(Context context, String ordernum) {
        model.requestOrderDetail(context, ordernum, new RequestResulteView() {
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
}

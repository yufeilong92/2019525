package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.OrderTracesContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/8/28 10:46
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class OrderTracesPresenter implements OrderTracesContract.Presenter {
    OrderTracesContract.Model model;
    OrderTracesContract.View view;

    @Override
    public void initModelView(OrderTracesContract.Model model, OrderTracesContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestOrderTraces(Context context, int  code) {
        model.requestOrderTraces(context, code, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.TracesSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.TracesError(msg);
            }
        });
    }
}

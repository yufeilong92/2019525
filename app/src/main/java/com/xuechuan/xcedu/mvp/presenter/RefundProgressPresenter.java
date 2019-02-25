package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.RefundProgressContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 退款进度
 * @author: L-BackPacker
 * @date: 2018/8/28 10:03
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class RefundProgressPresenter implements RefundProgressContract.Presenter {
    RefundProgressContract.Model model;
    RefundProgressContract.View view;

    @Override
    public void initModelView(RefundProgressContract.Model model, RefundProgressContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestRefundProgres(Context context, String refund) {
        model.requestRefundProgres(context, refund, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.RefundSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.RefundError(msg);
            }
        });
    }
}

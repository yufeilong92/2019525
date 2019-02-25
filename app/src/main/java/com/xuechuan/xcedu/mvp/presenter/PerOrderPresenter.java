package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.mvp.contract.PerOrderContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.utils.DialogUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 订单信息
 * @author: L-BackPacker
 * @date: 2018/5/25 16:46
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class PerOrderPresenter implements PerOrderContract.Presenter {
    PerOrderContract.Model model;
    PerOrderContract.View view;

    @Override
    public void BasePresenter(PerOrderContract.Model model, PerOrderContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestOrder(Context context, int page,String orderstat) {
        model.requestOrder(context,page, orderstat, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.OrderSuccess(result);
            }

            @Override
            public void error(String result) {
                view.OrderError(result);
            }
        });
    }

    @Override
    public void requestOrderMore(Context context, int page, String orderstat) {
        model.requestOrder(context,page, orderstat, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.OrderSuccessMore(result);
            }

            @Override
            public void error(String result) {
                view.OrderErrorMore(result);
            }
        });
    }

    @Override
    public void submitDelOrd(Context context, String ordernunm, String usetype) {
       final AlertDialog dialog = DialogUtil.showDialog(context, "",context.getResources().getString(R.string.submit_loading));
        model.submitDelOrd(context, ordernunm, usetype, new RequestResulteView() {
            @Override
            public void success(String result) {
                dialog.dismiss();
                view.submitSuccess(result);
            }

            @Override
            public void error(String result) {
                dialog.dismiss();
                view.submitError(result);
            }
        });
    }
}

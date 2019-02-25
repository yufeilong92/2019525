package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.ConfirmReceiveContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 确认收货
 * @author: L-BackPacker
 * @date: 2018/8/28 10:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ConfirmReceivePresenter implements ConfirmReceiveContract.Presenter {
    ConfirmReceiveContract.Model model;
    ConfirmReceiveContract.View view;

    @Override
    public void initModelView(ConfirmReceiveContract.Model model, ConfirmReceiveContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void submitConfirmReceive(Context context, String ordernum, int oid) {
        model.submitConfirmReceive(context, ordernum, oid, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.ConfirmSucces(success);
            }

            @Override
            public void error(String msg) {
                view.ConfirmError(msg);
            }
        });
    }
}

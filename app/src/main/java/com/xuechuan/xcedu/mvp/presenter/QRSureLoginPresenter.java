package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.QRLoginContract;
import com.xuechuan.xcedu.mvp.contract.QRSureLoginContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/7/31 10:08
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class QRSureLoginPresenter implements QRSureLoginContract.Presenter {
    QRSureLoginContract.Model model;
    QRSureLoginContract.View view;


    @Override
    public void initModelView(QRSureLoginContract.Model model, QRSureLoginContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void submitLoginSureRequest(Context context, String code) {
        model.submitLoginSureRequest(context, code, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.QrloginSureSuccess(result);
            }

            @Override
            public void error(String result) {
                view.QrloginSureError(result);
            }
        });
    }
}

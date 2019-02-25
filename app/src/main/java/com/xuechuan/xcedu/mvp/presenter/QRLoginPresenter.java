package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.contract.QRLoginContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.LoginService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 扫码登陆
 * @author: L-BackPacker
 * @date: 2018/7/31 8:48
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class QRLoginPresenter implements QRLoginContract.Presenter {
    QRLoginContract.Model model;
    QRLoginContract.View view;

    @Override
    public void initModelView(QRLoginContract.Model model, QRLoginContract.View view) {
        this.model = model;
        this.view = view;

    }

    @Override
    public void submitLoginRequest(Context context, final String code) {
        model.submitLoginRequest(context, code, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.QrloginSuccess(result);
            }

            @Override
            public void error(String result) {
                view.QrloginError(result);
            }
        });
    }


}

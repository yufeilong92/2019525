package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.DefaultAddressContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 获取用户地址
 * @author: L-BackPacker
 * @date: 2018/8/25 15:49
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DefaultAddressPresenter implements DefaultAddressContract.Presenter {
    DefaultAddressContract.Model model;
    DefaultAddressContract.View view;

    @Override
    public void initModelView(DefaultAddressContract.Model model, DefaultAddressContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestDefaultAddress(Context context) {
        model.requestDefaultAddress(context, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.addressSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.addressError(msg);
            }
        });

    }
}

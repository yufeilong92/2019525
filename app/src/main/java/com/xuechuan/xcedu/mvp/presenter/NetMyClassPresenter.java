package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.NetMyClassContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 我的网课界面
 * @author: L-BackPacker
 * @date: 2018.11.29 上午 11:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetMyClassPresenter implements NetMyClassContract.Presenter {
    NetMyClassContract.Model model;
    NetMyClassContract.View view;

    @Override
    public void initModelView(NetMyClassContract.Model model, NetMyClassContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestNetMyClass(Context context) {
        model.requestNetMyClass(context, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.MyClassSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.MyClassError(msg);
            }
        });

    }
}

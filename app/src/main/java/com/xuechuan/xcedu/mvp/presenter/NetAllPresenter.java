package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.model.NetAllModel;
import com.xuechuan.xcedu.mvp.view.NetAllView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 网课所有
 * @author: L-BackPacker
 * @date: 2018/5/14 20:10
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetAllPresenter {
    private NetAllModel model;
    private NetAllView view;

    public NetAllPresenter(NetAllModel model, NetAllView view) {
        this.model = model;
        this.view = view;
    }

    public void requestAllPresenterList(Context context) {
        model.reqeustAllClass(context, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.PriductSuccess(result);
            }

            @Override
            public void error(String result) {
                view.PriductError(result);
            }
        });
    }
}


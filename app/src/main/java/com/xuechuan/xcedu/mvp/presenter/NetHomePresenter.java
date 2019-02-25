package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.model.NetHomeModel;
import com.xuechuan.xcedu.mvp.view.NetHomeView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 网课
 * @author: L-BackPacker
 * @date: 2018/5/13 16:13
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetHomePresenter {
    NetHomeModel model;
    NetHomeView view;

    public NetHomePresenter(NetHomeModel model, NetHomeView view) {
        this.model = model;
        this.view = view;
    }

    public void requestClassSandProducts(Context context) {
        model.requestHomeList(context, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.ClassListContSuccess(result);
            }

            @Override
            public void error(String result) {
                view.ClassListContError(result);
            }
        });
    }
}

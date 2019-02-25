package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.RqeustErrorContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.08 下午 4:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class RequestErrorPresenter implements RqeustErrorContract.Presenter {
    RqeustErrorContract.Model model;
    RqeustErrorContract.View view;

    @Override
    public void initModelView(RqeustErrorContract.Model model, RqeustErrorContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestError(Context context) {
        model.requestError(context, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.getErrorSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.getErrorError(msg);
            }
        });
    }
}

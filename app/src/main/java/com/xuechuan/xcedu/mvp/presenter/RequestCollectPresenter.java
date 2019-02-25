package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.RequestCollectContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.08 下午 4:46
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class RequestCollectPresenter implements RequestCollectContract.Presenter {
    RequestCollectContract.Model model;
    RequestCollectContract.View view;

    @Override
    public void initModelView(RequestCollectContract.Model model, RequestCollectContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestCollect(Context context) {
        model.requestCollect(context, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.getCollectSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.getCollectError(msg);
            }
        });
    }
}

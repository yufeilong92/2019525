package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.RequestionProgressContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description:
 * @author: L-BackPacker
 * @date: 2019.01.08 下午 6:56
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class RequestionProgressPresenter implements RequestionProgressContract.Presenter {
    RequestionProgressContract.Model model;
    RequestionProgressContract.View view;

    @Override
    public void initModelView(RequestionProgressContract.Model model, RequestionProgressContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestProgress(Context context) {
        model.requestProgress(context, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.ProgressSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.ProgressError(msg);
            }
        });
    }
}

package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.SpecasListContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 规范数据控制器
 * @author: L-BackPacker
 * @date: 2018.11.15 上午 9:51
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SpecasListPresenter implements SpecasListContract.Presenter {
    SpecasListContract.Model model;
    SpecasListContract.View view;

    @Override
    public void initModelView(SpecasListContract.Model model, SpecasListContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void RequestSpecasListOne(Context context, int page) {
        model.RequestSpecasList(context, page, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.SpecasListOneSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.SpecasListOneErrorr(msg);

            }
        });
    }

    @Override
    public void RequestSpecasListTwo(Context context, int page) {
        model.RequestSpecasList(context, page, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.SpecasListTwoSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.SpecasListTwoErrorr(msg);

            }
        });
    }
}

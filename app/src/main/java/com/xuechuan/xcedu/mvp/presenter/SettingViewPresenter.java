package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.SettingViewContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/28 18:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SettingViewPresenter implements SettingViewContract.Presenter {
    SettingViewContract.Model model;
    SettingViewContract.View view;

    @Override
    public void initModelView(SettingViewContract.Model model, SettingViewContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void submitBindWeiXin(Context context, String code) {
        model.submitBindWeiXin(context, code, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.submitBindWeiXin(result);
            }

            @Override
            public void error(String result) {
                view.submitBindWeiXinError(result);
            }
        });

    }

    @Override
    public void requestAppCode(Context context) {
        model.requestAppCode(context, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.AppCodeSuccess(result);
            }

            @Override
            public void error(String result) {
                view.AppCodeError(result);
            }
        });

    }

    @Override
    public void requestOutLogin(Context context) {
        model.requestOutLogin(context, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.OutSuccess(result);
            }

            @Override
            public void error(String result) {
                view.OutError(result);
            }
        });
    }
}

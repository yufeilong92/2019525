package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.PerNationContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 我的通知
 * @author: L-BackPacker
 * @date: 2018/5/25 16:55
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class PerNationPresenter implements PerNationContract.Presenter {
    PerNationContract.Model model;
    PerNationContract.View view;

    @Override
    public void initModelView(PerNationContract.Model model, PerNationContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestNotification(Context context) {

        model.requestNotification(context, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.NotionSuccess(result);
            }

            @Override
            public void error(String result) {
                view.NotionError(result);
            }
        });
    }
}

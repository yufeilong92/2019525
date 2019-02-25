package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.HomeNetContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.11.27 下午 5:19
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class HomeNetPresenter implements HomeNetContract.Presenter {
    HomeNetContract.Model model;
    HomeNetContract.View view;

    @Override
    public void initModelView(HomeNetContract.Model model, HomeNetContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestNetOne(Context context, int page) {
        model.requestNetAll(context, page, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.NetSuccessOne(success);
            }

            @Override
            public void error(String msg) {
                view.NetErrorOne(msg);
            }
        });
    }

    @Override
    public void requestNetTwo(Context context, int page) {
        model.requestNetAll(context, page, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.NetSuccessOne(success);
            }

            @Override
            public void error(String msg) {
                view.NetErrorTwo(msg);
            }
        });
    }


}

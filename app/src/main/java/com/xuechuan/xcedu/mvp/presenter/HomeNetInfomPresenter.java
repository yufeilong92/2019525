package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.HomeNetInfomContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 首页课程详情
 * @author: L-BackPacker
 * @date: 2018.11.28 上午 8:40
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class HomeNetInfomPresenter implements HomeNetInfomContract.Presenter {
    HomeNetInfomContract.Model model;
    HomeNetInfomContract.View view;

    @Override
    public void initModelView(HomeNetInfomContract.Model model, HomeNetInfomContract.View view) {
        this.model = model;
        this.view = view;

    }

    @Override
    public void requestNetDetail(Context context, String productid) {
        model.requestNetDetail(context, productid, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.NetDetailSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.NetDeatailError(msg);
            }
        });
    }
}

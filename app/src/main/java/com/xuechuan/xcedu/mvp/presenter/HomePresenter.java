package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.HomeContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 首页
 * @author: L-BackPacker
 * @date: 2018/6/2 17:25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class HomePresenter implements HomeContract.Presenter {
    HomeContract.Model model;
    HomeContract.View view;

    @Override
    public void initModelView(HomeContract.Model model, HomeContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestHomePager(Context context, String code) {
        if (StringUtil.isEmpty(code)) {
            code = "100000";
        }
        model.requestHomePager(context, code, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.Success(result);
            }

            @Override
            public void error(String result) {
                view.Error(result);
            }
        });
    }
}

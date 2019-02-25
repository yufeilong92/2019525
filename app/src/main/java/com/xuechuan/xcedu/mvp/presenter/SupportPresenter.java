package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.SupportContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 点赞接口
 * @author: L-BackPacker
 * @date: 2018/6/1 18:59
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SupportPresenter implements SupportContract.Presenter {
    SupportContract.Model model;
    SupportContract.View view;

    @Override
    public void initModelView(SupportContract.Model model, SupportContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void submitSupport(Context context, String targetid, String isSupper, String usetype) {
        model.submitSupport(context, targetid, isSupper, usetype, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.SupportSuc(result);
            }

            @Override
            public void error(String result) {
                view.SupportErr(result);
            }
        });
    }
}

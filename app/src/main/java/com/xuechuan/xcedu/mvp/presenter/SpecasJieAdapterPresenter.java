package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.SpecasJieAdapterContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description:规范适配管理器
 * @author: L-BackPacker
 * @date: 2018.11.15 上午 9:48
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SpecasJieAdapterPresenter implements SpecasJieAdapterContract.Presenter {
    SpecasJieAdapterContract.Model model;
    SpecasJieAdapterContract.View view;

    @Override
    public void initModelView(SpecasJieAdapterContract.Model model, SpecasJieAdapterContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestSpecaJie(Context context, String chapterid) {
        model.requestSpecaJie(context, chapterid, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.Success(success);
            }

            @Override
            public void error(String msg) {
                view.Error(msg);
            }
        });
    }
}

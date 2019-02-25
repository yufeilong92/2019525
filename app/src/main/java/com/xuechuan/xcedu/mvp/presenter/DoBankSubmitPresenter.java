package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.DoBankSubmitContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.08 上午 10:29
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class DoBankSubmitPresenter implements DoBankSubmitContract.Presenter {
    DoBankSubmitContract.Model model;
    DoBankSubmitContract.View view;

    @Override
    public void initModelView(DoBankSubmitContract.Model model, DoBankSubmitContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void submitDoBank(Context context, String time, String history) {
        model.submitDoBank(context, time, history, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.DoBankSuecces(success);
            }

            @Override
            public void error(String msg) {
                view.DoBankError(msg);
            }
        });
    }
}

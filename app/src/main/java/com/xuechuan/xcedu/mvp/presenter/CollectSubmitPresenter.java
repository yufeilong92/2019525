package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.CollectSubmitContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.08 下午 3:47
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class CollectSubmitPresenter implements CollectSubmitContract.Presenter {
    CollectSubmitContract.Model model;
    CollectSubmitContract.View view;

    @Override
    public void initModelView(CollectSubmitContract.Model model, CollectSubmitContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void submitCollect(Context context, String add, String delete) {
        model.submitCollect(context, add, delete, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.submitCollectSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.submitCollectError(msg);
            }
        });
    }
}

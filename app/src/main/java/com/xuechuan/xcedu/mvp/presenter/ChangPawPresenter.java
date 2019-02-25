package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.ChangPawContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/31 19:33
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ChangPawPresenter implements ChangPawContract.Presenter {
    ChangPawContract.Model model;
    ChangPawContract.View view;

    @Override
    public void initModelView(ChangPawContract.Model model, ChangPawContract.View view) {
        this.model = model;
        this.view = view;

    }

    @Override
    public void submitChangerPaw(Context context, String old, String pas) {
        model.submitChangerPaw(context, old, pas, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.submitSuccess(result);
            }
            @Override
            public void error(String result) {
                view.submitError(result);
            }
        });
    }
}

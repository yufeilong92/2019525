package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.ErrorSubmtContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.08 下午 2:30
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class ErrorSubmtPresenter implements ErrorSubmtContract.Presenter {
    ErrorSubmtContract.Model model;
    ErrorSubmtContract.View view;

    @Override
    public void initModelView(ErrorSubmtContract.Model model, ErrorSubmtContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void submitError(Context context, String add, String delete) {
            model.submitError(context, add, delete, new RequestResulteView() {
                @Override
                public void success(String success) {
                    view.submitErrorSuccess(success);
                }

                @Override
                public void error(String msg) {
                   view.submitErrorError(msg);
                }
            });
    }
}

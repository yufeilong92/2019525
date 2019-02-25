package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.ChangeInfoContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: 1.0.8
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 根据兑换码获取兑换信息
 * @author: L-BackPacker
 * @date: 2018/8/24 9:36
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ChangeInfoPresenter implements ChangeInfoContract.Presenter {
    ChangeInfoContract.Model model;
    ChangeInfoContract.View view;
    @Override
    public void initModelView(ChangeInfoContract.Model model, ChangeInfoContract.View view) {
        this.model=model;
        this.view=view;
    }

    @Override
    public void requestChangeCode(Context context, String code) {
        model.requestChangeCode(context, code, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.ChangeInfomSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.ChangeInfomError(msg);
            }
        });

    }
}

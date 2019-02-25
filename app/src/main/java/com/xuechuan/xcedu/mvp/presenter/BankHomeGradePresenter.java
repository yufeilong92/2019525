package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.BankHomeGradeContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 题库首页版本跟新
 * @author: L-BackPacker
 * @date: 2018.12.06 下午 3:20
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BankHomeGradePresenter implements BankHomeGradeContract.Presenter {
    BankHomeGradeContract.Model model;
    BankHomeGradeContract.View view;

    @Override
    public void initModelView(BankHomeGradeContract.Model model, BankHomeGradeContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void reuqestGrade(Context context, String code) {
        model.requestGrade(context, code, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.GradeSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.GradeError(msg);
            }
        });
    }
}

package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.ProblemTagContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 请求获取所有问答模块问题标签
 * @author: L-BackPacker
 * @date: 2019.02.13 上午 10:00
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class ProblemTagPresenter implements ProblemTagContract.Presenter {
    ProblemTagContract.Model model;
    ProblemTagContract.View view;

    @Override
    public void initModelView(ProblemTagContract.Model model, ProblemTagContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestProblemtags(Context context) {
        model.requestProblemtags(context, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.ProblemTagSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.ProblemTagError(msg);
            }
        });
    }
}

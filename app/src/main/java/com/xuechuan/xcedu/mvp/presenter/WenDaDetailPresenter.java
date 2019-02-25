package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.WenDaDetailContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.10 上午 11:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class WenDaDetailPresenter implements WenDaDetailContract.Presenter {
    WenDaDetailContract.Model model;
    WenDaDetailContract.View view;

    @Override
    public void initModelView(WenDaDetailContract.Model model, WenDaDetailContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestPreblemDetail(Context context, int problemid) {
        model.requestPreblemDetail(context, problemid, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.ProblemDetailSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.ProblemDetailError(msg);
            }
        });
    }

    @Override
    public void submitProblemEvaluet(Context context, int problemid, String comment, int score) {
        model.submitProblemEvaluet(context, problemid, comment, score, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.ProblemEvalueSuccess(success);
            }

            @Override
            public void error(String msg) {
                 view.ProblemEvalueError(msg);
            }
        });
    }
}

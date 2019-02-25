package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Progress;
import com.xuechuan.xcedu.mvp.contract.WenDaContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteUpView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.10 上午 10:22
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class WenDaPresenter implements WenDaContract.Presenter {
    WenDaContract.Model model;
    WenDaContract.View view;

    @Override
    public void initModelView(WenDaContract.Model model, WenDaContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestWaitQuesiton(Context context) {
        model.requestWaitQuesiton(context, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.WaitQuestionSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.WaitQuestionError(msg);
            }
        });
    }

    @Override
    public void requestHankpickListsOne(Context context, int page) {
        model.requestHankpickLists(context, page, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.HankPicksuccessOne(success);
            }

            @Override
            public void error(String msg) {
                view.HankPickErrorOne(msg);
            }
        });
    }

    @Override
    public void requestHankpickListsTwo(Context context, int page) {
        model.requestHankpickLists(context, page, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.HankPicksuccessTwo(success);
            }

            @Override
            public void error(String msg) {
                view.HankPickErrorTwo(msg);
            }
        });
    }

    @Override
    public void requestMyQuestionListsOne(Context context, int page) {
        model.requestMyQuestionLists(context, page, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.MyQuestionSuccessOne(success);
            }

            @Override
            public void error(String msg) {
                view.MyQuestionErrorOne(msg);
            }
        });
    }

    @Override
    public void requestMyQuestionListsTwo(Context context, int page) {
        model.requestMyQuestionLists(context, page, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.MyQuestionSuccessTwo(success);
            }

            @Override
            public void error(String msg) {
                view.MyQuestionErrorTwo(msg);
            }
        });
    }




}

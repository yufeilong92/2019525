package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.iflytek.msc.MSC;
import com.xuechuan.xcedu.mvp.contract.ExamHistoryContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.09 下午 3:20
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class ExamHistoryPresenter implements ExamHistoryContract.Presenter {
    ExamHistoryContract.Model model;
    ExamHistoryContract.View view;

    @Override
    public void initModelView(ExamHistoryContract.Model model, ExamHistoryContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void deleteExamHistory(Context context, int historyid) {
        model.deleteExamHistory(context, historyid, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.deleteHistorySuccess(success);
            }

            @Override
            public void error(String msg) {
                view.deleteHistoryError(msg);
            }
        });
    }

    @Override
    public void requestExamHistoryOne(Context context, int page) {
        model.requestExamHistory(context, page, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.ExamHistorySuccessOne(success);
            }

            @Override
            public void error(String msg) {
                view.ExamHistoryErrrorOne(msg);
            }
        });
    }

    @Override
    public void requestExamHistoryTwo(Context context, int page) {
        model.requestExamHistory(context, page, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.ExamHistorySuccessTwo(success);
            }

            @Override
            public void error(String msg) {
                view.ExamHistoryErrrorTwo(msg);
            }
        });
    }

    @Override
    public void requestExamDetail(Context context, int historyid) {
        model.requestExamDetail(context, historyid, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.ExamHistoryDetailSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.ExamHistoryDetailError(msg);
            }
        });
    }

}

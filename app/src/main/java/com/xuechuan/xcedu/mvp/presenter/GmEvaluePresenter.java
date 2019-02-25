package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.GmEvalueContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 请求评价
 * @author: L-BackPacker
 * @date: 2018.12.19 上午 11:37
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class GmEvaluePresenter implements GmEvalueContract.Presenter {
    GmEvalueContract.Model model;
    GmEvalueContract.View view;

    @Override
    public void initModelView(GmEvalueContract.Model model, GmEvalueContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void reqeustEvalueContentOne(Context context, int page, String targettype, String targetid) {
        model.reqeustEvalueContent(context, page, targettype, targetid, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.EvaluesOneSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.EvaluesOneError(msg);
            }
        });
    }

    @Override
    public void reqeustEvalueContentMore(Context context, int page, String targettype, String targetid) {
        model.reqeustEvalueContent(context, page, targettype, targetid, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.EvaluesMoreSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.EvaluesMoreError(msg);
            }
        });
    }

    @Override
    public void reqeustNoteByQuestion(Context context, int question) {
        model.reqeustNoteByQuestion(context, question, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.NoTeByQuestionSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.NoteByQuestionError(msg);
            }
        });
    }
}

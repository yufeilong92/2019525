package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.MyNoteContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.02 下午 2:22
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class MyNotePresenter implements MyNoteContract.Presenter {
    MyNoteContract.Model model;
    MyNoteContract.View view;

    @Override
    public void initModelView(MyNoteContract.Model model, MyNoteContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestMyNoteListOne(Context context, String courseid, int page) {
        model.requestMyNoteList(context, courseid, page, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.MyNoteSuccessOne(success);
            }

            @Override
            public void error(String msg) {
                view.MyNoteErrorOne(msg);
            }
        });
    }

    @Override
    public void requestMyNoteListMore(Context context, String courseid, int page) {
        model.requestMyNoteList(context, courseid, page, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.MyNoteSuccessMore(success);
            }

            @Override
            public void error(String msg) {
                view.MyNoteErrorMore(msg);
            }
        });
    }

    @Override
    public void deleteNote(Context context, int id) {
        model.deleteNote(context, id, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.DeleteNoteSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.DeleteNoteError(msg);
            }
        });
    }

}

package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.NoteSubmiteContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.02 下午 4:03
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class NoteSubmitePresenter implements NoteSubmiteContract.Presenter {
    NoteSubmiteContract.Model model;
    NoteSubmiteContract.View view;

    @Override
    public void initModelView(NoteSubmiteContract.Model model, NoteSubmiteContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void submiteNote(Context context,int id, int question, String content) {
        model.submiteNote(context, id, question, content, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.SubmiteNoteSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.SubmiteNoteError(msg);
            }
        });
    }
}

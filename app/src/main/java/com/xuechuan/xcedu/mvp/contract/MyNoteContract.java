package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.02 下午 2:22
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public interface MyNoteContract {
    interface Model {
        public void requestMyNoteList(Context context, String courseid, int page, RequestResulteView resulteView);

        public void deleteNote(Context context, int id, RequestResulteView resulteView);

    }

    interface View {
        public void MyNoteSuccessOne(String success);

        public void MyNoteErrorOne(String error);

        public void MyNoteSuccessMore(String success);

        public void MyNoteErrorMore(String error);

        public void DeleteNoteSuccess(String succes);

        public void DeleteNoteError(String msg);
    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void requestMyNoteListOne(Context context, String courseid, int page);

        public void requestMyNoteListMore(Context context, String courseid, int page);

        public void deleteNote(Context context, int id);
    }
}

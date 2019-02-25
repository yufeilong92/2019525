package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.02 下午 4:03
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public interface NoteSubmiteContract {
    interface Model {
        public void submiteNote(Context context,int id, int question, String content, RequestResulteView view);
    }

    interface View {
         public void SubmiteNoteSuccess(String success);
         public void SubmiteNoteError(String msg);
    }


    interface Presenter {
         public void initModelView(Model model, View view);
        public void submiteNote(Context context,int id, int question, String content);
    }
}

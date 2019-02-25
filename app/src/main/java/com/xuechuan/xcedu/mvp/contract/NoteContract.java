package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.26 下午 5:06
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface NoteContract {
    interface Model {
        public void SubmiteQuestionNote(Context context, int quesitonid, String content, RequestResulteView resulteView);

        public void requestQuesitonNoteList(Context context,int questionid, RequestResulteView resulteView);

        public void requestionQuestionNoteNumber(Context context,int questionid,RequestResulteView RequestResulteView);
    }

    interface View {

    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void SubmiteQuestionNote(Context context, int quesitonid, String content);

        public void requestQuesitonNoteList(Context context,int questionid);

        public void requestionQuestionNoteNumber(Context context,int questionid);
    }
}

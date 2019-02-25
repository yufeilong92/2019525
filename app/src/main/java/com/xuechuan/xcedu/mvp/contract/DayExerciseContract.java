package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.26 下午 4:28
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface DayExerciseContract {
    interface Model {
        public void requestiDayExercise(Context context, RequestResulteView view);

        public void requestNoteCount(Context context, RequestResulteView resulteView);
    }

    interface View {
        public void DaySuccess(String success);

        public void DayError(String msg);

        public void NoteCountSuccess(String success);

        public void NoteCountError(String msg);

    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void requestiDayExercise(Context context);

        public void requestNoteCount(Context context);
    }
}

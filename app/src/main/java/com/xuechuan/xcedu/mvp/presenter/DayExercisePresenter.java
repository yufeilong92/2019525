package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.DayExerciseContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 获取题库科目首页每日一练
 * @author: L-BackPacker
 * @date: 2018.12.26 下午 4:28
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DayExercisePresenter implements DayExerciseContract.Presenter {
    DayExerciseContract.Model model;
    DayExerciseContract.View view;

    @Override
    public void initModelView(DayExerciseContract.Model model, DayExerciseContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestiDayExercise(Context context) {
        model.requestiDayExercise(context, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.DaySuccess(success);
            }

            @Override
            public void error(String msg) {
                view.DayError(msg);
            }
        });
    }

    @Override
    public void requestNoteCount(Context context) {
        model.requestNoteCount(context, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.NoteCountSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.NoteCountError(msg);
            }
        });
    }
}

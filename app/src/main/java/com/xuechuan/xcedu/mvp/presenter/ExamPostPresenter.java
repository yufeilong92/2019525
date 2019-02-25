package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.ExamPostContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.vo.SubmiteExamVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.04 下午 6:37
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class ExamPostPresenter implements ExamPostContract.Presenter {
    ExamPostContract.Model model;
    ExamPostContract.View view;

    @Override
    public void initModelView(ExamPostContract.Model model, ExamPostContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void submitExam(Context context, String score, int chapterid, int usetime, String finishtime, final List<SubmiteExamVo> vo) {
        model.submitExam(context, score, chapterid, usetime, finishtime, vo, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.submiteSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.submiteErrror(msg);
            }
        });
    }
}

package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.model.ExamModel;
import com.xuechuan.xcedu.mvp.view.ExamView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/3 20:54
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ExamPresenter {
    private ExamModel model;
    private ExamView view;

    public ExamPresenter(ExamModel model, ExamView view) {
        this.model = model;
        this.view = view;
    }

    public void requestExamContent(Context context, String couresid) {
        if (StringUtil.isEmpty(couresid)) {
            return;
        }
        model.requestContent(context, couresid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.ExamSuccess(result);
            }

            @Override
            public void error(String result) {
                view.ExamError(result);
            }
        });
    }
}

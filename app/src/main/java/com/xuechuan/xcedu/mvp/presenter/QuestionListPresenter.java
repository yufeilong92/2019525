package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.model.QuestionListModel;
import com.xuechuan.xcedu.mvp.view.QuestionListView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description:  章
 * @author: L-BackPacker
 * @date: 2018/5/5 20:04
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class QuestionListPresenter {
    private QuestionListModel model;
    private QuestionListView view;

    public QuestionListPresenter(QuestionListModel model, QuestionListView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * 请求文章章节
     * @param context
     * @param courseid
     */
    public void requestQuetionList(Context context, String courseid) {
        if (StringUtil.isEmpty(courseid)) {
            return;
        }
        model.reqeustQuestionList(context, courseid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.QuestionListSuccess(result);
            }

            @Override
            public void error(String result) {
                view.QuestionListError(result);
            }
        });


    }
}

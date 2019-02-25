package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.model.AllQuestionModel;
import com.xuechuan.xcedu.mvp.view.AllQuestionView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/5 17:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AllQuestionPresenter {
     private AllQuestionModel model;
     private AllQuestionView view;

    public AllQuestionPresenter(AllQuestionModel model, AllQuestionView view) {
        this.model = model;
        this.view = view;
    }

    /***
     * 根据科目获取所有练习题题号
     * @param context
     * @param courseid
     */
    public void getcoursequestionid(Context context, String courseid) {
        if (StringUtil.isEmpty(courseid)) {
            return;
        }
        model.reqeustAllQuestionId(context, courseid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.QuestionIdAllSuccess(result);
            }

            @Override
            public void error(String result) {
                view.QuestionIdAllError(result);
            }
        });
    }
}

package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.model.SpecailModel;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.mvp.view.SpecailView;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/5 16:55
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SpecailPresenter {
    private SpecailView view;
    private SpecailModel model;

    public SpecailPresenter(SpecailView view, SpecailModel model) {
        this.view = view;
        this.model = model;
    }

    /**
     * 根据科目获取题库标签
     *
     * @param context
     * @param courseid
     */
    public void getQuestionTags(Context context, String courseid) {
        if (StringUtil.isEmpty(courseid)) {
            return;
        }
        model.reqeustQuestionTags(context, courseid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.RequestionWithtagsSuccess(result);
            }

            @Override
            public void error(String result) {
                view.RequestionWithtagsError(result);
            }
        });
    }
}

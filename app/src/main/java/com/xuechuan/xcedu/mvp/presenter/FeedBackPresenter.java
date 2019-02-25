package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.model.FeedBackModel;
import com.xuechuan.xcedu.mvp.view.FeedBackView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 反馈信息
 * @author: L-BackPacker
 * @date: 2018/5/22 10:09
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class FeedBackPresenter {
    private FeedBackModel model;
    private FeedBackView view;

    public FeedBackPresenter(FeedBackModel model, FeedBackView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * 提交反馈信息
     * @param context
     * @param content
     * @param link
     */
    public void submitFeedBack(Context context, String content, String link) {
        if (StringUtil.isEmpty(content) ) {
            return;
        }
        model.submitFeedBack(context, content, link, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.feedSuccess(result);
            }

            @Override
            public void error(String result) {
                view.feedError(result);
            }
        });
    }
}

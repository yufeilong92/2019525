package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.model.ErrorTextModel;
import com.xuechuan.xcedu.mvp.view.ErrorTextView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 我的错题
 * @author: L-BackPacker
 * @date: 2018/5/3 10:35
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ErrorTextPresenter {
    private ErrorTextModel model;
    private ErrorTextView view;

    public ErrorTextPresenter(ErrorTextModel model, ErrorTextView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * 请求错题数
     *
     * @param context
     * @param courseid
     * @param tagtype
     */
    public void reqeusetQuestionCount(Context context, String courseid, String tagid, String tagtype) {
        if (StringUtil.isEmpty(tagtype)) {
            return;
        }
        model.requestErrorNumber(context, courseid, tagtype, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.ErrorSuccess(result);
            }

            @Override
            public void error(String result) {
                view.ErrorError(result);
            }
        });
    }

    /**
     * 获取错题数据和收数
     *
     * @param context
     * @param courseid
     */
    public void getErrOrCollNumber(Context context, String courseid) {
        if (StringUtil.isEmpty(courseid)) {
            return;
        }
        model.reqeustErrOrCoNumber(context, courseid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.ErrOrColNumberSuccess(result);
            }

            @Override
            public void error(String result) {
                view.ErrOrColNumberError(result);
            }
        });
    }

}

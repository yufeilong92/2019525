package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.model.EvalueModel;
import com.xuechuan.xcedu.mvp.view.EvalueView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 评价
 * @author: L-BackPacker
 * @date: 2018/5/3 15:50
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class EvaluePresenter {
    private EvalueModel model;
    private EvalueView view;

    public EvaluePresenter(EvalueModel model, EvalueView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * 提交二级评价
     *
     * @param context
     * @param targetid
     * @param comment
     * @param commentid
     * @param usetype
     */
    public void submitContent(Context context, String targetid, String comment, String commentid, String usetype) {
        if (StringUtil.isEmpty(comment)) {
            return;
        }
        model.SubmitContent(context, targetid, comment, commentid, usetype, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.submitEvalueSuccess(result);
            }

            @Override
            public void error(String result) {
                view.submitEvalueError(result);
            }
        });
    }

    public void requestEvalueOneContent(Context context, int page, String questonid) {
        if (StringUtil.isEmpty(questonid)) {
            return;
        }
        model.reqeustOneEvalueContent(context, page, questonid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.GetOneEvalueSuccess(result);
            }

            @Override
            public void error(String result) {
                view.GetOneEvalueError(result);
            }
        });
    }

    public void requestEvalueOneMoreContent(Context context, int page, String questonid) {
        if (StringUtil.isEmpty(questonid)) {
            return;
        }
        model.reqeustOneEvalueContent(context, page, questonid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.GetOneMoreEvalueSuccess(result);
            }

            @Override
            public void error(String result) {
                view.GetOneMoreEvalueError(result);
            }
        });
    }

    public void requestNewEvalueContent(Context context, int page, String targettype, String targetid) {
        if (StringUtil.isEmpty(targetid)) {
            return;
        }
        model.reqeustEvalueContentNew(context, page, targettype, targetid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.requestEvalueNewSuccess(result);
            }

            @Override
            public void error(String result) {
                view.requestEvalueNewError(result);
            }
        });
    }

    public void requestNewEvalueMoreContent(Context context, int page, String targettype, String targetid) {
        if (StringUtil.isEmpty(targetid)) {
            return;
        }
        model.reqeustEvalueContentNew(context, page, targettype, targetid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.requestMoreEvalueNewSuccess(result);
            }

            @Override
            public void error(String result) {
                view.requestMoreEvalueNewError(result);
            }
        });
    }


}

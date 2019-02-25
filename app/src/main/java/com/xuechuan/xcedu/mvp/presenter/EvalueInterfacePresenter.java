package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.mvp.contract.EvalueInterfaceContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 二级评价
 * @author: L-BackPacker
 * @date: 2018/6/1 20:21
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class EvalueInterfacePresenter implements EvalueInterfaceContract.Presenter {
    EvalueInterfaceContract.Model model;
    EvalueInterfaceContract.View view;

    @Override
    public void initModelView(EvalueInterfaceContract.Model model, EvalueInterfaceContract.View view) {
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
    @Override
    public void SubmitContent(Context context, String targetid, String comment, String commentid, String usetype) {
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

    /**
     * 请求二级评价内容
     *
     * @param context
     * @param page
     * @param commentid
     * @param type
     */
    @Override
    public void requestEvalueTwo(Context context, int page, String commentid, String type) {
        model.requestEvalueTwo(context, page, commentid, type, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.EvalueTwoSuc(result);
            }

            @Override
            public void error(String result) {
                view.EvalueTwoErro(result);
            }
        });
    }

    /**
     * 请求二级评价内容
     *
     * @param context
     * @param page
     * @param commentid
     * @param type
     */
    @Override
    public void requestEvalueTwoMore(Context context, int page, String commentid, String type) {
        model.requestEvalueTwo(context, page, commentid, type, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.EvalueTwoSucMore(result);
            }

            @Override
            public void error(String result) {
                view.EvalueTwoErroMore(result);
            }
        });
    }

    @Override
    public void requestCommentEvalueTwo(Context context, int page, String targettype, String commentid) {
        model.requestCommentEvalueTwo(context, page, targettype, commentid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.EvalueCommentSucMore(result);
            }

            @Override
            public void error(String result) {
                view.EvalueCommentErroMore(result);
            }
        });

    }

    @Override
    public void requestCommentEvalueMoreTwo(Context context, int page, String targettype, String commentid) {
        model.requestCommentEvalueTwo(context, page, targettype, commentid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.EvalueCommentSucMoreTwo(result);
            }

            @Override
            public void error(String result) {
                view.EvalueCommentErroMoreTwo(result);
            }
        });

    }


}

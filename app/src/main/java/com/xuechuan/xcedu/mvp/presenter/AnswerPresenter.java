package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.mvp.model.AnswerModel;
import com.xuechuan.xcedu.mvp.view.AnswerView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 控制器
 * @author: L-BackPacker
 * @date: 2018/5/2 11:11
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AnswerPresenter {
    AnswerModel model;
    AnswerView view;

    public AnswerPresenter(AnswerModel model, AnswerView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * 题干
     *
     * @param context
     * @param id
     */
    public void getTextContent(Context context, String id) {
        if (StringUtil.isEmpty(id)) {
            return;
        }
        model.getTextContent(context, id, new RequestResulteView() {
            @Override
            public void success(String result) {
                L.e("success");
                view.TextSuccess(result);
            }

            @Override
            public void error(String result) {
                view.TextError(result);
            }
        });

    }

    /**
     * 题详情
     *
     * @param content
     * @param id
     */
    public void getTextDetailContent(Context content, String id,int num,int targetid,int qt) {
        if (StringUtil.isEmpty(id)) {
            return;
        }
        model.getTextDetailContent(content, id,num, targetid,qt,new RequestResulteView() {
            @Override
            public void success(String result) {
                view.TextDetailSuccess(result);
            }

            @Override
            public void error(String result) {
                view.TextDetailError(result);
            }
        });
    }

    /**
     * @param context
     * @param targetid 练习题编号
     * @param isfav
     */
    public void submit0ollect(Context context, String targetid, boolean isfav) {
        if (StringUtil.isEmpty(targetid)) {
            return;
        }
        model.SubmitCollectContent(context, isfav, targetid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.SumbitCollectSuccess(result);
            }

            @Override
            public void error(String result) {
                view.SumbitCollectError(result);
            }
        });

    }

    /**
     * 提交做题结果
     *
     * @param context
     * @param targetid
     * @param isright
     */
    public void submitDoRecord(Context context, String targetid, boolean isright,int rnum,int questionid,int qt) {
        if (StringUtil.isEmpty(targetid)) {
            return;
        }
        model.SubmitDoResult(context, isright, targetid,rnum,questionid,qt, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.DoResultSuccess(result);
            }

            @Override
            public void error(String result) {
                view.DoResultError(result);
            }
        });
    }


    public void submitWoringQeustinDelect(Context context, String targetid) {
        if (targetid == null) {
            return;
        }
        model.SubmitWoringQuestionDelect(context, targetid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.WoringSuccess(result);
            }

            @Override
            public void error(String result) {
                view.WoringError(result);
            }
        });

    }

}

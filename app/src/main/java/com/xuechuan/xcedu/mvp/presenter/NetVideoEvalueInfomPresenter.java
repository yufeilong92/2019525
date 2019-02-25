package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.model.NetVideoEvalueInfomModel;
import com.xuechuan.xcedu.mvp.model.NetVideoEvalueModel;
import com.xuechuan.xcedu.mvp.view.NetVideoEvalueInfomView;
import com.xuechuan.xcedu.mvp.view.NetVideoEvalueView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 视屏评价
 * @author: L-BackPacker
 * @date: 2018/5/21 14:42
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetVideoEvalueInfomPresenter {

    private NetVideoEvalueInfomModel model;
    private NetVideoEvalueInfomView view;

    public NetVideoEvalueInfomPresenter(NetVideoEvalueInfomModel model, NetVideoEvalueInfomView view) {
        this.model = model;
        this.view = view;
    }

    public void requestVideoEvalue(Context context, final String videoId,String commentid, int page) {
        model.requestVideoEvalueContent(context, videoId,commentid, page, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.EvalueOneSuccess(result);
            }

            @Override
            public void error(String result) {
                view.EvalueOneError(result);
            }
        });
    }

    public void requestMoreVideoEvalue(Context context, final String videoId,String commentid, int page) {
        model.requestVideoEvalueContent(context, videoId,commentid, page, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.EvalueMoreSuccess(result);
            }

            @Override
            public void error(String result) {
                view.EvalueMoreError(result);
            }
        });
    }

    /**
     * 提交评价
     * @param context
     * @param videoId
     * @param con
     * @param commentid
     */
    public void submitEvalue(Context context, String videoId, String con, String commentid) {
        if (StringUtil.isEmpty(videoId) && StringUtil.isEmpty(con)) {
            return;
        }

        model.submitEvalueVideo(context, videoId, con, commentid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.SubmitEvalueSuccess(result);
            }

            @Override
            public void error(String result) {
                view.SubmitEvalueError(result);
            }
        });


    }


}

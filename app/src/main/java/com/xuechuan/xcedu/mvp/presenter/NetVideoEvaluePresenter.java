package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.mvp.model.NetVideoEvalueModel;
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
public class NetVideoEvaluePresenter {

    private NetVideoEvalueModel model;
    private NetVideoEvalueView view;

    public NetVideoEvaluePresenter(NetVideoEvalueModel model, NetVideoEvalueView view) {
        this.model = model;
        this.view = view;
    }

    public void requestVideoEvalue(Context context, final String videoId, int page) {
        model.requestVideoEvalueContent(context, videoId, page, new RequestResulteView() {
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

    public void requestMoreVideoEvalue(Context context, final String videoId, int page) {
        model.requestVideoEvalueContent(context, videoId, page, new RequestResulteView() {
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
     *
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

    /***
     * 请求评价数据 三次该
     * @param context
     * @param page
     * @param targettype 枚举类型article 文章，question问题，video视频
     * @param targetid 根据targettype类型传递主体（文章，问题，视频）编号
     */
    public void requestVideoEvalueNew(Context context, int page, String targettype, String targetid) {
        model.requestVideoEvalueNew(context, page, targettype, targetid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.RequestEvalueNewSuccess(result);
            }

            @Override
            public void error(String result) {
                view.RequestEvalueNewError(result);
            }
        });
    }
    /***
     * 请求评价数据 三次该
     * @param context
     * @param page
     * @param targettype 枚举类型article 文章，question问题，video视频
     * @param targetid 根据targettype类型传递主体（文章，问题，视频）编号
     */
    public void requestVideoEvalueNewMore(Context context, int page, String targettype, String targetid) {
        model.requestVideoEvalueNew(context, page, targettype, targetid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.RequestEvalueMoreNewSuccess(result);
            }

            @Override
            public void error(String result) {
                view.RequestEvalueMoreNewError(result);
            }
        });
    }

}

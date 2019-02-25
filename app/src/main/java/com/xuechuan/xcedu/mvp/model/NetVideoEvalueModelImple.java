package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.view.NetVideoEvalueView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.CurrencyService;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.NetService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 网课评价页
 * @author: L-BackPacker
 * @date: 2018/5/21 14:42
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetVideoEvalueModelImple implements NetVideoEvalueModel {
    /**
     * 请求视频评价
     *
     * @param context
     * @param videoid
     * @param page
     * @param view
     */
    @Override
    public void requestVideoEvalueContent(Context context, final String videoid, int page, final RequestResulteView view) {

        NetService service = new NetService(context);
        service.requestVideoEvalue(videoid, page, new StringCallBackView() {
            @Override
            public void onSuccess(String response) {
                view.success(response);
            }

            @Override
            public void onError(String response) {
                view.error(response);
            }
        });

    }

    @Override
    public void requestVideoEvalueNew(Context context, int page, String targettype, String targetid,final RequestResulteView view) {
        HomeService homeService = new HomeService(context);
        homeService.requestCommentCommentData(page, targettype, targetid, new StringCallBackView() {
            @Override
            public void onSuccess(String response) {
                view.success(response);
            }

            @Override
            public void onError(String response) {
                view.error(response);
            }
        });
    }

    /**
     * 提交评价啊
     *
     * @param context
     * @param videoId
     * @param con
     * @param commentid
     * @param view
     */
    @Override
    public void submitEvalueVideo(Context context, String videoId, String con, String commentid, final RequestResulteView view) {
        CurrencyService service = new CurrencyService(context);
        service.submitConmment(videoId, con, commentid, DataMessageVo.VIDEO, new StringCallBackView() {
            @Override
            public void onSuccess(String response) {
                view.success(response);
            }

            @Override
            public void onError(String response) {
                view.error(response);
            }
        });
    }

}

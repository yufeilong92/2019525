package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.CurrencyService;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/3 15:53
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class EvalueModelImpl implements EvalueModel {

    @Override
    public void SubmitContent(Context context, String targetid, String comment, String commentid, String usetype, final RequestResulteView view) {
        CurrencyService service = new CurrencyService(context);
        service.submitConmment(targetid, comment, commentid, usetype, new StringCallBackView() {
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
    public void reqeustOneEvalueContent(Context context, int page, String questionid, final RequestResulteView view) {
        BankService service = new BankService(context);
        service.reqiestQuestionCmment(questionid, page, new StringCallBackView() {
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
     * 请求问题评论数据三次该
     * @param context
     * @param page
     * @param targettype 枚举类型article 文章，question问题，video视频
     * @param targetid 	根据targettype类型传递主体（文章，问题，视频）编号
     * @param view
     */
    @Override
    public void reqeustEvalueContentNew(Context context, int page, String targettype, String targetid, final RequestResulteView view) {
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

}

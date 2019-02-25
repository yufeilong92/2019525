package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.google.android.exoplayer.C;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.contract.EvalueInterfaceContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.CurrencyService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/6/1 20:21
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class EvalueInterfaceModel implements EvalueInterfaceContract.Model {
    @Override
    public void requestEvalueTwo(Context context, int page, String commentid, String type, final RequestResulteView view) {
        CurrencyService service = new CurrencyService(context);
        service.requestEvalueData(page, commentid, type, new StringCallBackView() {
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
    public void requestCommentEvalueTwo(Context context, int page, String targettype, String commentid, final RequestResulteView view) {
        CurrencyService service = new CurrencyService(context);
        service.requestCommentEvalueData(page, targettype, commentid, new StringCallBackView() {
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
}

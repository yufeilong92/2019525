package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.WenDaDetailContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.WenDaService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.10 上午 11:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class WenDaDetailModel implements WenDaDetailContract.Model {
    @Override
    public void requestPreblemDetail(Context context, int problemid, final RequestResulteView resulteView) {
        WenDaService service = WenDaService.get_Instance(context);
        service.requestProbleDetail(problemid, new StringCallBackView() {
            @Override
            public void onSuccess(String success) {
                resulteView.success(success);
            }

            @Override
            public void onError(String msg) {
                resulteView.error(msg);
            }
        });
    }

    @Override
    public void submitProblemEvaluet(Context context, int problemid, String comment, int score, final RequestResulteView resulteView) {
        WenDaService service = WenDaService.get_Instance(context);
        service.submitProblemEvalues(problemid, score, comment, new StringCallBackView() {
            @Override
            public void onSuccess(String success) {
                resulteView.success(success);
            }

            @Override
            public void onError(String msg) {
             resulteView.error(msg);
            }
        });
    }
}

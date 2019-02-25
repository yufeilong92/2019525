package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.contract.WenDaContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteUpView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.UpData;
import com.xuechuan.xcedu.net.WenDaService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.10 上午 10:22
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class WenDaModel implements WenDaContract.Model {
    @Override
    public void requestWaitQuesiton(Context context, final RequestResulteView resulteView) {
        WenDaService service = WenDaService.get_Instance(context);
        service.requestWaitProblemLists(new StringCallBackView() {
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
    public void requestHankpickLists(Context context, int page, final RequestResulteView resulteView) {
        WenDaService service = WenDaService.get_Instance(context);
        service.requestHankpickProbleLists(page, new StringCallBackView() {
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
    public void requestMyQuestionLists(Context context, int page, final RequestResulteView resulteView) {
        WenDaService service = WenDaService.get_Instance(context);
        service.requestMyProbleLists(page, new StringCallBackView() {
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

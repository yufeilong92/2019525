package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.contract.PerOrderContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.MeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 订单信息
 * @author: L-BackPacker
 * @date: 2018/5/25 16:46
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class PerOrderModel implements PerOrderContract.Model {
    @Override
    public void requestOrder(Context context,int page, String orderstat, final RequestResulteView view) {
        MeService service = new MeService(context);
        service.requestOrder(page,orderstat ,new StringCallBackView() {
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
    public void submitDelOrd(Context context, String ordernunm, String usetype, final RequestResulteView view) {
        MeService service = new MeService(context);
        service.submitMarkorder(ordernunm, usetype, new StringCallBackView() {
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

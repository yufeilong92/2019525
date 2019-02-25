package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.AddressContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.MeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/8/15 17:28
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AddressModel implements AddressContract.Model {
    /**
     * 请求所有地址
     *
     * @param context
     * @param requestResulteView
     */
    @Override
    public void requestAllAddress(Context context, int page, final RequestResulteView requestResulteView) {
        MeService service = new MeService(context);
        service.requestAllAddress(page, new StringCallBackView() {
            @Override
            public void onSuccess(String success) {
                requestResulteView.success(success);
            }

            @Override
            public void onError(String msg) {
                requestResulteView.error(msg);
            }
        });
    }

    @Override
    public void submitOrDelectDafaultAddress(Context context, int addressid, int tag,final RequestResulteView view) {
        MeService service = new MeService(context);
        service.submitdefaultOrDelectAddress(addressid, tag,new StringCallBackView() {
            @Override
            public void onSuccess(String success) {
                view.success(success);
            }

            @Override
            public void onError(String msg) {
                view.error(msg);
            }
        });
    }


}

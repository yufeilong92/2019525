package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.NetMyClassContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.NetService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.11.29 上午 11:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetMyClassModel implements NetMyClassContract.Model {
    @Override
    public void requestNetMyClass(Context context, final RequestResulteView resulteView) {
        NetService service = NetService.getInstance(context);
        service.requestMyClass(new StringCallBackView() {
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

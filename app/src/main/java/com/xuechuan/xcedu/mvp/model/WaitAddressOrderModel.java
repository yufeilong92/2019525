package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.WaitAddressOrderContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.MeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/8/30 11:00
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class WaitAddressOrderModel implements WaitAddressOrderContract.Model {
    @Override
    public void requestWaitAddressOrder(Context context, final RequestResulteView view) {
        MeService service = new MeService(context);
        service.requestWaitAddressOrder(new StringCallBackView() {
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

    @Override
    public void submitConfimAddress(Context context, int addressid, final RequestResulteView view) {
        MeService service = new MeService(context);
        service.submitConfirmAddress(addressid, new StringCallBackView() {
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

package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.contract.SettingViewContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.MeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 请求版本
 * @author: L-BackPacker
 * @date: 2018/5/28 18:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SettingViewModel implements SettingViewContract.Model {
    @Override
    public void requestAppCode(Context context, final RequestResulteView view) {
        MeService service = new MeService(context);
        service.requestAppUpdata(new StringCallBackView() {
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
    public void submitBindWeiXin(Context context, String code, final RequestResulteView view) {
        MeService service = new MeService(context);
        service.submitBindWechat(code, new StringCallBackView() {
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
    public void requestOutLogin(Context context, final RequestResulteView view) {
        MeService service = new MeService(context);
        service.requestOutLogin(new StringCallBackView() {
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

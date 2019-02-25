package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.LoginService;
import com.xuechuan.xcedu.net.WeiXinLoginSercvice;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/2 13:01
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class LoginModelImpl implements LoginModel {

    @Override
    public void getLoginContent(Context context, String code, final RequestResulteView view) {
        WeiXinLoginSercvice instance = WeiXinLoginSercvice.getInstance(context);
        instance.requestWeiCode(code, new StringCallBackView() {
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
    public void getPawLoginContent(Context context, String username, String paw, final RequestResulteView view) {
        LoginService service = LoginService.getInstance(context);
        service.requestlogin(username, paw, new StringCallBackView() {
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

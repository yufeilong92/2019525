package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.LoginService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 刷新token
 * @author: L-BackPacker
 * @date: 2018/5/9 14:37
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class RefreshTokenModelImpl implements RefreshTokenModel {


    @Override
    public void Refresh(Context context, String oldtoken, final RequestResulteView view) {
        LoginService loginService = new LoginService(context);
        loginService.requestRefeshToken(oldtoken, new StringCallBackView() {
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

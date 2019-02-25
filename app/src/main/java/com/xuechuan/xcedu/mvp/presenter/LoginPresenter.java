package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.model.LoginModel;
import com.xuechuan.xcedu.mvp.view.LoginView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/2 12:58
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class LoginPresenter {
    LoginModel model;
    LoginView view;

    public LoginPresenter(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * 微信登录
     * @param context
     * @param code
     */
    public void getWeiXinLoginContent(Context context, String code) {
        if (StringUtil.isEmpty(code)) {
            return;
        }
        model.getLoginContent(context, code, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.WeiXinLoginSuccess(result);
            }

            @Override
            public void error(String result) {
                view.WeiXinLoginError(result);
            }
        });
    }

    /**
     * 密码登录
     * @param context
     * @param username
     * @param paw
     */
    public void getLoginContent(Context context, String username, String paw) {
        if (StringUtil.isEmpty(username)) {
            return;
        }
        if (StringUtil.isEmpty(paw)) {
            return;
        }
        model.getPawLoginContent(context, username, paw, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.LoginSuccess(result);
            }

            @Override
            public void error(String result) {
                view.LoginError(result);
            }
        });
    }
}

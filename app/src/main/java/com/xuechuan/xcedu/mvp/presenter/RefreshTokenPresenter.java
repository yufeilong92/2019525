package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.model.RefreshTokenModel;
import com.xuechuan.xcedu.mvp.model.RefreshTokenModelImpl;
import com.xuechuan.xcedu.mvp.view.RefreshTokenView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/9 14:37
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class RefreshTokenPresenter {
    private RefreshTokenModel model;
    private RefreshTokenView view;

    public RefreshTokenPresenter(RefreshTokenModel model, RefreshTokenView view) {
        this.model = model;
        this.view = view;
    }

    public void refreshToken(Context context, String oldtoken) {
        if (StringUtil.isEmpty(oldtoken)) {
            return;
        }
        model.Refresh(context, oldtoken, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.TokenSuccess(result);
            }

            @Override
            public void error(String result) {
                view.TokenError(result);
            }
        });
    }
}

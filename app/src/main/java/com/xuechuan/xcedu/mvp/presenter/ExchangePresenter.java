package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.model.AddVauleModel;
import com.xuechuan.xcedu.mvp.model.ExchangeModel;
import com.xuechuan.xcedu.mvp.view.AddVauleView;
import com.xuechuan.xcedu.mvp.view.ExchangeView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 正版验证
 * @author: L-BackPacker
 * @date: 2018/5/22 11:54
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ExchangePresenter {
    private ExchangeModel model;
    private ExchangeView view;

    public ExchangePresenter(ExchangeModel model, ExchangeView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * 请求兑换码
     * @param context
     * @param code
     */
    public void requestExchangeWithCode(Context context, String code) {
        if (StringUtil.isEmpty(code)) {
            return;
        }
        model.requestExchange(context, code, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.ExchangeSuccess(result);
            }

            @Override
            public void error(String result) {
                view.ExchangeError(result);
            }
        });

    }
}

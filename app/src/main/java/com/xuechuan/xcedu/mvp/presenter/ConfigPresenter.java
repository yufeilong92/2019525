package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.ConfigContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 请求配置
 * @author: L-BackPacker
 * @date: 2018.11.27 上午 10:41
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ConfigPresenter implements ConfigContract.Presenter {
    ConfigContract.Model model;
    ConfigContract.View view;

    @Override
    public void initModelView(ConfigContract.Model model, ConfigContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestConfig(Context context) {
        model.requestConfig(context, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.ConfigSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.ConfigError(msg);
            }
        });
    }
}

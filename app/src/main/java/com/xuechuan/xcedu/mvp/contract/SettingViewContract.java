package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: 请求版本更新
 * @author: L-BackPacker
 * @date: 2018/5/28 18:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface SettingViewContract {
    interface Model {
        public void requestAppCode(Context context, RequestResulteView view);

        public void submitBindWeiXin(Context context, String code, RequestResulteView view);
        public void requestOutLogin(Context context,RequestResulteView view);
    }

    interface View {
        public void AppCodeSuccess(String cont);

        public void AppCodeError(String msg);

        public void submitBindWeiXin(String com);

        public void submitBindWeiXinError(String com);

        public void OutSuccess(String com);

        public void OutError(String msg);
    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void submitBindWeiXin(Context context, String code);

        public void requestAppCode(Context context);

        public void requestOutLogin(Context context);
    }
}

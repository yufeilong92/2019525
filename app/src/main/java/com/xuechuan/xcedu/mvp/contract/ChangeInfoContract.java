package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: 1.0.8
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/8/24 9:36
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface ChangeInfoContract {
    interface Model {
        public void requestChangeCode(Context context, String code, RequestResulteView view);
    }

    interface View {
        public void ChangeInfomSuccess(String result);

        public void ChangeInfomError(String msg);
    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void requestChangeCode(Context context, String code);
    }
}

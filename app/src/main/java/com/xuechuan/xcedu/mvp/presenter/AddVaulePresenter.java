package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.model.AddVauleModel;
import com.xuechuan.xcedu.mvp.view.AddVauleView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/22 11:54
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AddVaulePresenter {
    private AddVauleModel model;
    private AddVauleView view;

    public AddVaulePresenter(AddVauleModel model, AddVauleView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * 请求兑换码
     * @param context
     * @param code
     */
    public void requestAddValueWithCode(Context context, String code) {
        if (StringUtil.isEmpty(code)) {
            return;
        }
        model.requestAddValue(context, code, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.AddVauleSuccess(result);
            }

            @Override
            public void error(String result) {
                view.AddVauleError(result);
            }
        });

    }
}

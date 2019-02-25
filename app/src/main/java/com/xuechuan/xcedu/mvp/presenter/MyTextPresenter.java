package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.MyTextContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/26 17:58
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyTextPresenter implements MyTextContract.Presenter {
    MyTextContract.Model model;
    MyTextContract.View view;

    @Override
    public void initModelView(MyTextContract.Model model, MyTextContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void reuqestBookId(Context context) {
        model.requestBookId(context, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.BookIDSuccess(result);
            }

            @Override
            public void error(String result) {
                view.BookIDError(result);
            }
        });
    }
}

package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.BookInfomContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.11.15 上午 9:46
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BookInfomPresenter implements BookInfomContract.Presenter {
    BookInfomContract.Model model;
    BookInfomContract.View view;

    @Override
    public void initModelView(BookInfomContract.Model model, BookInfomContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void RequestCapterListOne(Context context, String oid) {
        model.RequestCapterListOne(context, oid, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.CapterListSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.CapterListError(msg);
            }
        });
    }
}


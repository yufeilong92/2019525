package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.NetCommentContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 课程评价
 * @author: L-BackPacker
 * @date: 2018.11.28 上午 11:23
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetCommentPresenter implements NetCommentContract.Presenter {
    NetCommentContract.Model model;
    NetCommentContract.View view;

    @Override
    public void initModelView(NetCommentContract.Model model, NetCommentContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestNetCommentOne(Context context, int page, String  productid) {
        model.requestNetComment(context, page, productid, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.CommentSuccessOne(success);
            }

            @Override
            public void error(String msg) {
                view.CommentErrorOne(msg);
            }
        });
    }

    @Override
    public void requestNetCommentMore(Context context, int page, String productid) {
        model.requestNetComment(context, page, productid, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.CommentSuccessTwo(success);
            }
            @Override
            public void error(String msg) {
                view.CommentErrorTwo(msg);
            }
        });
    }
}

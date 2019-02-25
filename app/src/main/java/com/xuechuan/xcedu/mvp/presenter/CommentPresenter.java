package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.CommentContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 获取评价接口
 * @author: L-BackPacker
 * @date: 2018/7/31 10:54
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class CommentPresenter implements CommentContract.Presenter {
    CommentContract.Model model;
    CommentContract.View view;

    @Override
    public void initModelView(CommentContract.Model model, CommentContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestComment(Context context, int page,String targettype, String targetid) {
        model.requestComment(context,page,targettype, targetid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.commentSuccess(result);
            }

            @Override
            public void error(String result) {
                view.commentError(result);
            }
        });


    }

    @Override
    public void requestCommentMore(Context context, int page, String targettype, String targetid) {

    }
}

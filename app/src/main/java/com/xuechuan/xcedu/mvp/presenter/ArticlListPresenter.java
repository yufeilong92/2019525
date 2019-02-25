package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.ArticlListContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 文章管理器
 * @author: L-BackPacker
 * @date: 2018.11.15 上午 9:43
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ArticlListPresenter implements ArticlListContract.Presenter {
    ArticlListContract.Model model;
    ArticlListContract.View view;

    @Override
    public void initModelView(ArticlListContract.Model model, ArticlListContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void RequestArticleListOne(Context context, int staffid, int page) {
        model.RequestArticleList(context, staffid, page, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.ArticleListOneSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.ArticleListOneError(msg);
            }
        });
    }

    @Override
    public void RequestArticleListTwo(Context context, int staffid, int page) {
        model.RequestArticleList(context, staffid, page, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.ArticleListTwoSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.ArticleListTwoError(msg);
            }
        });
    }
}

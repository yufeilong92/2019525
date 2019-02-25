package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.model.ArticleTagModel;
import com.xuechuan.xcedu.mvp.view.ArticleTagView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 文章tag
 * @author: L-BackPacker
 * @date: 2018/5/13 12:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ArticleTagPresenter {
    private ArticleTagModel model;
    private ArticleTagView view;

    public ArticleTagPresenter(ArticleTagModel model, ArticleTagView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * 请求tag 集合
     *
     * @param context
     */
    public void reqeustTagList(Context context) {
        model.reqeustTaglist(context, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.ArticleTagSuccess(result);
            }

            @Override
            public void error(String result) {
                view.ArticleTagError(result);
            }
        });
    }


}

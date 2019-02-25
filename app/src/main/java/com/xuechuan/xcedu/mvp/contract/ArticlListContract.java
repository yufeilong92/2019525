package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.11.15 上午 9:43
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface ArticlListContract {
    interface Model {
        public  void RequestArticleList(Context context, int staffid, int page, RequestResulteView resulteView);
    }

    interface View {
        public void ArticleListOneSuccess(String success);
        public void ArticleListOneError(String msg);
        public void ArticleListTwoSuccess(String success);
        public void ArticleListTwoError(String msg);
    }

    interface Presenter {
        public void initModelView(Model model, View view);
        public  void RequestArticleListOne(Context context,int staffid, int page);
        public  void RequestArticleListTwo(Context context,int staffid, int page);

    }
}
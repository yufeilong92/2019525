package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.mvp.model.SearchModel;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.mvp.view.SearchView;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 搜素
 * @author: L-BackPacker
 * @date: 2018/5/8 17:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SearchPresenter {
    private SearchModel model;
    private SearchView view;

    public SearchPresenter(SearchModel model, SearchView view) {
        this.model = model;
        this.view = view;
    }

    //热词
    public void requestHostList(Context context, String num) {
        if (StringUtil.isEmpty(num))
            return;
        model.reqeustHostContent(context, num, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.HostSuccess(result);
            }

            @Override
            public void error(String result) {
                view.HostError(result);
            }
        });
    }

    //结果
    public void requestReusltCont(Context context, int page,String key, String usetype) {
        if (StringUtil.isEmpty(key) || StringUtil.isEmpty(usetype))
            return;
        model.reqeustResultContent(context,page, key, usetype, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.ResultSuccess(result);
            }

            @Override
            public void error(String result) {
                view.ResultError(result);
            }
        });
    }
    //结果
    public void requestMoreReusltCont(Context context, int page,String key, String usetype) {
        if (StringUtil.isEmpty(key) || StringUtil.isEmpty(usetype))
            return;
        model.reqeustResultContent(context,page, key, usetype, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.ResultMoreSuccess(result);
            }

            @Override
            public void error(String result) {
                view.ResultMoreError(result);
            }
        });
    }
}

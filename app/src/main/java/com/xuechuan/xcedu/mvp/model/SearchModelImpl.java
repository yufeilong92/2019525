package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.mvp.view.SearchView;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 搜索
 * @author: L-BackPacker
 * @date: 2018/5/8 17:39
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SearchModelImpl implements SearchModel {

    /**
     * 请求热词
     * @param context
     * @param num
     * @param view
     */
    @Override
    public void reqeustHostContent(Context context, String num, final RequestResulteView view) {
        HomeService service = new HomeService(context);
        service.requestHost(num, new StringCallBackView() {
            @Override
            public void onSuccess(String response) {
                view.success(response);
            }

            @Override
            public void onError(String response) {
                view.error(response);
            }
        });

    }

    /**
     * 请求搜索内容
     * @param context
     * @param key
     * @param usetype
     * @param view
     */
    @Override
    public void reqeustResultContent(Context context,int page, String key, String usetype, final RequestResulteView view) {
        HomeService service = new HomeService(context);
        service.requestResultList(page,key, usetype, new StringCallBackView() {
            @Override
            public void onSuccess(String response) {
                view.success(response);
            }

            @Override
            public void onError(String response) {
                view.error(response);
            }
        });
    }
}

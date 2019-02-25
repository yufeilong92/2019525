package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.NetService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 网课首页
 * @author: L-BackPacker
 * @date: 2018/5/13 16:10
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetHomeModelImpl implements NetHomeModel {
    /**
     *获取网课首页内容
     * @param context
     * @param view
     */
    @Override
    public void requestHomeList(Context context, final RequestResulteView view) {
        NetService service = new NetService(context);
        service.requestClassAndproductsList(new StringCallBackView() {
            @Override
            public void onSuccess(String msg) {
                view.success(msg);
            }

            @Override
            public void onError(String response) {
                view.error(response);
            }
        });

    }
}

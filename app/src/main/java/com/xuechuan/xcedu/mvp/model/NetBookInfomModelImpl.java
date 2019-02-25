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
 * @Description: 网课详情
 * @author: L-BackPacker
 * @date: 2018/5/14 16:32
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetBookInfomModelImpl implements NetBookInfomModel {
    /**
     * 请求视频详情
     *
     * @param context
     * @param classid
     * @param view
     */
    @Override
    public void requestVideoInfom(Context context, int page, String classid, final RequestResulteView view) {
        NetService service = new NetService(context);
        service.requestProductdetail(classid, new StringCallBackView() {
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

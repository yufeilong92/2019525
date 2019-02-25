package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.model.NetBookInfomModel;
import com.xuechuan.xcedu.mvp.view.NetBookInfomView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/14 16:31
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetBookInfomPresenter {

    private NetBookInfomModel model;
    private NetBookInfomView view;

    public NetBookInfomPresenter(NetBookInfomModel model, NetBookInfomView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * 请求课程的列表
     *
     * @param context
     * @param calssid
     */
    public void requestVideoBookOneList(Context context, int page, String calssid) {
        model.requestVideoInfom(context, page, calssid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.VideoInfomSuccess(result);
            }

            @Override
            public void error(String result) {
                view.VideoInfomError(result);
            }
        });

    }
    /**
     * 请求课程的列表
     *
     * @param context
     * @param calssid
     */
    public void requestVideoBookMoreList(Context context, int page, String calssid) {
        model.requestVideoInfom(context, page, calssid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.VideoInfomMoreSuccess(result);
            }

            @Override
            public void error(String result) {
                view.VideoInfomMoreError(result);
            }
        });

    }
}

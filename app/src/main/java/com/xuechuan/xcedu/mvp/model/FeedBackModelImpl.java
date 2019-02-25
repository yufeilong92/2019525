package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.MeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 提交反馈
 * @author: L-BackPacker
 * @date: 2018/5/22 10:09
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class FeedBackModelImpl implements FeedBackModel {
    /**
     * 提交用户建议提交
     * @param context
     * @param suggest
     * @param phone
     * @param view
     */
    @Override
    public void submitFeedBack(Context context, String suggest, String phone, final RequestResulteView view) {
        MeService meService = new MeService(context);
        meService.submitAdvice( suggest, phone, new StringCallBackView() {
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

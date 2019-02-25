package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.contract.CommentContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/7/31 10:54
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class CommentModel implements CommentContract.Model {

    @Override
    public void requestComment(Context context, int page, String targettype, String targetid, final RequestResulteView view) {
        HomeService homeService = new HomeService(context);
        homeService.requestCommentCommentData(page, targettype, targetid, new StringCallBackView() {
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

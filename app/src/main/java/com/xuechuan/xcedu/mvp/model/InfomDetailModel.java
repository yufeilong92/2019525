package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.contract.InfomDetailContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 根据文章编号获取文章详情
 * @author: L-BackPacker
 * @date: 2018/6/1 10:28
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class InfomDetailModel implements InfomDetailContract.Model {
    @Override
    public void requestGetDetail(Context context, String atricleid, final RequestResulteView view) {
        HomeService service = new HomeService(context);
        service.requestGetDetail(atricleid, new StringCallBackView() {
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

    @Override
    public void requestEvalueDetail(Context context, int page, String targettype, String targetid, final RequestResulteView view) {
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

/*    @Override
    public void requestEvalueDetail(Context context, String articleid, int page, final RequestResulteView view) {
        HomeService bankService = new HomeService(context);
        bankService.requestArticleCommentList(articleid, page, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                view.success(response.body().toString());
            }

            @Override
            public void onError(Response<String> response) {
                view.error(response.message());
            }
        });

    }*/
}

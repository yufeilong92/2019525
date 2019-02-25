package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 我的错题
 * @author: L-BackPacker
 * @date: 2018/5/3 10:32
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ErrorTextTextModelImpl implements ErrorTextModel {
    /**
     * 获取用户错题/收藏题统计信息
     * @param context
     * @param courseid
     * @param tagtype
     * @param view
     */
    @Override
    public void requestErrorNumber(Context context, String courseid, String tagtype, final RequestResulteView view) {
        BankService bankService = new BankService(context);
        bankService.requestQuestionTagScount(courseid, tagtype, new StringCallBackView() {
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
     * 题库首页获取错题和收藏数
     * @param context
     * @param courseid
     * @param view
     */
    @Override
    public void reqeustErrOrCoNumber(Context context, String courseid,final RequestResulteView view) {
        BankService service = new BankService(context);
        service.requestErrSetandFav(courseid, new StringCallBackView() {
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

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
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/7 18:51
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ErrOrColListModelImpl implements ErrOrColListModel {

    @Override
    public void reqeustOneEvalueContent(Context context, String courseid, String tagid, String tagtype, final RequestResulteView view) {
        BankService bankService = new BankService(context);
        bankService.requestQuestionTagids(courseid, tagtype, tagid, new StringCallBackView() {
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

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
 * @date: 2018/5/3 20:52
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ExamModelImpl implements ExamModel {

    @Override
    public void requestContent(Context context, String courseid, final RequestResulteView view) {
        BankService service = new BankService(context);
        service.requestExamchapte(courseid, new StringCallBackView() {
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

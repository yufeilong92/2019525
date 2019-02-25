package com.xuechuan.xcedu.mvp.model;

import android.content.Context;
import android.view.View;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.mvp.view.SpecailDetailView;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 根据 tagid 获取下面所有题号
 * @author: L-BackPacker
 * @date: 2018/5/5 19:02
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SpecailDetailModelImpl implements SpecailDetailModel {
    @Override
    public void requestSpecailDetail(Context context, String courseid, String tagid, final RequestResulteView view) {
        BankService bankService = new BankService(context);
        bankService.requestUestionIdSbyTagid(courseid, tagid, new StringCallBackView() {
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

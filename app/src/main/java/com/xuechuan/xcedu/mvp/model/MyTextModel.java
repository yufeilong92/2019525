package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.contract.MyTextContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 我的题库课目
 * @author: L-BackPacker
 * @date: 2018/5/26 17:58
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyTextModel implements MyTextContract.Model {
    @Override
    public void requestBookId(Context context,final RequestResulteView view) {
        BankService service = new BankService(context);
        service.requestBankProduct(new StringCallBackView() {
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

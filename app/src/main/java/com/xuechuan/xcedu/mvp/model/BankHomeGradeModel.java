package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.google.android.exoplayer.C;
import com.xuechuan.xcedu.mvp.contract.BankHomeGradeContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 题库更新版本
 * @author: L-BackPacker
 * @date: 2018.12.06 下午 3:20
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BankHomeGradeModel implements BankHomeGradeContract.Model {
    @Override
    public void requestGrade(Context context, String code, final RequestResulteView resulteView) {
        BankService service = BankService.getInstance(context);
        service.requestBankGrade(code, new StringCallBackView() {
            @Override
            public void onSuccess(String success) {
                resulteView.success(success);
            }

            @Override
            public void onError(String msg) {
                resulteView.error(msg);
            }
        });
    }
}

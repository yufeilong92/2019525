package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.EveyDayDetailContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.29 下午 1:49
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class EveyDayDetailModel implements EveyDayDetailContract.Model {
    @Override
    public void requestionDayDetail(Context context, int id, final RequestResulteView resulteView) {
        BankService service = BankService.getInstance(context);
        service.requestEveryDetail(id, new StringCallBackView() {
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

    @Override
    public void submitUserDayTest(Context context, int exerciseid, int duration, String accuracy, final RequestResulteView resulteView) {
        BankService service = BankService.getInstance(context);
        service.submitUserDoTestlog(exerciseid, duration, accuracy, new StringCallBackView() {
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

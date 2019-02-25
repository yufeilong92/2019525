package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.GmSubmitComContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.CurrencyService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 提交评价
 * @author: L-BackPacker
 * @date: 2019.01.03 下午 3:11
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class GmSubmitComModel implements GmSubmitComContract.Model {
    @Override
    public void submiteCommont(Context context, String tagetid, String comment, String commenttid, String usertype, final RequestResulteView resulteView) {
        CurrencyService service = CurrencyService.getInstance(context);
        service.submitConmment(tagetid, comment, commenttid, usertype, new StringCallBackView() {
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

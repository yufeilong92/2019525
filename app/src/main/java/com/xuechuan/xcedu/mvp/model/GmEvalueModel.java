package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.GmEvalueContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.19 上午 11:37
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class GmEvalueModel implements GmEvalueContract.Model {
    @Override
    public void reqeustEvalueContent(Context context, int page, String targettype, String targetid, final RequestResulteView resulteView) {
        HomeService service = new HomeService(context);
        service.requestCommentCommentData(page, targettype, targetid, new StringCallBackView() {
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
    public void reqeustNoteByQuestion(Context context, int question, final RequestResulteView resulteView) {
        BankService service = BankService.getInstance(context);
        service.requestNoteQuestion(question, new StringCallBackView() {
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

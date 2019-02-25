package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.ExamHistoryContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.09 下午 3:20
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class ExamHistoryModel implements ExamHistoryContract.Model {
    @Override
    public void requestExamHistory(Context context, int page, final RequestResulteView view) {
        BankService service = BankService.getInstance(context);
        service.requestExamHistoruList(page, new StringCallBackView() {
            @Override
            public void onSuccess(String success) {
                view.success(success);
            }

            @Override
            public void onError(String msg) {
                view.error(msg);
            }
        });
    }

    @Override
    public void deleteExamHistory(Context context, int historyid, final RequestResulteView view) {
        BankService service = BankService.getInstance(context);
        service.deleteHistoryItem(historyid, new StringCallBackView() {
            @Override
            public void onSuccess(String success) {
                view.success(success);
            }

            @Override
            public void onError(String msg) {
                view.error(msg);
            }
        });
    }

    @Override
    public void requestExamDetail(Context context, int historyid, final RequestResulteView view) {
        BankService service = BankService.getInstance(context);
        service.requestExamHistoruDetail(historyid, new StringCallBackView() {
            @Override
            public void onSuccess(String success) {
                view.success(success);
            }

            @Override
            public void onError(String msg) {
                view.error(msg);
            }
        });
    }
}

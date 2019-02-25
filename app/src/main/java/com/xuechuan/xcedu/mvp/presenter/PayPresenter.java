package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.google.android.exoplayer.C;
import com.xuechuan.xcedu.mvp.model.PayModel;
import com.xuechuan.xcedu.mvp.view.PayView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.utils.StringUtil;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 支付控制
 * @author: L-BackPacker
 * @date: 2018/5/22 17:45
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class PayPresenter {
    private PayModel model;
    private PayView view;

    public PayPresenter(PayModel model, PayView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * 下单
     *
     * @param context
     * @param usebalance
     * @param products
     * @param ordersource
     * @param remark
     */
    public void submitPayFrom(Context context, String usebalance, List<Integer> products, String ordersource, final String remark) {
        if ( StringUtil.isEmpty(ordersource)) {
            return;
        }
        model.sumbitPayFrom(context, usebalance, products, ordersource, remark, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.SumbitFromSuccess(result);
            }

            @Override
            public void error(String result) {
                view.SumbitFromError(result);
            }
        });

    }

    /**
     * 支付
     *
     * @param context
     * @param orderNum
     * @param paytype
     */
    public void submitPay(Context context, String orderNum, int paytype) {
        if (StringUtil.isEmpty(orderNum) ||paytype==-1) {
            return;
        }
        model.submitPay(context, orderNum, paytype, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.SumbitPaySuccess(result);
            }

            @Override
            public void error(String result) {
                view.SumbitPayError(result);
            }
        });
    }

    /**
     * 获取课程id
     * @param context
     */
    public void reuqestBookId(Context context) {
        model.requestBookId(context, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.BookIDSuccess(result);
            }

            @Override
            public void error(String result) {
                view.BookIDError(result);
            }
        });
    }

}

package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.PayService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 支付
 * @author: L-BackPacker
 * @date: 2018/5/22 17:41
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class PayModelImpl implements PayModel {
    /**
     * 提交支付表单
     *
     * @param context
     * @param usebalance  使用余额
     * @param products    产品编号集合
     * @param ordersource app，定值
     * @param remark      用户备注信息
     * @param view
     */
    @Override
    public void sumbitPayFrom(Context context, String usebalance, List<Integer> products, String ordersource, final String remark, final RequestResulteView view) {
        PayService service = new PayService(context);
        service.submitPayForm(usebalance, products, ordersource, remark, new StringCallBackView() {
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

    /**
     * 支付
     * @param context
     * @param ordernum
     * @param paytype
     * @param view
     */

    @Override
    public void submitPay(Context context, String ordernum, int paytype, final RequestResulteView view) {
        PayService service = new PayService(context);
        service.submitPay(ordernum, paytype, new StringCallBackView() {
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

    /**
     * 获取课程
     * @param context
     * @param view
     */
    @Override
    public void requestBookId(Context context, final RequestResulteView view) {
        BankService service = new BankService(context);
        service.requestBankProduct(new StringCallBackView() {
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

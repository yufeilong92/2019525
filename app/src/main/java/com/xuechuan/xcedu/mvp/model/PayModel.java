package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.google.android.exoplayer.C;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

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
public interface PayModel {
    /**
     * 下单
     *
     * @param context
     * @param usebalance
     * @param products
     * @param ordersource
     * @param remark
     * @param view
     */
    public void sumbitPayFrom(Context context, String usebalance, List<Integer> products,
                              String ordersource, String remark, RequestResulteView view);

    /**
     * 支付
     * @param context
     * @param ordernum
     * @param paytype
     * @param view
     */
    public void submitPay(Context context, String ordernum, int paytype, RequestResulteView view);

    /**
     * 获取科目id
     * @param context
     * @param view
     */
    public void requestBookId(Context context,RequestResulteView view);

}

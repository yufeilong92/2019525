package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.VideoOrderContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.PayService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/8/25 9:00
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class VideoOrderModel implements VideoOrderContract.Model {
    @Override
    public void sumbitPayFrom(Context context, String usebalance, List<Integer> products, String ordersource, String remark, int addressid, final RequestResulteView view) {
        PayService service = new PayService(context);
        service.submitVideoPayForm(usebalance, products, ordersource, remark, addressid, new StringCallBackView() {
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

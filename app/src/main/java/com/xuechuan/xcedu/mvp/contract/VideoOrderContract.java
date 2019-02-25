package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.PayService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: 视频下单
 * @author: L-BackPacker
 * @date: 2018/8/25 9:00
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface VideoOrderContract {
    interface Model {
        public void sumbitPayFrom(Context context, String usebalance, List<Integer> products, String ordersource, final String remark, int addressid, RequestResulteView view);
    }

    interface View {
        public void paySuccess(String result);

        public void payError(String msg);
    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void sumbitPayFrom(Context context, String usebalance, List<Integer> products, String ordersource, final String remark, int addressid);
    }
}

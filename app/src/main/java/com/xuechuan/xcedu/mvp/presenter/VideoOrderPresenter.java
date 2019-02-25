package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.VideoOrderContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 视频下单
 * @author: L-BackPacker
 * @date: 2018/8/25 9:00
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class VideoOrderPresenter implements VideoOrderContract.Presenter {
    VideoOrderContract.Model model;
    VideoOrderContract.View view;

    @Override
    public void initModelView(VideoOrderContract.Model model, VideoOrderContract.View view) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void sumbitPayFrom(Context context, String usebalance, List<Integer> products, String ordersource, String remark, int addressid) {
        model.sumbitPayFrom(context, usebalance, products, ordersource, remark, addressid, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.paySuccess(success);
            }

            @Override
            public void error(String msg) {
                view.payError(msg);
            }
        });
    }
}

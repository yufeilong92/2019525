package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.MyMsgContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.MeService;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 我的消息
 * @author: L-BackPacker
 * @date: 2018/5/30 17:09
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyMsgPresenter implements MyMsgContract.Presenter {
    MyMsgContract.Model model;
    MyMsgContract.View view;

    @Override
    public void initModelView(MyMsgContract.Model model, MyMsgContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestMyMsg(Context context, int page) {
        model.requestMyMsg(context, page, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.MyMsgSuccess(result);
            }

            @Override
            public void error(String result) {
                view.MyMsgError(result);
            }
        });
    }

    @Override
    public void requestMyMsgMore(Context context, int page) {
        model.requestMyMsg(context, page, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.MyMsgMoreSuccess(result);
            }

            @Override
            public void error(String result) {
                view.MyMsgMoreError(result);
            }
        });
    }

    @Override
    public void submitDelMyMsg(Context context, List<Integer> ids) {
        model.submitDelMyMsg(context, ids, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.DelMyMsg(result);
            }

            @Override
            public void error(String result) {
                view.DelMyError(result);
            }
        });
    }
}

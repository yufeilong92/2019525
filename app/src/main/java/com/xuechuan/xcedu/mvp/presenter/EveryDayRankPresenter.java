package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.EveryDayRankContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.29 下午 2:36
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class EveryDayRankPresenter implements EveryDayRankContract.Presenter {
    EveryDayRankContract.Model model;
    EveryDayRankContract.View view;

    @Override
    public void initModelView(EveryDayRankContract.Model model, EveryDayRankContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestRank(Context context, int id) {
        model.requestRank(context, id, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.RankSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.RankError(msg);
            }
        });
    }

    @Override
    public void requestHistory(Context context, int courseid) {
        model.requestHistory(context, courseid, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.HistorySuccess(success);
            }

            @Override
            public void error(String msg) {
                view.HistoryError(msg);
            }
        });
    }
}

package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.EveyDayDetailContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.29 下午 1:49
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class EveyDayDetailPresenter implements EveyDayDetailContract.Presenter {
    EveyDayDetailContract.Model model;
    EveyDayDetailContract.View view;

    @Override
    public void initModelView(EveyDayDetailContract.Model model, EveyDayDetailContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestionDayDetail(Context context, int id) {
        model.requestionDayDetail(context, id, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.DayDetailSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.DayDetailError(msg);
            }
        });
    }

    @Override
    public void submitUserDayTest(Context context, int exerciseid, int duration, String accuracy) {
        model.submitUserDayTest(context, exerciseid, duration, accuracy, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.SubmitUserDoSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.SubmitUserDoError(msg);
            }
        });
    }
}

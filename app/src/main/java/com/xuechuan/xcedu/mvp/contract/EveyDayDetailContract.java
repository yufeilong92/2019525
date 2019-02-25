package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.29 下午 1:49
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface EveyDayDetailContract {
    interface Model {
        public void requestionDayDetail(Context context, int id, RequestResulteView resulteView);

        public void submitUserDayTest(Context context, int exerciseid, int duration, String accuracy,RequestResulteView resulteView);
    }

    interface View {
        public void DayDetailSuccess(String success);

        public void DayDetailError(String msg);

        public void SubmitUserDoSuccess(String success);

        public void SubmitUserDoError(String success);

    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void requestionDayDetail(Context context, int id);
        public void submitUserDayTest(Context context, int exerciseid, int duration, String accuracy);

    }
}

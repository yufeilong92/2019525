package com.xuechuan.xcedu.mvp.contract;

import android.content.ContentValues;
import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.29 下午 2:36
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface EveryDayRankContract {
    interface Model {
        public void requestRank(Context context, int id, RequestResulteView resulteView);

        public void requestHistory(Context context, int courseid, RequestResulteView resulteView);
    }

    interface View {
        public void RankSuccess(String success);

        public void RankError(String msg);

        public void HistorySuccess(String success);

        public void HistoryError(String msg);
    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void requestRank(Context context, int id);

        public void requestHistory(Context context, int courseid);
    }
}

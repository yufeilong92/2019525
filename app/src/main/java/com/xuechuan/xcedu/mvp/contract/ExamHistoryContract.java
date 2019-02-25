package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.09 下午 3:20
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public interface ExamHistoryContract {
    interface Model {
        public void requestExamHistory(Context context, int page, RequestResulteView view);

        public void deleteExamHistory(Context context, int historyid, RequestResulteView view);

        public void requestExamDetail(Context context, int historyid, RequestResulteView view);
    }

    interface View {
        public void ExamHistorySuccessOne(String success);

        public void ExamHistoryErrrorOne(String msg);

        public void ExamHistorySuccessTwo(String success);

        public void ExamHistoryErrrorTwo(String msg);

        public void deleteHistorySuccess(String success);

        public void deleteHistoryError(String msg);

        public void ExamHistoryDetailSuccess(String success);

        public void ExamHistoryDetailError(String msg);
    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void deleteExamHistory(Context context, int historyid);

        public void requestExamHistoryOne(Context context, int page);

        public void requestExamHistoryTwo(Context context, int page);

        public void requestExamDetail(Context context, int historyid);
    }
}

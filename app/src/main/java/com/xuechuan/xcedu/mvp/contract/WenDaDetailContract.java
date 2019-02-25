package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.10 上午 11:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public interface WenDaDetailContract {
    interface Model {
        public void requestPreblemDetail(Context context, int problemid, RequestResulteView resulteView);

        public void submitProblemEvaluet(Context context, int problemid, String comment, int score, RequestResulteView resulteView);
    }

    interface View {
        public void ProblemDetailSuccess(String success);

        public void ProblemDetailError(String msg);

        public void ProblemEvalueSuccess(String success);
        public void ProblemEvalueError(String msg);
    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void requestPreblemDetail(Context context, int problemid);

        public void submitProblemEvaluet(Context context, int problemid, String comment, int score);
    }
}

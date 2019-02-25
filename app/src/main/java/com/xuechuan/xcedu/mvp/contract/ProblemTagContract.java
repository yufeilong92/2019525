package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.02.13 上午 10:00
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public interface ProblemTagContract {
    interface Model {
        public void requestProblemtags(Context context, RequestResulteView resulteView);
    }

    interface View {
        public void ProblemTagSuccess(String success);

        public void ProblemTagError(String msg);
    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void requestProblemtags(Context context);
    }
}

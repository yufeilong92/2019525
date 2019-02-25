package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.06 下午 3:20
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface BankHomeGradeContract {
    interface Model {
        public void requestGrade(Context context, String code, RequestResulteView resulteView);
    }

    interface View {
        public void GradeSuccess(String success);

        public void GradeError(String msg);
    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void reuqestGrade(Context context, String code);
    }
}

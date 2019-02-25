package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.lzy.okgo.model.Progress;
import com.xuechuan.xcedu.mvp.view.RequestResulteUpView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.view.StringCallBackUpView;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.10 上午 10:22
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public interface WenDaContract {
    interface Model {
        public void requestWaitQuesiton(Context context, RequestResulteView resulteView);

        public void requestHankpickLists(Context context, int page, RequestResulteView resulteView);

        public void requestMyQuestionLists(Context context, int page, RequestResulteView resulteView);


    }

    interface View {
        public void WaitQuestionSuccess(String success);

        public void WaitQuestionError(String msg);

        public void HankPicksuccessOne(String success);

        public void HankPickErrorOne(String msg);

        public void HankPicksuccessTwo(String success);

        public void HankPickErrorTwo(String msg);

        public void MyQuestionSuccessOne(String success);

        public void MyQuestionErrorOne(String msg);

        public void MyQuestionSuccessTwo(String success);

        public void MyQuestionErrorTwo(String msg);

    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void requestWaitQuesiton(Context context);

        public void requestHankpickListsOne(Context context, int page);

        public void requestHankpickListsTwo(Context context, int page);

        public void requestMyQuestionListsOne(Context context, int page);

        public void requestMyQuestionListsTwo(Context context, int page);


    }
}

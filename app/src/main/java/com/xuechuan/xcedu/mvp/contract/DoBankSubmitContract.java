package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.08 上午 10:29
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public interface DoBankSubmitContract {
    interface Model {
        public void submitDoBank(Context context, String time, String history, RequestResulteView resulteView);
    }

    interface View {
       public void DoBankSuecces(String success);
       public void DoBankError(String msg);
    }

    interface Presenter {
         public void initModelView(Model model, View view);
        public void submitDoBank(Context context, String time, String history);
    }
}

package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.08 下午 4:46
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public interface RequestCollectContract {
    interface Model {
        public void requestCollect(Context context, RequestResulteView view);
    }

    interface View {

        public void getCollectSuccess(String success);

        public void getCollectError(String msg);
    }


    interface Presenter {
         public void initModelView(Model model, View view);
        public void requestCollect(Context context);

    }
}

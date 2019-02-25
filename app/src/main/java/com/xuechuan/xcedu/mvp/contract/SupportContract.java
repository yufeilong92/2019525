package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/6/1 18:59
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface SupportContract {
    interface Model {
        public void submitSupport(Context context, String targetid,
                                  String isSupper, String usetype, RequestResulteView view);
    }

    interface View {
        public void SupportSuc(String con);

        public void SupportErr(String con);

    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void submitSupport(Context context, String targetid,
                                  String isSupper, String usetype);

    }
}

package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/31 19:33
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface ChangPawContract {
    interface Model {
        public void submitChangerPaw(Context context, String old, String pas, RequestResulteView view);
    }

    interface View {
        public void submitSuccess(String con);
        public void submitError(String con);
    }

    interface Presenter {
        public void initModelView(Model model,View view);
        public void submitChangerPaw(Context context, String old, String pas);
    }
}

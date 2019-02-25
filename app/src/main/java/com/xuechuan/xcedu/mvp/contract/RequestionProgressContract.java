package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: 获取用户进度
 * @author: L-BackPacker
 * @date: 2019.01.08 下午 6:56
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public interface RequestionProgressContract {
    interface Model {
        public void requestProgress(Context context, RequestResulteView resulteView);
    }

    interface View {
        public void ProgressSuccess(String success);

        public void ProgressError(String msg);
    }

    interface Presenter {
         public void initModelView(Model model, View view);
        public void requestProgress(Context context);
    }
}

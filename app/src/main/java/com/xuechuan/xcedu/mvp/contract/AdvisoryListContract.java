package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.11.15 上午 9:40
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface AdvisoryListContract {
    interface Model {
        public void RequestAdvisoryList(Context context, String provinceCode, int page, RequestResulteView resulteView);
    }

    interface View {
        public void AdvisoryListOneSuccess(String success);
        public void AdvisoryListOneError(String msg);
        public void AdvisoryListTwoSuccess(String success);
        public void AdvisoryListTwoError(String msg);
    }

    interface Presenter {
        public void initModelView(Model model, View view);
        public void RequestAdvisoryListOne(Context context,String provinceCode, int page);
        public void RequestAdvisoryListTwo(Context context,String provinceCode, int page);
    }
}

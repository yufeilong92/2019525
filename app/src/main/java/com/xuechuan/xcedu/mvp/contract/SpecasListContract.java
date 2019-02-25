package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.11.15 上午 9:51
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface SpecasListContract {
    interface Model {
        public void RequestSpecasList(Context context, int page, RequestResulteView resulteView);
    }

    interface View {
        public void SpecasListOneSuccess(String con);
        public void SpecasListOneErrorr(String msg);
        public void SpecasListTwoSuccess(String con);
        public void SpecasListTwoErrorr(String msg);
    }

    interface Presenter {
        public void initModelView(Model model, View view);
        public void RequestSpecasListOne(Context context,int page);
        public void RequestSpecasListTwo(Context context,int page);
    }
}

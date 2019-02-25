package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.11.28 上午 8:40
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface HomeNetInfomContract {
    interface Model {
        public void requestNetDetail(Context context, String productid, RequestResulteView resulteView);
    }

    interface View {
        public void NetDetailSuccess(String success);

        public void NetDeatailError(String msg);
    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void requestNetDetail(Context context, String productid);
    }
}

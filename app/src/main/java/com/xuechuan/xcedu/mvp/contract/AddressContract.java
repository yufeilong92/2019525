package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/8/15 17:28
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface AddressContract {
    interface Model {
        public void requestAllAddress(Context context, int page, RequestResulteView requestResulteView);

        public void submitOrDelectDafaultAddress(Context context, int addressid,int tag, RequestResulteView view);
    }

    interface View {
        public void allAddressSuccess(String com);

        public void allAddressError(String msg);

        public void allAddressMoreSuccess(String com);

        public void allAddressMoreError(String msg);
        public void DefaulitOrDelectSuccess(String com);
        public void DefaulitOrDelectError(String msg);
    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void requestAllAddress(Context context, int page);

        public void requestAllAddressMore(Context context, int page);

        public void submitDafaultAddress(Context context, int addressid,int tag);
    }
}

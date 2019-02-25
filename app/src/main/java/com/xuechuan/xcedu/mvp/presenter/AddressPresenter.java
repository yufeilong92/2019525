package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.AddressContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/8/15 17:28
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AddressPresenter implements AddressContract.Presenter {
    AddressContract.Model model;
    AddressContract.View view;

    @Override
    public void initModelView(AddressContract.Model model, AddressContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestAllAddress(Context context, int page) {

        model.requestAllAddress(context, page, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.allAddressSuccess(result);
            }

            @Override
            public void error(String result) {
                view.allAddressError(result);
            }
        });
    }

    @Override
    public void requestAllAddressMore(Context context, int page) {
        model.requestAllAddress(context, page, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.allAddressMoreSuccess(result);
            }

            @Override
            public void error(String result) {
                view.allAddressMoreError(result);
            }
        });
    }

    /**
     * @param context
     * @param addressid
     * @param tag       1为删除 0 为默认
     */
    @Override
    public void submitDafaultAddress(Context context, int addressid, int tag) {
        model.submitOrDelectDafaultAddress(context, addressid, tag, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.DefaulitOrDelectSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.DefaulitOrDelectError(msg);
            }
        });
    }


}

package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.BuyBankContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.14 上午 9:24
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BuyBankPresenter implements BuyBankContract.Presenter {
    BuyBankContract.Model model;
    BuyBankContract.View view;

    @Override
    public void initModelView(BuyBankContract.Model model, BuyBankContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void reqeusstUserBuy(final Context context) {
        model.reqeusstUserBuy(context, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.BuySuccess(success);
            }

            @Override
            public void error(String msg) {
                view.BuyError(msg);
            }
        });
    }
}

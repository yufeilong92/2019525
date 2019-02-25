package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.CreataOrUpAddressContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 提交修改地址
 * @author: L-BackPacker
 * @date: 2018/8/16 10:45
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class CreaOrUpAddressPresenter implements CreataOrUpAddressContract.Presenter {
    CreataOrUpAddressContract.Model model;
    CreataOrUpAddressContract.View view;

    @Override
    public void initModelView(CreataOrUpAddressContract.Model model, CreataOrUpAddressContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void submitCreateUpAddress(Context context, int addressid, String province, String city, String area, String address, String post, String receivename, boolean isdefault, String telphone) {
        this.model.submitCreateUpAddress(context, addressid, province, city, area, address, post, receivename, isdefault,telphone, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.CreOrUpSuccress(result);
            }

            @Override
            public void error(String result) {
                view.CreOrUpError(result);
            }
        });

    }


}

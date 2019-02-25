package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.CreataOrUpAddressContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.MeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/8/16 10:45
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class CreataOrUpAddressModel implements CreataOrUpAddressContract.Model {

    @Override
    public void submitCreateUpAddress(Context context, int addressid, String province, String city, String area, String address, String post, String receivename, boolean isdefault, String telphone,final RequestResulteView view) {
        MeService service = new MeService(context);
        service.submitCreateUpAddress(addressid, province, city, area, address
                , post, receivename, telphone,isdefault, new StringCallBackView() {
                    @Override
                    public void onSuccess(String success) {
                        view.success(success);
                    }

                    @Override
                    public void onError(String msg) {
                        view.error(msg);
                    }
                });
    }
}

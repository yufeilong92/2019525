package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/8/16 10:45
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface CreataOrUpAddressContract {
    interface Model {
        public void submitCreateUpAddress(Context context,int addressid, String province, String city, String area
                , String address, String post, String receivename,boolean isdefault,
                                          String telphone, RequestResulteView view);
    }

    interface View {
        public void CreOrUpSuccress(String content);
        public void CreOrUpError(String msg);
    }

    interface Presenter {
        public void initModelView(Model model, View view);
        public void submitCreateUpAddress(Context context,int addressid, String province, String city, String area
                , String address, String post, String receivename,boolean isdefault,
                                          String telphone);
    }
}

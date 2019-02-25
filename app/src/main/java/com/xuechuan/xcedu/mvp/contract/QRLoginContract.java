package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: 扫描登陆
 * @author: L-BackPacker
 * @date: 2018/7/31 8:48
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface QRLoginContract {
    interface Model {
        public void submitLoginRequest(Context context,String code, RequestResulteView view);


    }

    interface View {
        public void QrloginSuccess(String content);
        public void QrloginError(String msg);

    }

    interface Presenter {
        public void initModelView(Model model,View view);
        public void submitLoginRequest(Context context,String code);

    }

}

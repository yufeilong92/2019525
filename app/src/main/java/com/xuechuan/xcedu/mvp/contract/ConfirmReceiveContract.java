package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/8/28 10:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface ConfirmReceiveContract {
    interface Model {
        public void submitConfirmReceive(Context context, String ordernum, int oid, RequestResulteView view);
    }

    interface View {
        public void ConfirmSucces(String success);
        public void ConfirmError(String msg);
    }

    interface Presenter {
        public void initModelView(Model model,View view);
        public void submitConfirmReceive(Context context, String ordernum, int oid);
    }
}

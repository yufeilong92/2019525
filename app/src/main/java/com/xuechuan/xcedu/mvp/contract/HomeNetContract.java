package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.11.27 下午 5:18
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface HomeNetContract {
    interface Model {
        public void requestNetAll(Context context, int page, RequestResulteView resulteView);
    }

    interface View {
        public void NetSuccessOne(String success);

        public void NetErrorOne(String msg);

        public void NetSuccessTwo(String success);

        public void NetErrorTwo(String msg);
    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void requestNetOne(Context context,int page);

        public void requestNetTwo(Context context,int page);
    }
}

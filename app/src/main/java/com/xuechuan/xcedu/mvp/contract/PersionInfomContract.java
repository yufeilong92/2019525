package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: 个人信息
 * @author: L-BackPacker
 * @date: 2018/5/25 16:39
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface PersionInfomContract {
    interface Model {
        public void reqeustMInfo(Context context, RequestResulteView view);
    }

    interface View {
        public void InfomSuccess(String cont);

        public void InfomError(String cont);
    }

    interface Presenter {
        public void basePresenter(Model model, View view);

        public void reqeustMInfo(Context context);
    }
}

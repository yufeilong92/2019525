package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/26 17:58
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface MyTextContract {
    interface Model {
        /**
         * 获取科目id
         *
         * @param context
         * @param view
         */
        public void requestBookId(Context context, RequestResulteView view);
    }

    interface View {
        //功课
        public void BookIDSuccess(String con);

        public void BookIDError(String con);
    }

    interface Presenter {
        public void initModelView(Model model, View view);
        public void reuqestBookId(Context context);
    }
}

package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/6/2 17:25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface HomeContract {
    interface Model {
       public void requestHomePager(Context context,String code, RequestResulteView  view);
    }

    interface View {
        public void Success(String com);
        public void Error(String msg);
    }

    interface Presenter {
        public void initModelView(Model model,View view);
        public void requestHomePager(Context context,String code);
    }
}

package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: 视频详情
 * @author: L-BackPacker
 * @date: 2018/6/7 14:12
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface VideoBooksContract {
    interface Model {
        public void requestBookInfoms(Context context, String classid, RequestResulteView view);

    }

    interface View {
      public void   BookInfomSucces(String com);
      public void   BookInfomError(String msgt);
    }


    interface Presenter {
        public void initModelView(Model model,View view);
        public void requestBookInfoms(Context context, String classid);
    }
}

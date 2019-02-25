package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.view.StringCallBackView;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: 我的系统通知
 * @author: L-BackPacker
 * @date: 2018/5/30 17:47
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface MySystemContract {
    interface Model {
        public void requestSystemMsg(Context context, int page, RequestResulteView view);
        public void submitDelSystemMsg(Context context, List<Integer>ids, RequestResulteView view);
    }

    interface View {
        public void SystemSuccess(String con);
        public void SystemErrorr(String con);

        public void SystemMoreSuccess(String con);
        public void SystemMoreErrorr(String con);

        public void DelSuccess(String con);
        public void DelError(String con);
    }

    interface Presenter {
        public void initModelView(Model model,View view);
        public void requestSystemMsg(Context context, int page);
        public void requestSystemMoreMsg(Context context, int page);
        public void submitDelSystemMsg(Context context,List<Integer>ids);
    }
}

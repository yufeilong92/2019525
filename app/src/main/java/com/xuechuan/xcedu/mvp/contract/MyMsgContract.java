package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/30 17:09
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface MyMsgContract {
    interface Model {
        public void requestMyMsg(Context context, int page, RequestResulteView view);
        public void submitDelMyMsg(Context context, List<Integer> ids,RequestResulteView view);
    }

    interface View {
        public void MyMsgSuccess(String con);

        public void MyMsgError(String con);

        public void MyMsgMoreSuccess(String con);

        public void MyMsgMoreError(String con);
        public void DelMyMsg(String con);
        public void DelMyError(String con);
    }

    interface Presenter {
        public void initModelView(Model model,View view);
        public void requestMyMsg(Context context, int page);
        public void requestMyMsgMore(Context context, int page);
        public void submitDelMyMsg(Context context, List<Integer> ids);
    }
}

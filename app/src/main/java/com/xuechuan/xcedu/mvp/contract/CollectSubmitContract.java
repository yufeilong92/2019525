package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: 提交收藏
 * @author: L-BackPacker
 * @date: 2019.01.08 下午 3:47
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public interface CollectSubmitContract {
    interface Model {
        public void submitCollect(Context context, String add, String delete, RequestResulteView resulteView);
    }

    interface View {
        public void submitCollectSuccess(String success);

        public void submitCollectError(String msg);
    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void submitCollect(Context context, String add, String delete);
    }
}

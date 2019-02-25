package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.11.28 上午 11:23
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface NetCommentContract {
    interface Model {
        public void requestNetComment(Context context, int page, String  productid, RequestResulteView resulteView);
    }

    interface View {
        public void CommentSuccessOne(String success);

        public void CommentErrorOne(String msg);

        public void CommentSuccessTwo(String success);

        public void CommentErrorTwo(String msg);
    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void requestNetCommentOne(Context context, int page, String  productid);

        public void requestNetCommentMore(Context context, int page, String  productid);
    }
}

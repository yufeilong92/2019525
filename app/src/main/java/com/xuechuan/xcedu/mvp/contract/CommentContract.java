package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/7/31 10:54
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface CommentContract {
    interface Model {
        public void requestComment(Context context,int page,String targettype, String targetid , RequestResulteView view);
    }

    interface View {
        public void commentSuccess(String con);
        public void commentError(String con);
        public void commentMoreSuccess(String con);
        public void commentMoreError(String con);
    }

    interface Presenter {
        public void initModelView(Model model,View view);
        public void requestComment(Context context,int page,String targettype, String targetid );
        public void requestCommentMore(Context context,int page,String targettype, String targetid );
    }
}

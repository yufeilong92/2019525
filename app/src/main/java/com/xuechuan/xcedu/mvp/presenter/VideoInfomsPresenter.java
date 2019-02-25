package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.VideoBooksContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 我的网课界面
 * @author: L-BackPacker
 * @date: 2018/6/7 14:12
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class VideoInfomsPresenter implements VideoBooksContract.Presenter {
    VideoBooksContract.Model model;
    VideoBooksContract.View view;

    @Override
    public void initModelView(VideoBooksContract.Model model, VideoBooksContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestBookInfoms(Context context, String classid) {

        model.requestBookInfoms(context, classid, new RequestResulteView() {
            @Override
            public void success(String result) {
             view.BookInfomSucces(result);
            }

            @Override
            public void error(String result) {
           view.BookInfomError(result);
            }
        });
    }
}

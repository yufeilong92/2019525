package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.GmSubmitComContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 提交评价
 * @author: L-BackPacker
 * @date: 2019.01.03 下午 3:11
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class GmSubmitComPresenter implements GmSubmitComContract.Presenter {
    GmSubmitComContract.Model model;
    GmSubmitComContract.View view;

    @Override
    public void initModelView(GmSubmitComContract.Model model, GmSubmitComContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void submiteCommont(Context context, String tagetid, String comment, String commenttid, String usertype) {
        model.submiteCommont(context, tagetid, comment, commenttid, usertype, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.SubmitSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.SubmiteError(msg);
            }
        });
    }
}

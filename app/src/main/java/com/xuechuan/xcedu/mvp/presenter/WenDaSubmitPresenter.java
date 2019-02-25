package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Progress;
import com.xuechuan.xcedu.mvp.contract.WenDaSubmitContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteUpView;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.10 上午 11:35
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class WenDaSubmitPresenter implements WenDaSubmitContract.Presenter {
    WenDaSubmitContract.Model model;
    WenDaSubmitContract.View view;

    @Override
    public void initModelView(WenDaSubmitContract.Model model, WenDaSubmitContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void submtiProblem(Context context, int problemid, String comment, List<String> imgs, String tags) {
        model.submtiProblem(context, problemid, comment, imgs, tags, new RequestResulteUpView() {
            @Override
            public void success(String result) {
                view.SubmitProblemScu(result);
            }

            @Override
            public void error(String result) {
                view.SubmitProblemErr(result);
            }

            @Override
            public void Progress(Progress progress) {
                view.SubmitProgressHearImg(progress);
            }
        });
    }
}

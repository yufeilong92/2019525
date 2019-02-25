package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.AdvisoryListContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description:资讯列表管理器
 * @author: L-BackPacker
 * @date: 2018.11.15 上午 9:40
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AdvisoryListPresenter implements AdvisoryListContract.Presenter {
    AdvisoryListContract.Model model;
    AdvisoryListContract.View view;

    @Override
    public void initModelView(AdvisoryListContract.Model model, AdvisoryListContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void RequestAdvisoryListOne(Context context, String provinceCode, int page) {
        model.RequestAdvisoryList(context, provinceCode, page, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.AdvisoryListOneSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.AdvisoryListOneError(msg);
            }
        });
    }

    @Override
    public void RequestAdvisoryListTwo(Context context, String provinceCode, int page) {
        model.RequestAdvisoryList(context, provinceCode, page, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.AdvisoryListTwoSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.AdvisoryListTwoError(msg);
            }
        });
    }
}

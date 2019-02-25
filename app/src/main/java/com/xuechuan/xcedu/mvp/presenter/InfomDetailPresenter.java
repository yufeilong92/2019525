package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.InfomDetailContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 根据文章编号获取文章详情
 * @author: L-BackPacker
 * @date: 2018/6/1 10:28
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class InfomDetailPresenter implements InfomDetailContract.Presenter {
    InfomDetailContract.Model model;
    InfomDetailContract.View view;

    @Override
    public void initModelView(InfomDetailContract.Model model, InfomDetailContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestGetDetail(Context context, String atricleid) {
        model.requestGetDetail(context, atricleid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.GetDetailSuccess(result);
            }

            @Override
            public void error(String result) {
                view.GetDetailError(result);
            }
        });
    }

    @Override
    public void requestEvalueDetail(Context context, int page, String targettype, String targetid) {
        model.requestEvalueDetail(context, page, targettype, targetid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.EvalueDetail(result);
            }

            @Override
            public void error(String result) {
                view.EvalueDetailErr(result);
            }
        });

    }

    @Override
    public void requestEvalueMoreDetail(Context context, int page, String targettype, String targetid) {
        model.requestEvalueDetail(context, page, targettype, targetid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.EvalueMoreDetail(result);
            }

            @Override
            public void error(String result) {
                view.EvalueMoreDetailErr(result);
            }
        });
    }

  /*  @Override
    public void requestEvalueDetail(Context context, String articleid, int page) {
        model.requestEvalueDetail(context, articleid, page, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.EvalueDetail(result);
            }

            @Override
            public void error(String result) {
                view.EvalueDetailErr(result);
            }
        });
    }

    @Override
    public void requestEvalueMoreDetail(Context context, String articleid, int page) {
        model.requestEvalueDetail(context, articleid, page, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.EvalueMoreDetail(result);
            }

            @Override
            public void error(String result) {
                view.EvalueMoreDetailErr(result);
            }
        });
    }*/
}

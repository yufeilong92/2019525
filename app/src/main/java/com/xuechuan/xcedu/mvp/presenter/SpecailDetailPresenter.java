package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.model.SpecailDetailModel;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.mvp.view.SpecailDetailView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 根据 tagid 获取下面所有题号
 * @author: L-BackPacker
 * @date: 2018/5/5 19:01
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SpecailDetailPresenter {

    private SpecailDetailModel model;
    private SpecailDetailView view;

    public SpecailDetailPresenter(SpecailDetailModel model, SpecailDetailView view) {
        this.model = model;
        this.view = view;
    }

    public void requestSpecailDetail(Context context, String coureid, String tagid) {
        if (coureid == null) {
            return;
        }
        if (tagid == null) {
            return;
        }
        model.requestSpecailDetail(context, coureid, tagid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.SuccessSpecatilDetail(result);
            }

            @Override
            public void error(String result) {
                view.ErrorSpecatilDetail(result);
            }
        });


    }


}

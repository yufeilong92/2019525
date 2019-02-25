package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.model.ErrOrColListModel;
import com.xuechuan.xcedu.mvp.view.ErrOrColListView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description:  请求收藏题错误集合
 * @author: L-BackPacker
 * @date: 2018/5/7 18:50
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ErrOrColListPresenter {
    private ErrOrColListModel model;
    private ErrOrColListView view;

    public ErrOrColListPresenter(ErrOrColListModel model, ErrOrColListView view) {
        this.model = model;
        this.view = view;
    }

    public void requestionErrOrColCont(Context context, String courseid, String tagid, String tagtype) {
        if (StringUtil.isEmpty(courseid)) {
            return;
        }
        model.reqeustOneEvalueContent(context, courseid, tagid, tagtype, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.ErrOrColListSuccess(result);
            }

            @Override
            public void error(String result) {
                view.ErrOrColListError(result);
            }
        });
    }
}

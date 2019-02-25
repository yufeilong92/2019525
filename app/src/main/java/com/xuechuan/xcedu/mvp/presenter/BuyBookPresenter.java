package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.model.BuyBookModel;
import com.xuechuan.xcedu.mvp.view.BuyBookView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: BuyBookPresenter.java
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: YFL
 * @date: 2018/5/4 00:11
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/4 星期五
 * 注意：本内容仅限于学川教育有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class BuyBookPresenter {
    private BuyBookModel model;
    private BuyBookView view;

    public BuyBookPresenter(BuyBookModel model, BuyBookView view) {
        this.model = model;
        this.view = view;
    }

    public void requestBuyInfom(Context context, String courseid) {
        if (StringUtil.isEmpty(courseid)) {
            return;
        }
        model.getBuyInfom(context, courseid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.BuySuccess(result);
            }

            @Override
            public void error(String result) {
                view.BuyError(result);
            }
        });
    }
}

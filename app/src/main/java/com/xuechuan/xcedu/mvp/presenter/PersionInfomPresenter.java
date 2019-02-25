package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.PersionContract;
import com.xuechuan.xcedu.mvp.contract.PersionInfomContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 个人
 * @author: L-BackPacker
 * @date: 2018/5/25 16:39
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class PersionInfomPresenter implements PersionInfomContract.Presenter {
    private PersionInfomContract.Model model;
    private PersionInfomContract.View view;

    @Override
    public void basePresenter(PersionInfomContract.Model model, PersionInfomContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void reqeustMInfo(Context context) {
        model.reqeustMInfo(context, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.InfomSuccess(result);
            }

            @Override
            public void error(String result) {
                view.InfomError(result);
            }
        });
    }
}

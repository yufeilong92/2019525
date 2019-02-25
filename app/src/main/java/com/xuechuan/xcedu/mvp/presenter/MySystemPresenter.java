package com.xuechuan.xcedu.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.mvp.contract.MySystemContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.ui.bank.AnswerActivity;
import com.xuechuan.xcedu.utils.Defaultcontent;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.ShareUtils;
import com.xuechuan.xcedu.weight.CommonPopupWindow;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 系统通知
 * @author: L-BackPacker
 * @date: 2018/5/30 17:47
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MySystemPresenter implements MySystemContract.Presenter {
    MySystemContract.Model model;
    MySystemContract.View view;

    @Override
    public void initModelView(MySystemContract.Model model, MySystemContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestSystemMsg(Context context, int page) {
        model.requestSystemMsg(context, page, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.SystemSuccess(result);
            }

            @Override
            public void error(String result) {
                view.SystemErrorr(result);
            }
        });
    }

    @Override
    public void requestSystemMoreMsg(Context context, int page) {
        model.requestSystemMsg(context, page, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.SystemMoreSuccess(result);
            }

            @Override
            public void error(String result) {
                view.SystemMoreErrorr(result);
            }
        });
    }

    @Override
    public void submitDelSystemMsg(Context context, List<Integer> ids) {
        final AlertDialog alertDialog = DialogUtil.showDialog(context, "", context.getResources().getString(R.string.loading));
        model.submitDelSystemMsg(context, ids, new RequestResulteView() {
            @Override
            public void success(String result) {
                alertDialog.dismiss();
                view.DelSuccess(result);
            }

            @Override
            public void error(String result) {
                alertDialog.dismiss();
                view.DelError(result);
            }
        });
    }



}

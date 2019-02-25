package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.lzy.okgo.model.Progress;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.mvp.contract.PersionContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteUpView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.StringUtil;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 个人信息修改
 * @author: L-BackPacker
 * @date: 2018/5/25 16:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class PersionPresenter implements PersionContract.Presenter {

    private PersionContract.Model model;
    private PersionContract.View view;


    @Override
    public void BasePersion(PersionContract.Model model, PersionContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void submitPersionInfom(Context context, String nickname, int gender, String birthday, String province, String city) {
        final AlertDialog mDialog = DialogUtil.showDialog(context, "", context.getResources().getString(R.string.submit_loading));

        model.submitPersionInfom(context, nickname, gender, birthday, province, city, new RequestResulteView() {
            @Override
            public void success(String result) {
                if (mDialog != null && mDialog.isShowing())
                    mDialog.dismiss();
                view.SubmitPersionSuccess(result);
            }

            @Override
            public void error(String result) {
                if (mDialog != null && mDialog.isShowing())
                    mDialog.dismiss();
                view.SubmitPersionError(result);
            }
        });
    }

    @Override
    public void submitPersionHear(Context context, List<String> path) {
        model.submitPersionHear(context, path, new RequestResulteUpView() {
            @Override
            public void success(String result) {
                view.SubmitPersionHearScu(result);
            }

            @Override
            public void error(String result) {
                view.SubmitPersionHearErr(result);
            }

            @Override
            public void Progress(Progress progress) {
                view.SubmitProgressHear(progress);
            }
        });
    }


}

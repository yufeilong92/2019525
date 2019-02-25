package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.contract.WenDaSubmitContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteUpView;
import com.xuechuan.xcedu.net.UpData;
import com.xuechuan.xcedu.net.view.StringCallBackUpView;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.10 上午 11:35
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class WenDaSubmitModel implements WenDaSubmitContract.Model {
    @Override
    public void submtiProblem(Context context, int problemid, String comment, List<String> imgs, String tags, final RequestResulteUpView view) {
        UpData data = UpData.getInstance(context);
        data.SubmitProblem(imgs, problemid, tags, comment, new StringCallBackUpView() {
            @Override
            public void onSuccess(String response) {
                view.success(response);
            }

            @Override
            public void onError(String response) {
                view.error(response);
            }

            @Override
            public void onUpProgree(Progress progress) {
                view.Progress(progress);
            }
        });
    }

}

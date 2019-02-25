package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.contract.PersionContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteUpView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.MeService;
import com.xuechuan.xcedu.net.UpData;
import com.xuechuan.xcedu.net.view.StringCallBackUpView;
import com.xuechuan.xcedu.net.view.StringCallBackView;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 提交用户信息
 * @author: L-BackPacker
 * @date: 2018/5/25 16:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class PersionModel implements PersionContract.Model {


    @Override
    public void submitPersionInfom(Context context, String nickname, int gender, String birthday, String province, String city, final RequestResulteView view) {
        MeService service = new MeService(context);
        service.submitChangememberinfo(nickname, gender, birthday, province, city, new StringCallBackView() {
            @Override
            public void onSuccess(String response) {
                view.success(response);
            }

            @Override
            public void onError(String response) {
                view.error(response);
            }
        });
    }

    @Override
    public void submitPersionHear(Context context, List<String> path, final RequestResulteUpView view) {
        UpData data = new UpData(context);
        data.submitchangeheadimg(path, new StringCallBackUpView() {
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

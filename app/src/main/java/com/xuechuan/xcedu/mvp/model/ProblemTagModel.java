package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.ProblemTagContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.ConfigService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.02.13 上午 10:00
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class ProblemTagModel implements ProblemTagContract.Model {
    @Override
    public void requestProblemtags(Context context, final RequestResulteView resulteView) {
        ConfigService service = ConfigService.get_Instance(context);
        service.requestProblemtags(new StringCallBackView() {
            @Override
            public void onSuccess(String success) {
                resulteView.success(success);
            }

            @Override
            public void onError(String msg) {
                resulteView.error(msg);
            }
        });
    }
}

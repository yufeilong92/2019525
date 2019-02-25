package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.SubmiteProgressContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.vo.UpQuestionProgressVo;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.08 下午 5:49
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class SubmiteProgressModel implements SubmiteProgressContract.Model {
    @Override
    public void submitProgress(Context context, int courseid, ArrayList<UpQuestionProgressVo> mdata, final RequestResulteView view) {
        BankService service = BankService.getInstance(context);
        service.submitQuestionProgressLog(courseid, mdata, new StringCallBackView() {
            @Override
            public void onSuccess(String success) {
                view.success(success);
            }

            @Override
            public void onError(String msg) {
                view.error(msg);
            }
        });
    }
}

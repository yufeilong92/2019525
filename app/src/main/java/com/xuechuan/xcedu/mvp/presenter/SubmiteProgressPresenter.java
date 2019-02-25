package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.SubmiteProgressContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.vo.UpQuestionProgressVo;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.08 下午 5:49
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class SubmiteProgressPresenter implements SubmiteProgressContract.Presenter {
    SubmiteProgressContract.Model model;
    SubmiteProgressContract.View view;

    @Override
    public void initModelView(SubmiteProgressContract.Model model, SubmiteProgressContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void submitProgress(Context context, int courseid, ArrayList<UpQuestionProgressVo> mdata) {
        model.submitProgress(context, courseid, mdata, new RequestResulteView() {
            @Override
            public void success(String success) {
                view.ProgressQuestionSuccess(success);
            }

            @Override
            public void error(String msg) {
                view.ProgressQuestionError(msg);
            }
        });
    }
}

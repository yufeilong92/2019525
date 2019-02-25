package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.lzy.okgo.model.Progress;
import com.xuechuan.xcedu.mvp.view.RequestResulteUpView;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.10 上午 11:35
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public interface WenDaSubmitContract {
    interface Model {
        public void submtiProblem(Context context, int problemid, String comment, List<String> imgs, String tags,RequestResulteUpView view);
    }

    interface View {
        public void SubmitProblemScu(String con);

        public void SubmitProblemErr(String con);

        public void SubmitProgressHearImg(Progress progress);
    }

    interface Presenter {
         public void initModelView(Model model, View view);
        public void submtiProblem(Context context, int problemid, String comment, List<String> imgs, String tags);
    }
}

package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.lzy.okgo.model.Progress;
import com.xuechuan.xcedu.mvp.view.RequestResulteUpView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/25 16:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface PersionContract {
    interface Model {
        public void submitPersionInfom(Context context, String nickname,
                                       int gender, String birthday, String province
                , String city, RequestResulteView view);

        public void submitPersionHear(Context context, List<String> path, RequestResulteUpView view);
    }

    interface View {
        public void SubmitPersionSuccess(String con);

        public void SubmitPersionError(String con);

        public void SubmitPersionHearScu(String con);

        public void SubmitPersionHearErr(String con);

        public void SubmitProgressHear(Progress progress);
    }

    interface Presenter {

        public void BasePersion(Model model, View view);

        public void submitPersionInfom(Context context, String nickname,
                                       int gender, String birthday, String province
                , String city);

        public void submitPersionHear(Context context, List<String> path);

    }
}

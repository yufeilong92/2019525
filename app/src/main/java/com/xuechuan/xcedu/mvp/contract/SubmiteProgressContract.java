package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.vo.UpQuestionProgressVo;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: 提交用户进度
 * @author: L-BackPacker
 * @date: 2019.01.08 下午 5:49
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public interface SubmiteProgressContract {
    interface Model {
        public void submitProgress(Context context, int courseid, ArrayList<UpQuestionProgressVo> mdata, RequestResulteView view);
    }

    interface View {
        public void ProgressQuestionSuccess(String success);

        public void ProgressQuestionError(String msg);
    }

    interface Presenter {
         public void initModelView(Model model, View view);
        public void submitProgress(Context context, int courseid, ArrayList<UpQuestionProgressVo> mdata);
    }
}

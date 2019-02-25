package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.vo.SubmiteExamVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.04 下午 6:37
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public interface ExamPostContract {
    interface Model {
        public void submitExam(Context context, String score, int chapterid, int usetime, String finishtime,
                               List<SubmiteExamVo> vo, RequestResulteView resulteView);
    }

    interface View {
        public void submiteSuccess(String success);

        public void submiteErrror(String msg);
    }

    interface Presenter {
         public void initModelView(Model model, View view);
        public void submitExam(Context context, String score, int chapterid, int usetime, String finishtime,
                               List<SubmiteExamVo> o);
    }
}

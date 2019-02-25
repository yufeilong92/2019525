package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: 请求评价
 * @author: L-BackPacker
 * @date: 2018.12.19 上午 11:37
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface GmEvalueContract {
    interface Model {
        public void reqeustEvalueContent(Context context, int page, String targettype, String targetid, RequestResulteView resulteView);

        public void reqeustNoteByQuestion(Context context, int question, RequestResulteView resulteView);
    }

    interface View {

        public void EvaluesOneSuccess(String sucess);

        public void EvaluesOneError(String msg);

        public void EvaluesMoreSuccess(String sucess);

        public void EvaluesMoreError(String msg);

        public void NoTeByQuestionSuccess(String success);

        public void NoteByQuestionError(String msg);
    }

    interface Presenter {
        public void initModelView(Model model, View view);

        public void reqeustEvalueContentOne(Context context, int page, String targettype, String targetid);

        public void reqeustEvalueContentMore(Context context, int page, String targettype, String targetid);

        public void reqeustNoteByQuestion(Context context, int question);

    }
}

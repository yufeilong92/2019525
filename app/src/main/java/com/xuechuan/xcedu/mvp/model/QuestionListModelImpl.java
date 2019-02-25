package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.view.QuestionListView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 获取文章章节
 * @author: L-BackPacker
 * @date: 2018/5/5 20:04
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class QuestionListModelImpl implements QuestionListModel {
    /***
     * 获取文章章节
     * @param context
     * @param courseid 科目id
     * @param view
     */
    @Override
    public void reqeustQuestionList(Context context, String courseid, final RequestResulteView view) {
        BankService service = new BankService(context);
        service.requestChapterList(courseid, new StringCallBackView() {
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
}

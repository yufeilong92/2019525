package com.xuechuan.xcedu.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.xuechuan.xcedu.mvp.contract.RqeustErrorContract;
import com.xuechuan.xcedu.mvp.model.RqeustErrorModel;
import com.xuechuan.xcedu.mvp.presenter.RequestErrorPresenter;
import com.xuechuan.xcedu.sqlitedb.ErrorSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.QuestionSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.SubmiteLogHelp;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.CollectOrErrorListVo;
import com.xuechuan.xcedu.vo.ErrorOrCollectItemVo;
import com.xuechuan.xcedu.vo.SqliteVo.ErrorSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.SubmitLogVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: RequestErrorService
 * @Package com.xuechuan.xcedu.service
 * @Description: 请求所以错题
 * @author: L-BackPacker
 * @date: 2019.01.08 下午 4:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019.01.08
 */
public class RequestErrorService extends IntentService implements RqeustErrorContract.View {
    private static final String ACTION_FOO = "com.xuechuan.xcedu.service.action.FOO";
    private static String TAG = "【" + RequestErrorService.class + "】==";
    private static final String EXTRA_PARAM1 = "com.xuechuan.xcedu.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.xuechuan.xcedu.service.extra.PARAM2";
    private int mSaffid;

    public RequestErrorService() {
        super("RequestErrorService");
    }


    public static void startActionFoo(Context context, int saffid) {
        Intent intent = new Intent(context, RequestErrorService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, saffid);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                mSaffid = intent.getIntExtra(EXTRA_PARAM1, 0);
                handleActionFoo();
            }
        }
    }


    private void handleActionFoo() {
        RequestErrorPresenter presenter = new RequestErrorPresenter();
        presenter.initModelView(new RqeustErrorModel(), this);
        presenter.requestError(this);
    }


    @Override
    public void getErrorSuccess(String success) {
        CollectOrErrorListVo vo = Utils.getGosnT(success, CollectOrErrorListVo.class);
        if (vo.getStatus().getCode() == 200) {
            List<ErrorOrCollectItemVo> list = vo.getDatas();
            if (list == null || list.isEmpty()) return;
            //错题表
            ErrorSqlteHelp sqlteHelp = ErrorSqlteHelp.getInstance(this);
            //错题集合
            QuestionSqliteHelp questionSqliteHelp = QuestionSqliteHelp.get_Instance(this);
            List<QuestionSqliteVo> questionList = questionSqliteHelp.getQuestionList(list);
            if (questionList == null || questionList.isEmpty()) return;
            for (int i = 0; i < questionList.size(); i++) {//保存用户记录
                QuestionSqliteVo questionSqliteVo = questionList.get(i);
                ErrorSqliteVo sqliteVo = new ErrorSqliteVo();
                sqliteVo.setCourseid(questionSqliteVo.getCourseid());
                sqliteVo.setChapterid(questionSqliteVo.getChapter_id());
                sqliteVo.setQuesitonid(questionSqliteVo.getQuestion_id());
                sqliteVo.setUserid(String.valueOf(mSaffid));
                sqliteVo.setRightnumber(0);
                sqlteHelp.addErrorItem(sqliteVo);
            }
            SubmiteLogHelp submiteLogHelp = SubmiteLogHelp.get_Instance(this);
            SubmitLogVo submitLogVo = new SubmitLogVo();
            submitLogVo.setSaffid(mSaffid);
            submitLogVo.setError(1);
            submiteLogHelp.addItem(submitLogVo);
        }
        stopSelf();
    }

    @Override
    public void getErrorError(String msg) {
        stopSelf();
    }
}

package com.xuechuan.xcedu.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.xuechuan.xcedu.mvp.contract.RequestCollectContract;
import com.xuechuan.xcedu.mvp.model.RequestCollectModel;
import com.xuechuan.xcedu.mvp.presenter.RequestCollectPresenter;
import com.xuechuan.xcedu.sqlitedb.CollectSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.QuestionSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.SubmiteLogHelp;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.CollectOrErrorListVo;
import com.xuechuan.xcedu.vo.ErrorOrCollectItemVo;
import com.xuechuan.xcedu.vo.SqliteVo.CollectSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.SubmitLogVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: RequestCollectService
 * @Package com.xuechuan.xcedu.service
 * @Description: 请求收藏
 * @author: L-BackPacker
 * @date: 2019.01.08 下午 4:39
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019.01.08
 */
public class RequestCollectService extends IntentService implements RequestCollectContract.View {

    private static final String ACTION_FOO = "com.xuechuan.xcedu.service.action.FOO";
    private static String TAG = "【" + RequestCollectService.class + "】==";
    private static final String EXTRA_PARAM1 = "com.xuechuan.xcedu.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.xuechuan.xcedu.service.extra.PARAM2";
    private int mSaffid;

    public RequestCollectService() {
        super("RequestCollectService");
    }

    public static void startActionFoo(Context context, int saffid) {
        Intent intent = new Intent(context, RequestCollectService.class);
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
        RequestCollectPresenter presenter = new RequestCollectPresenter();
        presenter.initModelView(new RequestCollectModel(), this);
        presenter.requestCollect(this);
    }

    @Override
    public void getCollectSuccess(String success) {
        CollectOrErrorListVo vo = Utils.getGosnT(success, CollectOrErrorListVo.class);
        if (vo.getStatus().getCode() == 200) {
            List<ErrorOrCollectItemVo> datas = vo.getDatas();
            if (datas == null || datas.isEmpty()) return;
            //收藏表
            CollectSqliteHelp sqliteHelp = CollectSqliteHelp.get_Instance(this);
            //问题表
            QuestionSqliteHelp questionSqliteHelp = QuestionSqliteHelp.get_Instance(this);

            List<QuestionSqliteVo> list = questionSqliteHelp.getQuestionList(datas);

            if (list == null || list.isEmpty()) return;

            for (int i = 0; i < list.size(); i++) {
                QuestionSqliteVo sqliteVo = list.get(i);

                CollectSqliteVo collectSqliteVo = new CollectSqliteVo();

                collectSqliteVo.setQuestiontype(sqliteVo.getQuestiontype());
                collectSqliteVo.setQuestion_id(sqliteVo.getQuestion_id());
                collectSqliteVo.setCourseid(sqliteVo.getCourseid());
                collectSqliteVo.setChapterid(sqliteVo.getChapter_id());
                collectSqliteVo.setCollectable(1);
                sqliteHelp.addCoolectItem(collectSqliteVo);
            }
            SubmiteLogHelp submiteLogHelp = SubmiteLogHelp.get_Instance(this);
            SubmitLogVo submitLogVo = new SubmitLogVo();
            submitLogVo.setSaffid(mSaffid);
            submitLogVo.setCollect(1);
            submiteLogHelp.addItem(submitLogVo);
        }
        stopSelf();
    }

    @Override
    public void getCollectError(String msg) {
        stopSelf();
    }
}

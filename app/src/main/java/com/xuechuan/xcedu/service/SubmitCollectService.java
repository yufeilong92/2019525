package com.xuechuan.xcedu.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.CollectSubmitContract;
import com.xuechuan.xcedu.mvp.contract.RequestCollectContract;
import com.xuechuan.xcedu.mvp.model.CollectSubmitModel;
import com.xuechuan.xcedu.mvp.model.ErrorSubmtModel;
import com.xuechuan.xcedu.mvp.model.RequestCollectModel;
import com.xuechuan.xcedu.mvp.presenter.CollectSubmitPresenter;
import com.xuechuan.xcedu.mvp.presenter.ErrorSubmtPresenter;
import com.xuechuan.xcedu.mvp.presenter.RequestCollectPresenter;
import com.xuechuan.xcedu.sqlitedb.CollectSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.QuestionSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.SubmiteLogHelp;
import com.xuechuan.xcedu.sqlitedb.UpCollectSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.UpDeleteCollectSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.UpDeleteErrorSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.UpErrorSqlteHelp;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.CollectOrErrorListVo;
import com.xuechuan.xcedu.vo.ErrorOrCollectItemVo;
import com.xuechuan.xcedu.vo.ResultVo;
import com.xuechuan.xcedu.vo.SqliteVo.CollectSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.ErrorSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.SubmitLogVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: SubmitCollectService
 * @Package com.xuechuan.xcedu.service
 * @Description: 收藏
 * @author: L-BackPacker
 * @date: 2019.01.08 下午 3:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019.01.08
 */

public class SubmitCollectService extends IntentService implements CollectSubmitContract.View, RequestCollectContract.View {
    private static final String ACTION_FOO = "com.xuechuan.xcedu.service.action.FOO";

    private static final String EXTRA_PARAM1 = "com.xuechuan.xcedu.service.extra.PARAM1";
    private int mSaffid;
    private UpCollectSqlteHelp mUpCollectSqlteHelp;
    private UpDeleteCollectSqlteHelp mUpDeleteCollectSqlteHelp;
    private List<CollectSqliteVo> mCollectSqliteVoLists;
    private List<CollectSqliteVo> mCollectSqliteVo1Lists;

    public SubmitCollectService() {
        super("SubmitCollectService");
    }


    public static void startActionFoo(Context context, int saffid) {
        Intent intent = new Intent(context, SubmitCollectService.class);
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
        mUpCollectSqlteHelp = UpCollectSqlteHelp.getInstance(this);
        mUpDeleteCollectSqlteHelp = UpDeleteCollectSqlteHelp.getInstance(this);
        mCollectSqliteVoLists = mUpCollectSqlteHelp.queryAllList();
        mCollectSqliteVo1Lists = mUpDeleteCollectSqlteHelp.queryAllList();
        if (mCollectSqliteVoLists == null && mCollectSqliteVo1Lists == null) {
            stopSelf();
            return;
        }
        if (mCollectSqliteVoLists.isEmpty() && mCollectSqliteVo1Lists.isEmpty()) {
            stopSelf();
            return;
        }
        CollectSubmitPresenter presenter = new CollectSubmitPresenter();
        presenter.initModelView(new CollectSubmitModel(), this);
        StringBuffer collectDo = new StringBuffer();
        if (!mCollectSqliteVoLists.isEmpty()) {
            for (int i = 0; i < mCollectSqliteVoLists.size(); i++) {
                CollectSqliteVo vo = mCollectSqliteVoLists.get(i);
                if (i == mCollectSqliteVoLists.size() - 1) {
                    collectDo.append(vo.getQuestion_id());
                } else {
                    collectDo.append(vo.getQuestion_id());
                    collectDo.append(" ");
                }
            }
        }
        StringBuffer collectDos = new StringBuffer();
        if (!mCollectSqliteVo1Lists.isEmpty()) {
            for (int i = 0; i < mCollectSqliteVo1Lists.size(); i++) {
                CollectSqliteVo vo = mCollectSqliteVo1Lists.get(i);
                if (i == mCollectSqliteVo1Lists.size() - 1) {
                    collectDos.append(vo.getQuestion_id());
                } else {
                    collectDos.append(vo.getQuestion_id());
                    collectDos.append(" ");
                }
            }
        }

        presenter.submitCollect(this, collectDo.toString(), collectDos.toString());

    }


    @Override
    public void submitCollectSuccess(String success) {
        ResultVo vo = Utils.getGosnT(success, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData().getStatusX() == 1) {
                SubmiteLogHelp mSubmiteLogHelp = SubmiteLogHelp.get_Instance(this);
//                SubmitLogVo submitLogVo = mSubmiteLogHelp.queryAllItem();

                if (mCollectSqliteVoLists != null && !mCollectSqliteVoLists.isEmpty()) {
                    for (int i = 0; i < mCollectSqliteVoLists.size(); i++) {
                        CollectSqliteVo sqliteVo = mCollectSqliteVoLists.get(i);
                        mUpCollectSqlteHelp.delectItem(sqliteVo.getId());
                    }
                }
                if (mCollectSqliteVo1Lists != null && !mCollectSqliteVo1Lists.isEmpty()) {
                    for (int i = 0; i < mCollectSqliteVo1Lists.size(); i++) {
                        CollectSqliteVo sqliteVo = mCollectSqliteVo1Lists.get(i);
                        mUpDeleteCollectSqlteHelp.delectItem(sqliteVo.getId());
                    }
                }
                SubmitLogVo vo2 = new SubmitLogVo();
                vo2.setSaffid(mSaffid);
                vo2.setCollecttime(TimeUtil.dateToString(new Date()));
                mSubmiteLogHelp.addItem(vo2);
      /*          if (submitLogVo == null) {
                    SubmitLogVo vo2 = new SubmitLogVo();
                    vo2.setSaffid(mSaffid);
                    vo2.setCollecttime(TimeUtil.dateToString(new Date()));
                    mSubmiteLogHelp.addItem(vo2);
//                    requestData();
                } else {
                    submitLogVo.setSaffid(mSaffid);
                    submitLogVo.setCollecttime(TimeUtil.dateToString(new Date()));
                    mSubmiteLogHelp.addItem(submitLogVo);
                    if (submitLogVo.getCollect() == 0) {
//                        requestData();
                    }
                }*/

            } else {
                handleActionFoo();
            }

        } else {
            handleActionFoo();
        }
    }

    private void requestData() {
        RequestCollectPresenter presenter = new RequestCollectPresenter();
        presenter.initModelView(new RequestCollectModel(), this);
        presenter.requestCollect(this);
    }

    @Override
    public void submitCollectError(String msg) {
       stopSelf();
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

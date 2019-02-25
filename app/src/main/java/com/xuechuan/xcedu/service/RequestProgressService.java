package com.xuechuan.xcedu.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.RequestionProgressContract;
import com.xuechuan.xcedu.mvp.model.RequestionProgressModel;
import com.xuechuan.xcedu.mvp.presenter.RequestionProgressPresenter;
import com.xuechuan.xcedu.sqlitedb.DoLogProgressSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.SubmiteLogHelp;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.SqliteVo.DoLogProgreeSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.SubmitLogVo;
import com.xuechuan.xcedu.vo.UpDataProgressVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: RequestProgressService
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 获取进度
 * @author: L-BackPacker
 * @date: 2019.01.08 下午 6:53
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019.01.08
 */
public class RequestProgressService extends IntentService implements RequestionProgressContract.View {
    private static final String ACTION_FOO = "com.xuechuan.xcedu.mvp.presenter.action.FOO";

    private static final String EXTRA_PARAM1 = "com.xuechuan.xcedu.mvp.presenter.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.xuechuan.xcedu.mvp.presenter.extra.PARAM2";
    private int mSaffid;
    private DoLogProgressSqliteHelp mDoLogProgressSqliteHelp;
    private SubmiteLogHelp mSubmiteLogHelp;

    public RequestProgressService() {
        super("RequestProgressService");
    }

    public static void startActionFoo(Context context, int saffid) {
        Intent intent = new Intent(context, RequestProgressService.class);
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
        mSubmiteLogHelp = SubmiteLogHelp.get_Instance(this);
        mDoLogProgressSqliteHelp = DoLogProgressSqliteHelp.get_Instance(this);
        RequestionProgressPresenter presenter = new RequestionProgressPresenter();
        presenter.initModelView(new RequestionProgressModel(), this);
        presenter.requestProgress(this);

    }


    @Override
    public void ProgressSuccess(String success) {
        UpDataProgressVo vo = Utils.getGosnT(success, UpDataProgressVo.class);
        if (vo.getStatus().getCode() == 200) {
            List<UpDataProgressVo.DatasBean> datas = vo.getDatas();
            if (datas == null || datas.isEmpty()) {
                stopSelf();
                return;
            }
            for (int i = 0; i < datas.size(); i++) {
                UpDataProgressVo.DatasBean bean = datas.get(i);
                int type = 0;
                int chapter_id = 0;
                switch (bean.getQt()) {
                    case 1://章节
                        type = DataMessageVo.CHAPTER_ONE;
                        chapter_id = bean.getTargetid();
                        break;
                    case 2://专项
                        type = DataMessageVo.SPECIAL_TWO;
                        chapter_id = bean.getTargetid();
                        break;
                    case 3://顺序
                        type = DataMessageVo.ORDER_THREE;
                        chapter_id = DataMessageVo.CASE_ORDER_MAKE;
                        break;
                    case 4://错题
                        type = DataMessageVo.ERROR_SIX;
                        chapter_id = DataMessageVo.error_mark;
                        break;
                    case 5://错题标签练习
                        type = DataMessageVo.ERROR_FOUR;
                        chapter_id = bean.getTargetid();
                        break;
                    case 6://收藏
                        type = DataMessageVo.COLLOECT_FIVE;
                        chapter_id = DataMessageVo.collect_mark;
                        break;
                    case 7://收藏标签练习
                        type = DataMessageVo.COLLOECT_SEVEN;
                        chapter_id = bean.getTargetid();
                        break;

                }
                DoLogProgreeSqliteVo sqliteVo = new DoLogProgreeSqliteVo();
                sqliteVo.setType(type);
                sqliteVo.setChapterid(chapter_id);
                sqliteVo.setKid(bean.getCourseid());
                sqliteVo.setNumber(String.valueOf(bean.getRnum()));
                sqliteVo.setUserid(String.valueOf(mSaffid));
                mDoLogProgressSqliteHelp.addDoLookItem(sqliteVo);
            }
            SubmitLogVo vo2 = new SubmitLogVo();
            vo2.setSaffid(mSaffid);
            vo2.setProgresstime(TimeUtil.dateToString(new Date()));
            mSubmiteLogHelp.addItem(vo2);
            stopSelf();

        } else {
            handleActionFoo();
        }
    }

    @Override
    public void ProgressError(String msg) {
        stopSelf();
    }
}

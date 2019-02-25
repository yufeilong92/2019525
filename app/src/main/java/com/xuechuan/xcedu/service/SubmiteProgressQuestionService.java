package com.xuechuan.xcedu.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.andview.refreshview.callback.IFooterCallBack;
import com.xuechuan.xcedu.mvp.contract.SubmiteProgressContract;
import com.xuechuan.xcedu.mvp.model.SubmiteProgressModel;
import com.xuechuan.xcedu.mvp.presenter.SubmiteProgressPresenter;
import com.xuechuan.xcedu.sqlitedb.DoUpLogProgressSqliteHelp;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.ResultVo;
import com.xuechuan.xcedu.vo.SqliteVo.DoLogProgreeSqliteVo;
import com.xuechuan.xcedu.vo.UpQuestionProgressVo;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: SubmiteProgressQuestionService
 * @Package com.xuechuan.xcedu.service
 * @Description: 提交问题进度
 * @author: L-BackPacker
 * @date: 2019.01.08 下午 5:46
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019.01.08
 */
public class SubmiteProgressQuestionService extends IntentService implements SubmiteProgressContract.View {
    private static final String ACTION_FOO = "com.xuechuan.xcedu.service.action.FOO";

    private static final String EXTRA_PARAM1 = "com.xuechuan.xcedu.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.xuechuan.xcedu.service.extra.PARAM2";
    private static final String EXTRA_PARAM3 = "com.xuechuan.xcedu.service.extra.PARAM3";
    private ArrayList<UpQuestionProgressVo> mData;
    private String courseid;


    public SubmiteProgressQuestionService() {
        super("SubmiteProgressQuestionService");
    }

    public static void startActionFoo(Context context, ArrayList<UpQuestionProgressVo> mData, String courseid) {
        Intent intent = new Intent(context, SubmiteProgressQuestionService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM2, mData);
        intent.putExtra(EXTRA_PARAM3, courseid);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                mData = (ArrayList<UpQuestionProgressVo>) intent.getSerializableExtra(EXTRA_PARAM2);
                courseid = intent.getStringExtra(EXTRA_PARAM3);
                handleActionFoo();
            }
        }
    }

    private void handleActionFoo() {
        //进度表
        SubmiteProgressPresenter presenter = new SubmiteProgressPresenter();
        presenter.initModelView(new SubmiteProgressModel(), this);
        int id=Integer.valueOf(courseid);
        presenter.submitProgress(this, id, mData);
    }

    @Override
    public void ProgressQuestionSuccess(String success) {
        ResultVo vo = Utils.getGosnT(success, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData().getStatusX() == 1) {
                DoUpLogProgressSqliteHelp help = DoUpLogProgressSqliteHelp.get_Instance(this);
                for (int i = 0; i < mData.size(); i++) {
                    UpQuestionProgressVo progressVo = mData.get(i);
                    help.deleteLookItem(progressVo.getId());
                }
                stopSelf();
            } else {
                handleActionFoo();
            }

        } else {
            handleActionFoo();
        }
    }

    @Override
    public void ProgressQuestionError(String msg) {
           stopSelf();
    }
}

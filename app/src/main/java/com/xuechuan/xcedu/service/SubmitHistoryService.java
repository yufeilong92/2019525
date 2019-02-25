package com.xuechuan.xcedu.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.DoBankSubmitContract;
import com.xuechuan.xcedu.mvp.model.DoBankSubmitModel;
import com.xuechuan.xcedu.mvp.presenter.DoBankSubmitPresenter;
import com.xuechuan.xcedu.sqlitedb.DoBankSqliteUpHelp;
import com.xuechuan.xcedu.sqlitedb.SubmiteLogHelp;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.ResultVo;
import com.xuechuan.xcedu.vo.SqliteVo.SubmitLogVo;
import com.xuechuan.xcedu.vo.UpdataOrDeleteVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: SubmitEorrtService
 * @Package com.xuechuan.xcedu.service
 * @Description: 提交做题记录
 * @author: L-BackPacker
 * @date: 2019.01.07 下午 7:07
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019.01.07
 */
public class SubmitHistoryService extends IntentService implements DoBankSubmitContract.View {
    private static final String ACTION_FOO = "com.xuechuan.xcedu.service.action.FOO";
    private static final String KEY_PARAME = "com.xuechuan.xcedu.service.action.key_parame";
    private DoBankSqliteUpHelp mSqliteHelp;
    private List<UpdataOrDeleteVo> mDeleteVos;
    private SubmiteLogHelp mSubmiteLogHelp;
    private int mSaffid;

    public SubmitHistoryService() {
        super("SubmitEorrtService");
    }

    public static void startActionFoo(Context context, int Saffid) {
        Intent intent = new Intent(context, SubmitHistoryService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(KEY_PARAME, Saffid);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                mSaffid = intent.getIntExtra(KEY_PARAME, 0);
                handleActionFoo();
            }
        }
    }

    private void handleActionFoo() {
        final Context context = this;
        final DoBankSubmitPresenter presenter = new DoBankSubmitPresenter();
        presenter.initModelView(new DoBankSubmitModel(), this);
        mSqliteHelp = DoBankSqliteUpHelp.get_Instance(this);
        mDeleteVos = mSqliteHelp.queryAllDoBank();

        final String time = TimeUtil.dateToStringOne(new Date());
        final StringBuffer buffer = new StringBuffer();
        if (mDeleteVos == null || mDeleteVos.isEmpty()) {
            stopSelf();
            return;
        }
        for (int i = 0; i < mDeleteVos.size(); i++) {
            UpdataOrDeleteVo vo = mDeleteVos.get(i);
            if (i == mDeleteVos.size() - 1) {
                buffer.append(vo.getQuestion_id());
                buffer.append(" ");
                if (vo.getRight() == 1) {
                    buffer.append(1);
                } else {
                    buffer.append(0);
                }

            } else {
                buffer.append(vo.getQuestion_id());
                buffer.append(" ");
                if (vo.getRight() == 1) {
                    buffer.append(1);
                } else {
                    buffer.append(0);
                }
                buffer.append(" ");

            }
        }
        presenter.submitDoBank(this, time, buffer.toString());

    }

    @Override
    public void DoBankSuecces(String success) {
        ResultVo vo = Utils.getGosnT(success, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData().getStatusX() == 1) {//成功
                SubmiteLogHelp mSubmiteLogHelp = SubmiteLogHelp.get_Instance(this);
                SubmitLogVo vo2 = new SubmitLogVo();
                vo2.setSaffid(mSaffid);
                vo2.setDobanktime(TimeUtil.dateToString(new Date()));
                mSubmiteLogHelp.addItem(vo2);
                for (int i = 0; i < mDeleteVos.size(); i++) {
                    UpdataOrDeleteVo vo1 = mDeleteVos.get(i);
                    mSqliteHelp.deleteItem(vo1.getId());
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
    public void DoBankError(String msg) {
        stopSelf();
    }
}

package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.CollectSubmitContract;
import com.xuechuan.xcedu.mvp.contract.ErrorSubmtContract;
import com.xuechuan.xcedu.mvp.contract.RequestCollectContract;
import com.xuechuan.xcedu.mvp.contract.RqeustErrorContract;
import com.xuechuan.xcedu.mvp.model.CollectSubmitModel;
import com.xuechuan.xcedu.mvp.model.ErrorSubmtModel;
import com.xuechuan.xcedu.mvp.model.RequestCollectModel;
import com.xuechuan.xcedu.mvp.model.RqeustErrorModel;
import com.xuechuan.xcedu.mvp.presenter.CollectSubmitPresenter;
import com.xuechuan.xcedu.mvp.presenter.ErrorSubmtPresenter;
import com.xuechuan.xcedu.mvp.presenter.RequestCollectPresenter;
import com.xuechuan.xcedu.mvp.presenter.RequestErrorPresenter;
import com.xuechuan.xcedu.sqlitedb.CollectSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.ErrorSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.QuestionSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.SubmiteLogHelp;
import com.xuechuan.xcedu.sqlitedb.UpCollectSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.UpDeleteCollectSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.UpDeleteErrorSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.UpErrorSqlteHelp;
import com.xuechuan.xcedu.utils.T;
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
 * @Title: SysDataActivity
 * @Package com.xuechuan.xcedu.ui.me
 * @Description: 同步数据界面
 * @author: L-BackPacker
 * @date: 2019.01.09 上午 8:49
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019.01.09
 */
public class SysDataActivity extends BaseActivity implements View.OnClickListener, ErrorSubmtContract.View, CollectSubmitContract.View, RequestCollectContract.View, RqeustErrorContract.View {

    private ImageView mIvDelAllSystem;
    private TextView mTvMSysTime;
    private Button mBtnMSysn;
    private TextView mTvMSError;
    private TextView mTvMSCollect;
    private TextView mTvMZError;
    private TextView mTvMZCollect;
    private TextView mTvMCError;
    private TextView mTvMCCollect;
    private Context mContext;
    private UpErrorSqlteHelp mUpErrorSqlteHelp;
    private UpDeleteErrorSqlteHelp mUpDeleteErrorSqlteHelp;
    private UpCollectSqlteHelp mUpCollectSqlteHelp;
    private UpDeleteCollectSqlteHelp mUpDeleteCollectSqlteHelp;
    private ErrorSubmtPresenter mErrorPresenter;
    private CollectSubmitPresenter mCollectPresenter;
    private SubmiteLogHelp mSubmiteLogHelp;
    private ErrorSqlteHelp mErrorSqlteHelp;
    private CollectSqliteHelp mCollectSqliteHelp;
    /**
     * 错题集合
     */

    private boolean mRError = false;
    /**
     * 收藏集合
     */
    private boolean mRCollect = false;

    private RequestCollectPresenter mRequestCollectPresenter;
    private RequestErrorPresenter mRequestErrorPresenter;
    private RelativeLayout mRlSysLayout;
    private TextView mTvMCollect;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_data);
        initView();
    }*/

    private static String PARAMT_KEY = "com.xuechuan.xcedu.ui.meSysDataActivity.paramt_key";
    private int mSaffid;
    private QuestionSqliteHelp mQuestionSqliteHelp;

    public static Intent start_Intent(Context context, int saffid) {
        Intent intent = new Intent(context, SysDataActivity.class);
        intent.putExtra(PARAMT_KEY, saffid);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sys_data);
        if (getIntent() != null) {
            mSaffid = getIntent().getIntExtra(PARAMT_KEY, 0);
        }
        initView();
        initUtils();
        initErrorCollectNumber();

    }

    private void initErrorCollectNumber() {
        SubmitLogVo submitLogVo = mSubmiteLogHelp.queryAllItem();
        String time = "无";
        if (submitLogVo == null) {
            mTvMSysTime.setText(time);
            return;
        }
        String errortime = submitLogVo.getErrortime();
        String collecttime = submitLogVo.getCollecttime();
        long rro = TimeUtil.getTimeWString(errortime);
        long col = TimeUtil.getTimeWString(collecttime);
        if (rro == 0 && col == 0) {
            mTvMSysTime.setText(time);
        } else if (rro == 0 && col != 0) {
            mTvMSysTime.setText(collecttime);
        } else if (col == 0 && rro != 0) {
            mTvMSysTime.setText(errortime);
        } else {
            if (rro > col) {
                mTvMSysTime.setText(errortime);
            } else {
                mTvMSysTime.setText(collecttime);
            }
        }

        int errorOne = mErrorSqlteHelp.queryCountWithCourseid(DataMessageVo.CHAPTER_ONE);
        int errorTwo = mErrorSqlteHelp.queryCountWithCourseid(DataMessageVo.SPECIAL_TWO);
        int errorThree = mErrorSqlteHelp.queryCountWithCourseid(DataMessageVo.ORDER_THREE);
        int collectOne = mCollectSqliteHelp.queryCountWithCourseid(DataMessageVo.CHAPTER_ONE);
        int collectTwo = mCollectSqliteHelp.queryCountWithCourseid(DataMessageVo.SPECIAL_TWO);
        int collectThree = mCollectSqliteHelp.queryCountWithCourseid(DataMessageVo.ORDER_THREE);
        mTvMSError.setText(String.valueOf(errorOne));
        mTvMSCollect.setText(String.valueOf(collectOne));

        mTvMZError.setText(String.valueOf(errorTwo));
        mTvMZCollect.setText(String.valueOf(collectTwo));

        mTvMCError.setText(String.valueOf(errorThree));
        mTvMCCollect.setText(String.valueOf(collectThree));

    }


    private void initUtils() {
        mErrorSqlteHelp = ErrorSqlteHelp.getInstance(mContext);
        mCollectSqliteHelp = CollectSqliteHelp.get_Instance(mContext);
        mSubmiteLogHelp = SubmiteLogHelp.get_Instance(mContext);
        mUpErrorSqlteHelp = UpErrorSqlteHelp.getInstance(mContext);
        mUpDeleteErrorSqlteHelp = UpDeleteErrorSqlteHelp.getInstance(mContext);
        mUpCollectSqlteHelp = UpCollectSqlteHelp.getInstance(mContext);
        mUpDeleteCollectSqlteHelp = UpDeleteCollectSqlteHelp.getInstance(mContext);
        mQuestionSqliteHelp = QuestionSqliteHelp.get_Instance(mContext);

        mErrorPresenter = new ErrorSubmtPresenter();
        mErrorPresenter.initModelView(new ErrorSubmtModel(), this);
        mCollectPresenter = new CollectSubmitPresenter();
        mCollectPresenter.initModelView(new CollectSubmitModel(), this);

        mRequestCollectPresenter = new RequestCollectPresenter();
        mRequestCollectPresenter.initModelView(new RequestCollectModel(), this);
        mRequestErrorPresenter = new RequestErrorPresenter();
        mRequestErrorPresenter.initModelView(new RqeustErrorModel(), this);
    }


    private void initView() {
        mContext = this;
        mIvDelAllSystem = (ImageView) findViewById(R.id.iv_del_all_system);
        mTvMSysTime = (TextView) findViewById(R.id.tv_m_sys_time);
        mBtnMSysn = (Button) findViewById(R.id.btn_m_sysn);
        mTvMSError = (TextView) findViewById(R.id.tv_m_s_error);
        mTvMSCollect = (TextView) findViewById(R.id.tv_m_s_collect);
        mTvMZError = (TextView) findViewById(R.id.tv_m_z_error);
        mTvMZCollect = (TextView) findViewById(R.id.tv_m_z_collect);
        mTvMCError = (TextView) findViewById(R.id.tv_m_c_error);
        mTvMCCollect = (TextView) findViewById(R.id.tv_m_c_collect);

        mBtnMSysn.setOnClickListener(this);
        mRlSysLayout = (RelativeLayout) findViewById(R.id.rl_sys_layout);
        mRlSysLayout.setOnClickListener(this);
        mTvMCollect = (TextView) findViewById(R.id.tv_m_collect);
        mTvMCollect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_m_sysn:
                ShowProgress(true);
                initData();
                break;
        }
    }

    private void initData() {
        boolean error = false, collect = false;
        List<ErrorSqliteVo> mErrorSqliteVoLists = mUpErrorSqlteHelp.queryAllDataList();
        List<ErrorSqliteVo> mErrorSqliteVo1Lists = mUpDeleteErrorSqlteHelp.queryAllDataList();
        if (mErrorSqliteVoLists == null && mErrorSqliteVo1Lists == null) {
            error = true;
            mRError = true;
        }
        if (mErrorSqliteVoLists.isEmpty() && mErrorSqliteVo1Lists.isEmpty()) {
            error = true;
            mRError = true;

        }
        List<CollectSqliteVo> mCollectSqliteVoLists = mUpCollectSqlteHelp.queryAllDataList();
        List<CollectSqliteVo> mCollectSqliteVo1Lists = mUpDeleteCollectSqlteHelp.queryAllDataList();
        if (mCollectSqliteVoLists == null && mCollectSqliteVo1Lists == null) {
            collect = true;
            mRCollect = true;
        }
        if (mCollectSqliteVoLists.isEmpty() && mCollectSqliteVo1Lists.isEmpty()) {
            collect = true;
            mRCollect = true;
        }
        if (error && collect) {
            T.showToast(mContext, "暂无新数据同步");
            ShowProgress(false);
            return;
        }

        ErrorSubmtPresenter presenter = new ErrorSubmtPresenter();
        presenter.initModelView(new ErrorSubmtModel(), this);
        StringBuffer errorDe = new StringBuffer();
        if (!mErrorSqliteVoLists.isEmpty()) {
            for (int i = 0; i < mErrorSqliteVoLists.size(); i++) {
                ErrorSqliteVo vo = mErrorSqliteVoLists.get(i);
                if (i == mErrorSqliteVoLists.size() - 1) {
                    errorDe.append(vo.getQuesitonid());
                } else {
                    errorDe.append(vo.getQuesitonid());
                    errorDe.append(" ");
                }
            }
        }
        StringBuffer errorDelete = new StringBuffer();
        if (!mErrorSqliteVo1Lists.isEmpty()) {
            for (int i = 0; i < mErrorSqliteVo1Lists.size(); i++) {
                ErrorSqliteVo vo = mErrorSqliteVo1Lists.get(i);
                if (i == mErrorSqliteVo1Lists.size() - 1) {
                    errorDelete.append(vo.getQuesitonid());
                } else {
                    errorDelete.append(vo.getQuesitonid());
                    errorDelete.append(" ");
                }
            }
        }
        if (!error) {
            presenter.submitError(this, errorDe.toString(), errorDelete.toString());
        }


        CollectSubmitPresenter mCollectPresenter = new CollectSubmitPresenter();
        mCollectPresenter.initModelView(new CollectSubmitModel(), this);
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
        if (!collect)
            mCollectPresenter.submitCollect(this, collectDo.toString(), collectDos.toString());
    }

    private void ShowProgress(boolean show) {
        mRlSysLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        mBtnMSysn.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void submitErrorSuccess(String success) {
        ResultVo vo = Utils.getGosnT(success, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            ResultVo.DataBean data = vo.getData();
            if (data.getStatusX() == 1) {
                mRequestErrorPresenter.requestError(mContext);
            } else {
                T.showToast(mContext, "同步失败");
            }
        } else {
            T.showToast(mContext, "同步失败");
        }
    }



    @Override
    public void submitErrorError(String msg) {
        T.showToast(mContext, "同步失败");
        ShowProgress(false);
    }


    @Override
    public void submitCollectSuccess(String success) {
        ResultVo vo = Utils.getGosnT(success, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            ResultVo.DataBean data = vo.getData();
            if (data.getStatusX() == 1) {
                mRequestCollectPresenter.requestCollect(mContext);
            } else {
                T.showToast(mContext, "同步失败");
            }
        } else {
            T.showToast(mContext, "同步失败");
        }
        ;
    }

    @Override
    public void submitCollectError(String msg) {
        T.showToast(mContext, "同步失败");
        ShowProgress(false);
    }

    @Override
    public void getCollectSuccess(String success) {
        CollectOrErrorListVo vo = Utils.getGosnT(success, CollectOrErrorListVo.class);
        if (vo.getStatus().getCode() == 200) {
            List<ErrorOrCollectItemVo> datas = vo.getDatas();
            if (datas == null || datas.isEmpty()) return;
            //收藏表
            List<QuestionSqliteVo> list = mQuestionSqliteHelp.getQuestionList(datas);
            if (list == null || list.isEmpty()) return;
            for (int i = 0; i < list.size(); i++) {
                QuestionSqliteVo sqliteVo = list.get(i);
                CollectSqliteVo collectSqliteVo = new CollectSqliteVo();
                collectSqliteVo.setQuestiontype(sqliteVo.getQuestiontype());
                collectSqliteVo.setQuestion_id(sqliteVo.getQuestion_id());
                collectSqliteVo.setCourseid(sqliteVo.getCourseid());
                collectSqliteVo.setChapterid(sqliteVo.getChapter_id());
                collectSqliteVo.setCollectable(1);
                mCollectSqliteHelp.addCoolectItem(collectSqliteVo);
            }
            SubmiteLogHelp submiteLogHelp = SubmiteLogHelp.get_Instance(this);
            SubmitLogVo submitLogVo = new SubmitLogVo();
            submitLogVo.setSaffid(mSaffid);
            submitLogVo.setCollect(1);
            submitLogVo.setCollecttime(TimeUtil.dateToString(new Date()));
            submiteLogHelp.addItem(submitLogVo);
            mRCollect = true;
        } else {
            mRCollect = false;
        }
        showResult();
    }

    private void showResult() {
        if (mRError && mRCollect) {
            T.showToast(mContext, "同步成功");
            ShowProgress(false);
            deleteLog();
            initErrorCollectNumber();
        } else {
            T.showToast(mContext, "同步失败");
            ShowProgress(false);
        }
    }

    private void deleteLog() {
        boolean error = false, collect = false;
        List<ErrorSqliteVo> mErrorSqliteVoLists = mUpErrorSqlteHelp.queryAllDataList();
        List<ErrorSqliteVo> mErrorSqliteVo1Lists = mUpDeleteErrorSqlteHelp.queryAllDataList();
        if (mErrorSqliteVoLists == null && mErrorSqliteVo1Lists == null) {
            error=true;
        }
        if (mErrorSqliteVoLists.isEmpty() && mErrorSqliteVo1Lists.isEmpty()) {
            error=true;
        }
        List<CollectSqliteVo> mCollectSqliteVoLists = mUpCollectSqlteHelp.queryAllDataList();
        List<CollectSqliteVo> mCollectSqliteVo1Lists = mUpDeleteCollectSqlteHelp.queryAllDataList();
        if (mCollectSqliteVoLists == null && mCollectSqliteVo1Lists == null) {
        collect=true;
        }
        if (mCollectSqliteVoLists.isEmpty() && mCollectSqliteVo1Lists.isEmpty()) {
            collect=true;
        }
        if (!error){
            mUpErrorSqlteHelp.delectError();
            mUpDeleteErrorSqlteHelp.delectError();
        }
        if (!collect){
            mUpCollectSqlteHelp.delete();
            mUpDeleteCollectSqlteHelp.delect();
        }
    }

    @Override
    public void getCollectError(String msg) {
        T.showToast(mContext, "同步失败");
        ShowProgress(false);
    }

    @Override
    public void getErrorSuccess(String success) {
        CollectOrErrorListVo vo = Utils.getGosnT(success, CollectOrErrorListVo.class);
        if (vo.getStatus().getCode() == 200) {
            List<ErrorOrCollectItemVo> list = vo.getDatas();
            if (list == null || list.isEmpty()) return;
            //
            //错题集合
            List<QuestionSqliteVo> questionList = mQuestionSqliteHelp.getQuestionList(list);
            if (questionList == null || questionList.isEmpty()) return;
            for (int i = 0; i < questionList.size(); i++) {//保存用户记录
                QuestionSqliteVo questionSqliteVo = questionList.get(i);
                ErrorSqliteVo sqliteVo = new ErrorSqliteVo();
                sqliteVo.setCourseid(questionSqliteVo.getCourseid());
                sqliteVo.setChapterid(questionSqliteVo.getChapter_id());
                sqliteVo.setQuesitonid(questionSqliteVo.getQuestion_id());
                sqliteVo.setUserid(String.valueOf(mSaffid));
                sqliteVo.setRightnumber(0);
                mErrorSqlteHelp.addErrorItem(sqliteVo);
            }
            SubmitLogVo submitLogVo = new SubmitLogVo();
            submitLogVo.setSaffid(mSaffid);
            submitLogVo.setError(1);
            submitLogVo.setErrortime(TimeUtil.dateToString(new Date()));
            mSubmiteLogHelp.addItem(submitLogVo);
            mRError = true;
        } else {
            mRError = false;
            T.showToast(mContext, "同步失败");
        }
        showResult();
    }

    @Override
    public void getErrorError(String msg) {
        T.showToast(mContext, "同步失败");
        ShowProgress(false);
    }
}

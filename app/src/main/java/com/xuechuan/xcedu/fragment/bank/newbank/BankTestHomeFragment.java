package com.xuechuan.xcedu.fragment.bank.newbank;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.easefun.polyvsdk.PolyvDevMountInfo;
import com.easefun.polyvsdk.PolyvSDKUtil;
import com.lzy.okgo.model.Progress;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.event.DayHomeEvent;
import com.xuechuan.xcedu.mvp.contract.BankHomeGradeContract;
import com.xuechuan.xcedu.mvp.contract.BuyBankContract;
import com.xuechuan.xcedu.mvp.contract.DayExerciseContract;
import com.xuechuan.xcedu.mvp.contract.FileDownContract;
import com.xuechuan.xcedu.mvp.contract.RequestCollectContract;
import com.xuechuan.xcedu.mvp.contract.RqeustErrorContract;
import com.xuechuan.xcedu.mvp.model.BankHomeGradeModel;
import com.xuechuan.xcedu.mvp.model.BuyBankModel;
import com.xuechuan.xcedu.mvp.model.DayExerciseModel;
import com.xuechuan.xcedu.mvp.model.FileDownModel;
import com.xuechuan.xcedu.mvp.model.RequestCollectModel;
import com.xuechuan.xcedu.mvp.model.RqeustErrorModel;
import com.xuechuan.xcedu.mvp.presenter.BankHomeGradePresenter;
import com.xuechuan.xcedu.mvp.presenter.BuyBankPresenter;
import com.xuechuan.xcedu.mvp.presenter.DayExercisePresenter;
import com.xuechuan.xcedu.mvp.presenter.FileDownPresenter;

import com.xuechuan.xcedu.mvp.presenter.RequestCollectPresenter;
import com.xuechuan.xcedu.mvp.presenter.RequestErrorPresenter;
import com.xuechuan.xcedu.sqlitedb.CollectSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.DeleteSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.ErrorSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.QuestionSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.SqliteHelp;
import com.xuechuan.xcedu.sqlitedb.SubmiteLogHelp;
import com.xuechuan.xcedu.sqlitedb.UserInfomDbHelp;
import com.xuechuan.xcedu.utils.CompressOperate_zip4j;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.EncryptionUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.BankGradeVo;
import com.xuechuan.xcedu.vo.BuyVo;
import com.xuechuan.xcedu.vo.CollectOrErrorListVo;
import com.xuechuan.xcedu.vo.DayHomeBeanVo;
import com.xuechuan.xcedu.vo.DayHomeVo;
import com.xuechuan.xcedu.vo.ErrorOrCollectItemVo;
import com.xuechuan.xcedu.vo.NoteListVo;
import com.xuechuan.xcedu.vo.SqliteVo.CollectSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.ErrorSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.SubmitLogVo;
import com.xuechuan.xcedu.vo.SqliteVo.UserInfomSqliteVo;

import net.sqlcipher.database.SQLiteDatabase;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: BankTestFragment
 * @Package com.xuechuan.xcedu.fragment.bank
 * @Description: 新的题库界面
 * @author: L-BackPacker
 * @date: 2018.12.04 下午 4:02
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.12.04
 */
public class BankTestHomeFragment extends BaseFragment implements View.OnClickListener, FileDownContract.View, BankHomeGradeContract.View, BuyBankContract.View, DayExerciseContract.View, RequestCollectContract.View, RqeustErrorContract.View {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Context mContext;
    private TextView mTvBanktestSkill;
    private TextView mTvBanktextColligate;
    private TextView mTvBanktestCaseNew;
    private FrameLayout mFlContent;

    private int FragmentLayout = R.id.fl_banktest_content;
    private List<Fragment> fragments;
    private FileDownPresenter mPresenter;

    private static String TAG = "【" + BankTestHomeFragment.class + "】==";
    private DialogUtil mUpdataUtil;
    private BankHomeGradePresenter mBankGradePresenter;
    private DayExercisePresenter mDayExercisePresenter;
    private BuyBankPresenter mBuyBankPresenter;
    private DeleteSqliteHelp mDeleteSqliteHelp;
    //更新类
    private BankGradeVo.DataBean mGraderData;
    private UserInfomDbHelp mUserInfomHelp;
    private UserInfomSqliteVo mUserIofomVo;
    private boolean mCollectSuccess;
    private boolean mErrorSuccess;
    private SQLiteDatabase sqLiteDatabase;
    private String mDownDbPath;
    private File mDownFile;


    public static BankTestHomeFragment newInstance(String param1, String param2) {
        BankTestHomeFragment fragment = new BankTestHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bank_test_home, container, false);
        initView(view);
        return view;
    }
*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected int initInflateView() {
        return R.layout.fragment_bank_test_home;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initUtils();
        initData();
        fragments = creartFragment();

    }

    private void initUtils() {
        mBankGradePresenter = new BankHomeGradePresenter();
        mBankGradePresenter.initModelView(new BankHomeGradeModel(), this);
        mDayExercisePresenter = new DayExercisePresenter();
        mDayExercisePresenter.initModelView(new DayExerciseModel(), this);
        mBuyBankPresenter = new BuyBankPresenter();
        mBuyBankPresenter.initModelView(new BuyBankModel(), this);
        //删除表
        mDeleteSqliteHelp = DeleteSqliteHelp.get_Instance(mContext);
        mUserInfomHelp = UserInfomDbHelp.get_Instance(mContext);
        mUserIofomVo = mUserInfomHelp.findUserInfomVo();
    }

    private void initData() {
        mBuyBankPresenter.reqeusstUserBuy(mContext);
        mDayExercisePresenter.requestiDayExercise(mContext);
        mDayExercisePresenter.requestNoteCount(mContext);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mDayExercisePresenter != null)
            mDayExercisePresenter.requestNoteCount(mContext);
    }

    private void initRequest(String code) {
        mBankGradePresenter.reuqestGrade(mContext, code);
    }

    private boolean doOpenNewWork() {
        return PolyvSDKUtil.isOpenNetwork(mContext);
    }

    private void initView(View view) {
        mContext = getActivity();
        mTvBanktestSkill = (TextView) view.findViewById(R.id.tv_banktest_skill);
        mTvBanktestSkill.setOnClickListener(this);
        mTvBanktextColligate = (TextView) view.findViewById(R.id.tv_banktext_colligate_);
        mTvBanktextColligate.setOnClickListener(this);
        mTvBanktestCaseNew = (TextView) view.findViewById(R.id.tv_banktest_case_new);
        mTvBanktestCaseNew.setOnClickListener(this);
        mFlContent = (FrameLayout) view.findViewById(R.id.fl_banktest_content);
        mFlContent.setOnClickListener(this);
    }

    private List<Fragment> creartFragment() {
        List<Fragment> list = new ArrayList<>();
        SkillNewFragment skillNewFragment = SkillNewFragment.newInstance("", "");
        ColligateNewFragment colligateNewFragment = ColligateNewFragment.newInstance("", "");
        CaseNewFragment caseNewFragment = CaseNewFragment.newInstance("", "");
        list.add(skillNewFragment);
        list.add(colligateNewFragment);
        list.add(caseNewFragment);
        FragmentManager fm = getFragmentManager();
        for (int i = 0; i < list.size(); i++) {
            fm.beginTransaction().add(FragmentLayout, list.get(i)).hide(list.get(i)).commit();
        }
        fm.beginTransaction().show(skillNewFragment).commit();
        return list;
    }

    /**
     * @param fm     fragment管理器
     * @param list   fragment集合
     * @param layout 显示fragment 布局
     * @param id     要显示的集合的fragment 的几个
     * @return
     */
    private void showSelectFragment(FragmentManager fm, List<Fragment> list, int layout, int id) {
        if ((id + 1) > list.size()) {
            throw new IndexOutOfBoundsException("超出集合长度");
        }
        if (fm == null) {
            fm = getFragmentManager();
        }
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment fragment = list.get(id);
        if (!fragment.isVisible()) {
            if (!fragment.isAdded()) {
                transaction.add(layout, fragment, fragment.getClass().getName());
            } else {
                for (int i = 0; i < list.size(); i++) {
                    fm.beginTransaction().hide(list.get(i)).commit();
                }
                transaction.show(fragment);
            }
        }
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_banktest_skill://技术实务
                changeTextSize(true, false, false);
                showFragment(0);
                break;
            case R.id.tv_banktext_colligate_://综合案例
                changeTextSize(false, true, false);
                showFragment(1);

                break;
            case R.id.tv_banktest_case_new://案例分析
                changeTextSize(false, false, true);
                showFragment(2);
                break;
            default:

        }
    }

    private void changeTextSize(boolean skill, boolean col, boolean cas) {
        mTvBanktestSkill.setTextColor(mContext.getResources().getColor(skill ? R.color.text_title_color : R.color.text_fu_color));
        mTvBanktestSkill.setTextSize(skill ? 24 : 14);
        mTvBanktestSkill.setTypeface(Typeface.DEFAULT, skill ? Typeface.BOLD : Typeface.NORMAL);
        mTvBanktextColligate.setTextColor(mContext.getResources().getColor(col ? R.color.text_title_color : R.color.text_fu_color));
        mTvBanktextColligate.setTextSize(col ? 24 : 14);
        mTvBanktextColligate.setTypeface(Typeface.DEFAULT, col ? Typeface.BOLD : Typeface.NORMAL);
        mTvBanktestCaseNew.setTextColor(mContext.getResources().getColor(cas ? R.color.text_title_color : R.color.text_fu_color));
        mTvBanktestCaseNew.setTextSize(cas ? 24 : 14);
        mTvBanktestCaseNew.setTypeface(Typeface.DEFAULT, cas ? Typeface.BOLD : Typeface.NORMAL);
    }

    private void showFragment(int i) {
        if (fragments != null && !fragments.isEmpty())
            showSelectFragment(getFragmentManager(), fragments, FragmentLayout, i);
    }

    @Override
    public void Start(Progress progress) {
        if (mUpdataUtil != null) {
            mUpdataUtil.setUpdataBankDialogProgress(mContext, progress, false, false);
        }
    }

    @Override
    public void OnProgerss(Progress progress) {
        if (mUpdataUtil != null) {
            mUpdataUtil.setUpdataBankDialogProgress(mContext, progress, false, false);
        }
        L.e(TAG, "OnProgerss: ");
    }

    @Override
    public void OnFinish(final File file, Progress progress) {
        if (mUpdataUtil != null) {
            mUpdataUtil.setUpdataBankDialogProgress(mContext, progress, false, false);
        }
        jieyaZip(file);
        L.e(TAG, "OnFinish: ");
    }

    private void jieyaZip(File file) {
        mDownFile = file;
        CompressOperate_zip4j zip4j = CompressOperate_zip4j.get_Instance();
        String path = PolyvDevMountInfo.getInstance().getInternalSDCardPath() + File.separator + "xuechuan/databases/";
        zip4j.uncompressZip4j(file.getPath(), path, DataMessageVo.zipkey, new CompressOperate_zip4j.InfaceZip() {
            @Override
            public void UnZipNameSuccess(String path, String name) {
                selectInfom(path);
            }

            @Override
            public void UnZipNameError(String path) {
                Utils.delectFile(new File(path));
            }
        });
    }

    private void selectInfom(String path) {
        mDownDbPath = path;
        ansynectack ansynectack = new ansynectack();
        ansynectack.execute(path);
    }

    private class ansynectack extends AsyncTask<String, Void, SQLiteDatabase> {
        SqliteHelp mSqliteHelp;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSqliteHelp = SqliteHelp.get_Instance(mContext);
        }

        @Override
        protected SQLiteDatabase doInBackground(String... strings) {
            SQLiteDatabase mSqLiteDatabase = mSqliteHelp.acquireSqliteDb(strings[0]);
            if (mGraderData.getType().equals(DataMessageVo.GRADEADD)) {//添加
                mSqliteHelp.findQuestionSAll(mSqLiteDatabase);
            } else if (mGraderData.getType().equals(DataMessageVo.COVERDREADE)) {//覆盖
                mSqliteHelp.findTWOQuestionSAll(mSqLiteDatabase);
            }
            UserInfomDbHelp mDbHelp = UserInfomDbHelp.get_Instance(mContext);
            mDbHelp.upDateAddGrad(mGraderData.getVersioncode(), mGraderData.getVersionname(), TimeUtil.dateToStringOne(new Date()));
            return mSqLiteDatabase;
        }

        @Override
        protected void onPostExecute(SQLiteDatabase mSqLiteDatabase) {
            super.onPostExecute(mSqLiteDatabase);
            cancel(true);
            requestCollectData(mSqLiteDatabase);
        }
    }

    @Override
    public void OnRemove(Progress progress) {
        Log.e(TAG, "OnRemove: ");
    }

    @Override
    public void OnErrorr(Progress msg) {
        if (mUpdataUtil != null)
            mUpdataUtil.setUpdataBankDialogProgress(mContext, null, false, false);
        L.e(TAG, "OnErrorr: ");
    }

    @Override
    public void GradeSuccess(String success) {
        BankGradeVo vo = Utils.getGosnT(success, BankGradeVo.class);
        if (vo.getStatus().getCode() == 200) {
            doGradeBank(vo.getData());
        } else {
            T_ERROR(mContext);
        }
    }

    private void doGradeBank(BankGradeVo.DataBean data) {
        if (mUserIofomVo == null) return;
        mGraderData = data;
        if (StringUtil.isEmpty(mUserIofomVo.getBankversionname())) {
            initShowUpdataData();
            return;
        }
        if (data.getVersioncode() == mUserIofomVo.getBankversion()
                && data.getVersionname().equals(mUserIofomVo.getBankversionname())) {
            return;
        }

        initShowUpdataData();
    }

    private void initShowUpdataData() {
        mPresenter = new FileDownPresenter();
        mPresenter.initModelView(new FileDownModel(), this);
        mUpdataUtil = DialogUtil.getInstance();
        mUpdataUtil.showBankUpDialogOne(mContext, false);
        sendRequestBank();
    }

    /**
     * 请求收藏 和错题
     *
     * @param mSqLiteDatabase
     */
    private void requestCollectData(SQLiteDatabase mSqLiteDatabase) {
        RequestCollectPresenter presenter = new RequestCollectPresenter();
        presenter.initModelView(new RequestCollectModel(), this);
        presenter.requestCollect(mContext);
        RequestErrorPresenter errorpresenter = new RequestErrorPresenter();
        errorpresenter.initModelView(new RqeustErrorModel(), this);
        errorpresenter.requestError(mContext);
        sqLiteDatabase = mSqLiteDatabase;
        mUpdataUtil.setUpdataBankDialogProgress(mContext, null, false, true);
    }

    /**
     * 下载字典库
     */
    private void sendRequestBank() {
        File saveDir = new File(PolyvDevMountInfo.getInstance().getInternalSDCardPath() + File.separator + "xuechuan");
        mPresenter.fileDownRequest(mContext, mGraderData.getUrl(), saveDir.getAbsolutePath());
    }

    @Override
    public void GradeError(String msg) {
        T_ERROR(mContext);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {//显示
            if (mUserInfomHelp != null) {
                mUserIofomVo = mUserInfomHelp.findUserInfomVo();
//                String bankuptime = mUserIofomVo.getBankuptime();
//                if (!TimeUtil.isMoreHour(bankuptime)) {
//                    return;
//                }
                if (doOpenNewWork())
                    initRequest(String.valueOf(mUserIofomVo.getBankversion()));
            }
        }
    }

    @Override
    public void BuySuccess(String content) {
        BuyVo vo = Utils.getGosnT(content, BuyVo.class);
        if (vo.getStatus().getCode() == 200) {
            BuyVo.DataBean data = vo.getData();
            upDataUserInfomBuy(data);
        } else {
            T_ERROR(mContext);
        }
    }

    private void upDataUserInfomBuy(BuyVo.DataBean data) {
        String mSkillBuy = EncryptionUtil.getInstance().getUserBuy(DataMessageVo.BUYSKILL);
        String mColBuy = EncryptionUtil.getInstance().getUserBuy(DataMessageVo.BUYCOL);
        String mCaseBuy = EncryptionUtil.getInstance().getUserBuy(DataMessageVo.BUYCASE);
        String noBuy = EncryptionUtil.getInstance().getUserBuy(DataMessageVo.NOBUY);
        DataMessageVo vo = DataMessageVo.get_Instance();
        vo.setSkillBuy(mSkillBuy);
        vo.setCaseBuy(mCaseBuy);
        vo.setCollorBuy(mColBuy);
        UserInfomDbHelp instance = UserInfomDbHelp.get_Instance(mContext);
        instance.upDateBuy(getString(data.isCourse1(), mSkillBuy, noBuy), getString(data.isCourse2(), mColBuy, noBuy)
                , getString(data.isCourse3(), mCaseBuy, noBuy));
    }

    private String getString(boolean isBuy, String buy, String noBuy) {
        String pasw;
        if (isBuy) {
            pasw = buy;
        } else {
            pasw = noBuy;
        }
        return pasw;
    }

    @Override
    public void BuyError(String msg) {
        T_ERROR(mContext);
    }

    //    每日一练
    @Override
    public void DaySuccess(String success) {
        DayHomeVo homeVo = Utils.getGosnT(success, DayHomeVo.class);
        if (homeVo.getStatus().getCode() == 200) {
            List<DayHomeBeanVo> datas = homeVo.getDatas();
            if (datas == null || datas.isEmpty()) return;
            EventBus.getDefault().postSticky(new DayHomeEvent(1, datas, null));
        } else {
            T_ERROR(mContext);
        }
    }

    @Override
    public void DayError(String msg) {
        T_ERROR(mContext);
    }

    @Override
    public void NoteCountSuccess(String success) {
        NoteListVo listVo = Utils.getGosnT(success, NoteListVo.class);
        if (listVo.getStatus().getCode() == 200) {
            List<NoteListVo.DatasBean> datas = listVo.getDatas();
            if (datas == null || datas.isEmpty()) return;
            EventBus.getDefault().postSticky(new DayHomeEvent(2, null, datas));
        } else {
            T_ERROR(mContext);
        }


    }

    @Override
    public void NoteCountError(String msg) {
        T_ERROR(mContext);
    }

    @Override
    public void getCollectSuccess(String success) {
        CollectAsynectTack tack = new CollectAsynectTack();
        tack.execute(success);
    }

    private class CollectAsynectTack extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            CollectOrErrorListVo vo = Utils.getGosnT(strings[0], CollectOrErrorListVo.class);
            if (vo.getStatus().getCode() == 200) {
                List<ErrorOrCollectItemVo> datas = vo.getDatas();
                if (datas == null || datas.isEmpty()) return null;
                //收藏表
                CollectSqliteHelp sqliteHelp = CollectSqliteHelp.get_Instance(mContext);
                //问题表
                QuestionSqliteHelp questionSqliteHelp = QuestionSqliteHelp.get_Instance(mContext);
                List<QuestionSqliteVo> list = questionSqliteHelp.getQuestionList(datas);
                if (list == null || list.isEmpty()) return null;
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
                SubmiteLogHelp submiteLogHelp = SubmiteLogHelp.get_Instance(mContext);
                SubmitLogVo submitLogVo = new SubmitLogVo();
                submitLogVo.setSaffid(mUserIofomVo.getSaffid());
                submitLogVo.setCollect(1);
                submiteLogHelp.addItem(submitLogVo);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            cancel(true);
            mCollectSuccess = true;
            setSuccess();
        }
    }

    @Override
    public void getCollectError(String msg) {
        T_ERROR(mContext);
        mErrorSuccess = true;
        setSuccess();
    }

    @Override
    public void getErrorSuccess(String success) {
        ErrorAsynectack asynectack = new ErrorAsynectack();
        asynectack.execute(success);
    }

    private class ErrorAsynectack extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            CollectOrErrorListVo vo = Utils.getGosnT(strings[0], CollectOrErrorListVo.class);
            if (vo.getStatus().getCode() == 200) {
                List<ErrorOrCollectItemVo> list = vo.getDatas();
                if (list == null || list.isEmpty()) return null;
                //错题表
                ErrorSqlteHelp sqlteHelp = ErrorSqlteHelp.getInstance(mContext);
                //错题集合
                QuestionSqliteHelp questionSqliteHelp = QuestionSqliteHelp.get_Instance(mContext);
                List<QuestionSqliteVo> questionList = questionSqliteHelp.getQuestionList(list);
                if (questionList == null || questionList.isEmpty()) return null;
                for (int i = 0; i < questionList.size(); i++) {//保存用户记录
                    QuestionSqliteVo questionSqliteVo = questionList.get(i);
                    ErrorSqliteVo sqliteVo = new ErrorSqliteVo();
                    sqliteVo.setCourseid(questionSqliteVo.getCourseid());
                    sqliteVo.setChapterid(questionSqliteVo.getChapter_id());
                    sqliteVo.setQuesitonid(questionSqliteVo.getQuestion_id());
                    sqliteVo.setUserid(String.valueOf(mUserIofomVo.getSaffid()));
                    sqliteVo.setRightnumber(0);
                    sqlteHelp.addErrorItem(sqliteVo);
                }
                SubmiteLogHelp submiteLogHelp = SubmiteLogHelp.get_Instance(mContext);
                SubmitLogVo submitLogVo = new SubmitLogVo();
                submitLogVo.setSaffid(mUserIofomVo.getSaffid());
                submitLogVo.setError(1);
                submiteLogHelp.addItem(submitLogVo);

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            cancel(true);
            mErrorSuccess = true;
            setSuccess();
        }
    }

    private void setSuccess() {
        if (mErrorSuccess && mCollectSuccess) {
            if (mUpdataUtil != null) {
                mUpdataUtil.setUpdataBankDialogProgress(mContext, null, true, false);
                EventBus.getDefault().postSticky(new DayHomeEvent(3, null, null));
            }
        }
        if (mDownFile != null) {
            mDownFile.delete();
        }
        if (!StringUtil.isEmpty(mDownDbPath)) {
            File file = new File(mDownDbPath);
            file.delete();
        }
        mDownFile = null;
        mDownDbPath = null;

    }

    @Override
    public void getErrorError(String msg) {
        T_ERROR(mContext);
        mErrorSuccess = true;
        setSuccess();
    }
}

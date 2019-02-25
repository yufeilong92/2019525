package com.xuechuan.xcedu.ui.bank.newBankActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.easefun.polyvsdk.PolyvSDKUtil;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.bank.CaseAnswerAdapter;
import com.xuechuan.xcedu.adapter.bank.CaseExamAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.event.GmChangerColorEvent;
import com.xuechuan.xcedu.fragment.GmReadThreeFragment;
import com.xuechuan.xcedu.fragment.view.CaseExamInterface;
import com.xuechuan.xcedu.mvp.contract.ExamPostContract;
import com.xuechuan.xcedu.mvp.model.ExamPostModel;
import com.xuechuan.xcedu.mvp.presenter.ExamPostPresenter;
import com.xuechuan.xcedu.mvp.view.TimeShowView;
import com.xuechuan.xcedu.sqlitedb.CollectSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.DoBankSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.DoKaoShiSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.DoLogProgressSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.DoMockBankSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.DoUpLogProgressSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.ErrorSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.KaoShiSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.QuestionExamrelarionSqlitHelp;
import com.xuechuan.xcedu.sqlitedb.QuestionSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.UpCollectSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.UpDoBankSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.UpErrorSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.UserInfomDbHelp;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.GmReadColorManger;
import com.xuechuan.xcedu.utils.GmTextUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.MyTimeUitl;
import com.xuechuan.xcedu.utils.SaveDataListUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.TimeTools;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.vo.CaseCardVo;
import com.xuechuan.xcedu.vo.QuestionCaseVo;
import com.xuechuan.xcedu.vo.SqliteVo.CollectSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.ErrorSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.KaoShiSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuesitonExamRaltionSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.UserInfomSqliteVo;
import com.xuechuan.xcedu.vo.SubmiteExamVo;
import com.xuechuan.xcedu.weight.CommonPopupWindow;
import com.xuechuan.xcedu.weight.ReaderViewPager;
import com.xuechuan.xcedu.xunfei.basic.VideoUitls;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: OrderTestActivity
 * @Package com.xuechuan.xcedu.ui.bank
 * @Description: 案例模拟考试
 * @author: L-BackPacker
 * @date: 2018.12.21 下午 4:06
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.12.21
 */
public class CaseExamtActivity extends BaseActivity implements View.OnClickListener, ExamPostContract.View, CaseExamInterface, TimeShowView {
    private static String PARAMT_KEY = "com.xuechuan.xcedu.ui.bank.courserid";
    private static String PARAMT1_KEY = "com.xuechuan.xcedu.ui.bank.chaterid";
    private static String PARAMT2_KEY = "com.xuechuan.xcedu.ui.bank.type";
    private ImageView mIvTextBarBack;
    private ImageView mIvTextBarTimeImg;
    private TextView mActivityTitleText;
    private LinearLayout mLlTextBarTitle;
    private ImageView mIvTextBarDelect;
    private ImageView mIvTextBarMore;
    private LinearLayout mLlTextTitleBar;
    private View mVGmReadLine;
    private ImageView mShadowView;
    private FrameLayout mFlContentLayoutOne;
    private View mVGmBarLine;
    private TextView mTvTextCollect;
    private ImageView mIvTextMenu;
    private TextView mTvTextQid;
    private TextView mTvTextAllqid;
    private TextView mTvTextShare;
    private LinearLayout mLiTextNavbar;
    private LinearLayout mLlNewtextBar;
    private String mCourseId;
    private Context mContext;
    //帮助类
    private GmTextUtil mTextUtil;
    //做题记录临时表
    private DoBankSqliteHelp mDoBankHelp;
    //进度类
    private DoLogProgressSqliteHelp mDoLogProgressSqliteHelp;
    //第一个数据
    private List<CaseCardVo> mMainDataLists;
    //第二个数据
    private List<CaseCardVo> mFuDataLists;
    private GmReadThreeFragment mReadFragment;
    private GmReadColorManger mColorManger;
    //问题帮助类
    private QuestionSqliteHelp mQuestionSqliteHelp;
    private CommonPopupWindow mPopAnswer;
    private LinearLayout mCaseExamLayout;
    //做题本地帮助
    private DoMockBankSqliteHelp mDoMockBankSqliteHelp;
    private UserInfomDbHelp mInfomdbhelp;
    //上传帮助类
    private UpDoBankSqlteHelp mUpDoBankSqlteHelp;
    //错题类
    private ErrorSqlteHelp mErrorSqlteHelp;
    private TextView mTvEmpty;
    private ReaderViewPager mReaderViewPagerone;
    private ReaderViewPager mReaderViewPagertwo = null;
    //对话框工具类
    private DialogUtil mDialogUtil;
    //是否主问题
    private boolean mainquestion = true;
    private GmReadThreeFragment mReadFragmentTwo;
    private GmFragmentAdpaterTwo mTwoAdapter;
    private int mQuesiton_id = -1;
    private GmFragmentAdpater mOneAdapter;
    private ReaderViewPager mReaderViewPager;
    private SaveDataListUtil mDataListUtil;
    private ImageView mIvGmbanJianpan;
    private EditText mEtGmSubmit;
    private ImageView mIvGmSubmitSend;
    private LinearLayout mLlGmSubmitEvalue;
    private CheckBox mChbGmCollect;
    private CaseCardVo mCaseCardVo;
    //收藏
    private CollectSqliteHelp mCollectSqliteHelp;
    //时间
    private MyTimeUitl mTimeUitl;
    //暂停对话框
    private AlertDialog mStopDialog;
    //用于判断用户是否交卷
    private boolean mSubmit = false;
    private CommonPopupWindow mPopResult;
    /**
     * 用户使用时间
     */
    private String mUserDoTime;
    private ExamPostPresenter mPresenter;
    /**
     * 是否自评
     */
    private boolean mEvalueteAble = false;
    private TextView mTvCaseExamSubmit;
    private LinearLayout mOrderLayout;
    private UpErrorSqlteHelp mUpErrorSqlteHelp;
    private UpCollectSqlteHelp mUpCollectSqlteHelp;
    private DoUpLogProgressSqliteHelp mUpLogProgressSqliteHelp;
    private VideoUitls mVideoUitls;
    private KaoShiSqliteHelp mKaoShiSqliteHelp;
    private DoKaoShiSqlteHelp mDoKaoShiSqlteHelp;
    private String mChapter_id;
    private int mType;
    //显示时间
    private String mShowTime = "00:00:00";
    private QuestionExamrelarionSqlitHelp mQuestionExamHelp;

    /**
     * @param context
     * @param courseid 科目
     */
    public static Intent start_Intent(Context context, String courseid, String chapterid, int type) {
        Intent intent = new Intent(context, CaseExamtActivity.class);
        intent.putExtra(PARAMT_KEY, courseid);
        intent.putExtra(PARAMT1_KEY, chapterid);
        intent.putExtra(PARAMT2_KEY, type);
        return intent;
    }
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_exam_test);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_case_exam_test);
        if (getIntent() != null) {
            mCourseId = getIntent().getStringExtra(PARAMT_KEY);
            mChapter_id = getIntent().getStringExtra(PARAMT1_KEY);
            mType = getIntent().getIntExtra(PARAMT2_KEY, 0);
        }
        initView();
        initUtils();
        doNextEvent(getLists());
    }

    private void doNextEvent(List<CaseCardVo> list) {
        mMainDataLists = list;
        //初始化时间
        initTime();
        initConfig();
        mTvTextShare.setVisibility(View.INVISIBLE);
        //初始化翻页效果
        initReadViewPagerOne();
        //显示下表
        initData(0);
        //显示对话框

        initEvent();
        //初始化试卷
        initExam();
    }

    private void initExam() {
        switch (mType) {
            case 0:
                break;
            case 1:
                cancelTime();
                mSubmit = true;
                mActivityTitleText.setText(mShowTime);
                break;


        }
    }

    private void initConfig() {
        if (mMainDataLists == null || mMainDataLists.isEmpty()) {
            mFlContentLayoutOne.setVisibility(View.GONE);
            mTvEmpty.setVisibility(View.VISIBLE);
            cancelTime();
        } else {
            mTvEmpty.setVisibility(View.GONE);
            mFlContentLayoutOne.setVisibility(View.VISIBLE);
        }
    }

    private void initTime() {
        mTimeUitl.start(mActivityTitleText, 3, 0, 0, this);
        mTimeUitl.restart();
        setPauseRestartImg(true, R.mipmap.qbank_answer_icon_pau);
    }

    private void setPauseRestartImg(boolean isShow, int id) {
        mIvTextBarTimeImg.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mIvTextBarTimeImg.setImageDrawable(getDrawable(mContext, id));
    }

    private void initEvent() {
        mChbGmCollect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!mChbGmCollect.isPressed()) {
                    return;
                }
                CaseCardVo mCaseCardVo = mMainDataLists.get(prePosition1);
                if (mCaseCardVo.getCasevo() != null) {
                    CollectSqliteVo vo = new CollectSqliteVo();
                    vo.setCollectable(isChecked ? 1 : 0);
                    vo.setChapterid(mCaseCardVo.getCasevo().getChapter_id());
                    vo.setCourseid(Integer.parseInt(mCourseId));
                    vo.setQuestion_id(mCaseCardVo.getCasevo().getQuestion_id());
                    vo.setQuestiontype(mCaseCardVo.getCasevo().getQuestiontype());
                    vo.setTime(String.valueOf(new Date().getTime()));
                    if (isChecked) {
                        mCollectSqliteHelp.addCoolectItem(vo);
                        mUpCollectSqlteHelp.addCollectSqliteVo(vo);
                        T.showToast(mContext, "收藏成功");
                    } else {
                        T.showToast(mContext, "取消收藏");
                        mCollectSqliteHelp.deleteItem(Integer.parseInt(mCourseId),
                                mCaseCardVo.getCasevo().getChapter_id(), mCaseCardVo.getCasevo().getQuestion_id());
                        mUpCollectSqlteHelp.deleteItem(Integer.parseInt(mCourseId),
                                mCaseCardVo.getCasevo().getChapter_id(), mCaseCardVo.getCasevo().getQuestion_id());
                    }
                } else {
                    T.showToast(mContext, "暂无题收藏");
                }
            }
        });
    }

    private void initUtils() {
        mTextUtil = GmTextUtil.get_Instance(mContext);
        mDoBankHelp = DoBankSqliteHelp.get_Instance(mContext);
        mDoLogProgressSqliteHelp = DoLogProgressSqliteHelp.get_Instance(mContext);
        mDialogUtil = DialogUtil.getInstance();
        mQuestionSqliteHelp = QuestionSqliteHelp.get_Instance(mContext);
        //用户做题记录表（记录）
        mDoMockBankSqliteHelp = DoMockBankSqliteHelp.get_Instance(mContext);
        //用户信息表
        mInfomdbhelp = UserInfomDbHelp.get_Instance(mContext);
        //上传用户表
        mUpDoBankSqlteHelp = UpDoBankSqlteHelp.getInstance(mContext);
        //错题表
        mErrorSqlteHelp = ErrorSqlteHelp.getInstance(mContext);
        //收藏表
        mCollectSqliteHelp = CollectSqliteHelp.get_Instance(mContext);
        //时间
        mTimeUitl = MyTimeUitl.getInstance(mContext);

        mDataListUtil = SaveDataListUtil.getInstance();
        //上传做题表
        mUpErrorSqlteHelp = UpErrorSqlteHelp.getInstance(mContext);
        //上传收藏表
        mUpCollectSqlteHelp = UpCollectSqlteHelp.getInstance(mContext);
        //上传进度表
        mUpLogProgressSqliteHelp = DoUpLogProgressSqliteHelp.get_Instance(mContext);
        mVideoUitls = VideoUitls.get_singleton(mContext);

        mKaoShiSqliteHelp = KaoShiSqliteHelp.get_Instance(mContext);

        mDoKaoShiSqlteHelp = DoKaoShiSqlteHelp.getInstance(mContext);

        mQuestionExamHelp = QuestionExamrelarionSqlitHelp.get_Instance(mContext);

        //提交试卷
        mPresenter = new ExamPostPresenter();
        mPresenter.initModelView(new ExamPostModel(), this);
    }

    public List<CaseCardVo> getLists() {
        List<QuesitonExamRaltionSqliteVo> mExamVos = mQuestionExamHelp.queryExamRaltionVo(mChapter_id);
        List<QuestionCaseVo> mParentLists = mQuestionSqliteHelp.queryQuestionExamVo(mExamVos);
        List<QuestionCaseVo> mChlidLists = mQuestionSqliteHelp.queryQuestionChildListVos(mParentLists);
        List<CaseCardVo> mCardLists = mTextUtil.getDataListCaseEventFour(mParentLists, mChlidLists);
        return mCardLists;
    }

    /**
     * 重组只有简答题数据
     *
     * @return
     */
    private List<CaseCardVo> getGalleryList() {

        List<QuesitonExamRaltionSqliteVo> mExamVos = mQuestionExamHelp.queryExamRaltionVo(mChapter_id);
        List<QuestionCaseVo> mParentLists = mQuestionSqliteHelp.queryQuestionExamVo(mExamVos);
        List<QuestionCaseVo> mChlidLists = mQuestionSqliteHelp.queryQuestionCaseVoChildJianOne(mParentLists);
        List<CaseCardVo> mCardLists = mTextUtil.getDataListCaseEventFive(mParentLists, mChlidLists);
        if (mCardLists == null || mCardLists.isEmpty()) return mCardLists;
//        int size = mCardLists.size();
//        for (int i = 0; i < size; i++) {
//            QuestionCaseVo vo = mainlist.get(i);
//            //父数据
//            CaseCardVo cardVo = new CaseCardVo();
//            cardVo.setCasevo(vo);
//            cardVo.setMaintype(1);
//            cardVo.setType(1);
//            //子数据
//            List<QuestionCaseVo> childs = mQuestionSqliteHelp.queryQuestionCaseVoChildJianOne(
//                    vo.getQuestion_id(), vo.getChapter_id(), Integer.valueOf(mCourseId));
//            if (childs != null && !childs.isEmpty()) {
//                List<CaseCardVo> caseCardVos = new ArrayList<>();
//                int size1 = childs.size();
//                for (int k = 0; k < size1; k++) {
//                    QuestionCaseVo childvo = childs.get(k);
//                    CaseCardVo caseCardVo = new CaseCardVo();
//                    caseCardVo.setType(2);
//                    caseCardVo.setMaintype(2);
//                    caseCardVo.setCasevo(childvo);
//                    caseCardVos.add(caseCardVo);
//                }
//                cardVo.setList(caseCardVos);
//                mCardLists.add(cardVo);
//            }
//        }
        return mCardLists;
    }

    private void initData(int index) {
        mTvTextAllqid.setText(String.valueOf(mMainDataLists.size()));
        mTvTextQid.setText(String.valueOf(++index));
    }

    private int prePosition1 = 0;
    private int curPosition1;

    private void initReadViewPagerOne() {
        mOneAdapter = new GmFragmentAdpater(getSupportFragmentManager(), mContext, mCourseId);
        mReaderViewPagerone.setAdapter(mOneAdapter);
        mReaderViewPagerone.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mShadowView.setTranslationX(mReaderViewPagerone.getWidth() - positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                curPosition1 = position;
                prePosition1 = curPosition1;
                initData(position);
                initfuPostion();
                changerCllect();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mReaderViewPagerone.setOffscreenPageLimit(3);
    }

    //向前滑动
    private void changerMainView() {
        if (prePosition1 == 0) {
            T.showToast(mContext, "已经是第一道题");
            return;
        }
        doMainFuView(true, false);
        //初始化翻页效果
        initfuPostion();
        curPosition1 -= 1;
        doEventHine(false);
        mOneAdapter.notifyDataSetChanged();
    }

    //向后滑动
    private void changerFuView() {
        if (prePosition1 == mMainDataLists.size() - 1) {
            T.showToast(mContext, "已经是最后一道题");
            return;
        }
        doMainFuView(true, false);
        //初始化翻页效果
        initfuPostion();
        curPosition1 += 1;
        doEventHine(false);
        mOneAdapter.notifyDataSetChanged();
    }

    //初始子类游标
    private void initfuPostion() {
        curPosition2 = 0;

    }

    /**
     * @param main 主界面
     * @param fu   副界面
     */
    private void doMainFuView(boolean main, boolean fu) {
        mainquestion = main;
        mReaderViewPagerone.setVisibility(main ? View.VISIBLE : View.GONE);
        mReaderViewPagertwo.setVisibility(fu ? View.VISIBLE : View.GONE);
        if (fu && mEvalueteAble) {
            SettingOrSubmit(false);
        } else {
            SettingOrSubmit(true);
        }
    }

    private void SettingOrSubmit(boolean show) {
        mTvCaseExamSubmit.setVisibility(show ? View.GONE : View.VISIBLE);
        mIvTextBarMore.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void initView() {
        mContext = this;
        mIvTextBarBack = (ImageView) findViewById(R.id.iv_text_bar_back);
        mIvTextBarBack.setOnClickListener(this);
        mIvTextBarTimeImg = (ImageView) findViewById(R.id.iv_text_bar_time_img);
        mActivityTitleText = (TextView) findViewById(R.id.activity_title_text);
        mLlTextBarTitle = (LinearLayout) findViewById(R.id.ll_text_bar_title);
        mLlTextBarTitle.setOnClickListener(this);
        mIvTextBarDelect = (ImageView) findViewById(R.id.iv_text_bar_delect);
        mIvTextBarMore = (ImageView) findViewById(R.id.iv_text_bar_more);
        mIvTextBarMore.setOnClickListener(this);
        mLlTextTitleBar = (LinearLayout) findViewById(R.id.ll_text_title_bar);
        mVGmReadLine = (View) findViewById(R.id.v_gm_read_line);
        mShadowView = (ImageView) findViewById(R.id.shadowView);
        mFlContentLayoutOne = (FrameLayout) findViewById(R.id.fl_content_layout_one);
        mVGmBarLine = (View) findViewById(R.id.v_gm_bar_line);
        mTvTextCollect = (TextView) findViewById(R.id.tv_text_collect);
        mIvTextMenu = (ImageView) findViewById(R.id.iv_text_menu);
        mIvTextMenu.setOnClickListener(this);
        mTvTextQid = (TextView) findViewById(R.id.tv_text_qid);
        mTvTextAllqid = (TextView) findViewById(R.id.tv_text_allqid);
        mTvTextShare = (TextView) findViewById(R.id.tv_text_share);
        mLiTextNavbar = (LinearLayout) findViewById(R.id.li_text_navbar);
        mLlNewtextBar = (LinearLayout) findViewById(R.id.ll_newtext_bar);

        mCaseExamLayout = (LinearLayout) findViewById(R.id.case_exam_layout);
        mCaseExamLayout.setOnClickListener(this);
        mTvEmpty = (TextView) findViewById(R.id.tv_empty);
        mTvEmpty.setOnClickListener(this);
        mReaderViewPagerone = (ReaderViewPager) findViewById(R.id.readerViewPager);
        mReaderViewPagerone.setOnClickListener(this);
        mReaderViewPagertwo = (ReaderViewPager) findViewById(R.id.readerViewPagertwo);
        mReaderViewPagertwo.setOnClickListener(this);
        mReaderViewPager = (ReaderViewPager) findViewById(R.id.readerViewPager);
        mReaderViewPager.setOnClickListener(this);
        mIvGmbanJianpan = (ImageView) findViewById(R.id.iv_gmban_jianpan);
        mIvGmbanJianpan.setOnClickListener(this);
        mEtGmSubmit = (EditText) findViewById(R.id.et_gm_submit);
        mEtGmSubmit.setOnClickListener(this);
        mIvGmSubmitSend = (ImageView) findViewById(R.id.iv_gm_submit_send);
        mIvGmSubmitSend.setOnClickListener(this);
        mLlGmSubmitEvalue = (LinearLayout) findViewById(R.id.ll_gm_submit_evalue);
        mLlGmSubmitEvalue.setOnClickListener(this);
        mChbGmCollect = (CheckBox) findViewById(R.id.chb_gm_collect);
        mChbGmCollect.setOnClickListener(this);
        mTvCaseExamSubmit = (TextView) findViewById(R.id.tv_case_exam_submit);
        mTvCaseExamSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_text_menu://菜单
                //判断用户是否提交 显示不同答题卡布局
                if (mSubmit) {//显示答案解析
                    showResultPopLayout();
                } else {//显示未交接界面
                    showAnswerCardLayout();
                }
                break;
            case R.id.iv_text_bar_more://更多
                if (mReadFragment != null) {
                    mReadFragment.showGmSetting();
                }
                break;
            case R.id.iv_text_bar_back://返回
                showBackFinishDialog();
                break;
            case R.id.iv_gmTitle_mulu://目录
                doMainFuView(true, false);
                break;
            case R.id.ll_text_bar_title://时间
                setPauseRestartImg(true, R.mipmap.qbank_answer_icon_cont);
                pauseTime();
                showPauseDialog();
                break;
            case R.id.tv_case_exam_submit://交卷
                //判断用户是否做完
                doUserSumbitDialog();
                //暂停时间
                setPauseRestartImg(true, R.mipmap.qbank_answer_icon_cont);
                break;
            case R.id.tv_text_share:
                EventBus.getDefault().postSticky(new GmChangerColorEvent(0, 4, false));
                break;
        }
    }


    private void showBackFinishDialog() {
        mDialogUtil.showTitleDialog(mContext, "确定退出答题", "退出答题"
                , "取消", true);
        mDialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
            @Override
            public void onSureClickListener() {
                finish();
            }

            @Override
            public void onCancelClickListener() {
            }
        });

    }

    //显示暂停对话框
    private void showPauseDialog() {
        mStopDialog = mDialogUtil.showStopDialog(mContext);
        setStopDialogListener(mDialogUtil);
    }

    private void setStopDialogListener(DialogUtil util) {
        util.setStopClickListener(new DialogUtil.onStopClickListener() {
            @Override
            public void onNextClickListener() {
                dismissDialog(mStopDialog);
                setPauseRestartImg(true, R.mipmap.qbank_answer_icon_pau);
                resumeTime();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseTime();
    }


    private void resumeTime() {
        if (mTimeUitl != null)
            mTimeUitl.resume();
    }

    private void pauseTime() {
        if (mTimeUitl != null)
            mTimeUitl.pause();
    }

    private void cancelTime() {
        if (mTimeUitl != null)
            mTimeUitl.cancel();
    }

    private void restartTime() {
        if (mTimeUitl != null)
            mTimeUitl.restart();
    }

    @Override
    public void saveUserDoLog(DoBankSqliteVo vo) {
        if (vo == null) return;
        mDoBankHelp.addDoBankItem(vo);
        mDoMockBankSqliteHelp.addDoBankItem(vo);
        mUpDoBankSqlteHelp.addDoBankItem(vo);

    }

    /**
     * 保存用户解析
     *
     * @param position
     */
    public void saveAnsysicDataEvent(int position) {
        DoBankSqliteVo vo = new DoBankSqliteVo();
        CaseCardVo caseCardVo = getLists().get(curPosition1);
        CaseCardVo casedata = caseCardVo.getList().get(position);
        QuestionCaseVo casevo = casedata.getCasevo();
        if (mReadFragmentTwo != null) {
            String ansysic = mReadFragmentTwo.getUserAnsysic();
            vo.setAnalysis(ansysic);
        }
        vo.setIsmos(0);
        //科id
        vo.setCourseid(Integer.parseInt(mCourseId));
        //问题具体id
        vo.setQuestion_id(casevo.getQuestion_id());
        //父类id
        vo.setParent_id(casevo.getParent_id());
        //子类id
        vo.setChild_id(casevo.getQuestion_id());
        //问题类型
        vo.setQuestiontype(casevo.getQuestiontype());
        //主键id
        vo.setMockkeyid(casevo.getId());
        mDoBankHelp.addDoBankItem(vo);
    }

    @Override
    public DoBankSqliteVo getUserDoLog(int quesiton_id) {
        if (mDoBankHelp == null) return null;
        return mDoBankHelp.queryWQid(quesiton_id);
    }

    @Override
    public void deleteUserDolog(int quesiton_id) {
        mDoBankHelp.deleteBankWithQuestid(quesiton_id);
    }

    /**
     * 下一题
     */
    @Override
    public void doRightGo(int postion) {
        if (mFuDataLists != null && mFuDataLists.size() == postion) {
            changerFuView();
        } else {
            curPosition2 = postion;
            prePosition2 = postion;
            mReaderViewPagertwo.setCurrentItem(postion);
        }
        EventBus.getDefault().postSticky(new GmChangerColorEvent(0, 3, false));
    }

    @Override
    public void changerColor(GmReadColorManger colorManger) {
        this.mColorManger = colorManger;
        mLiTextNavbar.setBackgroundColor(colorManger.getmLayoutBgColor());
        mTvTextCollect.setTextColor(colorManger.getmTextFuColor());
        mTvTextShare.setTextColor(colorManger.getmTextFuColor());
        mTvTextQid.setTextColor(colorManger.getmTextRedColor());
        mTvTextAllqid.setTextColor(colorManger.getmTextRedColor());
        mLlTextTitleBar.setBackgroundColor(colorManger.getmLayoutBgColor());
        mActivityTitleText.setTextColor(colorManger.getmTextTitleColor());
        mLlTextTitleBar.setBackgroundColor(colorManger.getmLayoutBgColor());
        mVGmReadLine.setBackgroundColor(colorManger.getmCutLineColor());
        //标题
        mLlTextTitleBar.setBackgroundColor(colorManger.getmLayoutBgColor());
        mVGmBarLine.setBackgroundColor(colorManger.getmCutLineColor());

    }

    @Override
    public void doErrorLog(ErrorSqliteVo vo) {
        if (vo == null) {
            return;
        }
        mErrorSqlteHelp.addErrorItem(vo);
        mUpErrorSqlteHelp.addErrorItem(vo);
    }

    //改变副题
    @Override
    public void changerSmallQuestion(int postion) {
        doChildEvent(postion, curPosition2);
    }

    /**
     * 初始化子类数据
     *
     * @param postion 父类id
     * @param childid 子类id
     */
    public void doChildEvent(int postion, int childid) {
        if (mFuDataLists != null)
            mFuDataLists.clear();
        CaseCardVo vo = null;
        if (mEvalueteAble) {
            vo = getGalleryList().get(postion);
        } else {
            vo = getLists().get(postion);
        }
        if (vo.getList() == null || vo.getList().isEmpty()) {
            T.showToast(mContext, "暂无小题");
        } else {
            doMainFuView(false, true);
            mFuDataLists = vo.getList();
            if (mTwoAdapter != null) {
                mTwoAdapter.notifyDataSetChanged();
            }
            initReadViewPagerTwo();
            mReaderViewPagertwo.setCurrentItem(childid, true);
        }
    }

    @Override
    public void changerMainQuesiton() {
        doMainFuView(true, false);
        mFuDataLists = null;
    }

    @Override
    public boolean submitAble() {
        return this.mSubmit;
    }

    @Override
    public void doEventHine(boolean isShow) {
        mReaderViewPager.setCurrentItem(curPosition1, true);
        mLlNewtextBar.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }

    @Override
    public boolean getMainQuesiton() {
        return mainquestion;
    }


    @Override
    public int getFuPostionList() {
        if (mFuDataLists == null || mFuDataLists.isEmpty())
            return 0;
        return mFuDataLists.size();
    }

    @Override
    public QuestionSqliteVo getQuestionVo(int id) {
        QuestionSqliteVo vo = mQuestionSqliteHelp.queryQuestionVoWithId(id);
/*        String d = EncryptionUtil.D(vo.getQuestion());
        vo.setQuestionStr(d);
        String keyword = EncryptionUtil.D(vo.getKeywords());
        vo.setKeywordStr(keyword);
        String explain = EncryptionUtil.D(vo.getExplained());
        vo.setExplainedStr(explain);*/
        return vo;
    }

    @Override
    public void changerCllect() {
        if (mMainDataLists == null || mMainDataLists.isEmpty()) return;
        CaseCardVo vo = null;
        if (mMainDataLists.size() <= prePosition1) {
            vo = mMainDataLists.get(prePosition1 - 1);
        } else {
            vo = mMainDataLists.get(prePosition1);
        }
        initCollect(vo.getCasevo());
    }

    @Override
    public boolean getmEvalueteAble() {
        return mEvalueteAble;
    }

    @Override
    public List<CaseCardVo> getFuDataLists() {
        return mFuDataLists;
    }

    @Override
    public void MoveItem(int postion) {
        mReaderViewPagertwo.setCurrentItem(postion);
    }

    /**
     * 自评
     *
     * @param vo
     */
    @Override
    public void upDataEvalues(DoBankSqliteVo vo) {
        if (vo == null) return;
        mDoBankHelp.addDoBankItemOne(vo);
    }

    @Override
    public void videoInput(EditText editText) {
        mVideoUitls.initData(editText);
        mVideoUitls.clickMethod();
    }

    private void initCollect(QuestionCaseVo vo) {
        CollectSqliteVo sqliteVo = mCollectSqliteHelp.queryCollectVo(Integer.parseInt(mCourseId),
                vo.getChapter_id(), vo.getQuestion_id());
        if (sqliteVo != null) {
            mChbGmCollect.setChecked(sqliteVo.getCollectable() == 1);
        } else {
            mChbGmCollect.setChecked(false);
        }
    }

    private int prePosition2 = -1;
    private int curPosition2;
    private static String TAG = "【" + CaseExamtActivity.class + "】==";

    private void initReadViewPagerTwo() {
        mTwoAdapter = new GmFragmentAdpaterTwo(getSupportFragmentManager(), mContext, mCourseId);
        mReaderViewPagertwo.setAdapter(mTwoAdapter);
        mReaderViewPagertwo.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mShadowView.setTranslationX(mReaderViewPagertwo.getWidth() - positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                curPosition2 = position;
                prePosition2 = curPosition2;

            }

            private boolean mIsScrolled;                   //  viewpager是否处于惯性滑动

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        mIsScrolled = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        mIsScrolled = true;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (!mIsScrolled) {
//                            Log.e("===", "执行");
                            if (prePosition2 == 0) {//从左到右
                                changerMainView();
//                                if (StringUtil.isEmpty(mReadFragmentTwo.getUserAnsysic())) return;
//                                saveAnsysicDataEvent(curPosition2);
                            } else if (prePosition2 == mFuDataLists.size() - 1) {//从右到左
                                changerFuView();
//                                if (StringUtil.isEmpty(mReadFragmentTwo.getUserAnsysic())) return;
//                                saveAnsysicDataEvent(curPosition2);
                            }
                        }
                        mIsScrolled = true;
                        break;
                }
            }
        });

    }


    @Override
    public DoBankSqliteVo queryUserData(int qustion_id) {
        return mDoBankHelp.queryWQid(qustion_id);
    }


    /**
     * 设置答题卡布局
     */
    private void showAnswerCardLayout() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        mPopAnswer = new CommonPopupWindow(this, R.layout.pop_case_item_answer, ViewGroup.LayoutParams.MATCH_PARENT, (int) (screenHeight * 0.7)) {
            private LinearLayout mLlGmCardLayout;
            private RecyclerView mRlvCasenewText;
            private Button mBtnPopAnswerSumbit;
            private TextView mTvTextPopRegression;
            private TextView mTvTextPopError;
            private TextView mTvTextPopRight;
            private TextView mTvPopCount;
            private TextView mTvLine;
            private TextView mTvPopNew;

            @Override
            protected void initView() {
                View view = getContentView();
                mLlGmCardLayout = (LinearLayout) view.findViewById(R.id.ll_gm_card_layout);
                mRlvCasenewText = (RecyclerView) view.findViewById(R.id.rlv_casenew_text);
                mBtnPopAnswerSumbit = (Button) view.findViewById(R.id.btn_pop_answer_sumbit);
                mBtnPopAnswerSumbit.setVisibility(View.VISIBLE);
                mTvTextPopRegression = (TextView) view.findViewById(R.id.tv_text_pop_regression);
                mTvTextPopError = view.findViewById(R.id.tv_text_pop_error);
                mTvTextPopRight = view.findViewById(R.id.tv_text_pop_right);
                mTvPopCount = view.findViewById(R.id.tv_pop_count);
                mTvLine = view.findViewById(R.id.tv_line);
                mTvPopNew = view.findViewById(R.id.tv_pop_new);
            }

            @Override
            protected void initEvent() {
                if (mColorManger != null) {
                    mBtnPopAnswerSumbit.setTextColor(mColorManger.getmTextTitleColor());
                    mTvPopNew.setTextColor(mColorManger.getmTextTitleColor());
                    mTvPopCount.setTextColor(mColorManger.getmTextTitleColor());
                    mTvTextPopRight.setTextColor(mColorManger.getmTextFuColor());
                    mTvTextPopRegression.setTextColor(mColorManger.getmTextFuColor());
                    mTvLine.setTextColor(mColorManger.getmTextTitleColor());
                    mLlGmCardLayout.setBackgroundColor(mColorManger.getmLayoutBgColor());
                    mBtnPopAnswerSumbit.setBackgroundResource(mColorManger.getmSubmitColorBg());
                }
                mTvPopNew.setText(String.valueOf(curPosition1 + 1));
                mTvPopCount.setText(String.valueOf(mMainDataLists.size()));
                final List<DoBankSqliteVo> datas = findAllDoDatas(mMainDataLists);
                mBtnPopAnswerSumbit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //判断用户是否做完
                        doUserSumbitDialog();
                        //暂停时间
                        setPauseRestartImg(true, R.mipmap.qbank_answer_icon_cont);
                    }
                });
                bindGridViewAdapter(datas);
            }

            @Override
            protected void initWindow() {
                super.initWindow();
                PopupWindow instance = getPopupWindow();
                instance.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mTextUtil.setBackgroundAlpha(1f, CaseExamtActivity.this);
                    }
                });
            }

            private void bindGridViewAdapter(List<DoBankSqliteVo> datas) {
                setGridLayoutManger(mContext, mRlvCasenewText, 1);
                List<CaseCardVo> list = null;
                if (mEvalueteAble) {//是否继续
                    list = getGalleryList();
                } else {
                    list = getLists();
                }
                CaseAnswerAdapter adapter = new CaseAnswerAdapter(mContext, list);
                mRlvCasenewText.setAdapter(adapter);
                adapter.doChangerColor(mColorManger);
                adapter.doUserData(datas);
                adapter.setOnItemClickListener(new CaseAnswerAdapter.OnItemClickListener() {
                    @Override
                    public void onClickItem(int parentpostion, int childpostion) {
                        curPosition1 = parentpostion;
                        doChildEvent(parentpostion, childpostion);
                        doEventHine(true);
                        mPopAnswer.getPopupWindow().dismiss();
                    }
                });
            }
        };
        mPopAnswer.showAtLocation(mCaseExamLayout, Gravity.BOTTOM, 0, 0);
        mTextUtil.setBackgroundAlpha(0.5f, CaseExamtActivity.this);
    }

    //结果答题卡
    private void showResultPopLayout() {
        mPopResult = new CommonPopupWindow(this, R.layout.pop_case_result, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) {
            private LinearLayout mLlGmmockexamdialogLayout;
            private LinearLayout mLlMockexamBar;
            private Button mBtnGmCaseMockexanJiexi;
            private Button mBtnGmCaseMockexanAgain;
            private RecyclerView mRlvGmCaseCasenewText;
            private TextView mTvGmCaseMockexamRightContent;
            private TextView mTvGmCaseMockexamRightTitle;
            private TextView mTvGmCaseMockexamDotimeTitleContent;
            private TextView mTvGmCaseMockexamDotimeTitle;
            private TextView mTvGmCaseResultNumber;
            private ImageView mIvGmCaseExamBack;

            @Override
            protected void initView() {
                View view = getContentView();
                mLlGmmockexamdialogLayout = (LinearLayout) view.findViewById(R.id.ll_gm_mockexamdialog_layout);
                mLlMockexamBar = (LinearLayout) view.findViewById(R.id.ll_mockexam_bar);
                mBtnGmCaseMockexanAgain = (Button) view.findViewById(R.id.btn_gm_case_mockexan_again);
                mBtnGmCaseMockexanJiexi = (Button) view.findViewById(R.id.btn_gm_case_mockexan_jiexi);
                mRlvGmCaseCasenewText = (RecyclerView) view.findViewById(R.id.rlv_gm_case_casenew_text);
                mTvGmCaseMockexamRightContent = (TextView) view.findViewById(R.id.tv_gm_case_mockexam_right_content);
                mTvGmCaseMockexamRightTitle = (TextView) view.findViewById(R.id.tv_gm_case_mockexam_right_title);
                mTvGmCaseMockexamDotimeTitle = (TextView) view.findViewById(R.id.tv_gm_case_mockexam_dotime_title);
                mTvGmCaseMockexamDotimeTitleContent = (TextView) view.findViewById(R.id.tv_gm_case_mockexam_dotime_title_content);
                mTvGmCaseResultNumber = (TextView) view.findViewById(R.id.tv_gm_case_result_number);
                mIvGmCaseExamBack = (ImageView) view.findViewById(R.id.iv_gm_case_exam_back);

            }

            @Override
            protected void initEvent() {
                mIvGmCaseExamBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().postSticky(new GmChangerColorEvent(0, 3, false));
                        mPopResult.getPopupWindow().dismiss();
                    }
                });
                List<DoBankSqliteVo> vos = mDoBankHelp.finDAllUserDoText();
                bindViewData();
                setBtnListener(mBtnGmCaseMockexanAgain, 1);
                setBtnListener(mBtnGmCaseMockexanJiexi, 2);
                bindGridViewAdapter();
                bindData(vos);
            }

            private void bindData(List<DoBankSqliteVo> vos) {
                cancelTime();
                String grade = mTextUtil.getUserGrade(vos);
                mTvGmCaseResultNumber.setText(grade);

            }

            private void bindViewData() {
                mTvGmCaseMockexamDotimeTitleContent.setText(mUserDoTime);
            }

            @Override
            protected void initWindow() {
                super.initWindow();
                PopupWindow instance = getPopupWindow();
                instance.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mTextUtil.setBackgroundAlpha(1f, CaseExamtActivity.this);
                    }
                });
            }

            private void bindGridViewAdapter() {
                setGridLayoutManger(mContext, mRlvGmCaseCasenewText, 1);
                CaseExamAdapter adapter = new CaseExamAdapter(mContext, getLists());
                mRlvGmCaseCasenewText.setAdapter(adapter);
                adapter.doChangerColor(mColorManger);
                adapter.doUserData(findAllDoDatas(mMainDataLists));
                adapter.setOnItemClickListener(new CaseExamAdapter.OnItemClickListener() {
                    @Override
                    public void onClickItem(int parentpostion, int childpostion) {
                        curPosition1 = parentpostion;
                        doChildEvent(parentpostion, childpostion);
                        doEventHine(true);
                        mPopResult.getPopupWindow().dismiss();
                    }
                });
            }
        };
        mPopResult.showAtLocation(mCaseExamLayout, Gravity.BOTTOM, 0, 0);
        mTextUtil.setBackgroundAlpha(0.5f, CaseExamtActivity.this);
    }

    /**
     * 设置监听
     *
     * @param button
     * @param number 1为重新，2 为解析
     */
    private void setBtnListener(Button button, final int number) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopResult != null && mPopResult.getPopupWindow().isShowing()) {
                    mPopResult.getPopupWindow().dismiss();
                }
                switch (number) {
                    case 1://重新
                        mSubmit = false;
                        //图标
                        setPauseRestartImg(true, R.mipmap.qbank_answer_icon_pau);
                        //重置时间
                        restartTime();
                        //删除做题记录
                        mDoBankHelp.delelteTable();
                        //主副布局
                        doMainFuView(true, false);
                        //主副bar
                        doEventHine(false);
                        mEvalueteAble = false;
                        curPosition2 = 0;
                        curPosition1 = 0;
                        mMainDataLists = getLists();
                        initReadViewPagerOne();
                        initData(0);
                        //跳转
                        mReaderViewPager.setCurrentItem(0);
                        EventBus.getDefault().postSticky(new GmChangerColorEvent(0, 3, false));
                        break;
                    case 2://查看解析
                        mSubmit = true;
                        curPosition2 = 0;
                        curPosition1 = 0;
                        mMainDataLists = getLists();
                        initReadViewPagerOne();
                        initData(0);
                        //主副布局
                        doMainFuView(true, false);
                        //主副bar
                        doEventHine(false);
                        mReaderViewPager.setCurrentItem(0);
                        initfuPostion();
                        EventBus.getDefault().postSticky(new GmChangerColorEvent(0, 3, false));
                        break;

                }
            }
        });
    }

    /**
     * 交卷对话框
     */
    public void doUserSumbitDialog() {
        if (mPopAnswer != null && mPopAnswer.getPopupWindow().isShowing()) {
            mPopAnswer.getPopupWindow().dismiss();
        }
        if (mEvalueteAble) {
            mDialogUtil.showTitleDialog(mContext,
                    getStringWithId(R.string.has_submit_exam),
                    getStringWithId(R.string.submit_text),
                    getStringWithId(R.string.cancel), true);
            mDialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
                @Override
                public void onSureClickListener() {//交卷
                    mSubmit = true;
                    pauseTime();
                    //获取用户使用时间
                    long time = getUserTime();
                    doSubmitExam(time);
                    //取消时间
                    cancelTime();
                    mEvalueteAble = false;
                    mSubmit = true;
                    setPauseRestartImg(true, R.mipmap.qbank_answer_icon_pau);
                    //时间布局
                    setLinearBar(false);
                    showResultPopLayout();
                }

                @Override
                public void onCancelClickListener() {//取消

                }
            });
            return;
        }
        DialogUtil.showDialogTitleContent(mContext, getStringWithId(R.string.self_evaluation_One),
                getStringWithId(R.string.self_evaluation_One_contetn), getStringWithId(R.string.grade),
                getStringWithId(R.string.skip), true, new DialogUtil.onTitleClickListener() {
                    @Override
                    public void onSureClickListener() {
                        if (mEvalueteAble) return;
                        mSubmit = false;
                        mEvalueteAble = true;
                        doContinueEvent();
                        pauseTime();
                        initfuPostion();
                        EventBus.getDefault().postSticky(new GmChangerColorEvent(0, 3, false));

                    }

                    @Override
                    public void onCancelClickListener() {
                        //获取用户使用时间
                        long time = getUserTime();
                        doSubmitExam(time);
                        //取消时间
                        cancelTime();
                        mEvalueteAble = false;
                        mSubmit = true;
                        setPauseRestartImg(true, R.mipmap.qbank_answer_icon_pau);
                        //时间布局
                        setLinearBar(false);
                        showResultPopLayout();
                    }
                });

    }

    /**
     * 执行继续事件
     */
    private void doContinueEvent() {
        mMainDataLists = getGalleryList();
        if (mMainDataLists == null || mMainDataLists.isEmpty()) {
            T.showToast(mContext, "暂无自评题目,请交卷");
            return;
        }
        //初始化翻页效果
        initReadViewPagerOne();
        //显示下表
        initData(0);
        mOneAdapter.notifyDataSetChanged();

    }
/*    private void showSelfEvaluation() {
        mDialogUtil.showTitleDialog(mContext, "自我评价", "主观题目评分后计入总分", "评分", "跳过", true);
        mDialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
            @Override
            public void onSureClickListener() {

            }

            @Override
            public void onCancelClickListener() {//跳过直接显示答题卡界面
                showResultPopLayout();
            }
        });

    }*/

    private void setLinearBar(boolean clickable) {
        mLlTextBarTitle.setClickable(clickable);
    }

    private void doSubmitExam(long time) {
        List<DoBankSqliteVo> datas = findAllDoDatas(getLists());
        if (datas == null || datas.isEmpty()) return;
        //分说
        String grade = mTextUtil.getUserGrade(datas);
        if (!doOpenNewWork()) {
            int timeLog = (int) new Date().getTime();
            KaoShiSqliteVo kaoShiSqliteVo = new KaoShiSqliteVo();
            UserInfomSqliteVo infomVo = mInfomdbhelp.findUserInfomVo();
            if (infomVo != null)
                kaoShiSqliteVo.setSaffid(String.valueOf(infomVo.getSaffid()));
            kaoShiSqliteVo.setTimekey(timeLog);
            kaoShiSqliteVo.setUsertime((int) time);
            kaoShiSqliteVo.setSocre(Double.valueOf(grade));
            kaoShiSqliteVo.setChapter_id(Integer.valueOf(mChapter_id));
            mKaoShiSqliteHelp.addExamLogItem(kaoShiSqliteVo);
            if (datas == null || datas.isEmpty()) return;
            for (int i = 0; i < datas.size(); i++) {
                DoBankSqliteVo vo = datas.get(i);
                vo.setMockkeyid(timeLog);
                mDoKaoShiSqlteHelp.addDoLogItem(vo);
            }
            return;
        }


        List<SubmiteExamVo> doLog = mTextUtil.getUserDoLog(datas);
        long usertime = time / 1000;
        int useTime = (int) usertime;
        Date date = new Date();
        String finishTime = TimeUtil.dateToStringOne(date);
        mPresenter.submitExam(mContext, grade, mQuesiton_id, useTime, finishTime, doLog);
    }

    private boolean doOpenNewWork() {
        return PolyvSDKUtil.isOpenNetwork(mContext);
    }

    public long getUserTime() {
        if (mTimeUitl != null) {
            long time = mTimeUitl.getNubmer();
            if (time != 0) {
                mUserDoTime = TimeTools.getCountTimeByLong(time);
            }
            return time;
        }
        return 0;
    }

    //找到用户做题数据
    private List<DoBankSqliteVo> findAllDoDatas(List<CaseCardVo> mMainDataLists) {
        List<DoBankSqliteVo> list = new ArrayList<>();
        //查询用户数据
        List<DoBankSqliteVo> doBankSqliteVos = mDoBankHelp.finDAllUserDoText();
        if (doBankSqliteVos == null || doBankSqliteVos.isEmpty()) return list;
        int size = mMainDataLists.size();
        for (int i = 0; i < size; i++) {
            CaseCardVo caseCardVo = mMainDataLists.get(i);
            if (caseCardVo.getList() != null && !caseCardVo.getList().isEmpty()) {
                List<CaseCardVo> lists = caseCardVo.getList();
                for (int k = 0; k < lists.size(); k++) {
                    CaseCardVo bean = lists.get(k);
                    QuestionCaseVo vo = bean.getCasevo();
                    for (int l = 0; l < doBankSqliteVos.size(); l++) {
                        DoBankSqliteVo sqliteVo = doBankSqliteVos.get(l);
                        if (vo.getQuestion_id() == sqliteVo.getQuestion_id()) {
                            list.add(sqliteVo);
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 时间
     */
    @Override
    public void TimeFinish() {
        pauseTime();
        doUserSumbitDialog();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mSubmit) return;
        if (mTimeUitl != null && !mEvalueteAble) {
            if (mStopDialog == null || !mStopDialog.isShowing()) {
                mStopDialog = mDialogUtil.showStopDialog(mContext);
                setStopDialogListener(mDialogUtil);
                setPauseRestartImg(true, R.mipmap.qbank_answer_icon_cont);
            }
        }
    }

    //提交试卷
    @Override
    public void submiteSuccess(String success) {
        L.e(TAG, "submiteSuccess: " + success);
    }

    @Override
    public void submiteErrror(String msg) {
        L.e(TAG, "submiteErrror: " + msg);
    }

    public class GmFragmentAdpater extends FragmentStatePagerAdapter {

        private final String mCourseid;
        private final FragmentManager fm;
        private Context mContext;

        public GmFragmentAdpater(FragmentManager fm, Context mContext, String coursid) {
            super(fm);
            this.fm = fm;
            this.mContext = mContext;
            this.mCourseid = coursid;
        }

        @Override
        public Fragment getItem(int position) {
            if (mMainDataLists.size() == 0) {
                mReadFragment = GmReadThreeFragment.newInstance(null, position, mCourseid);
            } else {
                mCaseCardVo = (CaseCardVo) mMainDataLists.get(position);
                mQuesiton_id = mCaseCardVo.getCasevo().getQuestion_id();
                mReadFragment = GmReadThreeFragment.newInstance(mCaseCardVo, position, mCourseid);
            }
            return mReadFragment;
        }

        @Override
        public int getCount() {
            if (mMainDataLists.size() == 0) {
                return 1;
            } else
                return mMainDataLists.size();
        }

    }

    public class GmFragmentAdpaterTwo extends FragmentStatePagerAdapter {

        private final String mCourseid;
        private final FragmentManager fm;
        private Context mContext;


        public GmFragmentAdpaterTwo(FragmentManager fm, Context mContext, String coursid) {
            super(fm);
            this.fm = fm;
            this.mContext = mContext;
            this.mCourseid = coursid;
        }


        @Override
        public Fragment getItem(int position) {
            if (mFuDataLists.size() == 0) {
                mReadFragmentTwo = GmReadThreeFragment.newInstance(null, position, mCourseid);
            } else {
                CaseCardVo mCaseCardVoTwo = (CaseCardVo) mFuDataLists.get(position);
                mReadFragmentTwo = GmReadThreeFragment.newInstance(mCaseCardVoTwo, position, mCourseid);
            }
            return mReadFragmentTwo;
        }

        @Override
        public int getCount() {
            if (mFuDataLists.size() == 0) {
                return 1;
            } else
                return mFuDataLists.size();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDoBankHelp != null) {
            mDoBankHelp.delelteTable();
        }
        cancelTime();
        mTimeUitl = null;

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_UP) {
            showBackFinishDialog();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}

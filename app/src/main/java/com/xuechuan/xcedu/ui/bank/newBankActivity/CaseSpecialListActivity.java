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
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
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
import android.widget.Toast;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.bank.CaseAnswerAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.event.GmChangerColorEvent;
import com.xuechuan.xcedu.fragment.GmReadTwoFragment;
import com.xuechuan.xcedu.fragment.view.CaseInterface;
import com.xuechuan.xcedu.sqlitedb.CollectSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.DoBankSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.DoLogProgressSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.DoMockBankSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.DoUpLogProgressSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.ErrorSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.QuestionSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.QuestionTagreLationSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.UpCollectSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.UpDeleteCollectSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.UpDeleteErrorSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.UpDoBankSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.UpErrorSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.UserInfomDbHelp;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.EncryptionUtil;
import com.xuechuan.xcedu.utils.GmReadColorManger;
import com.xuechuan.xcedu.utils.GmTextUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.SaveDataListUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.CaseCardVo;
import com.xuechuan.xcedu.vo.QuestionCaseVo;
import com.xuechuan.xcedu.vo.SqliteVo.CollectSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.DoLogProgreeSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.ErrorSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuesitonExamRaltionSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionTagRelationSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.UserInfomSqliteVo;
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
 * @Description: 专项练习
 * @author: L-BackPacker
 * @date: 2018.12.21 下午 4:06
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.12.21
 */
public class CaseSpecialListActivity extends BaseActivity implements View.OnClickListener, CaseInterface {
    private static String PARAMT_KEY = "com.xuechuan.xcedu.ui.liveexamactivity.courserid";
    /**
     * 题干id
     */
    private static String PARAMT_TAGID = "com.xuechuan.xcedu.ui.liveexamactivity.tagid";
    private String mCourseId;
    private Context mContext;
    private GmTextUtil mTextUtil;
    private DoBankSqliteHelp mDoBankHelp;
    private DoLogProgressSqliteHelp mDoLogProgressSqliteHelp;
    //第一个数据
    private List<CaseCardVo> mMainDataLists;
    //第二个数据
    private List<CaseCardVo> mFuDataLists;
    private GmReadTwoFragment mReadFragment;
    private GmReadColorManger mColorManger;
    private QuestionSqliteHelp mQuestionSqliteHelp;
    private CommonPopupWindow mPopAnswer;
    private DoMockBankSqliteHelp mDoMockBankSqliteHelp;
    private UserInfomDbHelp mInfomdbhelp;
    private UpDoBankSqlteHelp mUpDoBankSqlteHelp;
    private ErrorSqlteHelp mErrorSqlteHelp;
    private DialogUtil mDialogUtil;

    private boolean mainquestion = true;
    private GmReadTwoFragment mReadFragmentTwo;
    private GmFragmentAdpaterTwo mTwoAdapter;
    private int mQuesiton_id = -1;
    private GmFragmentAdpater mOneAdapter;
    private SaveDataListUtil mDataListUtil;
    private String mTagId;
    private QuestionTagreLationSqliteHelp mTagRelationHelp;
    private CaseCardVo mCaseCardVo;
    private CollectSqliteHelp mCollectSqliteHelp;
    private UpErrorSqlteHelp mUpErrorSqlteHelp;
    private UpCollectSqlteHelp mUpCollectSqlteHelp;
    private DoUpLogProgressSqliteHelp mUpLogProgressSqliteHelp;
    private VideoUitls mVideoUitls;
    private UpDeleteErrorSqlteHelp mUpDeleteErrorSqlteHelp;
    private UpDeleteCollectSqlteHelp mUpDeleteCollectSqlteHelp;
    private ImageView mIvTextBarBack;
    private ImageView mIvTextBarTimeImg;
    private TextView mActivityTitleText;
    private LinearLayout mLlTextBarTitle;
    private ImageView mIvTextBarDelect;
    private ImageView mIvTextBarMore;
    private TextView mTvCaseExamSubmit;
    private LinearLayout mLlTextTitleBar;
    private View mVGmReadLine;
    private ReaderViewPager mReaderViewPager;
    private ReaderViewPager mReaderViewPagertwo;
    private ImageView mShadowView;
    private FrameLayout mFlContentLayoutOne;
    private TextView mTvEmpty;
    private View mVGmBarLine;
    private ImageView mIvGmbanJianpan;
    private EditText mEtGmSubmit;
    private ImageView mIvGmSubmitSend;
    private LinearLayout mLlGmSubmitEvalue;
    private CheckBox mChbGmCollect;
    private TextView mTvTextCollect;
    private ImageView mIvTextMenu;
    private TextView mTvTextQid;
    private TextView mTvTextAllqid;
    private TextView mTvTextShare;
    private LinearLayout mLiTextNavbar;
    private LinearLayout mLlNewtextBar;
    private LinearLayout mCasespecidRootLayout;

    /**
     * @param context
     * @param courseid 科目
     */
    public static Intent start_Intent(Context context, String courseid, String tagid) {
        Intent intent = new Intent(context, CaseSpecialListActivity.class);
        intent.putExtra(PARAMT_KEY, courseid);
        intent.putExtra(PARAMT_TAGID, tagid);
        return intent;
    }


/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_special_test);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_case_special_test);
        if (getIntent() != null) {
            mCourseId = getIntent().getStringExtra(PARAMT_KEY);
            //题目对应tagid
            mTagId = getIntent().getStringExtra(PARAMT_TAGID);
        }
        initView();
        initUtils();
        doNextEvent(getLists());
    }

    private void doNextEvent(List<CaseCardVo> list) {
        mMainDataLists = list;
        if (mMainDataLists == null || mMainDataLists.isEmpty()) {
            mFlContentLayoutOne.setVisibility(View.GONE);
            mTvEmpty.setVisibility(View.VISIBLE);
            return;
        } else {
            mTvEmpty.setVisibility(View.GONE);
            mFlContentLayoutOne.setVisibility(View.VISIBLE);
        }
        mTvTextShare.setVisibility(View.INVISIBLE);
        //初始化翻页效果
        initReadViewPagerOne();
        //显示下表
        initData(0);

        //显示对话框
        doShowDialogEvent();
        initEvent();
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
                        mUpDeleteCollectSqlteHelp.deleteItem(Integer.parseInt(mCourseId),
                                mCaseCardVo.getCasevo().getChapter_id(), mCaseCardVo.getCasevo().getQuestion_id());
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
        //上传做题表
        mUpErrorSqlteHelp = UpErrorSqlteHelp.getInstance(mContext);
        //上传收藏表
        mUpCollectSqlteHelp = UpCollectSqlteHelp.getInstance(mContext);
        //上传进度表
        mUpLogProgressSqliteHelp = DoUpLogProgressSqliteHelp.get_Instance(mContext);

        //上传删除错题表
        mUpDeleteErrorSqlteHelp = UpDeleteErrorSqlteHelp.getInstance(mContext);
        //上传错误收藏表
        mUpDeleteCollectSqlteHelp = UpDeleteCollectSqlteHelp.getInstance(mContext);

        mVideoUitls = VideoUitls.get_singleton(mContext);

        mDataListUtil = SaveDataListUtil.getInstance();
        mTagRelationHelp = QuestionTagreLationSqliteHelp.get_Instance(mContext);
    }

    private int number = 0;
    List<QuestionCaseVo> mainlist;
    public List<CaseCardVo> getLists() {

        List<QuestionTagRelationSqliteVo> listTags = mTagRelationHelp.queryTagRalationWithTag(mTagId);
        List<QuestionCaseVo> mParentList = mQuestionSqliteHelp.queryQuestionVoWithTagOneCase(listTags);
        List<QuestionCaseVo> mChildList = mQuestionSqliteHelp.queryQuestionChildListVos(mParentList);
        List<CaseCardVo> mCardLists = mTextUtil.getDataListCaseEventFour(mParentList,mChildList);
        return mCardLists;
    }

    private void initData(int index) {
        mTvTextAllqid.setText(String.valueOf(mMainDataLists.size()));
        mTvTextQid.setText(String.valueOf(++index));
    }


    /**
     * 显示继续答题对话框
     */
    private void doShowDialogEvent() {
        if (mDoLogProgressSqliteHelp != null) {
            if (mMainDataLists != null && !mMainDataLists.isEmpty()) {
                final DoLogProgreeSqliteVo look = mDoLogProgressSqliteHelp.findLookWithTidChapterId(
                        Integer.parseInt(mTagId), Integer.parseInt(mCourseId), DataMessageVo.SPECIAL_TWO);
                if (look == null) return;
                mDialogUtil.showContinueDialog(mContext, look.getNumber());
                mDialogUtil.setContinueClickListener(new DialogUtil.onContincueClickListener() {
                    @Override
                    public void onCancelClickListener() {
                        if (mReaderViewPager != null) {
                            mReaderViewPager.setCurrentItem(0, true);
                            mDoLogProgressSqliteHelp.deleteLookItem(look.getId());
                        }
                    }

                    @Override
                    public void onNextClickListener() {
                        if (mReaderViewPager != null)
                            mReaderViewPager.setCurrentItem(Integer.parseInt(look.getNumber()) - 1, true);
                    }
                });
            }
        }

    }

    private int prePosition1 = 0;
    private int curPosition1;

    private void initReadViewPagerOne() {
        mOneAdapter = new GmFragmentAdpater(getSupportFragmentManager(), mContext, mCourseId);
        mReaderViewPager.setAdapter(mOneAdapter);
        mReaderViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mShadowView.setTranslationX(mReaderViewPager.getWidth() - positionOffsetPixels);
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
        mReaderViewPager.setOffscreenPageLimit(3);
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
        mReaderViewPager.setVisibility(main ? View.VISIBLE : View.GONE);
        mReaderViewPagertwo.setVisibility(fu ? View.VISIBLE : View.GONE);
    }


    private void initView() {
        mContext = this;

        mIvTextBarBack = (ImageView) findViewById(R.id.iv_text_bar_back);
        mIvTextBarBack.setOnClickListener(this);
        mIvTextBarTimeImg = (ImageView) findViewById(R.id.iv_text_bar_time_img);
        mIvTextBarTimeImg.setOnClickListener(this);
        mActivityTitleText = (TextView) findViewById(R.id.activity_title_text);
        mActivityTitleText.setOnClickListener(this);
        mLlTextBarTitle = (LinearLayout) findViewById(R.id.ll_text_bar_title);
        mLlTextBarTitle.setOnClickListener(this);
        mIvTextBarDelect = (ImageView) findViewById(R.id.iv_text_bar_delect);
        mIvTextBarDelect.setOnClickListener(this);
        mIvTextBarMore = (ImageView) findViewById(R.id.iv_text_bar_more);
        mIvTextBarMore.setOnClickListener(this);
        mTvCaseExamSubmit = (TextView) findViewById(R.id.tv_case_exam_submit);
        mTvCaseExamSubmit.setOnClickListener(this);
        mLlTextTitleBar = (LinearLayout) findViewById(R.id.ll_text_title_bar);
        mLlTextTitleBar.setOnClickListener(this);
        mVGmReadLine = (View) findViewById(R.id.v_gm_read_line);
        mVGmReadLine.setOnClickListener(this);
        mReaderViewPager = (ReaderViewPager) findViewById(R.id.readerViewPager);
        mReaderViewPager.setOnClickListener(this);
        mReaderViewPagertwo = (ReaderViewPager) findViewById(R.id.readerViewPagertwo);
        mReaderViewPagertwo.setOnClickListener(this);
        mShadowView = (ImageView) findViewById(R.id.shadowView);
        mShadowView.setOnClickListener(this);
        mFlContentLayoutOne = (FrameLayout) findViewById(R.id.fl_content_layout_one);
        mFlContentLayoutOne.setOnClickListener(this);
        mTvEmpty = (TextView) findViewById(R.id.tv_empty);
        mTvEmpty.setOnClickListener(this);
        mVGmBarLine = (View) findViewById(R.id.v_gm_bar_line);
        mVGmBarLine.setOnClickListener(this);
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
        mTvTextCollect = (TextView) findViewById(R.id.tv_text_collect);
        mTvTextCollect.setOnClickListener(this);
        mIvTextMenu = (ImageView) findViewById(R.id.iv_text_menu);
        mIvTextMenu.setOnClickListener(this);
        mTvTextQid = (TextView) findViewById(R.id.tv_text_qid);
        mTvTextQid.setOnClickListener(this);
        mTvTextAllqid = (TextView) findViewById(R.id.tv_text_allqid);
        mTvTextAllqid.setOnClickListener(this);
        mTvTextShare = (TextView) findViewById(R.id.tv_text_share);
        mTvTextShare.setOnClickListener(this);
        mLiTextNavbar = (LinearLayout) findViewById(R.id.li_text_navbar);
        mLiTextNavbar.setOnClickListener(this);
        mLlNewtextBar = (LinearLayout) findViewById(R.id.ll_newtext_bar);
        mLlNewtextBar.setOnClickListener(this);
        mCasespecidRootLayout = (LinearLayout) findViewById(R.id.casespecid_root_layout);
        mCasespecidRootLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_text_menu://菜单
                showAnswerCardLayout();
                break;
            case R.id.iv_text_bar_more://更多
                if (mReadFragment != null) {
                    mReadFragment.showGmSetting();
                }
                break;
            case R.id.iv_text_bar_back://返回
                this.finish();
                break;
            case R.id.iv_gmTitle_mulu://目录
                doMainFuView(true, false);
                break;
            case R.id.tv_text_share:
                EventBus.getDefault().postSticky(new GmChangerColorEvent(0, 4, false));
                break;

        }
    }

    @Override
    public void saveUserDoLog(DoBankSqliteVo vo) {
        if (vo == null) return;
        mDoBankHelp.addDoBankItem(vo);
        mDoMockBankSqliteHelp.addDoBankItem(vo);
        mUpDoBankSqlteHelp.addDoBankItem(vo);

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
    public void doRightGo() {
        if (mFuDataLists == null) return;
        if (mReaderViewPagertwo == null) return;
        int currentItem = mReaderViewPagertwo.getCurrentItem();
        if (currentItem == mFuDataLists.size() - 1) {//是否事最后一题
            changerFuView();
        } else {
            currentItem += 1;
            curPosition2 = currentItem;
            prePosition2 = currentItem;
            mReaderViewPagertwo.setCurrentItem(curPosition2, true);
        }

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
        mUpDeleteErrorSqlteHelp.deleteErrorItem(vo);
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
        CaseCardVo vo = getLists().get(postion);
//        CaseCardVo vo = mMainDataLists.get(postion);
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
   /*     String d = EncryptionUtil.D(vo.getQuestion());
        vo.setQuestionStr(d);
        String keyword = EncryptionUtil.D(vo.getKeywords());
        vo.setKeywordStr(keyword);
        String explain = EncryptionUtil.D(vo.getExplained());
        vo.setExplainedStr(explain);*/
        return vo;
    }

    @Override
    public void changerCllect() {
        CaseCardVo vo = mMainDataLists.get(prePosition1);
        initCollect(vo.getCasevo());
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
    private static String TAG = "【" + CaseSpecialListActivity.class + "】==";

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
        mReaderViewPagertwo.setOffscreenPageLimit(3);

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
                    mTvPopNew.setTextColor(mColorManger.getmTextTitleColor());
                    mTvPopCount.setTextColor(mColorManger.getmTextTitleColor());
                    mTvTextPopRight.setTextColor(mColorManger.getmTextFuColor());
                    mTvTextPopRegression.setTextColor(mColorManger.getmTextFuColor());
                    mTvLine.setTextColor(mColorManger.getmTextTitleColor());
                    mLlGmCardLayout.setBackgroundColor(mColorManger.getmLayoutBgColor());
                }
                mTvPopNew.setText(String.valueOf(curPosition1 + 1));
                mTvPopCount.setText(String.valueOf(mMainDataLists.size()));
                bindGridViewAdapter();

            }

            @Override
            protected void initWindow() {
                super.initWindow();
                PopupWindow instance = getPopupWindow();
                instance.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mTextUtil.setBackgroundAlpha(1f, CaseSpecialListActivity.this);
                    }
                });
            }

            private void bindGridViewAdapter() {
                setGridLayoutManger(mContext, mRlvCasenewText, 1);
                CaseAnswerAdapter adapter = new CaseAnswerAdapter(mContext, getLists());
//                CaseAnswerAdapter adapter = new CaseAnswerAdapter(mContext, mMainDataLists);
                mRlvCasenewText.setAdapter(adapter);
                adapter.doChangerColor(mColorManger);
                adapter.doUserData(findAllDoDatas());
                adapter.setOnItemClickListener(new CaseAnswerAdapter.OnItemClickListener() {
                    @Override
                    public void onClickItem(int parentpostion, int childpostion) {
                        curPosition1 = parentpostion;
                        doChildEvent(parentpostion, childpostion);
                        mPopAnswer.getPopupWindow().dismiss();
                        doEventHine(true);
                    }
                });
            }
        };
        mPopAnswer.showAtLocation(mCasespecidRootLayout, Gravity.BOTTOM, 0, 0);
        mTextUtil.setBackgroundAlpha(0.5f, CaseSpecialListActivity.this);
    }

    //找到用户做题数据
    private List<DoBankSqliteVo> findAllDoDatas() {
        List<DoBankSqliteVo> list = new ArrayList<>();
        //查询用户数据
        List<DoBankSqliteVo> doBankSqliteVos = mDoBankHelp.finDAllUserDoText();
        if (doBankSqliteVos == null || doBankSqliteVos.isEmpty()) return list;
        for (int i = 0; i < mMainDataLists.size(); i++) {
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
                mReadFragment = GmReadTwoFragment.newInstance(null, position, mCourseid);
            } else {
                mCaseCardVo = (CaseCardVo) mMainDataLists.get(position);
                mQuesiton_id = mCaseCardVo.getCasevo().getQuestion_id();
                mReadFragment = GmReadTwoFragment.newInstance(mCaseCardVo, position, mCourseid);
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
                mReadFragmentTwo = GmReadTwoFragment.newInstance(null, position, mCourseid);
            } else {
                CaseCardVo mCaseCardVoTwo = (CaseCardVo) mFuDataLists.get(position);
                mReadFragmentTwo = GmReadTwoFragment.newInstance(mCaseCardVoTwo, position, mCourseid);
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
//        doDoText();

    }

    @Override
    protected void onPause() {
        super.onPause();
        doDoText();
    }

    private void doDoText() {
        if (curPosition1 == 0) return;
        if (mQuesiton_id != -1) {
            UserInfomDbHelp help = UserInfomDbHelp.get_Instance(mContext);
            UserInfomSqliteVo vo = help.findUserInfomVo();
            DoLogProgreeSqliteVo sqliteVo = new DoLogProgreeSqliteVo();
            sqliteVo.setChapterid(Integer.parseInt(mTagId));
            sqliteVo.setKid(Integer.parseInt(mCourseId));
            sqliteVo.setTextid(mQuesiton_id);
            sqliteVo.setNumber(String.valueOf(prePosition1 + 1));
            sqliteVo.setUserid(String.valueOf(vo.getSaffid()));
            sqliteVo.setAllNumber(String.valueOf(mMainDataLists.size()));
            sqliteVo.setType(DataMessageVo.SPECIAL_TWO);
            mDoLogProgressSqliteHelp.addDoLookItem(sqliteVo);
            mUpLogProgressSqliteHelp.addDoLookItem(sqliteVo);
        }
    }
}

package com.xuechuan.xcedu.ui.bank.newBankActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
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

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.bank.CaseAnswerAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.event.GmChangerColorEvent;
import com.xuechuan.xcedu.fragment.GmReadSeachFragment;
import com.xuechuan.xcedu.fragment.GmReadTwoFragment;
import com.xuechuan.xcedu.fragment.view.GmSeachInterface;
import com.xuechuan.xcedu.mvp.contract.GmSubmitComContract;
import com.xuechuan.xcedu.sqlitedb.CollectSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.DoBankSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.DoLogProgressSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.DoMockBankSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.DoUpLogProgressSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.ErrorSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.QuestionSqliteHelp;
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
import com.xuechuan.xcedu.utils.SaveDataListUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.CaseCardVo;
import com.xuechuan.xcedu.vo.QuestionCaseVo;
import com.xuechuan.xcedu.vo.SqliteVo.CollectSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.ErrorSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;
import com.xuechuan.xcedu.weight.CommonPopupWindow;
import com.xuechuan.xcedu.weight.ReaderViewPager;
import com.xuechuan.xcedu.xunfei.basic.VideoUitls;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: SeachResultActivity
 * @Package com.xuechuan.xcedu.ui.bank.newBankActivity
 * @Description: 搜索结果页
 * @author: L-BackPacker
 * @date: 2019.01.18 上午 10:57
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019.01.18
 */

public class SeachResultActivity extends BaseActivity implements GmSeachInterface, View.OnClickListener {
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
    private LinearLayout mSeachresultRootLayout;
    private Context mContext;
    private GmTextUtil mTextUtil;
    private DoBankSqliteHelp mDoBankHelp;
    private DoLogProgressSqliteHelp mDoLogProgressSqliteHelp;
    private DialogUtil mDialogUtil;
    private QuestionSqliteHelp mQuestionSqliteHelp;
    private DoMockBankSqliteHelp mDoMockBankSqliteHelp;
    private UserInfomDbHelp mInfomdbhelp;
    private UpDoBankSqlteHelp mUpDoBankSqlteHelp;
    private ErrorSqlteHelp mErrorSqlteHelp;
    private CollectSqliteHelp mCollectSqliteHelp;
    private UpErrorSqlteHelp mUpErrorSqlteHelp;
    private UpCollectSqlteHelp mUpCollectSqlteHelp;
    private DoUpLogProgressSqliteHelp mUpLogProgressSqliteHelp;
    private UpDeleteErrorSqlteHelp mUpDeleteErrorSqlteHelp;
    private UpDeleteCollectSqlteHelp mUpDeleteCollectSqlteHelp;
    private VideoUitls mVideoUitls;
    private SaveDataListUtil mDataListUtil;
    private Object lists;
    private GmReadSeachFragment mReadFragment;
    private GmFragmentAdpater mOneAdapter;
    private boolean mainquestion = true;
    //第一个数据
    private List<CaseCardVo> mMainDataLists;
    //第二个数据
    private List<CaseCardVo> mFuDataLists;

    private boolean isChild;
    /*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach_result);
        initView();
    }
*/
    private static String PARAMT_KEY = "com.xuechuan.paramt_key";
    private static String PARAMT1_KEY = "com.xuechua.paramt_key1";
    private QuestionSqliteVo mQuestionSqliteVo;
    private CommonPopupWindow mPopAnswer;
    private GmReadColorManger mColorManger;
    private GmReadSeachFragment mReadFragmentTwo;
    private GmFragmentAdpaterTwo mTwoAdapter;

    public static Intent start_Intent(Context context, QuestionSqliteVo vo, String paramt1) {
        Intent intent = new Intent(context, SeachResultActivity.class);
        intent.putExtra(PARAMT_KEY, vo);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_seach_result);
        if (getIntent() != null) {
            mQuestionSqliteVo = (QuestionSqliteVo) getIntent().getSerializableExtra(PARAMT_KEY);
        }
        initView();
        initUtils();
        mMainDataLists = getLists();
        if (mMainDataLists == null || mMainDataLists.isEmpty()) {
            mFlContentLayoutOne.setVisibility(View.GONE);
            mTvEmpty.setVisibility(View.VISIBLE);
            return;
        } else {
            mTvEmpty.setVisibility(View.GONE);
            mFlContentLayoutOne.setVisibility(View.VISIBLE);
        }
        if (isChild) {
            mTvTextShare.setVisibility(View.INVISIBLE);
        } else {
            mTvTextShare.setVisibility(View.VISIBLE);
        }
        //初始化翻页效果
        initReadViewPagerOne();
        //显示下表
        initData(0);
        //初始化事件
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
                    vo.setCourseid(mQuestionSqliteVo.getCourseid());
                    vo.setQuestion_id(mCaseCardVo.getCasevo().getQuestion_id());
                    vo.setQuestiontype(mCaseCardVo.getCasevo().getQuestiontype());
                    vo.setTime(String.valueOf(new Date().getTime()));
                    if (isChecked) {
                        mCollectSqliteHelp.addCoolectItem(vo);
                        mUpCollectSqlteHelp.addCollectSqliteVo(vo);
                        T.showToast(mContext, "收藏成功");
                        mUpDeleteCollectSqlteHelp.deleteItem(mQuestionSqliteVo.getCourseid(),
                                mCaseCardVo.getCasevo().getChapter_id(), mCaseCardVo.getCasevo().getQuestion_id());
                    } else {
                        T.showToast(mContext, "取消收藏");
                        mCollectSqliteHelp.deleteItem(mQuestionSqliteVo.getCourseid(),
                                mCaseCardVo.getCasevo().getChapter_id(), mCaseCardVo.getCasevo().getQuestion_id());
                        mUpCollectSqlteHelp.deleteItem(mQuestionSqliteVo.getCourseid(),
                                mCaseCardVo.getCasevo().getChapter_id(), mCaseCardVo.getCasevo().getQuestion_id());
                    }
                } else {
                    T.showToast(mContext, "暂无题收藏");
                }
            }
        });
    }

    private int prePosition1 = 0;
    private int curPosition1;

    private void initReadViewPagerOne() {
        mOneAdapter = new GmFragmentAdpater(getSupportFragmentManager(), mContext, String.valueOf(mQuestionSqliteVo.getCourseid()));
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

    private int prePosition2 = -1;
    private int curPosition2;

    private void initReadViewPagerTwo() {
        mTwoAdapter = new GmFragmentAdpaterTwo(getSupportFragmentManager(), mContext, String.valueOf(mQuestionSqliteVo.getCourseid()));
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
                            } else if (prePosition2 == mFuDataLists.size() - 1) {//从右到左
                                changerFuView();
                            }
                        }
                        mIsScrolled = true;
                        break;
                }
            }
        });
        mReaderViewPagertwo.setOffscreenPageLimit(3);
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

    /**
     * @param main 主界面
     * @param fu   副界面
     */
    private void doMainFuView(boolean main, boolean fu) {
        mainquestion = main;
        mReaderViewPager.setVisibility(main ? View.VISIBLE : View.GONE);
        mReaderViewPagertwo.setVisibility(fu ? View.VISIBLE : View.GONE);
    }

    //初始子类游标
    private void initfuPostion() {
        curPosition2 = 0;
    }

    private void initUtils() {
        mContext = this;
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

        //初始化数据
        mVideoUitls = VideoUitls.get_singleton(mContext);

        mDataListUtil = SaveDataListUtil.getInstance();
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
            case R.id.tv_text_share:
                EventBus.getDefault().postSticky(new GmChangerColorEvent(prePosition2, 4, false));
                break;

        }
    }

    /**
     * 设置答题卡布局
     */
    private void showAnswerCardLayout() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        mPopAnswer = new CommonPopupWindow(this, R.layout.pop_case_item_answer,
                ViewGroup.LayoutParams.MATCH_PARENT, (int) (screenHeight * 0.7)) {
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
                        mTextUtil.setBackgroundAlpha(1f, SeachResultActivity.this);
                    }
                });
            }

            private void bindGridViewAdapter() {
                setGridLayoutManger(mContext, mRlvCasenewText, 1);
                CaseAnswerAdapter adapter = new CaseAnswerAdapter(mContext, getLists());
                mRlvCasenewText.setAdapter(adapter);
                adapter.doChangerColor(mColorManger);
                adapter.doUserData(findAllDoDatas());
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
        mPopAnswer.showAtLocation(mSeachresultRootLayout, Gravity.BOTTOM, 0, 0);
        mTextUtil.setBackgroundAlpha(0.5f, SeachResultActivity.this);
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

    @Override
    public void doRightGo(int postion) {
        if (mReaderViewPager == null) return;
        int currentItem = mReaderViewPager.getCurrentItem();
        currentItem = currentItem + 1;
        if (currentItem < 0) {
            T.showToast(mContext, "已经是最后一题");
            currentItem = 0;
        }
        mReaderViewPager.setCurrentItem(currentItem, true);
    }

    @Override
    public DoBankSqliteVo queryUserData(int qustion_id) {
        return null;
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

    @Override
    public void changerSmallQuestion(int postion) {
        doChildEvent(postion, curPosition2);
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
        CaseCardVo vo = mMainDataLists.get(prePosition1);
        initCollect(vo.getCasevo());
    }

    private void initCollect(QuestionCaseVo vo) {
        CollectSqliteVo sqliteVo = mCollectSqliteHelp.queryCollectVo(mQuestionSqliteVo.getCourseid(),
                vo.getChapter_id(), vo.getQuestion_id());
        if (sqliteVo != null) {
            mChbGmCollect.setChecked(sqliteVo.getCollectable() == 1);
        } else {
            mChbGmCollect.setChecked(false);
        }
    }

    @Override
    public void videoInput(EditText editText) {
        mVideoUitls.initData(editText);
        mVideoUitls.clickMethod();
    }

    @Override
    public boolean getHaveChilds() {
        return isChild;
    }


    private void initView() {
        mIvTextBarBack = (ImageView) findViewById(R.id.iv_text_bar_back);
        mIvTextBarBack.setOnClickListener(this);
        mIvTextBarTimeImg = (ImageView) findViewById(R.id.iv_text_bar_time_img);
        mActivityTitleText = (TextView) findViewById(R.id.activity_title_text);
        mLlTextBarTitle = (LinearLayout) findViewById(R.id.ll_text_bar_title);
        mIvTextBarDelect = (ImageView) findViewById(R.id.iv_text_bar_delect);
        mIvTextBarMore = (ImageView) findViewById(R.id.iv_text_bar_more);
        mIvTextBarMore.setOnClickListener(this);
        mTvCaseExamSubmit = (TextView) findViewById(R.id.tv_case_exam_submit);
        mLlTextTitleBar = (LinearLayout) findViewById(R.id.ll_text_title_bar);
        mVGmReadLine = (View) findViewById(R.id.v_gm_read_line);
        mReaderViewPager = (ReaderViewPager) findViewById(R.id.readerViewPager);
        mReaderViewPagertwo = (ReaderViewPager) findViewById(R.id.readerViewPagertwo);
        mShadowView = (ImageView) findViewById(R.id.shadowView);
        mFlContentLayoutOne = (FrameLayout) findViewById(R.id.fl_content_layout_one);
        mTvEmpty = (TextView) findViewById(R.id.tv_empty);
        mVGmBarLine = (View) findViewById(R.id.v_gm_bar_line);
        mIvGmbanJianpan = (ImageView) findViewById(R.id.iv_gmban_jianpan);
        mEtGmSubmit = (EditText) findViewById(R.id.et_gm_submit);
        mIvGmSubmitSend = (ImageView) findViewById(R.id.iv_gm_submit_send);
        mLlGmSubmitEvalue = (LinearLayout) findViewById(R.id.ll_gm_submit_evalue);
        mChbGmCollect = (CheckBox) findViewById(R.id.chb_gm_collect);
        mTvTextCollect = (TextView) findViewById(R.id.tv_text_collect);
        mIvTextMenu = (ImageView) findViewById(R.id.iv_text_menu);
        mIvTextMenu.setOnClickListener(this);
        mTvTextQid = (TextView) findViewById(R.id.tv_text_qid);
        mTvTextAllqid = (TextView) findViewById(R.id.tv_text_allqid);
        mTvTextShare = (TextView) findViewById(R.id.tv_text_share);
        mLiTextNavbar = (LinearLayout) findViewById(R.id.li_text_navbar);
        mLlNewtextBar = (LinearLayout) findViewById(R.id.ll_newtext_bar);
        mSeachresultRootLayout = (LinearLayout) findViewById(R.id.seachresult_root_layout);
    }

    public List<CaseCardVo> getLists() {
        List<QuestionCaseVo> list = mQuestionSqliteHelp.queryAllListChildData(mQuestionSqliteVo.getQuestion_id());
        List<CaseCardVo> mCardLists = new ArrayList<>();
        QuestionCaseVo vo = new QuestionCaseVo();
        vo.setParent_id(mQuestionSqliteVo.getParent_id());
        vo.setQuestiontype(mQuestionSqliteVo.getQuestiontype());
        vo.setQuestion_id(mQuestionSqliteVo.getQuestion_id());
        vo.setId(mQuestionSqliteVo.getId());
        vo.setChapter_id(mQuestionSqliteVo.getChapter_id());
        vo.setDifficulty(mQuestionSqliteVo.getDifficulty());
        CaseCardVo caseCardVo = new CaseCardVo();
        caseCardVo.setMaintype(1);
        caseCardVo.setType(1);
        caseCardVo.setCasevo(vo);
        if (list == null || list.isEmpty()) {
            isChild = false;
        } else {
            List<CaseCardVo> childs = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                QuestionCaseVo caseVo = list.get(i);
                CaseCardVo cardVo = new CaseCardVo();
                cardVo.setType(2);
                cardVo.setMaintype(2);
                cardVo.setCasevo(caseVo);
                childs.add(cardVo);
            }
            caseCardVo.setList(childs);
            isChild = true;
        }
        mCardLists.add(caseCardVo);
//        mTextUtil.doDataListCaseEvent(mCardLists, list);
        return mCardLists;
    }

    private void initData(int index) {
        mTvTextAllqid.setText(String.valueOf(mMainDataLists.size()));
        mTvTextQid.setText(String.valueOf(++index));
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
                mReadFragment = GmReadSeachFragment.newInstance(null, position, mCourseid);
            } else {
                CaseCardVo mCaseCardVo = (CaseCardVo) mMainDataLists.get(position);
//                mQuesiton_id = mCaseCardVo.getCasevo().getQuestion_id();
                mReadFragment = GmReadSeachFragment.newInstance(mCaseCardVo, position, mCourseid);
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
                mReadFragmentTwo = GmReadSeachFragment.newInstance(null, position, mCourseid);
            } else {
                CaseCardVo mCaseCardVoTwo = (CaseCardVo) mFuDataLists.get(position);
                mReadFragmentTwo = GmReadSeachFragment.newInstance(mCaseCardVoTwo, position, mCourseid);
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
        mMainDataLists=null;
    }

}

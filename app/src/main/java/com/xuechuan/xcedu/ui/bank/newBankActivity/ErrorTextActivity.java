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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.bank.GmGridViewAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.event.GmChangerColorEvent;
import com.xuechuan.xcedu.fragment.GmReadFragment;
import com.xuechuan.xcedu.fragment.view.GmReadInterface;
import com.xuechuan.xcedu.mvp.contract.GmSubmitComContract;
import com.xuechuan.xcedu.mvp.model.GmSubmitComModel;
import com.xuechuan.xcedu.mvp.presenter.GmSubmitComPresenter;
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
import com.xuechuan.xcedu.utils.GmReadColorManger;
import com.xuechuan.xcedu.utils.GmTextUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.ErrorCollectVo;
import com.xuechuan.xcedu.vo.QuestionCaseVo;
import com.xuechuan.xcedu.vo.ResultVo;
import com.xuechuan.xcedu.vo.SqliteVo.CollectSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.DoLogProgreeSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.ErrorSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.UserInfomSqliteVo;
import com.xuechuan.xcedu.weight.CommonPopupWindow;
import com.xuechuan.xcedu.weight.ReaderViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: TextActivity
 * @Package com.xuechuan.xcedu.ui.bank
 * @Description: 错题
 * @author: L-BackPacker
 * @date: 2018.12.05 下午 3:21
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.12.05
 */
public class ErrorTextActivity extends BaseActivity implements GmSubmitComContract.View, View.OnClickListener, GmReadInterface {
    /**
     * 章节id
     */
    private static String ERRORCOLLECT = "com.xuechuan.xcedu.ui.bank.ercolist";
    /**
     * 科目
     */
    private static String COUERSID = "com.xuechuan.xcedu.ui.bank.COUERSID";

    /**
     * 类型
     */
    private static String TYPE = "com.xuechuan.xcedu.ui.bank.TYPE";
    /**
     * 章id
     */
    private static String CHAPTER_ID = "com.xuechuan.xcedu.ui.bank.chapter_id";
    private Context mContext;

    private String mCouers_id;
    private List<QuestionCaseVo> lists;
    private DoBankSqliteHelp mDoBankHelp;

    private GmReadFragment mReadFragment;

    private GmTextUtil mTextUtil;
    private CommonPopupWindow mPopAnswer;
    private GmReadColorManger mColorManger;
    private DoLogProgressSqliteHelp mDoLogProgressSqliteHelp;
    private DialogUtil mDialogUtil;
    private QuestionCaseVo mQuestionCaseVo;
    private DoMockBankSqliteHelp mDoMockBankSqliteHelp;
    private UserInfomDbHelp mInfomdbhelp;
    private UpDoBankSqlteHelp mUpDoBankSqlteHelp;
    private ErrorSqlteHelp mErrorSqlteHelp;
    private QuestionSqliteHelp mQuestionHelp;

    private GmSubmitComPresenter mSubmitPresenter;
    private AlertDialog mShowDialog;
    private ArrayList<ErrorCollectVo> mDataLists;
    /**
     * 删除次数
     */
    private int mDeletenumber;
    private CollectSqliteHelp mCollectSqliteHelp;
    private UpErrorSqlteHelp mUpErrorSqlteHelp;
    private UpCollectSqlteHelp mUpCollectSqlteHelp;
    private DoUpLogProgressSqliteHelp mUpLogProgressSqliteHelp;
    private UpDeleteErrorSqlteHelp mUpDeleteErrorSqlteHelp;
    private UpDeleteCollectSqlteHelp mUpDeleteCollectSqlteHelp;
    private int mType;
    private int mChapter_id;
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
    private LinearLayout mErrorRootLayout;


    /**
     * @param context
     * @param mCouersid  科目id
     * @param data       数据
     * @param chapter_id
     * @param type       1 为所有 2 章节
     * @return
     */
    public static Intent start_Intent(Context context, String mCouersid, ArrayList<ErrorCollectVo> data,
                                      int type, int chapter_id) {
        Intent intent = new Intent(context, ErrorTextActivity.class);
        intent.putExtra(ERRORCOLLECT, data);
        intent.putExtra(COUERSID, mCouersid);
        intent.putExtra(TYPE, type);
        intent.putExtra(CHAPTER_ID, chapter_id);
        return intent;
    }

 /*   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_text_layout);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_error_text_layout);
        if (getIntent() != null) {
            mDataLists = (ArrayList<ErrorCollectVo>) getIntent().getSerializableExtra(ERRORCOLLECT);
            //科目id
            mCouers_id = getIntent().getStringExtra(COUERSID);
            //类型
            mType = getIntent().getIntExtra(TYPE, 0);
            mChapter_id = getIntent().getIntExtra(CHAPTER_ID, 0);

        }
        initView();
        mIvTextBarDelect.setVisibility(View.VISIBLE);
        initUtils();
        initSetting();
        AsmycTcakData data = new AsmycTcakData();
        data.execute(mCouers_id);
    }

    private void doNextEvent(List<QuestionCaseVo> list) {
        lists = list;
        initConfig();
        //初始化翻页效果
        initReadViewPager();
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
                QuestionCaseVo mQuestionCaseVo = lists.get(prePosition2);
                if (mQuestionCaseVo != null) {
                    CollectSqliteVo vo = new CollectSqliteVo();
                    vo.setCollectable(isChecked ? 1 : 0);
                    vo.setChapterid(mQuestionCaseVo.getChapter_id());
                    vo.setCourseid(Integer.parseInt(mCouers_id));
                    vo.setQuestion_id(mQuestionCaseVo.getQuestion_id());
                    vo.setQuestiontype(mQuestionCaseVo.getQuestiontype());
                    vo.setTime(String.valueOf(new Date().getTime()));
                    if (isChecked) {
                        mCollectSqliteHelp.addCoolectItem(vo);
                        mUpCollectSqlteHelp.addCollectSqliteVo(vo);
                        T.showToast(mContext, "收藏成功");
                    } else {
                        T.showToast(mContext, "取消收藏");
                        mCollectSqliteHelp.deleteItem(Integer.parseInt(mCouers_id), mQuestionCaseVo.getChapter_id(), mQuestionCaseVo.getQuestion_id());
                        mUpCollectSqlteHelp.deleteItem(Integer.parseInt(mCouers_id), mQuestionCaseVo.getChapter_id(), mQuestionCaseVo.getQuestion_id());
                    }
                } else {
                    T.showToast(mContext, "暂无题收藏");
                }
            }
        });

    }

    private void initConfig() {
        if (lists == null || lists.isEmpty()) {
            mFlContentLayoutOne.setVisibility(View.GONE);
            mTvEmpty.setVisibility(View.VISIBLE);
            return;
        } else {
            mTvEmpty.setVisibility(View.GONE);
            mFlContentLayoutOne.setVisibility(View.VISIBLE);
        }
    }

    private void initSetting() {
        UserInfomSqliteVo userInfomVo = mInfomdbhelp.findUserInfomVo();
        String deletenumber = userInfomVo.getDeletenumber();
        if (StringUtil.isEmpty(deletenumber)) {
            deletenumber = "3";
        }
        mDeletenumber = Integer.parseInt(deletenumber);
    }

    /**
     * 显示继续答题对话框
     */
    private void doShowDialogEvent() {

        if (mDoLogProgressSqliteHelp != null) {
            if (lists != null && !lists.isEmpty()) {

                final DoLogProgreeSqliteVo look = mDoLogProgressSqliteHelp.findLookWithTidChapterId(
                        mChapter_id, Integer.parseInt(mCouers_id), mType == 1 ? DataMessageVo.ERROR_FOUR : DataMessageVo.ERROR_SIX);
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

    private void initUtils() {
        mTextUtil = GmTextUtil.get_Instance(mContext);
        //做题临时表
        mDoBankHelp = DoBankSqliteHelp.get_Instance(mContext);
        //用户进度表
        mDoLogProgressSqliteHelp = DoLogProgressSqliteHelp.get_Instance(mContext);
        mDialogUtil = DialogUtil.getInstance();
        //用户做题记录表（记录）
        mDoMockBankSqliteHelp = DoMockBankSqliteHelp.get_Instance(mContext);
        //用户信息表
        mInfomdbhelp = UserInfomDbHelp.get_Instance(mContext);

        //上传用户表
        mUpDoBankSqlteHelp = UpDoBankSqlteHelp.getInstance(mContext);
        //错题表
        mErrorSqlteHelp = ErrorSqlteHelp.getInstance(mContext);
        //用户题干新
        mQuestionHelp = QuestionSqliteHelp.get_Instance(mContext);
        //收藏
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

        //提交评价
        mSubmitPresenter = new GmSubmitComPresenter();
        mSubmitPresenter.initModelView(new GmSubmitComModel(), this);
    }


    private void initData(int index) {
        mTvTextAllqid.setText(String.valueOf(lists.size()));
        mTvTextQid.setText(String.valueOf(++index));
    }


    /**
     * 设置答题卡布局
     */
    private void showAnswerCardLayout() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;

        mPopAnswer = new CommonPopupWindow(this, R.layout.pop_item_answer, ViewGroup.LayoutParams.MATCH_PARENT, (int) (screenHeight * 0.7)) {
            private Button mBtnSubmit;
            private TextView mTvPopNew;
            private TextView mTvLine;
            private TextView mTvTextPopERROR;
            private TextView mTvTextPopRight;
            private TextView mTvTextPopRegression;
            private TextView mTvPopCount;
            private RecyclerView mRlvPopContent;
            private GridView mGvPopContent;
            private LinearLayout mLLPopLayout;

            @Override
            protected void initView() {
                View view = getContentView();
                mTvPopNew = (TextView) view.findViewById(R.id.tv_pop_new);
                mTvLine = (TextView) view.findViewById(R.id.tv_line);
                mTvPopCount = (TextView) view.findViewById(R.id.tv_pop_count);
                mRlvPopContent = view.findViewById(R.id.rlv_pop_content);
                mGvPopContent = view.findViewById(R.id.gv_pop_content);
                mBtnSubmit = (Button) view.findViewById(R.id.btn_pop_answer_sumbit);
                mLLPopLayout = (LinearLayout) view.findViewById(R.id.ll_pop_layout);
                mTvTextPopRight = (TextView) view.findViewById(R.id.tv_text_pop_right);
                mTvTextPopERROR = (TextView) view.findViewById(R.id.tv_text_pop_error);
                mTvTextPopRegression = (TextView) view.findViewById(R.id.tv_text_pop_regression);
            }

            @Override
            protected void initEvent() {
                if (mColorManger != null) {
                    mTvPopNew.setTextColor(mColorManger.getmTextTitleColor());
                    mTvPopCount.setTextColor(mColorManger.getmTextTitleColor());
                    mBtnSubmit.setTextColor(mColorManger.getmTextTitleColor());
                    mLLPopLayout.setBackgroundColor(mColorManger.getmLayoutBgColor());
                    mTvTextPopRight.setTextColor(mColorManger.getmTextFuColor());
                    mTvTextPopRegression.setTextColor(mColorManger.getmTextFuColor());
                    mTvTextPopERROR.setTextColor(mColorManger.getmTextFuColor());
                    mTvLine.setTextColor(mColorManger.getmTextTitleColor());
                }
                mTvPopNew.setText(String.valueOf(curPosition2 + 1));
                mTvPopCount.setText(String.valueOf(lists.size()));
                bindGridViewAdapter();
            }

            @Override
            protected void initWindow() {
                super.initWindow();
                PopupWindow instance = getPopupWindow();
                instance.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mTextUtil.setBackgroundAlpha(1f, ErrorTextActivity.this);
                    }
                });
            }

            private void bindGridViewAdapter() {
                GmGridViewAdapter adapter = new GmGridViewAdapter(mContext, lists);
                adapter.doEventListDatas(findAllDoDatas());
                adapter.doEventColor(mColorManger);
                adapter.doCurrentPostion(curPosition2);
                mGvPopContent.setAdapter(adapter);
                mGvPopContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        curPosition2 = position;
                        mReaderViewPager.setCurrentItem(position);
                        mPopAnswer.getPopupWindow().dismiss();
                    }
                });
            }
        };
        mPopAnswer.showAtLocation(mErrorRootLayout, Gravity.BOTTOM, 0, 0);
        mTextUtil.setBackgroundAlpha(0.5f, ErrorTextActivity.this);
    }

    private int prePosition2;
    private int curPosition2;

    private void initReadViewPager() {
        GmFragmentAdpater mViewapgerAdapter = new GmFragmentAdpater(getSupportFragmentManager(), mCouers_id);
        mReaderViewPager.setAdapter(mViewapgerAdapter);
        mReaderViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mShadowView.setTranslationX(mReaderViewPager.getWidth() - positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                curPosition2 = position;
                prePosition2 = curPosition2;
                initData(position);
                changerCollect();
                setShowBarView(false);
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
                        if (!mIsScrolled&&lists!=null) {
                            if (prePosition2 == (lists.size() - 1)) {
                                T.showToast(mContext, getStringWithId(R.string.last_questionOne));
                            }
                        }
                        mIsScrolled = true;
                        break;
                }
            }
        });
        mReaderViewPager.setOffscreenPageLimit(3);
    }


    @Override
    public void onClick(final View v) {
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
            case R.id.iv_gm_submit_send://评价
                String trim = mEtGmSubmit.getText().toString().trim();
                if (StringUtil.isEmpty(trim)) {
                    T.showToast(mContext, getStringWithId(R.string.input_content));
                    return;
                }
                mShowDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_loading));
                mSubmitPresenter.submiteCommont(mContext,
                        String.valueOf(mQuestionCaseVo.getParent_id()), trim, "", DataMessageVo.QUESTION);
                Utils.hideInputMethod(mContext, mEtGmSubmit);
                break;
            case R.id.iv_gmban_jianpan://键盘
                setShowBarView(false);
                break;
            case R.id.iv_text_bar_delect://删除错题
                if (lists == null || lists.isEmpty()) {
                    T.showToast(mContext, "暂无错误");
                    return;
                }
                DialogUtil dialogUtil = DialogUtil.getInstance();
                dialogUtil.showTitleDialog(mContext, getString(R.string.suredelete), getStringWithId(R.string.delect), getStringWithId(R.string.cancel), true);
                dialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
                    @Override
                    public void onSureClickListener() {
                        QuestionCaseVo questionCaseVo = lists.get(prePosition2);
                        ErrorSqliteVo vo = new ErrorSqliteVo();
                        vo.setQuesitonid(questionCaseVo.getQuestion_id());
                        vo.setChapterid(questionCaseVo.getChapter_id());
                        vo.setCourseid(Integer.parseInt(mCouers_id));
                        restart(vo);
                        mErrorSqlteHelp.deleteErrorItem(Integer.parseInt(mCouers_id), questionCaseVo.getChapter_id(), questionCaseVo.getQuestion_id());
                        mUpErrorSqlteHelp.deleteErrorItem(Integer.parseInt(mCouers_id), questionCaseVo.getChapter_id(), questionCaseVo.getQuestion_id());
                        mUpDeleteErrorSqlteHelp.addErrorItem(vo);
                    }

                    @Override
                    public void onCancelClickListener() {

                    }
                });

                break;
            case R.id.tv_text_share:
                EventBus.getDefault().postSticky(new GmChangerColorEvent(prePosition2, 4, false));
                break;
        }
    }

    private List<DoBankSqliteVo> findAllDoDatas() {
        List<DoBankSqliteVo> list = new ArrayList<>();
        List<DoBankSqliteVo> doBankSqliteVos = mDoBankHelp.finDAllUserDoText();
        if (doBankSqliteVos == null || doBankSqliteVos.isEmpty()) return list;
        for (int i = 0; i < lists.size(); i++) {
            QuestionCaseVo vo = lists.get(i);
            for (int k = 0; k < doBankSqliteVos.size(); k++) {
                DoBankSqliteVo sqliteVo = doBankSqliteVos.get(k);
                if (vo.getQuestion_id() == sqliteVo.getQuestion_id()) {
                    list.add(sqliteVo);
                }
            }

        }
        return list;
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
        mErrorRootLayout = (LinearLayout) findViewById(R.id.error_root_layout);
        mErrorRootLayout.setOnClickListener(this);
    }

    public ArrayList<QuestionCaseVo> getLists() {
        ArrayList<QuestionCaseVo> list = new ArrayList<>();
        for (int i = 0; i < mDataLists.size(); i++) {
            ErrorCollectVo vo = mDataLists.get(i);
            QuestionCaseVo questionListData = mQuestionHelp.queryAllQuestionQuestionListData(vo.getQuestionid(), mCouers_id);
            list.add(questionListData);
        }
     /*   if (questionListData != null && !questionListData.isEmpty()) {
            for (int i = 0; i < questionListData.size(); i++) {
                QuestionSqliteVo vo = questionListData.get(i);
                String d = EncryptionUtil.D(vo.getQuestion());
                vo.setQuestionStr(d);
                String keyword = EncryptionUtil.D(vo.getKeywords());
                vo.setKeywordStr(keyword);
                String explain = EncryptionUtil.D(vo.getExplained());
                vo.setExplainedStr(explain);
            }
        }*/
        return list;
    }

    @Override
    public void saveUserDoLog(DoBankSqliteVo vo) {
        if (vo == null) return;
        mDoBankHelp.addDoBankItem(vo);
        mUpDoBankSqlteHelp.addDoBankItem(vo);
        mDoMockBankSqliteHelp.addDoBankItem(vo);
    }

    @Override
    public DoBankSqliteVo getUserDoLog(int quesiton_id) {
        if (mDoBankHelp == null) return null;
        DoBankSqliteVo vo = mDoBankHelp.queryWQid(quesiton_id);
        return vo;
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
        mTvEmpty.setBackgroundColor(colorManger.getmLayoutBgColor());
        mTvEmpty.setTextColor(colorManger.getmTextTitleColor());

    }

    @Override
    public QuestionSqliteVo getQuestionVo(int id) {
        QuestionSqliteVo vo = mQuestionHelp.queryQuestionVoWithId(id);
/*        String d = EncryptionUtil.D(vo.getQuestion());
        vo.setQuestionStr(d);
        String keyword = EncryptionUtil.D(vo.getKeywords());
        vo.setKeywordStr(keyword);
        String explain = EncryptionUtil.D(vo.getExplained());
        vo.setExplainedStr(explain);*/
        return vo;
    }

    //错题表
    @Override
    public void doSaveErrorLog(ErrorSqliteVo vo) {
        if (vo == null) return;
//        mErrorSqlteHelp.addErrorItem(vo);
//        mUpErrorSqlteHelp.addErrorItem(vo);
    }

    @Override
    public void upDataSaveErrorLog(ErrorSqliteVo vo) {
        if (vo == null) return;
        initSetting();
        switch (mDeletenumber) {
            case 1:
                mDeletenumber = 1;
                break;
            case 2:
                mDeletenumber = 3;
                break;
            case 3:
                mDeletenumber = 5;
                break;
            case 4:
                mDeletenumber = -1;
                break;
        }
        if (mDeletenumber == -1) {//不删除
            return;
        }
        ErrorSqliteVo errorvo = mErrorSqlteHelp.queryIsAdd(vo.getCourseid(), vo.getChapterid(), vo.getQuesitonid());
        int rightnumber = errorvo.getRightnumber() + 1;
        if (mDeletenumber == rightnumber || rightnumber > mDeletenumber) {//删除错题
            mErrorSqlteHelp.delectErrorItem(errorvo.getId());
            mUpErrorSqlteHelp.delectErrorItem(errorvo.getId());
            restart(vo);
            mUpDeleteErrorSqlteHelp.addErrorItem(vo);
        } else {//更新错题
            mErrorSqlteHelp.upDataRightNumber(errorvo.getId(), rightnumber);
        }
    }

    /**
     * 重新布置数据
     *
     * @param vo
     */
    public void restart(ErrorSqliteVo vo) {
        int mIndex = 0;
        for (int i = 0; i < lists.size(); i++) {
            QuestionCaseVo caseVo = lists.get(i);
            if (caseVo.getQuestion_id() == vo.getQuesitonid()) {
                lists.remove(i);
                mIndex = i;
            }
        }
        if (lists.size() == 0) {
            initConfig();
            mQuestionCaseVo = null;
            return;
        }
        if (mIndex >= lists.size()) {//最后一道题
            mIndex = lists.size();
            prePosition2 = lists.size();
        }
        //初始化翻页效果
        initReadViewPager();
        //显示下表
        initData(0);
        mReaderViewPager.setCurrentItem(mIndex);
    }

    /**
     * 改变用户bar
     *
     * @param show
     */
    @Override
    public void changerBarView(boolean show) {
        setShowBarView(show);
    }

    @Override
    public boolean getErrorOrCollert() {
        return true;
    }

    @Override
    public void changerCollect() {
        QuestionCaseVo vo = lists.get(prePosition2);
        initCollect(vo);
    }

    private void initCollect(QuestionCaseVo vo) {
        CollectSqliteVo sqliteVo = mCollectSqliteHelp.queryCollectVo(Integer.parseInt(mCouers_id),
                vo.getChapter_id(), vo.getQuestion_id());
        if (sqliteVo != null) {
            mChbGmCollect.setChecked(sqliteVo.getCollectable() == 1);
        } else {
            mChbGmCollect.setChecked(false);
        }
    }

    /**
     * 是否显示输入框
     *
     * @param show
     */
    private void setShowBarView(boolean show) {
        mLiTextNavbar.setVisibility(show ? View.GONE : View.VISIBLE);
        mLlGmSubmitEvalue.setVisibility(show ? View.VISIBLE : View.GONE);
        if (show) {
            Utils.showSoftInputFromWindow(ErrorTextActivity.this, mEtGmSubmit);
        } else {
            mEtGmSubmit.setText(null);
            Utils.hideInputMethod(mContext, mEtGmSubmit);

        }
    }

    @Override
    public DoBankSqliteVo queryUserData(int qustion_id) {
        if (mDoBankHelp == null) return null;
        return mDoBankHelp.queryWQid(qustion_id);
    }

    @Override
    public void SubmitSuccess(String success) {
        dismissDialog(mShowDialog);
        ResultVo vo = Utils.getGosnT(success, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData().getStatusX() == 1) {
                T.showToast(mContext, getStringWithId(R.string.evelua_sucee));
                setShowBarView(false);
            } else {
                T_ERROR(mContext);
            }

        } else {
            T_ERROR(mContext);
        }

    }

    @Override
    public void SubmiteError(String msg) {
        setShowBarView(false);
        T_ERROR(mContext);
    }


    public class GmFragmentAdpater extends FragmentStatePagerAdapter {

        private final String mCourseid;

        public GmFragmentAdpater(FragmentManager fm, String coursid) {
            super(fm);
            this.mCourseid = coursid;
        }

        @Override
        public Fragment getItem(int position) {
            if (lists.size() == 0) {
                mReadFragment = GmReadFragment.newInstance(null, position, mCourseid);
            } else {
                mQuestionCaseVo = (QuestionCaseVo) lists.get(position);

                mReadFragment = GmReadFragment.newInstance(mQuestionCaseVo, position, mCourseid);
            }
            return mReadFragment;
        }

        @Override
        public int getCount() {
            if (lists.size() == 0) {
                return 1;
            } else
                return lists.size();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        doDoText();
        if (mDoBankHelp != null) {
            mDoBankHelp.delelteTable();
        }
        lists = null;
        mDataLists = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        doDoText();

    }

    private void doDoText() {
        if (mQuestionCaseVo != null) {
            UserInfomDbHelp help = UserInfomDbHelp.get_Instance(mContext);
            UserInfomSqliteVo vo = help.findUserInfomVo();
            DoLogProgreeSqliteVo sqliteVo = new DoLogProgreeSqliteVo();
            sqliteVo.setChapterid(mChapter_id);
            sqliteVo.setKid(Integer.parseInt(mCouers_id));
            sqliteVo.setTextid(mQuestionCaseVo.getQuestion_id());
            if (prePosition2 == 0) {
                sqliteVo.setNumber("0");
            } else {
                sqliteVo.setNumber(String.valueOf(++prePosition2));
            }
            sqliteVo.setUserid(String.valueOf(vo.getSaffid()));
            sqliteVo.setAllNumber(String.valueOf(lists.size()));
            sqliteVo.setType(mType == 1 ? DataMessageVo.ERROR_FOUR : DataMessageVo.ERROR_SIX);
            mDoLogProgressSqliteHelp.addDoLookItem(sqliteVo);
            mUpLogProgressSqliteHelp.addDoLookItem(sqliteVo);
        }

    }

    private class AsmycTcakData extends AsyncTask<String, Void, List<QuestionCaseVo>> {

        private AlertDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            show(false);
            dialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        }

        @Override
        protected List<QuestionCaseVo> doInBackground(String... strings) {
           /* ArrayList<QuestionCaseVo> list = new ArrayList<>();
            for (int i = 0; i < mDataLists.size(); i++) {
                ErrorCollectVo vo = mDataLists.get(i);
                QuestionCaseVo questionListData = mQuestionHelp.queryAllQuestionQuestionListData(vo.getQuestionid(), strings[0]);
                list.add(questionListData);
            }*/
            List<QuestionCaseVo> list = mQuestionHelp.queryAllQuestionQuestionListData(mDataLists, strings[0]);
            return list;
        }

        @Override
        protected void onPostExecute(List<QuestionCaseVo> list) {
            super.onPostExecute(list);
            dismissDialog(dialog);
            show(true);
            cancel(true);
            doNextEvent(list);
        }
    }

    private void show(boolean show) {
        mErrorRootLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
package com.xuechuan.xcedu.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.etiennelawlor.discreteslider.library.ui.DiscreteSlider;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.adapter.bank.GalleryAdapter;
import com.xuechuan.xcedu.adapter.bank.GmEvaluateAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.event.GmChangerColorEvent;
import com.xuechuan.xcedu.mvp.contract.GmEvalueContract;
import com.xuechuan.xcedu.mvp.model.GmEvalueModel;
import com.xuechuan.xcedu.mvp.presenter.GmEvaluePresenter;
import com.xuechuan.xcedu.sqlitedb.UserInfomDbHelp;
import com.xuechuan.xcedu.ui.ImagerActivity;
import com.xuechuan.xcedu.ui.bank.BankBuyActivity;
import com.xuechuan.xcedu.ui.bank.newBankActivity.CaseExamtActivity;
import com.xuechuan.xcedu.ui.bank.newbank.NoteMakeActivity;
import com.xuechuan.xcedu.utils.GmFontSizeUtil;
import com.xuechuan.xcedu.utils.GmReadColorManger;
import com.xuechuan.xcedu.utils.GmSelectImgManageUtil;
import com.xuechuan.xcedu.utils.GmTextUtil;
import com.xuechuan.xcedu.utils.ScreenShot;
import com.xuechuan.xcedu.utils.ShareUtils;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.Subsciber;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.TestObserver;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.CaseCardVo;
import com.xuechuan.xcedu.vo.EvalueNewVo;
import com.xuechuan.xcedu.vo.QuestionCaseVo;
import com.xuechuan.xcedu.vo.SelfEvaluateVo;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.UserInfomSqliteVo;
import com.xuechuan.xcedu.weight.CommonPopupWindow;
import com.xuechuan.xcedu.weight.ContentEditText;
import com.xuechuan.xcedu.weight.FlowLayout;
import com.xuechuan.xcedu.weight.SmartScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: ReadFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 案例分析(考试)
 * @author: L-BackPacker
 * @date: 2018.12.05 下午 5:04
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.12.05
 */

public class GmReadThreeFragment extends BaseFragment implements TestObserver, GmEvalueContract.View, View.OnClickListener {

    private static final String ARG_PARAM_DATA = "data";
    private static final String ARG_PARAM_COUID = "couid";
    private static final String ARG_PARAM_POSTION = "postion";
    private static String TAG = "【" + GmReadThreeFragment.class + "】==";
    private View view;
    private QuestionSqliteVo mQuestionSqliteVo;
    private TextView mTvContent;
    private Context mContext;

    private String mCouresid;
    /**
     * 判断用户是否购买
     */
    private boolean mUserBuy = false;
    private GmReadColorManger mGmReadColorManger;
    private GmSelectImgManageUtil mGmImgManageUtil;
    private TextView mTvGmTitleFirst;
    private TextView mTvGmTitleAllnumber;
    private TextView mTvGmTitleTextLast;
    private ImageView mIvGmTitleMulu;
    private LinearLayout mLlGmtitleSelectLayout;
    private ImageView mIvGmOneA;
    private TextView mTvGmOneAContent;
    private ImageView mIvGmOneB;
    private TextView mTvGmOneBContent;
    private ImageView mIvGmOneC;
    private TextView mTvGmOneCContent;
    private ImageView mIvGmOneD;
    private TextView mTvGmOneDContent;
    private ImageView mIvGmOneE;
    private TextView mTvGmOneEContent;
    private LinearLayout mLlGmOneELayout;
    private Button mBtnGmoneSuerAnswer;
    private View mVGmOneLine;
    private TextView mTvGmOneAnswerTitle;
    private TextView mTvGmOneAnswerContent;
    private LinearLayout mLlGmoneAnswerLayout;
    private LinearLayout mLlGmOneSelectLayout;
    private Button mBtnBBuy;
    private LinearLayout mLiBResolveBuy;
    private TextView mTvGmTwoJiexiTitle;
    private TextView mTvGmTwoJiexiContent;
    private TextView mTvGmTwoDifficultyTitle;
    private ImageView mIvGmTwoStarOne;
    private ImageView mIvGmTwoStarTwo;
    private ImageView mIvGmTwoStarThree;
    private ImageView mIvGmTwoStarFour;
    private ImageView mIvGmTwoStarFive;
    private TextView mTvGmTwoRightTitle;
    private TextView mTvGmTwoRightContent;
    private TextView mTvGmTwoCentreTitle;
    private FlowLayout mFlowlayoutGmTwo;
    private View mVGmTwoLineTwo;
    private LinearLayout mLlGmTwoAnalysisLayout;
    private TextView mTvGmThreeNoteTitle;
    private TextView mTvGmThreeNote;
    private View mVGmThreeLine;
    private View mVGmGmEightOneLineTwo;
    private LinearLayout mLlGmthreeNoteLayout;
    private TextView mTvGmfourEvaluateTitle;
    private TextView mTvGmfourNuber;
    private ImageView mIvGmfourEvaluate;
    private RecyclerView mRlvGmfourContent;
    private RelativeLayout mRlGmfourLayout;
    private LinearLayout mLlGmfourMoreData;
    private LinearLayout mLlFourEvaluateLayout;
    //    private WeiNestedScrollView mSmsvLayout;
    private SmartScrollView mSmsvLayout;
    private ImageView mIvNetEmptyContent;
    private Subsciber mSubsciber;
    private LinearLayout mLlGmoneALayout;
    private LinearLayout mLlGmoneBLayout;
    private LinearLayout mLlGmoneCLayout;
    private LinearLayout mLlGmoneDLayout;
    private GmTextUtil mGmTextUtil;

    /**
     * 选项A
     */
    private String mSelectA = "A";
    /**
     * 选项B
     */
    private String mSelectB = "B";
    /**
     * 选项C
     */
    private String mSelectC = "C";
    /**
     * 选项D
     */
    private String mSelectD = "D";
    /**
     * 选项E
     */
    private String mSelectE = "E";

    //多选状况
    private String mSelectMorItemA = null;
    private String mSelectMorItemB = null;
    private String mSelectMorItemC = null;
    private String mSelectMorItemD = null;
    private String mSelectMorItemE = null;
    //记录用户是否点击选项
    private boolean mSelectMorisClickA;
    private boolean mSelectMorisClickB;
    private boolean mSelectMorisClickC;
    private boolean mSelectMorisClickD;
    private boolean mSelectMorisClickE;
    //用户是否确认多选
    private boolean mDuoXunaSure = true;
    /**
     * ,问题类型 2,单选题；3,多选题；4,简答题 5预读理解
     */
    private int mQuestionType;
    private View mViewGmtwoLine;
    /**
     * 集合数据
     */
    private ArrayList mArray;
    /**
     * 是否显示加载更多
     */
    private boolean mShowMoreView = false;
    private GmEvaluateAdapter mEvalueAdapter;
    private GmEvaluePresenter mGmEvaluePresenter;
    private CommonPopupWindow mPopSetting;
    private LinearLayout mGmRoolLayout;
    //用户是否显示自动跳转
    private String mUserNextGo;
    //用户选中模式
    private String mShowDayOrNight;
    //用户选中删除次数
    private String mDeletenumber;

    private UserInfomDbHelp mUserDbHelp;
    //字体大小
    private String mFoneSize;
    private GmFontSizeUtil mGmFontSizeUtil;
    private View mVGmtwoLineThree;
    private int mPostion;
    private int mSaffid;
    /**
     * 考试
     */
    private CaseExamtActivity mCaseExamtActivity;
    private RelativeLayout mRlvGmSevenAnswerLayout;
    private TextView mTvGmsixtextCollect;
    private ImageView mIvGmsixtextMenu;
    private TextView mTvGmsixtextQid;
    private TextView mTvGmsixtextAllqid;
    private TextView mTvGmsixtextShare;
    private LinearLayout mLiGmsixTextNavbar;
    private FrameLayout mFlGmsixLayout;
    private Button mBtnGmsevenAnswer;
    private Button mBtnGmEightLookJiexi;
    private TextView mTvGmEightAnswerTitle;
    private TextView mTvGmEightAnswerTitleContent;
    private LinearLayout mLlGmEightLookJiexi;
    private LinearLayout mLlGmeightJiandaLayout;
    private CaseCardVo mCaseCardVo;
    private TextView mTvGmLine;
    private LinearLayout mGmTworoolLayout;
    private TextView mTvGmEightOneLookJiexi;
    private ImageView mIvGmEightOneYuyinImg;
    private TextView mTvGmEightOneYuyinContent;
    private RelativeLayout mRlGmEightOneYuyin;
    private ImageView mIvGmEightOneAnswerTitle;
    private TextView mTvGmEightOneAnswerTitleContent;
    private LinearLayout mLlGmEightOneLookJiexi;
    private LinearLayout mLlGmeightOneJiandaLayout;
    private ContentEditText mEtEightOneInputContent;
    private TextView mTvGmZoneQuestionTitle;
    private LinearLayout mLlGmZoneQuestionTitle;
    private TextView mTvGmsevenTextContent;
    private View mViewGmEightOneLine;
    private View mViewGmEightTwoLine;
    private TextView mTvGmThreeNoteContent;
    private LinearLayout mLlGmThreeNoteBg;
    private ImageView mIvGmEightOneYuyinOneImg;
    private TextView mTvGmEightOneInputContent;
    private RecyclerView mRlvGmEightOneGallery;
    private RelativeLayout mRlGmEightOneJiexifu;
    private TextView mTvGmEightOneInputTitle;

    private LinearLayout mLlGmEightOneInputShow;
    private LinearLayout mLlGmEightOneInputEdit;
    private View mViewGmEightOneLineOne;
    private TextView mTvGmEightOneNumberTitle;
    private TextView mTvGmEightOneNumberTitleContent;
    private Button mBtnGmEightOneAddEvalues;
    private TextView mTvGmEihgtOneZone;
    private TextView mTvGmEightOneSix;
    private SeekBar mSbGmEightOne;
    private LinearLayout mLlGmEightOneEvlauesLayout;
    private CommonPopupWindow mPopShare;
    private ImageView mIvGmZoneQuestintype;
    private SmartScrollView mSmsvTwoLayout;
    private ImageView mIvGmZoneQuestionImg;
    private ImageView mIvGmTwoAnalysisImg;


    /***
     *
     * @param vo 数据类型
     * @param mCouserid 科目id 1 技术，2 综合，3 案例
     * @return
     */
    public static GmReadThreeFragment newInstance(CaseCardVo vo, int postion, String mCouserid) {
        GmReadThreeFragment fragment = new GmReadThreeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_DATA, vo);
        args.putString(ARG_PARAM_COUID, mCouserid);
        args.putInt(ARG_PARAM_POSTION, postion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCaseCardVo = (CaseCardVo) getArguments().getSerializable(ARG_PARAM_DATA);
            mCouresid = getArguments().getString(ARG_PARAM_COUID);
            mPostion = getArguments().getInt(ARG_PARAM_POSTION);
        }

    }

 /*   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_red_two_text_layout, container, false);
        initView(view);
        return view;
    }*/

    @Override
    protected int initInflateView() {
        return R.layout.fragment_red_two_text_layout;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        //初始化activity
        initActivity();
        if (mCaseCardVo == null) {
            mQuestionSqliteVo = null;
        } else {
            if (mCaseExamtActivity != null) {
                mQuestionSqliteVo = mCaseExamtActivity.getQuestionVo(mCaseCardVo.getCasevo().getId());
            }
        }
        if (mQuestionSqliteVo == null) {
            //滑动布局
            mSmsvLayout.setVisibility(View.GONE);
            mIvNetEmptyContent.setVisibility(View.VISIBLE);
            return;
        } else {
            //滑动布局
            mSmsvLayout.setVisibility(View.VISIBLE);
            mIvNetEmptyContent.setVisibility(View.GONE);
        }

        //初始话用户购买情况
        initUserBuy();
        //初始化配置
        initSetting();
        //初始化工具类
        initUtils();

        //初始化界面
        initShowView();
        //解读数据
        readDataBindView();
        //初始化适配器
        initEvalueAdapter();
        //注册观察者
        registerOberovce();

        //初始化wiew事件
        initViewEvent();
        //初始化笔记
        initNoteData();
    }

    private void initNoteData() {
        mGmEvaluePresenter.reqeustNoteByQuestion(mContext, mQuestionSqliteVo.getQuestion_id());
    }

    private void initViewEvent() {
        saveUserInput();
        mSbGmEightOne.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double a = progress;
                double v = a * 0.5;
                mTvGmEightOneNumberTitle.setText(String.valueOf(v));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                double a = seekBar.getProgress();
                double v = a * 0.5;
                mTvGmEightOneNumberTitle.setText(String.valueOf(v));
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                double a = seekBar.getProgress();
                double v = a * 0.5;
                mTvGmEightOneNumberTitle.setText(String.valueOf(v));
            }
        });
    }

    private void initActivity() {
        //初始化选项图片
        if (getActivity() instanceof CaseExamtActivity) {//章节练习
            mCaseExamtActivity = (CaseExamtActivity) getActivity();
        }


    }

    private void initSetting() {
        if (mUserDbHelp == null) {
            mUserDbHelp = UserInfomDbHelp.get_Instance(mContext);
        }
        UserInfomSqliteVo vo = mUserDbHelp.findUserInfomVo();
        if (vo == null) {
            return;
        }
        mSaffid = vo.getSaffid();
        //是否自动跳转下一题
        mUserNextGo = vo.getUserNextGo();
        //用户选中得模式
        mShowDayOrNight = vo.getShowDayOrNight();
        //用户删除次数
        mDeletenumber = vo.getDeletenumber();
        //字体大小
        mFoneSize = vo.getFontSize();

    }

    //初始化适配器
    private void initEvalueAdapter() {
        mGmEvaluePresenter.reqeustEvalueContentOne(mContext, 1, DataMessageVo.QUESTION, String.valueOf(mQuestionSqliteVo.getQuestion_id()));
        clearData();
        setGridLayoutManger(mContext, mRlvGmfourContent, 1);
        mEvalueAdapter = new GmEvaluateAdapter(mContext, mArray);
        mEvalueAdapter.doChangerColor(mGmReadColorManger);
        mRlvGmfourContent.setAdapter(mEvalueAdapter);
      /*  mSmsvLayout.setmSmartScrollChangedListener(new WeiNestedScrollView.ISmartScrollChangedListener() {
            @Override
            public void onScrolledToBottom() {
                Log.e(TAG, "onScrolledToBottom: ");
            }

            @Override
            public void onScrolledToTop() {
                Log.e(TAG, "onScrolledToTop: ");
            }
        });*/
        mSmsvLayout.setScanScrollChangedListener(new SmartScrollView.ISmartScrollChangedListener() {
            @Override
            public void onScrolledToBottom() {

            }

            @Override
            public void onScrolledToTop() {

            }
        });

    }

    private void initUtils() {
        //颜色
        mGmReadColorManger = GmReadColorManger.get_Instance(mContext);
        //选则项按钮
        mGmImgManageUtil = GmSelectImgManageUtil.get_Instance(mContext);
        //做题控制器
        mGmTextUtil = GmTextUtil.get_Instance(mContext);
        //初始化字体大小
        mGmFontSizeUtil = GmFontSizeUtil.get_Instance(mContext);
    }

    private void initView(View view) {
        mContext = getActivity();
        mTvGmTitleFirst = (TextView) view.findViewById(R.id.tv_gmTitle_first);
        mTvGmTitleAllnumber = (TextView) view.findViewById(R.id.tv_gmTitle_Allnumber);
        mTvGmTitleAllnumber.setOnClickListener(this);
        mTvGmTitleTextLast = (TextView) view.findViewById(R.id.tv_gmTitle_text_last);
        mIvGmTitleMulu = (ImageView) view.findViewById(R.id.iv_gmTitle_mulu);
        mIvGmTitleMulu.setOnClickListener(this);
        mLlGmtitleSelectLayout = (LinearLayout) view.findViewById(R.id.ll_gmtitle_select_layout);
        mLlGmtitleSelectLayout.setOnClickListener(this);
        mIvGmOneA = (ImageView) view.findViewById(R.id.iv_gmOne_a);
        mTvGmOneAContent = (TextView) view.findViewById(R.id.tv_gmOne_a_content);
        mIvGmOneB = (ImageView) view.findViewById(R.id.iv_gmOne_b);
        mTvGmOneBContent = (TextView) view.findViewById(R.id.tv_gmOne_b_content);
        mIvGmOneC = (ImageView) view.findViewById(R.id.iv_gmOne_c);
        mTvGmOneCContent = (TextView) view.findViewById(R.id.tv_gmOne_c_content);
        mIvGmOneD = (ImageView) view.findViewById(R.id.iv_gmOne_d);
        mTvGmOneDContent = (TextView) view.findViewById(R.id.tv_gmOne_d_content);
        mIvGmOneE = (ImageView) view.findViewById(R.id.iv_gmOne_e);
        mTvGmOneEContent = (TextView) view.findViewById(R.id.Tv_gmOne_e_content);
        mLlGmOneELayout = (LinearLayout) view.findViewById(R.id.ll_gmOne_e_layout);
        mLlGmOneELayout.setOnClickListener(this);
        mBtnGmoneSuerAnswer = (Button) view.findViewById(R.id.btn_gmone_suer_answer);
        mBtnGmoneSuerAnswer.setOnClickListener(this);
        mBtnGmoneSuerAnswer.setVisibility(View.GONE);
        mVGmOneLine = (View) view.findViewById(R.id.v_gmOne_line);

        mTvGmOneAnswerTitle = (TextView) view.findViewById(R.id.tv_gmOne_answer_title);
        mTvGmOneAnswerTitle.setOnClickListener(this);
        mTvGmOneAnswerContent = (TextView) view.findViewById(R.id.tv_gmOne_answer_content);
        mTvGmOneAnswerContent.setOnClickListener(this);
        mLlGmoneAnswerLayout = (LinearLayout) view.findViewById(R.id.ll_gmone_answer_layout);
        mLlGmoneAnswerLayout.setOnClickListener(this);
        mLlGmOneSelectLayout = (LinearLayout) view.findViewById(R.id.ll_gmOne_select_layout);
        mBtnBBuy = (Button) view.findViewById(R.id.btn_b_buy);
        mBtnBBuy.setOnClickListener(this);
        //购买布局
        mLiBResolveBuy = (LinearLayout) view.findViewById(R.id.li_b_resolve_buy);
        mTvGmTwoJiexiTitle = (TextView) view.findViewById(R.id.tv_gmTwo_jiexi_title);
        mTvGmTwoJiexiTitle.setOnClickListener(this);
        mTvGmTwoJiexiContent = (TextView) view.findViewById(R.id.tv_gmTwo_jiexi_content);
        mTvGmTwoJiexiContent.setOnClickListener(this);
        mTvGmTwoDifficultyTitle = (TextView) view.findViewById(R.id.tv_gmTwo_difficulty_title);
        mTvGmTwoDifficultyTitle.setOnClickListener(this);
        mIvGmTwoStarOne = (ImageView) view.findViewById(R.id.iv_gmTwo_star_one);
        mIvGmTwoStarOne.setOnClickListener(this);
        mIvGmTwoStarTwo = (ImageView) view.findViewById(R.id.iv_gmTwo_star_two);
        mIvGmTwoStarTwo.setOnClickListener(this);
        mIvGmTwoStarThree = (ImageView) view.findViewById(R.id.iv_gmTwo_star_three);
        mIvGmTwoStarThree.setOnClickListener(this);
        mIvGmTwoStarFour = (ImageView) view.findViewById(R.id.iv_gmTwo_star_four);
        mIvGmTwoStarFour.setOnClickListener(this);
        mIvGmTwoStarFive = (ImageView) view.findViewById(R.id.iv_gmTwo_star_five);
        mIvGmTwoStarFive.setOnClickListener(this);
        mTvGmTwoRightTitle = (TextView) view.findViewById(R.id.tv_gmTwo_right_title);
        mTvGmTwoRightContent = (TextView) view.findViewById(R.id.tv_gmTwo_right_content);
        mTvGmTwoCentreTitle = (TextView) view.findViewById(R.id.tv_gmTwo_centre_title);
        mTvGmTwoCentreTitle.setOnClickListener(this);
        mFlowlayoutGmTwo = (FlowLayout) view.findViewById(R.id.flowlayout_gmTwo);
        mFlowlayoutGmTwo.setOnClickListener(this);
        mVGmTwoLineTwo = (View) view.findViewById(R.id.v_gmTwo_line_two);
        mVGmTwoLineTwo.setOnClickListener(this);
        //解析布局
        mLlGmTwoAnalysisLayout = (LinearLayout) view.findViewById(R.id.ll_gmTwo_analysis_layout);
        mTvGmThreeNoteTitle = (TextView) view.findViewById(R.id.tv_gmThree_note_title);
        mTvGmThreeNoteTitle.setOnClickListener(this);
        mTvGmThreeNote = (TextView) view.findViewById(R.id.tv_gmThree_note);

        mVGmThreeLine = (View) view.findViewById(R.id.v_gmThree_line);
        mVGmGmEightOneLineTwo = (View) view.findViewById(R.id.view_gm_eight_one_linetwo);
        mVGmThreeLine.setOnClickListener(this);
        //笔记布局
        mLlGmthreeNoteLayout = (LinearLayout) view.findViewById(R.id.ll_gmthree_note_layout);
        mTvGmfourEvaluateTitle = (TextView) view.findViewById(R.id.tv_gmfour_evaluate_title);
        mTvGmfourEvaluateTitle.setOnClickListener(this);
        mTvGmfourNuber = (TextView) view.findViewById(R.id.tv_gmfour_nuber);
        mIvGmfourEvaluate = (ImageView) view.findViewById(R.id.iv_gmfour_evaluate);
        mIvGmfourEvaluate.setOnClickListener(this);
        mRlvGmfourContent = (RecyclerView) view.findViewById(R.id.rlv_gmfour_content);
        mRlGmfourLayout = (RelativeLayout) view.findViewById(R.id.rl_gmfour_layout);
        mRlGmfourLayout.setOnClickListener(this);
        mLlGmfourMoreData = (LinearLayout) view.findViewById(R.id.ll_gmfour_more_data);
        //评价解面
        mLlFourEvaluateLayout = (LinearLayout) view.findViewById(R.id.ll_four_evaluate_layout);
//        mSmsvLayout = (WeiNestedScrollView) view.findViewById(R.id.smsv_two_layout);
        mSmsvLayout = (SmartScrollView) view.findViewById(R.id.smsv_two_layout);
        mIvNetEmptyContent = (ImageView) view.findViewById(R.id.iv_net_empty_content);
        mIvNetEmptyContent.setOnClickListener(this);
        mLlGmoneALayout = (LinearLayout) view.findViewById(R.id.ll_gmone_a_layout);
        mLlGmoneALayout.setOnClickListener(this);
        mLlGmoneBLayout = (LinearLayout) view.findViewById(R.id.ll_gmone_b_layout);
        mLlGmoneBLayout.setOnClickListener(this);
        mLlGmoneCLayout = (LinearLayout) view.findViewById(R.id.ll_gmone_c_layout);
        mLlGmoneCLayout.setOnClickListener(this);
        mLlGmoneDLayout = (LinearLayout) view.findViewById(R.id.ll_gmone_d_layout);
        mLlGmoneDLayout.setOnClickListener(this);
        mViewGmtwoLine = (View) view.findViewById(R.id.view_gmtwo_line);
        mViewGmtwoLine.setOnClickListener(this);
        mGmRoolLayout = (LinearLayout) view.findViewById(R.id.gm_tworool_layout);
        mGmRoolLayout.setOnClickListener(this);
        mVGmtwoLineThree = (View) view.findViewById(R.id.v_gmtwo_line_three);
        mVGmtwoLineThree.setOnClickListener(this);

        mTvGmsixtextCollect = (TextView) view.findViewById(R.id.tv_gmsixtext_collect);
        mTvGmsixtextCollect.setOnClickListener(this);
        mIvGmsixtextMenu = (ImageView) view.findViewById(R.id.iv_gmsixtext_menu);
        mIvGmsixtextMenu.setOnClickListener(this);
        mTvGmsixtextQid = (TextView) view.findViewById(R.id.tv_gmsixtext_qid);
        mTvGmsixtextQid.setOnClickListener(this);
        mTvGmsixtextAllqid = (TextView) view.findViewById(R.id.tv_gmsixtext_allqid);
        mTvGmsixtextAllqid.setOnClickListener(this);
        mTvGmsixtextShare = (TextView) view.findViewById(R.id.tv_gmsixtext_share);
        mTvGmsixtextShare.setOnClickListener(this);
        mLiGmsixTextNavbar = (LinearLayout) view.findViewById(R.id.li_gmsix_text_navbar);
        mLiGmsixTextNavbar.setOnClickListener(this);
        mFlGmsixLayout = (FrameLayout) view.findViewById(R.id.fl_gmsix_layout);
        mFlGmsixLayout.setOnClickListener(this);
        //阅读题布局
        mRlvGmSevenAnswerLayout = (RelativeLayout) view.findViewById(R.id.rlv_gm_seven_answer_layout);
        mRlvGmSevenAnswerLayout.setOnClickListener(this);
        mBtnGmsevenAnswer = (Button) view.findViewById(R.id.btn_gmseven_answer);
        mBtnGmsevenAnswer.setOnClickListener(this);
        //简答题布局
        mBtnGmEightLookJiexi = (Button) view.findViewById(R.id.btn_gm_eight_look_jiexi);
        mBtnGmEightLookJiexi.setOnClickListener(this);
        mTvGmEightAnswerTitle = (TextView) view.findViewById(R.id.tv_gm_eight_answer_title);
        mTvGmEightAnswerTitle.setOnClickListener(this);
        mTvGmEightAnswerTitleContent = (TextView) view.findViewById(R.id.tv_gm_eight_answer_title_content);
        mTvGmEightAnswerTitleContent.setOnClickListener(this);
        mLlGmEightLookJiexi = (LinearLayout) view.findViewById(R.id.ll_gm_eight_look_jiexi);
        mLlGmeightJiandaLayout = (LinearLayout) view.findViewById(R.id.ll_gmeight_jianda_layout);
        mLlGmeightJiandaLayout.setOnClickListener(this);
        mTvGmLine = (TextView) view.findViewById(R.id.tv_gm_line);
        mTvGmEightOneLookJiexi = (TextView) view.findViewById(R.id.tv_gm_eight_one_look_jiexi);
        mTvGmEightOneLookJiexi.setOnClickListener(this);
        mIvGmEightOneYuyinImg = (ImageView) view.findViewById(R.id.iv_gm_eight_one_yuyin_img);
        mIvGmEightOneYuyinImg.setOnClickListener(this);
        mTvGmEightOneYuyinContent = (TextView) view.findViewById(R.id.tv_gm_eight_one_yuyin_content);
        mTvGmEightOneYuyinContent.setOnClickListener(this);
        mRlGmEightOneYuyin = (RelativeLayout) view.findViewById(R.id.rl_gm_eight_one_yuyin);

        mIvGmEightOneAnswerTitle = (ImageView) view.findViewById(R.id.iv_gm_eight_one_answer_title);
        mIvGmEightOneAnswerTitle.setOnClickListener(this);
        mTvGmEightOneAnswerTitleContent = (TextView) view.findViewById(R.id.tv_gm_eight_one_answer_title_content);
        mLlGmEightOneLookJiexi = (LinearLayout) view.findViewById(R.id.ll_gm_eight_one_look_jiexi);
        mLlGmeightOneJiandaLayout = (LinearLayout) view.findViewById(R.id.ll_gmeight_one_jianda_layout);
        mLlGmeightOneJiandaLayout.setOnClickListener(this);
        mEtEightOneInputContent = (ContentEditText) view.findViewById(R.id.et_eight_one_input_content);
        mTvGmZoneQuestionTitle = (TextView) view.findViewById(R.id.tv_gm_zone_question_title);
        mTvGmZoneQuestionTitle.setOnClickListener(this);
        mLlGmZoneQuestionTitle = (LinearLayout) view.findViewById(R.id.ll_gm_zone_question_title);
        mLlGmZoneQuestionTitle.setOnClickListener(this);
        mTvGmsevenTextContent = (TextView) view.findViewById(R.id.tv_gmseven_text_content);
        mTvGmsevenTextContent.setOnClickListener(this);
        mViewGmEightOneLine = (View) view.findViewById(R.id.view_gm_eight_one_line);
        mViewGmEightOneLine.setOnClickListener(this);
        mViewGmEightTwoLine = (View) view.findViewById(R.id.view_gm_eight_two_line);
        mViewGmEightTwoLine.setOnClickListener(this);
        mTvGmThreeNoteContent = (TextView) view.findViewById(R.id.tv_gm_three_note_content);
        mTvGmThreeNoteContent.setOnClickListener(this);
        mLlGmThreeNoteBg = (LinearLayout) view.findViewById(R.id.ll_gm_three_note_bg);
        mLlGmThreeNoteBg.setOnClickListener(this);
        mIvGmEightOneYuyinOneImg = (ImageView) view.findViewById(R.id.iv_gm_eight_one_yuyin_one_img);
        mIvGmEightOneYuyinOneImg.setOnClickListener(this);
        mTvGmEightOneInputContent = (TextView) view.findViewById(R.id.tv_gm_eight_one_input_content);
        mTvGmEightOneInputContent.setOnClickListener(this);
        mRlvGmEightOneGallery = (RecyclerView) view.findViewById(R.id.rlv_gm_eight_one_gallery);
        mRlvGmEightOneGallery.setOnClickListener(this);
        mRlGmEightOneJiexifu = (RelativeLayout) view.findViewById(R.id.rl_gm_eight_one_jiexifu);

        mTvGmEightOneInputTitle = (TextView) view.findViewById(R.id.tv_gm_eight_one_input_title);
        mTvGmEightOneInputTitle.setOnClickListener(this);
        mLlGmEightOneInputShow = (LinearLayout) view.findViewById(R.id.ll_gm_eight_one_input_show);
        mLlGmEightOneInputShow.setOnClickListener(this);
        mLlGmEightOneInputEdit = (LinearLayout) view.findViewById(R.id.ll_gm_eight_one_input_edit);
        mLlGmEightOneInputEdit.setOnClickListener(this);
        mViewGmEightOneLineOne = (View) view.findViewById(R.id.view_gm_eight_one_line_one);
        mViewGmEightOneLineOne.setOnClickListener(this);
        mTvGmEightOneNumberTitle = (TextView) view.findViewById(R.id.tv_gm_eight_one_number_title);
        mTvGmEightOneNumberTitle.setOnClickListener(this);
        mTvGmEightOneNumberTitleContent = (TextView) view.findViewById(R.id.tv_gm_eight_one_number_title_content);
        mTvGmEightOneNumberTitleContent.setOnClickListener(this);
        mBtnGmEightOneAddEvalues = (Button) view.findViewById(R.id.btn_gm_eight_one_addEvalues);
        mBtnGmEightOneAddEvalues.setOnClickListener(this);
        mTvGmEihgtOneZone = (TextView) view.findViewById(R.id.tv_gm_eihgt_one_zone);
        mTvGmEihgtOneZone.setOnClickListener(this);
        mTvGmEightOneSix = (TextView) view.findViewById(R.id.tv_gm_eight_one_six);
        mTvGmEightOneSix.setOnClickListener(this);
        mSbGmEightOne = (SeekBar) view.findViewById(R.id.sb_gm_eight_one);
        mSbGmEightOne.setOnClickListener(this);
        mLlGmEightOneEvlauesLayout = (LinearLayout) view.findViewById(R.id.ll_gm_eight_one_evlaues_layout);

        mIvGmZoneQuestintype = (ImageView) view.findViewById(R.id.iv_gm_zone_questintype);
        mIvGmZoneQuestintype.setOnClickListener(this);
        mSmsvTwoLayout = (SmartScrollView) view.findViewById(R.id.smsv_two_layout);
        mSmsvTwoLayout.setOnClickListener(this);
        mIvGmZoneQuestionImg = (ImageView) view.findViewById(R.id.iv_gm_zone_question_img);
        mIvGmZoneQuestionImg.setOnClickListener(this);
        mIvGmTwoAnalysisImg = (ImageView) view.findViewById(R.id.iv_gm_two_analysis_img);
        mIvGmTwoAnalysisImg.setOnClickListener(this);
    }

    private void initUserBuy() {
        mGmEvaluePresenter = new GmEvaluePresenter();
        mGmEvaluePresenter.initModelView(new GmEvalueModel(), this);
        UserInfomDbHelp help = UserInfomDbHelp.get_Instance(mContext);
        UserInfomSqliteVo userInfomVo = help.findUserInfomVo();
        DataMessageVo vo = DataMessageVo.get_Instance();
        String skillBuy = vo.getSkillBuy();
        String caseBuy = vo.getCaseBuy();
        String collorBuy = vo.getCollorBuy();
        if (mCouresid.equals("1")) {
            if (!StringUtil.isEmpty(userInfomVo.getSkillbook()) && userInfomVo.getSkillbook().equals(skillBuy)) {
                mUserBuy = true;
            }
        } else if (mCouresid.equals("2")) {
            if (!StringUtil.isEmpty(userInfomVo.getSkillbook()) && userInfomVo.getColligatebook().equals(collorBuy)) {
                mUserBuy = true;
            }

        } else if (mCouresid.equals("3")) {
            if (!StringUtil.isEmpty(userInfomVo.getSkillbook()) && userInfomVo.getCasebook().equals(caseBuy))
                mUserBuy = true;
        }
    }

    private void initShowView() {

        //简答布局与选项布局有冲突
        if (mCaseCardVo.getMaintype() == 1) {//主题
            //阅读理解头
            mLlGmtitleSelectLayout.setVisibility(View.GONE);
            //阅读理解
            mRlvGmSevenAnswerLayout.setVisibility(View.VISIBLE);
            //简答布局输入框
            mLlGmeightOneJiandaLayout.setVisibility(View.GONE);
            //答题按钮
            mBtnGmsevenAnswer.setVisibility(View.VISIBLE);
            //选项布局
            mLlGmOneSelectLayout.setVisibility(View.GONE);

        } else if (mCaseCardVo.getMaintype() == 2) {//副题
            //阅读理解头
            mLlGmtitleSelectLayout.setVisibility(View.VISIBLE);
            if (mQuestionSqliteVo.getQuestiontype() == 2 || mQuestionSqliteVo.getQuestiontype() == 3) {
                //选项布局
                mLlGmOneSelectLayout.setVisibility(View.VISIBLE);
                //简答布局输入框
                mLlGmeightOneJiandaLayout.setVisibility(View.GONE);
            } else if (mQuestionSqliteVo.getQuestiontype() == 4) {
                //阅读理解
                mRlvGmSevenAnswerLayout.setVisibility(View.GONE);
                //简答布局输入框
                mLlGmeightOneJiandaLayout.setVisibility(View.VISIBLE);
                //选项布局
                mLlGmOneSelectLayout.setVisibility(View.GONE);
            }
            //阅读理解
            mRlvGmSevenAnswerLayout.setVisibility(View.GONE);
            //答题按钮
            mBtnGmsevenAnswer.setVisibility(View.GONE);
        }
        //简答布局
        mLlGmeightJiandaLayout.setVisibility(View.GONE);
        //评价布局
        mLlFourEvaluateLayout.setVisibility(View.GONE);
        //解析布局
        mLlGmTwoAnalysisLayout.setVisibility(View.GONE);
        //笔记布局
        mLlGmthreeNoteLayout.setVisibility(View.GONE);


    }

    /**
     * 订阅者
     *
     * @return
     */
    @Override
    public Subsciber subsciber() {
        return new Subsciber();
    }

    /**
     * 具体观察者
     * 用于更新布局颜色
     * todo 观察者
     */
    @Override
    public void refresh() {
        ShowSelectImg();
        setColor();
        setImgBg();
        setFontSize();
        if (mCaseExamtActivity != null) {
            mCaseExamtActivity.changerColor(mGmReadColorManger);
            mCaseExamtActivity.changerCllect();
            mBtnGmsevenAnswer.setText(!mCaseExamtActivity.getmEvalueteAble() ? getStrWithId(R.string.answe_one) : getString(R.string.self_evaluation));
            if (mCaseExamtActivity.getmEvalueteAble()) {
                doUserSelectEvaluesView(mCaseExamtActivity.getFuDataLists());
            }
        }
        doMainImgEvent();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initDoEvent();
            }
        }, 300);
    }

    private void doMainImgEvent() {
        if (mCaseExamtActivity != null) {
            if (!mCaseExamtActivity.getMainQuesiton()) {
                mIvGmZoneQuestintype.setVisibility(View.VISIBLE);
            } else {
                mIvGmZoneQuestintype.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 初始化用户已做数据
     */
    private void initDoEvent() {
        DoBankSqliteVo vo = null;
        if (getActivity() instanceof CaseExamtActivity) {
            mCaseExamtActivity = (CaseExamtActivity) getActivity();
            vo = mCaseExamtActivity.queryUserData(mQuestionSqliteVo.getQuestion_id());
            if (mCaseExamtActivity.submitAble()) {
                initTitleRed();
            }
        }
        //回现数据
        if (doUserLookJiXi()) return;
        if (vo == null) return;
        if (vo.getIsDo() == 0) return;
        if (vo.getQuestiontype() == 2) {//单选
            if (vo.getSelectA() == 1) {
                doRadioItemBgEvent(true, false, false, false, false);
                doRightErrorBg(mSelectA, 2);
            } else if (vo.getSelectB() == 1) {
                doRadioItemBgEvent(false, true, false, false, false);
                doRightErrorBg(mSelectB, 2);
            } else if (vo.getSelectC() == 1) {
                doRadioItemBgEvent(false, false, true, false, false);
                doRightErrorBg(mSelectC, 2);
            } else if (vo.getSelectD() == 1) {
                doRadioItemBgEvent(false, false, false, true, false);
                doRightErrorBg(mSelectD, 2);
            }
            setSelectABCDEClick(!mCaseExamtActivity.submitAble());
            doRadioEventView();
            return;
        }
        if (vo.getQuestiontype() == 3) {//多选
            if (vo.getSelectA() == 1) {
                mSelectMorItemA = mSelectA;
            }
            if (vo.getSelectB() == 1) {
                mSelectMorItemB = mSelectB;
            }
            if (vo.getSelectC() == 1) {
                mSelectMorItemC = mSelectC;
            }
            if (vo.getSelectD() == 1) {
                mSelectMorItemD = mSelectD;
            }
            if (vo.getSelectE() == 1) {
                mSelectMorItemE = mSelectE;
            }
            mDuoXunaSure = true;
//            mBtnGmoneSuerAnswer.setVisibility(mCaseExamtActivity.submitAble() ? View.GONE : View.VISIBLE);
            setSelectABCDEClick(!mCaseExamtActivity.submitAble());
            if (mCaseExamtActivity.submitAble()) {
                doSelectMoreEvent();
            } else {
                doBackNowMoreEvent(vo);
            }
            doRadioEventView();
        } else if (vo.getQuestiontype() == 4) {
            if (vo.getIsAnalySis() == 1) {
                if (vo.getIsDo() == 1) {
                    if (mCaseExamtActivity.submitAble()) {
                        if (!StringUtil.isEmpty(vo.getAnalysis()))
                            mTvGmEightOneInputContent.setText(vo.getAnalysis());
                        showInputView(false);
                        return;
                    } else {
                        showInputView(true);
                        if (!StringUtil.isEmpty(vo.getAnalysis()))
                            mEtEightOneInputContent.setText(vo.getAnalysis());
                    }
                    if (vo.getIslook() == 1) {//是否查看你解析
                        doJieXiView(false, true, true);
                    } else {
                        if (mCaseExamtActivity.getmEvalueteAble()) {//是否自评
                            doJieXiView(false, true, true);
                        } else
                            doJieXiView(true, false, false);
                    }
                }
            }
        }
//        initTitleRed();
    }

    private void initTitleRed() {
        if (StringUtil.isEmpty(mQuestionSqliteVo.getKeywordsString())) {
            return;
        }
        final int width = mIvGmZoneQuestintype.getLayoutParams().width + 20;
        Spanned spanned = StringUtil.repaceExamStr(mQuestionSqliteVo.getQuestionString(),
                mQuestionSqliteVo.getKeywordsString(), null, width);
        mTvGmZoneQuestionTitle.setText(spanned);
    }

    //用户查看解析
    private boolean doUserLookJiXi() {
        //处理用户查看解析
        if (getActivity() instanceof CaseExamtActivity){
            mCaseExamtActivity= (CaseExamtActivity) getActivity();
        }
        if (mCaseExamtActivity!=null&&mCaseExamtActivity.submitAble()) {
            if (mQuestionSqliteVo.getQuestiontype() == 2) {//单选
                doRightErrorBg(mQuestionSqliteVo.getChoice_answer(), 2);
                setSelectABCDEClick(!mCaseExamtActivity.submitAble());
                doRadioEventView();
            } else if (mQuestionSqliteVo.getQuestiontype() == 3) {//多选
                List<String> list = mGmTextUtil.getAnswerKeyList(mQuestionSqliteVo.getChoice_answer());
                if (list == null || list.isEmpty()) return true;
                //处理正确选择项
                for (int i = 0; i < list.size(); i++) {
                    String s = list.get(i);
                    if (s.equalsIgnoreCase(mSelectA)) {
                        mSelectMorItemA = mSelectA;
                    } else if (s.equalsIgnoreCase(mSelectB)) {
                        mSelectMorItemB = mSelectB;
                    } else if (s.equalsIgnoreCase(mSelectC)) {
                        mSelectMorItemC = mSelectC;
                    } else if (s.equalsIgnoreCase(mSelectD)) {
                        mSelectMorItemD = mSelectD;
                    } else if (s.equalsIgnoreCase(mSelectE)) {
                        mSelectMorItemE = mSelectE;
                    }
                }
                mDuoXunaSure = true;
//                mBtnGmoneSuerAnswer.setVisibility(mMockExamActivity.getSubmitAble() ? View.GONE : View.VISIBLE);
                setSelectABCDEClick(!mCaseExamtActivity.submitAble());
                doSelectMoreEvent();
                doRadioEventView();
            } else if (mQuestionSqliteVo.getQuestiontype() == 4) {
                showInputView(false);
                doJieXiView(false, true, true);
            }
        }

        return false;
    }

    /**
     * 显示简答输入框
     *
     * @param show
     */
    private void showInputView(boolean show) {
        //用户显示答案布局
        mLlGmEightOneInputShow.setVisibility(show ? View.GONE : View.VISIBLE);
        //用户输入布局
        mLlGmEightOneInputEdit.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void setFontSize() {
        mTvGmOneAContent.setTextSize(mGmFontSizeUtil.getFontSize());
        mTvGmOneBContent.setTextSize(mGmFontSizeUtil.getFontSize());
        mTvGmOneCContent.setTextSize(mGmFontSizeUtil.getFontSize());
        mTvGmOneDContent.setTextSize(mGmFontSizeUtil.getFontSize());
        mTvGmOneEContent.setTextSize(mGmFontSizeUtil.getFontSize());
        mBtnGmoneSuerAnswer.setTextSize(mGmFontSizeUtil.getFontSize());
        mTvGmOneAnswerTitle.setTextSize(mGmFontSizeUtil.getFontSize());
        mTvGmOneAnswerContent.setTextSize(mGmFontSizeUtil.getFontSize());
        mTvGmTwoJiexiTitle.setTextSize(mGmFontSizeUtil.getFontSize());
        mTvGmTwoJiexiContent.setTextSize(mGmFontSizeUtil.getFontSize());
        mTvGmEightAnswerTitleContent.setTextSize(mGmFontSizeUtil.getFontSize());
        mTvGmEightAnswerTitle.setTextSize(mGmFontSizeUtil.getFontSize());
        mTvGmEightOneAnswerTitleContent.setTextSize(mGmFontSizeUtil.getFontSize());
        mEtEightOneInputContent.setTextSize(mGmFontSizeUtil.getFontSize());
        mTvGmEightOneInputTitle.setTextSize(mGmFontSizeUtil.getFontSize());
        mTvGmEightOneInputContent.setTextSize(mGmFontSizeUtil.getFontSize());
        mTvGmZoneQuestionTitle.setTextSize(mGmFontSizeUtil.getFontSize());

    }

    //根据题型设置选项图标
    private void ShowSelectImg() {
        switch (mQuestionSqliteVo.getQuestiontype()) {
            case 2://单选
                mGmImgManageUtil.setStatus(GmSelectImgManageUtil.Stauts.DayRadio);
                break;
            case 3://多选
                mGmImgManageUtil.setStatus(GmSelectImgManageUtil.Stauts.Daychoice);
                break;
        }
    }

    //设置图片背景
    private void setImgBg() {
        mIvGmOneA.setImageDrawable(mGmImgManageUtil.getA());
        mIvGmOneB.setImageDrawable(mGmImgManageUtil.getB());
        mIvGmOneC.setImageDrawable(mGmImgManageUtil.getC());
        mIvGmOneD.setImageDrawable(mGmImgManageUtil.getD());
        mIvGmOneE.setImageDrawable(mGmImgManageUtil.getE());
        mIvGmfourEvaluate.setImageDrawable(mGmImgManageUtil.getP());

    }

    private void setColor() {
        mIvGmEightOneYuyinImg.setImageDrawable(mGmReadColorManger.getVoice());
        mIvGmEightOneYuyinOneImg.setImageDrawable(mGmReadColorManger.getInput());
        mTvGmTitleTextLast.setBackgroundDrawable(mGmReadColorManger.getTag());
        mLlGmThreeNoteBg.setBackgroundDrawable(mGmReadColorManger.getNote());

        mGmRoolLayout.setBackgroundColor(mGmReadColorManger.getmLayoutBgColor());
        //选择布局
        mLlGmOneSelectLayout.setBackgroundColor(mGmReadColorManger.getmLayoutBgColor());
        //题干背景
        mLlGmZoneQuestionTitle.setBackgroundColor(mGmReadColorManger.getmLayoutBgColor());
        //购买布局
//        mLiBResolveBuy.setBackgroundColor(mGmReadColorManger.getmLayoutBgColor());
        //解析布局
        mLlGmTwoAnalysisLayout.setBackgroundColor(mGmReadColorManger.getmLayoutBgColor());
        //笔记布局
        mLlGmthreeNoteLayout.setBackgroundColor(mGmReadColorManger.getmLayoutBgColor());
        //评论布局
        mLlFourEvaluateLayout.setBackgroundColor(mGmReadColorManger.getmLayoutBgColor());
        //简答布局
        mRlvGmSevenAnswerLayout.setBackgroundColor(mGmReadColorManger.getmLayoutBgColor());

        mTvGmOneAContent.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mTvGmOneBContent.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mTvGmOneCContent.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mTvGmOneDContent.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mTvGmOneEContent.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mTvGmOneAnswerContent.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mTvGmOneAnswerTitle.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mTvGmTwoJiexiTitle.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mTvGmTwoJiexiContent.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mTvGmTwoDifficultyTitle.setTextColor(mGmReadColorManger.getmTextFuColor());
        mTvGmTwoRightTitle.setTextColor(mGmReadColorManger.getmTextFuColor());
        mTvGmTwoRightContent.setTextColor(mGmReadColorManger.getmTextRedColor());
        mVGmTwoLineTwo.setBackgroundColor(mGmReadColorManger.getmCutLineColor());
        mViewGmtwoLine.setBackgroundColor(mGmReadColorManger.getmCutLineColor());
        mVGmThreeLine.setBackgroundColor(mGmReadColorManger.getmCutLineColor());
        mVGmGmEightOneLineTwo.setBackgroundColor(mGmReadColorManger.getmCutLineColor());
        mTvGmfourEvaluateTitle.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mTvGmfourNuber.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mVGmtwoLineThree.setBackgroundColor(mGmReadColorManger.getmCutLineColor());
        mVGmOneLine.setBackgroundColor(mGmReadColorManger.getmCutLineColor());
        mTvGmEightAnswerTitle.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mTvGmEightAnswerTitleContent.setTextColor(mGmReadColorManger.getmTextFuColor());
        mBtnGmEightLookJiexi.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mLlGmeightJiandaLayout.setBackgroundColor(mGmReadColorManger.getmLayoutBgColor());
        //阅读理解头
        mLlGmtitleSelectLayout.setBackgroundColor(mGmReadColorManger.getmLayoutBgColor());
        mTvGmTitleFirst.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mTvGmLine.setTextColor(mGmReadColorManger.getmTextFuColor());
        mTvGmTitleAllnumber.setTextColor(mGmReadColorManger.getmTextFuColor());
        mTvGmTitleTextLast.setTextColor(mGmReadColorManger.getmTextFuColor());
        //阅读解析
        mLlGmEightOneLookJiexi.setBackgroundColor(mGmReadColorManger.getmLayoutBgColor());
        mEtEightOneInputContent.setTextColor(mGmReadColorManger.getmTextFuColor());
        mEtEightOneInputContent.setHintTextColor(mGmReadColorManger.getmTextFuColor());
        mTvGmEightOneInputTitle.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mTvGmEightOneInputContent.setTextColor(mGmReadColorManger.getmTextFuColor());
        mTvGmEightOneLookJiexi.setTextColor(mGmReadColorManger.getmTextFuColor());
        mTvGmEightOneYuyinContent.setTextColor(mGmReadColorManger.getmTextFuColor());
        mLlGmEightOneLookJiexi.setBackgroundColor(mGmReadColorManger.getmLayoutBgColor());
        mTvGmEightOneAnswerTitleContent.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mTvGmZoneQuestionTitle.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mLlGmZoneQuestionTitle.setBackgroundColor(mGmReadColorManger.getmLayoutBgColor());
        mViewGmEightOneLine.setBackgroundColor(mGmReadColorManger.getmCutLineColor());
        mViewGmEightTwoLine.setBackgroundColor(mGmReadColorManger.getmCutLineColor());
        mViewGmEightOneLineOne.setBackgroundColor(mGmReadColorManger.getmCutLineColor());
        mLlGmEightOneInputShow.setBackgroundColor(mGmReadColorManger.getmLayoutBgColor());
        mLlGmEightOneEvlauesLayout.setBackgroundColor(mGmReadColorManger.getmLayoutBgColor());
        mLlGmEightOneInputEdit.setBackgroundColor(mGmReadColorManger.getmLayoutBgColor());
        mTvGmEightOneNumberTitle.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mTvGmEightOneNumberTitleContent.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mTvGmEihgtOneZone.setTextColor(mGmReadColorManger.getmTextFuColor());
        mTvGmEightOneSix.setTextColor(mGmReadColorManger.getmTextFuColor());
    }

    private void readDataBindView() {
        String question = mQuestionSqliteVo.getQuestionString();
        mQuestionType = mQuestionSqliteVo.getQuestiontype();
        //题干信息
//        mTvGmZoneQuestionTitle.setText(getString(question));
        mIvGmZoneQuestintype.setImageDrawable(getQuestionType());
       /* String empty = "            ";
        String concat = empty.concat(question);
        mTvGmZoneQuestionTitle.setText(concat);*/
        mTvGmZoneQuestionTitle.setText(doMainEvent(question));
        mTvGmOneAContent.setText(mQuestionSqliteVo.getOption_a());
        mTvGmOneBContent.setText(mQuestionSqliteVo.getOption_b());
        mTvGmOneCContent.setText(mQuestionSqliteVo.getOption_c());
        mTvGmOneDContent.setText(mQuestionSqliteVo.getOption_d());
        mTvGmOneEContent.setText(mQuestionSqliteVo.getOption_e());
        mTvGmOneAnswerContent.setText(mQuestionSqliteVo.getChoice_answer());
//        mTvGmTwoJiexiContent.setText(mQuestionSqliteVo.getExplainedStr());
        mTvGmTwoJiexiContent.setText(mQuestionSqliteVo.getExplainString());
        setStarNumber(mQuestionSqliteVo.getDifficulty());
        mTvGmTwoRightContent.setText(String.valueOf(mQuestionSqliteVo.getRight_rate()).concat("%"));

        mTvGmEightAnswerTitleContent.setText(mQuestionSqliteVo.getExplainString());
        //阅读题的小题类型
        mGmTextUtil.setQuestionType(mTvGmTitleTextLast, mQuestionSqliteVo.getQuestiontype());
        //简答新解析
        mTvGmEightOneAnswerTitleContent.setText(mQuestionSqliteVo.getExplainString());

        if (mCaseExamtActivity != null) {
            mTvGmTitleFirst.setText(String.valueOf(mPostion + 1));
            mTvGmTitleAllnumber.setText(String.valueOf(mCaseExamtActivity.getFuPostionList()));
        }
        if (StringUtil.isEmpty(mQuestionSqliteVo.getQuestionimg())) {
            mIvGmZoneQuestionImg.setVisibility(View.GONE);
        } else {
            MyAppliction.getInstance().displayImages(mIvGmZoneQuestionImg, mQuestionSqliteVo.getQuestionimg(), false);
        }
        if (StringUtil.isEmpty(mQuestionSqliteVo.getExplainimg())) {
            mIvGmTwoAnalysisImg.setVisibility(View.GONE);
        } else {
            MyAppliction.getInstance().displayImages(mIvGmTwoAnalysisImg, mQuestionSqliteVo.getExplainimg(), false);
        }
        bindKaoDian();


    }

    String ab = "\t";
    String d = "\t";
    int cound = 0;

    private String doMainEvent(String question) {
        final int width = mIvGmZoneQuestintype.getLayoutParams().width + 20;
        if (mCaseExamtActivity != null) {
            if (!mCaseExamtActivity.getMainQuesiton()) {
//                String empty = "               ";
                cound = 20;
                String getwith = getwith(20, width);
                String concat = getwith.concat(question);
                cound = 0;
                ab = "\t";
//                String concat = DataMessageVo.empty.concat(question);
                return concat;
            }
        }
        return question;
    }

    private String getwith(int wd, int width) {
        if (wd == 0 || width == 0) return ab;
        if (cound < width) {
            ab = ab.concat(d);
            cound += wd;
            getwith(wd, width);
        }
        return ab + ":";
    }

    /**
     * 绑定考点
     */
    private void bindKaoDian() {
        mFlowlayoutGmTwo.removeAllViews();
        String keywordStr = mQuestionSqliteVo.getKeywordsString();
        if (StringUtil.isEmpty(keywordStr)) return;
        ArrayList<String> list = mGmTextUtil.getKeyWords(keywordStr);
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                String key = list.get(i);
                View itemvo = LayoutInflater.from(mContext).inflate(R.layout.item_gmtext_key_layout, mFlowlayoutGmTwo, false);
                TextView tv = (TextView) itemvo.findViewById(R.id.tv_gm_keywords_content);
                tv.setTextColor(mGmReadColorManger.getmTextFuColor());
                tv.setText(key);
                if (mGmReadColorManger != null) {
                    tv.setTextColor(mGmReadColorManger.getmTextFuColor());
                }
                mFlowlayoutGmTwo.addView(itemvo);
            }
        }
    }

    /**
     * 富文本
     *
     * @param textbar
     * @return
     */
    private SpannableString getString(String textbar) {


        if (mCaseExamtActivity != null) {
            if (!mCaseExamtActivity.getMainQuesiton()) {
                return SpannableString.valueOf(textbar);
            }
        }
        if (mQuestionSqliteVo.getQuestiontype() == 4 || mQuestionSqliteVo.getQuestiontype() == 5) {
            return SpannableString.valueOf(textbar);
        }
        String img = "图片";
        String concat = img.concat(textbar);
        SpannableString ss = new SpannableString(concat);
        Drawable d = getQuestionType();
        if (d == null) {
            return SpannableString.valueOf(textbar);
        }
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d);
        ss.setSpan(span, 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return ss;
    }

    //获取题干信息
    private Drawable getQuestionType() {
        Drawable type = null;
        boolean isShow = false;
        switch (mQuestionSqliteVo.getQuestiontype()) {
            case 2:
                type = mGmImgManageUtil.getDrawable(R.drawable.qb_danxuan);
                isShow = false;
                mLlGmOneELayout.setVisibility(View.GONE);

                break;
            case 3:
                type = mGmImgManageUtil.getDrawable(R.drawable.qb_duoxuan);
                isShow = true;
                mLlGmOneELayout.setVisibility(View.VISIBLE);
                break;
            case 4:
                type = mGmImgManageUtil.getDrawable(R.drawable.qb_jianda);
                isShow = false;
                break;
        }
//        mBtnGmoneSuerAnswer.setVisibility(isShow ? View.VISIBLE : View.GONE);
        return type;
    }

    private void registerOberovce() {
        //初始化颜色
        mGmReadColorManger.setGmBgColor(GmReadColorManger.DAYTIME);
        //初始化用字体
        if (StringUtil.isEmpty(mFoneSize)) {
            mGmFontSizeUtil.status(GmFontSizeUtil.fontStatus.font16);
        } else if (mFoneSize.equals(DataMessageVo.FONT_ONE)) {
            mGmFontSizeUtil.status(GmFontSizeUtil.fontStatus.font14);
        } else if (mFoneSize.equals(DataMessageVo.FONT_TWO)) {
            mGmFontSizeUtil.status(GmFontSizeUtil.fontStatus.font16);
        } else if (mFoneSize.equals(DataMessageVo.FONT_THREE)) {
            mGmFontSizeUtil.status(GmFontSizeUtil.fontStatus.font18);
        } else if (mFoneSize.equals(DataMessageVo.FONT_FOUR)) {
            mGmFontSizeUtil.status(GmFontSizeUtil.fontStatus.font20);
        } else if (mFoneSize.equals(DataMessageVo.FONT_FIVE)) {
            mGmFontSizeUtil.status(GmFontSizeUtil.fontStatus.font24);
        }
        //初始化模式
        if (StringUtil.isEmpty(mShowDayOrNight)) {
            mGmReadColorManger.setGmBgColor(GmReadColorManger.DAYTIME);
        } else if (mShowDayOrNight.equals(DataMessageVo.PATTERN_ONE)) {
            mGmReadColorManger.setGmBgColor(GmReadColorManger.DAYTIME);
        } else if (mShowDayOrNight.equals(DataMessageVo.PATTERN_TWO)) {
            mGmReadColorManger.setGmBgColor(GmReadColorManger.NIGHT);
        } else if (mShowDayOrNight.equals(DataMessageVo.PATTERN_THREE)) {
            mGmReadColorManger.setGmBgColor(GmReadColorManger.EYE);
        }


        //初始化显示按钮

        mSubsciber = new GmReadThreeFragment().subsciber();
        mSubsciber.attch(this);
        mSubsciber.notifyChanger();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_gmone_suer_answer:
                mDuoXunaSure = true;
//                mBtnGmoneSuerAnswer.setVisibility(mCaseExamtActivity.submitAble() ? View.GONE : View.VISIBLE);
                setSelectABCDEClick(!mCaseExamtActivity.submitAble());
                doSelectMoreEvent();
                doRadioEventView();
                doSaveMoreEvent();
                break;
            case R.id.btn_b_buy:
                Intent intent1 = BankBuyActivity.newInstance(mContext, String.valueOf(mQuestionSqliteVo.getCourseid()), "");
                startActivity(intent1);
                break;
            case R.id.ll_gmone_a_layout://a
                if (mQuestionType == 2) {//处理单选
                    doRadioItemBgEvent(true, false, false, false, false);
                    setSelectABCDEClick(!mCaseExamtActivity.submitAble());
                    doRadioEventA();
                    doRightErrorBg(mSelectA, 2);
                    doRadioEventView();
                } else if (mQuestionType == 3) {//多选
                    mSelectMorItemA = mSelectA;
                    if (mSelectMorisClickA) {
                        mSelectMorisClickA = false;
                        mSelectMorItemA = null;
                    } else {
                        mSelectMorisClickA = true;
                    }
//                    setSureKeyBg();
                    setSelectMoreItemBG(mSelectA, mSelectMorisClickA);
                    doSaveMoreEvent();
                } else if (mQuestionType == 4) {//简答
                }

                break;
            case R.id.ll_gmone_b_layout://b
                if (mQuestionType == 2) {//处理单选
                    doRadioItemBgEvent(false, true, false, false, false);
                    setSelectABCDEClick(!mCaseExamtActivity.submitAble());
                    doRadioEventB();
                    doRightErrorBg(mSelectB, 2);
                    doRadioEventView();
                } else if (mQuestionType == 3) {//多选
                    mSelectMorItemB = mSelectB;
                    if (mSelectMorisClickB) {
                        mSelectMorisClickB = false;
                        mSelectMorItemB = null;
                    } else {
                        mSelectMorisClickB = true;
                    }
//                    setSureKeyBg();
                    setSelectMoreItemBG(mSelectB, mSelectMorisClickB);
                    doSaveMoreEvent();
                } else if (mQuestionType == 4) {//简答
                }

                break;
            case R.id.ll_gmone_c_layout://c
                if (mQuestionType == 2) {//处理单选
                    doRadioItemBgEvent(false, false, true, false, false);
                    setSelectABCDEClick(!mCaseExamtActivity.submitAble());
                    doRadioEventC();
                    doRightErrorBg(mSelectC, 2);
                    doRadioEventView();
                } else if (mQuestionType == 3) {//多选
                    mSelectMorItemC = mSelectC;
                    if (mSelectMorisClickC) {
                        mSelectMorisClickC = false;
                        mSelectMorItemC = null;
                    } else {
                        mSelectMorisClickC = true;
                    }
//                    setSureKeyBg();
                    setSelectMoreItemBG(mSelectC, mSelectMorisClickC);
                    doSaveMoreEvent();
                } else if (mQuestionType == 4) {//简答
                }
                break;
            case R.id.ll_gmone_d_layout://d
                if (mQuestionType == 2) {//处理单选
                    doRadioItemBgEvent(false, false, false, true, false);
                    setSelectABCDEClick(!mCaseExamtActivity.submitAble());
                    doRadioEventD();
                    doRightErrorBg(mSelectD, 2);
                    doRadioEventView();
                } else if (mQuestionType == 3) {//多选
                    mSelectMorItemD = mSelectD;
                    if (mSelectMorisClickD) {
                        mSelectMorisClickD = false;
                        mSelectMorItemD = null;
                    } else {
                        mSelectMorisClickD = true;
                    }
//                    setSureKeyBg();
                    setSelectMoreItemBG(mSelectD, mSelectMorisClickD);
                    doSaveMoreEvent();
                } else if (mQuestionType == 4) {//简答
                }
                break;
            case R.id.ll_gmOne_e_layout://e
                if (mQuestionType == 2) {//处理单选
                    doRadioItemBgEvent(false, false, false, false, true);
                    setSelectABCDEClick(!mCaseExamtActivity.submitAble());
                    doRadioEventE();
                    doRightErrorBg(mSelectE, 2);
                    doRadioEventView();
                } else if (mQuestionType == 3) {//多选
                    mSelectMorItemE = mSelectE;
                    if (mSelectMorisClickE) {
                        mSelectMorisClickE = false;
                        mSelectMorItemE = null;
                    } else {
                        mSelectMorisClickE = true;
                    }
//                    setSureKeyBg();
                    setSelectMoreItemBG(mSelectE, mSelectMorisClickE);
                    doSaveMoreEvent();
                } else if (mQuestionType == 4) {//简答
                }

                break;
            case R.id.btn_gmseven_answer://答题按钮
                if (mCaseExamtActivity != null) {
                    if (mCaseCardVo.getList() != null && !mCaseCardVo.getList().isEmpty()) {
                        mCaseExamtActivity.doEventHine(true);
                        mCaseExamtActivity.changerSmallQuestion(mPostion);
                    } else {
                        T.showToast(mContext, "暂无小题");
                    }
                }
                break;
            case R.id.btn_gm_eight_look_jiexi://用户点击插看解析(原来)
//                doLookJieXiEvent();
                break;
            case R.id.iv_gmTitle_mulu://目录
                if (mCaseExamtActivity != null) {
                    mCaseExamtActivity.changerMainQuesiton();
                    mCaseExamtActivity.doEventHine(false);
                }

                break;
            case R.id.tv_gm_eight_one_look_jiexi://查看解析
                doLookJieXiEvent();
                doJieXiView(false, true, false);
                break;
            case R.id.ll_gm_three_note_bg://笔记
                Intent intent = NoteMakeActivity.start_Intent(mContext, mQuestionSqliteVo.getQuestion_id(),
                        mQuestionSqliteVo.getQuestion_id(), mQuestionSqliteVo.getCourseid(),
                        "", mQuestionSqliteVo.getQuestionString(), mQuestionSqliteVo.getKeywordsString(), 1);
                mContext.startActivity(intent);
                break;
            case R.id.btn_gm_eight_one_addEvalues://评分
                if (mCaseExamtActivity != null) {
                    // TODO: 2019.01.07  更新用户评分
                    doEvaluesEvent();
                    mCaseExamtActivity.doRightGo(mPostion + 1);
                }
                break;
            case R.id.iv_gm_eight_one_yuyin_img://语音输入
                if (mCaseExamtActivity != null) {
                    mCaseExamtActivity.videoInput(mEtEightOneInputContent);
                }
                break;
            case R.id.iv_gm_zone_question_img://问题
                Intent intent2 = ImagerActivity.start_Intent(mContext, mQuestionSqliteVo.getQuestionimg(), 2);
                startActivity(intent2);
                break;
            case R.id.iv_gm_two_analysis_img://解析
                Intent intent3 = ImagerActivity.start_Intent(mContext, mQuestionSqliteVo.getExplainimg(), 2);
                startActivity(intent3);
                break;
        }
    }

    //处理评分事件
    private void doEvaluesEvent() {
        DoBankSqliteVo sqliteVo = new DoBankSqliteVo();
        sqliteVo.setQuestion_id(mQuestionSqliteVo.getQuestion_id());
        sqliteVo.setCourseid(mQuestionSqliteVo.getCourseid());
        sqliteVo.setIsDo(0);
        sqliteVo.setChapterid(mQuestionSqliteVo.getChapter_id());
        sqliteVo.setChild_id(mQuestionSqliteVo.getQuestion_id());
        String number = mTvGmEightOneNumberTitle.getText().toString();
        sqliteVo.setMos(number);
        sqliteVo.setIsmos(1);
        sqliteVo.setIsright(4);
        sqliteVo.setParent_id(mQuestionSqliteVo.getParent_id());
        sqliteVo.setQuestiontype(mQuestionSqliteVo.getQuestiontype());
        mCaseExamtActivity.upDataEvalues(sqliteVo);

    }

    /**
     * 处理用户自评界面
     *
     * @param fuPostionList
     */
    private void doUserSelectEvaluesView(List<CaseCardVo> fuPostionList) {
        doEvaluesJieXiView(false, true);
        setAdapter(fuPostionList);
    }

    // TODO: 2019.01.07 用户小题自评题目
    private void setAdapter(List<CaseCardVo> fuPostionList) {
        if (fuPostionList == null || fuPostionList.isEmpty()) return;
        List<SelfEvaluateVo> lists = new ArrayList<>();
        for (int i = 0; i < fuPostionList.size(); i++) {
            SelfEvaluateVo evaluateVo = new SelfEvaluateVo();
            CaseCardVo caseCardVo = fuPostionList.get(i);
            DoBankSqliteVo userDoLog = mCaseExamtActivity.getUserDoLog(caseCardVo.getCasevo().getQuestion_id());
            if (userDoLog != null) {
                evaluateVo.setScore(String.valueOf(userDoLog.getMos()));
                evaluateVo.setAble(!StringUtil.isEmpty(userDoLog.getMos()));
            }
            evaluateVo.setCasevo(caseCardVo.getCasevo());
            lists.add(evaluateVo);
        }
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.HORIZONTAL);
        mRlvGmEightOneGallery.setLayoutManager(manager);
        GalleryAdapter adapter = new GalleryAdapter(mContext, lists);
        adapter.doChangerColor(mGmReadColorManger);
        adapter.doPostion(mPostion);
        manager.scrollToPosition(mPostion);
        mRlvGmEightOneGallery.setAdapter(adapter);
        adapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(int postion) {//用户滑动监听
                if (mCaseExamtActivity != null) {
                    mCaseExamtActivity.MoveItem(postion);
                }
            }
        });
        adapter.setAddEvaluesClickListener(new GalleryAdapter.OnAddEvalueClickListener() {
            @Override
            public void onClickItem(QuestionCaseVo vo, int postion) {//评分监听
                showAddEvalueLayout(true);

            }
        });
    }

    /**
     * 点击查看解析
     *
     * @param input
     * @param jiexi
     */
    private void doJieXiView(boolean input, boolean jiexi, boolean evalues) {
        //输入拼音
        // TODO: 2019.01.07 jiexi
        mRlGmEightOneYuyin.setVisibility(input ? View.VISIBLE : View.GONE);
        //解析外包括布局
        mRlGmEightOneJiexifu.setVisibility(jiexi ? View.VISIBLE : View.GONE);
        //解析
        mLlGmEightOneLookJiexi.setVisibility(jiexi ? View.VISIBLE : View.GONE);
        //画廊 与自评布局冲突
        mRlvGmEightOneGallery.setVisibility(evalues ? View.VISIBLE : View.GONE);

    }

    /**
     * 点击查看解析
     *
     * @param input
     * @param jiexi
     */
    private void doEvaluesJieXiView(boolean input, boolean jiexi) {
        //输入拼音
        mRlGmEightOneYuyin.setVisibility(input ? View.VISIBLE : View.GONE);
        //解析父
        mRlGmEightOneJiexifu.setVisibility(jiexi ? View.VISIBLE : View.GONE);
        //解析
        mLlGmEightOneLookJiexi.setVisibility(jiexi ? View.VISIBLE : View.GONE);
        //画廊
        mRlvGmEightOneGallery.setVisibility(View.VISIBLE);
        //用户输入内容
        mLlGmEightOneInputShow.setVisibility(input ? View.GONE : View.VISIBLE);
        //显示输入框布局
        mLlGmEightOneInputEdit.setVisibility(input ? View.VISIBLE : View.GONE);
    }

    //评分布局
    private void showAddEvalueLayout(boolean show) {
        mLlGmEightOneEvlauesLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        mRlvGmEightOneGallery.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    /**
     * 用户点击查看解析
     */
    private void doLookJieXiView() {
        //简答（旧）布局显示
        mLlGmeightJiandaLayout.setVisibility(mUserBuy ? View.VISIBLE : View.GONE);

        mBtnGmEightLookJiexi.setVisibility(View.GONE);
        //笔记
        mLlGmthreeNoteLayout.setVisibility(View.GONE);
        //解析
        mLlGmTwoAnalysisLayout.setVisibility(View.GONE);
        //评价
        mLlFourEvaluateLayout.setVisibility(View.GONE);
        //购买
        mLiBResolveBuy.setVisibility(mUserBuy ? View.GONE : View.VISIBLE);
        //答案
        mLlGmoneAnswerLayout.setVisibility(View.GONE);
    }

    private void doLookJieXiEvent() {
        DoBankSqliteVo vo = new DoBankSqliteVo();
        vo.setQuestiontype(mQuestionSqliteVo.getQuestiontype());
        vo.setQuestion_id(mQuestionSqliteVo.getQuestion_id());
        vo.setChapterid(mQuestionSqliteVo.getChapter_id());
        vo.setCourseid(mQuestionSqliteVo.getCourseid());
        vo.setIsDo(1);
        vo.setIsAnalySis(1);
        vo.setIslook(1);
        vo.setTime(String.valueOf(new Date().getTime()));
        doSaveEvent(vo);

    }

    private void doSaveMoreEvent() {
        //0为未选，1为正确 ，2 为漏选 ，3为错误
        DoBankSqliteVo vo = new DoBankSqliteVo();
        setSelectABCDEClick(!mCaseExamtActivity.submitAble());
        String str = mQuestionSqliteVo.getChoice_answer();
        List<String> list = mGmTextUtil.getAnswerKeyList(str);
        if (list == null || list.isEmpty()) return;
        //用户选择结果
        ArrayList<String> listKeys = new ArrayList<>();
        if (listKeys == null || list.isEmpty()) return;
        addSelectList(mSelectMorItemA, listKeys);
        addSelectList(mSelectMorItemB, listKeys);
        addSelectList(mSelectMorItemC, listKeys);
        addSelectList(mSelectMorItemD, listKeys);
        addSelectList(mSelectMorItemE, listKeys);
        //判断用户是否正确
        if (listKeys.size() > list.size()) {
            vo.setIsright(3);//错误
        } else if (listKeys.size() == list.size()) {
            if (list.containsAll(listKeys)) {
                vo.setIsright(1); //正确
            } else {
                vo.setIsright(3);//错误
            }
        } else if (listKeys.size() < list.size()) {
            if (list.containsAll(listKeys)) {
                vo.setIsright(2);
            } else {
                vo.setIsright(3);
            }

        }
        //用户选中 1为做过，0 为没做
        vo.setQuestion_id(mQuestionSqliteVo.getQuestion_id());
        vo.setIsDo(1);
        //选中1 为选中，0 为不选中
        vo.setSelectA(StringUtil.isEmpty(mSelectMorItemA) ? 0 : 1);
        vo.setSelectB(StringUtil.isEmpty(mSelectMorItemB) ? 0 : 1);
        vo.setSelectC(StringUtil.isEmpty(mSelectMorItemC) ? 0 : 1);
        vo.setSelectD(StringUtil.isEmpty(mSelectMorItemD) ? 0 : 1);
        vo.setSelectE(StringUtil.isEmpty(mSelectMorItemE) ? 0 : 1);
        vo.setQuestiontype(mQuestionSqliteVo.getQuestiontype());
        vo.setCourseid(mQuestionSqliteVo.getCourseid());
        vo.setChapterid(mQuestionSqliteVo.getChapter_id());
        //用户输入解析
        vo.setAnalysis(mEtEightOneInputContent.getText().toString().trim());
        vo.setTime(String.valueOf(new Date().getTime()));
        doSaveEvent(vo);
    }

    private void addSelectList(String select, ArrayList<String> list) {
        if (!StringUtil.isEmpty(select))
            list.add(select);
    }

    //设置布局
    public void showGmSetting() {
        mPopSetting = new CommonPopupWindow(mContext, R.layout.gm_pop_setting, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
            private DiscreteSlider mDiscreteSlider;
            private ArrayList<CheckBox> mCheckBoxList;
            private RadioGroup mRgContentDelete;
            private RadioButton mRdbGmpopNo;
            private RadioButton mRdbGmpopFive;
            private RadioButton mRdbGmpopThree;
            private RadioButton mRdbGmpopOne;
            private CheckBox mSwtGmselectNext;
            private CheckBox mRdbGmsettingEye;
            private CheckBox mRdbGmsettingNight;
            private CheckBox mRdbGmsettingDay;

            @Override
            protected void initView() {
                View view = getContentView();
                //初始化配置
                initSetting();
                mRgContentDelete = view.findViewById(R.id.rg_content_delete);
                mRdbGmpopNo = view.findViewById(R.id.rdb_gmpop_no);
                mRdbGmpopFive = view.findViewById(R.id.rdb_gmpop_five);
                mRdbGmpopThree = view.findViewById(R.id.rdb_gmpop_three);
                mRdbGmpopOne = view.findViewById(R.id.rdb_gmpop_one);
                mSwtGmselectNext = view.findViewById(R.id.swt_gmselect_next);
                mRdbGmsettingEye = view.findViewById(R.id.rdb_gmsetting_eye);
                mDiscreteSlider = (DiscreteSlider) view.findViewById(R.id.discrete_slider);
                mRdbGmsettingNight = view.findViewById(R.id.rdb_gmsetting_night);
                mRdbGmsettingDay = view.findViewById(R.id.rdb_gmsetting_day);
                mCheckBoxList = new ArrayList<>();
                mCheckBoxList.add(mRdbGmsettingDay);
                mCheckBoxList.add(mRdbGmsettingNight);
                mCheckBoxList.add(mRdbGmsettingEye);
                if (!StringUtil.isEmpty(mShowDayOrNight)) {//模式
                    if (mShowDayOrNight.equals(DataMessageVo.PATTERN_ONE)) {//白天
                        mRdbGmsettingDay.setChecked(true);
                    } else if (mShowDayOrNight.equals(DataMessageVo.PATTERN_TWO)) {//夜间
                        mRdbGmsettingNight.setChecked(true);
                    } else if (mShowDayOrNight.equals(DataMessageVo.PATTERN_THREE)) {//护眼
                        mRdbGmsettingEye.setChecked(true);
                    }
                } else {
                    mShowDayOrNight = DataMessageVo.PATTERN_ONE;
                    mRdbGmsettingDay.setChecked(true);
                    mUserDbHelp.upDataDayOrNightOrEye(DataMessageVo.PATTERN_ONE);
                }
                if (!StringUtil.isEmpty(mDeletenumber)) {
                    if (mDeletenumber.equals(DataMessageVo.PATTERN_ONE)) {
                        mRdbGmpopOne.setChecked(true);
                    } else if (mDeletenumber.equals(DataMessageVo.PATTERN_TWO)) {
                        mRdbGmpopThree.setChecked(true);
                    } else if (mDeletenumber.equals(DataMessageVo.PATTERN_THREE)) {
                        mRdbGmpopFive.setChecked(true);
                    } else if (mDeletenumber.equals(DataMessageVo.PATTERN_FOUR)) {
                        mRdbGmpopNo.setChecked(true);
                    }
                }

                if (!StringUtil.isEmpty(mFoneSize)) {
                    if (mFoneSize.equals(DataMessageVo.FONT_ONE)) {
                        mDiscreteSlider.setPosition(0);
                    } else if (mFoneSize.equals(DataMessageVo.FONT_TWO)) {
                        mDiscreteSlider.setPosition(1);
                    } else if (mFoneSize.equals(DataMessageVo.FONT_THREE)) {
                        mDiscreteSlider.setPosition(2);
                    } else if (mFoneSize.equals(DataMessageVo.FONT_FOUR)) {
                        mDiscreteSlider.setPosition(3);
                    } else if (mFoneSize.equals(DataMessageVo.FONT_FIVE)) {
                        mDiscreteSlider.setPosition(4);
                    }
                }
                if (!StringUtil.isEmpty(mUserNextGo) && mUserNextGo.equals("go")) {
                    mSwtGmselectNext.setChecked(true);
                } else {
                    mSwtGmselectNext.setChecked(false);
                }
            }

            @Override

            protected void initEvent() {
                setCheckChanger(mCheckBoxList, mRdbGmsettingDay, 1);
                setCheckChanger(mCheckBoxList, mRdbGmsettingNight, 2);
                setCheckChanger(mCheckBoxList, mRdbGmsettingEye, 3);
                setCheckChanger(mCheckBoxList, mSwtGmselectNext, 4);
                setRadioButtonChanger(mRdbGmpopNo, 1);
                setRadioButtonChanger(mRdbGmpopThree, 2);
                setRadioButtonChanger(mRdbGmpopFive, 3);
                setRadioButtonChanger(mRdbGmpopNo, 4);
                setDiscreteSlderListener(mDiscreteSlider);
            }

            @Override
            protected void initWindow() {
                super.initWindow();
                PopupWindow instance = getPopupWindow();
                instance.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mGmTextUtil.setBackgroundAlpha(1.0f, mContext);
                    }
                });
            }
        };
        mPopSetting.showAtLocation(mGmRoolLayout, Gravity.BOTTOM, 0, 0);
        mGmTextUtil.setBackgroundAlpha(0.5f, mContext);
    }

    /**
     * todo 舒心
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MainChangerColorEvent(GmChangerColorEvent event) {
        switch (event.getType()) {
            case 1://颜色
                doEventbusChanger(event.getNumber(), event.getClick());
                break;
            case 2:// 字体
                String number = String.valueOf(event.getNumber() + 1);
                if (number.equals(DataMessageVo.FONT_ONE)) {
                    mGmFontSizeUtil.status(GmFontSizeUtil.fontStatus.font14);
                } else if (number.equals(DataMessageVo.FONT_TWO)) {
                    mGmFontSizeUtil.status(GmFontSizeUtil.fontStatus.font16);
                } else if (number.equals(DataMessageVo.FONT_THREE)) {
                    mGmFontSizeUtil.status(GmFontSizeUtil.fontStatus.font18);
                } else if (number.equals(DataMessageVo.FONT_FOUR)) {
                    mGmFontSizeUtil.status(GmFontSizeUtil.fontStatus.font20);
                } else if (number.equals(DataMessageVo.FONT_FIVE)) {
                    mGmFontSizeUtil.status(GmFontSizeUtil.fontStatus.font24);
                }
                mSubsciber.notifyChanger();
                break;
            case 3:
                mSubsciber.notifyChanger();
                break;
            case 4:
                if (event.getNumber() == mPostion)
                    showShareLayout();
                break;
            case 5:
                if (mPopShare != null) {
                    mPopShare.getPopupWindow().dismiss();
                }
                break;
        }
    }

    /**
     * @param number    类型
     * @param isChecked 是否选中
     */
    private void doEventbusChanger(int number, boolean isChecked) {
        switch (number) {
            case 1://白天
                if (isChecked) {
                    mShowDayOrNight = DataMessageVo.PATTERN_ONE;
                    mUserDbHelp.upDataDayOrNightOrEye(DataMessageVo.PATTERN_ONE);
                    mGmReadColorManger.setGmBgColor(GmReadColorManger.DAYTIME);
                    mSubsciber.notifyChanger();
                }
                break;
            case 2://夜间
                if (isChecked) {
                    mShowDayOrNight = DataMessageVo.PATTERN_TWO;
                    mUserDbHelp.upDataDayOrNightOrEye(DataMessageVo.PATTERN_TWO);
                    mGmReadColorManger.setGmBgColor(GmReadColorManger.NIGHT);
                    mSubsciber.notifyChanger();
                } else {
                    setDay();
                }
                break;
            case 3://护眼
                if (isChecked) {
                    mShowDayOrNight = DataMessageVo.PATTERN_THREE;
                    mUserDbHelp.upDataDayOrNightOrEye(DataMessageVo.PATTERN_THREE);
                    mGmReadColorManger.setGmBgColor(GmReadColorManger.EYE);
                    mSubsciber.notifyChanger();
                } else {
                    setDay();
                }
                break;
        }

    }


    private void setDiscreteSlderListener(DiscreteSlider mDiscreteSlider) {
        mDiscreteSlider.setOnDiscreteSliderChangeListener(new DiscreteSlider.OnDiscreteSliderChangeListener() {
            @Override
            public void onPositionChanged(int position) {
                String number = String.valueOf(position + 1);
                mUserDbHelp.upDataFoneSize(number);
                EventBus.getDefault().postSticky(new GmChangerColorEvent(position, 2, false));
            }
        });

    }

    /**
     * 1为白天，2为夜间，3为护眼 4，一次删除，5为三次，6为五次删除，7不删除
     *
     * @param mCheckBoxList
     * @param checkBox
     * @param id
     */
    private void setCheckChanger(final ArrayList<CheckBox> mCheckBoxList, final CheckBox checkBox, final int id) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (id != 4) {
                    for (int i = 0; i < mCheckBoxList.size(); i++) {
                        CheckBox box = mCheckBoxList.get(i);
                        box.setChecked(false);
                    }
                    if (!checkBox.isPressed()) return;
                }
                EventBus.getDefault().postSticky(new GmChangerColorEvent(id, 1, isChecked));
                switch (id) {
                    case 1://白天
                        if (isChecked) {
                            checkBox.setChecked(true);
                        }

                        break;
                    case 2://夜间
                        if (isChecked) {
                            checkBox.setChecked(true);
                        }
                        break;
                    case 3://护眼
                        if (isChecked) {
                            checkBox.setChecked(true);
                        }
                        break;
                    case 4://自动下一题
                        if (isChecked) {
                            mUserDbHelp.upDataUserNext();
                        } else {
                            mUserDbHelp.delectUserNext();
                        }
                        break;
                }

            }

        });
    }

    public void setDay() {
        mShowDayOrNight = DataMessageVo.PATTERN_ONE;
        mUserDbHelp.upDataDayOrNightOrEye(DataMessageVo.PATTERN_ONE);
        mGmReadColorManger.setGmBgColor(GmReadColorManger.DAYTIME);
        mSubsciber.notifyChanger();
    }

    /**
     * 1为白天，2为夜间，3为护眼 4，一次删除
     *
     * @param checkBox
     * @param id
     */
    private void setRadioButtonChanger(RadioButton checkBox, final int id) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (id) {
                    case 1://一次
                        if (isChecked) {
                            mUserDbHelp.upDataErrorNumber(DataMessageVo.PATTERN_ONE);
                        }
                        break;
                    case 2://三次
                        if (isChecked) {
                            mUserDbHelp.upDataErrorNumber(DataMessageVo.PATTERN_TWO);
                        }
                        break;
                    case 3://五次
                        if (isChecked) {
                            mUserDbHelp.upDataErrorNumber(DataMessageVo.PATTERN_THREE);
                        }
                        break;
                    case 4://不删除
                        if (isChecked) {
                            mUserDbHelp.upDataErrorNumber(DataMessageVo.PATTERN_FOUR);
                        }
                        break;
                }

            }
        });
    }

    /**
     * 处理显示多选择
     */
    private void doSelectMoreEvent() {
        if (!mCaseExamtActivity.submitAble()) return;
        String keywordStr = mQuestionSqliteVo.getChoice_answer();
        List<String> keyList = mGmTextUtil.getAnswerKeyList(keywordStr);
        for (String s : keyList) {
            if (s.equalsIgnoreCase(mSelectA)) {
                mGmTextUtil.setImgMiss(mIvGmOneA);
                mGmTextUtil.setTvMiss(mTvGmOneAContent);
            } else if (s.equalsIgnoreCase(mSelectB)) {
                mGmTextUtil.setImgMiss(mIvGmOneB);
                mGmTextUtil.setTvMiss(mTvGmOneBContent);
            } else if (s.equalsIgnoreCase(mSelectC)) {
                mGmTextUtil.setImgMiss(mIvGmOneC);
                mGmTextUtil.setTvMiss(mTvGmOneCContent);
            } else if (s.equalsIgnoreCase(mSelectD)) {
                mGmTextUtil.setImgMiss(mIvGmOneD);
                mGmTextUtil.setTvMiss(mTvGmOneDContent);
            } else if (s.equalsIgnoreCase(mSelectE)) {
                mGmTextUtil.setImgMiss(mIvGmOneE);
                mGmTextUtil.setTvMiss(mTvGmOneEContent);
            }
        }
        //用户选中结果
        if (!StringUtil.isEmpty(mSelectMorItemA)) {
            boolean isRight = mGmTextUtil.keyIsRight(keyList, mSelectMorItemA);
            mGmTextUtil.setImgRight(mIvGmOneA, isRight);
            mGmTextUtil.setTvRight(mTvGmOneAContent, isRight);
        }
        if (!StringUtil.isEmpty(mSelectMorItemB)) {
            boolean isRight = mGmTextUtil.keyIsRight(keyList, mSelectMorItemB);
            mGmTextUtil.setImgRight(mIvGmOneB, isRight);
            mGmTextUtil.setTvRight(mTvGmOneBContent, isRight);
        }
        if (!StringUtil.isEmpty(mSelectMorItemC)) {
            boolean isRight = mGmTextUtil.keyIsRight(keyList, mSelectMorItemC);
            mGmTextUtil.setImgRight(mIvGmOneC, isRight);
            mGmTextUtil.setTvRight(mTvGmOneCContent, isRight);
        }
        if (!StringUtil.isEmpty(mSelectMorItemD)) {
            boolean isRight = mGmTextUtil.keyIsRight(keyList, mSelectMorItemD);
            mGmTextUtil.setImgRight(mIvGmOneD, isRight);
            mGmTextUtil.setTvRight(mTvGmOneDContent, isRight);
        }
        if (!StringUtil.isEmpty(mSelectMorItemE)) {
            boolean isRight = mGmTextUtil.keyIsRight(keyList, mSelectMorItemE);
            mGmTextUtil.setImgRight(mIvGmOneE, isRight);
            mGmTextUtil.setTvRight(mTvGmOneEContent, isRight);
        }

    }

    /**
     * 回显用户选中数据
     *
     * @param vo
     */
    private void doBackNowMoreEvent(DoBankSqliteVo vo) {
        if (vo.getSelectA() == 1) {
            mSelectMorItemA = mSelectA;
            mSelectMorisClickA = true;
            setSelectMoreItemBG(mSelectA, mSelectMorisClickA);
        }
        if (vo.getSelectB() == 1) {
            mSelectMorItemB = mSelectB;
            mSelectMorisClickB = true;
            setSelectMoreItemBG(mSelectB, mSelectMorisClickB);
        }
        if (vo.getSelectC() == 1) {
            mSelectMorItemC = mSelectC;
            mSelectMorisClickC = true;
            setSelectMoreItemBG(mSelectC, mSelectMorisClickC);
        }
        if (vo.getSelectD() == 1) {
            mSelectMorItemD = mSelectD;
            mSelectMorisClickD = true;
            setSelectMoreItemBG(mSelectD, mSelectMorisClickD);
        }
        if (vo.getSelectE() == 1) {
            mSelectMorItemE = mSelectE;
            mSelectMorisClickE = true;
            setSelectMoreItemBG(mSelectE, mSelectMorisClickE);
        }
    }

    /**
     * 处理选择后得效果
     */
    private void doRadioEventView() {
        if (!mCaseExamtActivity.submitAble()) return;
        //判断用户是否购买
        doShowNoBuy(mUserBuy);
    }

    private void doShowNoBuy(boolean buy) {
        //笔记
        mLlGmthreeNoteLayout.setVisibility(false ? View.VISIBLE : View.GONE);
        //解析
        mLlGmTwoAnalysisLayout.setVisibility(buy ? View.VISIBLE : View.GONE);
        //评价
        mLlFourEvaluateLayout.setVisibility(false ? View.VISIBLE : View.GONE);
        //购买
        mLiBResolveBuy.setVisibility(buy ? View.GONE : View.VISIBLE);
        //简答布局
        mLlGmeightJiandaLayout.setVisibility(View.GONE);
        //简答带输入
        mLlGmeightOneJiandaLayout.setVisibility(View.GONE);
        //答案
        mLlGmoneAnswerLayout.setVisibility(View.VISIBLE);
    }


    private void doRadioEventE() {
        saveDb(mSelectE, false, false, false, false, true);
    }

    private void doRadioEventD() {
        saveDb(mSelectD, false, false, false, true, false);
    }

    private void doRadioEventC() {
        saveDb(mSelectC, false, false, true, false, false);
    }

    private void doRadioEventB() {
        saveDb(mSelectB, false, true, false, false, false);
    }

    private void doRadioEventA() {
        saveDb(mSelectA, true, false, false, false, false);
    }

    /**
     * 保存到数据库
     *
     * @param selectItem 用户选项
     * @param a
     * @param b
     * @param c
     * @param d
     * @param e
     */
    private void saveDb(String selectItem, boolean a, boolean b, boolean c, boolean d, boolean e) {
        //0为未选，1为正确 ，2 为漏选 ，3为错误
        String str = mQuestionSqliteVo.getChoice_answer();
        DoBankSqliteVo vo = new DoBankSqliteVo();
        List<String> list = mGmTextUtil.getAnswerKeyList(str);
        if (list == null || list.isEmpty()) return;
        int right = 0;
        if (mGmTextUtil.keyIsRight(list, selectItem)) {//正确
            right = 1;
        } else {//错误
            right = 3;
        }
        //用户选中 1为做过，0 为没做
        vo.setQuestion_id(mQuestionSqliteVo.getQuestion_id());
        vo.setIsDo(1);
        //选中1 为选中，0 为不选中
        vo.setSelectA(a ? 1 : 0);
        vo.setSelectB(b ? 1 : 0);
        vo.setSelectC(c ? 1 : 0);
        vo.setSelectD(d ? 1 : 0);
        vo.setSelectE(e ? 1 : 0);
        vo.setQuestiontype(mQuestionSqliteVo.getQuestiontype());
        vo.setCourseid(mQuestionSqliteVo.getCourseid());
        vo.setChapterid(mQuestionSqliteVo.getChapter_id());
        //是否作对3为错误 2为漏选 1 为作对，0为没做
        vo.setIsright(right);
        vo.setTime(String.valueOf(new Date().getTime()));
        doSaveEvent(vo);
    }

    /**
     * 保存用户做题记录
     *
     * @param vo
     */
    private void doSaveEvent(DoBankSqliteVo vo) {
        if (mCaseExamtActivity != null) {
            mCaseExamtActivity.saveUserDoLog(vo);
        }
    }

    private void saveUserInput() {
        mEtEightOneInputContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String trim = mEtEightOneInputContent.getText().toString().trim();
                if (StringUtil.isEmpty(trim)) return;
                DoBankSqliteVo vo = new DoBankSqliteVo();
                vo.setChild_id(mQuestionSqliteVo.getQuestion_id());
                vo.setQuestiontype(mQuestionSqliteVo.getQuestiontype());
                vo.setParent_id(mQuestionSqliteVo.getParent_id());
                vo.setAnalysis(trim);
                vo.setIsDo(1);
                vo.setChapterid(mQuestionSqliteVo.getChapter_id());
                vo.setCourseid(mQuestionSqliteVo.getCourseid());
                vo.setQuestion_id(mQuestionSqliteVo.getQuestion_id());
                vo.setIsAnalySis(1);
                vo.setTime(String.valueOf(new Date().getTime()));
                doSaveEvent(vo);
            }
        });
    }

    /**
     * 处理选中后得对错
     *
     * @param a    选项
     * @param type 类型 2 单选其它是多选
     */
    private void doRightErrorBg(String a, int type) {
        if (!mCaseExamtActivity.submitAble()) {
            return;
        }
        //单选模式
        if (type == 2) {
            mGmImgManageUtil.setRightStauts(GmSelectImgManageUtil.RigthStatus.RadioRight);
        } else {
            mGmImgManageUtil.setRightStauts(GmSelectImgManageUtil.RigthStatus.ChoiceRight);
        }
        TextView tv = getSelectTextView(a);
        ImageView imageView = getSelectItemImager(a);
        String str = mQuestionSqliteVo.getChoice_answer();
        List<String> list = mGmTextUtil.getAnswerKeyList(str);
        if (tv != null)
            tv.setTextColor(mGmTextUtil.keyIsRight(list, a) ? mGmReadColorManger.getmTextRightColor() : mGmReadColorManger.getmTextRedColor());
        if (imageView != null) {
            imageView.setImageDrawable(mGmTextUtil.keyIsRight(list, a) ? mGmImgManageUtil.getRight() : mGmImgManageUtil.getError());
        }
        setRightColorImg(list);
    }

    /**
     * 设置正确得显示结果
     *
     * @param list
     */
    private void setRightColorImg(List<String> list) {
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                String select = list.get(i);
                TextView tvone = getSelectTextView(select);
                ImageView imageViewone = getSelectItemImager(select);
                if (tvone != null) {
                    tvone.setTextColor(mGmReadColorManger.getmTextRightColor());
                }
                if (imageViewone != null) {
                    imageViewone.setImageDrawable(mGmImgManageUtil.getRight());
                }
            }
        }
    }

    /**
     * 处理单选
     */
    private void doRadioItemBgEvent(boolean a, boolean b, boolean c, boolean d, boolean e) {
        if (mGmImgManageUtil == null) {
            mGmImgManageUtil = GmSelectImgManageUtil.get_Instance(mContext);
        }
        setImgBg(mIvGmOneA, a, mGmImgManageUtil.getDrawable(R.drawable.qbank_answer_icon_single_a_s), mGmImgManageUtil.getDrawable(R.drawable.ic_b_single_a_n));
        setImgBg(mIvGmOneB, b, mGmImgManageUtil.getDrawable(R.drawable.qbank_answer_icon_single_b_s), mGmImgManageUtil.getDrawable(R.drawable.ic_b_single_b_n));
        setImgBg(mIvGmOneC, c, mGmImgManageUtil.getDrawable(R.drawable.qbank_answer_icon_single_c_s), mGmImgManageUtil.getDrawable(R.drawable.ic_b_single_c_n));
        setImgBg(mIvGmOneD, d, mGmImgManageUtil.getDrawable(R.drawable.qbank_answer_icon_single_d_s), mGmImgManageUtil.getDrawable(R.drawable.ic_b_single_d_n));
        setImgBg(mIvGmOneE, e, mGmImgManageUtil.getDrawable(R.drawable.qbank_answer_icon_single_e_s), mGmImgManageUtil.getDrawable(R.drawable.ic_b_e_n));
    }

    /**
     * 设置多选确认按钮
     */
    private void setSureKeyBg() {
        if (mSelectMorisClickA || mSelectMorisClickB || mSelectMorisClickC || mSelectMorisClickD || mSelectMorisClickE) {
//            mBtnGmoneSuerAnswer.setClickable(true);
            mDuoXunaSure = false;
            mBtnGmoneSuerAnswer.setTextColor(mGmTextUtil.getColorDrawable(R.color.white));
            mBtnGmoneSuerAnswer.setBackgroundResource(R.drawable.btn_b_sure_s);
        } else {
            mDuoXunaSure = true;
//            mBtnGmoneSuerAnswer.setClickable(true);
            mBtnGmoneSuerAnswer.setTextColor(mGmTextUtil.getColorDrawable(R.color.text_fu_color));
            mBtnGmoneSuerAnswer.setBackgroundResource(R.drawable.bg_gm_text_sure_n);
        }
    }

    /**
     * 多选结果展示
     *
     * @param id 0a,1b,2c,3d,4e
     */
    private void setSelectMoreItemBG(String id, boolean isSelect) {
        if (mGmImgManageUtil == null) {
            mGmImgManageUtil = GmSelectImgManageUtil.get_Instance(mContext);
        }
        if (id.equalsIgnoreCase(mSelectA)) {
            setImgBg(mIvGmOneA, isSelect, mGmImgManageUtil.getDrawable(R.drawable.qbank_answe_icon_multiple_a_s), mGmImgManageUtil.getDrawable(R.drawable.ic_b_a_n));
        } else if (id.equalsIgnoreCase(mSelectB)) {
            setImgBg(mIvGmOneB, isSelect, mGmImgManageUtil.getDrawable(R.drawable.qbank_answe_icon_multiple_b_s), mGmImgManageUtil.getDrawable(R.drawable.ic_b_b_n));
        } else if (id.equalsIgnoreCase(mSelectC)) {
            setImgBg(mIvGmOneC, isSelect, mGmImgManageUtil.getDrawable(R.drawable.qbank_answe_icon_multiple_c_s), mGmImgManageUtil.getDrawable(R.drawable.ic_b_c_n));
        } else if (id.equalsIgnoreCase(mSelectD)) {
            setImgBg(mIvGmOneD, isSelect, mGmImgManageUtil.getDrawable(R.drawable.qbank_answe_icon_multiple_d_s), mGmImgManageUtil.getDrawable(R.drawable.ic_b_d_n));
        } else if (id.equalsIgnoreCase(mSelectE)) {
            setImgBg(mIvGmOneE, isSelect, mGmImgManageUtil.getDrawable(R.drawable.qbank_answe_icon_multiple_e_s), mGmImgManageUtil.getDrawable(R.drawable.ic_b_e_n));
        }

    }

    /**
     * 设置背景
     *
     * @param iv         控件
     * @param is         选中
     * @param selectid   选中图片
     * @param unselectid 未选中图片
     */
    private void setImgBg(ImageView iv, boolean is, Drawable selectid, Drawable unselectid) {
        if (iv == null)
            return;
        if (is) {//选中
            iv.setImageDrawable(selectid);
        } else {//未选中
            iv.setImageDrawable(unselectid);
        }
    }

    /**
     * 设置文体文字
     *
     * @param select
     * @return
     */
    private TextView getSelectTextView(String select) {
        if (select.equalsIgnoreCase(mSelectA)) {
            return mTvGmOneAContent;
        }
        if (select.equalsIgnoreCase(mSelectB)) {
            return mTvGmOneBContent;
        }
        if (select.equalsIgnoreCase(mSelectC)) {
            return mTvGmOneCContent;
        }
        if (select.equalsIgnoreCase(mSelectD)) {
            return mTvGmOneDContent;
        }
        if (select.equalsIgnoreCase(mSelectE)) {
            return mTvGmOneEContent;
        }
        return null;
    }

    /**
     * 设置正确图片
     *
     * @param select
     * @return
     */
    private ImageView getSelectItemImager(String select) {
        if (select.equalsIgnoreCase(mSelectA)) {
            return mIvGmOneA;
        }
        if (select.equalsIgnoreCase(mSelectB)) {
            return mIvGmOneB;
        }
        if (select.equalsIgnoreCase(mSelectC)) {
            return mIvGmOneC;
        }
        if (select.equalsIgnoreCase(mSelectD)) {
            return mIvGmOneD;
        }
        if (select.equalsIgnoreCase(mSelectE)) {
            return mIvGmOneE;
        }
        return null;
    }

    /**
     * 获取用户输入内容
     *
     * @return
     */
    public String getUserAnsysic() {
        return mEtEightOneInputContent.getText().toString().trim();
    }

    /**
     * 设置选项是否能点击
     *
     * @param click
     */
    private void setSelectABCDEClick(boolean click) {
        mLlGmoneALayout.setClickable(click);
        mLlGmoneBLayout.setClickable(click);
        mLlGmoneCLayout.setClickable(click);
        mLlGmoneDLayout.setClickable(click);
        mLlGmOneELayout.setClickable(click);
    }

    //设置难度星星
    private void setStarNumber(int number) {
        ArrayList<ImageView> list = new ArrayList<>();
        list.add(mIvGmTwoStarOne);
        list.add(mIvGmTwoStarTwo);
        list.add(mIvGmTwoStarThree);
        list.add(mIvGmTwoStarFour);
        list.add(mIvGmTwoStarFive);
        for (int i = 0; i < number; i++) {
            ImageView imageView = list.get(i);
            imageView.setImageDrawable(mGmImgManageUtil.getDrawable(R.mipmap.ic_b_difficulty_s));
        }

    }

    //请求评论
    @Override
    public void EvaluesOneSuccess(String sucess) {
        EvalueNewVo vo = Utils.getGosnT(sucess, EvalueNewVo.class);
        clearData();
        addListData(vo.getDatas());
        mTvGmfourNuber.setText("(" + vo.getTotal().getTotal() + ")");
        if (!mArray.isEmpty() && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
            mShowMoreView = true;
        } else {
            mShowMoreView = false;
        }
        if (vo.getTotal().getTotal() == mArray.size())
            mShowMoreView = false;
        showMoreView(vo.getTotal().getTotal());
        mEvalueAdapter.notifyDataSetChanged();
    }

    /**
     * 显示加载更多布局
     */
    private void showMoreView(int number) {
        mIvNetEmptyContent.setVisibility(number == 0 ? View.VISIBLE : View.GONE);
        mLlGmfourMoreData.setVisibility(mShowMoreView ? View.VISIBLE : View.GONE);
    }

    @Override
    public void EvaluesOneError(String msg) {
        T_ERROR(mContext);
    }

    @Override
    public void EvaluesMoreSuccess(String sucess) {
        EvalueNewVo vo = Utils.getGosnT(sucess, EvalueNewVo.class);
        addListData(vo.getDatas());
        if (!mArray.isEmpty() && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
            mShowMoreView = true;
        } else {
            mShowMoreView = false;
        }
        if (vo.getTotal().getTotal() == mArray.size()) {
            mShowMoreView = false;
        }
        showMoreView(vo.getTotal().getTotal());
        mEvalueAdapter.notifyDataSetChanged();

    }

    //请求评论
    @Override
    public void EvaluesMoreError(String msg) {
        T_ERROR(mContext);
    }

    @Override
    public void NoTeByQuestionSuccess(String success) {


    }

    @Override
    public void NoteByQuestionError(String msg) {

    }

    private void clearData() {
        if (mArray == null) {
            mArray = new ArrayList();
        } else {
            mArray.clear();
        }
    }

    private void addListData(List<?> list) {
        if (mArray == null) {
            clearData();
        }
        if (list == null || list.isEmpty()) {
            return;
        }
        mArray.addAll(list);
    }

    /**
     * 当前数据有几页
     *
     * @return
     */
    private int getNowPage() {
        if (mArray == null || mArray.isEmpty())
            return 0;
        if (mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0)
            return mArray.size() / DataMessageVo.CINT_PANGE_SIZE;
        else
            return mArray.size() / DataMessageVo.CINT_PANGE_SIZE + 1;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().removeAllStickyEvents();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        clearSelectItem();
        mCaseExamtActivity = null;
    }

    private void clearSelectItem() {
        mSelectMorItemA = null;
        mSelectMorItemB = null;
        mSelectMorItemC = null;
        mSelectMorItemD = null;
        mSelectMorItemE = null;
    }

    /**
     * 分享布局
     */
    private void showShareLayout() {
        mPopShare = new CommonPopupWindow(mContext, R.layout.pop_item_share, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
            private TextView qqzon;
            private TextView qq;
            private TextView weibo;
            private TextView circle;
            private TextView weixin;

            @Override
            protected void initView() {
                View view = getContentView();
                weixin = (TextView) view.findViewById(R.id.tv_pop_weixin_share);
                circle = (TextView) view.findViewById(R.id.tv_pop_crile_share);
                weibo = (TextView) view.findViewById(R.id.tv_pop_weibo_share);
                qq = (TextView) view.findViewById(R.id.tv_pop_qq_share);
                qqzon = (TextView) view.findViewById(R.id.tv_pop_qqzon_share);
            }

            @Override
            protected void initEvent() {
                qq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.handleOnDoubleClick()) {
                            return;
                        }

                        // TODO: 2018.11.02 截图是否带有评论
//                        hideScreenshot(false);
                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mContext, mSmsvLayout));
                        //初始化选项图片
                        //初始化选项图片
                        if (getActivity() instanceof CaseExamtActivity) {//章节练习
                            mCaseExamtActivity = (CaseExamtActivity) getActivity();
                            ShareUtils.shareImg(mCaseExamtActivity, mQuestionSqliteVo.getQuestionString(),
                                    pic, SHARE_MEDIA.QQ);
                        }
                        EventBus.getDefault().postSticky(new GmChangerColorEvent(0, 5, false));
                    }
                });
                qqzon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.handleOnDoubleClick()) {
                            return;
                        }
                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mContext, mSmsvLayout));
                        //初始化选项图片
                        if (getActivity() instanceof CaseExamtActivity) {//章节练习
                            mCaseExamtActivity = (CaseExamtActivity) getActivity();
                            ShareUtils.shareImg(mCaseExamtActivity, mQuestionSqliteVo.getQuestionString(),
                                    pic, SHARE_MEDIA.QQ);
                        }
                        EventBus.getDefault().postSticky(new GmChangerColorEvent(0, 5, false));
                    }
                });
                weibo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.handleOnDoubleClick()) {
                            return;
                        }
                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mContext, mSmsvLayout));
                        //初始化选项图片
                        if (getActivity() instanceof CaseExamtActivity) {//章节练习
                            mCaseExamtActivity = (CaseExamtActivity) getActivity();
                            ShareUtils.shareImg(mCaseExamtActivity, mQuestionSqliteVo.getQuestionString(),
                                    pic, SHARE_MEDIA.SINA);
                        }
                        EventBus.getDefault().postSticky(new GmChangerColorEvent(0, 5, false));
                    }
                });
                weixin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.handleOnDoubleClick()) {
                            return;
                        }
                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mContext, mSmsvLayout));
                        //初始化选项图片
                        if (getActivity() instanceof CaseExamtActivity) {//章节练习
                            mCaseExamtActivity = (CaseExamtActivity) getActivity();
                            ShareUtils.shareImg(mCaseExamtActivity, mQuestionSqliteVo.getQuestionString(),
                                    pic, SHARE_MEDIA.WEIXIN);
                        }
                        EventBus.getDefault().postSticky(new GmChangerColorEvent(0, 5, false));
                    }
                });
                circle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.handleOnDoubleClick()) {
                            return;
                        }
                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mContext, mSmsvLayout));
                        //初始化选项图片
                        if (getActivity() instanceof CaseExamtActivity) {//章节练习
                            mCaseExamtActivity = (CaseExamtActivity) getActivity();
                            ShareUtils.shareImg(mCaseExamtActivity, mQuestionSqliteVo.getQuestionString(),
                                    pic, SHARE_MEDIA.WEIXIN_CIRCLE);
                        }
                        EventBus.getDefault().postSticky(new GmChangerColorEvent(0, 5, false));
                    }
                });
            }

            @Override
            protected void initWindow() {
                super.initWindow();
                PopupWindow instance = getPopupWindow();
                instance.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        Utils.setBackgroundAlpha(1f, mContext);
                        EventBus.getDefault().postSticky(new GmChangerColorEvent(0, 5, false));
                    }
                });
            }
        };
        mPopShare.showAtLocation(mGmRoolLayout, Gravity.BOTTOM, 0, 0);
        Utils.setBackgroundAlpha(0.5f, mContext);
    }
}

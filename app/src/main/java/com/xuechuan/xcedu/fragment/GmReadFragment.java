package com.xuechuan.xcedu.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
import android.widget.TextView;

import com.andview.refreshview.callback.IFooterCallBack;
import com.easefun.polyvsdk.PolyvSDKUtil;
import com.etiennelawlor.discreteslider.library.ui.DiscreteSlider;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.adapter.bank.GmEvaluateAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.event.EvalueTwoEvent;
import com.xuechuan.xcedu.event.GmChangerColorEvent;
import com.xuechuan.xcedu.mvp.contract.GmEvalueContract;
import com.xuechuan.xcedu.mvp.model.GmEvalueModel;
import com.xuechuan.xcedu.mvp.presenter.GmEvaluePresenter;
import com.xuechuan.xcedu.sqlitedb.UserInfomDbHelp;
import com.xuechuan.xcedu.ui.EvalueTwoActivity;
import com.xuechuan.xcedu.ui.ImagerActivity;
import com.xuechuan.xcedu.ui.bank.BankBuyActivity;
import com.xuechuan.xcedu.ui.bank.newBankActivity.ColloerTextActivity;
import com.xuechuan.xcedu.ui.bank.newBankActivity.ErrorTextActivity;
import com.xuechuan.xcedu.ui.bank.newBankActivity.FreedomTestActivity;
import com.xuechuan.xcedu.ui.bank.newBankActivity.NewTextActivity;
import com.xuechuan.xcedu.ui.bank.newBankActivity.NoteLookActivity;
import com.xuechuan.xcedu.ui.bank.newBankActivity.OrderTestActivity;
import com.xuechuan.xcedu.ui.bank.newBankActivity.SpecialTextActivity;
import com.xuechuan.xcedu.ui.bank.newbank.NoteMakeActivity;
import com.xuechuan.xcedu.utils.GmFontSizeUtil;
import com.xuechuan.xcedu.utils.GmReadColorManger;
import com.xuechuan.xcedu.utils.GmSelectImgManageUtil;
import com.xuechuan.xcedu.utils.GmTextUtil;
import com.xuechuan.xcedu.utils.ScreenShot;
import com.xuechuan.xcedu.utils.ShareUtils;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.Subsciber;
import com.xuechuan.xcedu.utils.SuppertUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.TestObserver;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.EvalueNewVo;
import com.xuechuan.xcedu.vo.QuestionCaseVo;
import com.xuechuan.xcedu.vo.QuestionNoteVo;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.ErrorSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.UserInfomSqliteVo;
import com.xuechuan.xcedu.weight.CommonPopupWindow;
import com.xuechuan.xcedu.weight.FlowLayout;
import com.xuechuan.xcedu.weight.SmartScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: ReadFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 通用做题界面
 * @author: L-BackPacker
 * @date: 2018.12.05 下午 5:04
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.12.05
 */

public class GmReadFragment extends BaseFragment implements TestObserver, View.OnClickListener, GmEvalueContract.View {

    private static final String ARG_PARAM_DATA = "data";
    private static final String ARG_PARAM_COUID = "couid";
    private static final String ARG_PARAM_POSTION = "postion";
    private static String TAG = "【" + GmReadFragment.class + "】==";
    private View view;
    private QuestionSqliteVo mQuestionSqliteVo;
    private QuestionCaseVo mQuestionCase;
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
     * ,问题类型 2,单选题；3,多选题；4,简答题
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
    //评价适配器
//    private AnswerEvaluateAdapter mEvalueAdapter;
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
     * 章节练习
     */
    private NewTextActivity mNewTextActivity;
    /**
     * 顺序练习
     */
    private OrderTestActivity mOrderTestActivity;
    /**
     * 自由组卷
     */
    private FreedomTestActivity mFreedomTestActivity;
    /**
     * 专项练习
     */
    private SpecialTextActivity mSpecialTextActivity;
    private TextView mTvGmsevenTextContent;
    private RelativeLayout mRlvGmSevenAnswerLayout;
    private TextView mTvGmsixtextCollect;
    private ImageView mIvGmsixtextMenu;
    private TextView mTvGmsixtextQid;
    private TextView mTvGmsixtextAllqid;
    private TextView mTvGmsixtextShare;
    private LinearLayout mLiGmsixTextNavbar;
    private FrameLayout mFlGmsixLayout;
    private TextView mTvGmLine;
    private TextView mTvGmZoneQuestionTitle;
    private LinearLayout mLlGmZoneQuestionTitle;
    private TextView mTvGmThreeNoteContent;
    private LinearLayout mLlGmThreeNoteBg;
    /**
     * 用户笔记
     */
    private String mNoteContent = "";
    /**
     * 笔记activity
     */
    private NoteLookActivity mNoteLookActivity;
    /**
     * 错题activity
     */
    private ErrorTextActivity mErrorTextActivity;
    private ColloerTextActivity mColloerTextActivity;
    private CommonPopupWindow mPopShare;
    private int mNote_id;
    private ImageView mIvGmZoneQuestintype;
    private ImageView mIvGmZoneQuestionImg;
    private ImageView mIvGmTwoAnalysisImg;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuestionCase = (QuestionCaseVo) getArguments().getSerializable(ARG_PARAM_DATA);
            mCouresid = getArguments().getString(ARG_PARAM_COUID);
            mPostion = getArguments().getInt(ARG_PARAM_POSTION);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mQuestionSqliteVo != null)
            initNoteView();
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
    }

    /***
     *
     * @param vo 数据类型
     * @param mCouserid 科目id 1 技术，2 综合，3 案例
     * @return
     */
    public static GmReadFragment newInstance(QuestionCaseVo vo, int postion, String mCouserid) {
        GmReadFragment fragment = new GmReadFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_DATA, vo);
        args.putString(ARG_PARAM_COUID, mCouserid);
        args.putInt(ARG_PARAM_POSTION, postion);
        fragment.setArguments(args);
        return fragment;
    }

    /*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bant_text_layout, container, false);
        initView(view);
        return view;
    }*/

    @Override
    protected int initInflateView() {
        return R.layout.fragment_bant_text_layout;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        //初始化activity
        initActivity();
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
        //初始化笔记

        //注册观察者
        registerOberovce();

    }

    private void initActivity() {
        //初始化选项图片
        if (getActivity() instanceof NewTextActivity) {//章节练习
            mNewTextActivity = (NewTextActivity) getActivity();
            mQuestionSqliteVo = mNewTextActivity.getQuestionVo(mQuestionCase.getId());
        }
        if (getActivity() instanceof OrderTestActivity) {//顺序练习
            mOrderTestActivity = (OrderTestActivity) getActivity();
            mQuestionSqliteVo = mOrderTestActivity.getQuestionVo(mQuestionCase.getId());
        }
        if (getActivity() instanceof FreedomTestActivity) {//自由组卷
            mFreedomTestActivity = (FreedomTestActivity) getActivity();
            mQuestionSqliteVo = mFreedomTestActivity.getQuestionVo(mQuestionCase.getId());
        }
        if (getActivity() instanceof SpecialTextActivity) {//专项练习
            mSpecialTextActivity = (SpecialTextActivity) getActivity();
            mQuestionSqliteVo = mSpecialTextActivity.getQuestionVo(mQuestionCase.getId());
        }
        if (getActivity() instanceof NoteLookActivity) {
            mNoteLookActivity = (NoteLookActivity) getActivity();
            mQuestionSqliteVo = mNoteLookActivity.getQuestionVo(mQuestionCase.getId());
        }
        if (getActivity() instanceof ErrorTextActivity) {
            mErrorTextActivity = (ErrorTextActivity) getActivity();
            mQuestionSqliteVo = mErrorTextActivity.getQuestionVo(mQuestionCase.getId());
        }
        if (getActivity() instanceof ColloerTextActivity) {
            mColloerTextActivity = (ColloerTextActivity) getActivity();
            mQuestionSqliteVo = mColloerTextActivity.getQuestionVo(mQuestionCase.getId());
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
        if (doOpenNewWork()) {
            mGmEvaluePresenter.reqeustEvalueContentOne(mContext, 1, DataMessageVo.QUESTION, String.valueOf(mQuestionSqliteVo.getQuestion_id()));
        }
        clearData();
        setGridLayoutManger(mContext, mRlvGmfourContent, 1);
//        mEvalueAdapter = new AnswerEvaluateAdapter(mContext, mArray);
        mEvalueAdapter = new GmEvaluateAdapter(mContext, mArray);
        mEvalueAdapter.doChangerColor(mGmReadColorManger);
        mRlvGmfourContent.setAdapter(mEvalueAdapter);
        mSmsvLayout.setScanScrollChangedListener(new SmartScrollView.ISmartScrollChangedListener() {
            @Override
            public void onScrolledToBottom() {
                if (!doOpenNewWork()) {
                    return;
                }
                if (mShowMoreView) {
                    mLlGmfourMoreData.setVisibility(View.VISIBLE);
                    mGmEvaluePresenter.reqeustEvalueContentMore(mContext, getNowPage() + 1, DataMessageVo.QUESTION, String.valueOf(mQuestionSqliteVo.getQuestion_id()));
                    mShowMoreView = false;
                } else {
                    mLlGmfourMoreData.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScrolledToTop() {
            }
        });
        mEvalueAdapter.setOnItemClickListener(new GmEvaluateAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                EvalueNewVo.DatasBean bean = (EvalueNewVo.DatasBean) obj;
                EventBus.getDefault().postSticky(new EvalueTwoEvent(bean));
                Intent intent = EvalueTwoActivity.newInstance(mContext, String.valueOf(bean.getTargetid()),
                        String.valueOf(bean.getId()), DataMessageVo.USERTYPEQC,
                        DataMessageVo.QUESTION);
                startActivity(intent);
            }
        });
        mEvalueAdapter.setClickChbListener(new GmEvaluateAdapter.onItemChbClickListener() {
            @Override
            public void onClickChbListener(Object obj, boolean isCheck, int position) {
                EvalueNewVo.DatasBean bean = (EvalueNewVo.DatasBean) obj;
                EvalueNewVo.DatasBean vo = (EvalueNewVo.DatasBean) mArray.get(position);
                SuppertUtil util = SuppertUtil.getInstance(mContext);
                vo.setIssupport(isCheck);
                if (isCheck) {
                    vo.setSupportcount(vo.getSupportcount() + 1);
                    util.submitSupport(String.valueOf(bean.getTargetid()), "true", DataMessageVo.USERTYPEQC);
                } else {
                    vo.setSupportcount(vo.getSupportcount() - 1);
                    util.submitSupport(String.valueOf(bean.getTargetid()), "false", DataMessageVo.USERTYPEQC);
                }
                mEvalueAdapter.notifyDataSetChanged();
            }
        });
   /*     mSmsvLayout.setmSmartScrollChangedListener(new WeiNestedScrollView.ISmartScrollChangedListener() {
            @Override
            public void onScrolledToBottom() {
                if (mShowMoreView) {
                    mLlGmfourMoreData.setVisibility(View.VISIBLE);
                    mGmEvaluePresenter.reqeustEvalueContentMore(mContext, getNowPage() + 1, DataMessageVo.QUESTION, String.valueOf(mQuestionSqliteVo.getQuestion_id()));
                } else {
                    mLlGmfourMoreData.setVisibility(View.GONE);
                }
            }
            @Override
            public void onScrolledToTop() {
                Log.e(TAG, "onScrolledToTop: ");
            }
        });*/


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
        mTvGmTitleFirst.setOnClickListener(this);
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
//        mSmsvLayout = (WeiNestedScrollView) view.findViewById(R.id.smsv_layout);
        mSmsvLayout = (SmartScrollView) view.findViewById(R.id.smsv_layout);
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
        mGmRoolLayout = (LinearLayout) view.findViewById(R.id.gm_rool_layout);
        mGmRoolLayout.setOnClickListener(this);
        mVGmtwoLineThree = (View) view.findViewById(R.id.v_gmtwo_line_three);
        mVGmtwoLineThree.setOnClickListener(this);
        mTvGmsevenTextContent = (TextView) view.findViewById(R.id.tv_gmseven_text_content);
        mTvGmsevenTextContent.setOnClickListener(this);

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
        //简答题布局
        mRlvGmSevenAnswerLayout = (RelativeLayout) view.findViewById(R.id.rlv_gm_seven_answer_layout);
        mRlvGmSevenAnswerLayout.setOnClickListener(this);

        mTvGmLine = (TextView) view.findViewById(R.id.tv_gm_line);
        mTvGmLine.setOnClickListener(this);
        //题干布局
        mTvGmZoneQuestionTitle = (TextView) view.findViewById(R.id.tv_gm_zone_question_title);
        mLlGmZoneQuestionTitle = (LinearLayout) view.findViewById(R.id.ll_gm_zone_question_title);
        mTvGmThreeNoteContent = (TextView) view.findViewById(R.id.tv_gm_three_note_content);

        mLlGmThreeNoteBg = (LinearLayout) view.findViewById(R.id.ll_gm_three_note_bg);
        mLlGmThreeNoteBg.setOnClickListener(this);

        mIvGmZoneQuestintype = (ImageView) view.findViewById(R.id.iv_gm_zone_questintype);

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
        //题干
        mLlGmZoneQuestionTitle.setVisibility(View.VISIBLE);
        //评价布局
        mLlFourEvaluateLayout.setVisibility(View.GONE);
        //解析布局
        mLlGmTwoAnalysisLayout.setVisibility(View.GONE);
        //笔记布局
        mLlGmthreeNoteLayout.setVisibility(View.GONE);
        //阅读理解
        mLlGmtitleSelectLayout.setVisibility(View.GONE);
        //简答题
        mRlvGmSevenAnswerLayout.setVisibility(View.GONE);

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
     */
    @Override
    public void refresh() {
        ShowSelectImg();
        setColor();
        setImgBg();
        setFontSize();
        if (mNewTextActivity != null) {
            mNewTextActivity.changerColor(mGmReadColorManger);
            mNewTextActivity.changerCollect();
        }
        if (mFreedomTestActivity != null) {
            mFreedomTestActivity.changerColor(mGmReadColorManger);
            mFreedomTestActivity.changerCollect();
        }
        if (mOrderTestActivity != null) {
            mOrderTestActivity.changerColor(mGmReadColorManger);
            mOrderTestActivity.changerCollect();
        }
        if (mSpecialTextActivity != null) {
            mSpecialTextActivity.changerColor(mGmReadColorManger);
            mSpecialTextActivity.changerCollect();
        }
        if (mNoteLookActivity != null) {
            mNoteLookActivity.changerColor(mGmReadColorManger);
            mNoteLookActivity.changerCollect();
        }
        if (mErrorTextActivity != null) {
            mErrorTextActivity.changerColor(mGmReadColorManger);
            mErrorTextActivity.changerCollect();
        }
        if (mColloerTextActivity != null) {
            mColloerTextActivity.changerColor(mGmReadColorManger);
            mColloerTextActivity.changerCollect();
        }

        initDoEvent();

        if (mEvalueAdapter != null) {
            mEvalueAdapter.notifyDataSetChanged();
        }

    }


    /**
     * 初始化数据
     */
    private void initDoEvent() {
        DoBankSqliteVo vo = null;
        //初始化选项图片
        if (getActivity() instanceof NewTextActivity) {//章节练习
            mNewTextActivity = (NewTextActivity) getActivity();
            vo = mNewTextActivity.queryUserData(mQuestionSqliteVo.getQuestion_id());
        }
        if (getActivity() instanceof OrderTestActivity) {//顺序练习
            mOrderTestActivity = (OrderTestActivity) getActivity();
            vo = mOrderTestActivity.queryUserData(mQuestionSqliteVo.getQuestion_id());
        }
        if (getActivity() instanceof FreedomTestActivity) {//自由组卷
            mFreedomTestActivity = (FreedomTestActivity) getActivity();
            vo = mFreedomTestActivity.queryUserData(mQuestionSqliteVo.getQuestion_id());
        }
        if (getActivity() instanceof SpecialTextActivity) {//专项练习
            mSpecialTextActivity = (SpecialTextActivity) getActivity();
            vo = mSpecialTextActivity.queryUserData(mQuestionSqliteVo.getQuestion_id());
        }
        if (getActivity() instanceof NoteLookActivity) {//查看题库
            mNoteLookActivity = (NoteLookActivity) getActivity();
            vo = mNoteLookActivity.queryUserData(mQuestionSqliteVo.getQuestion_id());
        }

        if (getActivity() instanceof ErrorTextActivity) {
            mErrorTextActivity = (ErrorTextActivity) getActivity();
            vo = mErrorTextActivity.queryUserData(mQuestionSqliteVo.getQuestion_id());
        }

        if (getActivity() instanceof ColloerTextActivity) {
            mColloerTextActivity = (ColloerTextActivity) getActivity();
            vo = mColloerTextActivity.queryUserData(mQuestionSqliteVo.getQuestion_id());
        }


        if (vo == null) return;
        if (vo.getIsDo() == 0) return;
        if (vo.getQuestiontype() == 2) {
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
            setSelectABCDEClick(false);
            doRadioEventView();
        } else if (vo.getQuestiontype() == 3) {
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
            mBtnGmoneSuerAnswer.setVisibility(View.GONE);
            setSelectABCDEClick(false);
            doSelectMoreEvent();
            doRadioEventView();
        }
        initTitleRed();

    }

    private void initTitleRed() {
        if (!mUserBuy) return;
//        if (StringUtil.isEmpty(mQuestionSqliteVo.getKeywordStr())) {
        if (StringUtil.isEmpty(mQuestionSqliteVo.getKeywordsString())) {
            return;
        }
        final int width = mIvGmZoneQuestintype.getLayoutParams().width+20;
//        Spanned spanned = StringUtil.repaceExamStr(mQuestionSqliteVo.getQuestionStr(), mQuestionSqliteVo.getKeywordStr(), null);
        Spanned spanned = StringUtil.repaceExamStr(mQuestionSqliteVo.getQuestionString(),
                mQuestionSqliteVo.getKeywordsString(), null,width);
        mTvGmZoneQuestionTitle.setText(spanned);

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
        mTvGmsevenTextContent.setTextSize(mGmFontSizeUtil.getFontSize());
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
        mTvGmfourEvaluateTitle.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mTvGmfourNuber.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mVGmtwoLineThree.setBackgroundColor(mGmReadColorManger.getmCutLineColor());
        mVGmOneLine.setBackgroundColor(mGmReadColorManger.getmCutLineColor());
        mTvGmsevenTextContent.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mTvGmZoneQuestionTitle.setTextColor(mGmReadColorManger.getmTextTitleColor());
        mTvGmThreeNoteContent.setTextColor(mGmReadColorManger.getmTextFuColor());

    }

    String ab = "\t";
    String d = "\t";
    int cound = 0;

    private void readDataBindView() {
//        String question = mQuestionSqliteVo.getQuestionStr();
        final String question = mQuestionSqliteVo.getQuestionString();
        mQuestionType = mQuestionSqliteVo.getQuestiontype();
        //题干信息
        mIvGmZoneQuestintype.setImageDrawable(getQuestionType());
         int width = mIvGmZoneQuestintype.getLayoutParams().width+20;
        if (mQuestionSqliteVo.getQuestiontype() == 5) {
            mTvGmZoneQuestionTitle.setText(question);
        } else {
            cound = 20;
            String getwith = getwith(20, width);
            String concat = getwith.concat(question);
            mTvGmZoneQuestionTitle.setText(concat);
            cound = 0;
            ab = "\t";
//            String concat = DataMessageVo.empty.concat(question);
//        mTvGmZoneQuestionTitle.setText(getString(question));
//            mTvGmZoneQuestionTitle.setText(concat);
        }
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

    private String getwith(int wd, int width) {
        if (wd==0||width==0)return ab;
        if (cound < width) {
            ab = ab.concat(d);
            cound += wd;
            getwith(wd, width);
        }
        return ab+":";
    }

    /**
     * 绑定考点
     */
    private void bindKaoDian() {
        mFlowlayoutGmTwo.removeAllViews();
//        String keywordStr = mQuestionSqliteVo.getKeywordStr();
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
        mBtnGmoneSuerAnswer.setVisibility(isShow ? View.VISIBLE : View.GONE);
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

        mSubsciber = new GmReadFragment().subsciber();
        mSubsciber.attch(this);
        mSubsciber.notifyChanger();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_gmone_suer_answer:
                if (mDuoXunaSure) {
                    T.showToast(mContext, "请选择试题答案");
                    return;
                }
                mDuoXunaSure = true;
                mBtnGmoneSuerAnswer.setVisibility(View.GONE);
                setSelectABCDEClick(false);
                doSelectMoreEvent();
                doRadioEventView();
                doSaveMoreEvent();
                break;
            case R.id.btn_b_buy://购买
                Intent intent1 = BankBuyActivity.newInstance(mContext, String.valueOf(mQuestionSqliteVo.getCourseid()), "");
                startActivity(intent1);
                break;
            case R.id.ll_gmone_a_layout://a
                if (mQuestionType == 2) {//处理单选
                    doRadioItemBgEvent(true, false, false, false, false);
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
                    setSureKeyBg();
                    setSelectMoreItemBG(mSelectA, mSelectMorisClickA);
                } else if (mQuestionType == 4) {//简答
                }

                break;
            case R.id.ll_gmone_b_layout://b
                if (mQuestionType == 2) {//处理单选
                    doRadioItemBgEvent(false, true, false, false, false);
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
                    setSureKeyBg();
                    setSelectMoreItemBG(mSelectB, mSelectMorisClickB);

                } else if (mQuestionType == 4) {//简答
                }

                break;
            case R.id.ll_gmone_c_layout://c
                if (mQuestionType == 2) {//处理单选
                    doRadioItemBgEvent(false, false, true, false, false);
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
                    setSureKeyBg();
                    setSelectMoreItemBG(mSelectC, mSelectMorisClickC);
                } else if (mQuestionType == 4) {//简答
                }
                break;
            case R.id.ll_gmone_d_layout://d
                if (mQuestionType == 2) {//处理单选
                    doRadioItemBgEvent(false, false, false, true, false);
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
                    setSureKeyBg();
                    setSelectMoreItemBG(mSelectD, mSelectMorisClickD);

                } else if (mQuestionType == 4) {//简答
                }
                break;
            case R.id.ll_gmOne_e_layout://e
                if (mQuestionType == 2) {//处理单选
                    doRadioItemBgEvent(false, false, false, false, true);
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
                    setSureKeyBg();
                    setSelectMoreItemBG(mSelectE, mSelectMorisClickE);
                } else if (mQuestionType == 4) {//简答
                }

                break;
            case R.id.btn_gmseven_answer:
                break;

            case R.id.ll_gm_three_note_bg://笔记
                Intent intent = NoteMakeActivity.start_Intent(mContext, mQuestionSqliteVo.getQuestion_id(),
                        mNote_id, mQuestionSqliteVo.getCourseid(),
//                        mNoteContent, mQuestionSqliteVo.getQuestionStr(), mQuestionSqliteVo.getKeywordStr(), 1);
                        mNoteContent, mQuestionSqliteVo.getQuestionString(), mQuestionSqliteVo.getKeywordsString(), 1);
                mContext.startActivity(intent);
                break;
            case R.id.iv_gmfour_evaluate://评价
                submitEvent();
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

    private void submitEvent() {
        if (mNewTextActivity != null) {//章节练习
            mNewTextActivity.changerBarView(true);
        }
        if (mOrderTestActivity != null) {//顺序练习
            mOrderTestActivity.changerBarView(true);
        }
        if (mFreedomTestActivity != null) {//自由组卷
            mFreedomTestActivity.changerBarView(true);
        }
        if (mSpecialTextActivity != null) {//专项练习
            mSpecialTextActivity.changerBarView(true);
        }
        if (mNoteLookActivity != null) {
            mNoteLookActivity.changerBarView(true);
        }

        if (mErrorTextActivity != null) {
            mErrorTextActivity.changerBarView(true);
        }
        if (mColloerTextActivity != null) {
            mColloerTextActivity.changerBarView(true);
        }

    }

    private void doSaveMoreEvent() {
        //0为未选，1为正确 ，2 为漏选 ，3为错误
        DoBankSqliteVo vo = new DoBankSqliteVo();
        setSelectABCDEClick(false);
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
        vo.setTime(String.valueOf(new Date().getTime()));
        ErrorSqliteVo error = null;
        ErrorSqliteVo right = null;
        if (vo.getIsright() == 3 || vo.getIsright() == 2) {
            //错题表
            error = new ErrorSqliteVo();
            error.setChapterid(mQuestionSqliteVo.getChapter_id());
            error.setCourseid(mQuestionSqliteVo.getCourseid());
            error.setQuesitonid(mQuestionSqliteVo.getQuestion_id());
            error.setUserid(String.valueOf(mSaffid));
            error.setRightnumber(0);
            error.setTime(String.valueOf(new Date().getTime()));
        } else if (vo.getIsright() == 1) {
            right = new ErrorSqliteVo();
            right.setChapterid(mQuestionSqliteVo.getChapter_id());
            right.setCourseid(mQuestionSqliteVo.getCourseid());
            right.setQuesitonid(mQuestionSqliteVo.getQuestion_id());
            right.setUserid(String.valueOf(mSaffid));
            right.setRightnumber(0);
            right.setTime(String.valueOf(new Date().getTime()));
        }
        doSaveEvent(vo, error, right);
        doNextGo(vo.getIsright() == 1);
    }

    /**
     * 自动下一题
     *
     * @param b
     */
    private void doNextGo(boolean b) {
        if (StringUtil.isEmpty(mUserNextGo)) {
            return;
        }
        if (!b) return;
        //初始化选项图片
        if (mNewTextActivity != null) {//章节练习
            mNewTextActivity.doRightGo();
        }
        if (mOrderTestActivity != null) {//顺序练习
            mOrderTestActivity.doRightGo();
        }
        if (mFreedomTestActivity != null) {//自由组卷
            mFreedomTestActivity.doRightGo();
        }
        if (mSpecialTextActivity != null) {//专项练习
            mSpecialTextActivity.doRightGo();
        }
        if (mNoteLookActivity != null) {
            mNoteLookActivity.doRightGo();
        }
        if (mErrorTextActivity != null) {
            mErrorTextActivity.doRightGo();
        }
        if (mColloerTextActivity != null) {
            mColloerTextActivity.doRightGo();
        }

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
                setRadioButtonChanger(mRdbGmpopOne, 1);
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
            case 4:
                if (event.getNumber() == mPostion) {
                    showShareLayout();
                }
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
  /*              if (number.equals(DataMessageVo.FONT_ONE)) {
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
*/
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
//                            mShowDayOrNight = DataMessageVo.PATTERN_ONE;
//                            mUserDbHelp.upDataDayOrNightOrEye(DataMessageVo.PATTERN_ONE);
//                            mGmReadColorManger.setGmBgColor(GmReadColorManger.DAYTIME);
//                            mSubsciber.notifyChanger();
                        }

                        break;
                    case 2://夜间
                        if (isChecked) {
//                            mShowDayOrNight = DataMessageVo.PATTERN_TWO;
                            checkBox.setChecked(true);
//                            mUserDbHelp.upDataDayOrNightOrEye(DataMessageVo.PATTERN_TWO);
//                            mGmReadColorManger.setGmBgColor(GmReadColorManger.NIGHT);
//                            mSubsciber.notifyChanger();
                        } /*else {
                            setDay();
                        }*/
                        break;
                    case 3://护眼
                        if (isChecked) {
//                            mShowDayOrNight = DataMessageVo.PATTERN_THREE;
                            checkBox.setChecked(true);
//                            mUserDbHelp.upDataDayOrNightOrEye(DataMessageVo.PATTERN_THREE);
//                            mGmReadColorManger.setGmBgColor(GmReadColorManger.EYE);
//                            mSubsciber.notifyChanger();
                        } /*else {
                            setDay();
                        }*/
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
        initTitleRed();
    }


    /**
     * 处理选择后得效果
     */
    private void doRadioEventView() {
        //判断用户是否购买
        doShowNoBuy(mUserBuy);
    }

    private void doShowNoBuy(boolean buy) {
        //笔记
        mLlGmthreeNoteLayout.setVisibility(buy ? View.VISIBLE : View.GONE);
        //解析
        mLlGmTwoAnalysisLayout.setVisibility(buy ? View.VISIBLE : View.GONE);
        //评价
        mLlFourEvaluateLayout.setVisibility(buy ? View.VISIBLE : View.GONE);
        //购买
        mLiBResolveBuy.setVisibility(buy ? View.GONE : View.VISIBLE);
        //答案
        mLlGmoneAnswerLayout.setVisibility(View.VISIBLE);

        doOpenNewWorkEvent();
    }

    private void doOpenNewWorkEvent() {
        if (!doOpenNewWork()) {
            mLlFourEvaluateLayout.setVisibility(View.GONE);
            mLlGmthreeNoteLayout.setVisibility(View.GONE);
        }
    }

    private boolean doOpenNewWork() {
        return PolyvSDKUtil.isOpenNetwork(mContext);
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
        setSelectABCDEClick(false);
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
        //做题记录表
        ErrorSqliteVo error = null;
        ErrorSqliteVo rightvo = null;
        if (right == 3) {
            //错题表
            error = new ErrorSqliteVo();
            error.setChapterid(mQuestionSqliteVo.getChapter_id());
            error.setCourseid(mQuestionSqliteVo.getCourseid());
            error.setQuesitonid(mQuestionSqliteVo.getQuestion_id());
            error.setUserid(String.valueOf(mSaffid));
            error.setRightnumber(0);
            error.setTime(String.valueOf(new Date().getTime()));
        } else {
            rightvo = new ErrorSqliteVo();
            rightvo.setChapterid(mQuestionSqliteVo.getChapter_id());
            rightvo.setCourseid(mQuestionSqliteVo.getCourseid());
            rightvo.setQuesitonid(mQuestionSqliteVo.getQuestion_id());
            rightvo.setUserid(String.valueOf(mSaffid));
            rightvo.setRightnumber(0);
            rightvo.setTime(String.valueOf(new Date().getTime()));

        }
        doSaveEvent(vo, error, rightvo);
        doNextGo(vo.getIsright() == 1);
    }

    /**
     * 保存用户做题记录
     *
     * @param vo    做题临时表
     * @param error 错题记录表
     */
    private void doSaveEvent(DoBankSqliteVo vo, ErrorSqliteVo error, ErrorSqliteVo right) {
        if (mNewTextActivity != null) {
            mNewTextActivity.saveUserDoLog(vo);
            mNewTextActivity.doSaveErrorLog(error);
        }
        if (mOrderTestActivity != null) {
            mOrderTestActivity.saveUserDoLog(vo);
            mOrderTestActivity.doSaveErrorLog(error);
        }
        if (mFreedomTestActivity != null) {
            mFreedomTestActivity.saveUserDoLog(vo);
            mFreedomTestActivity.doSaveErrorLog(error);
        }
        if (mSpecialTextActivity != null) {
            mSpecialTextActivity.saveUserDoLog(vo);
            mSpecialTextActivity.doSaveErrorLog(error);
        }
        if (mNoteLookActivity != null) {
            mNoteLookActivity.saveUserDoLog(vo);
            mNoteLookActivity.doSaveErrorLog(error);
        }
        if (mErrorTextActivity != null) {
            mErrorTextActivity.saveUserDoLog(vo);
            mErrorTextActivity.upDataSaveErrorLog(right);
        }
        if (mColloerTextActivity != null) {
            mColloerTextActivity.saveUserDoLog(vo);
            mColloerTextActivity.doSaveErrorLog(error);
        }


    }

    /**
     * 处理选中后得对错
     *
     * @param a    选项
     * @param type 类型 2 单选其它是多选
     */
    private void doRightErrorBg(String a, int type) {
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
        initTitleRed();
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
            mBtnGmoneSuerAnswer.setClickable(true);
            mDuoXunaSure = false;
            mBtnGmoneSuerAnswer.setTextColor(mGmTextUtil.getColorDrawable(R.color.white));
            mBtnGmoneSuerAnswer.setBackgroundResource(R.drawable.btn_b_sure_s);
        } else {
            mDuoXunaSure = true;
            mBtnGmoneSuerAnswer.setClickable(true);
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
            imageView.setImageDrawable(mGmImgManageUtil.getDrawable(R.mipmap.qbank_answer_difficulty_s));
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
        mShowMoreView = true;
        T_ERROR(mContext);
    }

    @Override
    public void EvaluesMoreSuccess(String sucess) {
        mShowMoreView = true;
        mLlGmfourMoreData.setVisibility(View.GONE);
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
        mShowMoreView = true;
        mLlGmfourMoreData.setVisibility(View.GONE);
        T_ERROR(mContext);
    }

    //初始笔记
    private void initNoteView() {
        if (!doOpenNewWork()) return;
        mGmEvaluePresenter.reqeustNoteByQuestion(mContext, mQuestionSqliteVo.getQuestion_id());
    }

    /**
     * testing
     * 笔记
     *
     * @param success
     */
    @Override
    public void NoTeByQuestionSuccess(String success) {
        QuestionNoteVo noteVo = Utils.getGosnT(success, QuestionNoteVo.class);
        if (noteVo.getStatus().getCode() == 200) {
            if (noteVo.getData() != null) {
                mNote_id = noteVo.getData().getId();
                mNoteContent = noteVo.getData().getContent();
                if (StringUtil.isEmpty(mNoteContent)) {
                    setNoteViewShowHine(false);
                } else {
                    mTvGmThreeNoteContent.setText(mNoteContent);
                    setNoteViewShowHine(true);
                }
            } else {
                setNoteViewShowHine(false);
            }
        } else {
            T_ERROR(mContext);
        }

    }

    /**
     * 是否显示笔记
     *
     * @param isShow
     */
    public void setNoteViewShowHine(boolean isShow) {
        mTvGmThreeNote.setVisibility(isShow ? View.GONE : View.VISIBLE);
        mTvGmThreeNoteContent.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void NoteByQuestionError(String msg) {
        T_ERROR(mContext);
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
                        if (getActivity() instanceof NewTextActivity) {//章节练习
                            mNewTextActivity = (NewTextActivity) getActivity();
//                            ShareUtils.shareImg(mNewTextActivity, mQuestionSqliteVo.getQuestionStr(),
                            ShareUtils.shareImg(mNewTextActivity, mQuestionSqliteVo.getQuestionString(),
                                    pic, SHARE_MEDIA.QQ);
                        }
                        if (getActivity() instanceof OrderTestActivity) {//顺序练习
                            mOrderTestActivity = (OrderTestActivity) getActivity();
                            ShareUtils.shareImg(mOrderTestActivity, mQuestionSqliteVo.getQuestionString(),
//                            ShareUtils.shareImg(mOrderTestActivity, mQuestionSqliteVo.getQuestionStr(),
                                    pic, SHARE_MEDIA.QQ);
                        }
                        if (getActivity() instanceof FreedomTestActivity) {//自由组卷
                            mFreedomTestActivity = (FreedomTestActivity) getActivity();
//                            ShareUtils.shareImg(mFreedomTestActivity, mQuestionSqliteVo.getQuestionStr(),
                            ShareUtils.shareImg(mFreedomTestActivity, mQuestionSqliteVo.getQuestionString(),
                                    pic, SHARE_MEDIA.QQ);
                        }
                        if (getActivity() instanceof SpecialTextActivity) {//专项练习
                            mSpecialTextActivity = (SpecialTextActivity) getActivity();
//                            ShareUtils.shareImg(mSpecialTextActivity, mQuestionSqliteVo.getQuestionStr(),
                            ShareUtils.shareImg(mSpecialTextActivity, mQuestionSqliteVo.getQuestionString(),
                                    pic, SHARE_MEDIA.QQ);
                        }
                        if (getActivity() instanceof NoteLookActivity) {
                            mNoteLookActivity = (NoteLookActivity) getActivity();
//                            ShareUtils.shareImg(mNoteLookActivity, mQuestionSqliteVo.getQuestionStr(),
                            ShareUtils.shareImg(mNoteLookActivity, mQuestionSqliteVo.getQuestionString(),
                                    pic, SHARE_MEDIA.QQ);
                        }
                        if (getActivity() instanceof ErrorTextActivity) {
                            mErrorTextActivity = (ErrorTextActivity) getActivity();
//                            ShareUtils.shareImg(mErrorTextActivity, mQuestionSqliteVo.getQuestionStr(),
                            ShareUtils.shareImg(mErrorTextActivity, mQuestionSqliteVo.getQuestionString(),
                                    pic, SHARE_MEDIA.QQ);
                        }
                        if (getActivity() instanceof ColloerTextActivity) {
                            mColloerTextActivity = (ColloerTextActivity) getActivity();
//                            ShareUtils.shareImg(mColloerTextActivity, mQuestionSqliteVo.getQuestionStr(),
                            ShareUtils.shareImg(mColloerTextActivity, mQuestionSqliteVo.getQuestionString(),
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
                        if (getActivity() instanceof NewTextActivity) {//章节练习
                            mNewTextActivity = (NewTextActivity) getActivity();
//                            ShareUtils.shareImg(mNewTextActivity, mQuestionSqliteVo.getQuestionStr(),
                            ShareUtils.shareImg(mNewTextActivity, mQuestionSqliteVo.getQuestionString(),
                                    pic, SHARE_MEDIA.QZONE);
                        }
                        if (getActivity() instanceof OrderTestActivity) {//顺序练习
                            mOrderTestActivity = (OrderTestActivity) getActivity();
//                            ShareUtils.shareImg(mOrderTestActivity, mQuestionSqliteVo.getQuestionStr(),
                            ShareUtils.shareImg(mOrderTestActivity, mQuestionSqliteVo.getQuestionString(),
                                    pic, SHARE_MEDIA.QZONE);
                        }
                        if (getActivity() instanceof FreedomTestActivity) {//自由组卷
                            mFreedomTestActivity = (FreedomTestActivity) getActivity();
//                            ShareUtils.shareImg(mFreedomTestActivity, mQuestionSqliteVo.getQuestionStr(),
                            ShareUtils.shareImg(mFreedomTestActivity, mQuestionSqliteVo.getQuestionString(),
                                    pic, SHARE_MEDIA.QZONE);
                        }
                        if (getActivity() instanceof SpecialTextActivity) {//专项练习
                            mSpecialTextActivity = (SpecialTextActivity) getActivity();
//                            ShareUtils.shareImg(mSpecialTextActivity, mQuestionSqliteVo.getQuestionStr(),
                            ShareUtils.shareImg(mSpecialTextActivity, mQuestionSqliteVo.getQuestionString(),
                                    pic, SHARE_MEDIA.QZONE);
                        }
                        if (getActivity() instanceof NoteLookActivity) {
                            mNoteLookActivity = (NoteLookActivity) getActivity();
//                            ShareUtils.shareImg(mSpecialTextActivity, mQuestionSqliteVo.getQuestionStr(),
                            ShareUtils.shareImg(mSpecialTextActivity, mQuestionSqliteVo.getQuestionString(),
                                    pic, SHARE_MEDIA.QZONE);
                        }
                        if (getActivity() instanceof ErrorTextActivity) {
                            mErrorTextActivity = (ErrorTextActivity) getActivity();
//                            ShareUtils.shareImg(mErrorTextActivity, mQuestionSqliteVo.getQuestionStr(),
                            ShareUtils.shareImg(mErrorTextActivity, mQuestionSqliteVo.getQuestionString(),
                                    pic, SHARE_MEDIA.QZONE);
                        }
                        if (getActivity() instanceof ColloerTextActivity) {
                            mColloerTextActivity = (ColloerTextActivity) getActivity();
//                            ShareUtils.shareImg(mColloerTextActivity, mQuestionSqliteVo.getQuestionStr(),
                            ShareUtils.shareImg(mColloerTextActivity, mQuestionSqliteVo.getQuestionString(),
                                    pic, SHARE_MEDIA.QZONE);
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
                        if (getActivity() instanceof NewTextActivity) {//章节练习
                            mNewTextActivity = (NewTextActivity) getActivity();
//                            ShareUtils.shareImg(mNewTextActivity, mQuestionSqliteVo.getQuestionStr(),
                            ShareUtils.shareImg(mNewTextActivity, mQuestionSqliteVo.getQuestionString(),
                                    pic, SHARE_MEDIA.SINA);
                        }
                        if (getActivity() instanceof OrderTestActivity) {//顺序练习
                            mOrderTestActivity = (OrderTestActivity) getActivity();
                            ShareUtils.shareImg(mOrderTestActivity, mQuestionSqliteVo.getKeywordsString(),
                                    pic, SHARE_MEDIA.SINA);
                        }
                        if (getActivity() instanceof FreedomTestActivity) {//自由组卷
                            mFreedomTestActivity = (FreedomTestActivity) getActivity();
                            ShareUtils.shareImg(mFreedomTestActivity, mQuestionSqliteVo.getKeywordsString(),
                                    pic, SHARE_MEDIA.SINA);
                        }
                        if (getActivity() instanceof SpecialTextActivity) {//专项练习
                            mSpecialTextActivity = (SpecialTextActivity) getActivity();
                            ShareUtils.shareImg(mSpecialTextActivity, mQuestionSqliteVo.getKeywordsString(),
                                    pic, SHARE_MEDIA.SINA);
                        }
                        if (getActivity() instanceof NoteLookActivity) {
                            mNoteLookActivity = (NoteLookActivity) getActivity();
                            ShareUtils.shareImg(mNoteLookActivity, mQuestionSqliteVo.getKeywordsString(),
                                    pic, SHARE_MEDIA.SINA);
                        }
                        if (getActivity() instanceof ErrorTextActivity) {
                            mErrorTextActivity = (ErrorTextActivity) getActivity();
                            ShareUtils.shareImg(mErrorTextActivity, mQuestionSqliteVo.getKeywordsString(),
                                    pic, SHARE_MEDIA.SINA);
                        }
                        if (getActivity() instanceof ColloerTextActivity) {
                            mColloerTextActivity = (ColloerTextActivity) getActivity();
                            ShareUtils.shareImg(mColloerTextActivity, mQuestionSqliteVo.getKeywordsString(),
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
                        if (getActivity() instanceof NewTextActivity) {//章节练习
                            mNewTextActivity = (NewTextActivity) getActivity();
                            ShareUtils.shareImg(mNewTextActivity, mQuestionSqliteVo.getKeywordsString(),
                                    pic, SHARE_MEDIA.WEIXIN);
                        }
                        if (getActivity() instanceof OrderTestActivity) {//顺序练习
                            mOrderTestActivity = (OrderTestActivity) getActivity();
                            ShareUtils.shareImg(mOrderTestActivity, mQuestionSqliteVo.getKeywordsString(),
                                    pic, SHARE_MEDIA.WEIXIN);
                        }
                        if (getActivity() instanceof FreedomTestActivity) {//自由组卷
                            mFreedomTestActivity = (FreedomTestActivity) getActivity();
                            ShareUtils.shareImg(mFreedomTestActivity, mQuestionSqliteVo.getKeywordsString(),
                                    pic, SHARE_MEDIA.WEIXIN);
                        }
                        if (getActivity() instanceof SpecialTextActivity) {//专项练习
                            mSpecialTextActivity = (SpecialTextActivity) getActivity();
                            ShareUtils.shareImg(mSpecialTextActivity, mQuestionSqliteVo.getKeywordsString(),
                                    pic, SHARE_MEDIA.WEIXIN);
                        }
                        if (getActivity() instanceof NoteLookActivity) {
                            mNoteLookActivity = (NoteLookActivity) getActivity();
                            ShareUtils.shareImg(mNoteLookActivity, mQuestionSqliteVo.getKeywordsString(),
                                    pic, SHARE_MEDIA.WEIXIN);
                        }
                        if (getActivity() instanceof ErrorTextActivity) {
                            mErrorTextActivity = (ErrorTextActivity) getActivity();
                            ShareUtils.shareImg(mErrorTextActivity, mQuestionSqliteVo.getKeywordsString(),
                                    pic, SHARE_MEDIA.WEIXIN);
                        }
                        if (getActivity() instanceof ColloerTextActivity) {
                            mColloerTextActivity = (ColloerTextActivity) getActivity();
                            ShareUtils.shareImg(mColloerTextActivity, mQuestionSqliteVo.getKeywordsString(),
                                    pic, SHARE_MEDIA.WEIXIN);
                        }


                        EventBus.getDefault().postSticky(new GmChangerColorEvent(0, 5, false));
                        getPopupWindow().dismiss();
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
                        if (getActivity() instanceof NewTextActivity) {//章节练习
                            mNewTextActivity = (NewTextActivity) getActivity();
                            ShareUtils.shareImg(mNewTextActivity, mQuestionSqliteVo.getKeywordsString(),
                                    pic, SHARE_MEDIA.WEIXIN_CIRCLE);
                        }
                        if (getActivity() instanceof OrderTestActivity) {//顺序练习
                            mOrderTestActivity = (OrderTestActivity) getActivity();
                            ShareUtils.shareImg(mNewTextActivity, mQuestionSqliteVo.getKeywordsString(),
                                    pic, SHARE_MEDIA.WEIXIN_CIRCLE);
                        }
                        if (getActivity() instanceof FreedomTestActivity) {//自由组卷
                            mFreedomTestActivity = (FreedomTestActivity) getActivity();
                            ShareUtils.shareImg(mFreedomTestActivity, mQuestionSqliteVo.getKeywordsString(),
                                    pic, SHARE_MEDIA.WEIXIN_CIRCLE);
                        }
                        if (getActivity() instanceof SpecialTextActivity) {//专项练习
                            mSpecialTextActivity = (SpecialTextActivity) getActivity();
                            ShareUtils.shareImg(mSpecialTextActivity, mQuestionSqliteVo.getKeywordsString(),
                                    pic, SHARE_MEDIA.WEIXIN_CIRCLE);
                        }
                        if (getActivity() instanceof NoteLookActivity) {
                            mNoteLookActivity = (NoteLookActivity) getActivity();
                            ShareUtils.shareImg(mNoteLookActivity, mQuestionSqliteVo.getKeywordsString(),
                                    pic, SHARE_MEDIA.WEIXIN_CIRCLE);
                        }
                        if (getActivity() instanceof ErrorTextActivity) {
                            mErrorTextActivity = (ErrorTextActivity) getActivity();
                            ShareUtils.shareImg(mErrorTextActivity, mQuestionSqliteVo.getKeywordsString(),
                                    pic, SHARE_MEDIA.WEIXIN_CIRCLE);
                        }
                        if (getActivity() instanceof ColloerTextActivity) {
                            mColloerTextActivity = (ColloerTextActivity) getActivity();
                            ShareUtils.shareImg(mColloerTextActivity, mQuestionSqliteVo.getKeywordsString(),
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

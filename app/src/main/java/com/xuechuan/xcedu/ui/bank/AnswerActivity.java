package com.xuechuan.xcedu.ui.bank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.bank.AnswerEvaluateAdapter;
import com.xuechuan.xcedu.adapter.bank.GridViewAdapter;
import com.xuechuan.xcedu.adapter.bank.GridViewResultAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DbHelperAssist;
import com.xuechuan.xcedu.db.UserInfomDb;
import com.xuechuan.xcedu.event.EvalueTwoEvent;
import com.xuechuan.xcedu.event.FreeDataEvent;
import com.xuechuan.xcedu.mvp.model.AllQuestionModelImpl;
import com.xuechuan.xcedu.mvp.model.AnswerModelImpl;
import com.xuechuan.xcedu.mvp.model.ErrOrColListModelImpl;
import com.xuechuan.xcedu.mvp.model.EvalueModelImpl;
import com.xuechuan.xcedu.mvp.model.SpecailDetailModelImpl;
import com.xuechuan.xcedu.mvp.presenter.AllQuestionPresenter;
import com.xuechuan.xcedu.mvp.presenter.AnswerPresenter;
import com.xuechuan.xcedu.mvp.presenter.ErrOrColListPresenter;
import com.xuechuan.xcedu.mvp.presenter.EvaluePresenter;
import com.xuechuan.xcedu.mvp.presenter.SpecailDetailPresenter;
import com.xuechuan.xcedu.mvp.view.AllQuestionView;
import com.xuechuan.xcedu.mvp.view.AnswerView;
import com.xuechuan.xcedu.mvp.view.ErrOrColListView;
import com.xuechuan.xcedu.mvp.view.EvalueView;
import com.xuechuan.xcedu.mvp.view.SpecailDetailView;
import com.xuechuan.xcedu.mvp.view.TimeShowView;
import com.xuechuan.xcedu.ui.EvalueTwoActivity;
import com.xuechuan.xcedu.utils.AnswerCardUtil;
import com.xuechuan.xcedu.utils.ArithUtil;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.MyTimeUitl;
import com.xuechuan.xcedu.utils.ScreenShot;
import com.xuechuan.xcedu.utils.ShareUtils;
import com.xuechuan.xcedu.utils.SharedSeletResultListUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.SuppertUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.TimeTools;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.Db.UserLookVo;
import com.xuechuan.xcedu.vo.ErrOrColListVoNew;
import com.xuechuan.xcedu.vo.EvalueNewVo;
import com.xuechuan.xcedu.vo.QuestionAllVo;
import com.xuechuan.xcedu.vo.QuestionAllVoNew;
import com.xuechuan.xcedu.vo.QuestionsBean;
import com.xuechuan.xcedu.vo.ResultVo;
import com.xuechuan.xcedu.vo.TextDetailVo;
import com.xuechuan.xcedu.vo.UseSelectItemInfomVo;
import com.xuechuan.xcedu.weight.CommonPopupWindow;
import com.xuechuan.xcedu.weight.MyRecyclerView;
import com.xuechuan.xcedu.weight.SmartScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


/**
 * @version V 1.0 xxxxxxxx
 * @Title: AnswerActivity
 * @Package com.xuechuan.xcedu.ui.bank
 * @Description: 答题界面
 * @author: L-BackPacker
 * @date: 2018/5/9 19:23
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/9
 */
public class AnswerActivity extends BaseActivity implements View.OnClickListener, AnswerView,
        EvalueView, AllQuestionView, SpecailDetailView, ErrOrColListView, TimeShowView {
    /**
     * 科目id
     */
    private static final String MTYPEID = "curseid";
    /***
     * 标签id
     */
    private static final String TAGEIDDATA = "tageiddata";
    /***
     * 标签id
     */
    private static final String TAGEIDDATAUSER = "tageiddatauser";
    /**
     * 收藏和错题类型 err 错题 fav收藏
     */
    private static final String TAGYTTPE = "typetag";
    /**
     * 考试类型
     */
    private static final String STYLECASE = "STYLECASE";
    private static final String TAG = "====================";
    private Context mContext;
    /**
     * 问题父id
     */
    private static String QUESTIONID = "courseid";

    /**
     * 搜索章节id
     */
    private static String SEARCHZID = "SearchZid";
    /**
     * 搜索题id
     */
    private static String SEARCHTID = "SearchTid";
    /**
     * 搜索类型
     */
    private static String SEARCHTYPE = "SearchTYPE";
    /**
     * 当前题干信息
     */
    private TextDetailVo mTextDetialNew;
    /**
     * 用户选择结果
     */
    private ArrayList<UseSelectItemInfomVo> mSeletList;
    /**
     * 用户查看解析
     */

    private String mOid;
    /**
     * 第几题
     */
    private static String RNUMMARK = "rnum";

    // 第几题
    private int mMark = 0;
    //题目数据
    private List<QuestionsBean> mTextDetial;
    private AlertDialog dialog;

    //用户是否点击下一题
    private boolean isNext;
    private String A = "a";
    private String B = "b";
    private String C = "c";
    private String D = "d";
    private String E = "e";
    //记录用户选中的选项 （用于单选）
    private String mSelectOnlyitem = null;
    //多选状况
    private String mSelectMorItemA = null;
    private String mSelectMorItemB = null;
    private String mSelectMorItemC = null;
    private String mSelectMorItemD = null;
    private String mSelectMorItemE = null;
    //记录用户是否点击选项
    private boolean isClickA;
    private boolean isClickB;
    private boolean isClickC;
    private boolean isClickD;
    private boolean isClickE;
    //用户是否确认多选
    private boolean isSure = true;
    //记录用户是否点击自动下一题
    private boolean mSelectNext = false;
    //用户选中的item
    private String mRightItem = null;
    //问题题干类型
    private String mTitleType = null;
    private final String mTitleTypeOnly = "only";
    private final String mTitleTypeMore = "more";
    private final String mTitleTypeWrite = "write";
    //评价数据集合
    private List mArray;

    private String mSelectViewBgZC = "zc";
    private String mSelectViewBgZCC = "zc";
    public static final String mSelectViewBgHY = "hy";
    public static final String mSelectViewBgYJ = "yj";
    private CommonPopupWindow popMore;
    private CommonPopupWindow popSetting;
    private CommonPopupWindow popAnswer;

    private CheckBox mSwtNext;
    //用户是否提交
    private boolean isSubmit;

    //剪切答案集合
    private ArrayList<String> list;
    private AnswerPresenter mPresnter;
    private AnswerEvaluateAdapter adapter;
    private ImageView mIvBMore;
    private TextView mTvRootEmpty;
    private TextView mTvBType;
    private TextView mTvBMatter;
    private ImageView mIvBA;
    private TextView mTvBAContent;
    private LinearLayout mLlBASelect;
    private ImageView mIvBB;
    private TextView mTvBBContent;
    private LinearLayout mLlBBSelect;
    private ImageView mIvBC;
    private TextView mTvBCContent;
    private LinearLayout mLlBCselect;
    private ImageView mIvBD;
    private TextView mTvBDContent;
    private LinearLayout mLlBDSelect;
    private ImageView mIvBE;
    private TextView mTvBEContent;
    private LinearLayout mLlBESelect;
    private Button mBtnBSureKey;
    private TextView mTvBAnswer;
    private LinearLayout mLlBAnswerKey;
    private TextView mTvBRosoleContent;
    private LinearLayout mLlBJiexi;
    private TextView mTvBAccuracy;
    private LinearLayout mLlBRightLu;
    private Button mBtnBBuy;
    private LinearLayout mLiBResolveBuy;
    private MyRecyclerView mRlvEualeContent;
    //    private RecyclerView mRlvEualeContent;
    private SmartScrollView mSloViewShow;
    private LinearLayout mLlBBack;
    private LinearLayout mLlBGo;
    private ImageView mIvBExpand;
    private TextView mTvBNew;
    private TextView mTvBCount;
    private CheckBox mChbBCollect;
    private LinearLayout mLlRootLayout;


    //当前结果
    private TextDetailVo.DataBean mResultData;
    private ImageView mIvBStar1;
    private ImageView mIvBStar2;
    private ImageView mIvBStar3;
    private ImageView mIvBStar4;
    private ImageView mIvBStar5;
    private LinearLayout mLlMoreData;
    private LinearLayout mLiXia;
    //加载数据
    private boolean isMoreData = true;
    private TextView mTvAnswerNumberevlue;
    private TextView mTvAnswerAddevlua;
    private EditText mEtBSubmit;
    private ImageView mIvBSubmitSend;
    private LinearLayout mLlBSubmitEvalue;
    private EvaluePresenter mEvaluePresenter;
    private AlertDialog mSubmitDialog;
    private ImageView mIvTitleBack;
    private static TextView mActivityTitleText;
    private LinearLayout mLlTitleBar;
    private TextView mTvAnswerAnswer;
    private TextView mTvAnswerJiexi;
    //护眼
    private boolean isEye = false;
    //夜间
    private boolean isNight = false;


    private View mVLine;
    private View mVLine2;
    private View mVLine3;
    private View mVLine1;
    private RelativeLayout mRlRootLayout;
    private Button mBtnLookWenAnswer;
    private View mVLine4;
    private TextView mTvLookAnswerCan;
    private TextView mTvLookAnswerWen;
    private LinearLayout mLlLookWenDa;
    private LinearLayout mLlSelectRootLayout;
    private RelativeLayout mRlLookEvalue;
    /**
     * 做题时间
     */
    private String mDoTime = "00:00:00";
    /**
     * 用户是否购买
     */
    private boolean isBuy = false;
    /**
     * 章节标识
     */
    private static String TYPEMARK = "typemark";

    private CommonPopupWindow popShare;
    /**
     * 1 技术 2 综合 3 案例
     */
    private String mTypeMark;

    //是否是模拟考试 ture 隐藏提交答题按钮
    private boolean isExamHine = false;
    //查看解析后的数据
    private String mResultMark;
    private CommonPopupWindow popResult;
    //科目id
    private String mTypeId;
    private AllQuestionPresenter mAllQuestion;
    private String startTime = "00:00:00";
    /**
     * 科目id
     */
    private static String COURSEIDKEMU = "coureshidkumu";
    /**
     * 科目id
     */
    private static String COURSEIDKEMUUSER = "courseidkemuuser";
    /**
     * 科目
     */
    private String mCouresidKuMu;
    /**
     * 标签id
     */
    private String mTagid;
    private SpecailDetailPresenter mDetailPresenter;
    private ErrOrColListPresenter mListPresenter;
    /**
     * 题干类型收藏fav/错题err
     */
    private String mTagType;
    //错题
    public static final String ERRTYPE = "err";
    //收藏
    public static final String FAVTYPE = "fav";
    private String mCouresidUser;
    //用户收藏或者错题集合
    private String mUserTagid;
    /**
     * 用户收藏题号信息
     */
    private List<Integer> mUserData;
    private UserInfomDb mInfomDb;
    /**
     * 试题类型
     */
    private String mStyleCase;
    /**
     * 用户是否查看解析
     */
    private boolean isUserLookWenJieXi;
    /**
     * 用户提交卷否查看解析
     */
    private boolean isUserLookResultJieXi = false;
    /**
     * 是否自由组卷
     */
    private String mFreeQuestion = "";
    /**
     * 是否自由组卷
     */
    private String mFreeQuestionTrue = "true";
    /**
     * 是否自由组卷
     */
    private String mFreeQuestionFalse = "false";
    private MyTimeUitl mTimeUitl;
    private ImageView mIvTimePlay;
    private LinearLayout mVBLineBar;
    private ImageView mIvBarDelect;
    private String mSearchZid;
    private String mSearchTid;
    private String mSearchType;
    private TextView mTvNam;
    private ImageView mIvJianPan;
    private boolean isBtnClickDel = false;
    private AlertDialog mStopdialog;
    private AlertDialog mStopAlertDialog;
    private TextView mTvNoTextEmpty;
    private long mCoundDate;
    private RelativeLayout rl_euale_content;
    /**
     * 当前题目所在集合（当前做题类型试题集合）顺序
     */
    private int mRunm;
    /**
     * 当前题目所在做题类型所属类型的目标编号
     */
    private int mTargetid;
    /**
     *
     */
    private int mQt;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");

        if (mTimeUitl != null) {
            mTimeUitl.cancel();
        }
        clearConstant();
        clearAll();
//        saveChapterRecords();
    }
    /***
     * 章节练习
     * @param context
     * @param questionId 问题id
     * @param type  类型  考试，练习等
     * @return
     */
    public static Intent newInstances(Context context, String questionId, String type, int numb) {
        Intent intent = new Intent(context, AnswerActivity.class);
        intent.putExtra(QUESTIONID, questionId);
        intent.putExtra(TYPEMARK, type);
        intent.putExtra(RNUMMARK, numb);
        return intent;
    }

    /**
     * 搜索界面
     *
     * @param context
     * @param courseid 课id
     * @param id
     * @return
     */
    public static Intent searchInstance(Context context, String courseid, String id, String type) {
        Intent intent = new Intent(context, AnswerActivity.class);
        intent.putExtra(SEARCHZID, courseid);
        intent.putExtra(SEARCHTYPE, type);
        intent.putExtra(SEARCHTID, id);
        return intent;
    }

    /***
     * 考试练习
     * @param context
     * @param questionId 问题id
     * @param type  类型  考试，练习等
     * @return
     */
    public static Intent examInstance(Context context, String questionId, String type) {
        Intent intent = new Intent(context, AnswerActivity.class);
        intent.putExtra(QUESTIONID, questionId);
        intent.putExtra(STYLECASE, type);
        return intent;
    }

    /***
     *  专项练习
     * @param mContext
     * @param courseid 科目id
     * @param tageid  标签
     *  id 多余无需传参
     *
     * @return
     */
    public static Intent newInstance(Context mContext, String courseid, String tageid, int rnum) {
        Intent intent = new Intent(mContext, AnswerActivity.class);
        intent.putExtra(TAGEIDDATA, tageid);
        intent.putExtra(COURSEIDKEMU, courseid);
        intent.putExtra(RNUMMARK, rnum);
        return intent;
    }


    /**
     * 我的错题我的收藏
     *
     * @param mContext
     * @param courseid 科目id
     * @param tageid   题干id
     * @param tagtype  类型
     * @return
     */
    public static Intent newInstance(Context mContext, String courseid, String tageid, String tagtype, int Number) {
        Intent intent = new Intent(mContext, AnswerActivity.class);
        intent.putExtra(TAGEIDDATAUSER, tageid);
        intent.putExtra(COURSEIDKEMUUSER, courseid);
        intent.putExtra(TAGYTTPE, tagtype);
        intent.putExtra(RNUMMARK, Number);
        return intent;
    }

    /**
     * @param mContext
     * @param courseid 科目id
     * @return
     */
    public static Intent newInstance(Context mContext, String courseid) {
        Intent intent = new Intent(mContext, AnswerActivity.class);
        intent.putExtra(MTYPEID, courseid);
        return intent;
    }

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        SharedSeletResultListUtil.getInstance().DeleteUser();
        setContentView(R.layout.activity_answer);
        clearConstant();
        if (getIntent() != null) {
            //问题id
            mOid = getIntent().getStringExtra(QUESTIONID);
            //章节练习标识
            mTypeMark = getIntent().getStringExtra(TYPEMARK);
            //章节练习索引
            mRunm = getIntent().getIntExtra(RNUMMARK, 0);
            //科目id
            mTypeId = getIntent().getStringExtra(MTYPEID);
            //科目
            mCouresidKuMu = getIntent().getStringExtra(COURSEIDKEMU);
            //用户的科目
            mCouresidUser = getIntent().getStringExtra(COURSEIDKEMUUSER);
            //获取文章tagid
            mTagid = getIntent().getStringExtra(TAGEIDDATA);
            //     //用户收藏或者错题集合
            mUserTagid = getIntent().getStringExtra(TAGEIDDATAUSER);
            //收藏
            mTagType = getIntent().getStringExtra(TAGYTTPE);
            //考试类型
            mStyleCase = getIntent().getStringExtra(STYLECASE);
            //搜索章id
            mSearchZid = getIntent().getStringExtra(SEARCHZID);
            //搜索题id
            mSearchTid = getIntent().getStringExtra(SEARCHTID);
            //搜索类型
            mSearchType = getIntent().getStringExtra(SEARCHTYPE);
        }
        initView();
        clearAll();
        mIvTimePlay.setVisibility(View.GONE);
        initData();
        clearData();
        initShowSet();
        initEvalueAdapter();


    }

    /**
     * 自由组卷
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void Mainthreaddata(FreeDataEvent event) {
        mRunm = DataMessageVo.OTHERZONE;
        mTargetid = DataMessageVo.OTHERZONE;
        mQt = DataMessageVo.OTHERZONE;
        initShowSet();
        isExamHine = true;
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        mTextDetial = event.getData();
        mTvBCount.setText(String.valueOf(mTextDetial.size()));
        bindTextNumberData();
        mFreeQuestion = mFreeQuestionTrue;
        mIvTimePlay.setVisibility(View.GONE);
    }

    /**
     * 初始化数据
     */
    private void initShowSet() {
        initSetting();
        Requestion();
        clearSeletItem();
    }

    /**
     * 请求问题类型
     */
    private void Requestion() {
        exam();
        errOrCol();
        shunxu();
        zhuanXiang();
        searchtype();
        chapter();
    }

    /**
     * 搜索结果
     */
    private void searchtype() {
        if (!StringUtil.isEmpty(mSearchType) && !StringUtil.isEmpty(mSearchTid) && !StringUtil.isEmpty(mSearchZid)) {
            isExamHine = true;
            List<QuestionsBean> list = new ArrayList<>();
            QuestionsBean datasBean = new QuestionsBean();
            datasBean.setId(Integer.parseInt(mSearchTid));
            datasBean.setCourseid(Integer.parseInt(mSearchZid));
            list.add(datasBean);
            mTextDetial = list;
            setShowLayout();
            mTvBCount.setText(String.valueOf(list.size()));
            bindTextNumberData();
            mQt = DataMessageVo.OTHERZONE;
            mRunm = DataMessageVo.OTHERZONE;
            mTargetid = DataMessageVo.OTHERZONE;
        }

    }

    /**
     * 专项
     */
    private void zhuanXiang() {
        //请求专项
        if (!StringUtil.isEmpty(mCouresidKuMu) && !StringUtil.isEmpty(mTagid)) {
            mDetailPresenter = new SpecailDetailPresenter(new SpecailDetailModelImpl(), this);
            mDetailPresenter.requestSpecailDetail(mContext, mCouresidKuMu, mTagid);
            isExamHine = true;

        }
    }

    /**
     * 顺序练习
     */
    private void shunxu() {
        //请求所有问题
        if (!StringUtil.isEmpty(mTypeId)) {
            isExamHine = true;
            mAllQuestion = new AllQuestionPresenter(new AllQuestionModelImpl(), this);
            mAllQuestion.getcoursequestionid(mContext, mTypeId);

        }
    }

    /**
     * 错题请求
     */
    private void errOrCol() {
        if (!StringUtil.isEmpty(mTagType) && !StringUtil.isEmpty(mCouresidUser) &&
                !StringUtil.isEmpty(mUserTagid)) {
            mTargetid = Integer.parseInt(mUserTagid);
            if (mUserTagid.equalsIgnoreCase(String.valueOf(DataMessageVo.OTHERZONE))) {
                if (mTagType.equalsIgnoreCase(ERRTYPE)) {
                    mQt = DataMessageVo.ERROREXERCISE;
                } else if (mTagType.equalsIgnoreCase(FAVTYPE)) {
                    mQt = DataMessageVo.FAVORITEEXERCISE;
                }
            } else {
                if (mTagType.equalsIgnoreCase(ERRTYPE)) {
                    mQt = DataMessageVo.ERROR_TAGEXERCISE;
                } else if (mTagType.equalsIgnoreCase(FAVTYPE)) {
                    mQt = DataMessageVo.FAVORITE_TAGEXERCISE;
                }
            }
            //请求错题和收藏题集合
            mListPresenter = new ErrOrColListPresenter(new ErrOrColListModelImpl(), this);
            mListPresenter.requestionErrOrColCont(mContext, mCouresidUser, mUserTagid, mTagType);
            isExamHine = true;
        }

        if (!StringUtil.isEmpty(mTagType) && mTagType.equals(ERRTYPE)) {//错题模式
            mIvBarDelect.setVisibility(View.VISIBLE);
            isExamHine = true;
        }
    }

    /**
     * 配置显示
     */
    private void initSetting() {
        if (mInfomDb != null) {
            String dayOrNight = mInfomDb.getShowDayOrNight();
            if (!StringUtil.isEmpty(dayOrNight)) {
                mSelectViewBgZC = dayOrNight;
                if (mSelectViewBgZC.equals(mSelectViewBgHY)) {
                    isNight = false;
                    isEye = true;
                    setHuYanLayout();
                } else if (mSelectViewBgZC.equals(mSelectViewBgYJ)) {
                    isNight = true;
                    isEye = false;
                    setNightLayout();
                } else {
                    isEye = false;
                    isNight = false;
                    setZhLayout();
                }
            }
            mSelectNext = mInfomDb.getUserNextGo();
        } else {
            isEye = false;
            isNight = false;
            setZhLayout();
        }
    }

    /***
     * 考试
     */
    private void exam() {
        if (!StringUtil.isEmpty(mStyleCase) && !StringUtil.isEmpty(mOid)) {
            isExamHine = false;
            mIvTimePlay.setVisibility(View.VISIBLE);
            mTimeUitl = MyTimeUitl.getInstance(mContext);
            if (mStyleCase.equals(DataMessageVo.MARKTYPECASE)) {//案例
                mTimeUitl.start(mActivityTitleText, 3, 0, 0, this);
                mCoundDate = mTimeUitl.getTime(3, 0, 0);
            } else if (mStyleCase.equals(DataMessageVo.MARKTYPESKILL)) {//技术
                mTimeUitl.start(mActivityTitleText, 2, 30, 0, this);
                mCoundDate = mTimeUitl.getTime(2, 30, 0);
            } else if (mStyleCase.equals(DataMessageVo.MARKTYPECOLLORT)) {//综合
                mTimeUitl.start(mActivityTitleText, 2, 30, 0, this);
                mCoundDate = mTimeUitl.getTime(2, 30, 0);
            }
            mPresnter.getTextContent(mContext, mOid);
            tabBarSetListener();
        }
    }

    /**
     * 状态栏
     */
    private void tabBarSetListener() {
        mVBLineBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIvTimePlay.setImageResource(R.mipmap.qbank_answer_icon_pau);
                DialogUtil dialogUtil = DialogUtil.getInstance();
                if (mStopAlertDialog == null || !mStopAlertDialog.isShowing())
                    mStopAlertDialog = dialogUtil.showStopDialog(mContext);
                mTimeUitl.pause();
                dialogUtil.setStopClickListener(new DialogUtil.onStopClickListener() {
                    @Override
                    public void onNextClickListener() {
                        mIvTimePlay.setImageResource(R.mipmap.qbank_answer_icon_cont);
                        mTimeUitl.resume();
                    }
                });
            }
        });
    }

    /***
     * 章节
     */
    private void chapter() {
   /*     if (!StringUtil.isEmpty(mTypeMark) && !StringUtil.isEmpty(mOid)) {
            showDialogContinue(mRunm);
        }*/

   /*     if (!StringUtil.isEmpty(mTypeMark) && !StringUtil.isEmpty(mOid)) {
 isExamHine = true;
            UserInfomDb userInfomDb = DbHelperAssist.getInstance(getApplicationContext()).queryWithuuUserInfom();
            if (mTypeMark.equals("1")) {//技术章节
                if (userInfomDb != null) {
                    List<UserLookVo> skillData = userInfomDb.getSkillData();
                    if (skillData != null && !skillData.isEmpty()) {
                        for (int i = 0; i < skillData.size(); i++) {
                            final UserLookVo vo = skillData.get(i);
                            showDialogContinue(vo);
                        }
                    }
                }

            } else if (mTypeMark.equals("2")) {//综合
                if (userInfomDb != null) {
                    List<UserLookVo> coloct = userInfomDb.getColoctData();
                    if (coloct != null && !coloct.isEmpty()) {
                        for (int i = 0; i < coloct.size(); i++) {
                            final UserLookVo vo = coloct.get(i);
                            showDialogContinue(vo);
                        }
                    }
                }

            } else if (mTypeMark.equals("3")) {//案例
                if (userInfomDb != null) {
                    List<UserLookVo> casedata = userInfomDb.getCaseData();
                    if (casedata != null && !casedata.isEmpty()) {
                        for (int i = 0; i < casedata.size(); i++) {
                            final UserLookVo vo = casedata.get(i);
                            showDialogContinue(vo);
                        }
                    }
                }

            }

        }
*/
    }

    /**
     * 显示继续对话框
     *
     * @param vo
     */
    private void showDialogContinue(final UserLookVo vo) {
        if (vo.getChapterId().equals(mOid)) {
            DialogUtil dialogUtil = DialogUtil.getInstance();
            int nextId = Integer.parseInt(vo.getNextId());
            if (nextId == 0) {
                return;
            }
            nextId += 1;
            dialogUtil.showContinueDialog(mContext, String.valueOf(nextId));
            dialogUtil.setContinueClickListener(new DialogUtil.onContincueClickListener() {
                @Override
                public void onCancelClickListener() {
                }

                @Override
                public void onNextClickListener() {
                    mMark = Integer.parseInt(vo.getNextId());
                    bindTextNumberData();
                }
            });
        }
    }

    /**
     * 显示继续对话框
     *
     * @param nextNumber 第几题
     */
    private void showDialogContinue(int nextNumber) {
        if (nextNumber == 0 || nextNumber == 1) return;
        final int postion = nextNumber;
        DialogUtil dialogUtil = DialogUtil.getInstance();
        dialogUtil.showContinueDialog(mContext, String.valueOf(postion));
        dialogUtil.setContinueClickListener(new DialogUtil.onContincueClickListener() {
            @Override
            public void onCancelClickListener() {
            }

            @Override
            public void onNextClickListener() {
                if (postion > 0)
                    mMark = postion - 1;
                else
                    mMark = postion;
                bindTextNumberData();
            }
        });
    }

    /**
     * 设置布局背景颜色
     */
    private void setLayoutBg() {
        if (mSelectViewBgZC.equals(mSelectViewBgHY)) {
            setHuYanLayout();
        } else if (mSelectViewBgZC.equals(mSelectViewBgYJ)) {
            setNightLayout();
        } else {
            setZhLayout();
        }
    }

    private void initData() {
        //读取保存信息
        mInfomDb = DbHelperAssist.getInstance(getApplicationContext()).queryWithuuUserInfom();
        //请求评价
        mEvaluePresenter = new EvaluePresenter(new EvalueModelImpl(), this);
        //请求答案
        mPresnter = new AnswerPresenter(new AnswerModelImpl(), this);
        dialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        if (!StringUtil.isEmpty(mOid) && !StringUtil.isEmpty(mTypeMark)) {//章节练习
            mPresnter.getTextContent(mContext, mOid);
            mQt = DataMessageVo.CHAPTEREXERCISE;
            isExamHine = true;
        }
        mEtBSubmit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    submitEvalue();
                    return true;
                }
                return false;
            }
        });
    }

    private void initEvalueAdapter() {
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 1);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvEualeContent.setLayoutManager(layoutManager);
        adapter = new AnswerEvaluateAdapter(mContext, mArray);
        mRlvEualeContent.setAdapter(adapter);
        adapter.setBGLayout(mSelectViewBgZC);
        mChbBCollect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mResultData != null) {
                    mPresnter.submit0ollect(mContext, String.valueOf(mResultData.getId()), isChecked);
                }
            }
        });
        mSloViewShow.setScanScrollChangedListener(new SmartScrollView.ISmartScrollChangedListener() {
            @Override
            public void onScrolledToBottom() {
                if (mRlvEualeContent.getVisibility() == View.VISIBLE && isMoreData) {
                    if (mOid != null) {
                        isMoreData = false;
//                        mEvaluePresenter.requestEvalueOneMoreContent(mContext, getNowPage() + 1, String.valueOf(mResultData.getId()));
                        mEvaluePresenter.requestNewEvalueMoreContent(mContext, getNowPage() + 1, DataMessageVo.QUESTION, String.valueOf(mResultData.getId()));
                        mLlMoreData.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onScrolledToTop() {

            }
        });
  /*      mRlvEualeContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {//加载更多
                    if (isMoreData) {
                        if (mOid != null) {
                            mEvaluePresenter.requestEvalueOneMoreContent(mContext, getNowPage() + 1, String.valueOf(mResultData.getId()));
                            mLlMoreData.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });*/
        adapter.setClickListener(new AnswerEvaluateAdapter.onItemClickListener() {
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
        adapter.setClickChbListener(new AnswerEvaluateAdapter.onItemChbClickListener() {
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
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 绑定数据
     */
    private void bindTextNumberData() {
        if (mTextDetial != null) {//不是错题
            mTvBNew.setText(String.valueOf(mMark + 1));
            if (mMark < mTextDetial.size()) {
                QuestionsBean bean = mTextDetial.get(mMark);
                int i = bean.getCourseid();
                UserInfomDb db = DbHelperAssist.getInstance(getApplicationContext()).queryWithuuUserInfom();
                if (db != null) {
                    if (i == 1) {//技术
                        isBuy = db.getSkillBook();
                    } else if (i == 2) {//综合
                        isBuy = db.getColligateBook();
                    } else if (i == 3) {//案例
                        isBuy = db.getCaseBook();
                    }
                }

                //请求评价
//                mEvaluePresenter.requestEvalueOneContent(mContext, 1, String.valueOf(bean.getId()));
                mEvaluePresenter.requestNewEvalueContent(mContext, 1, DataMessageVo.QUESTION, String.valueOf(bean.getId()));
                isMoreData = false;
                //请求题干详情
                if (!StringUtil.isEmpty(mCouresidKuMu) && !StringUtil.isEmpty(mTagid)) {
                    mPresnter.getTextDetailContent(mContext, String.valueOf(bean.getId()),
                            mMark, mTargetid, mQt);
                } else if (!StringUtil.isEmpty(mTypeId)) {//顺序练习
                    mTargetid = DataMessageVo.OTHERZONE;
                    mPresnter.getTextDetailContent(mContext, String.valueOf(bean.getId()),
                            mMark, DataMessageVo.OTHERZONE, mQt);
                } else if (!StringUtil.isEmpty(mTagType) && !StringUtil.isEmpty(mCouresidUser) &&
                        !StringUtil.isEmpty(mUserTagid)) {
                    mPresnter.getTextDetailContent(mContext, String.valueOf(bean.getId()),
                            mMark, mTargetid, mQt);

                } else {
                    mTargetid = bean.getChapterid();
                    mPresnter.getTextDetailContent(mContext, String.valueOf(bean.getId()),
                            mMark, bean.getChapterid(), mQt);
                }
            }
        }


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

    private void initView() {
        mContext = this;
        mIvJianPan = (ImageView) findViewById(R.id.iv_ban_jianpan);
        mIvJianPan.setOnClickListener(this);
        mTvNam = (TextView) findViewById(R.id.tv_nandu);
        mTvNoTextEmpty = (TextView) findViewById(R.id.tv_no_text_empty);
        mRlRootLayout = (RelativeLayout) findViewById(R.id.rl_root_layout);
        mIvBMore = (ImageView) findViewById(R.id.iv_b_more);
        mIvBMore.setOnClickListener(this);
        mTvRootEmpty = (TextView) findViewById(R.id.tv_root_empty);

        mTvBType = (TextView) findViewById(R.id.tv_b_type);
        mTvBType.setOnClickListener(this);
        mTvBMatter = (TextView) findViewById(R.id.tv_b_matter);
        mTvBMatter.setOnClickListener(this);
        mIvBA = (ImageView) findViewById(R.id.iv_b_a);
        mTvBAContent = (TextView) findViewById(R.id.tv_b_a_content);
        mLlBASelect = (LinearLayout) findViewById(R.id.ll_b_a_select);
        mLlBASelect.setOnClickListener(this);
        mIvBB = (ImageView) findViewById(R.id.iv_b_b);
        mTvBBContent = (TextView) findViewById(R.id.tv_b_b_content);
        mLlBBSelect = (LinearLayout) findViewById(R.id.ll_b_b_select);
        mLlBBSelect.setOnClickListener(this);
        mIvBC = (ImageView) findViewById(R.id.iv_b_c);
        mTvBCContent = (TextView) findViewById(R.id.tv_b_c_content);
        mLlBCselect = (LinearLayout) findViewById(R.id.ll_b_cselect);
        mLlBCselect.setOnClickListener(this);
        mIvBD = (ImageView) findViewById(R.id.iv_b_d);
        mTvBDContent = (TextView) findViewById(R.id.tv_b_d_content);
        mLlBDSelect = (LinearLayout) findViewById(R.id.ll_b_d_select);
        mLlBDSelect.setOnClickListener(this);
        mIvBE = (ImageView) findViewById(R.id.iv_b_e);
        mTvBEContent = (TextView) findViewById(R.id.tv_b_e_content);
        mLlBESelect = (LinearLayout) findViewById(R.id.ll_b_e_select);
        mLlBESelect.setOnClickListener(this);
        mBtnBSureKey = (Button) findViewById(R.id.btn_b_sure_key);
        mBtnBSureKey.setOnClickListener(this);
        mTvBAnswer = (TextView) findViewById(R.id.tv_b_answer);
        mTvBAnswer.setOnClickListener(this);
        mLlBAnswerKey = (LinearLayout) findViewById(R.id.ll_b_answer_key);
        mLlBAnswerKey.setOnClickListener(this);
        mTvBRosoleContent = (TextView) findViewById(R.id.tv_b_rosole_content);
        mTvBRosoleContent.setOnClickListener(this);
        mLlBJiexi = (LinearLayout) findViewById(R.id.ll_b_jiexi);
        mLlBJiexi.setOnClickListener(this);
        mTvBAccuracy = (TextView) findViewById(R.id.tv_b_accuracy);
        mTvBAccuracy.setOnClickListener(this);
        mLlBRightLu = (LinearLayout) findViewById(R.id.ll_b_right_lu);
        mLlBRightLu.setOnClickListener(this);
        mBtnBBuy = (Button) findViewById(R.id.btn_b_buy);
        mBtnBBuy.setOnClickListener(this);
        mLiBResolveBuy = (LinearLayout) findViewById(R.id.li_b_resolve_buy);
        mLiBResolveBuy.setOnClickListener(this);
        mSloViewShow = (SmartScrollView) findViewById(R.id.slv_view_show);
//        mRlvEualeContent = (RecyclerView) findViewById(R.id.rlv_euale_content);
        mRlvEualeContent = (MyRecyclerView) findViewById(R.id.rlv_euale_content);
        mRlvEualeContent.setHasFixedSize(true);
        mRlvEualeContent.setNestedScrollingEnabled(false);
        mSloViewShow.setOnClickListener(this);
        mLlBBack = (LinearLayout) findViewById(R.id.ll_b_back);
        mLlBBack.setOnClickListener(this);
        mLlBGo = (LinearLayout) findViewById(R.id.ll_b_go);
        mLlBGo.setOnClickListener(this);
        mIvBExpand = (ImageView) findViewById(R.id.iv_b_expand);
        mIvBExpand.setOnClickListener(this);
        mTvBNew = (TextView) findViewById(R.id.tv_b_new);
        mTvBNew.setOnClickListener(this);
        mTvBCount = (TextView) findViewById(R.id.tv_b_count);
        mTvBCount.setOnClickListener(this);
        mChbBCollect = (CheckBox) findViewById(R.id.chb_b_collect);
        mChbBCollect.setOnClickListener(this);
        mLlRootLayout = (LinearLayout) findViewById(R.id.ll_root_layout);
        mLlRootLayout.setOnClickListener(this);
        mIvBStar1 = (ImageView) findViewById(R.id.iv_b_star1);
        mIvBStar1.setOnClickListener(this);
        mIvBStar2 = (ImageView) findViewById(R.id.iv_b_star2);
        mIvBStar2.setOnClickListener(this);
        mIvBStar3 = (ImageView) findViewById(R.id.iv_b_star3);
        mIvBStar3.setOnClickListener(this);
        mIvBStar4 = (ImageView) findViewById(R.id.iv_b_star4);
        mIvBStar4.setOnClickListener(this);
        mIvBStar5 = (ImageView) findViewById(R.id.iv_b_star5);
        mIvBStar5.setOnClickListener(this);
        mLlMoreData = (LinearLayout) findViewById(R.id.ll_more_data);
        mLlMoreData.setOnClickListener(this);
        mLiXia = (LinearLayout) findViewById(R.id.li_xia);
        mLiXia.setOnClickListener(this);
        mTvAnswerNumberevlue = (TextView) findViewById(R.id.tv_answer_numberevlue);
        mTvAnswerNumberevlue.setOnClickListener(this);
        mTvAnswerAddevlua = (TextView) findViewById(R.id.tv_answer_addevlua);
        mTvAnswerAddevlua.setOnClickListener(this);
        mEtBSubmit = (EditText) findViewById(R.id.et_b_submit);
        mEtBSubmit.setOnClickListener(this);
        mIvBSubmitSend = (ImageView) findViewById(R.id.iv_b_submit_send);
        mIvBSubmitSend.setOnClickListener(this);
        mLlBSubmitEvalue = (LinearLayout) findViewById(R.id.ll_b_submit_evalue);
        mLlBSubmitEvalue.setOnClickListener(this);

        mIvTitleBack = (ImageView) findViewById(R.id.iv_title_back);
        mIvTitleBack.setOnClickListener(this);
        mActivityTitleText = (TextView) findViewById(R.id.activity_title_text);
        mLlTitleBar = (LinearLayout) findViewById(R.id.ll_title_bar);
        mLlTitleBar.setOnClickListener(this);
        mTvAnswerAnswer = (TextView) findViewById(R.id.tv_answer_answer);
        mTvAnswerAnswer.setOnClickListener(this);
        mTvAnswerJiexi = (TextView) findViewById(R.id.tv_answer_jiexi);
        mTvAnswerJiexi.setOnClickListener(this);
        mVLine = (View) findViewById(R.id.v_line);
        mVLine.setOnClickListener(this);
        mVLine2 = (View) findViewById(R.id.v_line2);
        mVLine2.setOnClickListener(this);
        mVLine3 = (View) findViewById(R.id.v_line3);
        mVLine3.setOnClickListener(this);
        mVLine1 = (View) findViewById(R.id.v_line1);
        mVLine1.setOnClickListener(this);
        mBtnLookWenAnswer = (Button) findViewById(R.id.btn_look_wen_answer);
        mBtnLookWenAnswer.setOnClickListener(this);
        mVLine4 = (View) findViewById(R.id.v_line4);
        mVLine4.setOnClickListener(this);
        mTvLookAnswerCan = (TextView) findViewById(R.id.tv_look_answer_can);
        mTvLookAnswerCan.setOnClickListener(this);
        mTvLookAnswerWen = (TextView) findViewById(R.id.tv_look_answer_wen);
        mTvLookAnswerWen.setOnClickListener(this);
        mLlLookWenDa = (LinearLayout) findViewById(R.id.ll_look_wen_da);
        mLlLookWenDa.setOnClickListener(this);
        mLlSelectRootLayout = (LinearLayout) findViewById(R.id.ll_select_root_layout);
        mLlSelectRootLayout.setOnClickListener(this);
        mRlLookEvalue = (RelativeLayout) findViewById(R.id.rl_look_evalue);
        mRlLookEvalue.setOnClickListener(this);
        mIvTimePlay = (ImageView) findViewById(R.id.iv_time_play);
        mVBLineBar = (LinearLayout) findViewById(R.id.v_b_lineBar);
        mVBLineBar.setOnClickListener(this);
        mIvBarDelect = (ImageView) findViewById(R.id.iv_bar_delect);
        mIvBarDelect.setOnClickListener(this);
        mRlvEualeContent.setParentScrollView(mSloViewShow);
        rl_euale_content = (RelativeLayout) findViewById(R.id.rl_euale_content);
        rl_euale_content.setOnClickListener(this);

    }

    /**
     * 绑定界面
     *
     * @param vo
     */
    private void bindViewData(TextDetailVo vo) {
        if (StringUtil.isEmpty(vo.getData().getQuestion())) {
            mTvNoTextEmpty.setVisibility(View.VISIBLE);
            mIvBMore.setVisibility(View.GONE);
        } else {
            mTvNoTextEmpty.setVisibility(View.GONE);
            mIvBMore.setVisibility(View.VISIBLE);
        }
        DbHelperAssist helperAssist = DbHelperAssist.getInstance(getApplicationContext());
        setShowLayout();
        //设置布局颜色
        setLayoutBg();
        //用户是做过
        boolean isdo = false;
        //用户选中信息
        UseSelectItemInfomVo item = null;
        this.mTextDetialNew = vo;
        mResultData = vo.getData();

        //赋值
        if (isBuy) {// 已购买显示解析
            mLiBResolveBuy.setVisibility(View.GONE);
            mLlBJiexi.setVisibility(View.VISIBLE);
            mLlBRightLu.setVisibility(View.VISIBLE);
            mRlLookEvalue.setVisibility(View.GONE);
        } else {//未购买 隐藏解析
            mLlBAnswerKey.setVisibility(View.GONE);
            mLlBJiexi.setVisibility(View.GONE);
            mLiBResolveBuy.setVisibility(View.GONE);
            mLlBRightLu.setVisibility(View.GONE);
            mRlLookEvalue.setVisibility(View.GONE);
            mRlvEualeContent.setVisibility(View.GONE);
            rl_euale_content.setVisibility(View.GONE);
        }
        List<UseSelectItemInfomVo> user = SharedSeletResultListUtil.getInstance().getUser();
        if (user != null && !user.isEmpty()) {
            for (int i = 0; i < user.size(); i++) {
                UseSelectItemInfomVo vo1 = user.get(i);
                if (vo1.getId() == mResultData.getId()) {//做过
                    isdo = true;
                    item = user.get(i);
                    if (!isBuy)
                        mLiBResolveBuy.setVisibility(View.VISIBLE);
                    break;
                }
            }

        }

        //  判断问题类型单选/多选->提供选择处理
        switch (mResultData.getQuestiontype()) {
            case 2://单选
                mBtnBSureKey.setVisibility(View.GONE);
                mTitleType = mTitleTypeOnly;
                setSelectOnlyItemBG(false, false, false, false, false);
                break;
            case 3://多选
                mBtnBSureKey.setVisibility(View.VISIBLE);
                mBtnBSureKey.setClickable(false);
                mTitleType = mTitleTypeMore;
                clearMoreBG();

                break;
            case 4://问答
                mBtnBSureKey.setVisibility(View.GONE);
                mTitleType = mTitleTypeWrite;
                break;
            default:

        }
        /**
         * 判断是否答题界面
         */
        if (!StringUtil.isEmpty(mStyleCase) && !isUserLookResultJieXi) {
            bindResultExamData(isdo, item);
            return;
        }

        /*       *//***
         * 是否自由组卷界面
         *//*
        if (!StringUtil.isEmpty(mFreeQuestion) && mFreeQuestion.equals(mFreeQuestionTrue) && !isUserLookResultJieXi) {
            bindResultExamData(isdo, item);
            return;
        }*/

        /**
         * 是否查看解析界面
         */
        if (isUserLookResultJieXi && isBuy) {
            bindResultWithJieXi(item);
        } else if (isBuy) {
            bindResultData(isdo, item);
        } else {
            bindNoResultData(isdo, item);
        }

    }

    /**
     * 绑定解析界面
     *
     * @param item
     */
    private void bindResultWithJieXi(UseSelectItemInfomVo item) {
        mLlBAnswerKey.setVisibility(View.VISIBLE);
        mLlBJiexi.setVisibility(View.VISIBLE);
        mLlBRightLu.setVisibility(View.VISIBLE);
        mRlLookEvalue.setVisibility(View.VISIBLE);
        mLlSelectRootLayout.setVisibility(View.VISIBLE);
        mRlvEualeContent.setVisibility(View.VISIBLE);
        rl_euale_content.setVisibility(View.VISIBLE);
        if (!StringUtil.isEmpty(mTitleType) && mTitleType.equals(mTitleTypeOnly)) {//单选模式
            mBtnLookWenAnswer.setVisibility(View.GONE);
            mLlLookWenDa.setVisibility(View.GONE);
            mLlSelectRootLayout.setVisibility(View.VISIBLE);
            setIsClick(false);
            setResultItemBG(mResultData.getChoiceanswer(), mResultData.getChoiceanswer(), mSelectViewBgZC);
            if (item != null && !StringUtil.isEmpty(item.getItem())) {
                setResultItemBG(item.getItem(), mResultData.getChoiceanswer(), mSelectViewBgZC);
            }

        } else if (!StringUtil.isEmpty(mTitleType) && mTitleType.equals(mTitleTypeMore)) {//多选模式
            mBtnLookWenAnswer.setVisibility(View.GONE);
            mLlLookWenDa.setVisibility(View.GONE);
            mLlSelectRootLayout.setVisibility(View.VISIBLE);
            mBtnBSureKey.setVisibility(View.GONE);
            setIsClick(false);
            setReusltWithJieXi(mResultData.getChoiceanswer());
            if (item != null) {
                if (!StringUtil.isEmpty(item.getSelectItemA()) ||
                        !StringUtil.isEmpty(item.getSelectItemB()) ||
                        !StringUtil.isEmpty(item.getSelectItemC()) ||
                        !StringUtil.isEmpty(item.getSelectItemD()) ||
                        !StringUtil.isEmpty(item.getSelectItemE())) {
                    String a = item.getSelectItemA();
                    String b = item.getSelectItemB();
                    String c = item.getSelectItemC();
                    String d = item.getSelectItemD();
                    String e = item.getSelectItemE();
                    setResultItemBG(a, b, c, d, e, mResultData.getChoiceanswer(), mSelectViewBgZC);
                }
            }

        } else if (!StringUtil.isEmpty(mTitleType) && mTitleType.equals(mTitleTypeWrite)) {//问答
            mLlSelectRootLayout.setVisibility(View.GONE);
            mBtnLookWenAnswer.setVisibility(View.GONE);
            mLlLookWenDa.setVisibility(View.VISIBLE);
        }

        mTvBType.setText(AnswerCardUtil.getTextType(mResultData.getQuestiontype()));
        if (StringUtil.isEmpty(mResultData.getQuestion())) {
            mTvBMatter.setText(null);
        } else {
            Spanned html = Html.fromHtml(mResultData.getQuestion());
//        mTvBMatter.setText(new HtmlSpanner().fromHtml(mResultData.getQuestion()));
            mTvBMatter.setMovementMethod(ScrollingMovementMethod.getInstance());
            mTvBMatter.setText(html.toString().trim());
        }

        if (!StringUtil.isEmpty(mResultData.getA())) {
            mTvBAContent.setText(mResultData.getA());
        } else {
            mTvBAContent.setText("");
        }
        if (!StringUtil.isEmpty(mResultData.getB())) {
            mTvBBContent.setText(mResultData.getB());
        } else {
            mTvBBContent.setText("");
        }
        if (!StringUtil.isEmpty(mResultData.getC())) {
            mTvBBContent.setText(mResultData.getB());
        } else {
            mTvBBContent.setText("");
        }
        if (!StringUtil.isEmpty(mResultData.getC())) {
            mTvBCContent.setText(mResultData.getC());
        } else {
            mTvBCContent.setText("");
        }
        if (!StringUtil.isEmpty(mResultData.getD())) {
            mTvBDContent.setText(mResultData.getD());
        } else {
            mTvBDContent.setText("");
        }

//        mTvBAContent.setText(mResultData.getA());
//        mTvBBContent.setText(mResultData.getB());
//        mTvBCContent.setText(mResultData.getC());
//        mTvBDContent.setText(mResultData.getD());
        if (StringUtil.isEmpty(mResultData.getE())) {//是否有e选项
            mIvBE.setVisibility(View.GONE);
            mTvBEContent.setVisibility(View.GONE);
        } else {
            mIvBE.setVisibility(View.VISIBLE);
            mTvBEContent.setVisibility(View.VISIBLE);
            mTvBEContent.setText(mResultData.getE());
        }

        mTvBAnswer.setText(mResultData.getChoiceanswer());
        if (StringUtil.isEmpty(mResultData.getAnalysis())) {
            mTvBRosoleContent.setText(null);
        } else {
            URLImageParser parser = new URLImageParser(mTvBRosoleContent);
            Spanned spanned = Html.fromHtml(mResultData.getAnalysis(), parser, null);
            mTvBRosoleContent.setMovementMethod(ScrollingMovementMethod.getInstance());
            mTvBRosoleContent.setText(spanned);
        }
        mTvBAccuracy.setText(mResultData.getAccuracy());
        mChbBCollect.setChecked(mResultData.isIsfav());
        //正确答案
        mRightItem = mResultData.getChoiceanswer();
        Spanned fromHtml = null;
        if (StringUtil.isEmpty(mResultData.getAnalysis())) {
            fromHtml = null;
        } else {
            fromHtml = Html.fromHtml(mResultData.getAnalysis());
        }
        if (isBuy)
            mTvLookAnswerWen.setText(fromHtml);
        int i = mResultData.getDifficultydegreee();
        setStarNumber(i);
    }

    /**
     * 绑定数据 用户购买
     *
     * @param isdo
     * @param item
     */
    private void bindResultData(boolean isdo, UseSelectItemInfomVo item) {

        if (isdo) {//用户做过 未做不处理
            mLlBAnswerKey.setVisibility(View.VISIBLE);
            mLlBJiexi.setVisibility(View.VISIBLE);
            mLlBRightLu.setVisibility(View.VISIBLE);
            mRlLookEvalue.setVisibility(View.VISIBLE);
            mLlSelectRootLayout.setVisibility(View.VISIBLE);
            mRlvEualeContent.setVisibility(View.VISIBLE);
            rl_euale_content.setVisibility(View.VISIBLE);
            if (!StringUtil.isEmpty(mTitleType) && mTitleType.equals(mTitleTypeOnly)) {//单选模式
                mBtnLookWenAnswer.setVisibility(View.GONE);
                mLlLookWenDa.setVisibility(View.GONE);
                mLlSelectRootLayout.setVisibility(View.VISIBLE);
                String onlyitme = item.getItem();
                setIsClick(false);
                setResultItemBG(onlyitme, mResultData.getChoiceanswer(), mSelectViewBgZC);
            } else if (!StringUtil.isEmpty(mTitleType) && mTitleType.equals(mTitleTypeMore)) {//多选模式
                mBtnLookWenAnswer.setVisibility(View.GONE);
                mLlLookWenDa.setVisibility(View.GONE);
                mLlSelectRootLayout.setVisibility(View.VISIBLE);
                mBtnBSureKey.setVisibility(View.GONE);
                String a = item.getSelectItemA();
                String b = item.getSelectItemB();
                String c = item.getSelectItemC();
                String d = item.getSelectItemD();
                String e = item.getSelectItemE();
                setIsClick(false);
                setResultItemBG(a, b, c, d, e, mResultData.getChoiceanswer(), mSelectViewBgZC);

            } else if (!StringUtil.isEmpty(mTitleType) && mTitleType.equals(mTitleTypeWrite)) {//问答
                mLlSelectRootLayout.setVisibility(View.GONE);
                mBtnLookWenAnswer.setVisibility(View.GONE);
                mLlLookWenDa.setVisibility(View.VISIBLE);
            }
        } else {
            if (!StringUtil.isEmpty(mTitleType) && mTitleType.equals(mTitleTypeWrite)) {
                mLlLookWenDa.setVisibility(View.GONE);
                mBtnLookWenAnswer.setVisibility(View.VISIBLE);
                mLlBAnswerKey.setVisibility(View.GONE);
                mLlBJiexi.setVisibility(View.GONE);
                mLlBRightLu.setVisibility(View.GONE);
                mRlLookEvalue.setVisibility(View.GONE);
                mLlSelectRootLayout.setVisibility(View.GONE);
                mRlvEualeContent.setVisibility(View.GONE);
                rl_euale_content.setVisibility(View.GONE);
            } else {
                mLlBAnswerKey.setVisibility(View.GONE);
                mLlBJiexi.setVisibility(View.GONE);
                mLlLookWenDa.setVisibility(View.GONE);
                mBtnLookWenAnswer.setVisibility(View.GONE);
                mLlBRightLu.setVisibility(View.GONE);
                mRlvEualeContent.setVisibility(View.GONE);
                rl_euale_content.setVisibility(View.GONE);
                mRlLookEvalue.setVisibility(View.GONE);
                mLlSelectRootLayout.setVisibility(View.VISIBLE);
            }
            setIsClick(true);
        }

        mTvBType.setText(AnswerCardUtil.getTextType(mResultData.getQuestiontype()));
        if (StringUtil.isEmpty(mResultData.getQuestion())) {
            mTvBMatter.setText("");
        } else {
            Spanned html = Html.fromHtml(mResultData.getQuestion());
            mTvBMatter.setMovementMethod(ScrollingMovementMethod.getInstance());
            mTvBMatter.setText(html.toString().trim());
        }
        mTvBAContent.setText(mResultData.getA());
        mTvBBContent.setText(mResultData.getB());
        mTvBCContent.setText(mResultData.getC());
        mTvBDContent.setText(mResultData.getD());
        if (StringUtil.isEmpty(mResultData.getE())) {//是否有e选项
            mIvBE.setVisibility(View.GONE);
            mTvBEContent.setVisibility(View.GONE);
        } else {
            mIvBE.setVisibility(View.VISIBLE);
            mTvBEContent.setVisibility(View.VISIBLE);
            mTvBEContent.setText(mResultData.getE());
        }

        mTvBAnswer.setText(mResultData.getChoiceanswer());
        if (StringUtil.isEmpty(mResultData.getAnalysis())) {
            mTvBRosoleContent.setText(null);
        } else {
            URLImageParser parser = new URLImageParser(mTvBRosoleContent);
            Spanned spanned = Html.fromHtml(mResultData.getAnalysis(), parser, null);
            mTvBRosoleContent.setMovementMethod(ScrollingMovementMethod.getInstance());
            mTvBRosoleContent.setText(spanned);
        }
        mTvBAccuracy.setText(mResultData.getAccuracy());
        mChbBCollect.setChecked(mResultData.isIsfav());
        //正确答案
        mRightItem = mResultData.getChoiceanswer();
        if (StringUtil.isEmpty(mResultData.getAnalysis())) {
            mTvLookAnswerWen.setText(null);
        } else {
            Spanned fromHtml = Html.fromHtml(mResultData.getAnalysis());
            mTvLookAnswerWen.setText(fromHtml);
        }
        int i = mResultData.getDifficultydegreee();
        setStarNumber(i);
    }

    /**
     * 用户未购买
     *
     * @param isdo
     * @param item
     */
    private void bindNoResultData(boolean isdo, UseSelectItemInfomVo item) {
        if (isdo) {//用户做过 未做不处理
            mLlBAnswerKey.setVisibility(View.VISIBLE);
            if (!StringUtil.isEmpty(mTitleType) && mTitleType.equals(mTitleTypeOnly)) {//单选模式
                mBtnLookWenAnswer.setVisibility(View.GONE);
                mLlLookWenDa.setVisibility(View.GONE);
                mLlSelectRootLayout.setVisibility(View.VISIBLE);
                String onlyitme = item.getItem();
                setIsClick(false);
                setResultItemBG(onlyitme, mResultData.getChoiceanswer(), mSelectViewBgZC);
            } else if (!StringUtil.isEmpty(mTitleType) && mTitleType.equals(mTitleTypeMore)) {//多选模式
                mBtnLookWenAnswer.setVisibility(View.GONE);
                mLlLookWenDa.setVisibility(View.GONE);
                mLlSelectRootLayout.setVisibility(View.VISIBLE);
                mBtnBSureKey.setVisibility(View.GONE);
                String a = item.getSelectItemA();
                String b = item.getSelectItemB();
                String c = item.getSelectItemC();
                String d = item.getSelectItemD();
                String e = item.getSelectItemE();
                setIsClick(false);
                setResultItemBG(a, b, c, d, e, mResultData.getChoiceanswer(), mSelectViewBgZC);

            } else if (!StringUtil.isEmpty(mTitleType) && mTitleType.equals(mTitleTypeWrite)) {//问答
                mLlSelectRootLayout.setVisibility(View.GONE);
                mBtnLookWenAnswer.setVisibility(View.GONE);
                mLlLookWenDa.setVisibility(View.GONE);
            }
        } else {
            if (!StringUtil.isEmpty(mTitleType) && mTitleType.equals(mTitleTypeWrite)) {
                mLlLookWenDa.setVisibility(View.GONE);
                mBtnLookWenAnswer.setVisibility(View.VISIBLE);
                mLlBAnswerKey.setVisibility(View.GONE);
                mLlBJiexi.setVisibility(View.GONE);
                mLlBRightLu.setVisibility(View.GONE);
                mRlLookEvalue.setVisibility(View.GONE);
                mLlSelectRootLayout.setVisibility(View.GONE);
                mRlvEualeContent.setVisibility(View.GONE);
                rl_euale_content.setVisibility(View.GONE);
            } else {
                mLlBAnswerKey.setVisibility(View.GONE);
                mLlBJiexi.setVisibility(View.GONE);
                mLlLookWenDa.setVisibility(View.GONE);
                mBtnLookWenAnswer.setVisibility(View.GONE);
                mLlBRightLu.setVisibility(View.GONE);
                mRlvEualeContent.setVisibility(View.GONE);
                rl_euale_content.setVisibility(View.GONE);
                mRlLookEvalue.setVisibility(View.GONE);
                mLlSelectRootLayout.setVisibility(View.VISIBLE);
            }
            setIsClick(true);
        }

        mTvBType.setText(AnswerCardUtil.getTextType(mResultData.getQuestiontype()));
        if (StringUtil.isEmpty(mResultData.getQuestion())) {
            mTvBMatter.setText(null);
        } else {
            Spanned html = Html.fromHtml(mResultData.getQuestion());
            mTvBMatter.setMovementMethod(ScrollingMovementMethod.getInstance());
            mTvBMatter.setText(html.toString().trim());
        }
        mTvBAContent.setText(mResultData.getA());
        mTvBBContent.setText(mResultData.getB());
        mTvBCContent.setText(mResultData.getC());
        mTvBDContent.setText(mResultData.getD());
        if (StringUtil.isEmpty(mResultData.getE())) {//是否有e选项
            mIvBE.setVisibility(View.GONE);
            mTvBEContent.setVisibility(View.GONE);
        } else {
            mIvBE.setVisibility(View.VISIBLE);
            mTvBEContent.setVisibility(View.VISIBLE);
            mTvBEContent.setText(mResultData.getE());
        }
        //正确答案
        mTvBAnswer.setText(mResultData.getChoiceanswer());
        mChbBCollect.setChecked(mResultData.isIsfav());
        if (StringUtil.isEmpty(mResultData.getAnalysis())) {
            mTvBRosoleContent.setText(null);
        } else {
            URLImageParser parser = new URLImageParser(mTvBRosoleContent);
            Spanned spanned = Html.fromHtml(mResultData.getAnalysis(), parser, null);
            mTvBRosoleContent.setMovementMethod(ScrollingMovementMethod.getInstance());
            mTvBRosoleContent.setText(spanned);
        }
    }

    /**
     * 绑定数据
     *
     * @param isdo
     * @param item
     */
    private void bindResultExamData(boolean isdo, UseSelectItemInfomVo item) {
        if (isdo) {//用户做过 未做不处理
            mLlBAnswerKey.setVisibility(View.GONE);
            mLlBJiexi.setVisibility(View.GONE);
            mLlBRightLu.setVisibility(View.GONE);
            mRlLookEvalue.setVisibility(View.GONE);
            mLlSelectRootLayout.setVisibility(View.VISIBLE);
            mRlvEualeContent.setVisibility(View.GONE);
            rl_euale_content.setVisibility(View.GONE);
            if (mTitleType.equals(mTitleTypeOnly)) {//单选模式
                mBtnLookWenAnswer.setVisibility(View.GONE);
                mLlLookWenDa.setVisibility(View.GONE);
                mLlSelectRootLayout.setVisibility(View.VISIBLE);
                String onlyitme = item.getItem();
                if (!StringUtil.isEmpty(onlyitme)) {
                    showAnswerCardSelect(onlyitme);
                }
            } else if (mTitleType.equals(mTitleTypeMore)) {//多选模式
                mBtnLookWenAnswer.setVisibility(View.GONE);
                mLlLookWenDa.setVisibility(View.GONE);
                mLlSelectRootLayout.setVisibility(View.VISIBLE);
                String a = item.getSelectItemA();
                String b = item.getSelectItemB();
                String c = item.getSelectItemC();
                String d = item.getSelectItemD();
                String e = item.getSelectItemE();
                showAnserCardSelect(a, b, c, d, e);
            } else if (mTitleType.equals(mTitleTypeWrite)) {//问答
                mLlSelectRootLayout.setVisibility(View.GONE);
                mBtnLookWenAnswer.setVisibility(View.GONE);
                mLlLookWenDa.setVisibility(View.VISIBLE);
            }
        } else {
            if (mTitleType.equals(mTitleTypeWrite)) {
                mLlLookWenDa.setVisibility(View.GONE);
                mBtnLookWenAnswer.setVisibility(View.VISIBLE);
                mLlBAnswerKey.setVisibility(View.GONE);
                mLlBJiexi.setVisibility(View.GONE);
                mLlBRightLu.setVisibility(View.GONE);
                mRlLookEvalue.setVisibility(View.GONE);
                mLlSelectRootLayout.setVisibility(View.GONE);
                mRlvEualeContent.setVisibility(View.GONE);
                rl_euale_content.setVisibility(View.GONE);
            } else {
                mLlBAnswerKey.setVisibility(View.GONE);
                mLlBJiexi.setVisibility(View.GONE);
                mLlLookWenDa.setVisibility(View.GONE);
                mBtnLookWenAnswer.setVisibility(View.GONE);
                mLlBRightLu.setVisibility(View.GONE);
                mRlvEualeContent.setVisibility(View.GONE);
                rl_euale_content.setVisibility(View.GONE);
                mRlLookEvalue.setVisibility(View.GONE);
                mLlSelectRootLayout.setVisibility(View.VISIBLE);
            }
        }
        mTvBType.setText(AnswerCardUtil.getTextType(mResultData.getQuestiontype()));
        if (StringUtil.isEmpty(mResultData.getQuestion())) {
            mTvBMatter.setText(null);
        } else {
            Spanned html = Html.fromHtml(mResultData.getQuestion());
            mTvBMatter.setMovementMethod(ScrollingMovementMethod.getInstance());
            mTvBMatter.setText(html.toString().trim());
        }
        mTvBAContent.setText(mResultData.getA());
        mTvBBContent.setText(mResultData.getB());
        mTvBCContent.setText(mResultData.getC());
        mTvBDContent.setText(mResultData.getD());
        if (StringUtil.isEmpty(mResultData.getE())) {//是否有e选项
            mIvBE.setVisibility(View.GONE);
            mTvBEContent.setVisibility(View.GONE);
        } else {
            mIvBE.setVisibility(View.VISIBLE);
            mTvBEContent.setVisibility(View.VISIBLE);
            mTvBEContent.setText(mResultData.getE());
        }
        Spanned fromHtml = null;
        if (StringUtil.isEmpty(mResultData.getAnalysis())) {
            fromHtml = null;
        } else
            fromHtml = Html.fromHtml(mResultData.getAnalysis());
        if (isBuy)
            mTvLookAnswerWen.setText(fromHtml);
    }

    private void setShowLayout() {
        mTvRootEmpty.setVisibility(View.GONE);
        mRlRootLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_b_more://设置
                showPopwindow();
                break;
            case R.id.ll_b_back://上一题
                goBefore();
                break;
            case R.id.ll_b_go://下一题
                NextGo();
                break;
            case R.id.iv_b_expand://扩展文件夹
                if (isSubmit) {
                    showAnswerCardResultLayout();
                } else
                    showAnswerCardLayout();
                break;
            case R.id.ll_b_a_select://选择a
                //单选/多选
                if (StringUtil.isEmpty(mTitleType))
                    return;
                if (mTitleType.equals(mTitleTypeOnly)) {//单选
                    mSelectOnlyitem = A;
                    setSelectOnlyItemBG(true, false, false, false, false);
                    if (isExamHine) {
                        setGoNextDan();
                    }
                } else if (mTitleType.equals(mTitleTypeMore)) {//多选
                    mSelectMorItemA = A;
                    if (isClickA) {
                        isClickA = false;
                        mSelectMorItemA = null;
                    } else {
                        isClickA = true;
                    }
                    setSureKeyBg();
                    setSelectMoreItemBG(0, isClickA);
                }

                break;
            case R.id.ll_b_b_select://选择b
                if (StringUtil.isEmpty(mTitleType))
                    return;
                if (mTitleType.equals(mTitleTypeOnly)) {             //单选
                    mSelectOnlyitem = B;
                    setSelectOnlyItemBG(false, true, false, false, false);
                    if (isExamHine) {
                        setGoNextDan();
                    }
                } else if (mTitleType.equals(mTitleTypeMore)) {//多选
                    mSelectMorItemB = B;
                    if (isClickB) {
                        isClickB = false;
                        mSelectMorItemB = null;
                    } else {
                        isClickB = true;
                    }
                    setSureKeyBg();
                    setSelectMoreItemBG(1, isClickB);
                }
                break;
            case R.id.ll_b_cselect://选择c
                if (StringUtil.isEmpty(mTitleType))
                    return;
                if (mTitleType.equals(mTitleTypeOnly)) {     //单选
                    mSelectOnlyitem = C;
                    setSelectOnlyItemBG(false, false, true, false, false);
                    if (isExamHine) {
                        setGoNextDan();
                    }
                } else if (mTitleType.equals(mTitleTypeMore)) {//多选
                    mSelectMorItemC = C;
                    if (isClickC) {
                        isClickC = false;
                        mSelectMorItemC = null;
                    } else {
                        isClickC = true;
                    }
                    setSureKeyBg();
                    setSelectMoreItemBG(2, isClickC);
                }
                break;
            case R.id.ll_b_d_select://选择d
                if (StringUtil.isEmpty(mTitleType))
                    return;
                if (mTitleType.equals(mTitleTypeOnly)) {//单选
                    mSelectOnlyitem = D;
                    setSelectOnlyItemBG(false, false, false, true, false);
                    if (isExamHine) {
                        setGoNextDan();
                    }

                } else if (mTitleType.equals(mTitleTypeMore)) {//多选
                    mSelectMorItemD = D;
                    if (isClickD) {
                        isClickD = false;
                        mSelectMorItemD = null;
                    } else {
                        isClickD = true;

                    }
                    setSureKeyBg();
                    setSelectMoreItemBG(3, isClickD);

                }
                break;
            case R.id.ll_b_e_select://选择e
                if (StringUtil.isEmpty(mTitleType))
                    return;
                if (mTitleType.equals(mTitleTypeOnly)) {        //单选
                    mSelectOnlyitem = E;
                    setSelectOnlyItemBG(false, false, false, false, true);
                    if (isExamHine) {
                        setGoNextDan();
                    }
                } else if (mTitleType.equals(mTitleTypeMore)) {//多选
                    mSelectMorItemE = E;
                    if (isClickE) {
                        isClickE = false;
                        mSelectMorItemE = null;
                    } else {
                        isClickE = true;
                    }
                    setSureKeyBg();
                    setSelectMoreItemBG(4, isClickE);
                }
                break;
            case R.id.btn_b_buy://购买
                QuestionsBean datasBean = mTextDetial.get(mMark);
//                Intent intent = new Intent(AnswerActivity.this, BankBuyActivity.class);
                Intent intent = BankBuyActivity.newInstance(mContext, String.valueOf(datasBean.getCourseid()), BankBuyActivity.TEXT);
                startActivity(intent);
                break;
            case R.id.btn_b_sure_key://多选确认
                isSure = true;
                if (isExamHine) {
                    setGoNextDan();
                } else {
                    NextGo();
                }
                if (isExamHine) {
                    mBtnBSureKey.setVisibility(View.GONE);
                } else {
                    mBtnBSureKey.setClickable(false);
                    mBtnBSureKey.setBackgroundResource(R.drawable.btn_b_sure_n);
                }
                break;
            case R.id.tv_answer_addevlua://添加评价
                mLiXia.setVisibility(View.GONE);
                mLlBSubmitEvalue.setVisibility(View.VISIBLE);
                Utils.showSoftInputFromWindow(AnswerActivity.this, mEtBSubmit);
                break;
            case R.id.iv_b_submit_send:
                submitEvalue();
                break;
            case R.id.iv_title_back:
                if (!isExamHine) {
                    DialogUtil instance = DialogUtil.getInstance();
                    instance.showTitleDialog(mContext, "确定退出答题", "退出答题"
                            , "取消", true);
                    instance.setTitleClickListener(new DialogUtil.onTitleClickListener() {
                        @Override
                        public void onSureClickListener() {
                            finish();
                        }

                        @Override
                        public void onCancelClickListener() {
                        }
                    });

                } else {
                    this.finish();
                }
                break;
            case R.id.btn_look_wen_answer:
                isUserLookWenJieXi = true;
                mBtnLookWenAnswer.setVisibility(View.GONE);
                if (isBuy) {
                    submitQuestionResult(true);
                    mLlLookWenDa.setVisibility(View.VISIBLE);
                    mLiBResolveBuy.setVisibility(View.GONE);
                } else {
                    mLiBResolveBuy.setVisibility(View.VISIBLE);
                    mLlLookWenDa.setVisibility(View.GONE);
                }
                break;

            case R.id.iv_bar_delect:
                DialogUtil dialogUtil11 = DialogUtil.getInstance();
                dialogUtil11.showTitleDialog(mContext, "确认删除", "删除", "取消", false);
                dialogUtil11.setTitleClickListener(new DialogUtil.onTitleClickListener() {
                    @Override
                    public void onSureClickListener() {
                        isBtnClickDel = true;
                        mPresnter.submitWoringQeustinDelect(mContext, String.valueOf(mResultData.getId()));
                    }

                    @Override
                    public void onCancelClickListener() {
                    }
                });
                break;
            case R.id.iv_ban_jianpan:
                doHineInput();
               /* mLiXia.setVisibility(View.VISIBLE);
                mLlBSubmitEvalue.setVisibility(View.GONE);
                Utils.hideInputMethod(mContext,mEtBSubmit);*/
                break;
        }
    }

    private void NextGo() {
        if (mTextDetial == null || mTextDetial.isEmpty())
            return;
        if (StringUtil.isEmpty(mTitleType)) {
            return;
        }
        if (mTitleType.equals(mTitleTypeMore)) {//多选
            if (!isSure) {
                T.showToast(mContext, "请点击确认");
                return;
            }
        }
        saveBeforeDate();
        if (mMark <= mTextDetial.size() - 2) {
            ++mMark;
        } else {//没有题了

            if (!StringUtil.isEmpty(mStyleCase)) {
                T.showToast(mContext, "已经是最后一题,请交卷");
            } else {
                T.showToast(mContext, "已经是最后一题 ");
            }
            return;
        }
        clearbg();
        //清空选项
        clearSeletItem();
        bindTextNumberData();
    }

    private void goBefore() {
        if (mTextDetial == null || mTextDetial.isEmpty())
            return;
        if (StringUtil.isEmpty(mTitleType))
            return;
        if (mTitleType.equals(mTitleTypeMore)) {//多选
            if (!isSure) {
                T.showToast(mContext, "请点击确认");
                return;
            }
        }
        if (mMark != 0) {
            --mMark;
        } else if (mMark == 0) {
            T.showToast(mContext, "已经是第一题");
            return;
        }
        saveBeforeDate();
        clearbg();
        //清空选项
        clearSeletItem();
        bindTextNumberData();
    }

    /**
     * 是否自动跳转下一题
     */
    private void setGoNextDan() {
        saveBeforeDate();
        //正确自动
        if (!StringUtil.isEmpty(mTitleType) && mTitleType.equals(mTitleTypeOnly)) {//单选模式
            if (mResultData.getChoiceanswer().equalsIgnoreCase(mSelectOnlyitem)) {//正确
                bindViewData(mTextDetialNew);
                goNext();
            } else {//错误
                bindViewData(mTextDetialNew);
            }
        } else if (!StringUtil.isEmpty(mTitleType) && mTitleType.equals(mTitleTypeMore)) {//多选模式
            mBtnBSureKey.setVisibility(View.VISIBLE);
            mBtnBSureKey.setClickable(true);
            String cont = null;
            List<String> list = getAnswerKeyList(mResultData.getChoiceanswer());
            ArrayList<String> mResult = new ArrayList<>();

            if (!StringUtil.isEmpty(mSelectMorItemA)) {
                mResult.add(mSelectMorItemA);
            }
            if (!StringUtil.isEmpty(mSelectMorItemB)) {
                mResult.add(mSelectMorItemB);
            }

            if (!StringUtil.isEmpty(mSelectMorItemC)) {
                mResult.add(mSelectMorItemC);
            }
            if (!StringUtil.isEmpty(mSelectMorItemD)) {
                mResult.add(mSelectMorItemD);
            }

            if (!StringUtil.isEmpty(mSelectMorItemE)) {
                mResult.add(mSelectMorItemE);
            }
            if (!StringUtil.isEmpty(mTitleType) && mTitleType.equals(mTitleTypeMore)) {
                if (mResult.size() > list.size()) {
                    cont = "1";
                } else if (mResult.size() == list.size()) {
                    boolean b = list.containsAll(mResult);
                    if (b) {
                        cont = "0";
                    } else {
                        cont = "1";
                    }
                } else if (mResult.size() < list.size()) {
                    if (list.containsAll(mResult)) {
                        cont = "2";
                    } else {
                        cont = "1";
                    }
                }
            }
            if (cont.equals("0")) {
                goNext();
            } else {
                bindViewData(mTextDetialNew);
            }
        }
    }

    private void goNext() {
        if (mSelectNext) {//是否自动跳
            if (!isSure) {
                T.showToast(mContext, "请点击确认");
                return;
            }
            saveBeforeDate();
            if (mMark <= mTextDetial.size() - 2) {
                ++mMark;
            } else {//没有题了
                T.showToast(mContext, "已经是最后一题 ");
                return;
            }
            clearbg();
            //清空选项
            clearSeletItem();
            bindTextNumberData();
        }
    }

    /**
     * 提交评价
     */
    private void submitEvalue() {
        String str = getTextStr(mEtBSubmit);
        if (StringUtil.isEmpty(str)) {
            T.showToast(mContext, getStringWithId(R.string.content_is_empty));
            return;
        }
        doHineInput();
        mSubmitDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_loading));
        mEvaluePresenter.submitContent(mContext, String.valueOf(mResultData.getId()), str, null, DataMessageVo.QUESTION);
        Utils.hideInputMethod(mContext, mEtBSubmit);

    }

    /***
     * 清空字体颜色任何背景
     */
    private void clearbg() {
        isClickA = false;
        isClickB = false;
        isClickC = false;
        isClickD = false;
        isClickE = false;
        setSelectOnlyItemBG(false, false, false, false, false);
        if (mSelectViewBgZC.equals(mSelectViewBgZCC)) {
            setTvColor(mTvBAContent);
            setTvColor(mTvBBContent);
            setTvColor(mTvBCContent);
            setTvColor(mTvBDContent);
            setTvColor(mTvBEContent);
        } else if (mSelectViewBgZC.equals(mSelectViewBgHY)) {
            setTvColor(mTvBAContent);
            setTvColor(mTvBBContent);
            setTvColor(mTvBCContent);
            setTvColor(mTvBDContent);
            setTvColor(mTvBEContent);
        }
        mBtnBSureKey.setBackgroundResource(R.drawable.btn_b_sure_n);
    }


    private void saveBeforeDate() {
        if (isUserLookResultJieXi) {
            return;
        }
        if (StringUtil.isEmpty(mTitleType)) {
            return;
        }
        //用户是否选中 没有选中不保存
        if (mTitleType.equals(mTitleTypeOnly)) {
            if (StringUtil.isEmpty(mSelectOnlyitem)) {
                return;
            }
        } else if (mTitleType.equals(mTitleTypeMore)) {
            if (StringUtil.isEmpty(mSelectMorItemB) && StringUtil.isEmpty(mSelectMorItemA) &&
                    StringUtil.isEmpty(mSelectMorItemC) && StringUtil.isEmpty(mSelectMorItemD)
                    && StringUtil.isEmpty(mSelectMorItemE)) {
                return;
            }
        } else if (mTitleType.equals(mTitleTypeWrite)) {//问答
            if (!isUserLookWenJieXi) {
                return;
            }

        }

        if (mTextDetialNew == null)
            return;
        SharedSeletResultListUtil sp = SharedSeletResultListUtil.getInstance();

        int id = mTextDetialNew.getData().getId();
        //保存用户选中结果
        if (mSeletList == null) {
            mSeletList = new ArrayList<>();
        }
        //保存结果
        UseSelectItemInfomVo vo = new UseSelectItemInfomVo();
        vo.setType(mTitleType);
        //获取上次保存内容
        List<UseSelectItemInfomVo> user = sp.getUser();
        if (user != null && !user.isEmpty() && user.size() != 0) {
            for (int i = 0; i < user.size(); i++) {
                UseSelectItemInfomVo infomVo = user.get(i);
                if (infomVo.getId() == mTextDetialNew.getData().getId()) {
                    if (StringUtil.isEmpty(mStyleCase) && StringUtil.isEmpty(mFreeQuestion)) {//不是考试/自由组卷就不重复保存
                        return;
                    } else {//删除旧的保存新的
                        if (mSeletList != null && !mSeletList.isEmpty()) {//删除集合
                            for (int j = 0; j < mSeletList.size(); j++) {
                                if (mSeletList.get(j).getId() == mTextDetialNew.getData().getId()) {
                                    mSeletList.remove(j);
//                                    vo = infomVo;
                                    vo.setId(infomVo.getId());

                                }
                            }
                        }
                    }
                }

            }
        }
//        }else {
//            ArrayList<UseSelectItemInfomVo> vos = new ArrayList<>();
//            sp.putListAdd(vos);
//        }
//            问题id
        vo.setId(id);
        if (!StringUtil.isEmpty(mSelectOnlyitem)) {//单选保存正确
            if (mSelectOnlyitem.equalsIgnoreCase(mResultData.getChoiceanswer())) {//正确
                vo.setItemStatus("0");
                submitQuestionResult(true);
                UserLookVo userLookVo = new UserLookVo();
                userLookVo.setChapterId(String.valueOf(id));
                updataRightQuestion(userLookVo);
            } else {//错误
                vo.setItemStatus("1");
                submitQuestionResult(false);
            }
            vo.setItem(mSelectOnlyitem);
        }
        List<String> list = getAnswerKeyList(mResultData.getChoiceanswer());
        //用户选中结果
        ArrayList<String> mResult = new ArrayList<>();

        if (!StringUtil.isEmpty(mSelectMorItemA)) {//单选
            vo.setSelectItemA(mSelectMorItemA);
            mResult.add(mSelectMorItemA.toUpperCase());
        }
        if (!StringUtil.isEmpty(mSelectMorItemB)) {
            vo.setSelectItemB(mSelectMorItemB);
            mResult.add(mSelectMorItemB.toUpperCase());
        }

        if (!StringUtil.isEmpty(mSelectMorItemC)) {
            vo.setSelectItemC(mSelectMorItemC);
            mResult.add(mSelectMorItemC.toUpperCase());
        }
        if (!StringUtil.isEmpty(mSelectMorItemD)) {
            vo.setSelectItemD(mSelectMorItemD);
            mResult.add(mSelectMorItemD.toUpperCase());
        }

        if (!StringUtil.isEmpty(mSelectMorItemE)) {
            vo.setSelectItemE(mSelectMorItemE);
            mResult.add(mSelectMorItemE.toUpperCase());
        }
        if (!StringUtil.isEmpty(mTitleType) && mTitleType.equals(mTitleTypeMore)) {//多选 保存正确
            if (mResult.size() > list.size()) {
                vo.setItemStatus("1");
                submitQuestionResult(false);
            } else if (mResult.size() == list.size()) {
                boolean b = list.containsAll(mResult);
                if (b) {
                    vo.setItemStatus("0");
                    UserLookVo userLookVo = new UserLookVo();
                    userLookVo.setChapterId(String.valueOf(id));
                    updataRightQuestion(userLookVo);
                    submitQuestionResult(true);
                } else {
                    vo.setItemStatus("1");
                    submitQuestionResult(false);
                }
            } else if (mResult.size() < list.size()) {
                ArrayList<String> strings = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    strings.add(list.get(i).toUpperCase());
                }
                if (strings.containsAll(mResult)) {
                    vo.setItemStatus("2");
                    submitQuestionResult(false);
                } else {
                    vo.setItemStatus("1");
                    submitQuestionResult(false);
                }
            }
        }
        //保存题号
        vo.setItemSelect(mMark);
        mSeletList.add(vo);

        //保存
        sp.DeleteUser();
        sp.putListAdd(mSeletList);

    }

    private void updataRightQuestion(UserLookVo vo) {
        if (!StringUtil.isEmpty(mTagType) && mTagType.equals(ERRTYPE)) {//错题模式
            ArrayList<UserLookVo> data = new ArrayList<>();
            if (data != null || !data.isEmpty()) {
                data.clear();
            }
            data.add(vo);
            UserInfomDb db = DbHelperAssist.getInstance(getApplicationContext()).queryWithuuUserInfom();
            if (mCouresidUser.equals("1")) {//技术
                if (db == null) {
                    return;
                }
                //更新数据库
                DbHelperAssist.getInstance(getApplicationContext()).upDataWoringSkill(data);
                //查询
                db = DbHelperAssist.getInstance(getApplicationContext()).queryWithuuUserInfom();
                String delectQuestion = db.getDelectQuestion();
                List<UserLookVo> skill = db.getWrongDataSkill();
                delectWroingQuestion(delectQuestion, skill, vo.getChapterId());
            } else if (mCouresidUser.equals("2")) {//综合
                if (db == null) {
                    return;
                }
                DbHelperAssist.getInstance(getApplicationContext()).updataWoringColoct(data);
                db = DbHelperAssist.getInstance(getApplicationContext()).queryWithuuUserInfom();
                String delectQuestion = db.getDelectQuestion();
                List<UserLookVo> colo = db.getWrongDataColoct();
                delectWroingQuestion(delectQuestion, colo, vo.getChapterId());

            } else if (mCouresidUser.equals("3")) {//案例
                if (db == null) {
                    return;
                }
                DbHelperAssist.getInstance(getApplicationContext()).updateWoringCase(data);
                db = DbHelperAssist.getInstance(getApplicationContext()).queryWithuuUserInfom();
                String delectQuestion = db.getDelectQuestion();
                List<UserLookVo> caselist = db.getWrongDataCase();
                delectWroingQuestion(delectQuestion, caselist, vo.getChapterId());
            }

        }
    }

    /***
     * 提交删除错题
     * @param delectQuestion 几次移除错误
     * @param skill
     */
    private void delectWroingQuestion(String delectQuestion, List<UserLookVo> skill, String id) {
        if (skill == null || skill.isEmpty()) {
            return;
        }
        if (StringUtil.isEmpty(delectQuestion)) {
            for (int i = 0; i < skill.size(); i++) {
                UserLookVo userLookVo = skill.get(i);
                String count = userLookVo.getNextId();
                if (!StringUtil.isEmpty(count) && count.equals("3")) {//发送移除
                    mPresnter.submitWoringQeustinDelect(mContext, userLookVo.getChapterId());
                    DbHelperAssist.getInstance(getApplicationContext()).delectDBWring(userLookVo.getChapterId(), mCouresidUser);
                }
            }
        } else {
            for (int i = 0; i < skill.size(); i++) {
                UserLookVo vo = skill.get(i);
                if (StringUtil.isEmpty(vo.getNextId())) {
                    continue;
                }
                int count = Integer.parseInt(vo.getNextId());
                if (delectQuestion.equalsIgnoreCase("No")) {
                    return;
                }
                int set = Integer.parseInt(delectQuestion);
                if (vo.getNextId() != null && id.equals(vo.getChapterId()) && count >= set) {
                    mPresnter.submitWoringQeustinDelect(mContext, vo.getChapterId());
                    DbHelperAssist.getInstance(getApplicationContext()).delectDBWring(vo.getChapterId(), mCouresidUser);
                }
            }
        }
    }

    /**
     * 提交答题记录
     *
     * @param isRight
     */
    private void submitQuestionResult(boolean isRight) {
        if (isExamHine)
            mPresnter.submitDoRecord(mContext, String.valueOf(mResultData.getId()), isRight, mMark + 1, mTargetid, mQt);

    }

    /**
     * 清空选项
     */
    private void clearSeletItem() {
        mSelectOnlyitem = null;
        mSelectMorItemA = null;
        mSelectMorItemB = null;
        mSelectMorItemC = null;
        mSelectMorItemD = null;
        mSelectMorItemE = null;
        isMoreData = true;
        if (StringUtil.isEmpty(mFreeQuestion) && StringUtil.isEmpty(mStyleCase)) {//处理是否是考试界面
            isUserLookResultJieXi = false;
        }
        clearClick();
        clearStarNumber();
    }

    /**
     * 设置是否能点击
     */
    private void setIsClick(boolean isClick) {
        mLlBASelect.setClickable(isClick);
        mLlBBSelect.setClickable(isClick);
        mLlBCselect.setClickable(isClick);
        mLlBDSelect.setClickable(isClick);
        mLlBESelect.setClickable(isClick);

    }

    /**
     * 显示pop
     */
    private void showPopwindow() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        popMore = new CommonPopupWindow(this, R.layout.popw_more_layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
            private TextView mTvSettring;
            private TextView mTvShare;

            @Override
            protected void initView() {
                View view = getContentView();
                mTvSettring = view.findViewById(R.id.tv_popu_setting);
                mTvShare = view.findViewById(R.id.tv_popi_share);
            }

            @Override
            protected void initEvent() {
                mTvSettring.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupWindow popupWindow = popMore.getPopupWindow();
                        popupWindow.dismiss();
                        showSettring();
                    }
                });
                mTvShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupWindow popupWindow = popMore.getPopupWindow();
                        popupWindow.dismiss();
                        showShareLayout();

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
                        setBackgroundAlpha(1f, AnswerActivity.this);
                    }
                });
            }
        };
        popMore.showAtLocation(mLlRootLayout, Gravity.BOTTOM, 0, 0);
        setBackgroundAlpha(0.5f, AnswerActivity.this);
    }


    /**
     * 设置白夜布局
     */
    private void showSettring() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        popSetting = new CommonPopupWindow(this, R.layout.pop_item_setting, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
            private CheckBox mChbSetNight;
            private CheckBox mChbSetEye;
            private RadioButton mRdbPop1;
            private RadioButton mRdbPop3;
            private RadioButton mRdbPop5;
            private RadioButton mRdbPopNo;

            @Override
            protected void initView() {
                View view = getContentView();
                mChbSetEye = view.findViewById(R.id.rdb_setting_eye);
                mChbSetNight = view.findViewById(R.id.rdb_setting_night);
                mSwtNext = view.findViewById(R.id.swt_select_next);
                mRdbPop1 = view.findViewById(R.id.rdb_pop_1);
                mRdbPop3 = view.findViewById(R.id.rdb_pop_3);
                mRdbPop5 = view.findViewById(R.id.rdb_pop_5);
                mRdbPopNo = view.findViewById(R.id.rdb_pop_no);
            }

            @Override

            protected void initEvent() {
                UserInfomDb userInfomDb = DbHelperAssist.getInstance(mContext).queryWithuuUserInfom();
                if (userInfomDb != null) {
                    String question = userInfomDb.getDelectQuestion();
                    if (!StringUtil.isEmpty(question)) {
                        if (question.equals("1")) {
                            mRdbPop1.setChecked(true);
                        }
                        if (question.equals("3")) {
                            mRdbPop3.setChecked(true);
                        }
                        if (question.equals("5")) {
                            mRdbPop5.setChecked(true);
                        }
                        if (question.equalsIgnoreCase("NO")) {
                            mRdbPopNo.setChecked(true);
                        }
                    }
                }
                mRdbPop1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            DbHelperAssist.getInstance(getApplicationContext()).upLooklistDelect();
                            DbHelperAssist.getInstance(getApplicationContext()).upDataDelect("1");
                        }
                    }
                });
                mRdbPop3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            DbHelperAssist.getInstance(getApplicationContext()).upLooklistDelect();
                            DbHelperAssist.getInstance(getApplicationContext()).upDataDelect("3");
                        }
                    }
                });
                mRdbPop5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            DbHelperAssist.getInstance(getApplicationContext()).upLooklistDelect();
                            DbHelperAssist.getInstance(getApplicationContext()).upDataDelect("5");
                        }
                    }
                });
                mRdbPopNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            DbHelperAssist.getInstance(getApplicationContext()).upLooklistDelect();
                            DbHelperAssist.getInstance(getApplicationContext()).upDataDelect("NO");
                        }
                    }
                });
                mChbSetNight.setChecked(isNight);
                mChbSetEye.setChecked(isEye);
                mChbSetEye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        isEye = isChecked;
                        if (isChecked) {
                            if (isNight) {
                                isNight = false;
                                mChbSetNight.setChecked(false);
                            }
                            mSelectViewBgZC = mSelectViewBgHY;
                            DbHelperAssist.getInstance(getApplicationContext()).upDataDayOrNight(mSelectViewBgZC);
                            setLayoutBg();
                        } else {
                            mSelectViewBgZC = mSelectViewBgZCC;
                            setZhLayout();
                            DbHelperAssist.getInstance(getApplicationContext()).upDataDayOrNight(mSelectViewBgZC);
                        }
                    }
                });
                mChbSetNight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        isNight = isChecked;
                        if (isChecked) {
                            if (isEye) {
                                isEye = false;
                                mChbSetEye.setChecked(false);
                            }
                            mSelectViewBgZC = mSelectViewBgYJ;
                            DbHelperAssist.getInstance(getApplicationContext()).upDataDayOrNight(mSelectViewBgZC);
                            setLayoutBg();
                        } else {
                            mSelectViewBgZC = mSelectViewBgZCC;
                            setZhLayout();
                            DbHelperAssist.getInstance(getApplicationContext()).upDataDayOrNight(mSelectViewBgZC);
                        }


                    }
                });
                mSwtNext.setChecked(mSelectNext);
                mSwtNext.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        mSelectNext = isChecked;
                        DbHelperAssist.getInstance(getApplicationContext()).upDataNextGo(isChecked);

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
                        setBackgroundAlpha(1f, AnswerActivity.this);
                    }
                });
            }
        };
        popSetting.showAtLocation(mLlRootLayout, Gravity.BOTTOM, 0, 0);
        setBackgroundAlpha(0.5f, AnswerActivity.this);

    }

    /**
     * 设置答题卡布局
     */
    private void showAnswerCardLayout() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        popAnswer = new CommonPopupWindow(this, R.layout.pop_item_answer, ViewGroup.LayoutParams.MATCH_PARENT, (int) (screenHeight * 0.7)) {
            private Button mBtnSubmit;
            private TextView mTvPopNew;
            private TextView mTvPopCount;
            private RecyclerView mRlvPopContent;
            private GridView mGvPopContent;

            @Override
            protected void initView() {
                View view = getContentView();
                mTvPopNew = (TextView) view.findViewById(R.id.tv_pop_new);
                mTvPopCount = (TextView) view.findViewById(R.id.tv_pop_count);
                mRlvPopContent = view.findViewById(R.id.rlv_pop_content);
                mGvPopContent = view.findViewById(R.id.gv_pop_content);
                mBtnSubmit = (Button) view.findViewById(R.id.btn_pop_answer_sumbit);
            }

            @Override
            protected void initEvent() {
                if (isExamHine) {
                    mBtnSubmit.setVisibility(View.GONE);
                } else {
                    mBtnSubmit.setVisibility(View.VISIBLE);
                }
                mBtnSubmit.getParent().requestDisallowInterceptTouchEvent(true);
                mBtnSubmit.setOnClickListener(new View.OnClickListener() {//交卷
                    @Override
                    public void onClick(View v) {
                        showDialog();
                    }
                });
                mTvPopNew.setText(String.valueOf(mMark + 1));
                mTvPopCount.setText(String.valueOf(mTextDetial.size()));
                bindGridViewAdapter();
            }

            private void showDialog() {
                List<UseSelectItemInfomVo> user = SharedSeletResultListUtil.getInstance().getUser();
                DialogUtil dialogUtil = DialogUtil.getInstance();
                if (user.size() < mTextDetial.size())
                    dialogUtil.setIsDoOver(false);
                else
                    dialogUtil.setIsDoOver(true);
                dialogUtil.showSubmitDialog(mContext);
                dialogUtil.setSubmitClickListener(new DialogUtil.onSubmitClickListener() {
                    @Override
                    public void onCancelClickListener() {
                    }

                    @Override
                    public void oSubmitClickListener() {
                        mDoTime = getTextStr(mActivityTitleText);
                        if (mTimeUitl != null) {
                            long time = mTimeUitl.getNubmer();
                            if (time != 0) {
                                mDoTime = TimeTools.getCountTimeByLong(time);
                            }
                        }
                        isSubmit = true;
                        saveBeforeDate();
                        showAnswerCardResultLayout();
                        //取消时间
                        mTimeUitl.cancel();
                        mIvTimePlay.setImageResource(R.mipmap.qbank_answer_icon_cont);
                        mVBLineBar.setClickable(false);
                        mActivityTitleText.setText(startTime);
                        popAnswer.getPopupWindow().dismiss();
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
                        setBackgroundAlpha(1f, AnswerActivity.this);
                    }
                });
            }

            private void bindGridViewAdapter() {
                GridViewAdapter adapter = new GridViewAdapter(mContext, mTextDetial);
                adapter.setItemSelect(isExamHine, mMark);
                mGvPopContent.setSelection(mMark);
                mGvPopContent.setAdapter(adapter);
                mGvPopContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mMark = position;
                        //切换是否需要保存
                        saveBeforeDate();
                        clearbg();
                        //清空选项
                        clearSeletItem();
                        popAnswer.getPopupWindow().dismiss();
                        bindTextNumberData();
                    }
                });
            }
        };
        popAnswer.showAtLocation(mLlRootLayout, Gravity.BOTTOM, 0, 0);
        setBackgroundAlpha(0.5f, AnswerActivity.this);
    }

    /**
     * 答案布局
     */
    private void showAnswerCardResultLayout() {
        popResult = new CommonPopupWindow(this, R.layout.activity_answer_result, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) {
            private List<UseSelectItemInfomVo> user;
            private ImageView mIcPopBack;
            private TextView mTvResultNumber;
            private TextView mTvAnswerTime;
            private TextView mTvAnswerLv;
            private TextView mTvPopTitle;
            private RecyclerView mRlvAnswerResultBag;
            private GridView mGvAnswerResultBag;
            private Button mBtnAnswerAgain;
            private Button mBtnAnswerJiexi;

            @Override
            protected void initView() {
                View view = getContentView();
                mTvResultNumber = (TextView) view.findViewById(R.id.tv_result_number);
                mTvAnswerTime = (TextView) view.findViewById(R.id.tv_answer_time);
                mTvAnswerLv = (TextView) view.findViewById(R.id.tv_answer_lv);
                mRlvAnswerResultBag = (RecyclerView) view.findViewById(R.id.rlv_answer_result_bag);
                mGvAnswerResultBag = view.findViewById(R.id.gv_answer_result_bag);
                mBtnAnswerAgain = (Button) view.findViewById(R.id.btn_answer_again);
                mBtnAnswerJiexi = (Button) view.findViewById(R.id.btn_answer_jiexi);
                mIcPopBack = (ImageView) view.findViewById(R.id.ic_pop_result);
                mTvPopTitle = (TextView) view.findViewById(R.id.tv_pop_answer_title_question);
            }

            /**
             * 显示分数
             * @param mTvResultNumber
             * @param user
             */
            private void showUserScore(TextView mTvResultNumber, List<UseSelectItemInfomVo> user) {
                double score = 0;
                for (int i = 0; i < user.size(); i++) {
                    UseSelectItemInfomVo vo = user.get(i);
                    if (vo.getItemStatus() != null && vo.getItemStatus().equals("0")) {
                        String type = vo.getType();
                        if (type.equals(mTitleTypeOnly)) {
                            score += 1;
                        }
                        if (type.equals(mTitleTypeMore)) {
                            score += 2;
                        }
                    }
                    if (vo.getItemStatus() != null && vo.getItemStatus().equals("2")) {
                        double more = 0;
                        String a = vo.getSelectItemA();
                        String b = vo.getSelectItemB();
                        String c = vo.getSelectItemC();
                        String d = vo.getSelectItemD();
                        String e = vo.getSelectItemE();
                        if (!StringUtil.isEmpty(a)) {
                            more += 0.5;
                        }
                        if (!StringUtil.isEmpty(b)) {
                            more += 0.5;
                        }
                        if (!StringUtil.isEmpty(c)) {
                            more += 0.5;
                        }
                        if (!StringUtil.isEmpty(d)) {
                            more += 0.5;
                        }
                        if (!StringUtil.isEmpty(e)) {
                            more += 0.5;
                        }
                        if (more > 2) {
                            more = 2;
                        }
                        score += more;
                    }
                }
                mTvResultNumber.setText(String.valueOf(score));
            }

            @Override
            protected void initEvent() {
                user = SharedSeletResultListUtil.getInstance().getUser();
                int size = user.size();
                if (!StringUtil.isEmpty(mFreeQuestion) && mFreeQuestion.equals(mFreeQuestionTrue)) {//自由组卷
                    mTvPopTitle.setText(R.string.answernubmer);
                    mTvResultNumber.setText(size + "");
                } else {//模拟考试
                    mTvPopTitle.setText(R.string.score);
                    showUserScore(mTvResultNumber, user);
                }
                int right = 0;
                for (int i = 0; i < size; i++) {
                    UseSelectItemInfomVo vo = user.get(i);
                    if (vo.getItemStatus() != null && vo.getItemStatus().equals("0")) {
                        right += 1;
                    }
                }
                mTvAnswerTime.setText(mDoTime);
                //正确率
                String number = ArithUtil.divNumber(right, mTextDetial.size(), 2);
                mTvAnswerLv.setText(number + "");
                mIcPopBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popResult.getPopupWindow().dismiss();
                    }
                });
                mBtnAnswerAgain.setOnClickListener(new View.OnClickListener() {//重新做题
                    @Override
                    public void onClick(View v) {
                        if (mSeletList != null && !mSeletList.isEmpty()) {
                            mSeletList.clear();
                        }
                        SharedSeletResultListUtil.getInstance().DeleteUser();
                        mMark = 0;
                        setIsClick(true);
                        clearbg();
                        clearSeletItem();
                        isSubmit = false;
                        mTimeUitl.restart();
                        mVBLineBar.setClickable(true);
                        isUserLookResultJieXi = false;
                        popResult.getPopupWindow().dismiss();
                        bindTextNumberData();
                    }
                });
                mBtnAnswerJiexi.setOnClickListener(new View.OnClickListener() {//查看解析
                    @Override
                    public void onClick(View v) {
                        isUserLookResultJieXi = true;
                        mMark = 0;
                        mTimeUitl.cancel();
                        popResult.getPopupWindow().dismiss();
                        bindTextNumberData();
                    }
                });
                initGVResultAdapter();


            }

            /**
             * 答题卡界面
             */
            private void initGVResultAdapter() {
                GridViewResultAdapter adapter = new GridViewResultAdapter(mContext, mTextDetial);
                mGvAnswerResultBag.setAdapter(adapter);
                mGvAnswerResultBag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        clearbg();
                        mMark = position;
                        setIsClick(false);
                        popResult.getPopupWindow().dismiss();
                        bindTextNumberData();
                        isUserLookResultJieXi = true;
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
                        setBackgroundAlpha(1f, AnswerActivity.this);
                    }
                });
            }
        };
        popResult.showAtLocation(mLlRootLayout, Gravity.BOTTOM, 0, 0);
        setBackgroundAlpha(0.5f, AnswerActivity.this);
    }

    /**
     * 分享布局
     */
    private void showShareLayout() {
        popShare = new CommonPopupWindow(this, R.layout.pop_item_share, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {

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
                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mContext, mSloViewShow));
                        ShareUtils.shareImg(AnswerActivity.this, Html.fromHtml(mResultData.getQuestion()).toString().trim(),
                                pic, SHARE_MEDIA.QQ);
                        getPopupWindow().dismiss();
//                        hideScreenshot(true);
                    }
                });
                qqzon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.handleOnDoubleClick()) {
                            return;
                        }
                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mContext, mSloViewShow));
                        ShareUtils.shareImg(AnswerActivity.this, Html.fromHtml(mResultData.getQuestion()).toString().trim(),
                                pic, SHARE_MEDIA.QZONE);
                        getPopupWindow().dismiss();
                    }
                });
                weibo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.handleOnDoubleClick()) {
                            return;
                        }
                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mContext, mSloViewShow));
                        ShareUtils.shareImg(AnswerActivity.this, Html.fromHtml(mResultData.getQuestion()).toString().trim(),
                                pic, SHARE_MEDIA.SINA);
                        getPopupWindow().dismiss();
                    }
                });
                weixin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.handleOnDoubleClick()) {
                            return;
                        }
                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mContext, mSloViewShow));
                      /*  ShareUtils.shareWeb(AnswerActivity.this, Defaultcontent.url,  mResultData.getQuestion()
                                , "",pic, R.mipmap.m_setting_about_xcimg, SHARE_MEDIA.WEIXIN
                        );*/
                        ShareUtils.shareImg(AnswerActivity.this, Html.fromHtml(mResultData.getQuestion()).toString().trim(),
                                pic, SHARE_MEDIA.WEIXIN);

                        getPopupWindow().dismiss();
                    }
                });
                circle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.handleOnDoubleClick()) {
                            return;
                        }
                        String pic = ScreenShot.savePic(ScreenShot.getBitmapByView(mContext, mSloViewShow));
                        ShareUtils.shareImg(AnswerActivity.this, Html.fromHtml(mResultData.getQuestion()).toString().trim(),
                                pic, SHARE_MEDIA.WEIXIN_CIRCLE);
                        getPopupWindow().dismiss();
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
                        setBackgroundAlpha(1f, AnswerActivity.this);
                    }
                });
            }
        };
        popShare.showAtLocation(mLlRootLayout, Gravity.BOTTOM, 0, 0);
        setBackgroundAlpha(0.5f, AnswerActivity.this);
    }

    /**
     * 设置背景颜色
     *
     * @param bgAlpha
     */
    public static void setBackgroundAlpha(float bgAlpha, Context mContext) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);

    }

    /**
     * 显示隐藏截图
     *
     * @param isShow
     */
    private void hideScreenshot(boolean isShow) {
        mLlBAnswerKey.setVisibility(isShow ? View.VISIBLE : View.GONE);//答案
        mLlBJiexi.setVisibility(isShow ? View.VISIBLE : View.GONE);//解析
        mLlBRightLu.setVisibility(isShow ? View.VISIBLE : View.GONE);//星
        mRlLookEvalue.setVisibility(isShow ? View.VISIBLE : View.GONE);//评价
        mRlvEualeContent.setVisibility(isShow ? View.VISIBLE : View.GONE);//评价内容
    }

    private void showAnswerCardSelect(String item) {
        if (item.equalsIgnoreCase("A")) {
            setSelectOnlyItemBG(true, false, false, false, false);
        }
        if (item.equalsIgnoreCase("B")) {
            setSelectOnlyItemBG(false, true, false, false, false);

        }
        if (item.equalsIgnoreCase("C")) {
            setSelectOnlyItemBG(false, false, true, false, false);
        }
        if (item.equalsIgnoreCase("D")) {
            setSelectOnlyItemBG(false, false, false, true, false);
        }
        if (item.equalsIgnoreCase("E")) {
            setSelectOnlyItemBG(false, false, false, false, true);
        }

    }

    /**
     * 单选结果展示
     *
     * @param a
     * @param b
     * @param c
     * @param d
     * @param e
     */
    private void setSelectOnlyItemBG(boolean a, boolean b, boolean c, boolean d,
                                     boolean e) {
        setImgBg(mIvBA, a, R.drawable.qbank_answer_icon_single_a_s, R.drawable.ic_b_single_a_n);
        setImgBg(mIvBB, b, R.drawable.qbank_answer_icon_single_b_s, R.drawable.ic_b_single_b_n);
        setImgBg(mIvBC, c, R.drawable.qbank_answer_icon_single_c_s, R.drawable.ic_b_single_c_n);
        setImgBg(mIvBD, d, R.drawable.qbank_answer_icon_single_d_s, R.drawable.ic_b_single_d_n);
        setImgBg(mIvBE, e, R.drawable.qbank_answer_icon_single_e_s, R.drawable.ic_b_e_n);
    }

    private void clearMoreBG() {
        setSelectMoreItemBG(0, false);
        setSelectMoreItemBG(1, false);
        setSelectMoreItemBG(2, false);
        setSelectMoreItemBG(3, false);
        setSelectMoreItemBG(4, false);

    }

    /**
     * 多选结果展示
     *
     * @param id 0a,1b,2c,3d,4e
     */
    private void setSelectMoreItemBG(int id, boolean isSelect) {
        if (id == 0) {
            setImgBg(mIvBA, isSelect, R.drawable.qbank_answe_icon_multiple_a_s, R.drawable.ic_b_a_n);
        } else if (id == 1) {
            setImgBg(mIvBB, isSelect, R.drawable.qbank_answe_icon_multiple_b_s, R.drawable.ic_b_b_n);
        } else if (id == 2) {
            setImgBg(mIvBC, isSelect, R.drawable.qbank_answe_icon_multiple_c_s, R.drawable.ic_b_c_n);
        } else if (id == 3) {
            setImgBg(mIvBD, isSelect, R.drawable.qbank_answe_icon_multiple_d_s, R.drawable.ic_b_d_n);
        } else if (id == 4) {
            setImgBg(mIvBE, isSelect, R.drawable.qbank_answe_icon_multiple_e_s, R.drawable.ic_b_e_n);
        }

    }


    /**
     * 单选
     *
     * @param select
     * @param answer
     * @param day
     */
    private void setResultItemBG(String select, String answer, String day) {
        if (select.equalsIgnoreCase(answer)) {//选项正确
            ImageView imageView0 = selectItemName(select);
            if (imageView0 != null) {
                imageView0.setImageResource(R.mipmap.ic_b_right);
            }
            TextView textView = selectTextView(select);
            if (textView != null) {
                textView.setTextColor(mContext.getResources().getColor(R.color.text_color_right));
            }
        } else {
            ImageView imageView1 = selectItemName(select);
            if (imageView1 != null) {
                imageView1.setImageResource(R.mipmap.ic_b_singlewrong);
            }
            ImageView imageView2 = selectItemName(answer);
            if (imageView2 != null) {
                imageView2.setImageResource(R.mipmap.ic_b_right);
            }
            TextView textView = selectTextView(select);
            if (textView != null)
                textView.setTextColor(mContext.getResources().getColor(R.color.text_color_error));
            TextView textView1 = selectTextView(answer);
            if (textView1 != null) {
                textView1.setTextColor(mContext.getResources().getColor(R.color.text_color_right));
            }
        }


    }


    /**
     * 考试
     * 多选结果展示
     *
     * @param a
     * @param b
     * @param c
     * @param d
     * @param e
     */
    private void showAnserCardSelect(String a, String b, String c, String d, String e) {
        if (!StringUtil.isEmpty(a)) {//用户选择
            mSelectMorItemA = A;
            isClickA = true;
            setSelectMoreItemBG(0, true);
        }
        if (!StringUtil.isEmpty(b)) {//用户选择
            mSelectMorItemB = B;
            isClickB = true;
            setSelectMoreItemBG(1, true);
        }
        if (!StringUtil.isEmpty(c)) {//用户选择
            mSelectMorItemC = C;
            isClickC = true;
            setSelectMoreItemBG(2, true);
        }
        if (!StringUtil.isEmpty(d)) {//用户选择
            mSelectMorItemD = D;
            isClickD = true;
            setSelectMoreItemBG(3, true);
        }
        if (!StringUtil.isEmpty(e)) {//用户选择
            mSelectMorItemE = E;
            isClickE = true;
            setSelectMoreItemBG(4, true);
        }


    }

    /**
     * 多选结果展示
     *
     * @param a
     * @param b
     * @param c
     * @param d
     * @param e
     * @param choiceanswer
     * @param mSelectViewBgZC
     */
    private void setResultItemBG(String a, String b, String c, String d, String e, String
            choiceanswer, String mSelectViewBgZC) {
        List<String> key = getAnswerKeyList(choiceanswer);
        for (String s : key) {
            if (s.equalsIgnoreCase("A")) {
                setImgMiss(mIvBA);
                setTvMiss(mTvBAContent);
            } else if (s.equalsIgnoreCase("B")) {
                setImgMiss(mIvBB);
                setTvMiss(mTvBBContent);
            } else if (s.equalsIgnoreCase("C")) {
                setImgMiss(mIvBC);
                setTvMiss(mTvBCContent);
            } else if (s.equalsIgnoreCase("D")) {
                setImgMiss(mIvBD);
                setTvMiss(mTvBDContent);
            } else if (s.equalsIgnoreCase("E")) {
                setImgMiss(mIvBE);
                setTvMiss(mTvBEContent);
            }
        }
        if (!StringUtil.isEmpty(a)) {//用户选择
            boolean a1 = keyIsRight(key, a);
            if (a1) {//正确
                setImgRight(mIvBA);
                setTvRight(mTvBAContent);
            } else {//错误
                setImgError(mIvBA);
                setTvError(mTvBAContent);
            }
        }
        if (!StringUtil.isEmpty(b)) {//用户选择
            boolean b1 = keyIsRight(key, b);
            if (b1) {//正确
                setImgRight(mIvBB);
                setTvRight(mTvBBContent);
            } else {//错误
                setImgError(mIvBB);
                setTvError(mTvBBContent);
            }
        }
        if (!StringUtil.isEmpty(c)) {//用户选择
            boolean c1 = keyIsRight(key, c);
            if (c1) {//正确
                setImgRight(mIvBC);
                setTvRight(mTvBCContent);
            } else {//错误
                setImgError(mIvBC);
                setTvError(mTvBCContent);
            }
        }
        if (!StringUtil.isEmpty(d)) {//用户选择
            boolean d1 = keyIsRight(key, d);
            if (d1) {//正确
                setImgRight(mIvBD);
                setTvRight(mTvBDContent);
            } else {//错误
                setImgError(mIvBD);
                setTvError(mTvBDContent);
            }
        }
        if (!StringUtil.isEmpty(e)) {//用户选择
            boolean e1 = keyIsRight(key, e);
            if (e1) {//正确
                setImgRight(mIvBE);
                setTvRight(mTvBEContent);
            } else {//错误
                setImgError(mIvBE);
                setTvError(mTvBEContent);
            }
        }


    }

    /**
     * 展示解析页
     *
     * @param choiceanswer
     */
    private void setReusltWithJieXi(String choiceanswer) {
        List<String> key = getAnswerKeyList(choiceanswer);
        for (String s : key) {
            if (s.equalsIgnoreCase("A")) {
                setImgRight(mIvBA);
                setTvRight(mTvBAContent);
            } else if (s.equalsIgnoreCase("B")) {
                setImgRight(mIvBB);
                setTvRight(mTvBBContent);
            } else if (s.equalsIgnoreCase("C")) {
                setImgRight(mIvBC);
                setTvRight(mTvBCContent);
            } else if (s.equalsIgnoreCase("D")) {
                setImgRight(mIvBD);
                setTvRight(mTvBDContent);
            } else if (s.equalsIgnoreCase("E")) {
                setImgRight(mIvBE);
                setTvRight(mTvBEContent);
            }
        }


    }

    private boolean keyIsRight(List<String> listkey, String key) {
        for (String s : listkey) {
            if (s.equalsIgnoreCase(key)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 设置正确图片
     *
     * @param select
     * @return
     */
    private ImageView selectItemName(String select) {
        if (select.equalsIgnoreCase("A")) {
            return mIvBA;
        }
        if (select.equalsIgnoreCase("B")) {
            return mIvBB;
        }
        if (select.equalsIgnoreCase("C")) {
            return mIvBC;
        }
        if (select.equalsIgnoreCase("D")) {
            return mIvBD;
        }
        if (select.equalsIgnoreCase("E")) {
            return mIvBE;
        }
        return null;
    }

    /**
     * 设置文体文字
     *
     * @param select
     * @return
     */
    private TextView selectTextView(String select) {
        if (select.equalsIgnoreCase("A")) {
            return mTvBAContent;
        }
        if (select.equalsIgnoreCase("B")) {
            return mTvBBContent;
        }
        if (select.equalsIgnoreCase("C")) {
            return mTvBCContent;
        }
        if (select.equalsIgnoreCase("D")) {
            return mTvBDContent;
        }
        if (select.equalsIgnoreCase("E")) {
            return mTvBEContent;
        }
        return null;
    }

    /**
     * 设置背景
     *
     * @param iv
     * @param is
     * @param selectid
     * @param unselectid
     */
    private void setImgBg(ImageView iv, boolean is, int selectid, int unselectid) {
        if (iv == null)
            return;
        if (is) {
            iv.setImageResource(selectid);
        } else {
            iv.setImageResource(unselectid);
        }
    }


    private void setTvColor(TextView tv) {
        tv.setTextColor(mContext.getResources().getColor(R.color.black));
    }

    /**
     * 设置正确图片
     *
     * @param imgRight
     */
    private void setImgRight(ImageView imgRight) {
        imgRight.setImageResource(R.mipmap.ic_b_text_right);
    }

    /**
     * 设置正确文字图片
     *
     * @param tv
     */
    private void setTvRight(TextView tv) {
        if (tv == null) return;
        tv.setTextColor(mContext.getResources().getColor(R.color.text_color_right));
    }

    /**
     * 设置错图片
     *
     * @param imgError
     */
    private void setImgError(ImageView imgError) {
        if (imgError == null) return;
        imgError.setImageResource(R.mipmap.ic_b_erro);
    }

    /**
     * 设置错文字
     *
     * @param tv
     */
    private void setTvError(TextView tv) {
        if (tv == null) return;
        tv.setTextColor(mContext.getResources().getColor(R.color.text_color_error));
    }

    /**
     * 设置图片miss
     *
     * @param imgMiss
     */
    private void setImgMiss(ImageView imgMiss) {
        if (imgMiss == null) return;
        imgMiss.setImageResource(R.mipmap.ic_b_miss);
    }

    /**
     * 设置字体miss
     *
     * @param tv
     */
    private void setTvMiss(TextView tv) {
        if (tv == null) return;
        tv.setTextColor(mContext.getResources().getColor(R.color.text_color_woring));
    }

    /**
     * 截取
     *
     * @param key
     * @return
     */
    private List<String> getAnswerKeyList(String key) {
        if (list == null) {
            list = new ArrayList<>();
        } else {
            list.clear();
        }
        getAnswer(key);
        return list;
    }


    public void getAnswer(String key) {
        int length = key.length();
        if (length > 1) {
            String substring = key.substring(0, 1);
            list.add(substring);
            key = key.substring(1, length);
            getAnswer(key);
        } else {
            list.add(key);
        }
    }

    /**
     * 设置确认按钮
     */
    private void setSureKeyBg() {
        if (isClickD || isClickB || isClickA || isClickE || isClickC) {
            mBtnBSureKey.setClickable(true);
            isSure = false;
            mBtnBSureKey.setBackgroundResource(R.drawable.btn_b_sure_s);
        } else {
            isSure = true;
            mBtnBSureKey.setClickable(true);
            mBtnBSureKey.setBackgroundResource(R.drawable.btn_b_sure_n);
        }
    }

    /**
     * 清空用户点击
     */
    private void clearClick() {
        isClickA = false;
        isClickB = false;
        isClickC = false;
        isClickD = false;
        isClickE = false;
    }

    /**
     * 章节数据
     *
     * @param msg
     */
    @Override
    public void TextSuccess(String msg) {
        if (dialog != null) {
            dialog.dismiss();
        }
        mQt = DataMessageVo.CHAPTEREXERCISE;
        ResolveQuestionDatas(msg);
    }

    /**
     * 返回类型为数组集合
     *
     * @param msg
     */
    private void ResolveQuestionDatas(String msg) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        Gson gson = new Gson();
        QuestionAllVo vo = gson.fromJson(msg, QuestionAllVo.class);
        if (vo != null && vo.getStatus() != null)
            if (vo.getStatus().getCode() == 200) {
//            mRlRootLayout.setVisibility(View.VISIBLE);
                if (vo.getDatas() == null || vo.getDatas().isEmpty()) {
                    mRlRootLayout.setVisibility(View.GONE);
                    mTvRootEmpty.setVisibility(View.VISIBLE);
                    if (mTimeUitl != null) {
                        mTimeUitl.cancel();
                    }
                    mActivityTitleText.setText(null);
                    mIvTimePlay.setVisibility(View.GONE);

                    return;
                } else {
                    mTvRootEmpty.setVisibility(View.GONE);
                    mRlRootLayout.setVisibility(View.VISIBLE);
                }
                if (mRunm > vo.getDatas().size()) {
                    mRunm = vo.getDatas().size();
                }
                showDialogContinue(mRunm);
                mTextDetial = vo.getDatas();
                mTvBCount.setText(String.valueOf(mTextDetial.size()));
                bindTextNumberData();

            } else {
                T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
                L.e(vo.getStatus().getMessage());
            }
    }

    private void ResolveQuestionData(String msg) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        Gson gson = new Gson();
        QuestionAllVoNew vo = gson.fromJson(msg, QuestionAllVoNew.class);
        if (vo != null && vo.getStatus() != null)
            if (vo.getStatus().getCode() == 200) {
//            mRlRootLayout.setVisibility(View.VISIBLE);
                if (vo.getData().getQuestions() == null || vo.getData().getQuestions().isEmpty()) {
                    mRlRootLayout.setVisibility(View.GONE);
                    mTvRootEmpty.setVisibility(View.VISIBLE);
                    if (mTimeUitl != null) {
                        mTimeUitl.cancel();
                    }
                    mActivityTitleText.setText(null);
                    mIvTimePlay.setVisibility(View.GONE);

                    return;
                } else {
                    mTvRootEmpty.setVisibility(View.GONE);
                    mRlRootLayout.setVisibility(View.VISIBLE);
                }
                mTextDetial = vo.getData().getQuestions();
                mTvBCount.setText(String.valueOf(mTextDetial.size()));
                mRunm = vo.getData().getRnum();
                if (mQt != DataMessageVo.OTHERZONE) {
                    int size = mTextDetial.size();
                    mRunm = vo.getData().getRnum();
                    if (mRunm < size)
                        showDialogContinue(mRunm);
                    else {
                        mRunm = (size == 0 ? 0 : size);
                        showDialogContinue(mRunm);
                    }
                }
                bindTextNumberData();

            } else {
                T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
                L.e(vo.getStatus().getMessage());
            }
    }

    @Override
    public void TextError(String con) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
    }

    @Override
    public void TextDetailSuccess(String message) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        L.d("题库详情", message);
        Gson gson = new Gson();
        TextDetailVo vo = gson.fromJson(message, TextDetailVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData() == null || StringUtil.isEmpty(vo.getData().getQuestion())) {
                setShowEmptyLayout();
                return;
            }
            bindViewData(vo);

        } else {
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
//            T.showToast(mContext, vo.getStatus().getMessage());
        }
    }

    @Override
    public void TextDetailError(String con) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
//        T.showToast(mContext, con);
    }


    @Override
    public void SumbitCollectSuccess(String con) {
        L.d("收藏" + con);
    }

    @Override
    public void SumbitCollectError(String con) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        L.e("收藏" + con);
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
    }

    @Override
    public void DoResultSuccess(String con) {
        L.d("做题结果" + con);
    }

    @Override
    public void DoResultError(String con) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        L.e("做题结果" + con);
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
    }

    @Override
    public void WoringSuccess(String con) {
        Gson gson = new Gson();
        ResultVo resultVo = gson.fromJson(con, ResultVo.class);
        if (resultVo.getStatus().getCode() == 200) {
            if (resultVo.getData().getStatusX() == 1) {
                if (!isBtnClickDel) {
                    return;
                }
                if (isBtnClickDel)
                    T.showToast(mContext, getString(R.string.delect_Success));
                if (mTextDetial != null && !mTextDetial.isEmpty())
                    for (int i = 0; i < mTextDetial.size(); i++) {
                        QuestionsBean bean = mTextDetial.get(i);
                        if (bean.getId() == mResultData.getId()) {
                            mTextDetial.remove(i);
                        }

                    }
                if (mTextDetial.size() == 0) {
                    mRlRootLayout.setVisibility(View.GONE);
                    mTvRootEmpty.setVisibility(View.VISIBLE);
                    mIvBarDelect.setVisibility(View.GONE);
                    return;
                }
                --mMark;
                if (mMark >= mTextDetial.size() - 1) {
                    ++mMark;
                    goBefore();
                } else {
                    NextGo();
                }
                mTvBCount.setText(String.valueOf(mTextDetial.size()));
            }
        } else {
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
//            L.e(resultVo.getStatus().getMessage());
        }
        L.d("错题移除" + con);
    }

    @Override
    public void WoringError(String con) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        L.e("错题移除" + con);
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));

    }

    //设置难度星星
    private void setStarNumber(int number) {

        ArrayList<ImageView> list = new ArrayList<>();
        list.add(mIvBStar1);
        list.add(mIvBStar2);
        list.add(mIvBStar3);
        list.add(mIvBStar4);
        list.add(mIvBStar5);
        for (int i = 0; i < number; i++) {
            ImageView imageView = list.get(i);
            imageView.setImageResource(R.mipmap.ic_b_difficulty_s);
        }

    }

    private void clearStarNumber() {
        ArrayList<ImageView> list = new ArrayList<>();
        list.add(mIvBStar1);
        list.add(mIvBStar2);
        list.add(mIvBStar3);
        list.add(mIvBStar4);
        list.add(mIvBStar5);
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = list.get(i);
            imageView.setImageResource(R.mipmap.ic_b_difficulty_n);
        }

    }

    @Override
    public void submitEvalueSuccess(String con) {
        if (mSubmitDialog != null && mSubmitDialog.isShowing()) {
            mSubmitDialog.dismiss();
        }
        T.showToast(mContext, getStringWithId(R.string.evelua_sucee));
        mLiXia.setVisibility(View.VISIBLE);

        mLlBSubmitEvalue.setVisibility(View.GONE);
    }

    @Override
    public void submitEvalueError(String con) {
        if (mSubmitDialog != null && mSubmitDialog.isShowing()) {
            mSubmitDialog.dismiss();
        }
        mLiXia.setVisibility(View.VISIBLE);
        mLlBSubmitEvalue.setVisibility(View.GONE);
        T.showToast(mContext, getStringWithId(R.string.net_error));
    }

    @Override
    public void GetOneEvalueSuccess(String con) {
    /*    L.e("习题评价" + con);
        Gson gson = new Gson();
        EvalueVo vo = gson.fromJson(con, EvalueVo.class);
        if (vo.getDatas() == null || vo.getDatas().isEmpty()) {
            isMoreData = false;
            mLlMoreData.setVisibility(View.GONE);
        } else {
            //判断是否能整除
            clearData();
            addListData(vo.getDatas());
            mTvAnswerNumberevlue.setText("评论(" + vo.getTotal().getTotal() + ")");
            if (!mArray.isEmpty() && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mLlMoreData.setVisibility(View.GONE);
                isMoreData = true;
            } else {
                isMoreData = false;
                mLlMoreData.setVisibility(View.GONE);
            }

            adapter.notifyDataSetChanged();
        }*/
    }

    @Override
    public void GetOneEvalueError(String con) {
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));

    }

    @Override
    public void GetOneMoreEvalueSuccess(String con) {
  /*      L.e("习题评价" + con);
        Gson gson = new Gson();
        EvalueVo vo = gson.fromJson(con, EvalueVo.class);
        if (vo.getDatas() == null || vo.getDatas().isEmpty()) {
            isMoreData = false;
            mLlMoreData.setVisibility(View.GONE);
        } else {
            //判断是否能整除
            addListData(vo.getDatas());
            mTvAnswerNumberevlue.setText("评论(" + vo.getTotal().getTotal() + ")");
            if (!mArray.isEmpty() && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mLlMoreData.setVisibility(View.GONE);
                isMoreData = true;
            } else {
                isMoreData = false;
                mLlMoreData.setVisibility(View.GONE);
            }

            adapter.notifyDataSetChanged();
        }*/
    }

    @Override
    public void GetOneMoreEvalueError(String con) {
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
    }

    /**
     * 请求评价
     *
     * @param con
     */
    @Override
    public void requestEvalueNewSuccess(String con) {
        L.e("习题评价" + con);
        Gson gson = new Gson();
        mLlMoreData.setVisibility(View.GONE);
        EvalueNewVo vo = gson.fromJson(con, EvalueNewVo.class);
/*        if (vo.getDatas() == null || vo.getDatas().isEmpty()) {
        } else {*/
        //判断是否能整除
        clearData();
        addListData(vo.getDatas());
        mTvAnswerNumberevlue.setText("评论(" + vo.getTotal().getTotal() + ")");
        if (!mArray.isEmpty() && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
            isMoreData = true;
        } else {
            isMoreData = false;
        }
        if (vo.getTotal().getTotal() == mArray.size()) {
            isMoreData = false;
        }
        adapter.notifyDataSetChanged();
//        }
    }

    @Override
    public void requestEvalueNewError(String msg) {
        T_ERROR(mContext);
    }

    /**
     * 请求更多评价
     *
     * @param con
     */
    @Override
    public void requestMoreEvalueNewSuccess(String con) {
        L.e("习题评价" + con);
        Gson gson = new Gson();
        mLlMoreData.setVisibility(View.GONE);
        EvalueNewVo vo = gson.fromJson(con, EvalueNewVo.class);
/*        if (vo.getDatas() == null || vo.getDatas().isEmpty()) {
            isMoreData = false;
            mLlMoreData.setVisibility(View.GONE);
        } else {*/
        //判断是否能整除
        addListData(vo.getDatas());

        //mTvAnswerNumberevlue.setText("评论(" + vo.getTotal().getTotal() + ")");
        if (!mArray.isEmpty() && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
            isMoreData = true;
        } else {
            isMoreData = false;
        }
        if (vo.getTotal().getTotal() == mArray.size()) {
            isMoreData = false;
        }
        adapter.notifyDataSetChanged();
//        }
    }

    @Override
    public void requestMoreEvalueNewError(String msg) {
        T_ERROR(mContext);
    }

    /**
     * 设置夜间
     */
    private void setNightLayout() {
        mLlTitleBar.setBackgroundColor(getLayoutColor(R.color.night_title_bar));
        mLlRootLayout.setBackgroundColor(getLayoutColor(R.color.night_layout_bg));
        mLlLookWenDa.setBackgroundColor(getLayoutColor(R.color.night_layout_bg));
        mVBLineBar.setBackgroundColor(getLayoutColor(R.color.night_title_bar));
        setBtnColor(mBtnLookWenAnswer,R.color.night_text_color);
        setTvColor(mTvBType, R.color.night_text_color);
        setTvColor(mTvLookAnswerWen, R.color.night_text_color);
        setTvColor(mTvBMatter, R.color.night_text_color);
        setTvColor(mTvBAContent, R.color.night_text_color);
        setTvColor(mTvBBContent, R.color.night_text_color);
        setTvColor(mTvBCContent, R.color.night_text_color);
        setTvColor(mTvBDContent, R.color.night_text_color);
        setTvColor(mTvBEContent, R.color.night_text_color);
        setTvColor(mTvAnswerJiexi, R.color.night_text_color);
        setTvColor(mTvAnswerAnswer, R.color.night_text_color);
        setTvColor(mTvBRosoleContent, R.color.night_text_color);
        setTvColor(mTvLookAnswerCan, R.color.night_text_color);
        setTvColor(mTvBAnswer, R.color.night_text_color);
        setTvColor(mTvNam, R.color.night_text_color);
        setTvColor(mTvAnswerNumberevlue, R.color.night_text_color);
        mLlBSubmitEvalue.setBackgroundColor(getLayoutColor(R.color.night_layout_bg));
        mLiXia.setBackgroundColor(getLayoutColor(R.color.night_title_bar));
        setLinebg(mVLine, R.color.night_layout_bg);
        setLinebg(mVLine1, R.color.night_line_bg);
        setLinebg(mVLine2, R.color.night_line_bg);
        setLinebg(mVLine3, R.color.night_line_bg);
        setLinebg(mVLine4, R.color.night_line_bg);
        setIvBG(mIvTitleBack, R.color.night_title_bar);
        setIvBG(mIvBMore, R.color.night_title_bar);
        setIvBG(mIvTimePlay, R.color.night_title_bar);
        setIvBG(mIvBarDelect, R.color.night_title_bar);

        if (adapter != null)
            adapter.setBGLayout(mSelectViewBgZC);


    }

    private void setIvBG(ImageView iv, int id) {
        iv.setBackgroundColor(mContext.getResources().getColor(id));
    }

    /**
     * 设置护眼
     */
    private void setHuYanLayout() {
        mVBLineBar.setBackgroundColor(getLayoutColor(R.color.eye_layout_bg));
        mLlTitleBar.setBackgroundColor(getLayoutColor(R.color.eye_layout_bg));
        mVBLineBar.setBackgroundColor(getLayoutColor(R.color.eye_layout_bg));
        mLlRootLayout.setBackgroundColor(getLayoutColor(R.color.eye_layout_bg));
        mLlBSubmitEvalue.setBackgroundColor(getLayoutColor(R.color.eye_layout_bg));
        mLiXia.setBackgroundColor(getLayoutColor(R.color.eye_layout_bg));
        mLlLookWenDa.setBackgroundColor(getLayoutColor(R.color.eye_layout_bg));
        setTvColor(mTvLookAnswerWen, R.color.text_title_color);
        setBtnColor(mBtnLookWenAnswer,R.color.night_text_color);
        setTvColor(mTvLookAnswerCan, R.color.text_title_color);
        setIvBG(mIvTitleBack, R.color.eye_layout_bg);
        setIvBG(mIvBMore, R.color.eye_layout_bg);
        setIvBG(mIvTimePlay, R.color.eye_layout_bg);
        setIvBG(mIvBarDelect, R.color.eye_layout_bg);
        setLinebg(mVLine, R.color.eye_line_bg);
        setLinebg(mVLine1, R.color.eye_line_bg);
        setLinebg(mVLine2, R.color.eye_line_bg);
        setLinebg(mVLine3, R.color.eye_line_bg);
        setLinebg(mVLine4, R.color.eye_line_bg);
        if (adapter != null)
            adapter.setBGLayout(mSelectViewBgZC);
    }

    /**
     * 设置正常
     */

    private void setZhLayout() {
        mVBLineBar.setBackgroundColor(getLayoutColor(R.color.white));
        mLlTitleBar.setBackgroundColor(getLayoutColor(R.color.white));
        mLlRootLayout.setBackgroundColor(getLayoutColor(R.color.white));
        mLlLookWenDa.setBackgroundColor(getLayoutColor(R.color.white));
        mVBLineBar.setBackgroundColor(getLayoutColor(R.color.white));
        setBtnColor(mBtnLookWenAnswer,R.color.text_title_color);
        setTvColor(mTvLookAnswerWen, R.color.text_title_color);
        setTvColor(mTvLookAnswerCan, R.color.text_title_color);
        setIvBG(mIvTitleBack, R.color.white);
        setIvBG(mIvBMore, R.color.white);
        setIvBG(mIvTimePlay, R.color.white);
        setIvBG(mIvBarDelect, R.color.white);
        setTvColor(mTvBType, R.color.text_fu_color);
        setTvColor(mTvBMatter, R.color.black);
        setTvColor(mTvBAContent, R.color.black);
        setTvColor(mTvBBContent, R.color.black);
        setTvColor(mTvBCContent, R.color.black);
        setTvColor(mTvBDContent, R.color.black);
        setTvColor(mTvBEContent, R.color.black);
        setTvColor(mTvAnswerJiexi, R.color.black);
        setTvColor(mTvNam, R.color.black);
        setTvColor(mTvAnswerAnswer, R.color.black);
        setTvColor(mTvBRosoleContent, R.color.black);
        setTvColor(mTvBAnswer, R.color.black);
        setTvColor(mTvAnswerNumberevlue, R.color.black);
        mLlBSubmitEvalue.setBackgroundColor(getLayoutColor(R.color.white));
        mLiXia.setBackgroundColor(getLayoutColor(R.color.white));
        setLinebg(mVLine, R.color.input_bg);
        setLinebg(mVLine1, R.color.input_bg);
        setLinebg(mVLine2, R.color.input_bg);
        setLinebg(mVLine3, R.color.input_bg);
        setLinebg(mVLine4, R.color.input_bg);
        if (adapter != null)
            adapter.setBGLayout(mSelectViewBgZC);
    }

    private int getLayoutColor(int id) {
        return mContext.getResources().getColor(id);
    }

    private void setTvColor(TextView tv, int id) {
        tv.setTextColor(getLayoutColor(id));
    }
    private void setBtnColor(Button btn, int id) {
        btn.setTextColor(getLayoutColor(id));
    }

    private void setLinebg(View v, int id) {
        v.setBackgroundColor(getLayoutColor(id));
    }

    //顺序
    @Override
    public void QuestionIdAllSuccess(String con) {
        Log.e("yfl", "QuestionIdAllSuccess: " + con);
        mQt = DataMessageVo.ORDEREXERCISE;
        ResolveQuestionData(con);
    }

    @Override
    public void QuestionIdAllError(String con) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        T.showToast(mContext, getStringWithId(R.string.net_error));
        Log.e("yfl", "QuestionIdAllError: " + con);
    }

    //专项
    @Override
    public void SuccessSpecatilDetail(String con) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        L.e("yfl", con + "SuccessSpecatilDetail");
        mQt = DataMessageVo.TAGEXERCISE;
        mTargetid = Integer.parseInt(mTagid);
        ResolveQuestionDatas(con);
    }

    @Override
    public void ErrorSpecatilDetail(String con) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        T.showToast(mContext, getStringWithId(R.string.net_error));
        L.e("yfl", con + "ErrorSpecatilDetail");
    }

    @Override
    public void ErrOrColListSuccess(String con) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        L.w(con);
        Gson gson = new Gson();
        ErrOrColListVoNew vo = gson.fromJson(con, ErrOrColListVoNew.class);
        if (vo.getStatus().getCode() == 200) {
            List<QuestionsBean> list = new ArrayList<>();
            mUserData = vo.getData().getQuestionids();
            int size = mUserData.size();
            mRunm = vo.getData().getRnum();
            if (mRunm < size)
                showDialogContinue(mRunm);
            else {
                mRunm = (size == 0 ? 0 : size);
                showDialogContinue(mRunm);
            }
            for (int i = 0; i < size; i++) {
                QuestionsBean datasBean = new QuestionsBean();
                datasBean.setId(mUserData.get(i));
                datasBean.setCourseid(Integer.parseInt(mCouresidUser));
                list.add(datasBean);
            }
            //保存用户错问题
            ArrayList<UserLookVo> vos = new ArrayList<>();
            if (mTagType.equals(ERRTYPE)) {
                if (mCouresidUser.equals("1")) {//技术
                    for (int i = 0; i < size; i++) {
                        UserLookVo userLookVo = new UserLookVo();
                        userLookVo.setChapterId(String.valueOf(mUserData.get(i)));
                        vos.add(userLookVo);
                    }
                    DbHelperAssist.getInstance(getApplicationContext()).saveWoringSkill(vos);

                } else if (mCouresidUser.equals("2")) {//总和
                    for (int i = 0; i < size; i++) {
                        UserLookVo userLookVo = new UserLookVo();
                        userLookVo.setChapterId(String.valueOf(mUserData.get(i)));
                        vos.add(userLookVo);
                    }
                    DbHelperAssist.getInstance(getApplicationContext()).saveWoringColoct(vos);
                } else if (mCouresidUser.equals("3")) {
                    for (int i = 0; i < size; i++) {
                        UserLookVo userLookVo = new UserLookVo();
                        userLookVo.setChapterId(String.valueOf(mUserData.get(i)));
                        vos.add(userLookVo);
                    }
                    DbHelperAssist.getInstance(getApplicationContext()).saveWoringCase(vos);
//
                }
            }
            mTextDetial = list;
            setShowLayout();
            mTvBCount.setText(String.valueOf(mUserData.size()));
            bindTextNumberData();


        } else {
            T.showToast(mContext, getStringWithId(R.string.net_error));
//            T.showToast(mContext, vo.getStatus().getMessage());
        }

    }

    @Override
    public void ErrOrColListError(String con) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
        L.w(con);
    }

    /**
     * 时间结果回调
     */
    @Override
    public void TimeFinish() {
        if (mTimeUitl != null) {
            mTimeUitl.cancel();
        }

        DialogUtil dialogUtil = DialogUtil.getInstance();
        dialogUtil.showTitleDialog(mContext, "考试时间结束,请交卷", "交卷", "取消", false);
        dialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
            @Override
            public void onSureClickListener() {
                mDoTime = getTextStr(mActivityTitleText);
                if (mTimeUitl != null) {
                    long time = mTimeUitl.getNubmer();
                    if (time != 0) {
                        mDoTime = TimeTools.getCountTimeByLong(time);
                    }
                }
                isSubmit = true;
                saveBeforeDate();
                showAnswerCardResultLayout();
                //取消时间
                mTimeUitl.cancel();
                mIvTimePlay.setImageResource(R.mipmap.qbank_answer_icon_cont);
                mVBLineBar.setClickable(false);
                mActivityTitleText.setText(startTime);
                popAnswer.getPopupWindow().dismiss();
            }

            @Override
            public void onCancelClickListener() {
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
        EventBus.getDefault().removeStickyEvent(FreeDataEvent.class);
        EventBus.getDefault().unregister(mContext);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");

        EventBus.getDefault().register(mContext);

    }

    private void clearAll() {
        clearbg();
        clearClick();
        clearSeletItem();
    }

    private void clearConstant() {
        isExamHine = false;
        mFreeQuestion = null;
        mStyleCase = null;
        mRunm = 0;
        if (mTimeUitl != null) {
            mTimeUitl.cancel();
            mActivityTitleText.setText("");
            mTimeUitl = null;
        }
    }

    float xStant = 0.0f;
    float yStart = 0.0f;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                doHineInput();
                xStant = ev.getX();
                yStart = ev.getY();
                break;
        /*    case MotionEvent.ACTION_MOVE:
                float x = ev.getX();
                float v = x - xStant;
                float y = ev.getY();
                float y1 = y - yStart;
                if (y1 > 200 && y1 < -200) {
                    return false;
                } else if (v > 0 && v > 300) {//正的
                    goBefore();
                    return true;
                } else if (v < 0 && v < -300) {//负的
                    NextGo();
                    return true;
                }*/
            case MotionEvent.ACTION_UP:
                float x = ev.getX();
                float v = x - xStant;
                float y = ev.getY();
                float y1 = y - yStart;
                if (y1 > 200 && y1 < -200) {
                    return false;
                } else if (v > 0 && v > 300) {//正的
                    goBefore();
                    return true;
                } else if (v < 0 && v < -300) {//负的
                    NextGo();
                    return true;
                }
        }
        try {
            return super.dispatchTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void doHineInput() {
        if (mLlBSubmitEvalue.getVisibility() == View.VISIBLE) {
            mLiXia.setVisibility(View.VISIBLE);
            mEtBSubmit.setText(null);
            mLlBSubmitEvalue.setVisibility(View.GONE);
            Utils.hideInputMethod(mContext, mEtBSubmit);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (isSubmit) return;
        if (mTimeUitl != null && !StringUtil.isEmpty(mStyleCase) &&
                mTvRootEmpty.getVisibility() == View.GONE) {
            mIvTimePlay.setImageResource(R.mipmap.qbank_answer_icon_pau);
            DialogUtil dialogUtil = DialogUtil.getInstance();
            if (mStopdialog == null || !mStopdialog.isShowing())
                mStopdialog = dialogUtil.showStopDialog(mContext);
            dialogUtil.setStopClickListener(new DialogUtil.onStopClickListener() {
                @Override
                public void onNextClickListener() {
                    mIvTimePlay.setImageResource(R.mipmap.qbank_answer_icon_cont);
                    mTimeUitl.resume();
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mTimeUitl != null && !StringUtil.isEmpty(mStyleCase) &&
                mTvRootEmpty.getVisibility() == View.GONE) {
//            mIvTimePlay.setImageResource(R.mipmap.qbank_answer_icon_pau);
//            DialogUtil dialogUtil = DialogUtil.getInstance();
//            if (mStopdialog == null || !mStopdialog.isShowing())
//                mStopdialog = dialogUtil.showStopDialog(mContext);
            mTimeUitl.pause();
//            dialogUtil.setStopClickListener(new DialogUtil.onStopClickListener() {
//                @Override
//                public void onNextClickListener() {
//                    mIvTimePlay.setImageResource(R.mipmap.qbank_answer_icon_cont);
//                    mTimeUitl.resume();
//                }
//            });
        }

    }

    //保存章节记录
    private void saveChapterRecords() {
        if (StringUtil.isEmpty(mTypeMark) || StringUtil.isEmpty(mOid)) {
            return;
        }
        DbHelperAssist assist = DbHelperAssist.getInstance(getApplicationContext());
        List<UserLookVo> lookVos = new ArrayList<>();
        UserLookVo userLookVo = new UserLookVo();
        if (mTypeMark.equals("1")) {
            userLookVo.setChapterId(mOid);
            userLookVo.setNextId(String.valueOf(mMark));
            if (mTextDetial != null && !mTextDetial.isEmpty())
                userLookVo.setCount(String.valueOf(mTextDetial.size()));
            lookVos.add(userLookVo);
            assist.upDataSkillRecord(lookVos);

            return;
        }
        if (mTypeMark.equals("2")) {
            userLookVo.setChapterId(mOid);
            userLookVo.setNextId(String.valueOf(mMark));
            if (mTextDetial != null && !mTextDetial.isEmpty())
                userLookVo.setCount(String.valueOf(mTextDetial.size()));
            lookVos.add(userLookVo);
            assist.upDataColoctRecord(lookVos);
            return;
        }
        if (mTypeMark.equals("3")) {
            userLookVo.setChapterId(mOid);
            userLookVo.setNextId(String.valueOf(mMark));
            if (mTextDetial != null && !mTextDetial.isEmpty())
                userLookVo.setCount(String.valueOf(mTextDetial.size()));
            lookVos.add(userLookVo);
            assist.upDataCaseRecord(lookVos);
            return;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_UP) {
            if (!isExamHine) {
                DialogUtil instance = DialogUtil.getInstance();
                instance.showTitleDialog(mContext, "确定退出答题", "退出答题"
                        , "取消", true);
                instance.setTitleClickListener(new DialogUtil.onTitleClickListener() {
                    @Override
                    public void onSureClickListener() {
                        finish();
                    }

                    @Override
                    public void onCancelClickListener() {
                    }
                });

            } else {
                this.finish();

            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private void setShowEmptyLayout() {
        mLiXia.setVisibility(View.VISIBLE);
        mTvNoTextEmpty.setText(R.string.del_text);
        mIvBMore.setVisibility(View.GONE);
        mSloViewShow.setVisibility(View.GONE);

    }

    public class URLImageParser implements Html.ImageGetter {
        TextView mTextView;

        public URLImageParser(TextView textView) {
            this.mTextView = textView;
        }

        @Override
        public Drawable getDrawable(String source) {
            final URLDrawable urlDrawable = new URLDrawable();
            ImageLoader.getInstance().loadImage(source,
                    new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            urlDrawable.bitmap = loadedImage;
                            if (loadedImage != null)
                                urlDrawable.setBounds(loadedImage.getWidth(), 0, loadedImage.getWidth(), loadedImage.getHeight());
                            mTextView.invalidate();
                            mTextView.setText(mTextView.getText());
                        }
                    });
            return urlDrawable;
        }
    }


    public class URLDrawable extends BitmapDrawable {
        protected Bitmap bitmap;

        @Override
        public void draw(Canvas canvas) {
            if (bitmap != null) {
           /*     int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                //设置想要的大小
                int newWidth = 300;
                int newHeight = 300;
                //计算压缩的比率
                if (width >= newWidth || height >= newHeight) {
                    float scaleWidth = ((float) newWidth) / width;
                    float scaleHeight = ((float) newHeight) / height;
                    //获取想要缩放的matrix
                    Matrix matrix = new Matrix();
                    matrix.postScale(scaleWidth, scaleHeight);
                    Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
                    canvas.drawBitmap(bitmap1, 0, 0, getPaint());
                    return;
                }*/
                canvas.drawBitmap(bitmap, 0, 0, getPaint());
            }
        }
    }
}


package com.xuechuan.xcedu.ui.bank.newBankActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
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
import com.xuechuan.xcedu.adapter.bank.GmGridTestViewAdapter;
import com.xuechuan.xcedu.adapter.bank.GmTestExamGridViewAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.event.GmChangerColorEvent;
import com.xuechuan.xcedu.fragment.GmTestReadFragment;
import com.xuechuan.xcedu.fragment.view.GmTestInterface;
import com.xuechuan.xcedu.mvp.contract.EveyDayDetailContract;
import com.xuechuan.xcedu.mvp.contract.GmSubmitComContract;
import com.xuechuan.xcedu.mvp.model.EveyDayDetailModel;
import com.xuechuan.xcedu.mvp.model.GmSubmitComModel;
import com.xuechuan.xcedu.mvp.presenter.EveyDayDetailPresenter;
import com.xuechuan.xcedu.mvp.presenter.GmSubmitComPresenter;
import com.xuechuan.xcedu.sqlitedb.CollectSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.DoBankSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.ErrorSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.QuestionSqliteHelp;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.GmReadColorManger;
import com.xuechuan.xcedu.utils.GmTextUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.MyTimeUitl;
import com.xuechuan.xcedu.utils.OrderTimeUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.EveryDetailVo;
import com.xuechuan.xcedu.vo.ResultVo;
import com.xuechuan.xcedu.vo.SqliteVo.CollectSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.ErrorSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;
import com.xuechuan.xcedu.weight.CommonPopupWindow;
import com.xuechuan.xcedu.weight.ReaderViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: EveryDayTestAtivity
 * @Package com.xuechuan.xcedu.ui.bank.newBankActivity
 * @Description: 每日一练
 * @author: L-BackPacker
 * @date: 2019.02.14 上午 9:15
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019.02.14
 */
public class EveryDayTestAtivity extends BaseActivity implements EveyDayDetailContract.View, GmTestInterface, View.OnClickListener, GmSubmitComContract.View {

    private ImageView mIvTextBarBack;
    private ImageView mIvTextBarTimeImg;
    private TextView mActivityTitleText;
    private LinearLayout mLlTextBarTitle;
    private ImageView mIvTextBarDelect;
    private ImageView mIvTextBarMore;
    private LinearLayout mLlTextTitleBar;
    private View mVGmReadLine;
    private ReaderViewPager mReaderViewPager;
    private ReaderViewPager mReaderViewPagertwo;
    private ImageView mShadowView;
    private FrameLayout mFlContentLayoutOne;
    private TextView mTvEmpty;
    private View mVGmBarLine;
    private TextView mTvTextCollect;
    private ImageView mIvTextMenu;
    private TextView mTvTextQid;
    private TextView mTvTextAllqid;
    private TextView mTvTextShare;
    private LinearLayout mLiTextNavbar;
    private LinearLayout mLlNewtextBar;
    private EveyDayDetailPresenter mPresenter;
    private Context mContext;
    private static String PARAMT_KEY = "com.xuechuan.xcedu.ui.bankEveryDayTestAtivity.paramt_key";
    private static String PARAMT1_KEY = "com.xuechuan.xcedu.ui.bankEveryDayTestAtivity.paramt_key1";
    private int mQuesiton_index;
    private AlertDialog mAlertDialog;
    private String mCourseid;
    private List<QuestionSqliteVo> mDataLists;
    private GmTestReadFragment mReadFragment;
    private QuestionSqliteHelp mQuestionHelp;
    private DialogUtil mDialogUtil;
    private DoBankSqliteHelp mDoBankHelp;
    private GmTextUtil mTextUtil;
    private CommonPopupWindow mPopAnswer;
    private LinearLayout mLlEverydataRoot;
    private GmReadColorManger mColorManger;
    private MyTimeUitl mTimeUitl;
    private OrderTimeUtil mOrderTimeUtil;
    /**
     * 判断用户是否提交
     */
    private boolean mSubmitAble = false;
    /**
     * 判断用户是否提交服务器
     */
    private boolean mSubmitExam = false;
    private CommonPopupWindow mPopResult;
    private String mUserDoTime;
    private ErrorSqlteHelp mErrorSqlteHelp;
    private CollectSqliteHelp mCollectSqliteHelp;
    private ImageView mIvGmbanJianpan;
    private EditText mEtGmSubmit;
    private ImageView mIvGmSubmitSend;
    private LinearLayout mLlGmSubmitEvalue;
    private CheckBox mChbGmCollect;
    private GmSubmitComPresenter mSubmitPresenter;
    private QuestionSqliteVo mQuestionSqliteVo;
    private AlertDialog mShowDialog;

    public static Intent start_Intent(Context context, int id, String courseid) {
        Intent intent = new Intent(context, EveryDayTestAtivity.class);
        intent.putExtra(PARAMT_KEY, id);
        intent.putExtra(PARAMT1_KEY, courseid);
        return intent;
    }

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_every_day_test_ativity);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_every_day_test_ativity);
        if (getIntent() != null) {
            mQuesiton_index = getIntent().getIntExtra(PARAMT_KEY, 0);
            mCourseid = getIntent().getStringExtra(PARAMT1_KEY);
        }
        initView();
        initUtils();
        initData();

    }

    private void initEvent() {
        mChbGmCollect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!mChbGmCollect.isPressed()) {
                    return;
                }
                if (mQuestionSqliteVo != null) {
                    CollectSqliteVo vo = new CollectSqliteVo();
                    vo.setCollectable(isChecked ? 1 : 0);
                    vo.setChapterid(mQuestionSqliteVo.getChapter_id());
                    vo.setCourseid(Integer.parseInt(mCourseid));
                    vo.setQuestion_id(mQuestionSqliteVo.getQuestion_id());
                    vo.setQuestiontype(mQuestionSqliteVo.getQuestiontype());
                    if (isChecked) {
                        mCollectSqliteHelp.addCoolectItem(vo);
                        T.showToast(mContext, "收藏成功");
                    } else {
                        T.showToast(mContext, "取消收藏");
                        mCollectSqliteHelp.deleteItem(Integer.parseInt(mCourseid),
                                mQuestionSqliteVo.getChapter_id(), mQuestionSqliteVo.getQuestion_id());
                    }
                } else {
                    T.showToast(mContext, "暂无题收藏");
                }
            }
        });
    }

    private void initUtils() {
        mTextUtil = GmTextUtil.get_Instance(mContext);
        //做题临时表
        mDoBankHelp = DoBankSqliteHelp.get_Instance(mContext);
        mDialogUtil = DialogUtil.getInstance();
        //用户题干新
        mQuestionHelp = QuestionSqliteHelp.get_Instance(mContext);
        //错题
        mErrorSqlteHelp = ErrorSqlteHelp.getInstance(mContext);
        //收藏表
        mCollectSqliteHelp = CollectSqliteHelp.get_Instance(mContext);
        //时间控制器
        mTimeUitl = MyTimeUitl.getInstance(mContext);

        //提交评价
        mSubmitPresenter = new GmSubmitComPresenter();
        mSubmitPresenter.initModelView(new GmSubmitComModel(), this);
    }

    private void initData() {
        mPresenter = new EveyDayDetailPresenter();
        mPresenter.initModelView(new EveyDayDetailModel(), this);
        mPresenter.requestionDayDetail(mContext, mQuesiton_index);
        mAlertDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));

    }

    private void initViewPagerData(int index) {
        mTvTextAllqid.setText(String.valueOf(mDataLists.size()));
        mTvTextQid.setText(String.valueOf(++index));
    }

    private void initView() {
        mContext = this;
        mIvTextBarBack = (ImageView) findViewById(R.id.iv_text_bar_back);
        mIvTextBarBack.setOnClickListener(this);
        mIvTextBarTimeImg = (ImageView) findViewById(R.id.iv_text_bar_time_img);
        mActivityTitleText = (TextView) findViewById(R.id.activity_title_text);
        mLlTextBarTitle = (LinearLayout) findViewById(R.id.ll_text_bar_title);
        mIvTextBarDelect = (ImageView) findViewById(R.id.iv_text_bar_delect);
        mIvTextBarMore = (ImageView) findViewById(R.id.iv_text_bar_more);
        mIvTextBarMore.setOnClickListener(this);
        mLlTextTitleBar = (LinearLayout) findViewById(R.id.ll_text_title_bar);
        mVGmReadLine = (View) findViewById(R.id.v_gm_read_line);
        mReaderViewPager = (ReaderViewPager) findViewById(R.id.readerViewPager);
        mReaderViewPagertwo = (ReaderViewPager) findViewById(R.id.readerViewPagertwo);
        mShadowView = (ImageView) findViewById(R.id.shadowView);
        mFlContentLayoutOne = (FrameLayout) findViewById(R.id.fl_content_layout_one);
        mTvEmpty = (TextView) findViewById(R.id.tv_empty);
        mVGmBarLine = (View) findViewById(R.id.v_gm_bar_line);
        mTvTextCollect = (TextView) findViewById(R.id.tv_text_collect);
        mTvTextCollect.setOnClickListener(this);
        mIvTextMenu = (ImageView) findViewById(R.id.iv_text_menu);
        mIvTextMenu.setOnClickListener(this);
        mTvTextQid = (TextView) findViewById(R.id.tv_text_qid);
        mTvTextAllqid = (TextView) findViewById(R.id.tv_text_allqid);
        mTvTextShare = (TextView) findViewById(R.id.tv_text_share);
        mTvTextShare.setOnClickListener(this);
        mLiTextNavbar = (LinearLayout) findViewById(R.id.li_text_navbar);
        mLlNewtextBar = (LinearLayout) findViewById(R.id.ll_newtext_bar);
        mLlEverydataRoot = (LinearLayout) findViewById(R.id.ll_everydata_root);
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
    }

    @Override
    public void DayDetailSuccess(String success) {
        dismissDialog(mAlertDialog);
        EveryDetailVo vo = Utils.getGosnT(success, EveryDetailVo.class);
        if (vo.getStatus().getCode() == 200) {
            ArrayList<QuestionSqliteVo> copyData = getCopyData(vo.getDatas());
            if (copyData == null || copyData.isEmpty()) {
                mLlNewtextBar.setVisibility(View.INVISIBLE);
                setOnClickAble(false);
            } else {
                mLlNewtextBar.setVisibility(View.VISIBLE);
                initDataTime();
            }
            bindViewData(copyData);
            initEvent();
        } else {
            T_ERROR(mContext);
        }
    }

    private void setOnClickAble(boolean able) {
        mIvTextBarMore.setClickable(able);
        mChbGmCollect.setChecked(able);
        mIvTextMenu.setClickable(able);
        mTvTextShare.setClickable(able);
    }

    private void initDataTime() {
        mOrderTimeUtil = OrderTimeUtil.getInstance(mContext);
        mOrderTimeUtil.start(mActivityTitleText);
    }

    private ArrayList<QuestionSqliteVo> getCopyData(List<EveryDetailVo.DatasBean> list) {
        ArrayList<QuestionSqliteVo> listsvo = new ArrayList<>();
        if (list == null || list.isEmpty()) return listsvo;
        for (int i = 0; i < list.size(); i++) {
            EveryDetailVo.DatasBean bean = list.get(i);
            QuestionSqliteVo vo = copyData(bean);
            listsvo.add(vo);
        }
        return listsvo;
    }

    private QuestionSqliteVo copyData(EveryDetailVo.DatasBean bean) {
        QuestionSqliteVo vo = new QuestionSqliteVo();
        vo.setExplainedStr(bean.getExplainstr());
        vo.setKeywordStr(bean.getKeywordsstr());
        vo.setQuestionStr(bean.getQuestionstr());
        vo.setRight_rate(bean.getRight_rate());
        vo.setChapter_id(bean.getChapter_id());
        vo.setChoice_answer(bean.getChoice_answer());
        vo.setCourseid(bean.getCourseid());
        vo.setDifficulty(bean.getDifficulty());
        vo.setQuestion_id(bean.getId());
        vo.setOption_a(bean.getOption_a());
        vo.setOption_b(bean.getOption_b());
        vo.setOption_c(bean.getOption_c());
        vo.setOption_d(bean.getOption_d());
        vo.setOption_e(bean.getOption_e());
        vo.setParent_id(bean.getParent_id());
        vo.setQuestion_mold(bean.getQuestion_mold());
        vo.setQuestionimg(bean.getQuestionimg());
        vo.setQuestiontype(bean.getQuestiontype());
        vo.setScore(bean.getScore());
        return vo;
    }

    /**
     * 绑定数据
     *
     * @param datas
     */
    private void bindViewData(List<QuestionSqliteVo> datas) {
        this.mDataLists = datas;
        initViewPagerData(0);
        initReadViewPager(datas);
    }

    private int prePosition2;
    private int curPosition2;

    private void initReadViewPager(List<QuestionSqliteVo> datas) {
        mReaderViewPager.setAdapter(new GmFragmentAdpater(getSupportFragmentManager(), mContext, datas, mCourseid));
        mReaderViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mShadowView.setTranslationX(mReaderViewPager.getWidth() - positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                curPosition2 = position;
                prePosition2 = curPosition2;
                initViewPagerData(position);
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
                        if (!mIsScrolled && mDataLists != null) {
                            if (prePosition2 == (mDataLists.size() - 1)) {
                                T.showToast(mContext, getStringWithId(R.string.last_question));
                            }
                        }
                        mIsScrolled = true;
                        break;
                }
            }
        });
        mReaderViewPager.setOffscreenPageLimit(1);
    }

    @Override
    public void saveUserDoLog(DoBankSqliteVo vo) {
        if (vo == null) return;
        mDoBankHelp.addDoBankItem(vo);
    }

    @Override
    public DoBankSqliteVo getUserDoLog(int quesiton_id) {
        if (mDoBankHelp == null) return null;
        DoBankSqliteVo vo = mDoBankHelp.queryWQid(quesiton_id);
        return vo;
    }

    @Override
    public void deleteUserDolog(int quesiton_id) {

    }

    @Override
    public void doRightGo() {

    }

    @Override
    public DoBankSqliteVo queryUserData(int qustion_id) {
        if (mDoBankHelp == null) return null;
        return mDoBankHelp.queryWQid(qustion_id);
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
    public QuestionSqliteVo getQuestionVo(int id) {
        return null;
    }

    @Override
    public void doSaveErrorLog(ErrorSqliteVo vo) {
        if (vo == null) return;
        mErrorSqlteHelp.addErrorItem(vo);
    }

    @Override
    public boolean getSubmitAble() {
        return this.mSubmitAble;
    }

    @Override
    public void changerCollect(CollectSqliteVo sqliteVo) {
        if (sqliteVo == null) return;
        initCollect(sqliteVo);
    }

    @Override
    public void changerBarView(boolean show) {
        setShowBarView(show);
    }

    private void initCollect(CollectSqliteVo vo) {
        CollectSqliteVo sqliteVo = mCollectSqliteHelp.queryCollectVo(Integer.parseInt(mCourseid),
                vo.getChapterid(), vo.getQuestion_id());
        if (sqliteVo != null) {
            mChbGmCollect.setChecked(sqliteVo.getCollectable() == 1);
        } else {
            mChbGmCollect.setChecked(false);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_text_menu://菜单
                if (mSubmitAble) {
                    showResultPopLayout();
                } else {
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
            case R.id.iv_gm_submit_send://评价
                String trim = mEtGmSubmit.getText().toString().trim();
                if (StringUtil.isEmpty(trim)) {
                    T.showToast(mContext, getStringWithId(R.string.input_content));
                    return;
                }
                mShowDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_loading));
                mSubmitPresenter.submiteCommont(mContext,
                        String.valueOf(mQuestionSqliteVo.getParent_id()), trim, "", DataMessageVo.QUESTION);
                Utils.hideInputMethod(mContext, mEtGmSubmit);
                break;
            case R.id.iv_gmban_jianpan://键盘
                setShowBarView(false);
                break;
            case R.id.tv_text_share:
                EventBus.getDefault().postSticky(new GmChangerColorEvent(prePosition2, 4, false));
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
            Utils.showSoftInputFromWindow(EveryDayTestAtivity.this, mEtGmSubmit);
        } else {
            mEtGmSubmit.setText(null);
            Utils.hideInputMethod(mContext, mEtGmSubmit);

        }
    }

    public class GmFragmentAdpater extends FragmentPagerAdapter {

        private final String mCourseid;
        private Context mContext;
        private List<?> mListDatas;


        public GmFragmentAdpater(FragmentManager fm, Context mContext, List<?> mListDatas, String coursid) {
            super(fm);
            this.mListDatas = mListDatas;
            this.mContext = mContext;
            this.mCourseid = coursid;
        }

        @Override
        public Fragment getItem(int position) {
            if (mListDatas.size() == 0) {
                mReadFragment = GmTestReadFragment.newInstance(null, position, mCourseid);
            } else {
                mQuestionSqliteVo = (QuestionSqliteVo) mListDatas.get(position);

                mReadFragment = GmTestReadFragment.newInstance(mQuestionSqliteVo, position, mCourseid);
            }
            return mReadFragment;
        }

        @Override
        public int getCount() {
            if (mListDatas.size() == 0) {
                return 1;
            } else
                return mListDatas.size();
        }
    }

    @Override
    public void DayDetailError(String msg) {
        dismissDialog(mAlertDialog);
        T_ERROR(mContext);
    }

    /**
     * 提交用户做题记录
     *
     * @param success
     */
    @Override
    public void SubmitUserDoSuccess(String success) {
        L.e("===", "SubmitUserDoSuccess: " + success);
    }

    @Override
    public void SubmitUserDoError(String success) {
        T_ERROR(mContext);
    }

    private List<DoBankSqliteVo> findAllDoDatas() {
        List<DoBankSqliteVo> list = new ArrayList<>();
        List<DoBankSqliteVo> doBankSqliteVos = mDoBankHelp.finDAllUserDoText();
        if (doBankSqliteVos == null || doBankSqliteVos.isEmpty()) return list;
        for (int i = 0; i < mDataLists.size(); i++) {
            QuestionSqliteVo vo = mDataLists.get(i);
            for (int k = 0; k < doBankSqliteVos.size(); k++) {
                DoBankSqliteVo sqliteVo = doBankSqliteVos.get(k);
                if (vo.getQuestion_id() == sqliteVo.getQuestion_id()) {
                    list.add(sqliteVo);
                }
            }

        }
        return list;
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
                mBtnSubmit.setVisibility(View.VISIBLE);
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
                    mBtnSubmit.setBackgroundResource(mColorManger.getmSubmitColorBg());
                }
                mTvPopNew.setText(String.valueOf(curPosition2 + 1));
                mTvPopCount.setText(String.valueOf(mDataLists.size()));
                //获取用户以做过的题目
                final List<DoBankSqliteVo> list = findAllDoDatas();
                bindGridViewAdapter(list);
                mBtnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doUserSumbitDialog(mDataLists.size() == list.size());
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
                        mTextUtil.setBackgroundAlpha(1f, EveryDayTestAtivity.this);
                    }
                });
            }

            private void bindGridViewAdapter(List<DoBankSqliteVo> list) {
                GmTestExamGridViewAdapter adapter = new GmTestExamGridViewAdapter(mContext, mDataLists);
                adapter.doEventListDatas(list);
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
        mPopAnswer.showAtLocation(mLlEverydataRoot, Gravity.BOTTOM, 0, 0);
        mTextUtil.setBackgroundAlpha(0.5f, EveryDayTestAtivity.this);
    }

    /**
     * 交卷对话框
     *
     * @param isOver
     */
    public void doUserSumbitDialog(boolean isOver) {
        if (mPopAnswer != null && mPopAnswer.getPopupWindow().isShowing()) {
            mPopAnswer.getPopupWindow().dismiss();
        }
        mDialogUtil.setIsDoOver(isOver);
        mDialogUtil.showSubmitDialog(mContext);
        mDialogUtil.setSubmitClickListener(new DialogUtil.onSubmitClickListener() {
            @Override
            public void onCancelClickListener() {
                mSubmitAble = false;
            }

            @Override
            public void oSubmitClickListener() {
                mOrderTimeUtil.pause();
                //获取用户使用时间
                mUserDoTime = mActivityTitleText.getText().toString().trim();
                mSubmitAble = true;
                //显示答案界面
                showResultPopLayout();
            }
        });
    }

    //无小题答题卡布局
    public void showResultPopLayout() {
        mPopResult = new CommonPopupWindow(this, R.layout.pop_result, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) {
            private LinearLayout mLlGmmockexamLayout;
            private View mViewGmMockexamLine;
            private LinearLayout mLlMockexamBar;
            private Button mBtnMockexanJiexi;
            private Button mBtnMockexanAgain;
            private GridView mGvMockexamResult;
            private TextView mTvMockexamRightContent;
            private TextView mTvMockexamRightTitle;
            private TextView mTvMockexamDotimeTitleContent;
            private TextView mTvMockexamDotimeTitle;
            private TextView mTvResultNumber;
            private ImageView mIvBackDayTest;

            @Override
            protected void initView() {
                View view = getContentView();

                mLlGmmockexamLayout = (LinearLayout) view.findViewById(R.id.ll_gmmockexamdialog_layout);
                mViewGmMockexamLine = (View) view.findViewById(R.id.view_gm_mockexam_line);
                //下标题
                mLlMockexamBar = (LinearLayout) view.findViewById(R.id.ll_mockexam_bar);
                mBtnMockexanJiexi = (Button) view.findViewById(R.id.btn_mockexan_jiexi);
                mBtnMockexanAgain = (Button) view.findViewById(R.id.btn_mockexan_again);
                mGvMockexamResult = (GridView) view.findViewById(R.id.gv_mockexam_result_bag);
                mTvMockexamRightContent = (TextView) view.findViewById(R.id.tv_mockexam_right_content);
                mTvMockexamRightTitle = (TextView) view.findViewById(R.id.tv_mockexam_right_title);
                mTvMockexamDotimeTitle = (TextView) view.findViewById(R.id.tv_mockexam_dotime_title);
                mTvMockexamDotimeTitleContent = (TextView) view.findViewById(R.id.tv_mockexam_dotime_title_content);
                mTvResultNumber = (TextView) view.findViewById(R.id.tv_result_number);
                mIvBackDayTest = (ImageView) view.findViewById(R.id.iv_back_day_test);

            }

            @Override
            protected void initEvent() {
                GmGridTestViewAdapter adapter = new GmGridTestViewAdapter(mContext, mDataLists);
                mGvMockexamResult.setAdapter(adapter);
                adapter.doEventColor(mColorManger);
                List<DoBankSqliteVo> list = findAllDoDatas();
                adapter.doEventListDatas(list);
                setBtnListener(mBtnMockexanAgain, 1);
                setBtnListener(mBtnMockexanJiexi, 2);
                bindData(list);
                mIvBackDayTest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mPopResult != null && mPopResult.getPopupWindow().isShowing()) {
                            EventBus.getDefault().postSticky(new GmChangerColorEvent(0, 3, false));
                            mPopResult.getPopupWindow().dismiss();
                        }
                    }
                });
            }

            private void bindData(List<DoBankSqliteVo> list) {
                //正确率
                String accuracy = mTextUtil.getRihgtAccuracy(list, mDataLists.size());
                //提交用户做题记录
                if (!mSubmitExam) {
                    mPresenter.submitUserDayTest(mContext, mQuesiton_index, mOrderTimeUtil.getTimeNubmer(), accuracy);
                    mSubmitExam = true;
                }
                //取消时间
                mOrderTimeUtil.cancel();

                mTvMockexamRightContent.setText(accuracy);
                mTvMockexamDotimeTitleContent.setText(mUserDoTime);
                //分说
                String grade = mTextUtil.getUserGrade(list);
                mTvResultNumber.setText(grade);
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
                                mSubmitAble = false;
                                //图标
                                //重置时间
                                mOrderTimeUtil.restart();
                                //删除做题记录
                                mDoBankHelp.delelteTable();
                                curPosition2 = 0;
                                //跳转
                                mReaderViewPager.setCurrentItem(0);
                                mSubmitExam = false;
                                EventBus.getDefault().postSticky(new GmChangerColorEvent(0, 3, false));
                                break;
                            case 2://查看解析
                                mSubmitAble = true;
                                //跳转
                                mReaderViewPager.setCurrentItem(0);
                                EventBus.getDefault().postSticky(new GmChangerColorEvent(0, 3, false));
                                break;

                        }
                    }
                });
            }

            private void doDialogColor() {
                if (mColorManger == null) {
                    return;
                }
                mTvResultNumber.setTextColor(mColorManger.getmTextTitleColor());
                mTvMockexamDotimeTitleContent.setTextColor(mColorManger.getmTextTitleColor());
                mTvMockexamDotimeTitle.setTextColor(mColorManger.getmTextFuColor());
                mTvMockexamRightTitle.setTextColor(mColorManger.getmTextFuColor());
                mTvMockexamRightContent.setTextColor(mColorManger.getmTextTitleColor());
                mLlMockexamBar.setBackgroundColor(mColorManger.getmLayoutBgColor());
                mBtnMockexanAgain.setTextColor(mColorManger.getmTextTitleColor());
                mBtnMockexanJiexi.setTextColor(mColorManger.getmTextTitleColor());
                mViewGmMockexamLine.setBackgroundColor(mColorManger.getmCutLineColor());
                mLlGmmockexamLayout.setBackgroundColor(mColorManger.getmLayoutBgColor());
            }

            @Override
            protected void initWindow() {
                super.initWindow();
                PopupWindow instance = getPopupWindow();
                instance.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mTextUtil.setBackgroundAlpha(1f, EveryDayTestAtivity.this);
                    }
                });
            }
        };
        mPopResult.showAtLocation(mLlEverydataRoot, Gravity.BOTTOM, 0, 0);
        mTextUtil.setBackgroundAlpha(0.5f, EveryDayTestAtivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDoBankHelp != null) {
            mDoBankHelp.delelteTable();
        }
        if (mOrderTimeUtil != null) {
            mOrderTimeUtil.cancel();
        }
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

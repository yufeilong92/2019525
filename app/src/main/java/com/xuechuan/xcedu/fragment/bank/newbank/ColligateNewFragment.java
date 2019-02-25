package com.xuechuan.xcedu.fragment.bank.newbank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.event.DayHomeEvent;
import com.xuechuan.xcedu.sqlitedb.CollectSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.DoMockBankSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.ErrorSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.QuestionSqliteHelp;
import com.xuechuan.xcedu.ui.bank.newbank.AtricleListNewActivity;
import com.xuechuan.xcedu.ui.bank.newbank.EveryDayActivity;
import com.xuechuan.xcedu.ui.bank.newbank.GmFreeQuestionActivity;
import com.xuechuan.xcedu.ui.bank.newbank.GmMockTestActivity;
import com.xuechuan.xcedu.ui.bank.newbank.GmSpecialListActivity;
import com.xuechuan.xcedu.ui.bank.newbank.MyCollectTextActivity;
import com.xuechuan.xcedu.ui.bank.newbank.MyErrorTextActivity;
import com.xuechuan.xcedu.ui.bank.newbank.MyNoteListActivity;
import com.xuechuan.xcedu.ui.bank.newBankActivity.OrderTestActivity;
import com.xuechuan.xcedu.ui.home.SpecasListActivity;
import com.xuechuan.xcedu.utils.ArithUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.DayHomeBeanVo;
import com.xuechuan.xcedu.vo.NoteListVo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: ColligateNewFragment
 * @Package com.xuechuan.xcedu.fragment.bank
 * @Description: 最新综合能力界面
 * @author: L-BackPacker
 * @date: 2018.12.04 下午 4:16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.12.04
 */

public class ColligateNewFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TextView mTvBanknewDoNumber;
    private TextView mTvBanknewAllNumber;
    private TextView mTvBanknewRightNumber;
    private LinearLayout mLlBanknewFree;
    private LinearLayout mLlBanknewSpecial;
    private LinearLayout mLlBanknewOrder;
    private TextView mTvBanknewErrorList;
    private TextView mTvBanknewFavoriteList;
    private TextView mTvBanknewNoteList;
    private LinearLayout mLlBanknewPractice;
    private LinearLayout mLlBanknewExam;
    private LinearLayout mLlBanknewError;
    private LinearLayout mLlBanknewFavaoite;
    private LinearLayout mLlBanknewNote;
    private Context mContext;
    private TextView mTvDayExecistTitle;
    private LinearLayout mLiGmDayExecrise;
    private DayHomeBeanVo mData;
    private ErrorSqlteHelp mErrorSqlteHelp;
    private CollectSqliteHelp mCollectHelp;
    private QuestionSqliteHelp mSqliteHelp;
    private DoMockBankSqliteHelp mDoMockHelp;
    private int mCollectNum;
    private int mErrorNum;

    public static ColligateNewFragment newInstance(String param1, String param2) {
        ColligateNewFragment fragment = new ColligateNewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /*
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_colligate_new, container, false);
            initView(view);
            return view;
        }
    */
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int initInflateView() {
        return R.layout.fragment_colligate_new;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initUtils();
        bindViewData();
    }

    @Override
    public void onResume() {
        super.onResume();
        bindViewData();
    }

    private void bindViewData() {
        int all = mSqliteHelp.queryCountWithCourseid(DataMessageVo.SPECIAL_TWO);
        mTvBanknewAllNumber.setText(String.valueOf(all));
        int donumber = mDoMockHelp.queryCountWithCourseid(DataMessageVo.SPECIAL_TWO);
        mTvBanknewDoNumber.setText(String.valueOf(donumber));
        //收藏数据
        mCollectNum = mCollectHelp.queryCountWithCourseid(DataMessageVo.SPECIAL_TWO);
        mTvBanknewFavoriteList.setText(String.valueOf(mCollectNum));
        //错题数

        mErrorNum = mErrorSqlteHelp.queryCountWithCourseid(DataMessageVo.SPECIAL_TWO);
        mTvBanknewErrorList.setText(String.valueOf(mErrorNum));

        int mRight = mDoMockHelp.queryRightCountWithCourseId(DataMessageVo.CHAPTER_ONE);
        String number = ArithUtil.divNumber(mRight, donumber, 2);
        String concat = number.concat("%");
        mTvBanknewRightNumber.setText(String.valueOf(concat));
    }
    private void initUtils() {
        //本地题库
        //错题表
        mErrorSqlteHelp = ErrorSqlteHelp.getInstance(mContext);
        //收藏表
        mCollectHelp = CollectSqliteHelp.get_Instance(mContext);
        //本地题库
        mSqliteHelp = QuestionSqliteHelp.get_Instance(mContext);

        //本地数据库
        mDoMockHelp = DoMockBankSqliteHelp.get_Instance(mContext);

    }

    private void initView(View view) {
        mContext = getActivity();
        mTvBanknewDoNumber = (TextView) view.findViewById(R.id.tv_banknew_do_number);
        mTvBanknewAllNumber = (TextView) view.findViewById(R.id.tv_banknew_all_number);
        mTvBanknewRightNumber = (TextView) view.findViewById(R.id.tv_banknew_right_number);
        mLlBanknewFree = (LinearLayout) view.findViewById(R.id.ll_banknew_free);
        mLlBanknewFree.setOnClickListener(this);
        mLlBanknewSpecial = (LinearLayout) view.findViewById(R.id.ll_banknew_special);
        mLlBanknewSpecial.setOnClickListener(this);
        mLlBanknewOrder = (LinearLayout) view.findViewById(R.id.ll_banknew_order);
        mLlBanknewOrder.setOnClickListener(this);
        mTvBanknewErrorList = (TextView) view.findViewById(R.id.tv_banknew_error_list);
        mTvBanknewFavoriteList = (TextView) view.findViewById(R.id.tv_banknew_favorite_list);
        mTvBanknewNoteList = (TextView) view.findViewById(R.id.tv_banknew_note_list);
        mLlBanknewPractice = (LinearLayout) view.findViewById(R.id.ll_banknew_practice);
        mLlBanknewPractice.setOnClickListener(this);
        mLlBanknewExam = (LinearLayout) view.findViewById(R.id.ll_banknew_exam);
        mLlBanknewExam.setOnClickListener(this);
        mLlBanknewError = (LinearLayout) view.findViewById(R.id.ll_banknew_error);
        mLlBanknewError.setOnClickListener(this);
        mLlBanknewFavaoite = (LinearLayout) view.findViewById(R.id.ll_banknew_favaoite);
        mLlBanknewFavaoite.setOnClickListener(this);
        mLlBanknewNote = (LinearLayout) view.findViewById(R.id.ll_banknew_note);
        mLlBanknewNote.setOnClickListener(this);
        mTvDayExecistTitle = (TextView) view.findViewById(R.id.tv_day_execist_title);
        mTvDayExecistTitle.setOnClickListener(this);
        mLiGmDayExecrise = (LinearLayout) view.findViewById(R.id.li_gm_day_execrise);
        mLiGmDayExecrise.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_banknew_practice://章节练习
                Intent intent = AtricleListNewActivity.newInstance(mContext, DataMessageVo.COURESID_SYNTHESIZE, "1");
                intent.putExtra(AtricleListNewActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(R.string.Chapter_practice));
                startActivity(intent);
                break;
            case R.id.ll_banknew_exam://模拟考试
                Intent intent3 = GmMockTestActivity.newInstance(mContext, DataMessageVo.COURESID_SYNTHESIZE);
                intent3.putExtra(GmMockTestActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(R.string.mokeexam));
                startActivity(intent3);
                break;
            case R.id.ll_banknew_free://自由组卷
                Intent intent6 = GmFreeQuestionActivity.newInstance(mContext, DataMessageVo.COURESID_SYNTHESIZE);
                intent6.putExtra(GmFreeQuestionActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(R.string.skill_free_composition));
                mContext.startActivity(intent6);
                break;
            case R.id.ll_banknew_special://专项练习
                Intent intent4 = GmSpecialListActivity.newInstance(mContext, DataMessageVo.COURESID_SYNTHESIZE);
                intent4.putExtra(SpecasListActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(R.string.special));
                startActivity(intent4);
                break;
            case R.id.ll_banknew_order://顺序练习
                Intent order = OrderTestActivity.start_Intent(mContext, DataMessageVo.COURESID_SYNTHESIZE);
                order.putExtra(OrderTestActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(R.string.sequential_exercise));
                mContext.startActivity(order);
                break;
            case R.id.ll_banknew_error://错题集合
                Intent newInstance = MyErrorTextActivity.newInstance(mContext, DataMessageVo.COURESID_SYNTHESIZE
                        , String.valueOf(mErrorNum));
                newInstance.putExtra(MyErrorTextActivity.CSTR_EXTRA_TITLE_STR,getStrWithId(R.string.mywrongquestion));
                startActivity(newInstance);
                break;
            case R.id.ll_banknew_favaoite://收藏夹
                Intent newInstance1 = MyCollectTextActivity.newInstance(mContext, DataMessageVo.COURESID_SYNTHESIZE,
                        String.valueOf(mCollectNum));
                newInstance1.putExtra(MyCollectTextActivity.CSTR_EXTRA_TITLE_STR,getStrWithId(R.string.bank_my_collect));
                startActivity(newInstance1);
                break;
            case R.id.ll_banknew_note://笔记
                Intent intent1 = MyNoteListActivity.start_Intent(mContext, DataMessageVo.COURESID_SYNTHESIZE, "");
                intent1.putExtra(MyNoteListActivity.CSTR_EXTRA_TITLE_STR,getStrWithId(R.string.note_list));
                startActivity(intent1);
                break;
            case R.id.li_gm_day_execrise://每日一练
                if (mData == null) {
                    T.showToast(mContext, "暂无内容");
                    return;
                }
                Intent every = EveryDayActivity.start_Intent(mContext, mData, DataMessageVo.COURESID_SYNTHESIZE);
                startActivity(every);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void DayHomeEvent(DayHomeEvent event) {

        switch(event.getType())
        {
            case 1:
                List<DayHomeBeanVo> data = event.getData();
                for (int i = 0; i < data.size(); i++) {
                    DayHomeBeanVo dayHomeBeanVo = data.get(i);
                    if (dayHomeBeanVo.getCourseid() == DataMessageVo.SPECIAL_TWO) {
                        initDayData(dayHomeBeanVo);
                    }
                }
                break;
            case 2:
                List<NoteListVo.DatasBean> notes = event.getNotes();
                for (int i = 0; i < notes.size(); i++) {
                    NoteListVo.DatasBean bean = notes.get(i);
                    if (bean.getCourseid()== DataMessageVo.SPECIAL_TWO) {
                        initNoteCount(bean.getCount());
                    }
                }
                break;
            case 3://刷新数据
                bindViewData();
                break;
            default:

        }
    }
    private void initNoteCount(int count) {
        mTvBanknewNoteList.setText(String.valueOf(count));
    }

    private void initDayData(DayHomeBeanVo vo) {
        this.mData = vo;
        mTvDayExecistTitle.setText(vo.getKeyword());
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            bindViewData();
        }
    }
}

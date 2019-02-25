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
import com.xuechuan.xcedu.ui.bank.newBankActivity.CaseExamtActivity;
import com.xuechuan.xcedu.ui.bank.newBankActivity.CaseOrderTestActivity;
import com.xuechuan.xcedu.ui.bank.newBankActivity.OrderTestActivity;
import com.xuechuan.xcedu.ui.bank.newbank.GmMockTestActivity;
import com.xuechuan.xcedu.ui.bank.newbank.GmSpecialListActivity;
import com.xuechuan.xcedu.ui.bank.newbank.MyCollectTextActivity;
import com.xuechuan.xcedu.ui.home.SpecasListActivity;
import com.xuechuan.xcedu.vo.DayHomeBeanVo;
import com.xuechuan.xcedu.vo.NoteListVo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: CaseNewFragment
 * @Package com.xuechuan.xcedu.fragment.bank
 * @Description: 最新案例界面
 * @author: L-BackPacker
 * @date: 2018.12.04 下午 4:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.12.04
 */
public class CaseNewFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Context mContext;
    private DayHomeBeanVo mData;
    private ErrorSqlteHelp mErrorSqlteHelp;
    private CollectSqliteHelp mCollectHelp;
    private QuestionSqliteHelp mSqliteHelp;
    private DoMockBankSqliteHelp mDoMockHelp;
    private int mCollectNum;
    private int mErrorNum;
    private TextView mTvDoCaseNumber;
    private TextView mTvAllCaseNumber;
    private TextView mTvDoCaseAllnumber;
    private LinearLayout mLlCaseSpecialLayout;
    private LinearLayout mLlCaseExamLayout;
    private TextView mTvBanknewFavoriteList;
    private LinearLayout mLlBanknewFavaoite;

    public static CaseNewFragment newInstance(String param1, String param2) {
        CaseNewFragment fragment = new CaseNewFragment();
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


    /*    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_case_new, container, false);
            initView(view);
            return view;
        }*/
    @Override
    protected int initInflateView() {
        return R.layout.fragment_case_new;
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
        int all = mSqliteHelp.queryCountWithCourseid(DataMessageVo.ORDER_THREE);
        mTvAllCaseNumber.setText(String.valueOf(all));
        int donumber = mDoMockHelp.queryCountWithCourseid(DataMessageVo.ORDER_THREE);
        mTvDoCaseNumber.setText(String.valueOf(donumber));
        //收藏数据
        mCollectNum = mCollectHelp.queryCountWithCourseid(DataMessageVo.ORDER_THREE);
        mTvBanknewFavoriteList.setText(String.valueOf(mCollectNum));
    }

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

        mTvDoCaseNumber = (TextView) view.findViewById(R.id.tv_do_case_number);
        mTvDoCaseNumber.setOnClickListener(this);
        mTvAllCaseNumber = (TextView) view.findViewById(R.id.tv_all_case_number);
        mTvAllCaseNumber.setOnClickListener(this);
        mTvDoCaseAllnumber = (TextView) view.findViewById(R.id.tv_do_case_allnumber);
        mTvDoCaseAllnumber.setOnClickListener(this);
        mLlCaseSpecialLayout = (LinearLayout) view.findViewById(R.id.ll_case_special_layout);
        mLlCaseSpecialLayout.setOnClickListener(this);
        mLlCaseExamLayout = (LinearLayout) view.findViewById(R.id.ll_case_exam_layout);
        mLlCaseExamLayout.setOnClickListener(this);
        mTvBanknewFavoriteList = (TextView) view.findViewById(R.id.tv_banknew_favorite_list);
        mTvBanknewFavoriteList.setOnClickListener(this);
        mLlBanknewFavaoite = (LinearLayout) view.findViewById(R.id.ll_banknew_favaoite);
        mLlBanknewFavaoite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_case_exam_layout://模拟考试
//                Intent intent3 = CaseExamtActivity.start_Intent(mContext, DataMessageVo.COURESID_CASE);
                Intent intent3 = GmMockTestActivity.newInstance(mContext, DataMessageVo.COURESID_CASE);
                intent3.putExtra(GmMockTestActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(R.string.mokeexam));
                startActivity(intent3);
                break;

            case R.id.ll_case_special_layout://专项练习
                Intent intent4 = GmSpecialListActivity.newInstance(mContext, DataMessageVo.COURESID_CASE);
//                Intent intent4 = CaseOrderTestActivity.start_Intent(mContext, DataMessageVo.COURESID_CASE);
                intent4.putExtra(GmSpecialListActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(R.string.special));
//                intent4.putExtra(CaseOrderTestActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(R.string.special));
                startActivity(intent4);
                break;
            case R.id.ll_banknew_favaoite://收藏夹
                Intent newInstance1 = MyCollectTextActivity.newInstance(mContext, DataMessageVo.COURESID_CASE,
                        String.valueOf(mCollectNum));
                newInstance1.putExtra(MyCollectTextActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(R.string.bank_my_collect));
                startActivity(newInstance1);
                break;
  /*          case R.id.ll_banknew_order://顺序练习
                Intent order = CaseOrderTestActivity.start_Intent(mContext, DataMessageVo.COURESID_CASE);
                order.putExtra(CaseOrderTestActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(R.string.sequential_exercise));
                mContext.startActivity(order);
                break;*/
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            bindViewData();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void DayHomeEvent(DayHomeEvent event) {

        switch (event.getType()) {
            case 3://刷新数据
                bindViewData();
                break;
            default:

        }
    }
}

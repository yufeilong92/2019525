package com.xuechuan.xcedu.ui.bank.newbank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.bank.HistoryAdapter;
import com.xuechuan.xcedu.adapter.bank.RankAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.flyn.Eyes;
import com.xuechuan.xcedu.mvp.contract.EveryDayRankContract;
import com.xuechuan.xcedu.mvp.model.EveryDayRankModel;
import com.xuechuan.xcedu.mvp.presenter.EveryDayRankPresenter;
import com.xuechuan.xcedu.ui.bank.newBankActivity.EveryDayTestAtivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.DayHomeBeanVo;
import com.xuechuan.xcedu.vo.EveryDayHistoryVo;
import com.xuechuan.xcedu.vo.EveryDayRankVo;
import com.xuechuan.xcedu.weight.CommonPopupWindow;
import com.xuechuan.xcedu.weight.WeiNestedScrollView;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: EveryDayActivity
 * @Package com.xuechuan.xcedu.ui.bank
 * @Description: 每日一练
 * @author: L-BackPacker
 * @date: 2018.12.29 上午 11:04
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.12.29
 */
public class EveryDayActivity extends BaseActivity implements View.OnClickListener, EveryDayRankContract.View {

    private ImageView mIvEverydayBack;
    private ImageView mIvEverydayCalendar;
    private TextView mTvEverydayTime;
    private TextView mTvEverydayKeyword;
    private TextView mTvEverydayTextnumber;
    private Button mBtnEverydayStartText;
    private RecyclerView mRlvEverydayRanking;
    private EveryDayRankPresenter mPresenter;
    private Context mContext;


    private static String PARAMT_KEY = "com.xuechuan.xcedu.ui.bankEveryDayActivity.paramt_id";
    private static String PARAMT1_KEY = "com.xuechuan.xcedu.ui.bankEveryDayActivity.paramt_courseid";
    private AlertDialog mDialog;
    private WeiNestedScrollView mWeiNestsvView;
    private ImageView mIvNetEmptyContent;
    private LinearLayout mRankRootLayout;
    private CommonPopupWindow mPopHistory;
    private String mCouresid;
    private DayHomeBeanVo mDayHomeData;

    public static Intent start_Intent(Context context, DayHomeBeanVo data, String courseid) {
        Intent intent = new Intent(context, EveryDayActivity.class);
        intent.putExtra(PARAMT_KEY, data);
        intent.putExtra(PARAMT1_KEY, courseid);
        return intent;
    }

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_every_day);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        Eyes.translucentStatusBar(this, false);
        setContentView(R.layout.activity_every_day);
        if (getIntent() != null) {
            mDayHomeData = (DayHomeBeanVo) getIntent().getSerializableExtra(PARAMT_KEY);
            mCouresid = getIntent().getStringExtra(PARAMT1_KEY);
        }
        initView();
        initData();
        initEvent();
        bindViewData();
    }

    private void bindViewData() {
        mTvEverydayTime.setText(TimeUtil.getYMDT(mDayHomeData.getDate()));
        mTvEverydayKeyword.setText(mDayHomeData.getKeyword());
        mTvEverydayTextnumber.setText(String.valueOf(mDayHomeData.getQuestionnum()));
    }

    private void initEvent() {
        mWeiNestsvView.setmSmartScrollChangedListener(new WeiNestedScrollView.ISmartScrollChangedListener() {
            @Override
            public void onScrolledToBottom() {

            }

            @Override
            public void onScrolledToTop() {

            }
        });
    }

    private void initData() {
        mPresenter = new EveryDayRankPresenter();
        mPresenter.initModelView(new EveryDayRankModel(), this);
        mPresenter.requestRank(mContext, mDayHomeData.getId());
        mDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));

    }

    private void initView() {
        mContext = this;
        mIvEverydayBack = (ImageView) findViewById(R.id.iv_everyday_back);
        mIvEverydayBack.setOnClickListener(this);
        mIvEverydayCalendar = (ImageView) findViewById(R.id.iv_everyday_calendar);
        mIvEverydayCalendar.setOnClickListener(this);
        mTvEverydayTime = (TextView) findViewById(R.id.tv_everyday_time);
        mTvEverydayKeyword = (TextView) findViewById(R.id.tv_everyday_keyword);
        mTvEverydayTextnumber = (TextView) findViewById(R.id.tv_everyday_textnumber);
        mBtnEverydayStartText = (Button) findViewById(R.id.btn_everyday_start_text);
        mRlvEverydayRanking = (RecyclerView) findViewById(R.id.rlv_everyday_ranking);
        mBtnEverydayStartText.setOnClickListener(this);
        mWeiNestsvView = (WeiNestedScrollView) findViewById(R.id.weiNestsv_view);
        mWeiNestsvView.setOnClickListener(this);
        mIvNetEmptyContent = (ImageView) findViewById(R.id.iv_net_empty_content);
        mIvNetEmptyContent.setOnClickListener(this);
        mRankRootLayout = (LinearLayout) findViewById(R.id.rank_root_layout);
        mRankRootLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_everyday_start_text://开始答题
                Intent intent = EveryDayTestAtivity.start_Intent(mContext, mDayHomeData.getId(), mCouresid);
                startActivity(intent);

                break;
            case R.id.iv_everyday_back://返回
                this.finish();
                break;
            case R.id.iv_everyday_calendar://历史
                mDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
                mPresenter.requestHistory(mContext, Integer.parseInt(mCouresid));
                break;
        }
    }


    @Override
    public void RankSuccess(String success) {
        dismissDialog(mDialog);
        EveryDayRankVo vo = Utils.getGosnT(success, EveryDayRankVo.class);
        if (vo.getStatus().getCode() == 200) {
            List<EveryDayRankVo.DatasBean> list = vo.getDatas();
            if (list == null || list.isEmpty()) {
                showHineView(false, true);
                return;
            }
            initAdapter(list);
        } else {
            T_ERROR(mContext);
        }
    }



    private void initAdapter(List<EveryDayRankVo.DatasBean> list) {
        setGridLayoutManger(mContext, mRlvEverydayRanking, 1);
        RankAdapter adapter = new RankAdapter(mContext, list);
        mRlvEverydayRanking.setAdapter(adapter);

    }

    private void showHineView(boolean main, boolean empty) {
        mRlvEverydayRanking.setVisibility(main ? View.VISIBLE : View.GONE);
        mIvNetEmptyContent.setVisibility(empty ? View.VISIBLE : View.GONE);
    }

    @Override
    public void RankError(String msg) {
        dismissDialog(mDialog);
        T_ERROR(mContext);
    }

    @Override
    public void HistorySuccess(String success) {
        dismissDialog(mDialog);
        EveryDayHistoryVo vo = Utils.getGosnT(success, EveryDayHistoryVo.class);
        if (vo.getStatus().getCode() == 200) {
            List<EveryDayHistoryVo.DatasBean> datas = vo.getDatas();
            if (datas == null || datas.isEmpty()) {
                Toast.makeText(EveryDayActivity.this, "暂无相关内容", Toast.LENGTH_SHORT).show();
                return;
            }
            showHistoryLayout(datas);
        } else {
            T_ERROR(mContext);
        }


    }

    @Override
    public void HistoryError(String msg) {

    }

    /**
     * 设置答题卡布局
     *
     * @param datas
     */
    private void showHistoryLayout(final List<EveryDayHistoryVo.DatasBean> datas) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        mPopHistory = new CommonPopupWindow(this, R.layout.pop_history_layout, ViewGroup.LayoutParams.MATCH_PARENT, (int) (screenHeight * 0.7)) {
            private RecyclerView mRlvRankDialog;

            @Override
            protected void initView() {
                View view = getContentView();
                mRlvRankDialog = view.findViewById(R.id.rlv_rank_dialog);
            }

            @Override
            protected void initEvent() {
                setGridLayoutManger(mContext, mRlvRankDialog, 1);
                HistoryAdapter adapter = new HistoryAdapter(mContext, datas);
                mRlvRankDialog.setAdapter(adapter);
                adapter.setOnItemClickListener(new HistoryAdapter.OnItemClickListener() {
                    @Override
                    public void onClickItem(EveryDayHistoryVo.DatasBean  o) {
                        Intent intent = EveryDayTestAtivity.start_Intent(mContext, o.getId(), mCouresid);
                        startActivity(intent);
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
                        Utils.setBackgroundAlpha(1f, EveryDayActivity.this);
                    }
                });
            }


        };
        mPopHistory.showAtLocation(mRankRootLayout, Gravity.BOTTOM, 0, 0);
        Utils.setBackgroundAlpha(0.5f, EveryDayActivity.this);
    }
}

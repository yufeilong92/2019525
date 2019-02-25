package com.xuechuan.xcedu.fragment.wenda;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.andview.refreshview.XRefreshView;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.wenda.WenDaHomeAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.WenDaContract;
import com.xuechuan.xcedu.mvp.model.WenDaModel;
import com.xuechuan.xcedu.mvp.presenter.WenDaPresenter;
import com.xuechuan.xcedu.ui.wenda.CreateProblemActivity;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.HankPickVo;
import com.xuechuan.xcedu.vo.MyProblemVo;
import com.xuechuan.xcedu.weight.XRefreshViewLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 问答
 * @author: L-BackPacker
 * @date: 2018/7/10 16:08
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AnswerFragment extends BaseFragment implements WenDaContract.View, View.OnClickListener {
    private RecyclerView mRlvNetContent;
    private ImageView mIvNetEmpty;
    private XRefreshViewLayout mXrfvContent;
    private Button mBtnWendaSubmitProblem;
    private Context mContext;
    /**
     * 精选
     */
    private List mArrary;
    /**
     * 我的
     */
    private List mTwoArrary;
    /**
     * 带解答
     */
    private List mThreeArrary;
    private boolean isRefresh;
    private long lastRefreshTime;

    private WenDaPresenter mPresenter;
    private WenDaHomeAdapter adapter;
    /**
     * 判断是否精选和我的
     */
    private boolean mMain = true;
    private RelativeLayout mRlAnswerLayout;

 /*   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answer, container, false);
        initView(view);
        return view;
    }
*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_wenda_submitProblem://
                Intent intent = CreateProblemActivity.start_Intent(mContext, 0, true);
                startActivity(intent);
                break;
            default:

        }
    }

    @Override
    protected int initInflateView() {
        return R.layout.fragment_answer;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
        clearData();
        clearDataTwo();
        clearDataThree();
        bindAdapterData();
        initXrfresh();
        mXrfvContent.startRefresh();
    }

    private void initXrfresh() {
        mXrfvContent.restoreLastRefreshTime(lastRefreshTime);
        mXrfvContent.setPullLoadEnable(true);
        mXrfvContent.setAutoLoadMore(true);
        mXrfvContent.setPullRefreshEnable(true);
        mXrfvContent.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                loadNewData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                LoadMoreData();
            }

        });
    }

    private void LoadMoreData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        if (mMain) {
            mPresenter.requestHankpickListsTwo(mContext, getNowPage() + 1);
        } else {
            mPresenter.requestMyQuestionListsTwo(mContext, getNowPageTwo() + 1);
        }
        mPresenter.requestWaitQuesiton(mContext);
    }

    private void loadNewData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        if (mMain) {
            mPresenter.requestHankpickListsOne(mContext, 1);
        } else {
            mPresenter.requestMyQuestionListsOne(mContext, 1);
        }
        mPresenter.requestWaitQuesiton(mContext);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
        if (mXrfvContent != null) {
            mXrfvContent.startRefresh();
        }
    }

    private boolean showHine;

    private static String TAG = "【" + AnswerFragment.class + "】==";

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {//显示
            Log.e(TAG, "setUserVisibleHint: " + isVisibleToUser);
        } else {//隐藏
            Log.e(TAG, "setUserVisibleHint: " + isVisibleToUser);
        }

    }

    private void bindAdapterData() {
        setGridLayoutManger(mContext, mRlvNetContent, 1);
        adapter = new WenDaHomeAdapter(mContext, getFragmentManager());
        adapter.changerHankpicList(mArrary,0);
        adapter.changerMyProlemList(mTwoArrary);
        adapter.changeWaitLists(mThreeArrary);
        mRlvNetContent.setAdapter(adapter);
        adapter.setOnRefreshClickListener(new WenDaHomeAdapter.OnRefreshClickListener() {
            @Override
            public void onClickItem(int type) {
                switch (type) {
                    case 1://精选
                        if (mArrary == null || mArrary.isEmpty()) {
                            mPresenter.requestHankpickListsOne(mContext, 1);
                        }else {
                          adapter.showEmptyImager(false);
                        }
                        isRefresh = false;
                        mXrfvContent.setLoadComplete(false);
                        mMain = true;

                        break;
                    case 2://我的
                        if (mTwoArrary == null || mTwoArrary.isEmpty()) {
                            mPresenter.requestMyQuestionListsOne(mContext, 1);
                        }else {
                            adapter.showEmptyImager(false);
                        }
                        isRefresh = false;
                        mMain = false;
                        mXrfvContent.setLoadComplete(false);
                        mXrfvContent.setPullLoadEnable(true);
                        break;

                }
            }
        });
    }

    private void initData() {
        mPresenter = new WenDaPresenter();
        mPresenter.initModelView(new WenDaModel(), this);
    }

    @Override
    public void WaitQuestionSuccess(String success) {
        MyProblemVo vo = Utils.getGosnT(success, MyProblemVo.class);
        if (vo.getStatus().getCode() == 200) {
            List<MyProblemVo.DatasBean> datas = vo.getDatas();
            clearDataThree();
            if (datas == null || datas.isEmpty()) {
                adapter.ShowOneView(false);
                adapter.notifyDataSetChanged();
                return;
            } else {
                addListDataThree(datas);
                adapter.ShowOneView(true);
                adapter.changeWaitLists(mThreeArrary);
                adapter.notifyDataSetChanged();
            }
        } else {
            T_ERROR(mContext);
        }

    }

    @Override
    public void WaitQuestionError(String msg) {
        T_ERROR(mContext);
    }

    @Override
    public void HankPicksuccessOne(String success) {
        mXrfvContent.stopRefresh();
        isRefresh = false;
        HankPickVo vo = Utils.getGosnT(success, HankPickVo.class);
        if (vo.getStatus().getCode() == 200) {
            List<HankPickVo.DatasBean> datas = vo.getDatas();
            clearData();
            if (datas == null || datas.isEmpty()) {
                mIvNetEmpty.setVisibility(View.VISIBLE);
                mXrfvContent.setLoadComplete(true);
                adapter.changerHankpicList(mArrary,vo.getTotal().getTotal());
                adapter.notifyDataSetChanged();
                return;
            } else {
                addListData(datas);
            }
            //判断是否能整除
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXrfvContent.setLoadComplete(false);
                mXrfvContent.setPullLoadEnable(true);
            } else {
                mXrfvContent.setLoadComplete(true);
            }
            if (vo.getTotal().getTotal() == mArrary.size()) {
                mXrfvContent.setLoadComplete(true);
            }
            adapter.changerHankpicList(mArrary,vo.getTotal().getTotal());
            adapter.notifyDataSetChanged();
        } else {
            T_ERROR(mContext);
        }


    }

    @Override
    public void HankPickErrorOne(String msg) {
        mXrfvContent.stopRefresh();
        isRefresh = false;
        T_ERROR(mContext);
    }

    @Override
    public void HankPicksuccessTwo(String success) {
        isRefresh = false;
        HankPickVo vo = Utils.getGosnT(success, HankPickVo.class);
        if (vo.getStatus().getCode() == 200) {
            List<HankPickVo.DatasBean> datas = vo.getDatas();
//            clearDataTwo();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                mXrfvContent.setLoadComplete(true);
                adapter.changerHankpicList(mArrary,vo.getTotal().getTotal());
                adapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXrfvContent.setLoadComplete(false);
                mXrfvContent.setPullLoadEnable(true);
            } else {
                mXrfvContent.setLoadComplete(true);
            }
            if (vo.getTotal().getTotal() == mArrary.size()) {
                mXrfvContent.setLoadComplete(true);
            }
            adapter.changerHankpicList(mArrary,vo.getTotal().getTotal());
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            T_ERROR(mContext);

        }
    }

    @Override
    public void HankPickErrorTwo(String msg) {
        T_ERROR(mContext);
        isRefresh = false;
        mXrfvContent.setLoadComplete(false);

    }

    @Override
    public void MyQuestionSuccessOne(String success) {
        mXrfvContent.stopRefresh();
        isRefresh = false;
        HankPickVo vo = Utils.getGosnT(success, HankPickVo.class);
        if (vo.getStatus().getCode() == 200) {
            List<HankPickVo.DatasBean> datas = vo.getDatas();
            clearDataTwo();
            if (datas == null || datas.isEmpty()) {
                mIvNetEmpty.setVisibility(View.VISIBLE);
                mXrfvContent.setLoadComplete(true);
                adapter.changerMyProlemList(mTwoArrary);
                adapter.notifyDataSetChanged();
                return;
            } else {
                addListDataTwo(datas);
            }
            //判断是否能整除
            if (!mTwoArrary.isEmpty() && mTwoArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXrfvContent.setLoadComplete(false);
                mXrfvContent.setPullLoadEnable(true);
            } else {
                mXrfvContent.setLoadComplete(true);
            }
            if (vo.getTotal().getTotal() == mTwoArrary.size()) {
                mXrfvContent.setLoadComplete(true);
            }
            adapter.changerMyProlemList(mTwoArrary);
            adapter.notifyDataSetChanged();
        } else {
            T_ERROR(mContext);
        }

    }

    @Override
    public void MyQuestionErrorOne(String msg) {
        T_ERROR(mContext);
        isRefresh = false;
    }

    @Override
    public void MyQuestionSuccessTwo(String success) {
        isRefresh = false;
        HankPickVo vo = Utils.getGosnT(success, HankPickVo.class);
        if (vo.getStatus().getCode() == 200) {
            List<HankPickVo.DatasBean> datas = vo.getDatas();
//            clearDataTwo();
            if (datas == null || datas.isEmpty()) {
                mXrfvContent.setLoadComplete(true);
                adapter.changerMyProlemList(mTwoArrary);
                adapter.notifyDataSetChanged();
                return;
            } else {
                addListDataTwo(datas);
            }
            //判断是否能整除
            if (!mTwoArrary.isEmpty() && mTwoArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXrfvContent.setLoadComplete(false);
                mXrfvContent.setPullLoadEnable(true);
            } else {
                mXrfvContent.setLoadComplete(true);
            }
            if (vo.getTotal().getTotal() == mTwoArrary.size()) {
                mXrfvContent.setLoadComplete(true);
            }
            adapter.changerMyProlemList(mTwoArrary);
            adapter.notifyDataSetChanged();
        } else {
            T_ERROR(mContext);
        }
    }

    @Override
    public void MyQuestionErrorTwo(String msg) {
        T_ERROR(mContext);
        isRefresh = false;
    }

    private void initView(View view) {
        mContext = getActivity();
        mRlvNetContent = (RecyclerView) view.findViewById(R.id.rlv_net_content);
        mIvNetEmpty = (ImageView) view.findViewById(R.id.iv_net_empty);
        mXrfvContent = (XRefreshViewLayout) view.findViewById(R.id.xrfv_content);
        mBtnWendaSubmitProblem = (Button) view.findViewById(R.id.btn_wenda_submitProblem);

        mBtnWendaSubmitProblem.setOnClickListener(this);
        mRlAnswerLayout = (RelativeLayout) view.findViewById(R.id.rl_answer_layout);
        mRlAnswerLayout.setOnClickListener(this);
    }

    private void clearData() {
        if (mArrary == null) {
            mArrary = new ArrayList();
        } else {
            mArrary.clear();
        }
    }

    private void clearDataTwo() {
        if (mTwoArrary == null) {
            mTwoArrary = new ArrayList();
        } else {
            mTwoArrary.clear();
        }
    }

    private void clearDataThree() {
        if (mThreeArrary == null) {
            mThreeArrary = new ArrayList();
        } else {
            mThreeArrary.clear();
        }
    }

    private void addListDataThree(List<?> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (mThreeArrary == null) {
            clearData();
        }
        mThreeArrary.addAll(list);
    }

    private void addListData(List<?> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (mArrary == null) {
            clearData();
        }
        mArrary.addAll(list);
    }

    private void addListDataTwo(List<?> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (mTwoArrary == null) {
            clearData();
        }
        mTwoArrary.addAll(list);
    }

    /**
     * 当前数据有几页
     *
     * @return
     */
    private int getNowPage() {
        if (mArrary == null || mArrary.isEmpty())
            return 0;
        if (mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0)
            return mArrary.size() / DataMessageVo.CINT_PANGE_SIZE;
        else
            return mArrary.size() / DataMessageVo.CINT_PANGE_SIZE + 1;
    }


    /**
     * 当前数据有几页
     *
     * @return
     */
    private int getNowPageTwo() {
        if (mTwoArrary == null || mTwoArrary.isEmpty())
            return 0;
        if (mTwoArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0)
            return mTwoArrary.size() / DataMessageVo.CINT_PANGE_SIZE;
        else
            return mTwoArrary.size() / DataMessageVo.CINT_PANGE_SIZE + 1;
    }


}

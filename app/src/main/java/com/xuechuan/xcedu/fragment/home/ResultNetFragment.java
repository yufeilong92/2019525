package com.xuechuan.xcedu.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.home.ResultNetAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.model.SearchModelImpl;
import com.xuechuan.xcedu.mvp.presenter.SearchPresenter;
import com.xuechuan.xcedu.mvp.view.SearchView;
import com.xuechuan.xcedu.ui.net.VideoInfomActivity;
import com.xuechuan.xcedu.vo.NetAllVo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: ResultNetFragment
 * @Package com.xuechuan.xcedu.fragment.home
 * @Description: 搜索结果网课模块
 * @author: L-BackPacker
 * @date: 2018.11.22 下午 3:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.11.22
 */
public class ResultNetFragment extends BaseFragment implements SearchView {
    private static final String MKEY = "key";
    private static final String MTYPE = "type";

    private String mSearchKey;
    private String mType;
    private RecyclerView mRlvNetContent;
    private ImageView mIvNetEmpty;
    private XRefreshView mXrfvContent;
    private Context mContext;
    private long lastRefreshTime;
    private List mArrary;
    private ResultNetAdapter adapter;
    private boolean isRefresh;
    private SearchPresenter mPresenter;

    public static ResultNetFragment newInstance(String key, String type) {
        ResultNetFragment fragment = new ResultNetFragment();
        Bundle args = new Bundle();
        args.putString(MKEY, key);
        args.putString(MTYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSearchKey = getArguments().getString(MKEY);
            mType = getArguments().getString(MTYPE);
        }
    }

    @Override
    protected int initInflateView() {
        return R.layout.fragment_result_net;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
        clearData();
        bindAdapterData();
        initXrfresh();
        mXrfvContent.startRefresh();
    }


    private void initData() {
        mPresenter = new SearchPresenter(new SearchModelImpl(), this);
    }


    private void bindAdapterData() {
        adapter = new ResultNetAdapter(mContext, mArrary);
        setGridLayoutManger(mContext, mRlvNetContent, 1);
        mRlvNetContent.setAdapter(adapter);
        adapter.setOnItemClickListener(new ResultNetAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(NetAllVo.DatasBean vo) {
                Intent intent = VideoInfomActivity.start_Intent(mContext, String.valueOf(vo.getId()));
                intent.putExtra(VideoInfomActivity.CSTR_EXTRA_TITLE_STR_HOME, vo.getName());
                mContext.startActivity(intent);
            }
        });
    }

    private void initView(View view) {
        mContext = getActivity();
        mRlvNetContent = (RecyclerView) view.findViewById(R.id.rlv_net_content);
        mIvNetEmpty = (ImageView) view.findViewById(R.id.iv_net_empty);
        mXrfvContent = (XRefreshView) view.findViewById(R.id.xrfv_content);
    }


    private void initXrfresh() {
        mXrfvContent.restoreLastRefreshTime(lastRefreshTime);
        mXrfvContent.setPullRefreshEnable(true);
        mXrfvContent.setPullLoadEnable(true);
        mXrfvContent.setAutoLoadMore(true);
        mXrfvContent.setEmptyView(mIvNetEmpty);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
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
        if (isRefresh) return;
        isRefresh = true;
        mPresenter.requestMoreReusltCont(mContext, getNowPage() + 1, mSearchKey, mType);
    }

    private void loadNewData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestReusltCont(mContext, 1, mSearchKey, mType);
    }

    private void clearData() {
        if (mArrary == null) {
            mArrary = new ArrayList();
        } else {
            mArrary.clear();
        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void HostSuccess(String cont) {

    }

    @Override
    public void HostError(String cont) {

    }

    @Override
    public void ResultSuccess(String cont) {
        mXrfvContent.stopRefresh();
        lastRefreshTime = mXrfvContent.getLastRefreshTime();
        isRefresh = false;
        Gson gson = new Gson();

    }

    @Override
    public void ResultError(String cont) {

    }

    @Override
    public void ResultMoreSuccess(String cont) {

    }

    @Override
    public void ResultMoreError(String cont) {

    }
}

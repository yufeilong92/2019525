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
import com.xuechuan.xcedu.adapter.home.ResultBookAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.BaseVo;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.event.SearchBookEvent;
import com.xuechuan.xcedu.mvp.model.SearchModelImpl;
import com.xuechuan.xcedu.mvp.presenter.SearchPresenter;
import com.xuechuan.xcedu.mvp.view.SearchView;
import com.xuechuan.xcedu.ui.home.YouZanActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ArticleListVo;
import com.xuechuan.xcedu.vo.ArticleVo;
import com.xuechuan.xcedu.vo.SearchBookVo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: BooksFragment
 * @Package com.xuechuan.xcedu.fragment.home
 * @Description: 搜索结果图书模块
 * @author: L-BackPacker
 * @date: 2018.11.22 下午 2:42
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.11.22
 */
public class ResultBooksFragment extends BaseFragment implements SearchView {
    private static final String KEY = "com.xuechuan.xcedu.fragment.home.paramt_key";
    private static final String TYPE = "com.xuechuan.xcedu.fragment.home.paramt_type";
    /**
     * 搜索的key
     */
    private String mKey;
    private String mType;
    private RecyclerView mRlvNetContent;
    private ImageView mIvNetEmpty;
    private XRefreshView mXrfvContent;
    private List mArrary;
    private Context mContext;
    private long lastRefreshTime;
    private ResultBookAdapter adapter;
    private SearchPresenter mPresenter;
    private boolean isRefresh;

    /**
     * @param key  搜索key
     * @param type
     * @return
     */
    public static ResultBooksFragment newInstance(String key, String type) {
        ResultBooksFragment fragment = new ResultBooksFragment();
        Bundle args = new Bundle();
        args.putString(KEY, key);
        args.putString(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mKey = getArguments().getString(KEY);
            mType = getArguments().getString(TYPE);
        }
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MainThreaData(SearchBookEvent event) {
        String key = event.getmKey();
        mKey = key;
        if (mXrfvContent != null) {
            mXrfvContent.startRefresh();
        }
    }

    @Override
    protected int initInflateView() {
        return R.layout.fragment_books;
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
        adapter = new ResultBookAdapter(mContext, mArrary);
        setGridLayoutManger(mContext, mRlvNetContent, 1);
        mRlvNetContent.setAdapter(adapter);
        adapter.setOnItemClickListener(new ResultBookAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(SearchBookVo.DatasBean vo) {
                Intent intent = YouZanActivity.newInstance(mContext, vo.getYouzanurl());
                intent.putExtra(YouZanActivity.CSTR_EXTRA_TITLE_STR, "商城");
                mContext.startActivity(intent);
            }
        });
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
        mPresenter.requestMoreReusltCont(mContext, getNowPage() + 1, mKey, mType);
    }

    private void loadNewData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestReusltCont(mContext, 1, mKey, mType);

    }

    private void initView(View inflate) {
        mContext = getActivity();
        mRlvNetContent = (RecyclerView) inflate.findViewById(R.id.rlv_net_content);
        mIvNetEmpty = (ImageView) inflate.findViewById(R.id.iv_net_empty);
        mXrfvContent = (XRefreshView) inflate.findViewById(R.id.xrfv_content);
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
        SearchBookVo vo = gson.fromJson(cont, SearchBookVo.class);
        BaseVo.StatusBean status = vo.getStatus();
        if (status.getCode() == 200) {
            List<SearchBookVo.DatasBean> datas = vo.getDatas();
            clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                adapter.notifyDataSetChanged();
                mXrfvContent.setLoadComplete(true);
                return;
            }
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                // 设置是否可以上拉加载
                mXrfvContent.setPullLoadEnable(true);
                mXrfvContent.setLoadComplete(false);
            } else
                mXrfvContent.setLoadComplete(true);
            if (vo.getTotal().getTotal() == mArrary.size())
                mXrfvContent.setLoadComplete(true);
            adapter.notifyDataSetChanged();
        } else {
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
        }
    }

    @Override
    public void ResultError(String cont) {
        isRefresh = false;
        mXrfvContent.stopRefresh();
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
    }

    @Override
    public void ResultMoreSuccess(String cont) {
        isRefresh = false;
        Gson gson = new Gson();
        SearchBookVo vo = gson.fromJson(cont, SearchBookVo.class);
        BaseVo.StatusBean status = vo.getStatus();
        if (status.getCode() == 200) {
            List<SearchBookVo.DatasBean> datas = vo.getDatas();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                adapter.notifyDataSetChanged();
                mXrfvContent.setLoadComplete(true);
                return;
            }

            if (mArrary != null && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXrfvContent.setPullLoadEnable(true);
                mXrfvContent.setLoadComplete(false);
            } else {
                mXrfvContent.setLoadComplete(true);
            }
            if (vo.getTotal().getTotal() == mArrary.size())
                mXrfvContent.setLoadComplete(true);
            adapter.notifyDataSetChanged();
        } else {
            mXrfvContent.setLoadComplete(false);
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
        }
    }

    @Override
    public void ResultMoreError(String cont) {
        isRefresh = false;
        mXrfvContent.setLoadComplete(false);
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }
}

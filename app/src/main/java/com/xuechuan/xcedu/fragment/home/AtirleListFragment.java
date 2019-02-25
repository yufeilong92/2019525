package com.xuechuan.xcedu.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.home.ArticleListAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.BaseVo;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.model.SearchModelImpl;
import com.xuechuan.xcedu.mvp.presenter.SearchPresenter;
import com.xuechuan.xcedu.mvp.view.SearchView;
import com.xuechuan.xcedu.ui.InfomDetailActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ArticleListVo;
import com.xuechuan.xcedu.vo.ArticleVo;
import com.xuechuan.xcedu.weight.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: ResultFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 结果
 * @author: L-BackPacker
 * @date: 2018/5/8 18:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/8
 */
public class AtirleListFragment extends BaseFragment implements SearchView {
    private static final String SEARCHKEY = "param1";
    private static final String TYPELEI = "param2";

    private String mSearchkey;
    private String mType;
    private RecyclerView mRlvReasultContent;
    private TextView mTvEmpty;
    private XRefreshView mXrfResultContent;

    private List mArray;
    private boolean isRefresh;
    private Context mContext;
    private AlertDialog mAlertDialog;
    private ArticleListAdapter adapter;
    long lastRefreshtime;
    private SearchPresenter mPresenter;

    /**
     * @param searck
     * @param type
     * @return
     */
    public static AtirleListFragment newInstance(String searck, String type) {
        AtirleListFragment fragment = new AtirleListFragment();
        Bundle args = new Bundle();
        args.putString(SEARCHKEY, searck);
        args.putString(TYPELEI, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSearchkey = getArguments().getString(SEARCHKEY);
            mType = getArguments().getString(TYPELEI);
        }
    }

    /*   @Override
       public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
           View view = inflater.inflate(R.layout.fragment_result, container, false);
           initView(view);
           initData();


           return view;
       }
   */
    @Override
    protected int initInflateView() {
        return R.layout.fragment_atirle_list;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
        clearData();
        bindAdapterData();
        initXrfresh();
    }
    private void bindAdapterData() {
        adapter = new ArticleListAdapter(mContext, mArray);
        setGridLayoutManger(mContext,mRlvReasultContent,1);
        mRlvReasultContent.addItemDecoration(new android.support.v7.widget.DividerItemDecoration(mContext, 2));
        mRlvReasultContent.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.BOTH_SET, R.drawable.recyclerline));
        mRlvReasultContent.setAdapter(adapter);
        adapter.setClickListener(new ArticleListAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                ArticleVo vo = (ArticleVo) obj;
                Intent intent = InfomDetailActivity.startInstance(mContext, vo.getGourl(),
                        String.valueOf(vo.getId())
                        , vo.getTitle(), DataMessageVo.USERTYOEARTICLE);
//                Intent intent = InfomDetailActivity.startInstance(mContext, String.valueOf(vo.getId()), vo.getGourl(),DataMessageVo.USERTYPEA );
                mContext.startActivity(intent);
            }
        });

    }

    private void initXrfresh() {
        mXrfResultContent.setPullLoadEnable(true);
        mXrfResultContent.setAutoRefresh(true);
        mXrfResultContent.setAutoLoadMore(true);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));

        mXrfResultContent.restoreLastRefreshTime(lastRefreshtime);
        mXrfResultContent.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                reqestData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                loadMoreData();

            }
        });
    }

    private void loadMoreData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestReusltCont(mContext, getPager() + 1, mSearchkey, mType);
    }

    private void reqestData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestReusltCont(mContext, 1, mSearchkey, mType);
    }

    private void initData() {
        mPresenter = new SearchPresenter(new SearchModelImpl(), this);
        mPresenter.requestReusltCont(mContext, 1, mSearchkey, mType);
        mAlertDialog = DialogUtil.showDialog(mContext, "", getStrWithId(mContext, R.string.loading));
    }


    private void initView(View view) {
        mContext = getActivity();
        mRlvReasultContent = (RecyclerView) view.findViewById(R.id.rlv_reasult_content);
        mTvEmpty = (TextView) view.findViewById(R.id.tv_empty);
        mXrfResultContent = (XRefreshView) view.findViewById(R.id.xrf_result_content);
    }

    @Override
    public void HostSuccess(String cont) {

    }

    @Override
    public void HostError(String cont) {

    }

    @Override
    public void ResultSuccess(String message) {
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
        mXrfResultContent.stopRefresh();
        lastRefreshtime = mXrfResultContent.getLastRefreshTime();
        isRefresh = false;

        L.w(message);
        Gson gson = new Gson();
        ArticleListVo vo = gson.fromJson(message, ArticleListVo.class);
        BaseVo.StatusBean status = vo.getStatus();
        if (status.getCode() == 200) {
            List<ArticleVo> datas = vo.getDatas();
            clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                adapter.notifyDataSetChanged();
                mXrfResultContent.setLoadComplete(true);
                return;
            }
            if (!mArray.isEmpty() && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                // 设置是否可以上拉加载
                mXrfResultContent.setPullLoadEnable(true);
                mXrfResultContent.setLoadComplete(false);
            } else
                mXrfResultContent.setLoadComplete(true);
//            mXrfResultContent.setPullLoadEnable(true);
//            mXrfResultContent.setLoadComplete(false);
            if (vo.getTotal().getTotal() == mArray.size())
                mXrfResultContent.setLoadComplete(true);
            adapter.notifyDataSetChanged();
        } else {
            T.showToast(mContext, getStrWithId(mContext, R.string.net_error));
//            T.showToast(mContext, status.getMessage());
        }

    }

    @Override
    public void ResultError(String cont) {
        isRefresh = false;
        mXrfResultContent.stopRefresh();
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
        T.showToast(mContext, getStrWithId(mContext, R.string.net_error));

    }

    @Override
    public void ResultMoreSuccess(String message) {
        isRefresh = false;
        L.w(message);
        Gson gson = new Gson();
        ArticleListVo vo = gson.fromJson(message, ArticleListVo.class);
        BaseVo.StatusBean status = vo.getStatus();
        if (status.getCode() == 200) {
            List<ArticleVo> datas = vo.getDatas();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                mXrfResultContent.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }

            if (mArray != null && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXrfResultContent.setPullLoadEnable(true);
                mXrfResultContent.setLoadComplete(false);
            } else {
                mXrfResultContent.setLoadComplete(true);
            }
            if (vo.getTotal().getTotal() == mArray.size())
                mXrfResultContent.setLoadComplete(true);
            adapter.notifyDataSetChanged();
        } else {
            mXrfResultContent.setLoadComplete(false);
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
            L.e(status.getMessage());
        }
    }

    @Override
    public void ResultMoreError(String cont) {
        isRefresh = false;
        mXrfResultContent.setLoadComplete(false);
        L.e(cont);
        T.showToast(mContext, getStrWithId(mContext, R.string.net_error));

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

    private int getPager() {
        if (mArray == null || mArray.isEmpty()) {
            return 0;
        }
        if (mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
            return mArray.size() / DataMessageVo.CINT_PANGE_SIZE;
        } else {
            return mArray.size() / DataMessageVo.CINT_PANGE_SIZE + 1;
        }
    }

}

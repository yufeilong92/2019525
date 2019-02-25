package com.xuechuan.xcedu.fragment.net;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.net.NetCommentAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.BaseVo;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.NetCommentContract;
import com.xuechuan.xcedu.mvp.model.NetCommentModel;
import com.xuechuan.xcedu.mvp.presenter.NetCommentPresenter;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.EvalueNewVo;
import com.xuechuan.xcedu.vo.SearchBookVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetEvaluateFragment
 * @Package com.xuechuan.xcedu.fragment.home
 * @Description: 网课评价
 * @author: L-BackPacker
 * @date: 2018.11.26 下午 2:18
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.11.26
 */
public class NetEvaluateFragment extends BaseFragment implements NetCommentContract.View {

    private static final String PRODUCTID = "productid";

    private String mProductid;
    private RecyclerView mRlvNetContent;
    private ImageView mIvNetEmpty;
    private XRefreshView mXrfvContent;
    private Context mContext;
    private boolean isRefresh;
    private long lastRefreshTime;
    private List mArrary;
    private NetCommentAdapter adapter;
    private NetCommentPresenter mPresenter;

    public static NetEvaluateFragment newInstance(String productid) {
        NetEvaluateFragment fragment = new NetEvaluateFragment();
        Bundle args = new Bundle();
        args.putString(PRODUCTID, productid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProductid = getArguments().getString(PRODUCTID);
        }
    }

 /*   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_net_evaluate, container, false);
        initView(view);
        return view;
    }*/

    @Override
    protected int initInflateView() {
        return R.layout.fragment_net_evaluate;
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
        mPresenter.requestNetCommentOne(mContext, getNowPage() + 1, mProductid);

    }

    private void loadNewData() {
        if (isRefresh) return;
        isRefresh = true;
        mPresenter.requestNetCommentOne(mContext, 1, mProductid);

    }

    private void bindAdapterData() {
        setGridLayoutManger(mContext, mRlvNetContent, 1);
        adapter = new NetCommentAdapter(mContext, mArrary);
        mRlvNetContent.setAdapter(adapter);
    }

    private void initData() {
        mPresenter = new NetCommentPresenter();
        mPresenter.initModelView(new NetCommentModel(), this);
    }


    private void initView(View view) {
        mContext = getActivity();
        mRlvNetContent = (RecyclerView) view.findViewById(R.id.rlv_net_content);
        mIvNetEmpty = (ImageView) view.findViewById(R.id.iv_net_empty);
        mXrfvContent = (XRefreshView) view.findViewById(R.id.xrfv_content);
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
    public void CommentSuccessOne(String success) {
        mXrfvContent.stopRefresh();
        isRefresh = false;
        EvalueNewVo evalueNewVo = Utils.getGosnT(success, EvalueNewVo.class);
        lastRefreshTime = mXrfvContent.getLastRefreshTime();
        if (evalueNewVo.getStatus().getCode() == 200) {
            List<EvalueNewVo.DatasBean> datas = evalueNewVo.getDatas();
            clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                adapter.notifyDataSetChanged();
                mXrfvContent.setLoadComplete(true);
            }
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                // 设置是否可以上拉加载
                mXrfvContent.setPullLoadEnable(true);
                mXrfvContent.setLoadComplete(false);
            } else
                mXrfvContent.setLoadComplete(true);
            if (evalueNewVo.getTotal().getTotal() == mArrary.size())
                mXrfvContent.setLoadComplete(true);
            adapter.notifyDataSetChanged();
        } else {
            T_ERROR(mContext);
        }
    }

    @Override
    public void CommentErrorOne(String msg) {
        isRefresh = false;
        mXrfvContent.stopRefresh();
        T_ERROR(mContext);
    }

    @Override
    public void CommentSuccessTwo(String success) {
        isRefresh = false;
        EvalueNewVo vo = Utils.getGosnT(success, EvalueNewVo.class);
        BaseVo.StatusBean status = vo.getStatus();
        if (status.getCode() == 200) {
            List<EvalueNewVo.DatasBean> datas = vo.getDatas();
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
            T_ERROR(mContext);
        }
    }

    @Override
    public void CommentErrorTwo(String msg) {
        isRefresh = false;
        mXrfvContent.setLoadComplete(false);
        T_ERROR(mContext);
    }
}

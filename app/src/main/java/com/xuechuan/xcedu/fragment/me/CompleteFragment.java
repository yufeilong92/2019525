package com.xuechuan.xcedu.fragment.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.me.MyOrderAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.PerOrderContract;
import com.xuechuan.xcedu.mvp.model.PerOrderModel;
import com.xuechuan.xcedu.mvp.presenter.PerOrderPresenter;
import com.xuechuan.xcedu.ui.home.YouZanActivity;
import com.xuechuan.xcedu.ui.me.DelectSuceessActivity;
import com.xuechuan.xcedu.ui.me.OrderInfomActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.MyOrderNewVo;
import com.xuechuan.xcedu.vo.ResultVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: CompleteFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 已完成、
 * @author: L-BackPacker
 * @date: 2018/5/26 13:13
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/26
 */
public class CompleteFragment extends BaseFragment implements PerOrderContract.View {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mStatus;
    private String mParam2;
    private RecyclerView mRlvMyOrderContentCom;
    private XRefreshView mXfvContentOrderCom;
    private Context mContext;
    private MyOrderAdapter adapter;
    private List mArrary;
    private ImageView mIvContentEmpty;
    private long lastRefreshTime;
    private boolean isRefresh;
    private PerOrderPresenter mPresenter;
    private int mDelPositon = -1;

    public CompleteFragment() {
    }


    public static CompleteFragment newInstance(String param1, String param2) {
        CompleteFragment fragment = new CompleteFragment();
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
            mStatus = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complete, container, false);
        initView(view);
        return view;
    }
*/

    @Override
    protected int initInflateView() {
        return R.layout.fragment_complete;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
        clearData();
        bindAdapterData();
        initXrfresh();

    }

    private void initData() {
        mPresenter = new PerOrderPresenter();
        mPresenter.BasePresenter(new PerOrderModel(), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mXfvContentOrderCom.startRefresh();
    }

    private void initView(View view) {
        mContext = getActivity();
        mRlvMyOrderContentCom = (RecyclerView) view.findViewById(R.id.rlv_my_order_content_com);
        mXfvContentOrderCom = (XRefreshView) view.findViewById(R.id.xfv_content_order_com);
        mIvContentEmpty = (ImageView) view.findViewById(R.id.iv_content_empty);
    }

    private void bindAdapterData() {
        setGridLayoutManger(mContext, mRlvMyOrderContentCom, 1);
        adapter = new MyOrderAdapter(mContext, mArrary);
        mRlvMyOrderContentCom.setAdapter(adapter);
        adapter.setClickListener(new MyOrderAdapter.onItemClickListener() {
            @Override
            public void onClickListener(MyOrderNewVo.DatasBean obj, int position) {
                if (obj.getOrdertype() == 1) {
                    Intent intent = YouZanActivity.newInstance(mContext, Utils.getYouZanUrl(mContext, obj.getOrdernum()));
                    startActivity(intent);
                } else {
                    Intent intent = OrderInfomActivity.newInstance(mContext, obj.getOrdernum());
                    intent.putExtra(OrderInfomActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(R.string.order_detail));
                    startActivity(intent);
                }
            }
        });
        adapter.setClickListener(new MyOrderAdapter.onItemDelClickListener() {
            @Override
            public void onDelClickListener(MyOrderNewVo.DatasBean obj, int position) {
                showData(obj, position);
            }
        });
    }

    private void showData(final MyOrderNewVo.DatasBean obj, final int position) {
        DialogUtil dialogUtil = DialogUtil.getInstance();
        dialogUtil.showTitleDialog(mContext, getStrWithId(mContext, R.string.delect_order1), getStrWithId(mContext, R.string.sure)
                , getStrWithId(mContext, R.string.cancel), true);
        dialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
            @Override
            public void onSureClickListener() {
                mDelPositon = position;
                mPresenter.submitDelOrd(mContext, obj.getOrdernum(), DataMessageVo.DELETEORDER);
            }

            @Override
            public void onCancelClickListener() {

            }
        });

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

    private void initXrfresh() {
        mXfvContentOrderCom.restoreLastRefreshTime(lastRefreshTime);
        mXfvContentOrderCom.setPullLoadEnable(true);
        mXfvContentOrderCom.setAutoLoadMore(true);
        mXfvContentOrderCom.setPullRefreshEnable(true);
        mXfvContentOrderCom.setEmptyView(mIvContentEmpty);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mXfvContentOrderCom.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
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

    private void loadNewData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;

        mPresenter.requestOrder(mContext, 1, mStatus);
    }

    private void LoadMoreData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestOrderMore(mContext, getNowPage() + 1, mStatus);
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
    public void OrderSuccess(String con) {
        L.d("com============" + con);
        mXfvContentOrderCom.stopRefresh();
        isRefresh = false;
        Gson gson = new Gson();
        MyOrderNewVo orderVo = gson.fromJson(con, MyOrderNewVo.class);
        if (orderVo.getStatus().getCode() == 200) {
            List<MyOrderNewVo.DatasBean> datas = orderVo.getDatas();
            clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                mXfvContentOrderCom.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                // 设置是否可以上拉加载
                mXfvContentOrderCom.setPullLoadEnable(true);
                mXfvContentOrderCom.setLoadComplete(false);
            } else
                mXfvContentOrderCom.setLoadComplete(true);
//            if (mArrary.size() == orderVo.getTotal().getTotal()) {
//                mXfvContentOrderCom.setPullLoadEnable(true);
//                mXfvContentOrderCom.setLoadComplete(true);
//            } else {
//            mXfvContentOrderCom.setPullLoadEnable(true);
//            mXfvContentOrderCom.setLoadComplete(false);
//            }
            if (orderVo.getTotal().getTotal() == mArrary.size())
                mXfvContentOrderCom.setLoadComplete(true);
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            T.showToast(mContext, getStrWithId(mContext, R.string.net_error));
            L.e(orderVo.getStatus().getMessage());
        }

    }

    @Override
    public void OrderError(String con) {
        isRefresh = false;
        mXfvContentOrderCom.stopRefresh();
        T.showToast(mContext, getStrWithId(mContext, R.string.net_error));

    }

    @Override
    public void OrderSuccessMore(String con) {
        isRefresh = false;
        Gson gson = new Gson();
        MyOrderNewVo orderVo = gson.fromJson(con, MyOrderNewVo.class);
        if (orderVo.getStatus().getCode() == 200) {
            List<MyOrderNewVo.DatasBean> datas = orderVo.getDatas();
//                    clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                mXfvContentOrderCom.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXfvContentOrderCom.setLoadComplete(false);
                mXfvContentOrderCom.setPullLoadEnable(true);
            } else {
                mXfvContentOrderCom.setLoadComplete(true);
            }
            if (orderVo.getTotal().getTotal() == mArrary.size())
                mXfvContentOrderCom.setLoadComplete(true);
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            mXfvContentOrderCom.setLoadComplete(false);
            T.showToast(mContext, getStrWithId(mContext, R.string.net_error));
            L.e(orderVo.getStatus().getMessage());
        }
    }

    @Override
    public void OrderErrorMore(String con) {
        isRefresh = false;
        mXfvContentOrderCom.setLoadComplete(false);
        T.showToast(mContext, getStrWithId(mContext, R.string.net_error));

    }


    @Override
    public void submitSuccess(String con) {
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(con, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (mDelPositon != -1 && mDelPositon >= 0) {
                mArrary.remove(mDelPositon);
                mRlvMyOrderContentCom.setItemAnimator(new DefaultItemAnimator());
                adapter.notifyDataSetChanged();
                mDelPositon = -1;
                DelectSuceessActivity.newInstance(mContext, DelectSuceessActivity.DELECTSUCCESS);
            }
        } else {
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
            L.e(vo.getStatus().getMessage());
        }


    }

    @Override
    public void submitError(String con) {
        T.showToast(mContext, getStrWithId(mContext, R.string.net_error));

    }
}

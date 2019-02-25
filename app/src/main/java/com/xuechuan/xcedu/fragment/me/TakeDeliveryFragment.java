package com.xuechuan.xcedu.fragment.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.xuechuan.xcedu.ui.me.OrderInfomActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.MyOrderNewVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: TakeDeliveryFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 待收货地址
 * @author: L-BackPacker
 * @date: 2018/8/22 14:30
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/8/22
 */

public class TakeDeliveryFragment extends BaseFragment implements PerOrderContract.View {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView mRlvMyTakeContentCom;
    private ImageView mIvContentEmpty;
    private XRefreshView mXfvContentTakeCom;
    private ArrayList mArrary;
    private Context mContext;
    private MyOrderAdapter mAdapter;
    private boolean isRefresh;
    private String mTagMake;
    private PerOrderPresenter mOrderPresenter;

    public static TakeDeliveryFragment newInstance(String tag, String param2) {
        TakeDeliveryFragment fragment = new TakeDeliveryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, tag);
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
        View view = inflater.inflate(R.layout.fragment_take_delivery, container, false);
        initView(view);
        return view;
    }*/

    @Override
    protected int initInflateView() {
        return R.layout.fragment_take_delivery;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        if (getArguments() != null) {
            mTagMake = getArguments().getString(ARG_PARAM1);
        }
        initView(view);
        initData();
        clearData();
        bindAdapterData();
        initXrfresh();
        mXfvContentTakeCom.startRefresh();
    }

    private void initXrfresh() {
        mXfvContentTakeCom.setPullLoadEnable(true);
        mXfvContentTakeCom.setAutoLoadMore(true);
        mXfvContentTakeCom.setPullRefreshEnable(true);
        mXfvContentTakeCom.setEmptyView(mIvContentEmpty);
        mAdapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mXfvContentTakeCom.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
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
        mOrderPresenter.requestOrder(mContext, getNowPage() + 1, mTagMake);
    }

    private void loadNewData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mOrderPresenter.requestOrder(mContext, 1, mTagMake);

    }

    private void bindAdapterData() {
        mAdapter = new MyOrderAdapter(mContext, mArrary);
        setGridLayoutManger(mContext,mRlvMyTakeContentCom,1);
        mRlvMyTakeContentCom.setAdapter(mAdapter);
        mAdapter.setClickListener(new MyOrderAdapter.onItemClickListener() {
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
        mAdapter.setClickListener(new MyOrderAdapter.onItemDelClickListener() {
            @Override
            public void onDelClickListener(MyOrderNewVo.DatasBean obj, int position) {

            }
        });
    }


    private void initData() {
        mOrderPresenter = new PerOrderPresenter();
        mOrderPresenter.BasePresenter(new PerOrderModel(), this);
    }


    private void initView(View view) {
        mContext = getActivity();
        mRlvMyTakeContentCom = (RecyclerView) view.findViewById(R.id.rlv_my_take_content_com);
        mIvContentEmpty = (ImageView) view.findViewById(R.id.iv_content_empty);
        mXfvContentTakeCom = (XRefreshView) view.findViewById(R.id.xfv_content_take_com);
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
    public void OrderSuccess(String con) {
        mXfvContentTakeCom.stopRefresh();
        isRefresh = false;
        Gson gson = new Gson();
        MyOrderNewVo orderVo = gson.fromJson(con, MyOrderNewVo.class);
        if (orderVo.getStatus().getCode() == 200) {
            List<MyOrderNewVo.DatasBean> datas = orderVo.getDatas();
            clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                mXfvContentTakeCom.setLoadComplete(true);
                mAdapter.notifyDataSetChanged();
                return;
            }
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                // 设置是否可以上拉加载
                mXfvContentTakeCom.setPullLoadEnable(true);
                mXfvContentTakeCom.setLoadComplete(false);
            } else
                mXfvContentTakeCom.setLoadComplete(true);

            if (orderVo.getTotal().getTotal() == mArrary.size())
                mXfvContentTakeCom.setLoadComplete(true);
            mAdapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            T.showToast(mContext, getStrWithId(mContext, R.string.net_error));
            L.e(orderVo.getStatus().getMessage());
        }
    }

    @Override
    public void OrderError(String con) {
        T_ERROR(mContext);
        isRefresh = false;
        mXfvContentTakeCom.stopRefresh();
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
                mXfvContentTakeCom.setLoadComplete(true);
                mAdapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXfvContentTakeCom.setLoadComplete(false);
                mXfvContentTakeCom.setPullLoadEnable(true);
            } else {
                mXfvContentTakeCom.setLoadComplete(true);
            }
            if (orderVo.getTotal().getTotal() == mArrary.size())
                mXfvContentTakeCom.setLoadComplete(true);
            mAdapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            mXfvContentTakeCom.setLoadComplete(false);
            T.showToast(mContext, getStrWithId(mContext, R.string.net_error));
            L.e(orderVo.getStatus().getMessage());
        }
    }

    @Override
    public void OrderErrorMore(String con) {
        isRefresh = false;
        mXfvContentTakeCom.setLoadComplete(false);
        T_ERROR(mContext);
    }

    @Override
    public void submitSuccess(String con) {

    }

    @Override
    public void submitError(String con) {

    }
}

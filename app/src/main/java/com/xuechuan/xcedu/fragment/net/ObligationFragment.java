package com.xuechuan.xcedu.fragment.net;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.xuechuan.xcedu.mvp.view.PayUtilView;
import com.xuechuan.xcedu.ui.home.YouZanActivity;
import com.xuechuan.xcedu.ui.me.DelectSuceessActivity;
import com.xuechuan.xcedu.ui.me.OrderInfomActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.PayUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.MyOrderNewVo;
import com.xuechuan.xcedu.vo.ResultVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: ObligationFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 待付款
 * @author: L-BackPacker
 * @date: 2018/5/26 13:12
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/26
 */


public class ObligationFragment extends BaseFragment implements PerOrderContract.View, PayUtilView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mStatus;
    private String mParam2;

    private RecyclerView mRlvMyOrderContentOb;
    private XRefreshView mXfvContentOrderOb;
    private Context mContext;
    private ImageView mIvContentEmpty;
    private MyOrderAdapter adapter;
    private List mArrary;
    private long lastRefreshTime;
    private boolean isRefresh;
    private PerOrderPresenter mPresenter;
    private AlertDialog mPayDialog;
    private PayUtil payUtil;
    private int mCancelPostion = -1;

    public static ObligationFragment newInstance(String param1, String param2) {
        ObligationFragment fragment = new ObligationFragment();
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

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_obligation, container, false);
//        initView(view);
//        return view;
//    }

    @Override
    protected int initInflateView() {
        return R.layout.fragment_obligation;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
        clearData();
        bindAdapterData();
        initXrfresh();
        mXfvContentOrderOb.startRefresh();

    }


    @Override
    public void onResume() {
        super.onResume();
        if (mXfvContentOrderOb != null)
            mXfvContentOrderOb.startRefresh();
    }

    private void initData() {
        mPresenter = new PerOrderPresenter();
        mPresenter.BasePresenter(new PerOrderModel(), this);
        payUtil = PayUtil.getInstance(mContext, getActivity());
        payUtil.init(this);
    }

    private void initView(View view) {
        mContext = getActivity();
        mRlvMyOrderContentOb = (RecyclerView) view.findViewById(R.id.rlv_my_order_content_ob);
        mXfvContentOrderOb = (XRefreshView) view.findViewById(R.id.xfv_content_order_ob);
        mIvContentEmpty = (ImageView) view.findViewById(R.id.iv_content_empty);
    }

    private void bindAdapterData() {
       setGridLayoutManger(mContext,mRlvMyOrderContentOb,1);
        adapter = new MyOrderAdapter(mContext, mArrary);
        mRlvMyOrderContentOb.setAdapter(adapter);

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
        adapter.setClickListener(new MyOrderAdapter.onItemCancelClickListener() {
            @Override
            public void onCancelClickListener(MyOrderNewVo.DatasBean obj, int position) {
                showData(obj, position);
            }
        });
        adapter.setClickListener(new MyOrderAdapter.onItemPayClickListener() {
            @Override
            public void onPayClickListener(MyOrderNewVo.DatasBean obj, int position) {
                showDialog(obj, position);
            }
        });
    }

    private void showData(final MyOrderNewVo.DatasBean obj, final int position) {
        DialogUtil dialogUtil = DialogUtil.getInstance();
        dialogUtil.showTitleDialog(mContext, getString(R.string.iscancelorder), getStrWithId(mContext, R.string.sure)
                , getStrWithId(mContext, R.string.cancel), true);
        dialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
            @Override
            public void onSureClickListener() {
                mCancelPostion = position;
                mPresenter.submitDelOrd(mContext, obj.getOrdernum(), DataMessageVo.CANCELORDER);
            }

            @Override
            public void onCancelClickListener() {
            }
        });

    }


    private void showDialog(final MyOrderNewVo.DatasBean data, int position) {
//        double discounts = data.getDiscounts();
//        double totalprice = data.getTotalprice();
//        final double v = totalprice - discounts;
//        final ArrayList<Integer> list = new ArrayList<>();
//        final List<OrderDetailVo> details = data.getDetails();
//        for (int i = 0; i < details.size(); i++) {
//            list.add(details.get(i).getProductid());
//        }
        DialogUtil instance = DialogUtil.getInstance();
        instance.showPayDialog(mContext);
        instance.setPayDialogClickListener(new DialogUtil.onItemPayDialogClickListener() {
            @Override
            public void onPayDialogClickListener(int obj, int position) {
                mPayDialog = DialogUtil.showDialog(mContext, "", getStrWithId(mContext, R.string.submit_loading));
                payUtil.showDiaolog(mPayDialog);
                if (obj == 1) {//微信
                    payUtil.SubmitfromPay(PayUtil.WEIXIN, data.getOrdernum());
                } else if (obj == 2) {//支付宝
                    payUtil.SubmitfromPay(PayUtil.ZFB, data.getOrdernum());
                }
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
        mXfvContentOrderOb.restoreLastRefreshTime(lastRefreshTime);
        mXfvContentOrderOb.setPullLoadEnable(true);
        mXfvContentOrderOb.setAutoLoadMore(true);
        mXfvContentOrderOb.setPullRefreshEnable(true);
        mXfvContentOrderOb.setEmptyView(mIvContentEmpty);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mXfvContentOrderOb.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
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
        L.d("all============" + con);
        mXfvContentOrderOb.stopRefresh();
        isRefresh = false;
        Gson gson = new Gson();
        MyOrderNewVo orderVo = gson.fromJson(con, MyOrderNewVo.class);
        if (orderVo.getStatus().getCode() == 200) {
            List<MyOrderNewVo.DatasBean> datas = orderVo.getDatas();
            clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                mXfvContentOrderOb.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
//            if ( mArrary.size() == orderVo.getTotal().getTotal()) {
//                mXfvContentOrderOb.setPullLoadEnable(true);
//                mXfvContentOrderOb.setLoadComplete(false);
//            } else {
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                // 设置是否可以上拉加载
                mXfvContentOrderOb.setPullLoadEnable(true);
                mXfvContentOrderOb.setLoadComplete(false);
            } else
                mXfvContentOrderOb.setLoadComplete(true);
//            mXfvContentOrderOb.setPullLoadEnable(true);
//            mXfvContentOrderOb.setLoadComplete(false);
//            }
            if (orderVo.getTotal().getTotal() == mArrary.size())
                mXfvContentOrderOb.setLoadComplete(true);
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
            L.e(orderVo.getStatus().getMessage());
        }

    }

    @Override
    public void OrderError(String con) {
        isRefresh = false;
        mXfvContentOrderOb.stopRefresh();
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
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
                mXfvContentOrderOb.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXfvContentOrderOb.setLoadComplete(false);
                mXfvContentOrderOb.setPullLoadEnable(true);
            } else {
                mXfvContentOrderOb.setLoadComplete(true);
            }
            if (orderVo.getTotal().getTotal() == mArrary.size())
                mXfvContentOrderOb.setLoadComplete(true);
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            L.e(orderVo.getStatus().getMessage());
            mXfvContentOrderOb.setLoadComplete(false);
            T.showToast(mContext, getStrWithId(mContext, R.string.net_error));
        }
    }

    @Override
    public void OrderErrorMore(String con) {
        isRefresh = false;
        mXfvContentOrderOb.setLoadComplete(false);
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
    }

    @Override
    public void submitSuccess(String con) {
        Gson gson = new Gson();
        ResultVo resultVo = gson.fromJson(con, ResultVo.class);
        if (resultVo.getStatus().getCode() == 200) {
            if (mCancelPostion != -1 && mCancelPostion >= 0) {
                mRlvMyOrderContentOb.setItemAnimator(new DefaultItemAnimator());
                mArrary.remove(mCancelPostion);
                adapter.notifyDataSetChanged();
                mCancelPostion = -1;
                DelectSuceessActivity.newInstance(mContext, DelectSuceessActivity.CANCELSUCCESS);
            }
//            T.showToast(mContext, getStrWithId(R.string.submit_success));
        } else {
            T.showToast(mContext, getStrWithId(mContext, R.string.net_error));
        }
    }

    @Override
    public void submitError(String con) {
        T.showToast(mContext, getStrWithId(mContext, R.string.net_error));
    }

    @Override
    public void PaySuccess(String type) {
        if (mXfvContentOrderOb != null) {
            mXfvContentOrderOb.startRefresh();
        }
    }

    @Override
    public void PayError(String type) {
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
    }

    @Override
    public void Dialog() {
        if (mPayDialog != null && mPayDialog.isShowing()) {
            mPayDialog.dismiss();
        }
    }
}

package com.xuechuan.xcedu.fragment.net;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.net.NetHomeAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.mvp.model.NetHomeModelImpl;
import com.xuechuan.xcedu.mvp.presenter.NetHomePresenter;
import com.xuechuan.xcedu.mvp.view.NetHomeView;
import com.xuechuan.xcedu.ui.net.NetAllBookActivity;
import com.xuechuan.xcedu.ui.net.NetBookDownActivity;
import com.xuechuan.xcedu.ui.net.NetBookMyInfomActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.CoursesBeanVo;
import com.xuechuan.xcedu.vo.NetHomeVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 网课
 * @author: L-BackPacker
 * @date: 2018/4/11 17:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/11
 */
public class NetFragment extends BaseFragment implements NetHomeView, View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private NetHomePresenter mPresenter;
    private Context mContext;
    private RecyclerView mRlvNetBuyList;
    private LinearLayout mLlNetBuy;
    private Button mBtnNetGoLook;
    private LinearLayout mLlNoBuy;
    private ImageView mIvNetMydown;
    private ImageView mIvNetAll;
    private int first = 0;
    /**
     * 是否第一次请求
     */
    private boolean isRequestHttp = false;


    public NetFragment() {
    }

    public static NetFragment newInstance(String param1, String param2) {
        NetFragment fragment = new NetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_net, null);
        initView(view);
        return view;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected int initInflateView() {
        return R.layout.fragment_net;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();

    }


    private void initData() {
        mPresenter = new NetHomePresenter(new NetHomeModelImpl(), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isVisible) return;
        if (first == 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPresenter.requestClassSandProducts(mContext);
                }
            }, 200);
//            mPresenter.requestClassSandProducts(mContext);
            ++first;
        } else {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    mPresenter.requestClassSandProducts(mContext);
                }
            }, 200);
        }

    }

    private boolean isVisible = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            if (isRequestHttp) return;
            isRequestHttp = true;
            if (mPresenter == null) return;
            mPresenter.requestClassSandProducts(mContext);
        } else {
            isVisible = false;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        isRequestHttp = false;
    }

    private void initView(View view) {
        mContext = getActivity();
        mRlvNetBuyList = (RecyclerView) view.findViewById(R.id.rlv_net_buy_list);
        mRlvNetBuyList.setOnClickListener(this);
        mLlNetBuy = (LinearLayout) view.findViewById(R.id.ll_net_buy);
        mLlNetBuy.setOnClickListener(this);
        mBtnNetGoLook = (Button) view.findViewById(R.id.btn_net_go_look);
        mBtnNetGoLook.setOnClickListener(this);
        mLlNoBuy = (LinearLayout) view.findViewById(R.id.ll_no_buy);
        mLlNoBuy.setOnClickListener(this);
        mIvNetMydown = (ImageView) view.findViewById(R.id.iv_net_mydown);
        mIvNetMydown.setOnClickListener(this);
        mIvNetAll = (ImageView) view.findViewById(R.id.iv_net_all);
        mIvNetAll.setOnClickListener(this);
    }

    @Override
    public void ClassListContSuccess(String msg) {
        Gson gson = new Gson();
        NetHomeVo homeVo = gson.fromJson(msg, NetHomeVo.class);
        if (homeVo.getStatus().getCode() == 200) {
            List<CoursesBeanVo> datas = homeVo.getDatas();
            if (datas == null || datas.isEmpty()) {//未购买
                mLlNetBuy.setVisibility(View.GONE);
                mLlNoBuy.setVisibility(View.VISIBLE);
            } else {//已有购买
                mLlNetBuy.setVisibility(View.VISIBLE);
                mLlNoBuy.setVisibility(View.GONE);
                bindBuyView(datas);

            }
        } else {
            T.showToast(mContext, getString(R.string.net_error));
            L.e(homeVo.getStatus().getMessage());
        }

    }

    private void bindBuyView(List<CoursesBeanVo> datas) {
        setGridLayoutManger(mContext, mRlvNetBuyList, 1);
        final NetHomeAdapter adapter = new NetHomeAdapter(mContext, datas);
        mRlvNetBuyList.setAdapter(adapter);
        adapter.setClickListener(new NetHomeAdapter.onItemClickListener() {
            @Override
            public void onClickListener(CoursesBeanVo vo, int position) {
                Intent intent = NetBookMyInfomActivity.newInstance(mContext, String.valueOf(vo.getId()));
                intent.putExtra(NetBookMyInfomActivity.CSTR_EXTRA_TITLE_STR, vo.getName());
                startActivity(intent);
            }
        });

    }


    @Override
    public void ClassListContError(String msg) {
        L.e("Class" + msg);
        T.showToast(mContext, getStrWithId(mContext, R.string.net_error));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_net_go_look:
                startActivity(new Intent(mContext, NetAllBookActivity.class));
                break;
            case R.id.iv_net_mydown:
//                Intent intent=new Intent(mContext,NetBookPlayActivity.class);
//                startActivity(intent);
                startActivity(new Intent(mContext, NetBookDownActivity.class));
                break;
            case R.id.iv_net_all:
                startActivity(new Intent(mContext, NetAllBookActivity.class));
                break;
            default:

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        first = 0;
    }
}

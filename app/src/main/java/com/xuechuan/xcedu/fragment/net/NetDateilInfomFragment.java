package com.xuechuan.xcedu.fragment.net;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.home.HomeNetInfomPopAdapter;
import com.xuechuan.xcedu.adapter.net.NetHomeInfomAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.event.ScrollEvent;
import com.xuechuan.xcedu.mvp.contract.HomeNetInfomContract;
import com.xuechuan.xcedu.mvp.model.HomeNetInfomModel;
import com.xuechuan.xcedu.mvp.presenter.HomeNetInfomPresenter;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.GiftVo;
import com.xuechuan.xcedu.vo.HomeInfomBean;
import com.xuechuan.xcedu.weight.CommonPopupWindow;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetDateilInfomFragment
 * @Package com.xuechuan.xcedu.fragment.net
 * @Description: 网课详情
 * @author: L-BackPacker
 * @date: 2018.11.30 下午 2:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.11.30
 */
public class NetDateilInfomFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private HomeInfomBean.DataBean dataX;
    private String mParam2;
    private RecyclerView mRlvHomeNetInfom;
    private Context mContext;
    private CommonPopupWindow mPopData;
    private LinearLayout mLlNetInfomRoot;
    private HomeNetInfomPresenter mHNIPresenter;
    private AlertDialog mShowDialog;
    private HomeInfomBean.DataBean mInfomData;
    private NetHomeInfomAdapter adapter;

    public static NetDateilInfomFragment newInstance(HomeInfomBean.DataBean dataX) {
        NetDateilInfomFragment fragment = new NetDateilInfomFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, dataX);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dataX = (HomeInfomBean.DataBean) getArguments().getSerializable(ARG_PARAM1);
        }
    }

/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_net_dateil_infom, container, false);
        initView(view);
        return view;
    }
*/

    @Override
    protected int initInflateView() {
        return R.layout.fragment_net_dateil_infom;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initRecyclerView();
    }


    private void initView(View view) {
        mContext = getActivity();
        mRlvHomeNetInfom = (RecyclerView) view.findViewById(R.id.rlv_home_net_infom);
        mLlNetInfomRoot = (LinearLayout) view.findViewById(R.id.ll_net_infom_root);
    }

    private void initRecyclerView() {
        setGridLayoutManger(mContext, mRlvHomeNetInfom, 1);
        adapter = new NetHomeInfomAdapter(mContext, new ArrayList<Object>());
        adapter.setAdapterView(dataX);
        mRlvHomeNetInfom.setAdapter(adapter);
        mRlvHomeNetInfom.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * SCROLL_STATE_IDLE：停止滑动时的状态
             SCROLL_STATE_DRAGGING：手指拖动时的状态
             SCROLL_STATE_SETTLING：惯性滑动时的状态（这是我的理解）
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        adapter.setOnZnPClickListener(new NetHomeInfomAdapter.OnZnPClickListener() {
            @Override
            public void onClickZnP(List<GiftVo> gifts) {//赠品
                if (gifts != null && !gifts.isEmpty())
                    show_Z_V_Layout(gifts);
            }
        });
    }
    /**
     * 赠品
     *
     * @param mData
     */
    private void show_Z_V_Layout(final List<GiftVo> mData) {

        //绑定数据
        mPopData = new CommonPopupWindow(mContext, R.layout.pop_item_net_catalogue, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
            private TextView mTvTitle;
            private RecyclerView mRlvNetInfomPopContent;
            private ImageView mIvNetInfomPopClose;

            @Override
            protected void initView() {
                View view = getContentView();
                mTvTitle = (TextView) view.findViewById(R.id.tv_net_pop_title);
                mRlvNetInfomPopContent = (RecyclerView) view.findViewById(R.id.rlv_net_infom_pop_content);
                mIvNetInfomPopClose = (ImageView) view.findViewById(R.id.iv_net_infom_pop_close);
                mTvTitle.setText(getStrWithId(R.string.premium));
                ininBindPopData();
            }

            //绑定数据
            private void ininBindPopData() {
                setGridLayoutManger(mContext, mRlvNetInfomPopContent, 1);
                HomeNetInfomPopAdapter adapter = new HomeNetInfomPopAdapter(mContext, mData);
                adapter.setShowType(HomeNetInfomPopAdapter.ZENGPINGD_TYPE);
                mRlvNetInfomPopContent.setAdapter(adapter);
            }

            @Override
            protected void initEvent() {
                mIvNetInfomPopClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPopData.getPopupWindow().dismiss();
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
                        setBackgroundAlpha(1f, mContext);
                    }
                });
            }
        };
        mPopData.showAtLocation(mLlNetInfomRoot, Gravity.BOTTOM, 0, 0);
        setBackgroundAlpha(0.5f, mContext);
    }

    /**
     * 设置背景颜色
     *
     * @param bgAlpha
     */
    public static void setBackgroundAlpha(float bgAlpha, Context mContext) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

}

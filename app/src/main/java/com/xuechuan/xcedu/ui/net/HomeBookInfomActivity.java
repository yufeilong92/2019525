package com.xuechuan.xcedu.ui.net;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.debug.log.E;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.home.HomeNetInfomPopAdapter;
import com.xuechuan.xcedu.adapter.home.MyTagPagerAdapter;
import com.xuechuan.xcedu.adapter.me.MyOrderIndicatorAdapter;
import com.xuechuan.xcedu.adapter.net.NetHomeInfomAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.event.ScrollEvent;
import com.xuechuan.xcedu.flyn.Eyes;
import com.xuechuan.xcedu.fragment.net.NetCatalogueTabFragment;
import com.xuechuan.xcedu.fragment.net.NetEvaluateFragment;
import com.xuechuan.xcedu.fragment.net.NetInfomTabFragment;
import com.xuechuan.xcedu.mvp.contract.HomeNetInfomContract;
import com.xuechuan.xcedu.mvp.model.HomeNetInfomModel;
import com.xuechuan.xcedu.mvp.presenter.HomeNetInfomPresenter;
import com.xuechuan.xcedu.net.view.EventListener;
import com.xuechuan.xcedu.utils.ArrayToListUtil;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.GiftVo;
import com.xuechuan.xcedu.vo.HomeInfomBean;
import com.xuechuan.xcedu.weight.CommonPopupWindow;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: HomeBookInfomActivity
 * @Package com.xuechuan.xcedu.ui.home
 * @Description: 首页网课详情
 * @author: L-BackPacker
 * @date: 2018.11.23 下午 5:16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.11.23
 */
public class HomeBookInfomActivity extends BaseActivity implements View.OnClickListener, HomeNetInfomContract.View  {

    private RecyclerView mRlvHomeNetInfom;
    private TextView mTvNetInfomBookAllprice;
    private TextView mTvNetInfomContactService;
    private Button mBtnNetInfomGoBuy;
    private Context mContext;
    private NetHomeInfomAdapter adapter;
    private MagicIndicator mMagicindicatorHomeInfom;
    private ViewPager mVpNetHomeInfomContent;
    private LinearLayout mLlTitleRoot;
    private TextView mTvTabNetInfom;
    public static final String CSTR_EXTRA_TITLE_STR_HOME = "home_title";
    private String mTitle;
    private CommonPopupWindow mPopData;
    private RelativeLayout mRlNetInfomRoot;
    private HomeNetInfomPresenter mHNIPresenter;


/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_book_infom);
        initView();
    }*/

    public static String PARAMT_KEY = "com.xuechuan.xcedu.ui.home.paramt_key";
    private String mProductid;
    private AlertDialog mShowDialog;
    private HomeInfomBean.DataBean mInfomData;

    public static Intent start_Intent(Context context, String productid) {
        Intent intent = new Intent(context, HomeBookInfomActivity.class);
        intent.putExtra(PARAMT_KEY, productid);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        if (getIntent() != null) {
            mTitle = getIntent().getStringExtra(CSTR_EXTRA_TITLE_STR_HOME);
            mProductid = getIntent().getStringExtra(PARAMT_KEY);
        }
        EventBus.getDefault().register(this);
        Eyes.translucentStatusBar(this, false);
        setContentView(R.layout.activity_home_book_infom);
        initView();
        mTvTabNetInfom.setText(mTitle);
        initRecyclerView();
        initData();

    }

    private void initData() {
        mHNIPresenter = new HomeNetInfomPresenter();
        mHNIPresenter.initModelView(new HomeNetInfomModel(), this);
        mHNIPresenter.requestNetDetail(mContext, mProductid);
        mShowDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));

    }


    @Override
    public void onHomeClick(View view) {
        if (mLlTitleRoot.getVisibility() == View.VISIBLE) {
            mLlTitleRoot.setVisibility(View.GONE);
            setViewAlap(mRlvHomeNetInfom, 0, 1.0f, 0);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setViewAlapView(mLlTitleRoot, 500, 1, 0);
                    setViewAlap(mRlvHomeNetInfom, 1000, 0, 1);
                    alpa = 0;
                }
            }, 300);
        } else
            super.onHomeClick(view);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (mLlTitleRoot.getVisibility() == View.VISIBLE) {
                mLlTitleRoot.setVisibility(View.GONE);
                setViewAlap(mRlvHomeNetInfom, 0, 1.0f, 0);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setViewAlapView(mLlTitleRoot, 300, 1, 0);
                        setViewAlap(mRlvHomeNetInfom, 800, 0, 1);
                        alpa = 0;
                    }
                }, 300);
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //初始化Tab网课界面
    private void initTitle(HomeInfomBean.DataBean dataX) {
        final ArrayList<String> titleLists = ArrayToListUtil.arraytoList(mContext, R.array.net_home_infom_title);
        mMagicindicatorHomeInfom.setBackgroundColor(Color.WHITE);
        mMagicindicatorHomeInfom.setBackgroundColor(Color.parseColor("#ffffff"));
        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setScrollPivotX(0.25f);
        if (titleLists.size() > 4) {
            commonNavigator.setAdjustMode(false);
        } else {
            commonNavigator.setAdjustMode(true);
        }
        List<Fragment> fragments = creartFragment(titleLists.size(), dataX);
        MyOrderIndicatorAdapter adapter = new MyOrderIndicatorAdapter(titleLists, mVpNetHomeInfomContent);
        mMagicindicatorHomeInfom.setNavigator(commonNavigator);
        commonNavigator.setAdapter(adapter);
        MyTagPagerAdapter tagPagerAdapter = new MyTagPagerAdapter(getSupportFragmentManager(), fragments);
        mVpNetHomeInfomContent.setAdapter(tagPagerAdapter);
        mVpNetHomeInfomContent.setOffscreenPageLimit(3);
        ViewPagerHelper.bind(mMagicindicatorHomeInfom, mVpNetHomeInfomContent);

    }

    private List<Fragment> creartFragment(int number, HomeInfomBean.DataBean dataX) {
        ArrayList<Fragment> fragments = new ArrayList<>();
        if (number < 2) {
            mMagicindicatorHomeInfom.setVisibility(View.GONE);
        } else {
            mMagicindicatorHomeInfom.setVisibility(View.VISIBLE);
        }
        //详情
        NetInfomTabFragment tabFragment = NetInfomTabFragment.newInstance(dataX.getDetailurl(), "");
        //目录
        NetCatalogueTabFragment catalogueTabFragment = NetCatalogueTabFragment.newInstance(dataX);
        //评价
        NetEvaluateFragment evaluateFragment = NetEvaluateFragment.newInstance(String.valueOf(dataX.getId()));
        fragments.add(tabFragment);
        fragments.add(catalogueTabFragment);
        fragments.add(evaluateFragment);
        return fragments;

    }

    private float alpa = 0;

    private void initRecyclerView() {
        setGridLayoutManger(mContext, mRlvHomeNetInfom, 1);
        adapter = new NetHomeInfomAdapter(mContext, new ArrayList<Object>());
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
                if (dy > 0)
                    alpa += dy;
                if (alpa > 300) {
                    mLlTitleRoot.setVisibility(View.VISIBLE);
                    setViewAlapView(mLlTitleRoot, 0, 1.0f, 0);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setViewAlap(mRlvHomeNetInfom, 300, 1, 0);
                            setViewAlapView(mLlTitleRoot, 800, 0, 1);
                            alpa = 0;
                        }
                    }, 300);
                }
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
     * 显示隐藏动画
     *
     * @param view
     * @param time
     * @param v
     * @param v1
     */
    private void setViewAlap(RecyclerView view, long time, float v, float v1) {
        //1 为显示 0 为隐藏
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", v, v1);
        alpha.setDuration(time);
        alpha.start();
    }

    private void setViewAlapView(LinearLayout view, long time, float v, float v1) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", v, v1);
        alpha.setDuration(time);
        alpha.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_net_infom_contact_service://联系客服
                Intent intent1 = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" + "400-963-8119"));
                startActivity(intent1);
                break;
            case R.id.btn_net_infom_go_buy://购买
                if (mInfomData != null) {
                    Intent intent = NetBuyActivity.newInstance(mContext, mInfomData.getPrice(), mInfomData.getId(),
                            mInfomData.getName(), mInfomData.getCoverimg(), mInfomData.getGifts());
                    startActivity(intent);
                }
                break;

        }
    }

    /**
     * 滑动隐藏布局显示
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void scrrolEvent(ScrollEvent event) {
        if (mLlTitleRoot.getVisibility() == View.VISIBLE) {
            mLlTitleRoot.setVisibility(View.GONE);
            setViewAlap(mRlvHomeNetInfom, 0, 1.0f, 0);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setViewAlapView(mLlTitleRoot, 300, 1, 0);
                    setViewAlap(mRlvHomeNetInfom, 800, 0, 1);
                    alpa = 0;
                }
            }, 300);
            if (mVpNetHomeInfomContent!=null){
                mVpNetHomeInfomContent.setCurrentItem(0);
            }
        }
    }


    private void initView() {
        mContext = this;
        mRlvHomeNetInfom = (RecyclerView) findViewById(R.id.rlv_home_net_infom);
        mTvNetInfomBookAllprice = (TextView) findViewById(R.id.tv_net_infom_book_allprice);
        mTvNetInfomContactService = (TextView) findViewById(R.id.tv_net_infom_contact_service);
        mBtnNetInfomGoBuy = (Button) findViewById(R.id.btn_net_infom_go_buy);
        mTvNetInfomContactService.setOnClickListener(this);
        mBtnNetInfomGoBuy.setOnClickListener(this);
        mMagicindicatorHomeInfom = (MagicIndicator) findViewById(R.id.magicindicator_home_infom);
        mMagicindicatorHomeInfom.setOnClickListener(this);
        mVpNetHomeInfomContent = (ViewPager) findViewById(R.id.vp_net_home_infom_content);
        mVpNetHomeInfomContent.setOnClickListener(this);
        mLlTitleRoot = (LinearLayout) findViewById(R.id.ll_title_root);
        mLlTitleRoot.setOnClickListener(this);
        mTvTabNetInfom = (TextView) findViewById(R.id.tv_tab_net_infom);
        mTvTabNetInfom.setSelected(true);
        mRlNetInfomRoot = (RelativeLayout) findViewById(R.id.rl_net_infom_root);
        mRlNetInfomRoot.setOnClickListener(this);
    }


    /**
     * 赠品
     *
     * @param mData
     */
    private void show_Z_V_Layout(final List<GiftVo> mData) {

        mPopData = new CommonPopupWindow(this, R.layout.pop_item_net_catalogue, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
            private TextView mTvTitle;
            private RecyclerView mRlvNetInfomPopContent;
            private ImageView mIvNetInfomPopClose;

            @Override
            protected void initView() {
                View view = getContentView();
                mTvTitle = (TextView) view.findViewById(R.id.tv_net_pop_title);
                mRlvNetInfomPopContent = (RecyclerView) view.findViewById(R.id.rlv_net_infom_pop_content);
                mIvNetInfomPopClose = (ImageView) view.findViewById(R.id.iv_net_infom_pop_close);
                mTvTitle.setText(getStringWithId(R.string.premium));
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
                        setBackgroundAlpha(1f, HomeBookInfomActivity.this);
                    }
                });
            }
        };
        mPopData.showAtLocation(mRlNetInfomRoot, Gravity.BOTTOM, 0, 0);
        setBackgroundAlpha(0.5f, HomeBookInfomActivity.this);
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

    @Override
    public void NetDetailSuccess(String success) {
        dismissDialog(mShowDialog);
        HomeInfomBean bean = Utils.getGosnT(success, HomeInfomBean.class);
        if (bean.getStatus().getCode() == 200) {
            HomeInfomBean.DataBean dataX = bean.getData();
            mInfomData = dataX;
            if (dataX == null) {
                mRlNetInfomRoot.setVisibility(View.GONE);
                return;
            } else {
                mRlNetInfomRoot.setVisibility(View.VISIBLE);
            }
            if (adapter != null) {
                adapter.setAdapterView(dataX);
                adapter.notifyDataSetChanged();
            }
            initTitle(dataX);
            mTvNetInfomBookAllprice.setText(String.valueOf(dataX.getPrice()));
        } else {
            T_ERROR(mContext);
        }
    }

    @Override
    public void NetDeatailError(String msg) {
        dismissDialog(mShowDialog);
        T_ERROR(mContext);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }


}

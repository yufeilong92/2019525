package com.xuechuan.xcedu.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.andview.refreshview.XRefreshView;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.xuechuan.xcedu.HomeActivity;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.home.HomsAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.BaseVo;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.ChangeInfoContract;
import com.xuechuan.xcedu.mvp.contract.HomeContract;
import com.xuechuan.xcedu.mvp.contract.QRLoginContract;
import com.xuechuan.xcedu.mvp.model.ChangeInfoModel;
import com.xuechuan.xcedu.mvp.model.ExchangeModelImpl;
import com.xuechuan.xcedu.mvp.model.HomeModel;
import com.xuechuan.xcedu.mvp.model.QRLoginModel;
import com.xuechuan.xcedu.mvp.presenter.ChangeInfoPresenter;
import com.xuechuan.xcedu.mvp.presenter.ExchangePresenter;
import com.xuechuan.xcedu.mvp.presenter.HomePresenter;
import com.xuechuan.xcedu.mvp.presenter.QRLoginPresenter;
import com.xuechuan.xcedu.mvp.view.ExchangeView;
import com.xuechuan.xcedu.ui.ScanConfirmActivity;
import com.xuechuan.xcedu.ui.SecondActivity;
import com.xuechuan.xcedu.ui.home.AddressShowActivity;
import com.xuechuan.xcedu.ui.home.SearchActivity;
import com.xuechuan.xcedu.ui.me.ExchangeActivity;
import com.xuechuan.xcedu.ui.net.HomeNetActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.PushXmlUtil;
import com.xuechuan.xcedu.utils.QRCodeUtil;
import com.xuechuan.xcedu.utils.ScanCodeUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.GenuienVo;
import com.xuechuan.xcedu.vo.HomePageVo;
import com.xuechuan.xcedu.vo.ResultVo;
import com.xuechuan.xcedu.weight.AddressTextView;
import com.xuechuan.xcedu.weight.XRefreshViewLayout;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: HomesFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 首页碎片
 * @author: L-BackPacker
 * @date: 2018.10.16 下午 2:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.10.16
 */
public class HomesFragment extends BaseFragment implements HomeContract.View, ExchangeView, QRCodeUtil.qrCodeView, QRLoginContract.View, ChangeInfoContract.View, View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private LinearLayout mLiSearch;
    private RecyclerView mRlvContentContent;
    private XRefreshViewLayout mXfvHomeContent;
    private Context mContext;

    /**
     * 请求结果码
     */
    private int REQUESTCODE = 1001;
    /**
     * 请求回调码
     */
    public static final int REQUESTRESULT = 1002;
    /**
     * 省份
     */
    public static final String STR_INT_PROVINCE = "province";
    /**
     * code码
     */
    public static String STR_INT_CODE = "code";
    /**
     * 位标
     */
    public static String STR_INT_POSITION = "position";
    /**
     * 地址
     */
    private String provice;
    /**
     * 扫描确认
     */
    public static String mCode;

    private long lastRefreshTime;
    private HomePresenter mPresenter;
    private LocationClient mLocationClient;
    private HomsAdapter adapter;

    private HomePageVo homePageVo;
    private ImageView mIvSearchQrcode;
    private LinearLayout mLlHomeRoot;
    private QRCodeUtil mQrUtil;
    private ExchangePresenter mExchangPresenter;
    private AlertDialog mAlertDialog;
    private QRLoginPresenter mQrPresenter;
    private ChangeInfoPresenter mAddValuePresenter;
    private ScanCodeUtil mScanCode;
    private AddressTextView mTvHomeAddressView;
    private LinearLayout mLlHomeSerach;
    private ImageView mIvHomeScan;
    private LinearLayout mLlSeachLayout;
    private RelativeLayout mRlBg;
    private ImageView mIvNavIconPtr;


    public HomesFragment() {
    }

    public static HomesFragment newInstance(String param1, String param2) {
        HomesFragment fragment = new HomesFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
/*

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homes, container, false);
        initView(view);
        return view;
    }
*/

    @Override
    public void onStop() {
        super.onStop();
        if (mLocationClient != null)
            mLocationClient.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mLocationClient != null) {
            mLocationClient.restart();
        }
    }

    @Override
    protected int initInflateView() {
        return R.layout.fragment_homes;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
        initAdapter();
        initXrfresh();
        requestData();
        initBaiduLocation();

    }


    private void initAdapter() {
        adapter = new HomsAdapter(mContext, homePageVo, code);
        final GridLayoutManager gridLayoutManger = getGridLayoutManger(mContext, mRlvContentContent, 1);
        mRlvContentContent.setAdapter(adapter);
        mRlvContentContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                float alphaFloat = getAlphaFloat(getScroolY(gridLayoutManger));
                if (alphaFloat > 0.5) {
                    ((HomeActivity) getActivity()).showBar(true);
                    Drawable drawable = mContext.getResources().getDrawable(R.mipmap.erweima);
                    mIvHomeScan.setImageDrawable(drawable);
                    Drawable drawable1 = mContext.getResources().getDrawable(R.mipmap.ic_home_location);
                    mIvNavIconPtr.setImageDrawable(drawable1);
                    int color = mContext.getResources().getColor(R.color.text_title_color);
                    mTvHomeAddressView.setTextColor(color);
                } else if (alphaFloat < 0.5) {
                    ((HomeActivity) getActivity()).showBar(false);
                    Drawable drawable = mContext.getResources().getDrawable(R.mipmap.nav_icon_saoma);
                    mIvHomeScan.setImageDrawable(drawable);
                    Drawable drawable1 = mContext.getResources().getDrawable(R.mipmap.nav_icon_ptr);
                    mIvNavIconPtr.setImageDrawable(drawable1);
                    int color = mContext.getResources().getColor(R.color.white);
                    mTvHomeAddressView.setTextColor(color);

                }
                mRlBg.setAlpha(alphaFloat);
            }
        });
        adapter.setOnScanClickListener(new HomsAdapter.OnScanClickListener() {
            @Override
            public void onClickScan() {//扫码
                Intent star = new Intent(mContext, SecondActivity.class);
                startActivityForResult(star, QRCodeUtil.REQUEST_CODE);
            }
        });
        adapter.setOnAddressClickListener(new HomsAdapter.OnAddressClickListener() {
            @Override
            public void onClickAddress(String address) {//地址
                Intent intent = AddressShowActivity.newInstance(mContext, address, AddressShowActivity.TYPEHOME);
                intent.putExtra(AddressShowActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.location));
                startActivityForResult(intent, REQUESTCODE);
            }
        });
        adapter.setOnSearchClickListener(new HomsAdapter.OnSearchClickListener() {
            @Override
            public void onClickSearch() {//搜索
                Intent searchIntent = new Intent(mContext, SearchActivity.class);
                startActivity(searchIntent);
            }
        });
        adapter.setOnNetMoreClickListener(new HomsAdapter.OnNetMoreClickListener() {
            @Override
            public void onClickNetMore() {//网课更多
                Intent intent = new Intent(mContext, HomeNetActivity.class);
                intent.putExtra(HomeNetActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(R.string.home_net));
                startActivity(intent);
            }
        });
    }

    /**
     * 获取滑动距离
     *
     * @param manager
     * @return
     */
    private int getScroolY(GridLayoutManager manager) {
        View c = mRlvContentContent.getChildAt(0);
        if (c == null)
            return 0;
        int i = manager.findFirstVisibleItemPosition();
        int top = c.getTop();
        /**
         * 声明一下，这里测试得到的top值始终是RecyclerView条目中显示的第一条距离顶部的距离，
         * 而这个在坐标中的表示是一个负数，所以需要对其取一个绝对值
         */
        return i * c.getHeight() + Math.abs(top);
    }

    /**
     * 更具相应位子显示相应的透明度
     *
     * @param dis
     * @return
     */
    private float getAlphaFloat(int dis) {
        int step = 200;
        if (dis == 0) {
            return 0.0f;
        }
        if (dis < step) {
            return (float) (dis * (1.0 / step));
        } else {
            return 1.0f;
        }
    }

    private void initBaiduLocation() {
        mLocationClient = new LocationClient(getActivity());
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(0);
        option.setOpenGps(true);
        option.setLocationNotify(false);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setWifiCacheTimeOut(5 * 60 * 1000);
        option.setEnableSimulateGps(false);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(locationListener);
        mLocationClient.start();

    }

    private String code;
    private BDAbstractLocationListener locationListener = new BDAbstractLocationListener() {


        @Override
        public void onReceiveLocation(BDLocation location) {
            if (StringUtil.isEmpty(location.getProvince())) {
                mLocationClient.restart();
                return;
            }
            String province = location.getProvince();    //获取省份
            setBannerAddressData(province);
            mTvHomeAddressView.setText(province);
            code = PushXmlUtil.getInstance().getLocationCode(mContext, province);
            if (!StringUtil.isEmpty(code)) {
                requestData();
            }
        }
    };

    private void setBannerAddressData(String provice) {
        if (adapter != null) {
            adapter.setAddressContent(provice);
        }
    }

    private void initData() {
        mScanCode = ScanCodeUtil.getInstance(getActivity());
        mQrPresenter = new QRLoginPresenter();
        mQrPresenter.initModelView(new QRLoginModel(), this);
        mQrUtil = QRCodeUtil.getInstance(mContext);
        mQrUtil.setQrCodeView(this);
        mExchangPresenter = new ExchangePresenter(new ExchangeModelImpl(), this);
        mAddValuePresenter = new ChangeInfoPresenter();
        mAddValuePresenter.initModelView(new ChangeInfoModel(), this);
        mPresenter = new HomePresenter();
        mPresenter.initModelView(new HomeModel(), this);
        String code = PushXmlUtil.getInstance().getLocationCode(mContext, "全国");
        mPresenter.requestHomePager(mContext, code);

    }

    private void initXrfresh() {
        mXfvHomeContent.restoreLastRefreshTime(lastRefreshTime);
        mXfvHomeContent.setPullLoadEnable(false);
        mXfvHomeContent.setAutoLoadMore(false);
        mXfvHomeContent.setPullRefreshEnable(true);
        mXfvHomeContent.enableReleaseToLoadMore(false);
        mXfvHomeContent.enableRecyclerViewPullUp(true);
        mXfvHomeContent.enablePullUpWhenLoadCompleted(true);
        mXfvHomeContent.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                requestData();
            }

            @Override
            public void onHeaderMove(double offset, int offsetY) {
                super.onHeaderMove(offset, offsetY);
                if (offsetY > 5) {
                    mRlBg.setVisibility(View.GONE);
                    mLlSeachLayout.setVisibility(View.GONE);
                } else {
                    mRlBg.setVisibility(View.VISIBLE);
                    mLlSeachLayout.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    private void requestData() {
        mPresenter.requestHomePager(mContext, code);
    }

    private void initView(View view) {
        mContext = getActivity();
        mRlvContentContent = (RecyclerView) view.findViewById(R.id.rlv_content_content);
        mXfvHomeContent = (XRefreshViewLayout) view.findViewById(R.id.xfv_home_content);

        mTvHomeAddressView = (AddressTextView) view.findViewById(R.id.tv_home_address_view);
        mTvHomeAddressView.setOnClickListener(this);
        mLlHomeSerach = (LinearLayout) view.findViewById(R.id.ll_home_serach);
        mLlHomeSerach.setOnClickListener(this);
        mIvHomeScan = (ImageView) view.findViewById(R.id.iv_home_scan);
        mIvHomeScan.setOnClickListener(this);
        mLlSeachLayout = (LinearLayout) view.findViewById(R.id.ll_seach_layout);
        mLlSeachLayout.setOnClickListener(this);
        mRlBg = (RelativeLayout) view.findViewById(R.id.rl_bg);
        mRlBg.setOnClickListener(this);
        mIvNavIconPtr = (ImageView) view.findViewById(R.id.iv_nav_icon_ptr);
        mIvNavIconPtr.setOnClickListener(this);
    }


    @Override
    public void Success(String com) {
        mXfvHomeContent.stopRefresh();
        mXfvHomeContent.restoreLastRefreshTime(mXfvHomeContent.getLastRefreshTime());
        Gson gson = new Gson();
        HomePageVo homePageVo = gson.fromJson(com, HomePageVo.class);
        BaseVo.StatusBean status = homePageVo.getStatus();
        if (status.getCode() == 200) {//成功
            int number = 4;
            boolean polyvlice = false, book = false, net = false;
            HomePageVo.DataBean data = homePageVo.getData();
            //直播
            if (data.getPolyvlive() != null && !data.getPolyvlive().isEmpty()) {
                number += 1;
                polyvlice = true;
            }
            //教辅
            if (data.getBook() != null && !data.getBook().isEmpty()) {
                number += 1;
                book = true;
            }
            //网课
            if (data.getClassX() != null && !data.getClassX().isEmpty()) {
                number += 1;
                net = true;

            }
            adapter.doChangerItem(polyvlice, book, net);
            adapter.doChangerItem(number);
            adapter.setData(homePageVo, code);
            adapter.notifyDataSetChanged();
        } else {
            T.showToast(mContext, getStrWithId(mContext, R.string.net_error));
        }
    }

    @Override
    public void Error(String msg) {
        mXfvHomeContent.stopRefresh();
        if (adapter != null)
            adapter.notifyDataSetChanged();
        T.showToast(mContext, getStrWithId(mContext, R.string.net_error));
    }

    /**
     * 省份
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE && resultCode == REQUESTRESULT) {
            if (data != null) {
                provice = data.getStringExtra(STR_INT_PROVINCE);
                mTvHomeAddressView.setText(provice);
//                setBannerAddressData(provice);
                code = PushXmlUtil.getInstance().getLocationCode(mContext, provice);
                if (!StringUtil.isEmpty(code)) {
                    if (mXfvHomeContent != null) {
                        mXfvHomeContent.startRefresh();
                    }

                }
            }
        }
        mQrUtil.doResueltIntent(requestCode, resultCode, data);
    }

    private boolean isVisible = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
        } else {
            isVisible = false;
        }
    }

    /**
     * 兑换扫码
     *
     * @param code
     */
    @Override
    public void codeResultSuccess(String code) {
        mScanCode.doIntent(mContext, DataMessageVo.QRTAG, code);
    }

    /**
     * 登录
     *
     * @param code
     */
    @Override
    public void loginResultSuccess(String code) {
        mScanCode.doIntent(mContext, DataMessageVo.LOGINTAG, code);
    }

    @Override
    public void addValueResultSuccess(String code) {
        mScanCode.doIntent(mContext, DataMessageVo.ADDVALUEHTTP, code);
    }

    /**
     * 兑换成功
     *
     * @param com
     */
    @Override
    public void ExchangeSuccess(String com) {
        dialogDimiss(mAlertDialog);
        Gson gson = new Gson();
        GenuienVo vo = gson.fromJson(com, GenuienVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData().getStatusX() == 1) {
                GenuienVo.DataBean data = vo.getData();
                Intent intent = ExchangeActivity.newInstance(mContext, true, data.getQuerynum(), data.isHaveexchange()
                        , mCode, data.getExchangeinfo());
                startActivity(intent);
                mCode = null;

            } else {
                Intent intent = ExchangeActivity.newInstance(mContext, false, 0, false, null, null);
                startActivity(intent);
                mCode = null;
            }

        } else {
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
            L.e(vo.getStatus().getMessage());
        }
    }

    /**
     * 兑换失败
     *
     * @param com
     */
    @Override
    public void ExchangeError(String com) {
        dialogDimiss(mAlertDialog);
        T_ERROR(mContext);
    }

    @Override
    public void QrloginSuccess(String content) {
        dialogDimiss(mAlertDialog);
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(content, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData().getStatusX() == 1) {
                Intent intent = ScanConfirmActivity.newInstance(mContext, mCode);
                startActivity(intent);
                mCode = null;
            } else {
                T_ERROR(mContext, vo.getData().getMessage());
            }

        } else {
            T_ERROR(mContext);
        }
    }

    @Override
    public void QrloginError(String msg) {
        dialogDimiss(mAlertDialog);
        T_ERROR(mContext);

    }

    @Override
    public void ChangeInfomSuccess(String result) {
        dialogDimiss(mAlertDialog);
        Gson gson = new Gson();
        GenuienVo vo = gson.fromJson(result, GenuienVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData().getStatusX() == 1) {
                GenuienVo.DataBean data = vo.getData();
                Intent intent = ExchangeActivity.newInstance(mContext, true, data.getQuerynum(), data.isHaveexchange()
                        , mCode, data.getExchangeinfo(), DataMessageVo.ADDVALUEHTTP);
                intent.putExtra(ExchangeActivity.CSTR_EXTRA_TITLE_STR, "兑换增值服务");
                mContext.startActivity(intent);
                mCode = null;

            } else {
                Intent intent = ExchangeActivity.newInstance(mContext, false, 0, false, null, null, DataMessageVo.ADDVALUEHTTP);
                intent.putExtra(ExchangeActivity.CSTR_EXTRA_TITLE_STR, "兑换增值服务");
                mContext.startActivity(intent);
                mCode = null;
            }

        } else {
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
            L.e(vo.getStatus().getMessage());
        }
    }

    @Override
    public void ChangeInfomError(String msg) {
        dialogDimiss(mAlertDialog);
        T_ERROR(mContext);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_home_address_view:
                String address = mTvHomeAddressView.getText().toString().trim();
                Intent intent = AddressShowActivity.newInstance(mContext, address, AddressShowActivity.TYPEHOME);
                intent.putExtra(AddressShowActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.location));
                startActivityForResult(intent, REQUESTCODE);
                break;
            case R.id.ll_home_serach:
                Intent searchIntent = new Intent(mContext, SearchActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.iv_home_scan:
                Intent star = new Intent(mContext, SecondActivity.class);
                startActivityForResult(star, QRCodeUtil.REQUEST_CODE);
                break;
            default:

        }
    }
}

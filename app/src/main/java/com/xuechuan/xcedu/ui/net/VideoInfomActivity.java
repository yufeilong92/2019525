package com.xuechuan.xcedu.ui.net;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.home.HomeNetInfomPopAdapter;
import com.xuechuan.xcedu.adapter.net.NetCommentAdapter;
import com.xuechuan.xcedu.adapter.net.NetHomeInfomAdapter;
import com.xuechuan.xcedu.adapter.net.NetTableAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.BaseVo;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.event.NetPlayEvent;
import com.xuechuan.xcedu.flyn.Eyes;
import com.xuechuan.xcedu.mvp.contract.HomeNetInfomContract;
import com.xuechuan.xcedu.mvp.contract.NetCommentContract;
import com.xuechuan.xcedu.mvp.model.HomeNetInfomModel;
import com.xuechuan.xcedu.mvp.model.NetCommentModel;
import com.xuechuan.xcedu.mvp.presenter.HomeNetInfomPresenter;
import com.xuechuan.xcedu.mvp.presenter.NetCommentPresenter;
import com.xuechuan.xcedu.ui.EvalueTwoActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.ChaptersBeanVo;
import com.xuechuan.xcedu.vo.EvalueNewVo;
import com.xuechuan.xcedu.vo.GiftVo;
import com.xuechuan.xcedu.vo.HomeInfomBean;
import com.xuechuan.xcedu.vo.RecyclerSelectVoOne;
import com.xuechuan.xcedu.vo.VideosBeanVo;
import com.xuechuan.xcedu.weight.CommonPopupWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: VideoInfomActivity
 * @Package com.xuechuan.xcedu.ui.net
 * @Description: 滑动见
 * @author: L-BackPacker
 * @date: 2018.11.30 下午 3:25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.11.30
 */
public class VideoInfomActivity extends BaseActivity implements HomeNetInfomContract.View, View.OnClickListener, NetCommentContract.View {
    private static String TAG = "【" + VideoInfomActivity.class + "】==";
    private Context mContext;
    private AlertDialog mShowDialog;
    private HomeNetInfomPresenter mHNIPresenter;
    public static final String CSTR_EXTRA_TITLE_STR_HOME = "home_title";
    public static String PARAMT_KEY = "com.xuechuan.xcedu.ui.home.paramt_key";
    private String mProductid;
    private HomeInfomBean.DataBean mInfomData;
    private RecyclerView mRlvHomeNetOneInfom;
    private LinearLayout mLlNetInfomOneRoot;
    private TextView mTvNetInfomTwoCatalogue;
    private Button mBtnHomeNetInfomTwoCatalogueSwitch;
    private RecyclerView mRlvNetInfomTwoCatalogueContent;
    private ImageView mIvNetEmptyContent;
    private LinearLayout mLlNetInfomTwoRoot;
    private RecyclerView mRlvNetContent;
    private ImageView mIvNetEmpty;
    private XRefreshView mXrfvContent;
    private LinearLayout mLlNetInfomThreeRoot;
    private TextView mTvNetInfomBookAllprice;
    private TextView mTvNetInfomContactService;
    private Button mBtnNetInfomGoBuy;
    private RelativeLayout mRootAll;
    private NetHomeInfomAdapter mOneAdapter;
    private CommonPopupWindow mOnePopData;
    /**
     * 是否显示标题
     */
    private boolean isShowTitle = false;
    private List<ChaptersBeanVo> mTwoChaptersList;
    private NetTableAdapter mTwoAdapter;
    private CommonPopupWindow mTwoPopData;
    /**
     * 判断用户是点击年份(第二个布局)
     */
    private boolean isSelectYear = false;
    /**
     * 用于判断是否重复点击 及用户选中第几个
     */
    private int selectNetId = -1;
    /**
     * 储存目录数据
     */
    private HashMap<Integer, HomeInfomBean.DataBean> mMuHashMap;
    private NetCommentPresenter mThreePresenter;
    private ArrayList mThreeArrary;
    /**
     * 第三试图
     */
    private boolean isRefresh = false;
    private long lastRefreshTime;
    private NetCommentAdapter mThreeAdapter;
    private TextView mTvTabAllnetInfom;
    private RadioButton mRdbNetInfomOne;
    private RadioButton mRdbNetMuluTwo;
    private RadioButton mRdbNetEvaluateThree;
    private LinearLayout mLiTitleTabContent;
    private String mTitle;
    /**
     * 用于第三个试图用户选中课程切换评价
     */
    private String mSelectProductid;
    /*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_infom);
        initView();
    }
*/

    public static Intent start_Intent(Context context, String productid) {
        Intent intent = new Intent(context, VideoInfomActivity.class);
        intent.putExtra(PARAMT_KEY, productid);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        if (getIntent() != null) {
            mProductid = getIntent().getStringExtra(PARAMT_KEY);
            mTitle = getIntent().getStringExtra(CSTR_EXTRA_TITLE_STR_HOME);
        }
        EventBus.getDefault().register(this);
        Eyes.translucentStatusBar(this, false);
        setContentView(R.layout.activity_video_infom);
        initView();
        mTvTabAllnetInfom.setText(mTitle);
        initRequstView();
        initViewEvent();
    }


    private void initView() {
        mContext = this;

        mRlvHomeNetOneInfom = (RecyclerView) findViewById(R.id.rlv_home_net_one_infom);
        mRlvHomeNetOneInfom.setOnClickListener(this);
        mLlNetInfomOneRoot = (LinearLayout) findViewById(R.id.ll_net_infom_one_root);
        mLlNetInfomOneRoot.setOnClickListener(this);
        mTvNetInfomTwoCatalogue = (TextView) findViewById(R.id.tv_net_infom_two_catalogue);
        mTvNetInfomTwoCatalogue.setOnClickListener(this);
        mBtnHomeNetInfomTwoCatalogueSwitch = (Button) findViewById(R.id.btn_home_net_infom_two_catalogue_switch);
        mBtnHomeNetInfomTwoCatalogueSwitch.setOnClickListener(this);
        mRlvNetInfomTwoCatalogueContent = (RecyclerView) findViewById(R.id.rlv_net_infom_two_catalogue_content);
        mRlvNetInfomTwoCatalogueContent.setOnClickListener(this);
        mIvNetEmptyContent = (ImageView) findViewById(R.id.iv_net_empty_content);
        mIvNetEmptyContent.setOnClickListener(this);
        mLlNetInfomTwoRoot = (LinearLayout) findViewById(R.id.ll_net_infom_two_root);
        mLlNetInfomTwoRoot.setOnClickListener(this);
        mRlvNetContent = (RecyclerView) findViewById(R.id.rlv_net_content);
        mRlvNetContent.setOnClickListener(this);
        mIvNetEmpty = (ImageView) findViewById(R.id.iv_net_empty);
        mIvNetEmpty.setOnClickListener(this);
        mXrfvContent = (XRefreshView) findViewById(R.id.xrfv_content);
        mXrfvContent.setOnClickListener(this);
        mLlNetInfomThreeRoot = (LinearLayout) findViewById(R.id.ll_net_infom_three_root);
        mLlNetInfomThreeRoot.setOnClickListener(this);
        mTvNetInfomBookAllprice = (TextView) findViewById(R.id.tv_net_infom_book_allprice);
        mTvNetInfomBookAllprice.setOnClickListener(this);
        mTvNetInfomContactService = (TextView) findViewById(R.id.tv_net_infom_contact_service);
        mTvNetInfomContactService.setOnClickListener(this);
        mBtnNetInfomGoBuy = (Button) findViewById(R.id.btn_net_infom_go_buy);
        mBtnNetInfomGoBuy.setOnClickListener(this);
        mRootAll = (RelativeLayout) findViewById(R.id.root_all);
        mRootAll.setOnClickListener(this);
        mTvTabAllnetInfom = (TextView) findViewById(R.id.tv_tab_allnet_infom);
        mTvTabAllnetInfom.setOnClickListener(this);
        mRdbNetInfomOne = (RadioButton) findViewById(R.id.rdb_net_infom_one);
        mRdbNetInfomOne.setOnClickListener(this);
        mRdbNetMuluTwo = (RadioButton) findViewById(R.id.rdb_net_mulu_two);
        mRdbNetMuluTwo.setOnClickListener(this);
        mRdbNetEvaluateThree = (RadioButton) findViewById(R.id.rdb_net_evaluate_three);
        mRdbNetEvaluateThree.setOnClickListener(this);
        mLiTitleTabContent = (LinearLayout) findViewById(R.id.li_title_tab_content);
        mLiTitleTabContent.setOnClickListener(this);
        mRootAll.setVisibility(View.GONE);
        mIvNetEmptyContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_net_infom_go_buy:
                if (mInfomData != null) {
                    Intent intent = NetBuyActivity.newInstance(mContext, mInfomData.getPrice(), mInfomData.getId(),
                            mInfomData.getName(), mInfomData.getCoverimg(), mInfomData.getGifts());
                    startActivity(intent);
                }
                break;
            case R.id.tv_net_infom_contact_service://联系客服
                Intent intent1 = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" + "400-963-8119"));
                startActivity(intent1);
                break;
            case R.id.btn_home_net_infom_two_catalogue_switch:
                if (mInfomData == null) return;
                if (mInfomData.isIspackage()) {
                    addHashMap();
                } else {
                    T.showToast(mContext, getStringWithId(R.string.other_catalogue));
                }
                break;
        }
    }


    @Override
    public void NetDetailSuccess(String success) {
        dismissDialog(mShowDialog);
        HomeInfomBean bean = Utils.getGosnT(success, HomeInfomBean.class);
        if (bean.getStatus().getCode() == 200) {
            HomeInfomBean.DataBean dataX = bean.getData();
            mInfomData = dataX;
            if (dataX == null) {
                mRootAll.setVisibility(View.GONE);
                mIvNetEmptyContent.setVisibility(View.VISIBLE);
                return;
            } else {
                mRootAll.setVisibility(View.VISIBLE);
                mIvNetEmptyContent.setVisibility(View.GONE);
            }
            mTvNetInfomBookAllprice.setText(String.valueOf(dataX.getPrice()));
            bindViewData(dataX);
        } else {
            T_ERROR(mContext);
        }

    }

    /**
     * 开始绑定数据
     *
     * @param dataX
     */
    private void bindViewData(HomeInfomBean.DataBean dataX) {

        bindViewOne(dataX);
        bindViewTwo(dataX);
        bindViewThree();

    }

    private void initViewEvent() {
        setRadioButtonListener(mRdbNetInfomOne, 1);
        setRadioButtonListener(mRdbNetMuluTwo, 2);
        setRadioButtonListener(mRdbNetEvaluateThree, 3);

    }

    /**
     * 设置radiobutton按钮监听事件
     *
     * @param mRadioButton
     * @param number       第几个按钮 1详情，2目录，3评价
     */
    private void setRadioButtonListener(final RadioButton mRadioButton, final int number) {
        mRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    switch (number) {
                        case 1:
                            setRadioButtonSelecet(true, false, false);
                            break;
                        case 2:
                            setRadioButtonSelecet(false, true, false);
                            break;
                        case 3:
                            setRadioButtonSelecet(false, false, true);
                            break;
                    }
            }
        });
    }

    /**
     * @param infom    详情
     * @param catager  目录
     * @param evaluate 评价
     */
    private void setRadioButtonSelecet(boolean infom, boolean catager, boolean evaluate) {
        mRdbNetInfomOne.setChecked(infom);
        mRdbNetMuluTwo.setChecked(catager);
        mRdbNetEvaluateThree.setChecked(evaluate);
        mLlNetInfomOneRoot.setVisibility(infom ? View.VISIBLE : View.GONE);
        mLlNetInfomTwoRoot.setVisibility(catager ? View.VISIBLE : View.GONE);
        mLlNetInfomThreeRoot.setVisibility(evaluate ? View.VISIBLE : View.GONE);
        //查看目录是否选中
        if (catager) {
            showMule();
        }
    }

    /**
     * 显示目录
     */
    private void showMule() {
        if (mInfomData == null) return;
        if (mInfomData.isIspackage()) {
            if (mMuHashMap == null)
                addHashMap();
            else {
                bindViewDataTwo(mMuHashMap.get(selectNetId));
            }
        }
    }

    /**
     * 添加课程
     */
    private void addHashMap() {
        if (mInfomData == null) return;
        if (mMuHashMap == null) {
            mMuHashMap = new HashMap<>();
            List<HomeInfomBean.DataBean> childrenproduct = mInfomData.getChildrenproduct();
            if (childrenproduct != null && !childrenproduct.isEmpty())
                for (int i = 0; i < childrenproduct.size(); i++) {
                    HomeInfomBean.DataBean dataBean = childrenproduct.get(i);
                    mMuHashMap.put(dataBean.getId(), dataBean
                    );
                }
        }
        initSelectVo();
    }


    private void initSelectVo() {
        List<HomeInfomBean.DataBean> childrenproduct = mInfomData.getChildrenproduct();
        if (childrenproduct != null && !childrenproduct.isEmpty()) {
            ArrayList<RecyclerSelectVoOne> selectVoOnes = new ArrayList<>();
            for (int i = 0; i < childrenproduct.size(); i++) {
                HomeInfomBean.DataBean bean = childrenproduct.get(i);
                RecyclerSelectVoOne one = new RecyclerSelectVoOne();
                one.setId(bean.getId());
                one.setName(bean.getName());
                if (selectNetId == bean.getId())
                    one.setSelect(true);
                else
                    one.setSelect(false);
                selectVoOnes.add(one);
            }
            showCatalogueLayout(selectVoOnes);
        }
    }

    private void initRequstView() {
        mHNIPresenter = new HomeNetInfomPresenter();
        mHNIPresenter.initModelView(new HomeNetInfomModel(), this);
        mHNIPresenter.requestNetDetail(mContext, mProductid);
        mShowDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
    }

    private void bindViewThree() {
        mSelectProductid=mProductid;
        initThreeData();
        clearData();
        bindThreeAdapterData();
        initThreeXrfresh();
        mXrfvContent.startRefresh();

    }

    private void initThreeXrfresh() {
        mXrfvContent.restoreLastRefreshTime(lastRefreshTime);
        mXrfvContent.setPullRefreshEnable(true);
        mXrfvContent.setPullLoadEnable(true);
        mXrfvContent.setAutoLoadMore(true);
        mXrfvContent.setEmptyView(mIvNetEmpty);
        mThreeAdapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mXrfvContent.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                loadThreeNewData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                LoadThreeMoreData();
            }
        });
    }

    private void LoadThreeMoreData() {
        if (isRefresh) return;
        isRefresh = true;
        mThreePresenter.requestNetCommentOne(mContext, 1, mSelectProductid);
    }

    private void loadThreeNewData() {
        if (isRefresh) return;
        isRefresh = true;
        mThreePresenter.requestNetCommentOne(mContext, getNowPage() + 1, mSelectProductid);
    }

    private void bindThreeAdapterData() {
        setGridLayoutManger(mContext, mRlvNetContent, 1);
        mThreeAdapter = new NetCommentAdapter(mContext, mThreeArrary);
        mRlvNetContent.setAdapter(mThreeAdapter);
        mThreeAdapter.setOnItemClickListener(new NetCommentAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(EvalueNewVo.DatasBean vo) {
                Intent intent = EvalueTwoActivity.newInstance(mContext, String.valueOf(vo.getTargetid()),
                        String.valueOf(vo.getId()),
                        DataMessageVo.USERTYPEVC,
                        DataMessageVo.VIDEO);
                mContext.startActivity(intent);
            }
        });
    }

    private void initThreeData() {
        mThreePresenter = new NetCommentPresenter();
        mThreePresenter.initModelView(new NetCommentModel(), this);
    }

    private void bindViewTwo(HomeInfomBean.DataBean dataX) {
        bindViewDataTwo(dataX);
    }

    /**
     * 播放视频
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onNetMainPlayId(NetPlayEvent event) {
        VideosBeanVo vo = event.getVo();
        Intent intent = NetBookPlayActivity.newIntent(mContext, NetBookPlayActivity.PlayMode.landScape, vo.getVid(),
                Integer.parseInt("3"), true, false);
        // 在线视频和下载的视频播放的时候只显示播放器窗口，用该参数来控制
        mContext.startActivity(intent);

    }

    private void bindViewDataTwo(HomeInfomBean.DataBean mData) {
        if (mData.getChapters() != null && !mData.getChapters().isEmpty()) {
            mIvNetEmptyContent.setVisibility(View.GONE);
            mRlvNetInfomTwoCatalogueContent.setVisibility(View.VISIBLE);
            setGridLayoutManger(mContext, mRlvNetInfomTwoCatalogueContent, 1);
            mTwoChaptersList = mData.getChapters();
            mTwoAdapter = new NetTableAdapter(mContext, mTwoChaptersList);
            mRlvNetInfomTwoCatalogueContent.setAdapter(mTwoAdapter);
        } else {
            mRlvNetInfomTwoCatalogueContent.setVisibility(View.GONE);
            mIvNetEmptyContent.setVisibility(View.VISIBLE);
        }
        mTvNetInfomTwoCatalogue.setText(mData.getName());
    }

    /**
     * 绑定详情页数据
     *
     * @param dataX
     */
    private void bindViewOne(HomeInfomBean.DataBean dataX) {
        initRecyclerViewDataOne(dataX);

    }

    private void initRecyclerViewDataOne(final HomeInfomBean.DataBean dataX) {
        final GridLayoutManager mOneViewlayoutManger = getGridLayoutManger(mContext, mRlvHomeNetOneInfom, 1);
        mOneAdapter = new NetHomeInfomAdapter(mContext, new ArrayList<Object>());
        mOneAdapter.setAdapterView(dataX);
        mRlvHomeNetOneInfom.setAdapter(mOneAdapter);
        mRlvHomeNetOneInfom.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                mLiTitleTabContent.setAlpha(getAlphaFloat(getScroolY(mOneViewlayoutManger)));
            }
        });
        mOneAdapter.setOnZnPClickListener(new NetHomeInfomAdapter.OnZnPClickListener() {
            @Override
            public void onClickZnP(List<GiftVo> gifts) {//赠品
                if (gifts != null && !gifts.isEmpty())
                    show_Z_V_Layout(gifts);
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
        View c = mRlvHomeNetOneInfom.getChildAt(0);
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

    /**
     * 赠品
     *
     * @param mData
     */
    private void show_Z_V_Layout(final List<GiftVo> mData) {

        //绑定数据
        mOnePopData = new CommonPopupWindow(this, R.layout.pop_item_net_catalogue, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
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
                        mOnePopData.getPopupWindow().dismiss();
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
                        setBackgroundAlpha(1f, VideoInfomActivity.this);
                    }
                });
            }
        };
        mOnePopData.showAtLocation(mRootAll, Gravity.BOTTOM, 0, 0);
        setBackgroundAlpha(0.5f, VideoInfomActivity.this);
    }

    /**
     * 目录
     *
     * @param selectVolists
     */
    private void showCatalogueLayout(final ArrayList<RecyclerSelectVoOne> selectVolists) {
        //绑定数据
        //绑定数据
//是否重复点击
//用户选中科目后，不请空hashMap
        mTwoPopData = new CommonPopupWindow(mContext, R.layout.pop_item_net_catalogue, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
            private TextView mTvTitle;
            private RecyclerView mRlvNetInfomPopContent;
            private ImageView mIvNetInfomPopClose;

            @Override
            protected void initView() {
                View view = getContentView();
                mTvTitle = (TextView) view.findViewById(R.id.tv_net_pop_title);
                mRlvNetInfomPopContent = (RecyclerView) view.findViewById(R.id.rlv_net_infom_pop_content);
                mIvNetInfomPopClose = (ImageView) view.findViewById(R.id.iv_net_infom_pop_close);
                mTvTitle.setText(getStringWithId(R.string.select_net));
            }


            @Override
            protected void initEvent() {
                ininBindPopData();
                mIvNetInfomPopClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTwoPopData.getPopupWindow().dismiss();

                    }
                });

            }

            //绑定数据
            private void ininBindPopData() {
                setGridLayoutManger(mContext, mRlvNetInfomPopContent, 1);
                final HomeNetInfomPopAdapter adapter = new HomeNetInfomPopAdapter(mContext, selectVolists);
                adapter.setShowType(HomeNetInfomPopAdapter.MULU_TYEP);
                mRlvNetInfomPopContent.setAdapter(adapter);
                adapter.setOnItemClickListener(new HomeNetInfomPopAdapter.OnItemClickListener() {
                    @Override
                    public void onClickItem(RecyclerSelectVoOne vo) {
                        isSelectYear = true;
                        mTwoPopData.getPopupWindow().dismiss();
                        if (vo.getId() != selectNetId) {//是否重复点击
                            selectNetId = vo.getId();
                            mSelectProductid=String.valueOf(vo.getId());
                            bindViewDataTwo(mMuHashMap.get(selectNetId));
                        }
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
                        //用户选中科目后，不请空hashMap
                        if (isSelectYear) return;
                        if (mTwoChaptersList == null || mTwoChaptersList.isEmpty()) {
                            mMuHashMap = null;
                            setRadioButtonSelecet(true, false, false);
                        }
                    }
                });
            }
        };
        mTwoPopData.showAtLocation(mRootAll, Gravity.BOTTOM, 0, 0);
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

    /**
     * 第三试图
     *
     * @param success
     */
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
                mThreeAdapter.notifyDataSetChanged();
                mXrfvContent.setLoadComplete(true);
            }
            if (!mThreeArrary.isEmpty() && mThreeArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                // 设置是否可以上拉加载
                mXrfvContent.setPullLoadEnable(true);
                mXrfvContent.setLoadComplete(false);
            } else
                mXrfvContent.setLoadComplete(true);
            if (evalueNewVo.getTotal().getTotal() == mThreeArrary.size())
                mXrfvContent.setLoadComplete(true);
            mThreeAdapter.notifyDataSetChanged();
        } else {
            T_ERROR(mContext);
        }

    }

    /**
     * 第三试图
     *
     * @param msg
     */
    @Override
    public void CommentErrorOne(String msg) {
        isRefresh = false;
        mXrfvContent.stopRefresh();
        T_ERROR(mContext);
    }

    /**
     * 第三试图
     *
     * @param success
     */
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
                mThreeAdapter.notifyDataSetChanged();
                mXrfvContent.setLoadComplete(true);
                return;
            }

            if (mThreeArrary != null && mThreeArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXrfvContent.setPullLoadEnable(true);
                mXrfvContent.setLoadComplete(false);
            } else {
                mXrfvContent.setLoadComplete(true);
            }
            if (vo.getTotal().getTotal() == mThreeArrary.size())
                mXrfvContent.setLoadComplete(true);
            mThreeAdapter.notifyDataSetChanged();
        } else {
            mXrfvContent.setLoadComplete(false);
            T_ERROR(mContext);
        }
    }

    /**
     * 第三试图
     *
     * @param msg
     */
    @Override
    public void CommentErrorTwo(String msg) {
        isRefresh = false;
        mXrfvContent.setLoadComplete(false);
        T_ERROR(mContext);
    }

    private void clearData() {
        if (mThreeArrary == null) {
            mThreeArrary = new ArrayList();
        } else {
            mThreeArrary.clear();
        }
    }

    private void addListData(List<?> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (mThreeArrary == null) {
            clearData();
        }
        mThreeArrary.addAll(list);
    }

    /**
     * 当前数据有几页
     *
     * @return
     */
    private int getNowPage() {
        if (mThreeArrary == null || mThreeArrary.isEmpty())
            return 0;
        if (mThreeArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0)
            return mThreeArrary.size() / DataMessageVo.CINT_PANGE_SIZE;
        else
            return mThreeArrary.size() / DataMessageVo.CINT_PANGE_SIZE + 1;
    }

}

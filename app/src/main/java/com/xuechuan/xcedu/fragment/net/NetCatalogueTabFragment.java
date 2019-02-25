package com.xuechuan.xcedu.fragment.net;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.home.HomeNetInfomPopAdapter;
import com.xuechuan.xcedu.adapter.net.NetTableAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.event.NetPlayEvent;
import com.xuechuan.xcedu.event.ScrollEvent;
import com.xuechuan.xcedu.ui.net.NetBookPlayActivity;
import com.xuechuan.xcedu.vo.ChaptersBeanVo;
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
 * @Title: NetCatalogueTabFragment
 * @Package com.xuechuan.xcedu.fragment.home
 * @Description: 网课目录页
 * @author: L-BackPacker
 * @date: 2018.11.26 下午 2:07
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.11.26
 */
public class NetCatalogueTabFragment extends BaseFragment implements View.OnClickListener {
    private static String TAG = "【" + NetCatalogueTabFragment.class + "】==";
    private static final String HOMEINFOM = "homeinfom";
    private Context mContext;
    private CommonPopupWindow mPopData;
    private HomeInfomBean.DataBean mData;
    private NetTableAdapter mAdapter;
    private TextView mTvNetCatalogue;
    private Button mBtnHomeNetCatalogueSwitch;
    private RecyclerView mRlvNetCatalogueContent;
    private ImageView mIvNetEmptyContent;
    private LinearLayout mLlNetInfomRoot;
    private List<ChaptersBeanVo> mChaptersList = new ArrayList<>();
    private int selectNetId = -1;
    private HashMap<Integer, HomeInfomBean.DataBean> mMuHashMap;
    /**
     * 判断用户是点击年份
     */
    private boolean isSelectYear = false;

    /***
     * @param   dataX 当前课程信息
     * @return
     */
    public static NetCatalogueTabFragment newInstance(HomeInfomBean.DataBean dataX) {
        NetCatalogueTabFragment fragment = new NetCatalogueTabFragment();
        Bundle args = new Bundle();
        args.putSerializable(HOMEINFOM, dataX);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mData = (HomeInfomBean.DataBean) getArguments().getSerializable(HOMEINFOM);
        }
        EventBus.getDefault().register(this);
    }

/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_net_catalogue_tab, container, false);
        initView(view);
        return view;
    }
*/

    @Override
    protected int initInflateView() {
        return R.layout.fragment_net_catalogue_tab;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        bindViewData(mData);
    }

    private void bindViewData(HomeInfomBean.DataBean mData) {
        if (mData.getChapters() != null && !mData.getChapters().isEmpty()) {
            mIvNetEmptyContent.setVisibility(View.GONE);
            mRlvNetCatalogueContent.setVisibility(View.VISIBLE);
            setGridLayoutManger(mContext, mRlvNetCatalogueContent, 1);
            mChaptersList = mData.getChapters();
            mAdapter = new NetTableAdapter(mContext, mChaptersList);
            mRlvNetCatalogueContent.setAdapter(mAdapter);
        } else {
            mRlvNetCatalogueContent.setVisibility(View.GONE);
            mIvNetEmptyContent.setVisibility(View.VISIBLE);
        }
        mTvNetCatalogue.setText(mData.getName());
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

    /**
     * 目录
     *
     * @param selectVolists
     */
    private void showCatalogueLayout(final ArrayList<RecyclerSelectVoOne> selectVolists) {
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
                mTvTitle.setText(getStrWithId(R.string.select_net));
            }


            @Override
            protected void initEvent() {
                ininBindPopData();
                mIvNetInfomPopClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPopData.getPopupWindow().dismiss();

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
                        mPopData.getPopupWindow().dismiss();
                        if (vo.getId() != selectNetId) {//是否重复点击
                            selectNetId = vo.getId();
                            bindViewData(mMuHashMap.get(selectNetId));
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
                        if (mChaptersList == null || mChaptersList.isEmpty()) {
                            mMuHashMap = null;
                            EventBus.getDefault().postSticky(new ScrollEvent("TOP"));
                        }
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

    private void initView(View view) {
        mContext = getActivity();
        mTvNetCatalogue = (TextView) view.findViewById(R.id.tv_net_catalogue);
        mTvNetCatalogue.setOnClickListener(this);
        mBtnHomeNetCatalogueSwitch = (Button) view.findViewById(R.id.btn_home_net_catalogue_switch);
        mBtnHomeNetCatalogueSwitch.setOnClickListener(this);
        mRlvNetCatalogueContent = (RecyclerView) view.findViewById(R.id.rlv_net_catalogue_content);
        mRlvNetCatalogueContent.setOnClickListener(this);
        mIvNetEmptyContent = (ImageView) view.findViewById(R.id.iv_net_empty_content);
        mIvNetEmptyContent.setOnClickListener(this);
        mLlNetInfomRoot = (LinearLayout) view.findViewById(R.id.ll_net_infom_root);
        mLlNetInfomRoot.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home_net_catalogue_switch://切换网课
                if (mData != null && mData.isIspackage()) {//是否打包产品
                    addHashMap();
                    initSelectVo();
                } else {//否
                    T_ERROR(mContext, getString(R.string.other_catalogue));
                }
                break;

        }
    }

    private void addHashMap() {
        if (mMuHashMap == null) {
            mMuHashMap = new HashMap<>();
            List<HomeInfomBean.DataBean> childrenproduct = mData.getChildrenproduct();
            if (childrenproduct != null && !childrenproduct.isEmpty())
                for (int i = 0; i < childrenproduct.size(); i++) {
                    HomeInfomBean.DataBean dataBean = childrenproduct.get(i);
                    mMuHashMap.put(dataBean.getId(), dataBean
                    );
                }
        }
    }

    private void initSelectVo() {
        List<HomeInfomBean.DataBean> childrenproduct = mData.getChildrenproduct();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {//显示
            showMulu();
            Log.e(TAG, "setUserVisibleHint: 显示");
        } else {
            Log.e(TAG, "setUserVisibleHint: 隐藏");
        }
    }

    private void showMulu() {
        if (mData == null) return;
        if (mMuHashMap != null) return;
        if (mData.isIspackage()) {
            addHashMap();
        }
        initSelectVo();
    }
}

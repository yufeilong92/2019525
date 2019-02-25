package com.xuechuan.xcedu.fragment.net;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.home.MyTagPagerAdapter;
import com.xuechuan.xcedu.adapter.me.MyClassIndicatorAdapter;
import com.xuechuan.xcedu.adapter.me.MyOrderIndicatorAdapter;
import com.xuechuan.xcedu.adapter.net.NetYearAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.event.RefreshVideoNetEvent;
import com.xuechuan.xcedu.mvp.contract.NetMyClassContract;
import com.xuechuan.xcedu.mvp.model.NetMyClassModel;
import com.xuechuan.xcedu.mvp.presenter.NetMyClassPresenter;
import com.xuechuan.xcedu.utils.ArrayToListUtil;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.ClassBean;

import com.xuechuan.xcedu.vo.MyClassVo;
import com.xuechuan.xcedu.vo.TagsBean;
import com.xuechuan.xcedu.weight.CommonPopupWindow;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetVideoHomeFragment
 * @Package com.xuechuan.xcedu.fragment.net
 * @Description: 网课首页
 * @author: L-BackPacker
 * @date: 2018.11.29 上午 10:25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.11.29
 */
public class NetVideoHomeFragment extends BaseFragment implements NetMyClassContract.View, View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TextView mTvYearTitle;
    private ViewPager mViewpageContent;
    private Context mContext;
    private MagicIndicator mMgiLayout;
    private AlertDialog mShowDialog;
    private LinearLayout mRlNetMyRoot;
    private CommonPopupWindow mPopYearData;
    private MyClassVo.DataBean data;


    public static NetVideoHomeFragment newInstance(String param1, String param2) {
        NetVideoHomeFragment fragment = new NetVideoHomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_net_video_home, container, false);
        initView(view);
        return view;
    }
*/

    @Override
    protected int initInflateView() {
        return R.layout.fragment_net_video_home;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();

    }

    private void initData() {
        NetMyClassPresenter myClassPresenter = new NetMyClassPresenter();
        myClassPresenter.initModelView(new NetMyClassModel(), this);
        myClassPresenter.requestNetMyClass(mContext);
        Calendar instance = Calendar.getInstance();
        int i = instance.get(Calendar.YEAR);
        mTvYearTitle.setText(String.valueOf(i));
//        mShowDialog = DialogUtil.showDialog(mContext, "", getStrWithId(R.string.loading));
    }


    @Override
    public void MyClassSuccess(String success) {
        dialogDimiss(mShowDialog);
        MyClassVo vo = Utils.getGosnT(success, MyClassVo.class);
        if (vo.getStatus().getCode() == 200) {
            data = vo.getData();
            bindViewData(data);
        } else {
            T_ERROR(mContext);
        }
    }

    /**
     * 绑定界面
     *
     * @param data
     */
    private void bindViewData(MyClassVo.DataBean data) {
        initTitle(data);

    }

    @Override
    public void MyClassError(String msg) {
        dialogDimiss(mShowDialog);
        T_ERROR(mContext);
    }

    private void initTitle(MyClassVo.DataBean data) {
        List<TagsBean> tags = data.getTags();
        mMgiLayout.setBackgroundColor(Color.WHITE);
        mMgiLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setScrollPivotX(0.25f);
        if (tags.size() > 4) {
            commonNavigator.setAdjustMode(false);
        } else {
            commonNavigator.setAdjustMode(true);
        }
        List<Fragment> fragments = creartFragment(tags, data);
        MyClassIndicatorAdapter adapter = new MyClassIndicatorAdapter(tags, mViewpageContent);
        mMgiLayout.setNavigator(commonNavigator);
        commonNavigator.setAdapter(adapter);
        MyTagPagerAdapter tagPagerAdapter = new MyTagPagerAdapter(getFragmentManager(), fragments);
        mViewpageContent.setAdapter(tagPagerAdapter);
        mViewpageContent.setOffscreenPageLimit(3);
        ViewPagerHelper.bind(mMgiLayout, mViewpageContent);

    }

    public List<Fragment> creartFragment(List<TagsBean> tagsBeans, MyClassVo.DataBean data) {
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < tagsBeans.size(); i++) {
            TagsBean tagsBean = tagsBeans.get(i);
            int year = Integer.parseInt(mTvYearTitle.getText().toString().trim());
            GMNetVideoFragment fragment = GMNetVideoFragment.newInstance(year, tagsBean.getId(), data);
            fragments.add(fragment);
        }
        return fragments;
    }

    /**
     * 目录
     *
     * @param mLists 年份
     */
    private void showYearLayout(final List<Integer> mLists) {
        //绑定数据
        //绑定数据
        mPopYearData = new CommonPopupWindow(mContext, R.layout.pop_my_net_year, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
            private RecyclerView mRlcContentYear;

            @Override
            protected void initView() {
                View view = getContentView();
                mRlcContentYear = (RecyclerView) view.findViewById(R.id.rlv_content_year);
            }

            @Override
            protected void initEvent() {
                setGridLayoutManger(mContext, mRlcContentYear, 1);
                NetYearAdapter adapter = new NetYearAdapter(mContext, mLists);
                mRlcContentYear.setAdapter(adapter);
                adapter.setYear(Integer.valueOf(mTvYearTitle.getText().toString().trim()));
                adapter.setOnItemClickListener(new NetYearAdapter.OnItemClickListener() {
                    @Override
                    public void onClickItem(int o) {
                        mTvYearTitle.setText(String.valueOf(o));
                        EventBus.getDefault().postSticky(new RefreshVideoNetEvent(o));
                        mPopYearData.getPopupWindow().dismiss();
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
//        mPopYearData.showAtLocation(mRlNetMyRoot, Gravity.TOP|Gravity.RIGHT, mTvTitle.getWidth(), mTvTitle.getHeight());
        mPopYearData.showAsDropDown(mTvYearTitle, -16, 10);
        setBackgroundAlpha(1f, mContext);
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
        mTvYearTitle = (TextView) view.findViewById(R.id.tv_year_title);
        mViewpageContent = (ViewPager) view.findViewById(R.id.viewpage_content);
        mMgiLayout = (MagicIndicator) view.findViewById(R.id.mgi_layout);
        mRlNetMyRoot = (LinearLayout) view.findViewById(R.id.rl_net_my_root);
        mTvYearTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_year_title:
                if (data != null)
                    showYearLayout(data.getYears());
                break;

            default:

        }
    }
}

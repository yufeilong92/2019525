package com.xuechuan.xcedu.fragment.net;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.net.GmNetListAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.event.RefreshVideoNetEvent;
import com.xuechuan.xcedu.ui.net.NetBookMyInfomActivity;
import com.xuechuan.xcedu.vo.ClassBean;
import com.xuechuan.xcedu.vo.MyClassVo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: GMNetgVideoFragment
 * @Package com.xuechuan.xcedu.adapter.net
 * @Description: 通用的网课列表界面
 * @author: L-BackPacker
 * @date: 2018.11.29 下午 3:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.11.29
 */
public class GMNetVideoFragment extends BaseFragment {

    private static final String YEAR = "year";
    private static final String SUBJECT = "subject";
    private static final String DATA = "data";
    private int mYear;
    private int mTagid;
    private Context mContext;
    private RecyclerView mRlvNetHome;
    private MyClassVo.DataBean mData;
    private GmNetListAdapter adapter;
    private ImageView mIvNetEmptyContent;


    public static GMNetVideoFragment newInstance(int year, int tagid, MyClassVo.DataBean data) {
        GMNetVideoFragment fragment = new GMNetVideoFragment();
        Bundle args = new Bundle();
        args.putInt(YEAR, year);
        args.putInt(SUBJECT, tagid);
        args.putSerializable(DATA, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mYear = getArguments().getInt(YEAR, 0);
            mTagid = getArguments().getInt(SUBJECT, 0);
            mData = (MyClassVo.DataBean) getArguments().getSerializable(DATA);
        }
    }

/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gmnetg_video, container, false);
        initView(view);
        return view;
    }*/

    @Override
    protected int initInflateView() {
        return R.layout.fragment_gmnetg_video;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        initView(view);
        initData();


    }

    private void initData() {
        ArrayList<ClassBean> dataLists = new ArrayList<>();
        List<ClassBean> list = mData.getClasses();
        for (int k = 0; k < list.size(); k++) {
            ClassBean classesBean = list.get(k);
            if (classesBean.getYears().contains(mYear) && classesBean.getTagids().contains(mTagid)) {
                dataLists.add(classesBean);
            }
        }
        initAdapter(dataLists);
    }

    private void initAdapter(ArrayList<ClassBean> dataLists) {
        if (dataLists == null || dataLists.isEmpty()) {
            mIvNetEmptyContent.setVisibility(View.VISIBLE);
            mRlvNetHome.setVisibility(View.GONE);
            mIvNetEmptyContent.setImageResource(R.drawable.common_blankimg_course);
            return;
        } else {
            mIvNetEmptyContent.setVisibility(View.GONE);
            mRlvNetHome.setVisibility(View.VISIBLE);
        }
        setGridLayoutManger(mContext, mRlvNetHome, 1);
        adapter = new GmNetListAdapter(mContext, dataLists);
        mRlvNetHome.setAdapter(adapter);
        adapter.setOnItemClickListener(new GmNetListAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(ClassBean vo) {
                if (vo == null) return;
                Intent intent = NetBookMyInfomActivity.newInstance(mContext, String.valueOf(vo.getId()));
                intent.putExtra(NetBookMyInfomActivity.CSTR_EXTRA_TITLE_STR, vo.getName());
                startActivity(intent);
            }
        });
    }


    private void initView(View view) {
        mContext = getActivity();
        mRlvNetHome = (RecyclerView) view.findViewById(R.id.rlv_net_home);
        mIvNetEmptyContent = (ImageView) view.findViewById(R.id.iv_net_empty_content);
    }

    /**
     * 用户刷新切换年份数据
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void refreshData(RefreshVideoNetEvent event) {
        mYear = event.getYear();
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }
}

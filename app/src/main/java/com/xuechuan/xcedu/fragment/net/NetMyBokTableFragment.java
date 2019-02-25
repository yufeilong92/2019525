package com.xuechuan.xcedu.fragment.net;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xuechuan.xcedu.event.RefreshPlayIconEvent;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.net.NetMyTableAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.vo.ChaptersBeanVo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetMyBokTableFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 课程表
 * @author: L-BackPacker
 * @date: 2018/5/16 14:17
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/16
 */
public class NetMyBokTableFragment extends BaseFragment {
    private static final String CLASSID = "classid";

    private RecyclerView mRlvSpecaContent;
    private TextView mTvNetEmptyContent;
    private List<ChaptersBeanVo> mBookList;
    private Context mContext;
    private NetMyTableAdapter adapter;

    public NetMyBokTableFragment() {
    }
    public static NetMyBokTableFragment newInstance(List<ChaptersBeanVo> bookList) {
        NetMyBokTableFragment fragment = new NetMyBokTableFragment();
        Bundle args = new Bundle();
        args.putSerializable(CLASSID, (Serializable) bookList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBookList = (List<ChaptersBeanVo>) getArguments().getSerializable(CLASSID);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }

    /*   @Override
           public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                    Bundle savedInstanceState) {
               View view = inflater.inflate(R.layout.fragment_net_my_bok_table, container, false);
               initView(view);
               return view;
           }
       */
    @Override
    protected int initInflateView() {
        return R.layout.fragment_net_my_bok_table;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        if (mBookList == null || mBookList.isEmpty()) {
            mRlvSpecaContent.setVisibility(View.GONE);
            mTvNetEmptyContent.setVisibility(View.VISIBLE);
            return;
        } else {
            mRlvSpecaContent.setVisibility(View.VISIBLE);
            mTvNetEmptyContent.setVisibility(View.GONE);
        }
        bindAdapterData();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MainRefrsh(RefreshPlayIconEvent event) {
        String vid = event.getVid();
        String name = event.getName();
        adapter.refreshData(vid,name);
    }

    private void bindAdapterData() {
        setGridLayoutManger(mContext,mRlvSpecaContent,1);
        adapter = new NetMyTableAdapter(mContext, mBookList);
        mRlvSpecaContent.setAdapter(adapter);
    }


    private void initView(View view) {
        mContext = getActivity();
        mRlvSpecaContent = (RecyclerView) view.findViewById(R.id.rlv_speca_content);
        mTvNetEmptyContent = (TextView) view.findViewById(R.id.tv_net_empty_content);
    }
}

package com.xuechuan.xcedu.fragment.net;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.net.NetTableAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.vo.ChaptersBeanVo;

import java.io.Serializable;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetTableFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 未购买课程表
 * @author: L-BackPacker
 * @date: 2018/5/15 16:40
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/15
 */
public class NetTableFragment extends BaseFragment {

    private static final String CLASSID = "classid";
    private RecyclerView mRlvSpecaContent;
    private Context mContext;
    private NetTableAdapter adapter;
    private List<ChaptersBeanVo> mBookInfoms;
    private TextView empty;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBookInfoms = (List<ChaptersBeanVo>) getArguments().getSerializable(CLASSID);
        }
    }

    public NetTableFragment() {

    }

    public static NetTableFragment newInstance(List<ChaptersBeanVo> list) {
        NetTableFragment fragment = new NetTableFragment();
        Bundle args = new Bundle();
        args.putSerializable(CLASSID, (Serializable) list);
        fragment.setArguments(args);
        return fragment;
    }

    /*
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_net_table, container, false);
            initView(view);
            return view;
        }
    */
    @Override
    protected int initInflateView() {
        return R.layout.fragment_net_table;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        if (mBookInfoms == null || mBookInfoms.isEmpty()) {
            mRlvSpecaContent.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
            return;
        }else {
            mRlvSpecaContent.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
        }
        bindAdapterData();
    }


    private void bindAdapterData() {
        setGridLayoutManger(mContext,mRlvSpecaContent,1);
        adapter = new NetTableAdapter(mContext, mBookInfoms);
        mRlvSpecaContent.setAdapter(adapter);
    }

    private void initView(View view) {
        mContext = getActivity();
        empty = view.findViewById(R.id.tv_net_empty_content);
        mRlvSpecaContent = (RecyclerView) view.findViewById(R.id.rlv_speca_content);
    }
}


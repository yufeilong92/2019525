package com.xuechuan.xcedu.fragment.wenda;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseFragment;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: JingXuanFragment
 * @Package com.xuechuan.xcedu.fragment.wenda
 * @Description: 精选
 * @author: L-BackPacker
 * @date: 2019/1/9 21:28
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019/1/9
 */
public class HankPickFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView mRlvHankpickContent;
    private Context mContext;


    public HankPickFragment() {
    }

    public static HankPickFragment newInstance(String param1, String param2) {
        HankPickFragment fragment = new HankPickFragment();
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
        View view = inflater.inflate(R.layout.fragment_jing_xuan, container, false);
        initView(view);
        return view;
    }
*/

    @Override
    protected int initInflateView() {
        return R.layout.fragment_jing_xuan;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);

    }

    private void initView(View view) {
        mContext = getActivity();
    }
}

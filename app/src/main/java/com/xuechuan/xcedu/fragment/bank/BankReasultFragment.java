package com.xuechuan.xcedu.fragment.bank;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseFragment;

/**
 * All rights Reserved, Designed By
 *
 * @version V 1.0 xxxxxxx
 * @Title: BankReasultFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 题库结果页
 * @author: YFL
 * @date: 2018/4/20  0:05
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/20   Inc. All rights reserved.
 * 注意：本内容仅限于XXXXXX有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class BankReasultFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public BankReasultFragment() {
    }

      public static BankReasultFragment newInstance(String param1, String param2) {
        BankReasultFragment fragment = new BankReasultFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bank_reasult, container, false);
    }

    @Override
    protected int initInflateView() {
        return R.layout.fragment_bank_reasult;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {

    }

}

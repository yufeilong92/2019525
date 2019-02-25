package com.xuechuan.xcedu.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.ui.LoginActivity;
import com.xuechuan.xcedu.utils.SaveIsDoneUtil;

/**
 * All rights Reserved, Designed By
 *
 * @version V 1.0 xxxxxxx
 * @Title: GuideFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 引导页
 * @author: YFL
 * @date: 2018/6/4  21:13
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/6/4   Inc. All rights reserved.
 * 注意：本内容仅限于XXXXXX有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class GuideFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ImageView mIvGuidler;
    private Button mBtnDone;
    private Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public GuideFragment() {
    }


    public static GuideFragment newInstance(String param1, String param2) {
        GuideFragment fragment = new GuideFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide, container, false);
        initView(view);
        return view;
    }
*/

    @Override
    protected int initInflateView() {
        return R.layout.fragment_guide;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
    }

    public static final String INTERFACE_WITH = GuideFragment.class.getName();

    private void initData() {
        if (mParam1.equals("0")) {
            mIvGuidler.setImageResource(R.mipmap.ydy_1);
            mBtnDone.setVisibility(View.GONE);
        } else if (mParam1.equals("1")) {
            mIvGuidler.setImageResource(R.mipmap.ydy_2);
            mBtnDone.setVisibility(View.GONE);
        } else if (mParam1.equals("2")) {
            mIvGuidler.setImageResource(R.mipmap.ydy_3);
            mBtnDone.setVisibility(View.GONE);
        }
    }


    private void initView(View view) {
        mContext = getActivity();
        mIvGuidler = (ImageView) view.findViewById(R.id.iv_guidler);
        mBtnDone = (Button) view.findViewById(R.id.btn_done);
        mIvGuidler.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_done:
                SaveIsDoneUtil.getInstance().putUUID("ture");
                Intent intent1 = new Intent(mContext, LoginActivity.class);
                startActivity(intent1);
                getActivity().finish();
                break;
                case R.id.iv_guidler:
                    if (mParam1.equals("2")){
                        SaveIsDoneUtil.getInstance().putUUID("ture");
                        Intent intent2 = new Intent(mContext, LoginActivity.class);
                        startActivity(intent2);
                        getActivity().finish();
                    }
                  break;
        }
    }
}

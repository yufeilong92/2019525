package com.xuechuan.xcedu.fragment.bank;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.me.MyOrderIndicatorAdapter;
import com.xuechuan.xcedu.adapter.home.MyTagPagerAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.ArrayToListUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.BookHomeVo;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: BankFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 题库
 * @author: L-BackPacker
 * @date: 2018/4/11 17:13
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/11
 */
public class BankFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context mContext;
    private String mParam1;
    private String mParam2;
    private ViewPager mVpgContent;
    private MagicIndicator mMagicindicatorBank;


    public BankFragment() {
    }

    public static BankFragment newInstance(String param1, String param2) {
        BankFragment fragment = new BankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_bank, null);
        initView(view);
        return view;
    }
*/

    @Override
    protected int initInflateView() {
        return R.layout.fragment_bank;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        requestChapters();
        initView(view);
//        initViewData();
        initIndicator();
    }

    public static final String INTERFACE_PARAME = BankFragment.class.getName();


    private void initIndicator() {
        final ArrayList<String> mTabs = ArrayToListUtil.arraytoList(mContext, R.array.bank_tab);
        mMagicindicatorBank.setBackgroundColor(Color.parseColor("#ffffff"));
        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setScrollPivotX(0.25f);
        if (mTabs.size() > 4) {
            commonNavigator.setAdjustMode(false);
        } else {
            commonNavigator.setAdjustMode(true);
        }
        List<Fragment> fragments = creartFragment(mTabs);
        MyOrderIndicatorAdapter adapter = new MyOrderIndicatorAdapter(mTabs, mVpgContent);
        mMagicindicatorBank.setNavigator(commonNavigator);
        commonNavigator.setAdapter(adapter);
        MyTagPagerAdapter tagPagerAdapter = new MyTagPagerAdapter(getFragmentManager(), fragments);
        mVpgContent.setAdapter(tagPagerAdapter);
        mVpgContent.setOffscreenPageLimit(3);
        ViewPagerHelper.bind(mMagicindicatorBank, mVpgContent);
    }

    private List<Fragment> creartFragment(List<String> list) {
        if (list.size() < 2) {
            mMagicindicatorBank.setVisibility(View.GONE);
        }
        ArrayList<Fragment> mlist = new ArrayList<>();
        SkillFragment skillFragment = SkillFragment.newInstance("1");
        ColligateFragment colligateFragment = ColligateFragment.newInstance("2");
        CaseFragment caseFragment = CaseFragment.newInstance("3");

        mlist.add(skillFragment);
        mlist.add(colligateFragment);
        mlist.add(caseFragment);
        return mlist;
    }
    private void requestChapters() {
        HomeService service = new HomeService(getContext());
/*        service.setIsShowDialog(true);
        service.setDialogContext(getContext(), "", getStrWithId(R.string.loading));*/
        service.requestCourse(1, new StringCallBackView() {
            @Override
            public void onSuccess(String message) {
//                String message = response.body().toString();
                L.d("教程首页内容", message);
                Gson gson = new Gson();
                BookHomeVo vo = gson.fromJson(message, BookHomeVo.class);
                if (vo.getStatus().getCode() == 200) {
                    List<BookHomeVo.DatasBean> datas = vo.getDatas();
                    bindTab(datas);
                } else {
                    L.e(vo.getStatus().getMessage());
//                    T.showToast(getContext(), vo.getStatus().getMessage());
                }
            }

            @Override
            public void onError(String response) {
                L.w(response);
                T.showToast(mContext,getStrWithId(mContext, R.string.net_error));
            }
        });
    }

    private void bindTab(List<BookHomeVo.DatasBean> datas) {

    }


    private void initView(View view) {
        mContext = getActivity();
        mMagicindicatorBank = (MagicIndicator) view.findViewById(R.id.magicindicator_bank);
        mVpgContent = (ViewPager) view.findViewById(R.id.vpg_content);
        mVpgContent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}

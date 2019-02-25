package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.me.MyOrderIndicatorAdapter;
import com.xuechuan.xcedu.adapter.home.MyTagPagerAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.fragment.me.CompleteFragment;
import com.xuechuan.xcedu.fragment.me.MyAllOrderFragment;
import com.xuechuan.xcedu.fragment.net.ObligationFragment;
import com.xuechuan.xcedu.fragment.me.TakeDeliveryFragment;
import com.xuechuan.xcedu.utils.ArrayToListUtil;
import com.xuechuan.xcedu.weight.CommonPopupWindow;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: MyOrderFromActivity
 * @Package com.xuechuan.xcedu.ui.me
 * @Description: 我的订单
 * @author: L-BackPacker
 * @date: 2018/5/26 12:46
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/26
 */
public class MyOrderActivity extends BaseActivity {

    private MagicIndicator mMagicindicator;
    private ViewPager mViewPagerContent;
    private Context mContext;
    private CommonPopupWindow popPay;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_from);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_order);
        initView();
        initIndoter();
    }


    private void initIndoter() {
        ArrayList<String> titleList = ArrayToListUtil.arraytoList(mContext, R.array.my_order_title);
        mMagicindicator.setBackgroundColor(Color.parseColor("#ffffff"));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.25f);
        commonNavigator.setAdjustMode(true);
        MyOrderIndicatorAdapter adapter = new MyOrderIndicatorAdapter(titleList, mViewPagerContent);
        mMagicindicator.setNavigator(commonNavigator);
        commonNavigator.setAdapter(adapter);
        List<Fragment> fragments = creartFragment();
        MyTagPagerAdapter tagPagerAdapter = new MyTagPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPagerContent.setAdapter(tagPagerAdapter);
        mViewPagerContent.setOffscreenPageLimit(4);
        ViewPagerHelper.bind(mMagicindicator, mViewPagerContent);
    }

    private List<Fragment> creartFragment() {
        ArrayList<Fragment> list = new ArrayList<>();
        MyAllOrderFragment allOrderFragment = MyAllOrderFragment.newInstance(DataMessageVo.ORDERALL, "");
        ObligationFragment obligationFragment = ObligationFragment.newInstance(DataMessageVo.ORDERPAY, "");
        CompleteFragment completeFragment = CompleteFragment.newInstance(DataMessageVo.ORDERACOM, "");
        TakeDeliveryFragment deliveryFragment = TakeDeliveryFragment.newInstance(DataMessageVo.ORDERAGOODS, "");
        list.add(allOrderFragment);
        list.add(obligationFragment);
        list.add(completeFragment);
        list.add(deliveryFragment);
        return list;
    }

    private void initView() {
        mContext = this;
        mMagicindicator = (MagicIndicator) findViewById(R.id.magicindicator);
        mViewPagerContent = (ViewPager) findViewById(R.id.view_pager_content);
    }


}

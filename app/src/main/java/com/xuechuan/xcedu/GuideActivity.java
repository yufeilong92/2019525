package com.xuechuan.xcedu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.xuechuan.xcedu.adapter.home.MyTagPagerAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.fragment.GuideFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: GuideActivity
 * @Package com.xuechuan.xcedu
 * @Description: 引导页
 * @author: L-BackPacker
 * @date: 2018/6/4 18:41
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/6/4
 */
public class GuideActivity extends BaseActivity {

    private ViewPager mViewpager;
    private MagicIndicator mMagicindicator;
    //引导页个数
    private int index = 3;
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_guide);
        initView();
        initData();
        initMagicIndicatore();
    }


    private void initMagicIndicatore() {
        CircleNavigator circleNavigator = new CircleNavigator(this);
        circleNavigator.setCircleCount(3);
        circleNavigator.setCircleColor(Color.RED);
        circleNavigator.setCircleClickListener(new CircleNavigator.OnCircleClickListener() {
            @Override
            public void onClick(int index) {
                mViewpager.setCurrentItem(index);
            }
        });
        mMagicindicator.setNavigator(circleNavigator);
        ViewPagerHelper.bind(mMagicindicator, mViewpager);
    }

    private void initData() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < index; i++) {
            fragments.add(GuideFragment.newInstance(i + "", ""));
        }
        MyTagPagerAdapter adapter = new MyTagPagerAdapter(getSupportFragmentManager(), fragments);
        mViewpager.setAdapter(adapter);
    }

    private void initView() {
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mMagicindicator = (MagicIndicator) findViewById(R.id.magicindicator);
    }
}

package com.xuechuan.xcedu.adapter.home;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 搜索结果适配器
 * @author: L-BackPacker
 * @date: 2018/4/20 8:32
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class HomeReasultViewPagAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private ArrayList<Fragment> mData;
    private ArrayList<String> mTitle;

    public HomeReasultViewPagAdapter(FragmentManager fm, Context mContext, ArrayList<Fragment> mData, ArrayList<String> mTitle) {
        super(fm);
        this.mContext = mContext;
        this.mData = mData;
        this.mTitle = mTitle;
    }

    public HomeReasultViewPagAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
//        return super.getPageTitle(position);
        return mTitle.get(position);
    }
}

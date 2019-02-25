package com.xuechuan.xcedu.adapter.me;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.vo.ArtilceTagVo;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: MyApplication
 * @Package com.example.administrator.myapplication
 * @Description: 我的订单
 * @author: L-BackPacker
 * @date: 2018/5/13 12:07
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyOrderIndicatorAdapter extends CommonNavigatorAdapter {
    private List<String> mData;
    private ViewPager pager;

    public MyOrderIndicatorAdapter(List<String> mData, ViewPager pager) {

        this.mData = mData;
        this.pager = pager;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        SimplePagerTitleView view = new SimplePagerTitleView(context);
        view.setText(mData.get(index));
        view.setNormalColor(context.getResources().getColor(R.color.text_fu_color));
        view.setSelectedColor(Color.BLACK);
        view.setTextSize(16);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(index);
            }
        });
        return view;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        indicator.setYOffset(UIUtil.dip2px(context, 1));
        indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
        indicator.setColors(context.getResources().getColor(R.color.red_text));
        return indicator;
    }
}

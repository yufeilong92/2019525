package com.xuechuan.xcedu.utils;

import android.support.v7.widget.RecyclerView;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.xuechuan.xcedu.vo.RecyclerSelectVo;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.11.07 下午 5:36
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class RecyclerUtil {
    //目标项是否在最后一个可见项之后
    public static boolean mShouldScroll;
    //记录目标项位置
    public static int mToPosition;

    /**
     * 滑动到指定位置
     */
    public static void smoothMoveToPosition(final RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mRecyclerView.smoothScrollToPosition(position);
                }
            }, 300);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                final int top = mRecyclerView.getChildAt(movePosition).getTop();
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mRecyclerView.smoothScrollBy(0, top, new AccelerateDecelerateInterpolator());
                    }
                }, 300);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mRecyclerView.smoothScrollToPosition(position);
                }
            }, 300);

        }
    }

    /**
     * 设置recyclerview滑动监听
     *
     * @param rlv
     * @param name
     */
    public static void setTitleName(RecyclerView rlv, String name, boolean isShow) {
        rlv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }


    /**
     * @param postion 点击坐标
     * @return
     */
    public static ArrayList<RecyclerSelectVo> initSelectVo(int size, int postion) {

        ArrayList<RecyclerSelectVo> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            RecyclerSelectVo vo = new RecyclerSelectVo();
            vo.setSelect(postion == i);
            list.add(vo);
        }
        return list;
    }


}

package com.xuechuan.xcedu.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Description: 选中指定item
 * @author: YFL
 * @date: 2017/7/5 17:03
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 */
public class RecyclerSelectItem {

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager        设置RecyclerView对应的manager
     * @param recyclerView   当前的RecyclerView
     * @param selectPosition 要跳转的位置
     */
    public static  void MoveToPostion(GridLayoutManager manager, RecyclerView recyclerView, int selectPosition) {
        int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
        if (selectPosition <= firstVisibleItemPosition) {
            recyclerView.scrollToPosition(selectPosition);
        } else if (lastVisibleItemPosition >= selectPosition) {
            int top = recyclerView.getChildAt(selectPosition - firstVisibleItemPosition).getTop();
            recyclerView.scrollBy(0, top);
        } else {
            recyclerView.smoothScrollToPosition(selectPosition);
        }
    }

}

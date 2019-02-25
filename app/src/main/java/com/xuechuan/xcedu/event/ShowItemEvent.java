package com.xuechuan.xcedu.event;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.Event
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/6/19 9:48
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ShowItemEvent {
    private int  showItem;

    public ShowItemEvent(int showItem) {
        this.showItem = showItem;
    }

    public int getShowItem() {
        return showItem;
    }
}

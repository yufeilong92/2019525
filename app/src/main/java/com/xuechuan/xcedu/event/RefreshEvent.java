package com.xuechuan.xcedu.event;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.event
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.04 下午 2:32
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class RefreshEvent {
    private int data;

    public RefreshEvent(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }
}

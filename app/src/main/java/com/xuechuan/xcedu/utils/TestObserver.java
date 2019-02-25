package com.xuechuan.xcedu.utils;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 观察人物
 * @author: L-BackPacker
 * @date: 2018.12.07 上午 8:32
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface TestObserver {
    /**
     * 订阅者
     *
     * @return
     */
    public Subsciber subsciber();

    /**
     * 要触发的事件
     */
    public void refresh();
}

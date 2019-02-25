package com.xuechuan.xcedu.utils;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 订阅事件
 * @author: L-BackPacker
 * @date: 2018.12.07 上午 8:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class Subsciber {
    private ArrayList<TestObserver> observers = new ArrayList<>();

    public void attch(TestObserver observer) {
        observers.add(observer);

    }

    public void notifyChanger() {
        if (observers != null && !observers.isEmpty()) {
            for (int i = 0; i < observers.size(); i++) {
                observers.get(i).refresh();
            }
        }
    }
}

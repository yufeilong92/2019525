package com.xuechuan.xcedu.event;

import com.xuechuan.xcedu.db.DownVideoDb;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.Event
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/19 8:42
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetDownEvent {

    private  DownVideoDb bean;

    public DownVideoDb getBean() {
        return bean;
    }

    public NetDownEvent(DownVideoDb bean) {
        this.bean = bean;
    }
}

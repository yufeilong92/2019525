package com.xuechuan.xcedu.event;

import com.xuechuan.xcedu.db.DownVideoDb;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.Event
 * @Description: 已完成的
 * @author: L-BackPacker
 * @date: 2018/5/19 18:29
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetDownDoneEvent {

    /**
     * 视频个数
     */
    private int number;
    /**
     * 总大小
     */
    private String count;
    /**
     * 对象
     */
    private DownVideoDb db;

    public int getNumber() {
        return number;
    }

    public String getCount() {
        return count;
    }

    public DownVideoDb getDb() {
        return db;
    }

    public NetDownDoneEvent(DownVideoDb db, int number, String count) {
        this.db = db;
        this.number = number;
        this.count = count;
    }


}

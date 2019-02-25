package com.xuechuan.xcedu.event;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.Event
 * @Description: 获取表
 * @author: L-BackPacker
 * @date: 2018/5/17 10:56
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BookTableEvent {
    private List Arrary;

    public List getArrary() {
        return Arrary;
    }

    public BookTableEvent(List arrary) {
        Arrary = arrary;
    }
}

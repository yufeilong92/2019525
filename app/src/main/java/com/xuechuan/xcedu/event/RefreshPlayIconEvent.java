package com.xuechuan.xcedu.event;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.Event
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/6/12 8:53
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class RefreshPlayIconEvent {

    public String vid;
    public String name;

    public RefreshPlayIconEvent(String vid, String name) {
        this.vid = vid;
        this.name = name;
    }

    public String getVid() {
        return vid;
    }

    public String getName() {
        return name;
    }
}

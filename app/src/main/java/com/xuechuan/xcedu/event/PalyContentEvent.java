package com.xuechuan.xcedu.event;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.event
 * @Description: 继续播放事件
 * @author: L-BackPacker
 * @date: 2018/7/16 17:11
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class PalyContentEvent {
    private String vid;

    public PalyContentEvent(String vid) {
        this.vid = vid;
    }

    public String getVid() {
        return vid;
    }
}

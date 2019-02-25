package com.xuechuan.xcedu.event;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.Event
 * @Description: 网课视频id
 * @author: L-BackPacker
 * @date: 2018/5/21 15:03
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class VideoIdEvent {
    private String videoId;
    public VideoIdEvent(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoId() {
        return videoId;
    }
}

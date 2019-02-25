package com.xuechuan.xcedu.event;

import com.xuechuan.xcedu.vo.VideosBeanVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.Event
 * @Description: 视频播放
 * @author: L-BackPacker
 * @date: 2018/5/15 17:19
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetPlayEvent {
    private VideosBeanVo vo;

    public NetPlayEvent(VideosBeanVo vo) {
        this.vo = vo;
    }

    public VideosBeanVo getVo() {
        return vo;
    }
}

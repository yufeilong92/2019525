package com.xuechuan.xcedu.vo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 保利直播vo
 * @author: L-BackPacker
 * @date: 2018.11.27 下午 2:05
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class PolyvliveBean {
    /**
     * liveurl : https://live.polyv.cn/watch/255742
     * channelnum : 256078
     * name : test
     * begintime : 2018-11-28 14:03:47
     * pushflowurl : rtmp://push2.videocc.net/recordfe/d740a5635720181113210320928
     * indexurl : http://img0.imgtn.bdimg.com/it/u=1090786583,2450690491&fm=200&gp=0.jpg
     */

    private String liveurl;
    private String channelnum;
    private String name;
    private String begintime;
    private String pushflowurl;
    private String indexurl;
    private String gifurl;
    private String descripturl;

    public String getDescripturl() {
        return descripturl;
    }

    public void setDescripturl(String descripturl) {
        this.descripturl = descripturl;
    }

    public String getGifurl() {
        return gifurl;
    }

    public void setGifurl(String gifurl) {
        this.gifurl = gifurl;
    }

    public String getLiveurl() {
        return liveurl;
    }

    public void setLiveurl(String liveurl) {
        this.liveurl = liveurl;
    }

    public String getChannelnum() {
        return channelnum;
    }

    public void setChannelnum(String channelnum) {
        this.channelnum = channelnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBegintime() {
        return begintime;
    }

    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    public String getPushflowurl() {
        return pushflowurl;
    }

    public void setPushflowurl(String pushflowurl) {
        this.pushflowurl = pushflowurl;
    }

    public String getIndexurl() {
        return indexurl;
    }

    public void setIndexurl(String indexurl) {
        this.indexurl = indexurl;
    }
}

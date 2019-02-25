package com.xuechuan.xcedu.vo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 处理下载下载详情选择问题
 * @author: L-BackPacker
 * @date: 2018/5/19 18:53
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DownInfomSelectVo {
    /**
     * 章节di
     */
    private String zid;
    /**
     * 篇id
     */
    private String pid;
    /**
     * 视频id保利
     */
    private String vid;
    /**
     * 清晰度
     */
    private String bitrate;


    /**
     * 选择按钮是否选中
     */
    private boolean isChbSelect;
    /**
     * 是否显示选择按钮
     */
    private boolean isShowChb;
    /**
     * 是否显示播放按钮
     */
    private boolean isShowPlay;
    /**
     * 播放按钮是否选中
     */
    private boolean isPlaySelect;

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getZid() {
        return zid;
    }

    public void setZid(String zid) {
        this.zid = zid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public boolean isChbSelect() {
        return isChbSelect;
    }

    public void setChbSelect(boolean chbSelect) {
        isChbSelect = chbSelect;
    }

    public boolean isShowChb() {
        return isShowChb;
    }

    public void setShowChb(boolean showChb) {
        isShowChb = showChb;
    }

    public boolean isShowPlay() {
        return isShowPlay;
    }

    public void setShowPlay(boolean showPlay) {
        isShowPlay = showPlay;
    }

    public boolean isPlaySelect() {
        return isPlaySelect;
    }

    public void setPlaySelect(boolean playSelect) {
        isPlaySelect = playSelect;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }
}

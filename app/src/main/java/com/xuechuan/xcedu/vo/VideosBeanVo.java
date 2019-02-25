package com.xuechuan.xcedu.vo;

import java.io.Serializable;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 视频vo
 * @author: L-BackPacker
 * @date: 2018/5/15 9:16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class VideosBeanVo implements Serializable{
    /**
     * s所属章节id
     */
    private int chapterid;
    /**
     * 是否试看
     */
    private boolean istrysee;
    /**
     * 	主讲人
     */
    private String speaker;
    /**
     * 	视频时长
     */
    private int timelong;
    /**
     * 视频播放参数，保利威视视频标识
     */
    private String vid;
    /**
     * 	视频编号
     */
    private int videoid;
    /**
     * 视频名
     */
    private String videoname;
    /**
     * 	排序
     */
    private int sort;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getChapterid() {
        return chapterid;
    }

    public void setChapterid(int chapterid) {
        this.chapterid = chapterid;
    }

    public boolean isIstrysee() {
        return istrysee;
    }

    public void setIstrysee(boolean istrysee) {
        this.istrysee = istrysee;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public int getTimelong() {
        return timelong;
    }

    public void setTimelong(int timelong) {
        this.timelong = timelong;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public int getVideoid() {
        return videoid;
    }

    public void setVideoid(int videoid) {
        this.videoid = videoid;
    }

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoname) {
        this.videoname = videoname;
    }
}

package com.xuechuan.xcedu.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 视频章节vo
 * @author: L-BackPacker
 * @date: 2018/5/15 9:17
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ChaptersBeanVo implements Serializable {
    /***
     * 所属章节编号
     */
    private int chapterid;
    /**
     * 章节名字
     */
    private String chaptername;
    /***
     * 	科目编号
     */
    private int courseid;
    /**
     * 	排序
     */
    private int sort;
    /**
     * 章节视频
     */
    private List<VideosBeanVo> videos;

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

    public String getChaptername() {
        return chaptername;
    }

    public void setChaptername(String chaptername) {
        this.chaptername = chaptername;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public List<VideosBeanVo> getVideos() {
        return videos;
    }

    public void setVideos(List<VideosBeanVo> videos) {
        this.videos = videos;
    }
}

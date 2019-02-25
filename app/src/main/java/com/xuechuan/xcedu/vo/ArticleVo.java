package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.utils.TimeUtil;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 文章列表
 * @author: L-BackPacker
 * @date: 2018/4/20 9:27
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ArticleVo {
    private String gourl;
    private int id;
    private boolean issupport;
    private String publishdate;
    private String supportcount;
    private String thumbnailimg;
    private String title;
    private int type;
    private int viewcount;
    private List<TagsBeanVo> tags;
    public List<TagsBeanVo> getTags() {
        return tags;
    }

    public void setTags(List<TagsBeanVo> tags) {
        this.tags = tags;
    }

    public String getGourl() {
        return gourl;
    }

    public void setGourl(String gourl) {
        this.gourl = gourl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIssupport() {
        return issupport;
    }

    public void setIssupport(boolean issupport) {
        this.issupport = issupport;
    }

    public String getPublishdate() {
        return TimeUtil.getYMDT(publishdate);
    }

    public void setPublishdate(String publishdate) {
        this.publishdate = publishdate;
    }

    public String getSupportcount() {
        return supportcount;
    }

    public void setSupportcount(String supportcount) {
        this.supportcount = supportcount;
    }

    public String getThumbnailimg() {
        return thumbnailimg;
    }

    public void setThumbnailimg(String thumbnailimg) {
        this.thumbnailimg = thumbnailimg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getViewcount() {
        return viewcount;
    }

    public String getStringViewcount() {
        return String.valueOf(viewcount);
    }

    public void setViewcount(int viewcount) {
        this.viewcount = viewcount;
    }
}

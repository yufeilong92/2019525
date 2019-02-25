package com.xuechuan.xcedu.vo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 资讯
 * @author: L-BackPacker
 * @date: 2018/4/18 20:36
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AdvisoryBean extends HomePageVo {
    /**
     * 来源
     */
    private String gourl;
    /**
     * id
     */
    private int id;
    /**
     * 省份code
     */
    private String provincecode;
    /**
     * 发布时间
     */
    private String publishdate;
    /**
     * 来源
     */
    private String source;
    /**
     * 网址
     */
    private String thumbnailimg;
    /**
     * 标题
     */
    private String title;
    /**
     * 类型
     */
    private int type;
    /**
     * 视图数
     */
    private int viewcount;
    /**
     * 分享url
     */
    private String shareurl;

    public String getShareurl() {
        return shareurl;
    }

    public void setShareurl(String shareurl) {
        this.shareurl = shareurl;
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

    public String getProvincecode() {
        return provincecode;
    }

    public void setProvincecode(String provincecode) {
        this.provincecode = provincecode;
    }

    public String getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(String publishdate) {
        this.publishdate = publishdate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public void setViewcount(int viewcount) {
        this.viewcount = viewcount;
    }
}

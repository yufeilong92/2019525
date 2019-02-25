package com.xuechuan.xcedu.vo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 轮播图
 * @author: L-BackPacker
 * @date: 2018/4/18 20:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BannerBean extends HomePageVo {
    /**
     * 跳转地址
     */
    private String gourl;
    /**
     * id
     */
    private int id;
    /**
     * 图片地址
     */
    private String imageurl;
    /**
     *
     */
    private int sort;
    /**
     *
     */
    private int type;

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

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

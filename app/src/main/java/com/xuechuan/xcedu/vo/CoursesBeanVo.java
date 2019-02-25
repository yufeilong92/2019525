package com.xuechuan.xcedu.vo;

import java.io.Serializable;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 网课item vo
 * @author: L-BackPacker
 * @date: 2018/5/14 9:22
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class CoursesBeanVo implements Serializable {
    /**
     * 课程id
     */
    private int id;
    /**
     * 科目
     */
    private int courseid;
    /**
     * 名字
     */
    private String name;
    /**
     * 图片
     */
    private String coverimg;
    /**
     * 价格
     */
    private double price;
    /**
     * 是否是全科
     */
    private boolean isall;
    /**
     * 详情地址
     */
    private String description;
    /**
     * 最后播放时间
     */
    private String lastmodifytime;
    /**
     * 开始时间
     */
    private String createtime;

    /**
     * 最后观看的视频名称
     *
     * @return
     */
    private String lastview;

    public String getLastview() {
        return lastview;
    }

    public void setLastview(String lastview) {
        this.lastview = lastview;
    }

    public String getLastmodifytime() {
        return lastmodifytime;
    }

    public void setLastmodifytime(String lastmodifytime) {
        this.lastmodifytime = lastmodifytime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverimg() {
        return coverimg;
    }

    public void setCoverimg(String coverimg) {
        this.coverimg = coverimg;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isIsall() {
        return isall;
    }

    public void setIsall(boolean isall) {
        this.isall = isall;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

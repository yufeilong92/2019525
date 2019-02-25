package com.xuechuan.xcedu.vo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 视屏的科目vo
 * @author: L-BackPacker
 * @date: 2018/5/15 9:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ClassBeanVideoVo {
    /**
     * 科目id
     */
    private int courseid;
    /**
     * 科目图片
     */
    private String coverimg;
    /**
     * 开始时间
     */
    private String createtime;
    /**
     *描述
     */
    private String description;
    /**
     * id
     */
    private int id;
    /**
     * 是否是全科
     */
    private boolean isall;
    /**
     * 	最后修改时间
     */
    private String lastmodifytime;
    /**
     * 课程名称
     */
    private String name;
    /**
     * 价格
     */
    private double price;

    /**
     * 详情地址
     */
    private String detailurl;

    public String getDetailurl() {
        return detailurl;
    }

    public void setDetailurl(String detailurl) {
        this.detailurl = detailurl;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public String getCoverimg() {
        return coverimg;
    }

    public void setCoverimg(String coverimg) {
        this.coverimg = coverimg;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIsall() {
        return isall;
    }

    public void setIsall(boolean isall) {
        this.isall = isall;
    }

    public String getLastmodifytime() {
        return lastmodifytime;
    }

    public void setLastmodifytime(String lastmodifytime) {
        this.lastmodifytime = lastmodifytime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

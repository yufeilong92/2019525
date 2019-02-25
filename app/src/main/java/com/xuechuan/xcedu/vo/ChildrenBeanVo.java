package com.xuechuan.xcedu.vo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description:  教程子类
 * @author: L-BackPacker
 * @date: 2018/4/23 10:16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ChildrenBeanVo extends BookHomePageVo {
    /**
     * 地址
     */
    private String gourl;
    /**
     * id
     */
    private int id;
    /**
     * 跳转时使用这个id
     */
    private int _id;

    /**
     * 是否终节点
     */
    private boolean isend;
    /**
     * 父级编号
     */
    private int parentid;
    /**
     * 标题
     */
    private String title;
    /**
     * 子题数据
     */
    private int qnum;
    /**
     * 做题记录索引
     */
    private int rnum;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getRnum() {
        return rnum;
    }

    public void setRnum(int rnum) {
        this.rnum = rnum;
    }

    private List<ChildrenBeanVo> children;

    public int getQnum() {
        return qnum;
    }

    public void setQnum(int qnum) {
        this.qnum = qnum;
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

    public boolean isIsend() {
        return isend;
    }

    public void setIsend(boolean isend) {
        this.isend = isend;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ChildrenBeanVo> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBeanVo> children) {
        this.children = children;
    }
}
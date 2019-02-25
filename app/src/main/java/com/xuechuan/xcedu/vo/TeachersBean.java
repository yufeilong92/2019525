package com.xuechuan.xcedu.vo;

import java.io.Serializable;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.11.27 下午 5:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class TeachersBean implements Serializable {
    /**
     * id : 1
     * name : 王老师 a
     * realname : 王老师 a
     * title : 老师 程序c
     * description : 简洁001a
     * headimg : http://192.168.1.111:8081ceshi001 b
     * headimg_small :
     * createtime : 2018-11-24 14:32:44
     * score : 0
     */
    private int id;//老师编号
    private String name;//老师姓名
    private String realname;//真实姓名
    private String title;//职称
    private String description;//简介
    private String headimg;//头像地址
    private String headimg_small;//小头像地址
    private String createtime;//创建时间
    private int score;//综合服务评分

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getHeadimg_small() {
        return headimg_small;
    }

    public void setHeadimg_small(String headimg_small) {
        this.headimg_small = headimg_small;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

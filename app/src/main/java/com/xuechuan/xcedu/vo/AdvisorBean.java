package com.xuechuan.xcedu.vo;

import java.io.Serializable;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.11.29 上午 10:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AdvisorBean implements Serializable{
    private static final long serialVersionUID = 3476968166472700319L;
    private int id;//课程顾问编号
    private String name;//姓名
    private String headimg;//头像
    private String wechatimg;//微信图片地址
    private String telphone;//手机号

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

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getWechatimg() {
        return wechatimg;
    }

    public void setWechatimg(String wechatimg) {
        this.wechatimg = wechatimg;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
}

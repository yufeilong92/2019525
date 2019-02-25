package com.xuechuan.xcedu.vo;

import java.io.Serializable;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 我的网课标题
 * @author: L-BackPacker
 * @date: 2018.11.29 下午 3:23
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class TagsBean implements Serializable{
    /**
     * id : 1
     * tagname : 技术实务
     */

    private int id;
    private String tagname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }
}

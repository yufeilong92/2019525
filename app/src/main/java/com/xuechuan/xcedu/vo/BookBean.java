package com.xuechuan.xcedu.vo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 推荐图书
 * @author: L-BackPacker
 * @date: 2018.11.27 下午 2:07
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BookBean {
    /**
     * id : 119
     * alias : alidass654321
     * item_id : null
     * youzanurl : detail_ur654321
     * price : 1019.0
     * title : cessiabv654321
     * img : imga654321
     */

    private int id;
    private String alias;
    private Object item_id;
    private String youzanurl;
    private double price;
    private String title;
    private String img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Object getItem_id() {
        return item_id;
    }

    public void setItem_id(Object item_id) {
        this.item_id = item_id;
    }

    public String getYouzanurl() {
        return youzanurl;
    }

    public void setYouzanurl(String youzanurl) {
        this.youzanurl = youzanurl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

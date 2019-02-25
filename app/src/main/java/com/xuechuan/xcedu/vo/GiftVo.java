package com.xuechuan.xcedu.vo;

import java.io.Serializable;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/8/25 14:10
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class GiftVo implements Serializable {

    /**
     * coverimg : https://img.yzcdn.cn/upload_files/2017/05/22/FuyWLeN5Zm9oXwVFbLM60qAdGaHx.png
     * id : 3
     * itemid : 272672840
     * name : 现货发售丨注册消防工程师综合案例分析
     * price : 16800.0
     */

    private String coverimg;
    private int giftid;
    private int itemid;
    private String giftname;
    private double price;
    /**
     * 订单id
     */
    private  int oid;

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    private int num;
    /**
     * 是否主商品
     * （自己添加）
     */
    private boolean isMainProducts;
    /**
     * 快递公司
     */
    private String express_company;

    /**
     * 快递单号
     *
     * @return
     */
    private String express_number;

    /**
     * @return
     */
    private int state;

    /**
     * @return
     */
    private int itemtype;
    private boolean isgift;

    public boolean isIsgift() {
        return isgift;
    }

    public void setIsgift(boolean isgift) {
        this.isgift = isgift;
    }

    public boolean isMainProducts() {
        return isMainProducts;
    }

    public void setMainProducts(boolean mainProducts) {
        isMainProducts = mainProducts;
    }

    public String getExpress_company() {
        return express_company;
    }

    public void setExpress_company(String express_company) {
        this.express_company = express_company;
    }

    public String getExpress_number() {
        return express_number;
    }

    public void setExpress_number(String express_number) {
        this.express_number = express_number;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getItemtype() {
        return itemtype;
    }

    public void setItemtype(int itemtype) {
        this.itemtype = itemtype;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCoverimg() {
        return coverimg;
    }

    public void setCoverimg(String coverimg) {
        this.coverimg = coverimg;
    }

    public int getId() {
        return giftid;
    }

    public void setId(int id) {
        this.giftid = id;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public String getName() {
        return giftname;
    }

    public void setName(String name) {
        this.giftname = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

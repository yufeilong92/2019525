package com.xuechuan.xcedu.vo;

import java.io.Serializable;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 兑换信息
 * @author: L-BackPacker
 * @date: 2018/7/24 15:31
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ExchangeinfoBean implements Serializable{
    /**
     * 	兑换状态false,为兑换，true 已兑换
     */
    private boolean exchangestate;
    /***
     * 产品编号
     */
    private int productid;
    /**
     * 兑换产品信息
     */
    private String productname;
    /**
     * 是否有兑换
     */
    private int producttype;

    public boolean isExchangestate() {
        return exchangestate;
    }

    public void setExchangestate(boolean exchangestate) {
        this.exchangestate = exchangestate;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public int getProducttype() {
        return producttype;
    }

    public void setProducttype(int producttype) {
        this.producttype = producttype;
    }
}

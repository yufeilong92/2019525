package com.xuechuan.xcedu.vo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 微信下单
 * @author: L-BackPacker
 * @date: 2018/5/23 11:58
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class WechatsignBeanVo implements Serializable {
    /**
     * 随机字符串
     */
    private String noncestr;
    /**
     *扩展字段
     */
    private String packageX;
    /**
     * 商户号
     */
    private String partnerid;
    /**
     * 付预支付交易会话ID
     */
    private String prepayid;
    /**
     *签名
     */
    private String sign;
    /**
     * 时间戳
     */
    private String timespan;

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimespan() {
        return timespan;
    }

    public void setTimespan(String timespan) {
        this.timespan = timespan;
    }
}

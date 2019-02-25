package com.xuechuan.xcedu.vo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 用户数据
 * @author: L-BackPacker
 * @date: 2018/4/8 14:48
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class HttpInfomVo {
    /**
     * 时间撮
     */
    private String timeStamp;
    /**
     * 随机数
     */
    private String nonce;
    /**
     * token
     */
    private String token;
    /**
     * 用户标识
     */
    private String staffid;
    /**
     * token时效性
     */
    private String ExpireTime;




    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getToken() {
        return token;
}

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "HttpInfomVo{" +
                "timeStamp='" + timeStamp + '\'' +
                ", nonce='" + nonce + '\'' +
                ", token='" + token + '\'' +
                ", staffid='" + staffid + '\'' +
                ", ExpireTime='" + ExpireTime + '\'' +
                '}';
    }
}

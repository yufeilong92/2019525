package com.xuechuan.xcedu.vo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 用于数据库加密
 * @author: L-BackPacker
 * @date: 2018.12.08 上午 10:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class EncrypVo implements Serializable{

    /**
     * delete : delete
     * do : do
     */

    private String delete;
    @SerializedName("do")
    private String doX;

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public String getDoX() {
        return doX;
    }

    public void setDoX(String doX) {
        this.doX = doX;
    }
}

package com.xuechuan.xcedu.vo;

import com.google.gson.annotations.SerializedName;
import com.xuechuan.xcedu.db.DownVideoDb;

import java.io.Serializable;
import java.util.ArrayList;

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
public class EncrypBaseVo implements Serializable {
    /**
     * 是否删除以前数据
     */
    private String delete;
    /**
     * 是否执行操作
     */
    private String isdo;
    /**
     * 以前下载数据
     */
    private ArrayList<DownVideoDbVo> downVideoDbs;

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public String getIsdo() {
        return isdo;
    }

    public void setIsdo(String isdo) {
        this.isdo = isdo;
    }

    public ArrayList<DownVideoDbVo> getDownVideoDbs() {
        return downVideoDbs;
    }

    public void setDownVideoDbs(ArrayList<DownVideoDbVo> downVideoDbs) {
        this.downVideoDbs = downVideoDbs;
    }
}

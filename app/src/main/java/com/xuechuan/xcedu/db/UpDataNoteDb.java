package com.xuechuan.xcedu.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.db
 * @Description: 记录版本更行日志
 * @author: L-BackPacker
 * @date: 2018/8/3 9:09
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
@Entity
public class UpDataNoteDb {
    @Id(autoincrement = true)
    private Long id;
    /**
     * 是否执行更新
     */
    private boolean isupdata ;
    /**
     * 更新日志
     */
    private String upatanote;
    /**
     * 上一代版本号
     */
    private int oldVersion;
    /**
     * 当前版本
     */
    private int newVersion;
    @Generated(hash = 1768517356)
    public UpDataNoteDb(Long id, boolean isupdata, String upatanote, int oldVersion,
            int newVersion) {
        this.id = id;
        this.isupdata = isupdata;
        this.upatanote = upatanote;
        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
    }
    @Generated(hash = 1830271585)
    public UpDataNoteDb() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public boolean getIsupdata() {
        return this.isupdata;
    }
    public void setIsupdata(boolean isupdata) {
        this.isupdata = isupdata;
    }
    public String getUpatanote() {
        return this.upatanote;
    }
    public void setUpatanote(String upatanote) {
        this.upatanote = upatanote;
    }
    public int getOldVersion() {
        return this.oldVersion;
    }
    public void setOldVersion(int oldVersion) {
        this.oldVersion = oldVersion;
    }
    public int getNewVersion() {
        return this.newVersion;
    }
    public void setNewVersion(int newVersion) {
        this.newVersion = newVersion;
    }

}

package com.xuechuan.xcedu.vo.SqliteVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo.SqliteVo
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.15 下午 1:54
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class KaoShiSqliteVo {
    private int id;
    private int  timekey;
    private String saffid;
    private int isDo;
    private int chapter_id;
    private int usertime;
    private double socre;

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }

    public int getUsertime() {
        return usertime;
    }

    public void setUsertime(int usertime) {
        this.usertime = usertime;
    }

    public double getSocre() {
        return socre;
    }

    public void setSocre(double socre) {
        this.socre = socre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimekey() {
        return timekey;
    }

    public void setTimekey(int timekey) {
        this.timekey = timekey;
    }

    public String getSaffid() {
        return saffid;
    }

    public void setSaffid(String saffid) {
        this.saffid = saffid;
    }

    public int getIsDo() {
        return isDo;
    }

    public void setIsDo(int isDo) {
        this.isDo = isDo;
    }
}

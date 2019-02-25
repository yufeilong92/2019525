package com.xuechuan.xcedu.vo.SqliteVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo.SqliteVo
 * @Description: 用户错题记录表
 * @author: L-BackPacker
 * @date: 2018.12.10 上午 11:42
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ErrorSqliteVo {
    private int id;
    private int chapterid;//章节id
    private int courseid;//科目id
    private int rightnumber;//正确次数
    private String rightAllNumber;//用户设置总对数
    private String userid;//用户id
    private String time;//时间
    private int  quesitonid;//问题id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChapterid() {
        return chapterid;
    }

    public void setChapterid(int chapterid) {
        this.chapterid = chapterid;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public int getRightnumber() {
        return rightnumber;
    }

    public void setRightnumber(int rightnumber) {
        this.rightnumber = rightnumber;
    }

    public String getRightAllNumber() {
        return rightAllNumber;
    }

    public void setRightAllNumber(String rightAllNumber) {
        this.rightAllNumber = rightAllNumber;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getQuesitonid() {
        return quesitonid;
    }

    public void setQuesitonid(int quesitonid) {
        this.quesitonid = quesitonid;
    }
}

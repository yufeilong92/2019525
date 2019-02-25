package com.xuechuan.xcedu.vo;

import java.io.Serializable;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.12.26 下午 6:54
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DayHomeBeanVo implements Serializable{

    /**
     * courseid : 3
     * date : 2018-12-26 00:00:00
     * id : 21
     * keyword : 红旗渠测试-a
     * questionnum : 4
     */

    private int courseid;
    private String date;
    private int id;
    private String keyword;
    private int questionnum;

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getQuestionnum() {
        return questionnum;
    }

    public void setQuestionnum(int questionnum) {
        this.questionnum = questionnum;
    }
}

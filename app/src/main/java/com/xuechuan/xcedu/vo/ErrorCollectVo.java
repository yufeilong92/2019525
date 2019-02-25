package com.xuechuan.xcedu.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 错题或者收藏
 * @author: L-BackPacker
 * @date: 2019/1/3 20:59
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class ErrorCollectVo implements Serializable{
    private int chapter_id;
    private int coursed;
    private String chapter_name;
    private int questionid;
    private int id;
    private int num;//当前观看记录
    private ArrayList<ErrorCollectVo> child;
    private List<Integer> childOne;
    public int getNum() {
        return num;
    }

    public List<Integer> getChildOne() {
        return childOne;
    }

    public void setChildOne(List<Integer> childOne) {
        this.childOne = childOne;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }

    public int getCoursed() {
        return coursed;
    }

    public void setCoursed(int coursed) {
        this.coursed = coursed;
    }

    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }

    public int getQuestionid() {
        return questionid;
    }

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }

    public ArrayList<ErrorCollectVo> getChild() {
        return child;
    }

    public void setChild(ArrayList<ErrorCollectVo> child) {
        this.child = child;
    }
}

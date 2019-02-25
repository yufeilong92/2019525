package com.xuechuan.xcedu.vo.SqliteVo;

import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo.SqliteVo
 * @Description: 做题帮助类
 * @author: L-BackPacker
 * @date: 2018.12.14 上午 10:40
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DoBankSqliteVo {
    private int id;
    private int question_id;
    private int mockkeyid;//做题主键(评价问题主键id)
    private int isright;//0为未选，1为正确 ，2 为漏选 ，3为错误 4,自评
    private int selectA;
    private int selectB;
    private int selectC;
    private int selectD;
    private int selectE;
    private int isDo; //是否已做，或者已评价
    private int questiontype;//题干类型
    private int chapterid;//章节
    private int courseid;//科目id
    private int parent_id;//自评父id
    private int child_id;//自评子id
    private String mos;//自评得分
    private int ismos;//是否自评
    private int islook;//是否查看解析
    private String time;
    private int isAnalySis;//是否是解析

    public int getIslook() {
        return islook;
    }

    public void setIslook(int islook) {
        this.islook = islook;
    }

    public int getIsAnalySis() {
        return isAnalySis;
    }

    public void setIsAnalySis(int isAnalySis) {
        this.isAnalySis = isAnalySis;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getChild_id() {
        return child_id;
    }

    public void setChild_id(int child_id) {
        this.child_id = child_id;
    }

    public String getMos() {
        return mos;
    }

    public void setMos(String mos) {
        this.mos = mos;
    }

    public int getIsmos() {
        return ismos;
    }

    public void setIsmos(int ismos) {
        this.ismos = ismos;
    }

    private String analysis;//用户输入解析

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public int getMockkeyid() {
        return mockkeyid;
    }

    public void setMockkeyid(int mockkeyid) {
        this.mockkeyid = mockkeyid;
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

    public int getQuestiontype() {
        return questiontype;
    }

    public void setQuestiontype(int questiontype) {
        this.questiontype = questiontype;
    }

    public int getIsDo() {
        return isDo;
    }

    public void setIsDo(int isDo) {
        this.isDo = isDo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getIsright() {
        return isright;
    }

    public void setIsright(int isright) {
        this.isright = isright;
    }

    public int getSelectA() {
        return selectA;
    }

    public void setSelectA(int selectA) {
        this.selectA = selectA;
    }

    public int getSelectB() {
        return selectB;
    }

    public void setSelectB(int selectB) {
        this.selectB = selectB;
    }

    public int getSelectC() {
        return selectC;
    }

    public void setSelectC(int selectC) {
        this.selectC = selectC;
    }

    public int getSelectD() {
        return selectD;
    }

    public void setSelectD(int selectD) {
        this.selectD = selectD;
    }

    public int getSelectE() {
        return selectE;
    }

    public void setSelectE(int selectE) {
        this.selectE = selectE;
    }
}

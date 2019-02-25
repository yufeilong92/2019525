package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 问题详情
 * @author: L-BackPacker
 * @date: 2018/4/27 12:41
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class TextDetailVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * 练习题编号
         */
        private int id;
        /**
         *问题题干
         */
        private String question;
        /**
         *问题类型2单选3多选4问答题
         */
        private int questiontype;
        /**
         * 选项a
         */
        private String a;
        /**
         * 选项b
         */
        private String b;
        /***
         * 选项c
         */
        private String c;
        /**
         * d
         */
        private String d;
        /**
         * e
         */
        private String e;
        /**
         * 选择题答案
         */
        private String choiceanswer;
        /**
         * 解析
         */
        private String analysis;
        /**
         * 难度
         */
        private int difficultydegreee;
        /**
         * 正确率
         */
        private String accuracy;
        /**
         * v所属科目
         */
        private int courseid;
        /**
         *  所在章节末节章节
         */
        private int chapterid;
        /**
         *问题模型1题库2历年真题3独家密卷
         */
        private int questionmold;
        /**
         *是否已收藏True收藏False未收藏
         */
        private boolean isfav;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public int getQuestiontype() {
            return questiontype;
        }

        public void setQuestiontype(int questiontype) {
            this.questiontype = questiontype;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }

        public String getD() {
            return d;
        }

        public void setD(String d) {
            this.d = d;
        }

        public String getE() {
            return e;
        }

        public void setE(String e) {
            this.e = e;
        }

        public String getChoiceanswer() {
            return choiceanswer;
        }

        public void setChoiceanswer(String choiceanswer) {
            this.choiceanswer = choiceanswer;
        }

        public String getAnalysis() {
            return analysis;
        }

        public void setAnalysis(String analysis) {
            this.analysis = analysis;
        }

        public int getDifficultydegreee() {
            return difficultydegreee;
        }

        public void setDifficultydegreee(int difficultydegreee) {
            this.difficultydegreee = difficultydegreee;
        }

        public String getAccuracy() {
            return accuracy;
        }

        public void setAccuracy(String accuracy) {
            this.accuracy = accuracy;
        }

        public int getCourseid() {
            return courseid;
        }

        public void setCourseid(int courseid) {
            this.courseid = courseid;
        }

        public int getChapterid() {
            return chapterid;
        }

        public void setChapterid(int chapterid) {
            this.chapterid = chapterid;
        }

        public int getQuestionmold() {
            return questionmold;
        }

        public void setQuestionmold(int questionmold) {
            this.questionmold = questionmold;
        }

        public boolean isIsfav() {
            return isfav;
        }

        public void setIsfav(boolean isfav) {
            this.isfav = isfav;
        }
    }
}

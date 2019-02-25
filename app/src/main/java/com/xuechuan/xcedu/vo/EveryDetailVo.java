package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 每日一练详情类
 * @author: L-BackPacker
 * @date: 2018.12.29 下午 2:23
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class EveryDetailVo extends BaseVo {

    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * chapter_id : 109
         * choice_answer : B
         * courseid : 1
         * difficulty : 2
         * explainimg :
         * explainstr : 硫、磷、钾、钠、蜡烛、松香等可燃固体，在受到火源加热时，先熔融蒸发，随后蒸气与氧气发生燃烧反应，这种形式的燃烧一般称为蒸发燃烧。本题答案为B。
         * id : 3813
         * keywordsstr : 蒸发燃烧
         * option_a : 焦炭的燃烧
         * option_b : 松香的燃烧
         * option_c : 煤的燃烧
         * option_d : 铁的燃烧
         * option_e :
         * parent_id : 0
         * question_mold : 1
         * questionimg :
         * questionstr : 下列属于蒸发燃烧的是（      ）。
         * questiontype : 2
         * right_rate : 18.45
         * score : 0.0
         */

        private int chapter_id;
        private String choice_answer;
        private int courseid;
        private int difficulty;
        private String explainimg;
        private String explainstr;
        private int id;
        private String keywordsstr;
        private String option_a;
        private String option_b;
        private String option_c;
        private String option_d;
        private String option_e;
        private int parent_id;
        private int question_mold;
        private String questionimg;
        private String questionstr;
        private int questiontype;
        private double right_rate;
        private double score;

        public int getChapter_id() {
            return chapter_id;
        }

        public void setChapter_id(int chapter_id) {
            this.chapter_id = chapter_id;
        }

        public String getChoice_answer() {
            return choice_answer;
        }

        public void setChoice_answer(String choice_answer) {
            this.choice_answer = choice_answer;
        }

        public int getCourseid() {
            return courseid;
        }

        public void setCourseid(int courseid) {
            this.courseid = courseid;
        }

        public int getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(int difficulty) {
            this.difficulty = difficulty;
        }

        public String getExplainimg() {
            return explainimg;
        }

        public void setExplainimg(String explainimg) {
            this.explainimg = explainimg;
        }

        public String getExplainstr() {
            return explainstr;
        }

        public void setExplainstr(String explainstr) {
            this.explainstr = explainstr;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getKeywordsstr() {
            return keywordsstr;
        }

        public void setKeywordsstr(String keywordsstr) {
            this.keywordsstr = keywordsstr;
        }

        public String getOption_a() {
            return option_a;
        }

        public void setOption_a(String option_a) {
            this.option_a = option_a;
        }

        public String getOption_b() {
            return option_b;
        }

        public void setOption_b(String option_b) {
            this.option_b = option_b;
        }

        public String getOption_c() {
            return option_c;
        }

        public void setOption_c(String option_c) {
            this.option_c = option_c;
        }

        public String getOption_d() {
            return option_d;
        }

        public void setOption_d(String option_d) {
            this.option_d = option_d;
        }

        public String getOption_e() {
            return option_e;
        }

        public void setOption_e(String option_e) {
            this.option_e = option_e;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public int getQuestion_mold() {
            return question_mold;
        }

        public void setQuestion_mold(int question_mold) {
            this.question_mold = question_mold;
        }

        public String getQuestionimg() {
            return questionimg;
        }

        public void setQuestionimg(String questionimg) {
            this.questionimg = questionimg;
        }

        public String getQuestionstr() {
            return questionstr;
        }

        public void setQuestionstr(String questionstr) {
            this.questionstr = questionstr;
        }

        public int getQuestiontype() {
            return questiontype;
        }

        public void setQuestiontype(int questiontype) {
            this.questiontype = questiontype;
        }

        public double getRight_rate() {
            return right_rate;
        }

        public void setRight_rate(double right_rate) {
            this.right_rate = right_rate;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }


}

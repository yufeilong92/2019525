package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: t题干信息
 * @author: L-BackPacker
 * @date: 2018/5/8 21:17
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ResultQuesitonVo extends BaseVo {

    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * chapterid : 186
         * courseid : 3
         * id : 2956
         * question : 某体育馆长89.32m，宽52.4m，比赛场地位于一层中部，观众席共三层，总建筑面积为14247.4㎡，总座位数6075座，其中固定座椅4050座，活动座椅2025座。比赛主馆屋面标高为28.8m。建筑耐火等级为一级。并按规范要求设置了消防设施。请分析以下问题：<br/><br/>（1）确定建筑物构建燃烧性能。<br/><br/><br/><br/>（2）确定消防车道应的形式。<br/><br/><br/><br/>（3）该体育馆消防车道有何技术要求？<br/><br/><br/><br/>（4）确定该体育馆内的防火分区面积。<br/><br/><br/><br/>（5）确定观众厅的疏散门数量。
         */

        private int chapterid;
        private int courseid;
        private int id;
        private String question;

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
    }
}

package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 所有问题vo
 * @author: L-BackPacker
 * @date: 2018/7/27 17:31
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class QuestionAllVoNew  extends BaseVo{
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
//        第几题
        private int rnum;
        private List<QuestionsBean> questions;

        public int getRnum() {
            return rnum;
        }

        public void setRnum(int rnum) {
            this.rnum = rnum;
        }

        public List<QuestionsBean> getQuestions() {
            return questions;
        }

        public void setQuestions(List<QuestionsBean> questions) {
            this.questions = questions;
        }

//        public static class QuestionsBean {
//            /**
//             * 文章id
//             */
//            private int chapterid;
//            private int courseid;
//            private int difficultydegree;
//            private int id;
//            private int type;
//
//            public int getChapterid() {
//                return chapterid;
//            }
//
//            public void setChapterid(int chapterid) {
//                this.chapterid = chapterid;
//            }
//
//            public int getCourseid() {
//                return courseid;
//            }
//
//            public void setCourseid(int courseid) {
//                this.courseid = courseid;
//            }
//
//            public int getDifficultydegree() {
//                return difficultydegree;
//            }
//
//            public void setDifficultydegree(int difficultydegree) {
//                this.difficultydegree = difficultydegree;
//            }
//
//            public int getId() {
//                return id;
//            }
//
//            public void setId(int id) {
//                this.id = id;
//            }
//
//            public int getType() {
//                return type;
//            }
//
//            public void setType(int type) {
//                this.type = type;
//            }
//        }
    }
}

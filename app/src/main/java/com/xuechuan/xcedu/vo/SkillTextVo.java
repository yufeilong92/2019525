package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 资讯
 * @author: L-BackPacker
 * @date: 2018/4/27 10:41
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SkillTextVo extends BaseVo {


    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * 章节编号
         */
        private int id;
        /**
         * 该题总题数(本地数据库加得字段)
         */
        private String questionNumber;

        /**
         * 是否末节
         */
        private boolean isend;
        /**
         * 父级编号
         */
        private int parentid;
        /**
         * 标题
         */
        private String title;
        /**
         *  做题记录索引
         */
        private int rnum;
        /**
         * 子题数据
         */
        private int qnum;

        public int getRnum() {
            return rnum;
        }

        public void setRnum(int rnum) {
            this.rnum = rnum;
        }

        public String getQuestionNumber() {
            return questionNumber;
        }

        public void setQuestionNumber(String questionNumber) {
            this.questionNumber = questionNumber;
        }

        private List<ChildrenBeanVo> children;

        public int getQnum() {
            return qnum;
        }

        public void setQnum(int qnum) {
            this.qnum = qnum;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isIsend() {
            return isend;
        }

        public void setIsend(boolean isend) {
            this.isend = isend;
        }

        public int getParentid() {
            return parentid;
        }

        public void setParentid(int parentid) {
            this.parentid = parentid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ChildrenBeanVo> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBeanVo> children) {
            this.children = children;
        }
/*
        public static class ChildrenBean {
            *//**
         * children : []
         * id : 109
         * isend : true
         * parentid : 1
         * title : 第一篇第一章 燃烧基础知识
         *//*

            private int id;
            private boolean isend;
            private int parentid;
            private String title;
            private List<?> children;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public boolean isIsend() {
                return isend;
            }

            public void setIsend(boolean isend) {
                this.isend = isend;
            }

            public int getParentid() {
                return parentid;
            }

            public void setParentid(int parentid) {
                this.parentid = parentid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<?> getChildren() {
                return children;
            }

            public void setChildren(List<?> children) {
                this.children = children;
            }
        }*/
    }
}

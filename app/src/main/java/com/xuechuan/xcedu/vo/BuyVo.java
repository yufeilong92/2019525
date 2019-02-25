package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 用户是否购买
 * @author: L-BackPacker
 * @date: 2018/4/27 22:08
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BuyVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * 	true已购买，false未购买
         */
        private boolean isbought;
        /**
         * 当前科目
         */
        private int courseid;
        /**
         *技术实务是否购买
         */
        private boolean course1;
        /**
         *综合能力是否购买
         */
        private boolean course2;
        /**
         *	案例分析是否购买
         */
        private boolean course3;

        public boolean isCourse1() {
            return course1;
        }

        public void setCourse1(boolean course1) {
            this.course1 = course1;
        }

        public boolean isCourse2() {
            return course2;
        }

        public void setCourse2(boolean course2) {
            this.course2 = course2;
        }

        public boolean isCourse3() {
            return course3;
        }

        public void setCourse3(boolean course3) {
            this.course3 = course3;
        }

        public boolean isIsbought() {
            return isbought;
        }

        public void setIsbought(boolean isbought) {
            this.isbought = isbought;
        }

        public int getCourseid() {
            return courseid;
        }

        public void setCourseid(int courseid) {
            this.courseid = courseid;
        }
    }
}

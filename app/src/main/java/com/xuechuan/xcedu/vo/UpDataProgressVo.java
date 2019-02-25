package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.15 下午 3:47
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class UpDataProgressVo extends BaseVo {

    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * courseid : 0
         * qt : 7
         * rnum : 4
         * targetid : -16
         */

        private int courseid;
        private int qt;
        private int rnum;
        private int targetid;

        public int getCourseid() {
            return courseid;
        }

        public void setCourseid(int courseid) {
            this.courseid = courseid;
        }

        public int getQt() {
            return qt;
        }

        public void setQt(int qt) {
            this.qt = qt;
        }

        public int getRnum() {
            return rnum;
        }

        public void setRnum(int rnum) {
            this.rnum = rnum;
        }

        public int getTargetid() {
            return targetid;
        }

        public void setTargetid(int targetid) {
            this.targetid = targetid;
        }
    }
}

package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 试卷vo
 * @author: L-BackPacker
 * @date: 2019.01.09 下午 3:40
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class ExamHistoryVo extends BaseVo {

    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * chapterid : 9962
         * finishtime : 2019-01-07 17:20:55
         * id : 36
         * score : 25.1
         * usetime : 2
         */

        private int chapterid;
        private String finishtime;
        private int id;
        private double score;
        private int usetime;
        //本地标识（后加）
        private int timekey;

        public int getTimekey() {
            return timekey;
        }

        public void setTimekey(int timekey) {
            this.timekey = timekey;
        }

        public int getChapterid() {
            return chapterid;
        }

        public void setChapterid(int chapterid) {
            this.chapterid = chapterid;
        }

        public String getFinishtime() {
            return finishtime;
        }

        public void setFinishtime(String finishtime) {
            this.finishtime = finishtime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public int getUsetime() {
            return usetime;
        }

        public void setUsetime(int usetime) {
            this.usetime = usetime;
        }
    }
}

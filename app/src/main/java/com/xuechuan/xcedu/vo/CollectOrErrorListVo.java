package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 获取所有错题vo
 * @author: L-BackPacker
 * @date: 2019.01.08 下午 5:10
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class CollectOrErrorListVo extends BaseVo{

    private List<ErrorOrCollectItemVo> datas;

    public List<ErrorOrCollectItemVo> getDatas() {
        return datas;
    }

    public void setDatas(List<ErrorOrCollectItemVo> datas) {
        this.datas = datas;
    }

/*    public static class DatasBean {
        *//**
         * courseid : 1
         * questionid : 2580
         *//*

        private int courseid;
        private int questionid;

        public int getCourseid() {
            return courseid;
        }

        public void setCourseid(int courseid) {
            this.courseid = courseid;
        }

        public int getQuestionid() {
            return questionid;
        }

        public void setQuestionid(int questionid) {
            this.questionid = questionid;
        }
    }*/
}

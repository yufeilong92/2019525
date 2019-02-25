package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 错题集合数据
 * @author: L-BackPacker
 * @date: 2018/7/28 17:54
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ErrOrColListVoNew extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * 记录数
         */
        private int rnum;
        /**
         *  问题id
         */
        private List<Integer> questionids;

        public int getRnum() {
            return rnum;
        }

        public void setRnum(int rnum) {
            this.rnum = rnum;
        }

        public List<Integer> getQuestionids() {
            return questionids;
        }

        public void setQuestionids(List<Integer> questionids) {
            this.questionids = questionids;
        }
    }
}

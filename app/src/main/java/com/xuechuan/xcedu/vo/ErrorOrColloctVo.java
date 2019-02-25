package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 错题和收藏题
 * @author: L-BackPacker
 * @date: 2018/5/5 12:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ErrorOrColloctVo extends BaseVo {

    /**
     * data : {"error":39,"favorite":1,"question":1355}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        /**
         * 错题数
         */
        private int error;
        /**
         * 收藏数
         */
        private int favorite;
        /**
         * 总数
         */
        private int question;

        public int getError() {
            return error;
        }

        public void setError(int error) {
            this.error = error;
        }

        public int getFavorite() {
            return favorite;
        }

        public void setFavorite(int favorite) {
            this.favorite = favorite;
        }

        public int getQuestion() {
            return question;
        }

        public void setQuestion(int question) {
            this.question = question;
        }
    }
}

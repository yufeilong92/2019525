package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 问题笔记
 * @author: L-BackPacker
 * @date: 2019.01.02 下午 4:56
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class QuestionNoteVo extends BaseVo {

    /**
     * data : {"content":"","createtime":"2019-01-02 16:55:38","id":0,"questionid":0}
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
         * content :
         * createtime : 2019-01-02 16:55:38
         * id : 0
         * questionid : 0
         */

        private String content;
        private String createtime;
        private int id;
        private int questionid;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getQuestionid() {
            return questionid;
        }

        public void setQuestionid(int questionid) {
            this.questionid = questionid;
        }
    }
}

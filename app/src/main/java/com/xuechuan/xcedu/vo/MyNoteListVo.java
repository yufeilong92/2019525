package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.03 上午 9:04
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class MyNoteListVo extends BaseVo {

    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * content : content
         * createtime : 2018-12-26 17:45:31
         * id : 3
         * questionid : 2570
         * tags : [{"questionid":2570,"tagid":13,"tagname":"消防基础知识"}]
         */

        private String content;
        private String createtime;
        private int id;
        private int questionid;
        private List<TagsBean> tags;

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

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public static class TagsBean {
            /**
             * questionid : 2570
             * tagid : 13
             * tagname : 消防基础知识
             */

            private int questionid;
            private int tagid;
            private String tagname;

            public int getQuestionid() {
                return questionid;
            }

            public void setQuestionid(int questionid) {
                this.questionid = questionid;
            }

            public int getTagid() {
                return tagid;
            }

            public void setTagid(int tagid) {
                this.tagid = tagid;
            }

            public String getTagname() {
                return tagname;
            }

            public void setTagname(String tagname) {
                this.tagname = tagname;
            }
        }
    }
}

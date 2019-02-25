package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2019.01.11 上午 11:35
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019
 */
public class MyProblemVo extends BaseVo {

    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * content : 测试图片
         * id : 17
         * isread : false
         * lastmodifytime : 2019-01-11 11:01:55
         * problemstatus : 0
         */

        private String content;
        private int id;
        private boolean isread;
        private String lastmodifytime;
        private int problemstatus;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isIsread() {
            return isread;
        }

        public void setIsread(boolean isread) {
            this.isread = isread;
        }

        public String getLastmodifytime() {
            return lastmodifytime;
        }

        public void setLastmodifytime(String lastmodifytime) {
            this.lastmodifytime = lastmodifytime;
        }

        public int getProblemstatus() {
            return problemstatus;
        }

        public void setProblemstatus(int problemstatus) {
            this.problemstatus = problemstatus;
        }
    }
}

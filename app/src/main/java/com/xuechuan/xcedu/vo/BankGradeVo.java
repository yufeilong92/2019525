package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 题库更新vo
 * @author: L-BackPacker
 * @date: 2018.12.06 下午 3:35
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BankGradeVo extends BaseVo {

    /**
     * data : {"remark":"test","type":"cover","url":"http://192.168.1.111:8081/questiondb/1/1.4.zip","versioncode":6,"versionname":"1.4"}
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
         * remark : test
         * type : cover
         * url : http://192.168.1.111:8081/questiondb/1/1.4.zip
         * versioncode : 6
         * versionname : 1.4
         */

        private String remark;
        private String type;
        private String url;
        private int versioncode;
        private String versionname;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getVersioncode() {
            return versioncode;
        }

        public void setVersioncode(int versioncode) {
            this.versioncode = versioncode;
        }

        public String getVersionname() {
            return versionname;
        }

        public void setVersionname(String versionname) {
            this.versionname = versionname;
        }
    }
}

package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 错误信息
 * @author: L-BackPacker
 * @date: 2018/5/3 10:51
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ErrorVo extends BaseVo {

    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * 数量
         */
        private int count;
        /**
         * 标签编号
         */
        private int tagid;
        /**
         * 标签名
         */
        private String tagname;
        /**
         * 当前题号索引
         */
        private int rnum;

        public int getRnum() {
            return rnum;
        }

        public void setRnum(int rnum) {
            this.rnum = rnum;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
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

package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 教程 课才
 * @author: L-BackPacker
 * @date: 2018/4/23 10:15
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BookHomePageVo extends BaseVo {

    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        private int id;
        private boolean isend;
        private int parentid;
        private String title;
        private List<ChildrenBeanVo> children;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isIsend() {
            return isend;
        }

        public void setIsend(boolean isend) {
            this.isend = isend;
        }

        public int getParentid() {
            return parentid;
        }

        public void setParentid(int parentid) {
            this.parentid = parentid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ChildrenBeanVo> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBeanVo> children) {
            this.children = children;
        }


    }
}

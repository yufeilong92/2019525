package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 标签结果
 * @author: L-BackPacker
 * @date: 2018/5/13 14:11
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class TagListVo extends BaseVo {

    private List<ArticleVo> datas;

    public List<ArticleVo> getDatas() {
        return datas;
    }

    public void setDatas(List<ArticleVo> datas) {
        this.datas = datas;
    }

   /* public static class DatasBean {
        *//**
         * id : 8
         * issupport : true
         * publishdate : 2018-04-23 17:35:28
         * supportcount : 0
         * tags : [{"id":3,"name":"建筑"},{"id":4,"name":"学川快讯"},{"id":5,"name":"咨询"}]
         * thumbnailimg : /imgs/201804/23/123a141544504423baab62174b4bf478.png
         * title : 消防规范：KTV火灾惨案18死5伤，违反了哪些消防规范？
         * type : 0
         * viewcount : 0
         *//*
        private String gourl;
        private int id;
        private boolean issupport;
        private String publishdate;
        private int supportcount;
        private String thumbnailimg;
        private String title;
        private int type;
        private int viewcount;
        private List<TagsBeanVo> tags;

        public String getGourl() {
            return gourl;
        }

        public void setGourl(String gourl) {
            this.gourl = gourl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isIssupport() {
            return issupport;
        }

        public void setIssupport(boolean issupport) {
            this.issupport = issupport;
        }

        public String getPublishdate() {
            return publishdate;
        }

        public void setPublishdate(String publishdate) {
            this.publishdate = publishdate;
        }

        public int getSupportcount() {
            return supportcount;
        }

        public void setSupportcount(int supportcount) {
            this.supportcount = supportcount;
        }

        public String getThumbnailimg() {
            return thumbnailimg;
        }

        public void setThumbnailimg(String thumbnailimg) {
            this.thumbnailimg = thumbnailimg;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getViewcount() {
            return viewcount;
        }

        public void setViewcount(int viewcount) {
            this.viewcount = viewcount;
        }

        public List<TagsBeanVo> getTags() {
            return tags;
        }

        public void setTags(List<TagsBeanVo> tags) {
            this.tags = tags;
        }


    }*/
}

package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.io.Serializable;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 评价
 * @author: L-BackPacker
 * @date: 2018/4/25 13:41
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class EvalueVo extends BaseVo {
    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean implements Serializable {

        /**
         * 评论编号
         */
        private int id;
        /**
         *文章目标编号
         */
        private int targetid;
        /**
         * 内容
         */
        private String content;
        /**
         * 评论数
         */
        private int commentcount;
        /**
         * 赞数
         */
        private int supportcount;
        /**
         * 针对评论的评论编号
         */
        private int commentid;
        /**
         * 用户编号
         */
        private int memberid;
        /**
         * 用户昵称
         */
        private String nickname;
        /**
         * 头像
         */
        private String headicon;
        /**
         * 是否是管理园回复
         */
        private boolean isadmin;
        /**
         * 是否已赞
         */
        private boolean issupport;
        private String createtime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTargetid() {
            return targetid;
        }

        public void setTargetid(int targetid) {
            this.targetid = targetid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCommentcount() {
            return commentcount;
        }

        public void setCommentcount(int commentcount) {
            this.commentcount = commentcount;
        }

        public int getSupportcount() {
            return supportcount;
        }

        public void setSupportcount(int supportcount) {
            this.supportcount = supportcount;
        }

        public int getCommentid() {
            return commentid;
        }

        public void setCommentid(int commentid) {
            this.commentid = commentid;
        }

        public int getMemberid() {
            return memberid;
        }

        public void setMemberid(int memberid) {
            this.memberid = memberid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHeadicon() {
            return headicon;
        }

        public void setHeadicon(String headicon) {
            this.headicon = headicon;
        }

        public boolean isIsadmin() {
            return isadmin;
        }

        public void setIsadmin(boolean isadmin) {
            this.isadmin = isadmin;
        }

        public boolean isIssupport() {
            return issupport;
        }

        public void setIssupport(boolean issupport) {
            this.issupport = issupport;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }
    }
}

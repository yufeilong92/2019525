package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description:  评价信息
 * @author: L-BackPacker
 * @date: 2018/5/4 8:28
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class EvalueInfomVo extends BaseVo {

    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * id : 3041
         * targetid : 143
         * content : 明
         * commentcount : 0
         * supportcount : 0
         * commentid : 0
         * memberid : 1010
         * nickname : u38326
         * headicon :
         * isadmin : false
         * issupport : true
         * createtime : 2018-05-03 21:57:09
         */

        private int id;
        private int targetid;
        private String content;
        private int commentcount;
        private int supportcount;
        private int commentid;
        private int memberid;
        private String nickname;
        private String headicon;
        private boolean isadmin;
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

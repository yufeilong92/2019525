package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 我的消息
 * @author: L-BackPacker
 * @date: 2018/6/1 19:27
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyMsgVo extends BaseVo {

    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * id : 8
         * title : waterm赞了您
         * notifytype : 2
         * source : 来自文章：李克强：消防、人防等并入施工图审查 将工程建设项目审批时间压减一半以上
         * createtime : 2018-06-01 19:26:08
         * sourcetype : 1
         * targetid : 16
         * targetcontent : Qqq
         * content : null
         * membername : waterm
         * memberheadicon : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLnDAUFqMy41kCUuzL4Kqj3dmjFibw0zc3dXHeP9tDpicdzsbyRusibSiaLOt4tKRqCtP2icX2bz40eFmA/132
         * isread : true
         */
        /**
         * id
         */
        private int id;
        /**
         * b标题
         */
        private String title;
        /**
         * 	1,评论，2赞
         */
        private int notifytype;
        /**
         * 来源
         */
        private String source;
        /**
         * 创建时间
         */
        private String createtime;
        /**
         * 来源类型

         1,文章评论赞

         2,文章评论

         3,题库评论赞

         4,题库评论

         5,视频评论赞

         6,视频评论
         */
        private int sourcetype;
        /**
         *
         */
        private int targetid;
        /**
         * 主题内容
         */
        private String targetcontent;
        /**
         * 评论内容
         */
        private String content;
        /**
         *用户名
         */
        private String membername;
        /**
         * 头像
         */
        private String memberheadicon;
        /**
         *  是否阅读
         */
        private boolean isread;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getNotifytype() {
            return notifytype;
        }

        public void setNotifytype(int notifytype) {
            this.notifytype = notifytype;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getSourcetype() {
            return sourcetype;
        }

        public void setSourcetype(int sourcetype) {
            this.sourcetype = sourcetype;
        }

        public int getTargetid() {
            return targetid;
        }

        public void setTargetid(int targetid) {
            this.targetid = targetid;
        }

        public String getTargetcontent() {
            return targetcontent;
        }

        public void setTargetcontent(String targetcontent) {
            this.targetcontent = targetcontent;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMembername() {
            return membername;
        }

        public void setMembername(String membername) {
            this.membername = membername;
        }

        public String getMemberheadicon() {
            return memberheadicon;
        }

        public void setMemberheadicon(String memberheadicon) {
            this.memberheadicon = memberheadicon;
        }

        public boolean isIsread() {
            return isread;
        }

        public void setIsread(boolean isread) {
            this.isread = isread;
        }
    }
}

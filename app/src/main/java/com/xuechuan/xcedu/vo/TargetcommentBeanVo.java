package com.xuechuan.xcedu.vo;

import java.io.Serializable;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 评价信息类
 * @author: L-BackPacker
 * @date: 2018/6/2 8:58
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class TargetcommentBeanVo implements Serializable {
    /**
     * commentcount : 0
     * commentid : 0
     * content : 品轮测试
     * createtime : 2018-06-01 17:31:18
     * headicon : http://thirdwx.qlogo.cn/mmopen/vi_32/sfVX0u3mBcFPvNPq973CCNbJYIPy6xDDia5Y4nSaAEr23O7wObV4lVn1sFJUgqLKCYgsyjqStub9hXCqdhAzQDQ/132
     * id : 23
     * isadmin : false
     * issupport : false
     * memberid : 1015
     * nickname : 青衣素酒客��จุ๊บ
     * supportcount : 0
     * targetid : 16
     */

    private int commentcount;
    private int commentid;
    private String content;
    private String createtime;
    private String headicon;
    private int id;
    private boolean isadmin;
    private boolean issupport;
    private int memberid;
    private String nickname;
    private int supportcount;
    private int targetid;

    public int getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(int commentcount) {
        this.commentcount = commentcount;
    }

    public int getCommentid() {
        return commentid;
    }

    public void setCommentid(int commentid) {
        this.commentid = commentid;
    }

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

    public String getHeadicon() {
        return headicon;
    }

    public void setHeadicon(String headicon) {
        this.headicon = headicon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSupportcount() {
        return supportcount;
    }

    public void setSupportcount(int supportcount) {
        this.supportcount = supportcount;
    }

    public int getTargetid() {
        return targetid;
    }

    public void setTargetid(int targetid) {
        this.targetid = targetid;
    }


}

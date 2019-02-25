package com.xuechuan.xcedu.vo.Db;

import java.io.Serializable;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo.Db
 * @Description: 用户查看视频记录
 * @author: L-BackPacker
 * @date: 2018/5/21 19:39
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class UserLookVideoVo implements Serializable {
    /**
     * 课目id
     */
    private String kid;
    /**
     * 篇id
     */
    private String pid;
    /**
     * 视频id
     */
    private String zid;
    /**
     * 进度
     */
    private String progress;
    /**
     * 视频名称
     */
    private String titleName;
    /**
     * 保利id
     */
    private String vid;

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getZid() {
        return zid;
    }

    public void setZid(String zid) {
        this.zid = zid;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
}

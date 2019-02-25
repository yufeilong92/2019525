package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.vo.Db.DownVideoVo;

import java.io.Serializable;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 复制数据库累
 * @author: L-BackPacker
 * @date: 2018.12.08 上午 11:29
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DownVideoDbVo implements Serializable {

    private long id;
    /**
     * 用户id
     */
    private String staffid;
    /**
     * 课目id
     */
    private String kid;
    /**
     * 课目封面
     */
    private String urlImg;
    /**
     * 课目名字
     */
    private String kName;
    /**
     * 保利视频id
     */
    private String vid;
    /**
     * 播放时长
     */
    private String time;

    private List<DownVideoVo> downlist;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getkName() {
        return kName;
    }

    public void setkName(String kName) {
        this.kName = kName;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<DownVideoVo> getDownlist() {
        return downlist;
    }

    public void setDownlist(List<DownVideoVo> downlist) {
        this.downlist = downlist;
    }
}

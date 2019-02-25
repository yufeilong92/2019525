package com.xuechuan.xcedu.db;

import com.xuechuan.xcedu.db.Converent.DownVideoConverent;
import com.xuechuan.xcedu.db.Converent.UserLookConverent;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.db
 * @Description: 临时记录用户选择
 * @author: L-BackPacker
 * @date: 2018/5/9 11:56
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
@Entity
public class DownVideoDb  {
    @Id(autoincrement = true)
    private Long id;
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

    @Convert(converter = DownVideoConverent.class, columnType = String.class)
    private List<DownVideoVo> downlist;


    @Generated(hash = 215552143)
    public DownVideoDb(Long id, String staffid, String kid, String urlImg,
                       String kName, String vid, String time, List<DownVideoVo> downlist) {
        this.id = id;
        this.staffid = staffid;
        this.kid = kid;
        this.urlImg = urlImg;
        this.kName = kName;
        this.vid = vid;
        this.time = time;
        this.downlist = downlist;
    }

    @Generated(hash = 1189966955)
    public DownVideoDb() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStaffid() {
        return this.staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getKid() {
        return this.kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getUrlImg() {
        return this.urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getKName() {
        return this.kName;
    }

    public void setKName(String kName) {
        this.kName = kName;
    }

    public String getVid() {
        return this.vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<DownVideoVo> getDownlist() {
        return this.downlist;
    }

    public void setDownlist(List<DownVideoVo> downlist) {
        this.downlist = downlist;
    }


}

package com.xuechuan.xcedu.vo.Db;

import java.io.Serializable;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 记录视频数据
 * @author: L-BackPacker
 * @date: 2018/5/17 17:29
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */

public class DownVideoVo implements Serializable{
    /**
     * 篇id
     */
    private String pid;
    /**
     * 篇名字
     */
    private String pName;
    /**
     * 章id
     */
    private String zid;

    /**
     * 保利视频id
     */
    private String vid;
    /**
     * 当前视频的id
     */
    private String VideoOid;

    /**
     * 清晰度 1流畅 2 高清 3超清
     */
    private String bitRate;

    /**
     * 总时长
     */
    private String duration;
    /**
     * 文件大小
     */
    private long fileSize;
    /**
     * 标题
     */
    private String title;
    /**
     * 已下载的文件大小(mp4)/已下载的文件个数(ts)
     */
    private long percent;
    /**
     * 总文件大小
     */
    private long total;
    /**
     * 完成状态 0为完成 1已经开始 2等待
     */
    private String status;
    /**
     *  篇排序
     */
    private String psort;
    /**
     * 章排序
     */
    private String zsort;

    public String getPsort() {
        return psort==null?"0":psort;
    }

    public void setPsort(String psort) {
        this.psort = psort;
    }

    public String getZsort() {
        return zsort==null?"0":zsort;
    }

    public void setZsort(String zsort) {
        this.zsort = zsort;
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

    public String getBitRate() {
        return bitRate;
    }

    public void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPercent() {
        return percent;
    }

    public void setPercent(long percent) {
        this.percent = percent;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVideoOid() {
        return VideoOid;
    }

    public void setVideoOid(String videoOid) {
        VideoOid = videoOid;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }
}

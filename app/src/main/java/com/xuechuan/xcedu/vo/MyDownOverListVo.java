package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.db.Converent.DownVideoConverent;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;

import org.greenrobot.greendao.annotation.Convert;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 下载结果Vo
 * @author: L-BackPacker
 * @date: 2018/8/6 17:10
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyDownOverListVo {
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

    private List<PdownListVo> downlist;

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

    public List<PdownListVo> getDownlist() {
        return downlist;
    }

    public void setDownlist(List<PdownListVo> downlist) {
        this.downlist = downlist;
    }

    public static class PdownListVo {
        /**
         * 篇id
         */
        private String chapterid;
        /**
         * 篇名字
         */
        private String pName;
        /**
         * 排序字段
         */
        private String sort;

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        private ArrayList<zDownListVo> zDownlist;

        public String getChapterid() {
            return chapterid;
        }

        public void setChapterid(String chapterid) {
            this.chapterid = chapterid;
        }

        public String getpName() {
            return pName;
        }

        public void setpName(String pName) {
            this.pName = pName;
        }

        public ArrayList<zDownListVo> getzDownlist() {
            return zDownlist;
        }

        public void setzDownlist(ArrayList<zDownListVo> zDownlist) {
            this.zDownlist = zDownlist;
        }
    }

    public static  class zDownListVo {
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
         * 篇排序
         */
        private String psort;
        /**
         * 章排序
         */
        private String zsort;

        public String getZid() {
            return zid;
        }

        public void setZid(String zid) {
            this.zid = zid;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getVideoOid() {
            return VideoOid;
        }

        public void setVideoOid(String videoOid) {
            VideoOid = videoOid;
        }

        public String getBitRate() {
            return bitRate;
        }

        public void setBitRate(String bitRate) {
            this.bitRate = bitRate;
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

        public String getpName() {
            return pName;
        }

        public void setpName(String pName) {
            this.pName = pName;
        }

        public String getPid() {

            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }
    }
}

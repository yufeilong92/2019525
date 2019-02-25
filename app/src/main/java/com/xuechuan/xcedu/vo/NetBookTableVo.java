package com.xuechuan.xcedu.vo;

import com.google.gson.annotations.SerializedName;
import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 视频列表类
 * @author: L-BackPacker
 * @date: 2018/5/15 9:12
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetBookTableVo extends BaseVo {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        @SerializedName("class")
        /**
         * 当前科目（2017技术，综合等）详情vo
         */
        private ClassBeanVideoVo classX;
        /**
         * 所属科目的视频信息
         */
        private List<ChaptersBeanVo> chapters;
        /**
         * 视频进度
         */
        private PlayProgressBeanVo playprogress;

        /**
         * 赠品信息
         * @return
         */
        private List<GiftVo>gifts;

        public List<GiftVo> getGifts() {
            return gifts;
        }

        public void setGifts(List<GiftVo> gifts) {
            this.gifts = gifts;
        }

        public PlayProgressBeanVo getPlayprogress() {
            return playprogress;
        }

        public void setPlayprogress(PlayProgressBeanVo playprogress) {
            this.playprogress = playprogress;
        }

        public ClassBeanVideoVo getClassX() {
            return classX;
        }

        public void setClassX(ClassBeanVideoVo classX) {
            this.classX = classX;
        }

        public List<ChaptersBeanVo> getChapters() {
            return chapters;
        }

        public void setChapters(List<ChaptersBeanVo> chapters) {
            this.chapters = chapters;
        }
    }
}

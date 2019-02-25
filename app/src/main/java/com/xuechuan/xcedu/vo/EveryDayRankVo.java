package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 每日一练排行
 * @author: L-BackPacker
 * @date: 2018.12.29 下午 2:50
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class EveryDayRankVo extends BaseVo {


    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {

        private int staffid; //用户id
        private String nickname; //用户名称
        private String headicon;//头像
        private int duration; //事件（秒）

        public int getStaffid() {
            return staffid;
        }

        public void setStaffid(int staffid) {
            this.staffid = staffid;
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

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }
    }
}

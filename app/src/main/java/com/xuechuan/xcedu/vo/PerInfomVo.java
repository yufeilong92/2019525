package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.io.Serializable;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 个人信息
 * @author: L-BackPacker
 * @date: 2018/5/25 17:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class PerInfomVo extends BaseVo {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        private static final long serialVersionUID = -7451285274196851743L;
        /**
         * 生日
         */
        private String birthday;
        /**
         * 城市
         */
        private String city;
        /**
         * 性别
         */
        private int gender;
        /**
         * 头像
         */
        private String headicon;
        /**
         * id
         */
        private int id;
        /**
         *
         */
        private boolean ischeck;
        /**
         * 是否有新的消息
         */
        private boolean ishasnews;
        /**
         * 昵称
         */
        private String nickname;
        /**
         * 电话
         */
        private String phone;
        /**
         * 省
         */
        private String province;
        /**
         * 否有未读消息
         */
        private boolean ishavemembernotify;
        /**
         * 是否有未读通知
         */
        private boolean ishavesystemnotify;
        /**
         * 是否有待确认赠品
         */
        private boolean ishavewaitaddress;
        /**
         * 课程顾问
         */
        private AdvisorBean advisor;

        public AdvisorBean getAdvisor() {
            return advisor;
        }

        public void setAdvisor(AdvisorBean advisor) {
            this.advisor = advisor;
        }

        public boolean isIshavewaitaddress() {
            return ishavewaitaddress;
        }

        public void setIshavewaitaddress(boolean ishavewaitaddress) {
            this.ishavewaitaddress = ishavewaitaddress;
        }

        public boolean isIshavemembernotify() {
            return ishavemembernotify;
        }

        public void setIshavemembernotify(boolean ishavemembernotify) {
            this.ishavemembernotify = ishavemembernotify;
        }

        public boolean isIshavesystemnotify() {
            return ishavesystemnotify;
        }

        public void setIshavesystemnotify(boolean ishavesystemnotify) {
            this.ishavesystemnotify = ishavesystemnotify;
        }

        /**
         *
         */
        private List<ThirdaccountBean> thirdaccount;

        public boolean isIshasnews() {
            return ishasnews;
        }

        public void setIshasnews(boolean ishasnews) {
            this.ishasnews = ishasnews;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
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

        public boolean isIscheck() {
            return ischeck;
        }

        public void setIscheck(boolean ischeck) {
            this.ischeck = ischeck;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public List<ThirdaccountBean> getThirdaccount() {
            return thirdaccount;
        }

        public void setThirdaccount(List<ThirdaccountBean> thirdaccount) {
            this.thirdaccount = thirdaccount;
        }

        public static class ThirdaccountBean implements Serializable {

            /**
             * 微信昵称
             */
            private String nickname;
            /**
             * 微信
             */
            private String platform;

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getPlatform() {
                return platform;
            }

            public void setPlatform(String platform) {
                this.platform = platform;
            }
        }
    }
}

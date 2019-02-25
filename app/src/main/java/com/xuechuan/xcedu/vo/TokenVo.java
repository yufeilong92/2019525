package com.xuechuan.xcedu.vo;

import com.google.gson.annotations.SerializedName;
import com.xuechuan.xcedu.base.BaseVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: token
 * @author: L-BackPacker
 * @date: 2018/5/9 15:22
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class TokenVo extends BaseVo {


    /**
     * data : {"status":1,"info":"","token":{"staffid":1010,"signtoken":"868af344de1a4e209c52417cf32be847","expiretime":"2018-05-24 17:10:57"}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * status : 1
         * info :
         * token : {"staffid":1010,"signtoken":"868af344de1a4e209c52417cf32be847","expiretime":"2018-05-24 17:10:57"}
         */
        @SerializedName("status")
        private int statusX;
        private String info;
        private TokenBean token;
        private YouzanuserBean youzanuser;

        public YouzanuserBean getYouZanUser() {
            return youzanuser;
        }

        public void setYouZanUser(YouzanuserBean youZanUser) {
            this.youzanuser = youZanUser;
        }

        public int getStatusX() {
            return statusX;
        }

        public void setStatusX(int statusX) {
            this.statusX = statusX;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public TokenBean getToken() {
            return token;
        }

        public void setToken(TokenBean token) {
            this.token = token;
        }

        public static class TokenBean {
            /**
             * staffid : 1010
             * signtoken : 868af344de1a4e209c52417cf32be847
             * expiretime : 2018-05-24 17:10:57
             */

            private int staffid;
            private String signtoken;
            private String expiretime;

            public int getStaffid() {
                return staffid;
            }

            public void setStaffid(int staffid) {
                this.staffid = staffid;
            }

            public String getSigntoken() {
                return signtoken;
            }

            public void setSigntoken(String signtoken) {
                this.signtoken = signtoken;
            }

            public String getExpiretime() {
                return expiretime;
            }

            public void setExpiretime(String expiretime) {
                this.expiretime = expiretime;
            }
        }
    }
}

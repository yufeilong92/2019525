package com.xuechuan.xcedu.vo;

import com.google.gson.annotations.SerializedName;
import com.xuechuan.xcedu.base.BaseVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description:  验证码结果
 * @author: L-BackPacker
 * @date: 2018/7/24 15:21
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class GenuienVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        /**
         * 只有为1时表示成功，其他表示失败
         * status
         */
        @SerializedName("status")
        private int statusX;
        /**
         * 描述信息
         */
        private String message;
        /**
         * 上次查询次数
         */
        private int querynum;
        /**
         * 	是否有兑换信息
         */
        private boolean haveexchange;

        /**
         * 兑换信息
         */
        private ExchangeinfoBean exchangeinfo;

        public int getStatusX() {
            return statusX;
        }

        public void setStatusX(int statusX) {
            this.statusX = statusX;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getQuerynum() {
            return querynum;
        }

        public void setQuerynum(int querynum) {
            this.querynum = querynum;
        }

        public boolean isHaveexchange() {
            return haveexchange;
        }

        public void setHaveexchange(boolean haveexchange) {
            this.haveexchange = haveexchange;
        }

        public ExchangeinfoBean getExchangeinfo() {
            return exchangeinfo;
        }

        public void setExchangeinfo(ExchangeinfoBean exchangeinfo) {
            this.exchangeinfo = exchangeinfo;
        }

    }
}

package com.xuechuan.xcedu.vo;

import com.google.gson.annotations.SerializedName;
import com.xuechuan.xcedu.base.BaseVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 购买结果Vo
 * @author: L-BackPacker
 * @date: 2018/5/23 11:57
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BuyFromResultVo extends BaseVo {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String message;
        @SerializedName("status")
        private int statusX;
        private WechatsignBeanVo wechatsign;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatusX() {
            return statusX;
        }

        public void setStatusX(int statusX) {
            this.statusX = statusX;
        }

        public WechatsignBeanVo getWechatsign() {
            return wechatsign;
        }

        public void setWechatsign(WechatsignBeanVo wechatsign) {
            this.wechatsign = wechatsign;
        }


    }
}

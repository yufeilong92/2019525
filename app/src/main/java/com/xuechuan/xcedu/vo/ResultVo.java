package com.xuechuan.xcedu.vo;

import com.google.gson.annotations.SerializedName;
import com.xuechuan.xcedu.base.BaseVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 通用结果Vo
 * @author: L-BackPacker
 * @date: 2018/5/11 15:40
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ResultVo extends BaseVo {


    /**
     * data : {"message":"","status":1}
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
         * message :
         * status : 1
         */

        private String message;
        @SerializedName("status")
        private int statusX;

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
    }
}

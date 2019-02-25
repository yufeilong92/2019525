package com.xuechuan.xcedu.base;

import android.os.Parcel;
import android.os.Parcelable;

import com.xuechuan.xcedu.vo.TotalBeanVo;
import com.xuechuan.xcedu.vo.TotalVo;

import java.io.Serializable;


/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.base
 * @Description: 基础vo
 * @author: L-BackPacker
 * @date: 2018/4/10 15:27
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BaseVo implements Serializable {

    private static final long serialVersionUID = -4316564845660466881L;

    /**
     * 状态码
     */
    private StatusBean status;

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    private TotalBeanVo total;

    public TotalBeanVo getTotal() {
        return total;
    }

    public void setTotal(TotalBeanVo total) {
        this.total = total;
    }

    public static class StatusBean {

        /**
         * 成功码（200）
         */
        private int code;
        /**
         * 信息
         */
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}

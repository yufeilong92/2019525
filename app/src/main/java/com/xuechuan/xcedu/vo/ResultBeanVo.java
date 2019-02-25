package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 请求结果数据
 * @author: L-BackPacker
 * @date: 2018/4/8 11:17
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ResultBeanVo extends BaseVo {
    private static final long serialVersionUID = -1187399594874356432L;

    private int StatusCode;
    private String Info;
    private DataBean Data;


    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int StatusCode) {
        this.StatusCode = StatusCode;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String Info) {
        this.Info = Info;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {

        private int StaffId;
        private String SignToken;
        private String ExpireTime;

        public int getStaffId() {
            return StaffId;
        }

        public void setStaffId(int StaffId) {
            this.StaffId = StaffId;
        }

        public String getSignToken() {
            return SignToken;
        }

        public void setSignToken(String SignToken) {
            this.SignToken = SignToken;
        }

        public String getExpireTime() {
            return ExpireTime;
        }

        public void setExpireTime(String ExpireTime) {
            this.ExpireTime = ExpireTime;
        }
    }
}

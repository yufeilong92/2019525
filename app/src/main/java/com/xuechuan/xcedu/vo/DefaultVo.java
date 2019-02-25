package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description:  会员地址
 * @author: L-BackPacker
 * @date: 2018/8/25 16:03
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DefaultVo extends BaseVo {

    private AddressItemsBean data;

    public AddressItemsBean getData() {
        return data;
    }

    public void setData(AddressItemsBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private String province;
        private String city;
        private String area;
        private String address;
        private String post;
        private String receivename;
        private String telphone;
        private String createtime;
        private boolean isdefault;
        private int staffid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPost() {
            return post;
        }

        public void setPost(String post) {
            this.post = post;
        }

        public String getReceivename() {
            return receivename;
        }

        public void setReceivename(String receivename) {
            this.receivename = receivename;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public boolean isIsdefault() {
            return isdefault;
        }

        public void setIsdefault(boolean isdefault) {
            this.isdefault = isdefault;
        }

        public int getStaffid() {
            return staffid;
        }

        public void setStaffid(int staffid) {
            this.staffid = staffid;
        }
    }
}

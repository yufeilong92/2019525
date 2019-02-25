package com.xuechuan.xcedu.vo;

import com.google.gson.annotations.SerializedName;
import com.xuechuan.xcedu.base.BaseVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 用户密码修改
 * @author: L-BackPacker
 * @date: 2018/5/31 19:55
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ChangerPawVo extends BaseVo {

    /**
     * data : {"status":1,"info":"","user":{"id":1015,"province":"河南省","city":"开封市","birthday":"2018-05-28","gender":1,"headicon":"http://thirdwx.qlogo.cn/mmopen/vi_32/sfVX0u3mBcFPvNPq973CCNbJYIPy6xDDia5Y4nSaAEr23O7wObV4lVn1sFJUgqLKCYgsyjqStub9hXCqdhAzQDQ/132","nickname":"青衣素酒客��จุ๊บ","phone":"18317837561","uuid":"9cc2fe25e1ab9015","token":"a6790f4c0e4c4bef93c96f1662a99cb2","tokenexpire":"2018-06-16 09:07:16"}}
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
         * user : {"id":1015,"province":"河南省","city":"开封市","birthday":"2018-05-28","gender":1,"headicon":"http://thirdwx.qlogo.cn/mmopen/vi_32/sfVX0u3mBcFPvNPq973CCNbJYIPy6xDDia5Y4nSaAEr23O7wObV4lVn1sFJUgqLKCYgsyjqStub9hXCqdhAzQDQ/132","nickname":"青衣素酒客��จุ๊บ","phone":"18317837561","uuid":"9cc2fe25e1ab9015","token":"a6790f4c0e4c4bef93c96f1662a99cb2","tokenexpire":"2018-06-16 09:07:16"}
         */

        @SerializedName("status")
        private int statusX;
        private String info;
        private UserBean user;

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

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * id : 1015
             * province : 河南省
             * city : 开封市
             * birthday : 2018-05-28
             * gender : 1
             * headicon : http://thirdwx.qlogo.cn/mmopen/vi_32/sfVX0u3mBcFPvNPq973CCNbJYIPy6xDDia5Y4nSaAEr23O7wObV4lVn1sFJUgqLKCYgsyjqStub9hXCqdhAzQDQ/132
             * nickname : 青衣素酒客��จุ๊บ
             * phone : 18317837561
             * uuid : 9cc2fe25e1ab9015
             * token : a6790f4c0e4c4bef93c96f1662a99cb2
             * tokenexpire : 2018-06-16 09:07:16
             */

            private int id;
            private String province;
            private String city;
            private String birthday;
            private int gender;
            private String headicon;
            private String nickname;
            private String phone;
            private String uuid;
            private String token;
            private String tokenexpire;

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

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
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

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getTokenexpire() {
                return tokenexpire;
            }

            public void setTokenexpire(String tokenexpire) {
                this.tokenexpire = tokenexpire;
            }
        }
    }
}

package com.xuechuan.xcedu.vo;

import com.google.gson.annotations.SerializedName;
import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 物流类vo
 * @author: L-BackPacker
 * @date: 2018/8/28 11:12
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class OrderTracesVo extends BaseVo {

    private DataBeanX data;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {

        private ResponseBean response;
        private String express_company;
        private String express_code;
        private String receiver_name;
        private String receiver_mobile;
        private String receiver_state;
        private String receiver_city;
        private String receiver_district;
        private String receiver_address;

        public ResponseBean getResponse() {
            return response;
        }

        public void setResponse(ResponseBean response) {
            this.response = response;
        }

        public String getExpress_company() {
            return express_company;
        }

        public void setExpress_company(String express_company) {
            this.express_company = express_company;
        }

        public String getExpress_code() {
            return express_code;
        }

        public void setExpress_code(String express_code) {
            this.express_code = express_code;
        }

        public String getReceiver_name() {
            return receiver_name;
        }

        public void setReceiver_name(String receiver_name) {
            this.receiver_name = receiver_name;
        }

        public String getReceiver_mobile() {
            return receiver_mobile;
        }

        public void setReceiver_mobile(String receiver_mobile) {
            this.receiver_mobile = receiver_mobile;
        }

        public String getReceiver_state() {
            return receiver_state;
        }

        public void setReceiver_state(String receiver_state) {
            this.receiver_state = receiver_state;
        }

        public String getReceiver_city() {
            return receiver_city;
        }

        public void setReceiver_city(String receiver_city) {
            this.receiver_city = receiver_city;
        }

        public String getReceiver_district() {
            return receiver_district;
        }

        public void setReceiver_district(String receiver_district) {
            this.receiver_district = receiver_district;
        }

        public String getReceiver_address() {
            return receiver_address;
        }

        public void setReceiver_address(String receiver_address) {
            this.receiver_address = receiver_address;
        }

        public static class ResponseBean {
            private List<OrderexpresslistBean> orderexpresslist;

            public List<OrderexpresslistBean> getOrderexpresslist() {
                return orderexpresslist;
            }

            public void setOrderexpresslist(List<OrderexpresslistBean> orderexpresslist) {
                this.orderexpresslist = orderexpresslist;
            }

            public static class OrderexpresslistBean {

                private String com;
                private String created_time;
                private String express_id;
                private String nu;
                private String ep_condition;
                private String id;
                private String state;
                private String message;
                @SerializedName("status")
                private String statusX;
                private List<DataBean> data;

                public String getCom() {
                    return com;
                }

                public void setCom(String com) {
                    this.com = com;
                }

                public String getCreated_time() {
                    return created_time;
                }

                public void setCreated_time(String created_time) {
                    this.created_time = created_time;
                }

                public String getExpress_id() {
                    return express_id;
                }

                public void setExpress_id(String express_id) {
                    this.express_id = express_id;
                }

                public String getNu() {
                    return nu;
                }

                public void setNu(String nu) {
                    this.nu = nu;
                }

                public String getEp_condition() {
                    return ep_condition;
                }

                public void setEp_condition(String ep_condition) {
                    this.ep_condition = ep_condition;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }

                public String getMessage() {
                    return message;
                }

                public void setMessage(String message) {
                    this.message = message;
                }

                public String getStatusX() {
                    return statusX;
                }

                public void setStatusX(String statusX) {
                    this.statusX = statusX;
                }

                public List<DataBean> getData() {
                    return data;
                }

                public void setData(List<DataBean> data) {
                    this.data = data;
                }

                public static class DataBean {
                    /**
                     * context : 货物已交付京东物流
                     * time : 2018-08-20 10:34:59
                     * status :
                     */

                    private String context;
                    private String time;
                    @SerializedName("status")
                    private String statusX;

                    public String getContext() {
                        return context;
                    }

                    public void setContext(String context) {
                        this.context = context;
                    }

                    public String getTime() {
                        return time;
                    }

                    public void setTime(String time) {
                        this.time = time;
                    }

                    public String getStatusX() {
                        return statusX;
                    }

                    public void setStatusX(String statusX) {
                        this.statusX = statusX;
                    }
                }
            }
        }
    }
}

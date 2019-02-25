package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 订单详情Vo
 * @author: L-BackPacker
 * @date: 2018/8/27 14:40
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class OrderInfomdVo extends BaseVo {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ordernum : 18082710163970733
         * ordertype : 2
         * totalprice : 0.0
         * yzstate :
         * state : -1
         * discounts : 0.0
         * seller_message : null
         * createtime : 2018-08-27 10:16:39
         * receiver_name : null
         * receiver_mobile : null
         * receiver_state : null
         * receiver_city : null
         * receiver_district : null
         * receiver_address : null
         * details : [{"productid":37,"productname":"测试产品","itemtype":60,"price":0,"num":1,"coverimg":"http://192.168.1.111:8081/upload/image/201806/28/a521c73086a94750a89edf454c16496b.png","state":12}]
         * gifts : [{"giftid":3,"giftname":"现货发售丨注册消防工程师综合案例分析","itemtype":0,"price":0,"num":1,"coverimg":"https://img.yzcdn.cn/upload_files/2017/05/22/FuyWLeN5Zm9oXwVFbLM60qAdGaHx.png","express_company":"","express_number":"","state":0},{"giftid":4,"giftname":"预售丨2018版《注册消防工程师考试一本通》，通关必备！","itemtype":0,"price":0,"num":1,"coverimg":"https://img.yzcdn.cn/upload_files/2018/07/14/FiYAbH2xeADbo98c9jcaeB5ZNkzJ.jpg","express_company":"","express_number":"","state":0}]
         */

        private String ordernum;
        private int ordertype;
        private double totalprice;
        private String yzstate;
        private int state;
        private double discounts;
        private String  seller_message;
        /**
         * 购买者留言
         */
        private String  buyer_message;

        private String createtime;
        private String receiver_name;
        private String receiver_mobile;
        private String receiver_state;
        private String receiver_city;
        private String receiver_district;
        private String receiver_address;
        private List<DetailsBeanMainVo> details;
        /**
         * 有改动 gifs-->books
         */
        private List<GiftVo> books;

        public List<GiftVo> getBooks() {
            return books;
        }

        public void setBooks(List<GiftVo> books) {
            this.books = books;
        }

        public String getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(String ordernum) {
            this.ordernum = ordernum;
        }

        public String getBuyer_message() {
            return buyer_message;
        }

        public void setBuyer_message(String buyer_message) {
            this.buyer_message = buyer_message;
        }

        public int getOrdertype() {
            return ordertype;
        }

        public void setOrdertype(int ordertype) {
            this.ordertype = ordertype;
        }

        public double getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(double totalprice) {
            this.totalprice = totalprice;
        }

        public String getYzstate() {
            return yzstate;
        }

        public void setYzstate(String yzstate) {
            this.yzstate = yzstate;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public double getDiscounts() {
            return discounts;
        }

        public void setDiscounts(double discounts) {
            this.discounts = discounts;
        }

        public String getSeller_message() {
            return seller_message;
        }

        public void setSeller_message(String seller_message) {
            this.seller_message = seller_message;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
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

        public List<DetailsBeanMainVo> getDetails() {
            return details;
        }

        public void setDetails(List<DetailsBeanMainVo> details) {
            this.details = details;
        }

        public List<GiftVo> getGifts() {
            return getBooks();
        }

        public void setGifts(List<GiftVo> gifts) {
            this.books = gifts;
        }
    }
}
